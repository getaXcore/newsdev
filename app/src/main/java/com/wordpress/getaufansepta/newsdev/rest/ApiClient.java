package com.wordpress.getaufansepta.newsdev.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Taufan Septaufani on 01-Mar-18.
 */

public class ApiClient {
    public static String BASE_URL = "https://newsapi.org/v2/";
    public static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
