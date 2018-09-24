package com.wordpress.getaufansepta.newsdev.database;

/**
 * Created by Taufan Septaufani on 08-Mar-18.
 */

public class ArticleDB {
    int id;
    String source_name;
    String author;
    String title;
    String desc;
    String url;
    String urlToImage;
    String publishAt;
    String category;

    public ArticleDB(){

    }

    public ArticleDB(String source_name,String author,String title,String desc,String source_url,String urlToImage,String publishAt,String category){
        this.source_name = source_name;
        this.author = author;
        this.title = title;
        this.desc = desc;
        this.url = source_url;
        this.urlToImage = urlToImage;
        this.publishAt = publishAt;
        this.category = category;
    }

    public ArticleDB(String source_name,String author,String title,String desc,String source_url,String urlToImage,String publishAt){
        this.source_name = source_name;
        this.author = author;
        this.title = title;
        this.desc = desc;
        this.url = source_url;
        this.urlToImage = urlToImage;
        this.publishAt = publishAt;
    }

    public ArticleDB(String title,String urlToImage){
        this.title = title;
        this.urlToImage = urlToImage;
    }

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setSource_name(String source_name){
        this.source_name = source_name;
    }
    public String getSource_name(){
        return this.source_name;
    }
    public void setAuthor(String author){
        this.author = author;
    }
    public String getAuthor(){
        return this.author;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setDesc(String desc){
        this.desc = desc;
    }
    public String getDesc(){
        return this.desc;
    }
    public void setUrl(String source_url){
        this.url = source_url;
    }
    public String getUrl(){
        return this.url;
    }
    public void setUrlToImage(String urlToImage){
        this.urlToImage = urlToImage;
    }
    public String getUrlToImage(){
        return this.urlToImage;
    }
    public void setPublishAt(String publishAt){
        this.publishAt = publishAt;
    }
    public String getPublishAt(){
        return this.publishAt;
    }
    public void setCategory(String category){
        this.category = category;
    }
    public String getCategory(){
        return this.category;
    }

}
