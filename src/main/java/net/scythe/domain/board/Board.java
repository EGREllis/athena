package net.scythe.domain.board;

import java.util.Map;

/**
 * This Class is intended to represent the Scythe board
 * Radial representation from the factory outwards
 */
public class Board {
    private Map<String,Space> spaces;

    public Board(Map<String,Space> spaces) {
        this.spaces = spaces;
    }


}
