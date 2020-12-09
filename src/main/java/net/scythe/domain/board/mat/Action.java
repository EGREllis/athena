package net.scythe.domain.board.mat;

public enum Action {
    MOVE("move", true),
    BOLSTER("bolster", true),
    TRADE("trade", true),
    PRODUCE("produce", true),
    UPGRADE("upgrade", false),
    DEPLOY("deploy", false),
    BUILD("build", false),
    ENLIST("enlist", false);

    private final String key;
    private final boolean isTop;

    Action(String key, boolean isTop) {
        this.key = key;
        this.isTop = isTop;
    }

    public static Action parseAction(String text) {
        String key = text.toLowerCase();
        Action result = null;
        for (Action action : Action.values()) {
            if (action.key.equals(key)) {
                result = action;
                break;
            }
        }
        return result;
    }

    public String getKey() {
        return key;
    }

    public boolean isTop() {
        return isTop;
    }
}
