package com.derekentringer.gizmo.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.derekentringer.gizmo.IGameService;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.util.log.GLog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

public class AndroidLauncher extends AndroidApplication implements IGameService, GoogleApiClient.ConnectionCallbacks {

    private static final String TAG = AndroidLauncher.class.getSimpleName();

    private GoogleApiClient mGoogleApiClient;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        connectToGoogleApi();
        initialize(new Gizmo(this), config);
	}

    private void connectToGoogleApi() {
        GLog.d(TAG, "connecting to GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult result) {
                        GLog.d(TAG, "GoogleApiClient - onConnectionFailed");
                    }
                })
                .addApi(Wearable.API)
                .build();
        mGoogleApiClient.connect();
    }

    // GoogleApiClient

    @Override
    public void onConnected(Bundle bundle) {
        GLog.d(TAG, "GoogleApiClient - onConnected");
    }

    @Override
    public void onConnectionSuspended(int i) {
        GLog.d(TAG, "GoogleApiClient - onConnectionSuspended");
    }

    // GameService

    @Override
    public void login() {
        GLog.d(TAG, "GameService - login");
    }

    @Override
    public void logOut() {
        GLog.d(TAG, "GameService - logOut");
    }

    @Override
    public boolean isSignedIn() {
        GLog.d(TAG, "GameService - isSignedIn");
        return false;
    }

    @Override
    public void submitScore(int score) {
        GLog.d(TAG, "GameService - submitScore");
    }

    @Override
    public void unlockAchievement(String achievementID) {
        GLog.d(TAG, "GameService - unlockAchievement");
    }

    @Override
    public void showScores() {
        GLog.d(TAG, "GameService - showScores");
    }

    @Override
    public void showAchievements() {
        GLog.d(TAG, "GameService - showAchievements");
    }

}