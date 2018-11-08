package solution;

import problem.Action;
import problem.ActionType;
import problem.ProblemSpec;
import simulator.State;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Util {
    public static double getReward(ProblemSpec spec, State startState, State endState) {
        int n = spec.getN();
        int endPos = endState.getPos();
        if(endPos == n) {
            return 100; //big reward for reaching goal state
        }

        if(startState == null) {
            return endPos;
        }
        int startPos = startState.getPos();
        if(startPos == endPos) {
            return -1; //small penalty for staying in the same state
        } else if(startPos > endPos) {
            return endPos - startPos - 1; //relatively small penalty for going backwards
        } else {
            return endPos - startPos; //small reward for going forwards
        }
    }

    public static List<Action> getLevelActions(ProblemSpec spec) {
        List<Action> actions = new LinkedList<>();
        for(ActionType type : spec.getLevel().getAvailableActions()) {
            actions.add(new Action(type));
        }
        return actions;
    }
}
