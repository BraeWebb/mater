package solution;

import problem.Action;
import problem.ActionType;
import problem.ProblemSpec;
import simulator.State;

/**
 * Class to run the solution on a given problem input
 */
public class Solution {
    // problem specification for the solution
    private ProblemSpec problem;

    /**
     * Load a new problem file to produce solution
     */
    public Solution(ProblemSpec problemSpec) {
        problem = problemSpec;
    }

    public Action nextAction() {
        return new Action(ActionType.MOVE);
    }

    public void addState(State state) {

    }
}
