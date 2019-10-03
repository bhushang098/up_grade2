package com.example.upgrade;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.upgrade.models.Article;

import java.util.List;

public class AdapterOfNews extends RecyclerView.Adapter<AdapterOfNews.MyNewsViewHolder> {

    List<Article> articles;
    private Context context;

    public AdapterOfNews(List<Article> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public MyNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.news_items,parent,false);
        return new MyNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyNewsViewHolder holder, final int position) {

        holder.title.setText(articles.get(position).getTitle());
        holder.author.setText(articles.get(position).getAuthor());
        holder.desc.setText(articles.get(position).getDescription());
        holder.time.setText(Utils.DateFormat(articles.get(position).getPublishedAt()));
        holder.publishedAt.setText(Utils.DateFormat(articles.get(position).getPublishedAt()));
        holder.source.setText(articles.get(position).getSource().getName());

        Glide.with(context).load(articles.get(position).getUrlToImage()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    holder.progressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.GONE);

                return false;
            }
        }).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,NewsDetails.class);
                i.putExtra("url",articles.get(position).getUrl());
                i.putExtra("urlOfImage",articles.get(position).getUrlToImage());
                i.putExtra("date",articles.get(position).getPublishedAt());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    class MyNewsViewHolder extends RecyclerView.ViewHolder {

        TextView title, desc, author, publishedAt, source, time;
        ImageView imageView;
        ProgressBar progressBar;


        public MyNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            author = itemView.findViewById(R.id.author);
            publishedAt = itemView.findViewById(R.id.publishedAt);
            source = itemView.findViewById(R.id.source);
            time = itemView.findViewById(R.id.time);
            imageView = itemView.findViewById(R.id.img);
            progressBar = itemView.findViewById(R.id.progress_load_photo);
        }
    }
}
