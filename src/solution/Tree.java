package solution;

import problem.Action;
import problem.ActionType;
import problem.ProblemSpec;
import simulator.Simulator;
import simulator.State;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Tree {
    private final static int SIM_TIME = 10000; //10 seconds

    private TreeNode root;
    private ProblemSpec problem;
    private Simulator sim;
    private boolean solved;

    public Tree(ProblemSpec problem, Simulator sim, boolean expand) {
        this.root = new TreeNode(problem);
        this.problem = problem;
        this.sim = sim;
        this.solved = false;
        if(expand) {
            root.expand();
        }
    }

    public Tree(ProblemSpec problem, Simulator sim) {
        this(problem, sim, false);
    }

    private double simulate(List<Action> actionsTaken) {
        sim.reset();
        double value = 0;
        State pre = null;
        for(Action action : actionsTaken) {
            State post = sim.step(action);
            if(post == null || (pre == post && action.getActionType() != ActionType.MOVE)) {
                return -100; //not a valid branch or not a useful action
            }
            value += Util.getReward(problem, pre, post);
            if(post.getPos() == problem.getN()) {
                solved = true;
                return value; //goal state reached
            }
            pre = post;
        }
        long startTime = System.currentTimeMillis();
        long endTime = startTime + SIM_TIME;
        while(System.currentTimeMillis() < endTime) {
            Action action = new Action(ActionType.MOVE); //TODO: use a better heuristic
            State post = sim.step(action);
            if(post == null) {
                return value;
            }
            value += Util.getReward(problem, pre, post);
            if(post.getPos() == problem.getN()) {
                solved = true;
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
            TreeNode selected = current.select();
            visited.add(selected);
            actionsTaken.add(current.getChildren().get(selected));
            current = selected;
        }

        if(current.hasSimulated()) {
            current.expand();
            visited.add(current.select());
        }

        double newValue = simulate(actionsTaken);
        for (TreeNode seen : visited) {
            seen.backpropagate(newValue);
        }
    }

    public boolean isSolved() {
        return solved;
    }
}
