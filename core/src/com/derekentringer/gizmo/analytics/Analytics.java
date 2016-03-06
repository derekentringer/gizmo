package com.derekentringer.gizmo.analytics;

import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.analytics.model.AnalyticsSettings;
import com.derekentringer.gizmo.analytics.model.EventRequestDictionary;
import com.derekentringer.gizmo.analytics.request.EventRequest;
import com.derekentringer.gizmo.analytics.request.SessionStartRequest;
import com.derekentringer.gizmo.analytics.response.InitResponse;
import com.derekentringer.gizmo.analytics.util.AnalyticsUtils;
import com.derekentringer.gizmo.network.util.HMAC;
import com.derekentringer.gizmo.util.log.GLog;
import com.google.gson.Gson;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Analytics {

    private static final String TAG = Analytics.class.getSimpleName();

    private static Gson gson = new Gson();

    public static void initialize() {
        SessionStartRequest initRequest = new SessionStartRequest(AnalyticsUtils.getPlatform(),
                AnalyticsUtils.getOsVersion(),
                AnalyticsSettings.REST_API_VERSION);

        Gizmo.getRetrofitClient().initialize(HMAC.hmacWithKey(AnalyticsSettings.API_SECRET_KEY_DEV, gson.toJson(initRequest).getBytes()),
                AnalyticsSettings.API_GAME_KEY_DEV,
                initRequest)
                .enqueue(new Callback<InitResponse>() {
                    @Override
                    public void onResponse(Call<InitResponse> call, Response<InitResponse> response) {
                        if (response.isSuccess()) {
                            AnalyticsSettings.setIsAnalyticsAvailable(response.body().isEnabled());
                            AnalyticsSettings.setSessionStartTimestamp(AnalyticsUtils.getTimestamp());
                            Analytics.sendEvent("user", null, 0);
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

    public static void sendEvent(String category, String eventId, int attemptNum) {
        if (AnalyticsSettings.getIsAnalyticsAvailable()) {

            ArrayList<EventRequest> eventRequests = new ArrayList<EventRequest>();
            eventRequests.add(EventRequestDictionary.buildEventRequestParameters(category, eventId, attemptNum));

            Gizmo.getRetrofitClient().sendEvent(HMAC.hmacWithKey(AnalyticsSettings.API_SECRET_KEY_DEV, gson.toJson(eventRequests).getBytes()),
                    AnalyticsSettings.API_GAME_KEY_DEV,
                    eventRequests)
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

}