package net.scythe.domain.board;

import net.scythe.util.Util;

public enum Direction {
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH_WEST,
    WEST,
    NORTH_WEST;

    public static Direction parseDirection(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Null not allowed");
        }
        Direction result = null;
        String value = text.toLowerCase();
        switch (value) {
            case "north_east":
                result = NORTH_EAST;
                break;
            case "east":
                result = EAST;
                break;
            case "south_east":
                result = SOUTH_EAST;
                break;
            case "south_west":
                result = SOUTH_WEST;
                break;
            case "west":
                result = WEST;
                break;
            case "north_west":
                result = NORTH_WEST;
                break;
            default:
                Util.unreachableBranch(value);
                break;
        }
        return result;
    }
}
