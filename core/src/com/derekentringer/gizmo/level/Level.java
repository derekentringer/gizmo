package com.derekentringer.gizmo.level;

public class Level {

    private int sLevelInt;
    private String sLevelMap;
    private String sLevelMidMap;
    private String sLevelBackMap;
    private int sXpos;
    private int sYpos;

    public Level(int levelInt, String levelMap, String levelMidMap, String levelBackMap, int xPos, int yPos) {
        sLevelInt = levelInt;
        sLevelMap = levelMap;
        sLevelMidMap = levelMidMap;
        sLevelBackMap = levelBackMap;
        sXpos = xPos;
        sYpos = yPos;
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

    public int getXpos() {
        return sXpos;
    }

    public void setsXpos(int xPos) {
        sXpos = xPos;
    }

    public int getYpos() {
        return sYpos;
    }

    public void setsYpos(int yPos) {
        sYpos = yPos;
    }

}