package com.example.mystripepayment;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

public class Retrofit2Client {

    private static Retrofit2Client instance = null;
    private Retrofit retrofit;
    private OkHttpClient httpClient;

    private MyApiEndpointInterface apiService;

    public Retrofit2Client() {

        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request.Builder ongoing = chain.request().newBuilder();
                ongoing.addHeader("Accept", "application/json;versions=1");
                //if (isUserLoggedIn()) {
                //ongoing.addHeader("Authorization", "Basic RmxuMzZELW5wOFhxU295enFpNjV6X1RLbW9jcWRoYmY6");
                // }
                return chain.proceed(ongoing.build());
            }
        };
        httpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(interceptor)
                .build();


        ObjectMapper jacksonMapper = new ObjectMapper().configure(FAIL_ON_UNKNOWN_PROPERTIES, false);


        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(MyStripePayment.serverURL)
                .addConverterFactory(JacksonConverterFactory.create(jacksonMapper));
        retrofitBuilder.client(httpClient);

        retrofit = retrofitBuilder.build();
        apiService =
                retrofit.create(MyApiEndpointInterface.class);

    }

    public static Retrofit2Client getInstance() {
        if (instance == null) {
            instance = new Retrofit2Client();
        }

        return instance;
    }

    public MyApiEndpointInterface getApiService() {
        return apiService;
    }
}
