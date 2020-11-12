package net.scythe.domain.board;

import java.util.Map;

public class Space {
    private Terrain terrain;
    private Map<Direction, Space> neighbour;
    private boolean encounter;
    private boolean tunnel;
    private Faction faction;

    public Space(Terrain terrain, Map<Direction, Space> neighbour, boolean encounter, boolean tunnel, Faction faction) {
        this.terrain = terrain;
        this.neighbour = neighbour;
        this.encounter = encounter;
        this.tunnel = tunnel;
        this.faction = faction;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public Space getSpace(Direction direction) {
        return neighbour.get(direction);
    }

    public boolean isEncounter() {
        return encounter;
    }

    public boolean isTunnel() {
        return tunnel;
    }

    public Faction getFaction() {
        return faction;
    }
}