package net.scythe.domain.resource;

import net.scythe.util.Util;

public enum Resource {
    OIL,
    STEEL,
    WOOD,
    FOOD,
    WORKER,
    VOID,
    COIN,
    POPULARITY,
    POWER;

    public static Resource parseResource(String resource) {
        Resource result = null;
        switch(resource) {
            case "oil":
                result = OIL;
                break;
            case "steel":
                result = STEEL;
                break;
            case "wood":
                result = WOOD;
                break;
            case "food":
                result = FOOD;
                break;
            case "worker":
                result = WORKER;
                break;
            case "gold":
            case "coin":
                result = COIN;
                break;
            case "popularity":
                result = POPULARITY;
                break;
            case "power":
                result = POWER;
                break;
            default:
                Util.unreachableBranch(resource);
                break;
        }
        return result;
    }
}
