package com.derekentringer.gizmo;

public interface IGameService {

    public void login();
    public void logOut();

    public boolean isSignedIn();

    public void submitScore(int score);
    public void unlockAchievement(String achievementID);

    //gets the scores/achievements and displays them threw googles default widget
    public void showScores();
    public void showAchievements();

}