package com.wordpress.getaufansepta.newsdev.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wordpress.getaufansepta.newsdev.R;
import com.wordpress.getaufansepta.newsdev.database.ArticleDB;

import java.util.List;

/**
 * Created by Taufan Septaufani on 08-Mar-18.
 */

public class AdapterLineDB extends RecyclerView.Adapter<AdapterLineDB.CategoryViewHolder> {
    private Context context;
    private List<ArticleDB> articleList;

    public void setListNews(List<ArticleDB> list){
        this.articleList = list;
    }

    public AdapterLineDB(Context context){
        this.context = context;
    }

    @Override
    public AdapterLineDB.CategoryViewHolder onCreateViewHolder(ViewGroup parent, int ViewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news,parent,false);
        return new AdapterLineDB.CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterLineDB.CategoryViewHolder holder, int position){
        String img_url = getArticleList().get(position).getUrlToImage();
        if (TextUtils.isEmpty(img_url)){
            Picasso.with(context).load(R.drawable.broken_image).into(holder.imgnews);
        }else{
            Picasso.with(context).load(img_url)
                    .placeholder(R.color.cardview_dark_background)
                    .error(R.drawable.broken_image)
                    .into(holder.imgnews);
        }

        String title = getArticleList().get(position).getTitle();
        holder.descnews.setText(title);
        String source = getArticleList().get(position).getSource_name();
        holder.sourcenewsname.setText(source);
    }

    @Override
    public int getItemCount(){
        return getArticleList().size();
    }

    public  List<ArticleDB> getArticleList(){
        return articleList;
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder{
        ImageView imgnews;
        TextView descnews,sourcenewsname;
        public CategoryViewHolder(View itemView){
            super(itemView);
            imgnews = (ImageView)itemView.findViewById(R.id.imgnews);
            descnews = (TextView)itemView.findViewById(R.id.descnews);
            sourcenewsname = (TextView)itemView.findViewById(R.id.srcname_news);
        }
    }
}
