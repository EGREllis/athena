package net.scythe.domain.board.mat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
    This class tracks:
        1) Which actions are paired
        2) Which upgrades have been paid for
        3) Which enlists have been paid for
        4) Which buildings have been built
 */
public class Mat {
    private final List<ActionPair> pairs;
    private final Map<Action, Boolean> enlists;
    private final Map<Building, Boolean> buildings;
    private final Map<Action, Integer> upgrades;

    public Mat(List<ActionPair> pairs, Map<Action, Boolean> enlists, Map<Building, Boolean> buildings, Map<Action,Integer> upgrades) {
        this.pairs = pairs;
        this.enlists = enlists;
        this.buildings = buildings;
        this.upgrades = upgrades;
    }

    public static Mat newMat(List<ActionPair> pairs) {
        Map<Action, Boolean> enlists = createEnlistMap();
        Map<Building, Boolean> buildings = createBuildingMap();
        Map<Action, Integer> upgrades = new HashMap<>();

        for (Action action : Action.values()) {

        }

        return new Mat(pairs, enlists, buildings, upgrades);
    }

    private static Map<Action, Boolean> createEnlistMap() {
        Map<Action, Boolean> enlists = new HashMap<>();
        for (Action action : Action.values()) {
            if (action.isTop()) {
                enlists.put(action, false);
            }
        }
        return enlists;
    }

    private static Map<Building, Boolean> createBuildingMap() {
        Map<Building, Boolean> buildings = new HashMap<>();
        for (Building building : Building.values()) {
            buildings.put(building, false);
        }
        return buildings;
    }

}
