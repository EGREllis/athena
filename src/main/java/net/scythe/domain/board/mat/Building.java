package net.scythe.domain.board.mat;

import net.scythe.util.Util;

public enum Building {
    TUNNEL,
    MONUMENT,
    ARMOURY,
    WINDMILL;

    public static Building parseBuilding(String text) {
        String key = text.toLowerCase();
        Building result = null;
        switch(key) {
            case "tunnel":
                result = TUNNEL;
                break;
            case "monument":
                result = MONUMENT;
                break;
            case "armoury":
                result = ARMOURY;
                break;
            case "windmill":
                result = WINDMILL;
                break;
            default:
                Util.unreachableBranch(key);
                break;
        }
        return result;
    }
}
