package com.chenminhua.retrofitfeignexp.retrofitClient;

import okhttp3.OkHttpClient;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Component
public class RetrofitClient {

    private OkHttpClient generateClient() {
        return new OkHttpClient.Builder().build();
    }

    public SomeApi generateSomeApi() {
        return new Retrofit.Builder()
                .baseUrl("http://localhost:8082/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(generateClient())
                .build().create(SomeApi.class);
    }

}
