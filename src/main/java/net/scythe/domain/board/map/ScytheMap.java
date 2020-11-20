package net.scythe.domain.board.map;

/**
 * This Class is intended to represent the Scythe board
 * Radial representation from the factory outwards
 */
public class ScytheMap {
    private java.util.Map<String,Space> spaces;

    public ScytheMap(java.util.Map<String,Space> spaces) {
        this.spaces = spaces;
    }
}
