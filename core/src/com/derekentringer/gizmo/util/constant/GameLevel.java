package com.derekentringer.gizmo.util.constant;

public class GameLevel<L,R> {

    private final L left;
    private final R right;

    public GameLevel(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L getLevel() { return left; }
    public R getMap() { return right; }

    @Override
    public int hashCode() { return left.hashCode() ^ right.hashCode(); }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof GameLevel)) return false;
        GameLevel pairo = (GameLevel) o;
        return this.left.equals(pairo.getLevel()) &&
                this.right.equals(pairo.getMap());
    }

}