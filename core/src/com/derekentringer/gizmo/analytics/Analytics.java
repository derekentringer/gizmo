package com.derekentringer.gizmo.analytics;

import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.network.request.InitRequest;
import com.derekentringer.gizmo.network.response.InitResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Analytics {

    private static final String TAG = Analytics.class.getSimpleName();

    public static void initialize(InitRequest initRequest) {
        Gizmo.getRetrofitClient().initialize(AnalyticsSettings.API_GAME_KEY_SANDBOX, initRequest).enqueue(new Callback<InitResponse>() {
            @Override
            public void onResponse(Call<InitResponse> call, Response<InitResponse> response) {
                AnalyticsSettings.setIsAnalyticsAvailable(response.isSuccess());
            }

            @Override
            public void onFailure(Call<InitResponse> call, Throwable t) {
                AnalyticsSettings.setIsAnalyticsAvailable(false);
            }
        });
    }

}