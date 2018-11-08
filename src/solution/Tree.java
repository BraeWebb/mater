package solution;

import problem.Action;
import problem.ActionType;
import problem.ProblemSpec;
import simulator.Simulator;
import simulator.State;

import java.util.LinkedList;
import java.util.List;

public class Tree {
    private final static int SIM_TIME = 10000; //10 seconds

    private TreeNode root;
    private ProblemSpec problem;
    private Simulator sim;

    public Tree(ProblemSpec problem, Simulator sim) {
        this.root = new TreeNode(problem);
        this.problem = problem;
        this.sim = sim;
    }

    private double simulate(List<Action> actionsTaken) {
        sim.reset();
        double value = 0;
        State pre = null;
        for(Action action : actionsTaken) {
            State post = sim.step(action);
            value += Util.getReward(problem, pre, post);
            if(post.getPos() == problem.getN()) {
                return value; //goal state reached
            }
            pre = post;
        }
        long startTime = System.currentTimeMillis();
        long endTime = startTime + SIM_TIME;
        while(System.currentTimeMillis() < endTime) {
            Action action = new Action(ActionType.MOVE); //TODO: use a better heuristic
            State post = sim.step(action);
            value += Util.getReward(problem, pre, post);
            if(post.getPos() == problem.getN()) {
                return value; //goal state reached
            }
            pre = post;
        }
        return value;
    }

    public void selectAction() {
        List<TreeNode> visited = new LinkedList<>();
        List<Action> actionsTaken = new LinkedList<>();
        TreeNode current = root;
        visited.add(root);

        while (!current.isLeaf()) {
            current = current.select();
            visited.add(current);
            actionsTaken.add(current.getChildren().get(current));
        }

        if(current.numVisits != 0) {
            current.expand();
            visited.add(current.select());
        }

        double newValue = simulate(actionsTaken);
        for (TreeNode seen : visited) {
            seen.backpropagate(newValue);
        }
    }
}
