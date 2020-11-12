package net.scythe.domain.board;

import java.util.Map;

public class Space {
    private final String id;
    private Terrain terrain;
    private Map<Direction, Space> neighbour;
    private boolean encounter;
    private boolean tunnel;
    private Faction faction;
    private Map<Direction, Space> rivers;

    public Space(String id, Terrain terrain, Map<Direction, Space> neighbour, Map<Direction,Space> rivers, boolean encounter, boolean tunnel, Faction faction) {
        this.id = id;
        this.terrain = terrain;
        this.neighbour = neighbour;
        this.rivers = rivers;
        this.encounter = encounter;
        this.tunnel = tunnel;
        this.faction = faction;
    }

    public String getId() {
        return id;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public Space getSpace(Direction direction) {
        return neighbour.get(direction);
    }

    public Direction getDirectionToNeighbour(Space space) {
        Direction direction = null;
        for (Map.Entry<Direction, Space> entry : neighbour.entrySet()) {
            if (space.equals(entry.getValue())) {
                direction = entry.getKey();
                break;
            }
        }
        return direction;
    }

    public boolean isRiver(Direction direction) {
        return rivers.containsKey(direction);
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

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();
        message.append(String.format("Space (%1$s)", id));
        for (Map.Entry<Direction, Space> entry : neighbour.entrySet()) {
            message.append(String.format("(%1$s) -> (%2$s)", entry.getKey(), entry.getValue().getId()));
        }
        return message.toString();
    }
}