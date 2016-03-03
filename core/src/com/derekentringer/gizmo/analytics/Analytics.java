package com.derekentringer.gizmo.analytics;

import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.analytics.model.AnalyticsSettings;
import com.derekentringer.gizmo.analytics.model.EventRequestDictionary;
import com.derekentringer.gizmo.analytics.request.EventRequest;
import com.derekentringer.gizmo.analytics.request.InitRequest;
import com.derekentringer.gizmo.analytics.response.InitResponse;
import com.derekentringer.gizmo.analytics.util.AnalyticsUtils;
import com.derekentringer.gizmo.network.util.HMAC;
import com.derekentringer.gizmo.util.log.GLog;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Analytics {

    private static final String TAG = Analytics.class.getSimpleName();

    public static void initialize() {
        InitRequest initRequest = new InitRequest(AnalyticsUtils.getPlatform(),
                AnalyticsUtils.getOsVersion(),
                AnalyticsSettings.REST_API_VERSION);

        Gizmo.getRetrofitClient().initialize(HMAC.hmacWithKey(AnalyticsSettings.API_SECRET_KEY_DEV, initRequest.toString().getBytes()),
                AnalyticsSettings.API_GAME_KEY_DEV,
                initRequest)
                .enqueue(new Callback<InitResponse>() {
                    @Override
                    public void onResponse(Call<InitResponse> call, Response<InitResponse> response) {
                        if (response.isSuccess()) {
                            AnalyticsSettings.setIsAnalyticsAvailable(response.body().isEnabled());
                            EventRequestDictionary.buildDefaultParameters();
                            ArrayList<EventRequest> eventRequests = new ArrayList<EventRequest>();
                            eventRequests.add(EventRequestDictionary.getDefaultParameters());
                            Analytics.sendEvent(eventRequests);
                        }
                        else {
                            AnalyticsSettings.setIsAnalyticsAvailable(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<InitResponse> call, Throwable t) {
                        AnalyticsSettings.setIsAnalyticsAvailable(false);
                    }
                });
    }

    public static void sendEvent(ArrayList<EventRequest> eventRequest) {
        Gizmo.getRetrofitClient().sendEvent(HMAC.hmacWithKey(AnalyticsSettings.API_SECRET_KEY_DEV, eventRequest.toString().getBytes()),
                AnalyticsSettings.API_GAME_KEY_DEV,
                eventRequest)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        GLog.d(TAG, "sendEvent:onResponse:" + response.isSuccess());
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        GLog.d(TAG, "sendEvent:onFailure");
                    }
                });
    }

}