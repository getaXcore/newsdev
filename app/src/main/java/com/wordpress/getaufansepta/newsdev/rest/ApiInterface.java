package com.wordpress.getaufansepta.newsdev.rest;

import com.wordpress.getaufansepta.newsdev.model.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Taufan Septaufani on 01-Mar-18.
 */

public interface ApiInterface {
    @GET("top-headlines")
    Call<News> getArticles(@Query("country") String country,
                           @Query("category") String category,
                           @Query("apiKey") String apikey
                           );
}
