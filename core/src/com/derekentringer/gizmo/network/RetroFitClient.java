package com.derekentringer.gizmo.network;

import com.derekentringer.gizmo.analytics.AnalyticsSettings;
import com.derekentringer.gizmo.network.interceptor.*;
import com.derekentringer.gizmo.network.request.InitRequest;
import com.derekentringer.gizmo.network.response.InitResponse;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetroFitClient {

    @POST("v2/{game_key}/init")
    Call<InitResponse> initialize(@Path("game_key") String gameKey, @Body InitRequest initRequest);

    class Factory {
        public static RetroFitClient create() {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new HeaderInterceptor())
                    .addInterceptor(new GzipRequestInterceptor())
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AnalyticsSettings.ENDPOINT_SANDBOX)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
            return retrofit.create(RetroFitClient.class);
        }
    }

}