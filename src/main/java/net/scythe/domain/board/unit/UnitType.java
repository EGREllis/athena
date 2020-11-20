package net.scythe.domain.board.unit;

public enum UnitType {
    LEADER(true, false),
    MECH(true, true),
    WORKER(true, false);

    private final boolean canCarryWorkers;
    private final boolean canCarryResources;

    UnitType(boolean canCarryResources, boolean canCarryWorkers) {
        this.canCarryResources = canCarryResources;
        this.canCarryWorkers = canCarryWorkers;
    }

    public boolean isAbleToCarryResources() {
        return canCarryResources;
    }

    public boolean isAbleToCarryWorkers() {
        return canCarryWorkers;
    }
}
