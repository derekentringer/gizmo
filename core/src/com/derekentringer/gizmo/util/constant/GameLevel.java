package com.derekentringer.gizmo.util.constant;

public class GameLevel<I,S,X,Y> {

    private final I levelInt;
    private final S levelMap;
    private final X xpos;
    private final Y ypos;

    public GameLevel(I levelInt, S levelMap, X xpos, Y ypos) {
        this.levelInt = levelInt;
        this.levelMap = levelMap;
        this.xpos = xpos;
        this.ypos = ypos;
    }

    public I getLevel() { return levelInt; }
    public S getMap() { return levelMap; }
    public X getXpos() { return xpos; }
    public Y getYpos() { return ypos; }

    @Override
    public int hashCode() { return levelInt.hashCode() ^ levelMap.hashCode() ^ xpos.hashCode() ^ ypos.hashCode(); }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof GameLevel)) return false;
        GameLevel pairo = (GameLevel) o;
        return this.levelInt.equals(pairo.getLevel())
                && this.levelMap.equals(pairo.getMap());
    }

}