package net.scythe.domain.board;

import net.scythe.util.Util;

public enum Faction {
    RUSVIET,
    CRIMEA,
    POLONIA,
    ALBION,
    NORDIC,
    JAPAN,
    SAXONY;

    public static Faction parseFaction(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Null arguments are not accepted.");
        }
        String key = text.toLowerCase();
        Faction faction = null;
        switch(key) {
            case "rusviet":
                faction = RUSVIET;
                break;
            case "crimea":
                faction = CRIMEA;
                break;
            case "polonia":
                faction = POLONIA;
                break;
            case "albion":
                faction = ALBION;
                break;
            case "nordic":
                faction = NORDIC;
                break;
            case "japan":
                faction = JAPAN;
                break;
            case "saxony":
                faction = SAXONY;
                break;
            default:
                Util.unreachableBranch(key);
                break;
        }
        return faction;
    }
}
