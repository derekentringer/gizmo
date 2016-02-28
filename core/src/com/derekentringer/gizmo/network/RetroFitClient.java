package com.derekentringer.gizmo.network;

import com.derekentringer.gizmo.analytics.AnalyticsSettings;
import com.derekentringer.gizmo.network.request.InitRequest;
import com.derekentringer.gizmo.network.response.InitResponse;
import com.derekentringer.gizmo.network.util.HMAC;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            Interceptor headerInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .header("Content-Type", "application/json")
                            .header("Authorization", HMAC.hmacDigest(original.body(), AnalyticsSettings.API_SECRET_KEY_SANDBOX))
                            .method(original.method(), original.body())
                            .build();
                    return chain.proceed(request);
                }
            };

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(headerInterceptor)
                    .addInterceptor(loggingInterceptor)
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