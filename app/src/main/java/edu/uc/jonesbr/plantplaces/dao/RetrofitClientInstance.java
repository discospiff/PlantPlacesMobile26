package edu.uc.jonesbr.plantplaces.dao;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ucint on 6/28/2018.
 */

public class RetrofitClientInstance {

    private  static Retrofit retrofit;
    private static final String BASE_URL = "http://www.plantplaces.com/";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            // create a retrofit instance, only if it has not been created yet.
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
