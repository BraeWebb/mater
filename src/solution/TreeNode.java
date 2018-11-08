package solution;

import problem.Action;
import problem.ActionType;
import problem.ProblemSpec;
import simulator.State;

import java.util.*;

/**
 * Represents a node in a search tree (used for MCTS)
 */
public class TreeNode {

    // the parent of this node
    private TreeNode parent;

    // the children of this node
    private Map<TreeNode, Action> children;

    private ProblemSpec problem;

    private State state; //is this needed?

    int numVisits;
    int qValue;

    /**
     * Constructs a new TreeNode object
     */
    public TreeNode(TreeNode parent, ProblemSpec problem) {
        this.parent = parent;
        this.problem = problem;

        this.numVisits = 0;
        this.qValue = 0; // not sure what this should start at
    }

    public TreeNode(ProblemSpec problem) {
        this(null, problem);
    }

    TreeNode select() {
        TreeNode selected = null;
        double currentBest = Double.MIN_VALUE;
        for (TreeNode child : children.keySet()) {
            double uct = child.uctValue();
            if (uct > currentBest) {
                selected = child;
                currentBest = uct;
            }
        }

        return selected;
    }

    private double uctValue() {
        if(numVisits == 0) {
            return -1;
        }
        return (qValue / numVisits) + Math.sqrt(Math.log(numVisits + 1) / numVisits);
    }

    void expand() {
        children = new HashMap<>();
        List<Action> actions = Util.getLevelActions(problem);
        for (Action action : actions) {
            children.put(new TreeNode(this, problem), action);
        }
    }

    void backpropagate(double value) {
        numVisits++;
        qValue += value; // might wanna average with current rather than just adding? idk
    }

    public boolean isLeaf() {
        return children == null;
    }

    /**
     * @return the children of this node
     */
    public Map<TreeNode, Action> getChildren() {
        return children;
    }
}
