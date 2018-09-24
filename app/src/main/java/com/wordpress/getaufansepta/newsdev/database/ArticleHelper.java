package com.wordpress.getaufansepta.newsdev.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.wordpress.getaufansepta.newsdev.model.Article;
import com.wordpress.getaufansepta.newsdev.model.Source;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taufan Septaufani on 08-Mar-18.
 */

public class ArticleHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "NewsManager";
    private static final String TABLE_NAME = "article";

    private static final int KEY_ID = 0;
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESC = "desc";
    private static final String KEY_POSTER_URL = "poster";
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_SOURCE_NAME = "source_name";
    private static final String KEY_SOURCE_URL = "source_url";
    private static final String KEY_PUBLISH_AT = "publish_at";
    private static final String KEY_CATEGORY = "category";

    public ArticleHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+ "("
                +KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                +KEY_TITLE+" TEXT,"
                +KEY_DESC+" TEXT,"
                +KEY_POSTER_URL+" TEXT,"
                +KEY_AUTHOR+" TEXT,"
                +KEY_SOURCE_NAME+" TEXT,"
                +KEY_SOURCE_URL+" TEXT,"
                +KEY_PUBLISH_AT+" TEXT,"
                +KEY_CATEGORY+" TEXT"
                +")";
        //create table
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);

        //recreate table
        onCreate(db);
    }

    //add news article
    public void addArticle(ArticleDB articleDB){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE,articleDB.getTitle());
        values.put(KEY_DESC,articleDB.getDesc());
        values.put(KEY_POSTER_URL,articleDB.getUrlToImage());
        values.put(KEY_AUTHOR,articleDB.getAuthor());
        values.put(KEY_SOURCE_NAME,articleDB.getSource_name());
        values.put(KEY_SOURCE_URL,articleDB.getUrl());
        values.put(KEY_PUBLISH_AT,articleDB.getPublishAt());
        values.put(KEY_CATEGORY,articleDB.getCategory());

        //insert data
        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    public List<ArticleDB> getAllArticles(String category){
        SQLiteDatabase db = this.getReadableDatabase();
        List<ArticleDB> articleList = new ArrayList<ArticleDB>();

        //Cursor cursor = db.query(TABLE_NAME,new String[]{KEY_TITLE,KEY_POSTER_URL},
        //KEY_CATEGORY+" =? ",new String[]{String.valueOf(category)},null,null,null,null);
        String query = "SELECT "+KEY_ID+","+KEY_TITLE+","+KEY_DESC+","+KEY_POSTER_URL+","+KEY_AUTHOR+","+KEY_SOURCE_NAME+","+KEY_SOURCE_URL+","+KEY_PUBLISH_AT+","+KEY_CATEGORY+" FROM "+TABLE_NAME+" WHERE "+KEY_CATEGORY+"='"+category+"'";
        Cursor cursor = db.rawQuery(query,null);

        Log.e("count cursor",String.valueOf(cursor.getCount()));

        //loop data from DB and add to ArticleDB
        if (cursor.moveToFirst()){
            do {
                ArticleDB articleDB = new ArticleDB();
                articleDB.setId(cursor.getInt(0));
                articleDB.setTitle(cursor.getString(1));
                articleDB.setDesc(cursor.getString(2));
                articleDB.setUrlToImage(cursor.getString(3));
                articleDB.setAuthor(cursor.getString(4));
                articleDB.setSource_name(cursor.getString(5));
                articleDB.setUrl(cursor.getString(6));
                articleDB.setPublishAt(cursor.getString(7));
                articleDB.setCategory(cursor.getString(8));

                //add to new array of list ArticleDB
                articleList.add(articleDB);
            }while (cursor.moveToNext());
        }

        return articleList;
    }

    public ArticleDB getArticleDetailByIdorTitle(String id_or_title){
        SQLiteDatabase db = this.getReadableDatabase();

        //Cursor cursor = db.query(TABLE_NAME,new String[]{KEY_SOURCE_NAME,KEY_AUTHOR,KEY_TITLE,KEY_DESC,KEY_SOURCE_URL,KEY_POSTER_URL,KEY_PUBLISH_AT},KEY_ID+" =? OR "+KEY_TITLE+" =? ",new String[]{String.valueOf(id_or_title)},null,null,null,null);
        Cursor cursor = db.query(TABLE_NAME,new String[]{KEY_SOURCE_NAME,KEY_AUTHOR,KEY_TITLE,KEY_DESC,KEY_SOURCE_URL,KEY_POSTER_URL,KEY_PUBLISH_AT},KEY_TITLE+" =? ",new String[]{id_or_title},null,null,null,null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        ArticleDB articleDB = new ArticleDB(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));

        return articleDB;
    }

    public void deleteAllArticlesByCategory(String category){
        SQLiteDatabase db = this.getWritableDatabase();

        String DELETE_ALL_BY_CATEGORY = "DELETE FROM "+TABLE_NAME+" WHERE "+KEY_CATEGORY+"= '"+category+"'";
        db.execSQL(DELETE_ALL_BY_CATEGORY);
    }

    public int getArticlesCount(){
        SQLiteDatabase db = this.getReadableDatabase();

        String QUERY_GET_COUNT = "SELECT COUNT(*) FROM "+TABLE_NAME;
        Cursor cursor = db.rawQuery(QUERY_GET_COUNT,null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        return count;
    }
}
