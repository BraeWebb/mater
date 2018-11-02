package solution;

import java.util.ArrayList;

/**
 * Represents a node in a search tree (used for MCTS)
 */
public class TreeNode {

    // the parent of this node
    private TreeNode parent;

    // the children of this node
    private ArrayList<TreeNode> children;

    /**
     * Constructs a new TreeNode object
     */
    public TreeNode(TreeNode parent) {
        this.parent = parent;
        this.children = new ArrayList<>();
    }

    public TreeNode() {
        this(null);
    }

    /**
     * @return the children of this node
     */
    public ArrayList<TreeNode> getChildren() {
        return children;
    }
}
