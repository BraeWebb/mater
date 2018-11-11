package solution;

import problem.*;
import simulator.Simulator;
import simulator.State;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Util {
    public static double getReward(ProblemSpec spec, State startState, State endState, int choice) {
        switch(choice) {
            case 0:
                return heuristic00(spec, startState, endState);
            case 1:
                return heuristic01(spec, startState, endState);
        }
        return 0;
    }

    private static double heuristic00(ProblemSpec spec, State startState, State endState) {
        int n = spec.getN();
        int endPos = endState.getPos();
        if(endPos == n) {
            return 100; //big reward for reaching goal state
        } else if(endState.isInBreakdownCondition() || endState.isInSlipCondition()) {
            return -5;
        }

        if(startState == null) {
            return endPos;
        }
        int startPos = startState.getPos();
        if(startPos == endPos) {
            return -0.1; //small penalty for staying in the same state
        } else if(startPos > endPos) {
            return endPos - startPos; //relatively small penalty for going backwards
        } else {
            return endPos - startPos; //small reward for going forwards
        }
    }

    private static double heuristic01(ProblemSpec spec, State startState, State endState) {
        int n = spec.getN();
        int endPos = endState.getPos();
        if(endPos == n) {
            return 100; //big reward for reaching goal state
        }

        if(startState == null) {
            return endPos;
        }
        int startPos = startState.getPos();

        if (startPos != endPos) {
            return 10;
        }
        return 0;
    }

    //this method may need some refactoring
    public static List<Action> getLevelActions(ProblemSpec spec) {
        List<Action> actions = new LinkedList<>();
        for(ActionType type : spec.getLevel().getAvailableActions()) {
            if(type == ActionType.CHANGE_CAR) {
                for(String car : spec.getCarOrder()) {
                    actions.add(new Action(type, car));
                }
            } else if(type == ActionType.CHANGE_DRIVER) {
                for(String driver : spec.getDriverOrder()) {
                    actions.add(new Action(type, driver));
                }
            } else if(type == ActionType.CHANGE_CAR_AND_DRIVER) {
                for(String driver : spec.getDriverOrder()) {
                    for(String car : spec.getCarOrder()) {
                        actions.add(new Action(type, car, driver));
                    }
                }
            } else if(type == ActionType.CHANGE_TIRES) {
                for(Tire tire : spec.getTireOrder()) {
                    actions.add(new Action(type, tire));
                }
            } else if(type == ActionType.CHANGE_PRESSURE) {
                for(TirePressure pressure : TirePressure.values()) {
                    actions.add(new Action(type, pressure));
                }
            } else if(type == ActionType.ADD_FUEL) {
                for(int i = 1; i <= 5; i++) {
                    actions.add(new Action(type, i*10)); //TODO: make this work for smaller increments
                }
            } else if(type == ActionType.CHANGE_TIRE_FUEL_PRESSURE) {
                for(int i = 1; i <= 5; i++) {
                    for(TirePressure pressure : TirePressure.values()) {
                        for(Tire tire : spec.getTireOrder()) {
                            actions.add(new Action(type, tire, i*10, pressure));
                        }
                    }
                }
            } else {
                actions.add(new Action(type));
            }
        }

        return actions;
    }
}
