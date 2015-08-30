package com.derekentringer.gizmo.util;

import com.derekentringer.gizmo.model.level.LevelModel;

import java.util.ArrayList;

public class GameLevelUtils {

    public static final ArrayList<LevelModel> gameLevels = new ArrayList<LevelModel>();

    public static void buildGameLevelList() {
        LevelModel levelOne = new LevelModel(0,
                "res/maps/level_one/level_one.tmx",
                "res/maps/level_one/mid_background.tmx",
                "res/maps/level_one/background.tmx");

        LevelModel levelTwo = new LevelModel(1,
                "res/maps/level_two/level_two.tmx",
                "res/maps/level_two/mid_background.tmx",
                "res/maps/level_two/background.tmx");

        LevelModel levelThree = new LevelModel(2,
                "res/maps/level_three/level_three.tmx",
                "res/maps/level_three/mid_background.tmx",
                "res/maps/level_three/background.tmx");

        gameLevels.add(levelOne);
        gameLevels.add(levelTwo);
        gameLevels.add(levelThree);
    }

}