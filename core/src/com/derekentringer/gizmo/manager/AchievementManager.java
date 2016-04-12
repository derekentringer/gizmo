package com.derekentringer.gizmo.manager;

import com.derekentringer.gizmo.util.log.GLog;

import java.util.HashMap;

public class AchievementManager {

    public static final String TAG = AchievementManager.class.getSimpleName();

    private static AchievementManager mInstance;

    public HashMap<String, String> achievementsPlayServices = new HashMap<String, String>();

    public static AchievementManager getInstance() {
        if (mInstance == null) {
            mInstance = new AchievementManager();
        }
        return mInstance;
    }

    private AchievementManager() {
    }

    public void buildAchievementsPlayServices() {
        GLog.d(TAG, "building achievements");
        achievementsPlayServices.put("achievement_let_the_adventure_begin", "CgkI2aLN-rkTEAIQDA");
        achievementsPlayServices.put("achievement_ya_like_dags", "CgkI2aLN-rkTEAIQAQ");
        achievementsPlayServices.put("achievement_boomerang", "CgkI2aLN-rkTEAIQBA");
        achievementsPlayServices.put("achievement_headshot", "CgkI2aLN-rkTEAIQAw");
        achievementsPlayServices.put("achievement_phantom_be_gone", "CgkI2aLN-rkTEAIQAg");
        achievementsPlayServices.put("achievement_game_over_man_game_over", "CgkI2aLN-rkTEAIQBQ");
    }

    public String getAchievementsPlayServices(String key) {
        GLog.d(TAG, "getAchievementsPlayServices: " + key);
        if (achievementsPlayServices.containsKey(key)) {
            return achievementsPlayServices.get(key);
        }
        return null;
    }

}