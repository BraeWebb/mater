package solution;

import problem.Action;
import problem.ActionType;
import problem.ProblemSpec;
import simulator.Simulator;
import simulator.State;

/**
 * Class to run the solution on a given problem input
 */
public class Solution {
    // problem specification for the solution
    private static final int TIME_LIMIT = 60000; //60 seconds
    private int timeLimit;
    private ProblemSpec problem;
    private String output;

    public boolean basicMCTS() {
        timeLimit = TIME_LIMIT;
        if(mcts(0)) {
            System.out.println("Problem solved!");
            return true;
        } else {
            System.out.println("Problem could not be solved in the time limit");
            return false;
        }
    }

    public boolean advancedMCTS() {
        timeLimit = TIME_LIMIT;
        if(!mcts(0) || !mcts(1)) {
            System.out.println("Initial attempt failed");
            timeLimit = 5000;
            int stepsLeft = problem.getMaxT() * 3;
            int counter = 1;
            int heuristic = 0;
            while(stepsLeft > 0) {
                System.out.println("Trying re-attempt " + counter + " of " + problem.getMaxT());
                if(mcts(heuristic)) {
                    System.out.println("Problem solved!");
                    return true;
                }
                stepsLeft--;
                counter++;
                heuristic = (heuristic + 1) % 2;
            }
        } else {
            System.out.println("Problem solved!");
            return true;
        }
        System.out.println("Problem could not be solved in the time limit");
        return false;
    }

    private boolean mcts(int heuristic) {
        Simulator sim = new Simulator(problem, output);
        Tree tree = new Tree(problem, sim, true, heuristic);

        long startTime = System.currentTimeMillis();
        long endTime = startTime + timeLimit;
        while(!tree.isSolved() && System.currentTimeMillis() < endTime) {
            tree.selectAction();
        }

        System.out.println("Number of nodes - " + tree.numNodes());
        return tree.isSolved();
    }

    /**
     * Load a new problem file to produce solution
     */
    public Solution(ProblemSpec problemSpec) {
        problem = problemSpec;
        this.output = "examples/level_1/myoutput.txt"; //TODO: don't hardcode this
    }

    public Action nextAction() {
        return new Action(ActionType.MOVE);
    }

    public void addState(State state) {

    }

    public static void main(String[] args) throws java.io.IOException {
        Solution sol = new Solution(new ProblemSpec("examples/level_1/input_lvl1_2.txt"));
        sol.advancedMCTS();
    }
}
