package net.scythe.domain.board;

import net.scythe.domain.resource.Resource;
import net.scythe.util.Util;

public enum Terrain {
    FACTORY(Resource.VOID),
    VILLAGE(Resource.WORKER),
    OIL_FIELD(Resource.OIL),
    MOUNTAIN(Resource.STEEL),
    FOREST(Resource.WOOD),
    FARM(Resource.FOOD),
    LAKE(Resource.VOID),
    HOME(Resource.VOID);

    private Resource resource;

    Terrain(Resource resource) {
        this.resource = resource;
    }

    private Resource getResource() {
        return resource;
    }

    public static Terrain parseTerrain(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Does not accept null arguments.");
        }
        Terrain result = null;
        String value = text.toLowerCase();
        switch (value) {
            case "factory":
                result = FACTORY;
                break;
            case "village":
                result = VILLAGE;
                break;
            case "oil":
                result = OIL_FIELD;
                break;
            case "mountain":
                result = MOUNTAIN;
                break;
            case "forest":
                result = FOREST;
                break;
            case "farm":
                result = FARM;
                break;
            case "lake":
                result = LAKE;
                break;
            case "home":
                result = HOME;
                break;
            default:
                Util.unreachableBranch(value);
                break;
        }
        return result;
    }
}
