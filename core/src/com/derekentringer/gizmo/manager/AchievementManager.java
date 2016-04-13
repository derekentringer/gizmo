package com.derekentringer.gizmo.manager;

import com.derekentringer.gizmo.util.log.GLog;

import java.util.HashMap;

public class AchievementManager {

    public static final String TAG = AchievementManager.class.getSimpleName();

    private static HashMap<String, String> achievementsPlayServices = new HashMap<String, String>();

    public static final String LET_THE_ADVENTURE_BEGIN = "achievement_let_the_adventure_begin";
    public static final String YA_LIKE_DAGS = "achievement_ya_like_dags";
    public static final String BOOMERANG_WOOD = "achievement_boomerang";
    public static final String HEADSHOT = "achievement_headshot";
    public static final String PHANTOM_BE_GONE = "achievement_phantom_be_gone";
    public static final String GAME_OVER_MAN_GAME_OVER = "achievement_game_over_man_game_over";

    private AchievementManager() {
    }

    public static void buildAchievementsPlayServices() {
        GLog.d(TAG, "building achievements");
        achievementsPlayServices.put(LET_THE_ADVENTURE_BEGIN, "CgkI2aLN-rkTEAIQDA");
        achievementsPlayServices.put(YA_LIKE_DAGS, "CgkI2aLN-rkTEAIQAQ");
        achievementsPlayServices.put(BOOMERANG_WOOD, "CgkI2aLN-rkTEAIQBA");
        achievementsPlayServices.put(HEADSHOT, "CgkI2aLN-rkTEAIQAw");
        achievementsPlayServices.put(PHANTOM_BE_GONE, "CgkI2aLN-rkTEAIQAg");
        achievementsPlayServices.put(GAME_OVER_MAN_GAME_OVER, "CgkI2aLN-rkTEAIQBQ");
    }

    public static String getAchievementsPlayServices(String key) {
        GLog.d(TAG, "getAchievementsPlayServices: " + key);
        if (achievementsPlayServices.containsKey(key)) {
            return achievementsPlayServices.get(key);
        }
        return null;
    }

}