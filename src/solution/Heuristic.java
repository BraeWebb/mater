package solution;

import problem.ProblemSpec;
import simulator.State;

/**
 * Utility class for managing multiple differing heuristic functions
 */
public abstract class Heuristic {

    /**
     * Abstract heuristic method to calculate the heuristic for transitioning
     * from one start state to other state in a given problem
     *
     * @param spec The problem space
     * @param start Starting state
     * @param end State that is being transitioned into
     *
     * @return The reward for the relevant transition
     */
    public abstract double heuristic(ProblemSpec spec, State start, State end);

    /**
     * Return the Heuristic calculator for a given type of heuristic
     *
     * @param type The type of heuristic function
     * @return A Heuristic containing the associated heuristic method for the
     *          given heuristic type
     */
    public static Heuristic getHeuristic(HeuristicType type) {
        switch (type) {
            case BASIC:
                return new Heuristic() {
                    @Override
                    public double heuristic(ProblemSpec spec, State start, State end) {
                        return Heuristic.heuristic01(spec, start, end);
                    }
                };
            case ADVANCED:
                return new Heuristic() {
                    @Override
                    public double heuristic(ProblemSpec spec, State start, State end) {
                        return Heuristic.heuristic00(spec, start, end);
                    }
                };
        }

        return null;
    }

    /**
     * An advanced heuristic function which considers
     *      * breakdown conditions
     *      * slip conditions
     *      * actual distance travelled
     *
     * @param spec The problem space
     * @param startState Starting state
     * @param endState State that is being transitioned into
     *
     * @return The reward for the relevant transition
     */
    private static double heuristic00(ProblemSpec spec, State startState, State endState) {
        int endPos = endState.getPos();
        if (endPos == spec.getN()) {
            return 100; //big reward for reaching goal state
        } else if (endState.isInBreakdownCondition() || endState.isInSlipCondition()) {
            return -5;
        }

        if(startState == null) {
            return endPos;
        }

        int startPos = startState.getPos();
        if (startPos == endPos) {
            return -0.1; //small penalty for staying in the same state
        } else if (startPos > endPos) {
            return endPos - startPos; //relatively small penalty for going backwards
        } else {
            return endPos - startPos; //small reward for going forwards
        }
    }

    /**
     * A basic heuristic function which considers only whether the goal has
     * been reached or whether there has been a change in position
     *
     * @param spec The problem space
     * @param startState Starting state
     * @param endState State that is being transitioned into
     *
     * @return The reward for the relevant transition
     */
    private static double heuristic01(ProblemSpec spec, State startState, State endState) {
        int endPos = endState.getPos();
        if(endPos == spec.getN()) {
            return 100; //big reward for reaching goal state
        }

        if(startState == null) {
            return endPos;
        }

        int startPos = startState.getPos();
        return startPos != endPos ? 10 : 0; // small reward for changing position
    }
}
