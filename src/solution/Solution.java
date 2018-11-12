package solution;

import problem.ProblemSpec;
import simulator.Simulator;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Class to run the solution on a given problem input
 */
public class Solution {
    // problem specification for the solution
    private static final int TIME_LIMIT = 60000; //60 seconds
    // Logger to log messages to
    private static final Logger LOGGER = Logger.getLogger(Solution.class.getName());
    private int timeLimit;
    private ProblemSpec problem;
    private String output;

    /**
     * Attempt to solve the problem with an advanced heuristic using the full
     * time limit
     *
     * @return true iff a solution was reached
     */
    public boolean basicMCTS() {
        timeLimit = TIME_LIMIT;
        if (mcts(HeuristicType.ADVANCED)) {
            LOGGER.info("Problem solved!");
            return true;
        } else {
            LOGGER.info("Problem could not be solved in the time limit");
            return false;
        }
    }

    /**
     * Attempt to solve the problem with an advanced heuristic then an
     * alternation of basic and advanced heuristics repeatedly with
     * short time limits
     *
     * @return true iff a solution was reached
     */
    public boolean advancedMCTS() {
        timeLimit = TIME_LIMIT;
        if(!mcts(HeuristicType.ADVANCED) || !mcts(HeuristicType.BASIC)) {
            LOGGER.warning("Initial attempt failed");
            timeLimit = 5000;
            int stepsLeft = problem.getMaxT() * 3;
            int counter = 1;
            int heuristic = 0;
            while(stepsLeft > 0) {
                LOGGER.warning("Trying re-attempt " + counter + " of " + problem.getMaxT());
                if(mcts(HeuristicType.values()[heuristic])) {
                    System.out.println("Problem solved!");
                    return true;
                }
                stepsLeft--;
                counter++;
                heuristic = (heuristic + 1) % HeuristicType.values().length;
            }
        } else {
            System.out.println("Problem solved!");
            return true;
        }
        System.out.println("Problem could not be solved in the time limit");
        return false;
    }

    /**
     * Perform Monte-Carlo Tree Search for a the problem until solved
     * or time limit reached
     *
     * @param heuristic The heuristic function to use
     * @return true iff a solution was reached
     */
    private boolean mcts(HeuristicType heuristic) {
        Simulator simulator = new Simulator(problem, output);
        Tree tree = new Tree(problem, simulator, true, heuristic);

        long startTime = System.currentTimeMillis();
        long endTime = startTime + timeLimit;
        while(!tree.isSolved() && System.currentTimeMillis() < endTime) {
            tree.selectAction();
        }

        LOGGER.fine("Number of nodes - " + tree.numNodes());
        return tree.isSolved();
    }

    /**
     * Load a new problem file to produce solution
     */
    public Solution(ProblemSpec problem, String output) {
        this.problem = problem;
        this.output = output;
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("Invalid Usage: java ProgramName inputFileName outputFileName");
            System.exit(1);
        }

        String input = args[0];
        String output = args[1];

        Solution sol = new Solution(new ProblemSpec(input), output);
        sol.advancedMCTS();
    }
}
