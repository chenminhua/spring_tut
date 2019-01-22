package com.chenminhua.retrofitfeignexp.retrofitClient;

import retrofit2.Call;

public interface SomeApi {

    public Call<SomethingDTO> getSomething();
}
