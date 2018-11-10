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
    private static final int TIME_LIMIT = 120000; //2 minutes
    private ProblemSpec problem;
    private int timeLimit;
    private String output;

    public boolean mcts() {
        Simulator sim = new Simulator(problem, output);
        Tree tree = new Tree(problem, sim, true);

        long startTime = System.currentTimeMillis();
        long endTime = startTime + timeLimit;
        while(!tree.isSolved() && System.currentTimeMillis() < endTime) {
            tree.selectAction();
        }
        if(!tree.isSolved()) {
            System.out.println("Could not solve within 2 seconds");
        } else {
            System.out.println("Problem solved!");
        }

        return tree.isSolved();
    }

    /**
     * Load a new problem file to produce solution
     */
    public Solution(ProblemSpec problem, int timeout) {
        this.timeLimit = timeout;
        this.problem = problem;
        this.output = "examples/level_1/myoutput.txt"; //TODO: don't hardcode this
    }

    public Solution(ProblemSpec problem) {
        this(problem, TIME_LIMIT);
    }

    public Action nextAction() {
        return new Action(ActionType.MOVE);
    }

    public void addState(State state) {

    }

    public static void main(String[] args) throws java.io.IOException {
        Solution sol = new Solution(new ProblemSpec("examples/level_1/input_lvl1_2.txt"));
        sol.mcts();
    }
}
