package com.wordpress.getaufansepta.newsdev.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.wordpress.getaufansepta.newsdev.R;
import com.wordpress.getaufansepta.newsdev.database.ArticleDB;
import com.wordpress.getaufansepta.newsdev.database.ArticleHelper;
import com.wordpress.getaufansepta.newsdev.model.Article;
import com.wordpress.getaufansepta.newsdev.model.News;
import com.wordpress.getaufansepta.newsdev.rest.ApiClient;
import com.wordpress.getaufansepta.newsdev.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends AppCompatActivity {
    ArticleHelper db;
    final static String api_key = "9d03765f66364f68ba71caa77488fe2b";
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

        db = new ArticleHelper(SplashScreen.this);

        //get existing data count from database
        count = db.getArticlesCount();

        //get data and save to database
        getHeadlines("general");
        getHeadlines("business");
        getHeadlines("science");
        getHeadlines("sports");
        getHeadlines("technology");
        getHeadlines("health");
        getHeadlines("entertainment");

    }

    private void getHeadlines(final String category){

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        final Call<News> newsCall = apiInterface.getArticles("id",category,api_key);

        newsCall.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {

                try {
                    final List<Article> list = response.body().getArticles();

                    //truncate table before add new data
                    db.deleteAllArticlesByCategory(category);
                    //Log.i("category", category);

                    for (int i = 0;i < list.size();i++){

                        //add
                        db.addArticle(new ArticleDB(
                                list.get(i).getSource().getName(),
                                list.get(i).getAuthor(),
                                list.get(i).getTitle(),
                                list.get(i).getDescription(),
                                list.get(i).getUrl(),
                                list.get(i).getUrlToImage(),
                                list.get(i).getPublishedAt(),
                                category
                        ));

                    }

                    //start main activity
                    startActivity(new Intent(SplashScreen.this,MainActivity.class));
                    finish();

                }catch (NullPointerException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                //Log.i("count",String.valueOf(count));

                //if there is no existing data in database
                if (count <= 0){
                    //show notification
                    Toast.makeText(SplashScreen.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
                    Log.i("onFailure",t.getMessage());
                }else {
                    //start main activity
                    startActivity(new Intent(SplashScreen.this,MainActivity.class));
                    finish();
                }
            }
        });

    }
}
