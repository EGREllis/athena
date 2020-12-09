package net.scythe.domain.board.mat;

public class ActionPair {
    private final Action topAction;
    private final Action bottomAction;

    public ActionPair(Action topAction, Action bottomAction) {
        if (    topAction == null ||
                bottomAction == null ||
                !topAction.isTop() ||
                bottomAction.isTop() ) {
            throw new IllegalArgumentException(String.format("Top: %1$s Bottom: %2$s", topAction, bottomAction));
        }
        this.topAction = topAction;
        this.bottomAction = bottomAction;
    }

    public Action getTopAction() {
        return topAction;
    }

    public Action getBottomAction() {
        return bottomAction;
    }
}
