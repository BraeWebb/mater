package solution;

import problem.ActionType;
import problem.ProblemSpec;

import java.util.List;

public class Tree {
    private TreeNode root;
    private ProblemSpec problem;
    List<ActionType> actions;

    public Tree(ProblemSpec problem) {
        this.root = null;
        this.problem = problem;
        this.actions = problem.getLevel().getAvailableActions();
    }
}
