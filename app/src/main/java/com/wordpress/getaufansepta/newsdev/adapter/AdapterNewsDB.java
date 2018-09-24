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

public class AdapterNewsDB extends RecyclerView.Adapter<AdapterNewsDB.CategoryViewHolder> {
    private Context context;
    private List<ArticleDB> articleList;

    public void setListNews(List<ArticleDB> list){
        this.articleList = list;
    }

    public AdapterNewsDB(Context context){
        this.context = context;
    }

    @Override
    public AdapterNewsDB.CategoryViewHolder onCreateViewHolder(ViewGroup parent, int ViewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.headline,parent,false);
        return new AdapterNewsDB.CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterNewsDB.CategoryViewHolder holder, int position){
        String img_url = getArticleList().get(position).getUrlToImage();
        if (TextUtils.isEmpty(img_url)){
            Picasso.with(context).load(R.drawable.broken_image).into(holder.imgheadline);
        }else{
            Picasso.with(context).load(img_url)
                    .placeholder(R.color.cardview_dark_background)
                    .error(R.drawable.broken_image)
                    .into(holder.imgheadline);
        }

        String title = getArticleList().get(position).getTitle();
        holder.descheadline.setText(title);
        String source = getArticleList().get(position).getSource_name();
        holder.sourceheadlinename.setText(source);

    }

    @Override
    public int getItemCount(){
        return getArticleList().size();
    }

    public  List<ArticleDB> getArticleList(){
        return articleList;
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder{
        ImageView imgheadline;
        TextView descheadline,sourceheadlinename;
        public CategoryViewHolder(View itemView){
            super(itemView);
            imgheadline = (ImageView)itemView.findViewById(R.id.imgheadline);
            descheadline = (TextView)itemView.findViewById(R.id.descheadline);
            sourceheadlinename = (TextView)itemView.findViewById(R.id.srcname_headline);
        }
    }
}
