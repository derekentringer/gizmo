package com.derekentringer.gizmo.integrations.play;

public interface GooglePlayServices {
    void signIn();
    void signOut();
    void rateGame();
    void unlockAchievement(String achievement);
    void submitScore(int highScore);
    void showAchievement();
    void showScore();
    boolean isSignedIn();
}