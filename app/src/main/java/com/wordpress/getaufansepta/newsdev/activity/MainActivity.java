package com.wordpress.getaufansepta.newsdev.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
//import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.wordpress.getaufansepta.newsdev.R;
import com.wordpress.getaufansepta.newsdev.adapter.AdapterLine;
import com.wordpress.getaufansepta.newsdev.adapter.AdapterLineDB;
import com.wordpress.getaufansepta.newsdev.adapter.AdapterNews;
import com.wordpress.getaufansepta.newsdev.adapter.AdapterNewsDB;
import com.wordpress.getaufansepta.newsdev.database.ArticleDB;
import com.wordpress.getaufansepta.newsdev.database.ArticleHelper;
/*import com.wordpress.getaufansepta.newsdev.fragment.Cat1Fragment;
import com.wordpress.getaufansepta.newsdev.fragment.Cat2Fragment;
import com.wordpress.getaufansepta.newsdev.fragment.Cat3Fragment;
import com.wordpress.getaufansepta.newsdev.fragment.Cat4Fragment;
import com.wordpress.getaufansepta.newsdev.fragment.Cat5Fragment;
import com.wordpress.getaufansepta.newsdev.fragment.Cat6Fragment;*/
import com.wordpress.getaufansepta.newsdev.helper.ItemClickView;
import com.wordpress.getaufansepta.newsdev.model.Article;
import com.wordpress.getaufansepta.newsdev.model.News;
import com.wordpress.getaufansepta.newsdev.rest.ApiClient;
import com.wordpress.getaufansepta.newsdev.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    TextView menuBisnis,menuSains,menuOlahraga,menuTekno,menuKesehatan,menuHiburan;
    RecyclerView headlinelist,newslist;
    AdView adView1;
    SwipeRefreshLayout pullRefresh;
    ArticleHelper articleHelper;
    String api_key = "9d03765f66364f68ba71caa77488fe2b";
    String init_menu_home = "general";
    String init_menu_bisnis = "business";
    String init_menu_sains = "science";
    String init_menu_olahraga = "sports";
    String init_menu_tekno = "technology";
    String init_menu_kesehatan = "health";
    String init_menu_hiburan = "entertainment";
    String title_default_appName = "Berkabar";
    String title_appName_bisnis = "Bisnis";
    String title_appName_sains = "Sains";
    String title_appName_olahraga = "Olahraga";
    String title_appName_tekno = "Tekno";
    String title_appName_kesehatan = "Kesehatan";
    String title_appName_hiburan = "Hiburan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init(); //identify id menu from layout
        actionClickMenu(); //add action for each menu
        getHeadlines(init_menu_home);
        actionPullRefresh(); //add action for pull refresh for each menu

        //show add
        AdRequest adRequest = new AdRequest.Builder().build();
        adView1.loadAd(adRequest);
    }

    private void actionPullRefresh() {
        //Refresh data if pull down
        pullRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //get current menu name
                SharedPreferences catkey = getSharedPreferences("categoryName",0);
                String categoryName = catkey.getString("category","");
                Log.i("namakategori",categoryName);

                if (categoryName == init_menu_bisnis){
                    getHeadlines(init_menu_bisnis);
                }else if (categoryName == init_menu_sains){
                    getHeadlines(init_menu_sains);
                }else if (categoryName == init_menu_olahraga){
                    getHeadlines(init_menu_olahraga);
                }else if (categoryName == init_menu_tekno){
                    getHeadlines(init_menu_tekno);
                }else if (categoryName == init_menu_kesehatan){
                    getHeadlines(init_menu_kesehatan);
                }else if (categoryName == init_menu_hiburan){
                    getHeadlines(init_menu_hiburan);
                }else if (categoryName == init_menu_home){
                    getHeadlines(init_menu_home);
                }
            }
        });
    }

    private void getHeadlinesFromDB(String category) {
        //get all articles
        final List<ArticleDB> list = articleHelper.getAllArticles(category);

        ArrayList<ArticleDB> arrayList = (ArrayList<ArticleDB>) list;
        ArrayList<ArticleDB> arrayListTop5 = new ArrayList<ArticleDB>(); //create new array for top 5 articles
        ArrayList<ArticleDB> arrayListOthers = new ArrayList<ArticleDB>(); //create new array for other articles

        try {
            for (int i=0;i < 5;i++){
                arrayListTop5.add(arrayList.get(i)); //add in to arrayListTop5
            }

            for (int x=5;x < arrayList.size();x++){
                arrayListOthers.add(arrayList.get(x)); //add in to arrayListOthers
            }
        }catch (IndexOutOfBoundsException e){
            Log.e("ExceptionArrayList",e.getMessage() );
            Toast.makeText(MainActivity.this,"Maaf ada kesalahan",Toast.LENGTH_SHORT).show();
        }

        //get Article into List
        final List<ArticleDB> articleList = arrayListTop5;
        final List<ArticleDB> articleOther = arrayListOthers; //get other artical into other List

        AdapterNewsDB adapterNewsDB = new AdapterNewsDB(MainActivity.this);
        adapterNewsDB.setListNews(articleList);//add to list news adapter

        AdapterLineDB adapterLineDB = new AdapterLineDB(MainActivity.this);
        adapterLineDB.setListNews(articleOther);//add to list news adapter

        //add headline to recyclerView
        headlinelist.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
        headlinelist.setAdapter(adapterNewsDB);

        //add newsline to recyclerView
        newslist.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
        newslist.setAdapter(adapterLineDB);

        ItemClickView.addTo(headlinelist).setOnItemClickListener(new ItemClickView.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView recyclerView, int position, View v) {
                //Toast.makeText(MainActivity.this,articleList.get(position).getSource_name(),Toast.LENGTH_SHORT).show();

                //set intent
                Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                //put param data to intent
                intent.putExtra("title",articleList.get(position).getTitle());
                //start detail activity
                startActivity(intent);
            }
        });

        ItemClickView.addTo(newslist).setOnItemClickListener(new ItemClickView.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView recyclerView, int position, View v) {
                //Toast.makeText(MainActivity.this,articleOther.get(position).getSource_name(),Toast.LENGTH_SHORT).show();

                //set intent
                Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                //put param data to intent
                intent.putExtra("title",articleOther.get(position).getTitle());
                //start detail activity
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.homebutton:
                //change title bar
                getSupportActionBar().setTitle(title_default_appName);

                //get data headline news
                getHeadlines(init_menu_home);

                //put menu name
                SharedPreferences catkey = getSharedPreferences("categoryName",0);
                SharedPreferences.Editor editor = catkey.edit();
                editor.putString("category",init_menu_home);
                editor.apply();
                break;
            default:
                break;
        }

        return true;
    }

    private void getHeadlines(final String category) {
        //show progress dialog while getting data
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Memuat...");
        progressDialog.show();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        final Call<News> newsCall = apiInterface.getArticles("id",category,api_key);

        newsCall.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                try{
                    //don't show progress dialog
                    progressDialog.dismiss();

                    //don't show pull referesh data animation               ,
                    pullRefresh.setRefreshing(false);

                    //get all articles
                    ArrayList<Article> arrayList = (ArrayList<Article>) response.body().getArticles();
                    ArrayList<Article> arrayListTop5 = new ArrayList<Article>(); //create new array for top 5 articles
                    final ArrayList<Article> arrayListOthers = new ArrayList<Article>(); //create new array for other articles
                    try {
                        for (int i=0;i < 5;i++){
                            arrayListTop5.add(arrayList.get(i)); //add in to arrayListTop5
                        }

                        for (int x=5;x < arrayList.size();x++){
                            arrayListOthers.add(arrayList.get(x)); //add in to arrayListOthers
                        }
                    }catch (IndexOutOfBoundsException e){
                        Log.e("ExceptionArrayList",e.getMessage() );
                        Toast.makeText(MainActivity.this,"Maaf ada kesalahan",Toast.LENGTH_SHORT).show();
                    }


                    //get Article into List
                    //final List<Article> articleList = response.body().getArticles();
                    final List<Article> articleList = arrayListTop5;
                    final List<Article> articleOther = arrayListOthers; //get other artical into other List

                    AdapterNews adapterNews = new AdapterNews(MainActivity.this);
                    adapterNews.setListNews(articleList);//add to list news adapter

                    AdapterLine adapterLine = new AdapterLine(MainActivity.this);
                    adapterLine.setListNews(articleOther);//add to line news adapter

                    //add headline to recyclerView
                    headlinelist.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
                    headlinelist.setAdapter(adapterNews);

                    //add newsline to recyclerView
                    newslist.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
                    newslist.setAdapter(adapterLine);

                    ItemClickView.addTo(headlinelist).setOnItemClickListener(new ItemClickView.OnItemClickListener() {
                        @Override
                        public void onItemClick(RecyclerView recyclerView, int position, View v) {
                            //Toast.makeText(MainActivity.this,articleList.get(position).getSource().getName(),Toast.LENGTH_SHORT).show();

                            //set intent
                            Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                            //put param data to intent
                            intent.putExtra("title",articleList.get(position).getTitle());
                            //start detail activity
                            startActivity(intent);
                        }
                    });

                    ItemClickView.addTo(newslist).setOnItemClickListener(new ItemClickView.OnItemClickListener() {
                        @Override
                        public void onItemClick(RecyclerView recyclerView, int position, View v) {
                            //Toast.makeText(MainActivity.this,articleOther.get(position).getSource().getName(),Toast.LENGTH_SHORT).show();

                            //set intent
                            Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                            //put param data to intent
                            intent.putExtra("title",articleOther.get(position).getTitle());
                            //start detail activity
                            startActivity(intent);
                        }
                    });

                }catch (NullPointerException e){
                    Log.e("result","onResponse",e);
                }

            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Log.e("error",t.getMessage() );

                //don't show progress dialog
                progressDialog.dismiss();

                //notify failed to get data from server
                Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();

                //get data from database
                getHeadlinesFromDB(category);//get headline news from database

                //don't show pull referesh data animation
                pullRefresh.setRefreshing(false);
            }
        });
    }

    private void init() {
        menuBisnis = (TextView)findViewById(R.id.cat1);
        menuSains = (TextView)findViewById(R.id.cat2);
        menuOlahraga = (TextView)findViewById(R.id.cat3);
        menuTekno = (TextView)findViewById(R.id.cat4);
        menuKesehatan = (TextView)findViewById(R.id.cat5);
        menuHiburan = (TextView)findViewById(R.id.cat6);
        headlinelist = (RecyclerView)findViewById(R.id.headlinelist);
        newslist = (RecyclerView)findViewById(R.id.newslist);
        adView1 = (AdView)findViewById(R.id.adView1);
        pullRefresh = (SwipeRefreshLayout)findViewById(R.id.pullRefresh);

        articleHelper = new ArticleHelper(MainActivity.this);
    }

    private void actionClickMenu() {
        //Bisnis Category
        menuBisnis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.contentFragment,new Cat1Fragment())
                        .addToBackStack("fragment")
                        .commit();*/

                //change tiitle bar
                getSupportActionBar().setTitle(title_appName_bisnis);

                //get data headline news
                getHeadlines(init_menu_bisnis);

                //put menu name
                SharedPreferences catkey = getSharedPreferences("categoryName",0);
                SharedPreferences.Editor editor = catkey.edit();
                editor.putString("category",init_menu_bisnis);
                editor.apply();

            }
        });

        //Sains Category
        menuSains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change title bar
                getSupportActionBar().setTitle(title_appName_sains);

                //get data headline news
                getHeadlines(init_menu_sains);

                //put menu name
                SharedPreferences catkey = getSharedPreferences("categoryName",0);
                SharedPreferences.Editor editor = catkey.edit();
                editor.putString("category",init_menu_sains);
                editor.apply();
            }
        });

        //Olahraga Category
        menuOlahraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change title bar
                getSupportActionBar().setTitle(title_appName_olahraga);

                //get data headline news
                getHeadlines(init_menu_olahraga);

                //put menu name
                SharedPreferences catkey = getSharedPreferences("categoryName",0);
                SharedPreferences.Editor editor = catkey.edit();
                editor.putString("category",init_menu_olahraga);
                editor.apply();
            }
        });

        //Tekno Category
        menuTekno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change title bar
                getSupportActionBar().setTitle(title_appName_tekno);

                //get data headline news
                getHeadlines(init_menu_tekno);

                //put menu name
                SharedPreferences catkey = getSharedPreferences("categoryName",0);
                SharedPreferences.Editor editor = catkey.edit();
                editor.putString("category",init_menu_tekno);
                editor.apply();
            }
        });

        //Kesehatan Category
        menuKesehatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change title bar
                getSupportActionBar().setTitle(title_appName_kesehatan);

                //get data headline news
                getHeadlines(init_menu_kesehatan);

                //put menu name
                SharedPreferences catkey = getSharedPreferences("categoryName",0);
                SharedPreferences.Editor editor = catkey.edit();
                editor.putString("category",init_menu_kesehatan);
                editor.apply();
            }
        });

        //Hiburan Category
        menuHiburan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change title bar
                getSupportActionBar().setTitle(title_appName_hiburan);

                //get data headline news
                getHeadlines(init_menu_hiburan);

                //put menu name
                SharedPreferences catkey = getSharedPreferences("categoryName",0);
                SharedPreferences.Editor editor = catkey.edit();
                editor.putString("category",init_menu_hiburan);
                editor.apply();
            }
        });
    }


}
