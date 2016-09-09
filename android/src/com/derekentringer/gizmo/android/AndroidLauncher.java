package com.derekentringer.gizmo.android;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.integrations.play.GooglePlayServices;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;

public class AndroidLauncher extends AndroidApplication implements GooglePlayServices {

    private static final String TAG = AndroidLauncher.class.getSimpleName();

    private AndroidLauncher mAndroidLauncher;
    AndroidApplicationConfiguration mConfig;
    private GameHelper gameHelper;
    private final static int requestCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAndroidLauncher = this;
        mConfig = new AndroidApplicationConfiguration();

        gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
        gameHelper.enableDebugLog(true);

        GameHelper.GameHelperListener gameHelperListener = new GameHelper.GameHelperListener() {
            @Override
            public void onSignInFailed() {
                Gdx.app.log(TAG, "Google Play Services Not Available");
            }

            @Override
            public void onSignInSucceeded() {
                Gdx.app.log(TAG, "Google Play Services Available");
            }
        };
        gameHelper.setup(gameHelperListener);

        initialize(new Gizmo(mAndroidLauncher), mConfig);
    }

    @Override
    protected void onStart() {
        super.onStart();
        gameHelper.onStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        gameHelper.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        gameHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void signIn() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    gameHelper.beginUserInitiatedSignIn();
                }
            });
        }
        catch (Exception e) {
            Gdx.app.log(TAG, "Log in failed: " + e.getMessage() + ".");
        }
    }

    @Override
    public void signOut() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    gameHelper.signOut();
                }
            });
        }
        catch (Exception e) {
            Gdx.app.log(TAG, "Log out failed: " + e.getMessage() + ".");
        }
    }

    @Override
    public void rateGame() {
        /*String str = "play_store_link";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));*/
    }

    @Override
    public void unlockAchievement(String achievement) {
        Gdx.app.log(TAG, "ACHIEVEMENT UNLOCKED! " + achievement);
        Games.Achievements.unlock(gameHelper.getApiClient(), achievement);
    }

    @Override
    public void submitScore(int highScore) {
        /*if (isSignedIn() == true) {
            Games.Leaderboards.submitScore(gameHelper.getApiClient(), getString(R.string.leaderboard_highest), highScore);
        }*/
    }

    @Override
    public void showAchievement() {
        if (isSignedIn()) {
            startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), requestCode);
        }
        else {
            signIn();
        }
    }

    @Override
    public void showScore() {
        /*if (isSignedIn()) {
            startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(),
                    getString(R.string.achievement_dags)), requestCode);
        }
        else {
            signIn();
        }*/
    }

    @Override
    public boolean isSignedIn() {
        return gameHelper.isSignedIn();
    }

}