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
import com.wordpress.getaufansepta.newsdev.model.Article;

import java.util.List;

/**
 * Created by Taufan Septaufani on 05-Mar-18.
 */

public class AdapterNews extends RecyclerView.Adapter<AdapterNews.CategoryViewHolder> {
    private Context context;
    private List<Article> articleList;

    public void setListNews(List<Article> list){
        this.articleList = list;
    }

    public AdapterNews(Context context){
        this.context = context;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int ViewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.headline,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position){
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
        String source = getArticleList().get(position).getSource().getName();
        holder.sourceheadlinename.setText(source);

    }

    @Override
    public int getItemCount(){
        return getArticleList().size();
    }

    public  List<Article> getArticleList(){
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
