package solution;

/**
 * Type of Heuristic functions available
 */
public enum HeuristicType {
    /**
     * Considers only whether a goal has been reached and if position has changed
     */
    BASIC,

    /**
     * Considers whether a goal has been reached and:
     *      * breakdown conditions
     *      * slip conditions
     *      * actual distance travelled
     */
    ADVANCED
}
