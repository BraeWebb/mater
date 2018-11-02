package solution;

import problem.ProblemSpec;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a node in a search tree (used for MCTS)
 */
public class TreeNode {

    // the parent of this node
    private TreeNode parent;

    // the children of this node
    private TreeNode[] children;

    private ProblemSpec problem;

    private int numActions;
    private int numVisits;
    private int qValue;

    /**
     * Constructs a new TreeNode object
     */
    public TreeNode(TreeNode parent, ProblemSpec problem) {
        this.parent = parent;
        this.problem = problem;
        this.numActions = problem.getLevel().getAvailableActions().size();
        this.numVisits = 0;
        this.qValue = 0; // not sure what this should start at
    }

    public TreeNode() {
        this(null, null);
    }

    public void selectAction() {
        List<TreeNode> visited = new LinkedList<>();
        TreeNode current = this;
        visited.add(this);

        while (!current.isLeaf()) {
            current = current.select();
            visited.add(current);
        }

        // current is now a leaf node and needs to be expanded
        current.expand();

        TreeNode nodeToExplore = current.select();
        visited.add(nodeToExplore);
        double newValue = simulate();
        for (TreeNode seen : visited) {
            seen.backpropagate(newValue);
        }
    }

    private TreeNode select() {
        TreeNode selected = null;
        double currentBest = Double.MIN_VALUE;
        for (TreeNode child : children) {
            double uct = child.uctValue();
            if (uct > currentBest) {
                selected = child;
                currentBest = uct;
            }
        }

        return selected;
    }

    private double uctValue() {
        return (qValue / numVisits) + Math.sqrt(Math.log(numVisits + 1) / numVisits);
    }

    private void expand() {
        children = new TreeNode[numActions];
        for (int i = 0; i < numActions; i++) {
            children[i] = new TreeNode(this, problem);
        }
    }

    private double simulate() {
        // do the thing
        return 0;
    }

    private void backpropagate(double value) {
        numVisits++;
        qValue += value; // might wanna average with current rather than just adding? idk
    }

    public boolean isLeaf() {
        return children == null;
    }

    /**
     * @return the children of this node
     */
    public TreeNode[] getChildren() {
        return children;
    }
}
