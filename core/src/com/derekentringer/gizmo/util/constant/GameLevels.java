package com.derekentringer.gizmo.util.constant;

public class GameLevels<I,S,M,B,X,Y> {

    private final I levelInt;
    private final S levelMap;
    private final M levelMidMap;
    private final B levelBackMap;
    private final X xpos;
    private final Y ypos;

    public GameLevels(I levelInt, S levelMap, M levelMidMap, B levelBackMap, X xpos, Y ypos) {
        this.levelInt = levelInt;
        this.levelMap = levelMap;
        this.levelMidMap = levelMidMap;
        this.levelBackMap = levelBackMap;
        this.xpos = xpos;
        this.ypos = ypos;
    }

    public I getLevel() { return levelInt; }
    public S getMap() { return levelMap; }
    public M getMidMap() { return levelMidMap; }
    public B getBackMap() { return levelBackMap; }
    public X getXpos() { return xpos; }
    public Y getYpos() { return ypos; }

    @Override
    public int hashCode() {
        return levelInt.hashCode()
                ^ levelMap.hashCode()
                ^ levelMidMap.hashCode()
                ^ levelBackMap.hashCode()
                ^ xpos.hashCode()
                ^ ypos.hashCode(); }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof GameLevels)) return false;
        GameLevels pairo = (GameLevels) o;
        return this.levelInt.equals(pairo.getLevel())
                && this.levelMap.equals(pairo.getMap());
    }

}