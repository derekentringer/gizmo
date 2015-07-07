package com.derekentringer.gizmo.level;

public class Level {

    private int sLevelInt;
    private String sLevelMap;
    private String sLevelMidMap;
    private String sLevelBackMap;

    public Level(int levelInt, String levelMap, String levelMidMap, String levelBackMap) {
        sLevelInt = levelInt;
        sLevelMap = levelMap;
        sLevelMidMap = levelMidMap;
        sLevelBackMap = levelBackMap;
    }

    public int getLevelInt() {
        return sLevelInt;
    }

    public void setLevelInt(int levelInt) {
        sLevelInt = levelInt;
    }

    public String getLevelMap() {
        return sLevelMap;
    }

    public void setLevelMap(String levelMap) {
        sLevelMap = levelMap;
    }

    public String getsLevelMidMap() {
        return sLevelMidMap;
    }

    public void setsLevelMidMap(String levelMidMap) {
        sLevelMidMap = levelMidMap;
    }

    public String getsLevelBackMap() {
        return sLevelBackMap;
    }

    public void setsLevelBackMap(String levelBackMap) {
        sLevelBackMap = levelBackMap;
    }

}