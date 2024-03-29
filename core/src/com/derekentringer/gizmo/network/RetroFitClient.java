package com.derekentringer.gizmo.network;

import com.derekentringer.gizmo.analytics.model.AnalyticsSettings;
import com.derekentringer.gizmo.analytics.request.EventRequest;
import com.derekentringer.gizmo.analytics.request.SessionStartRequest;
import com.derekentringer.gizmo.analytics.response.InitResponse;
import com.derekentringer.gizmo.network.interceptor.HeaderInterceptor;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetroFitClient {

    @POST("v2/{game_key}/init")
    Call<InitResponse> initialize(@Header("Authorization") String auth,
                                  @Path("game_key") String gameKey,
                                  @Body SessionStartRequest initRequest);

    @POST("v2/{game_key}/events")
    Call<ResponseBody> sendEvent(@Header("Authorization") String auth,
                                 @Path("game_key") String gameKey,
                                 @Body ArrayList<EventRequest> eventRequest);

    class Factory {
        public static RetroFitClient create() {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new HeaderInterceptor())
                    //.addInterceptor(new GzipRequestInterceptor())
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AnalyticsSettings.ENDPOINT_DEV)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
            return retrofit.create(RetroFitClient.class);
        }
    }

}