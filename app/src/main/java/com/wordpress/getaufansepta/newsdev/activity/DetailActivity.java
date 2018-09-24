package com.wordpress.getaufansepta.newsdev.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.share.widget.ShareButton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.squareup.picasso.Picasso;
import com.wordpress.getaufansepta.newsdev.R;
import com.wordpress.getaufansepta.newsdev.database.ArticleDB;
import com.wordpress.getaufansepta.newsdev.database.ArticleHelper;

public class DetailActivity extends AppCompatActivity {
    TextView title,author,sourcename,description,btnSource;
    ImageView imgSingle;
    //ShareButton btnShare;
    AdView adView;
    ArticleHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        init();//identify id all element from layout
        //get title form main activity where the news is clicked
        String titleKey = getIntent().getStringExtra("title");

        //init new db helper
        db = new ArticleHelper(DetailActivity.this);

        //get news by title
        ArticleDB article = db.getArticleDetailByIdorTitle(titleKey);

        //set content
        title.setText(article.getTitle());
        author.setText(article.getAuthor());
        sourcename.setText(article.getSource_name());
        description.setText(article.getDesc());
        Picasso.with(this).load(article.getUrlToImage()).error(R.drawable.broken_image).into(imgSingle);

        //show add
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

    }

    private void init() {
        title = (TextView)findViewById(R.id.txtTitle);
        author = (TextView)findViewById(R.id.txtAuthor);
        sourcename = (TextView)findViewById(R.id.txtSourcename);
        description = (TextView)findViewById(R.id.txtDesc);
        btnSource = (TextView)findViewById(R.id.btnSource);
        imgSingle = (ImageView)findViewById(R.id.imgSingle);
        //btnShare = (ShareButton)findViewById(R.id.btnShare);
        adView = (AdView)findViewById(R.id.adView1);
    }
}

