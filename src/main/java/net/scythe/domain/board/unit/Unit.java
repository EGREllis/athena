package net.scythe.domain.board.unit;

import net.scythe.domain.board.Faction;

public class Unit {
    private final Faction faction;
    private final UnitType unitType;

    public Unit(final Faction faction, final UnitType unitType) {
        this.faction = faction;
        this.unitType = unitType;
    }

    public Faction getFaction() {
        return faction;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    @Override
    public int hashCode() {
        return faction.hashCode() * 13 + unitType.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        if (obj instanceof Unit) {
            Unit other = (Unit)obj;
            result = other.faction.equals(faction) && other.unitType.equals(unitType);
        }
        return result;
    }
}
