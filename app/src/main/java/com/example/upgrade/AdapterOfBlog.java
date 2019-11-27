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
import com.example.upgrade.BlogModel.Item;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;

public class AdapterOfBlog extends RecyclerView.Adapter<AdapterOfBlog.BlogViewHolder> {

    //List Of Items
    private List<Item> items;
    private Context context;

    public AdapterOfBlog(List<Item> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.blog_items,parent,false);

        return new BlogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BlogViewHolder holder, final int position) {

        holder.autherOfBlog.setText(items.get(position).getAuthor().getDisplayName());
        holder.titleOfBlog.setText(items.get(position).getTitle());
        holder.blogdatepublishedAt.setText(items.get(position).getPublished());
        holder.blogdatepublishedAt.setText(Utils.DateFormat(items.get(position).getPublished()));
       Document document = Jsoup.parse(items.get(position).getContent());
        Elements elements = document.select("img");
        Glide.with(context).load(elements.attr("src")).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                holder.pogBarLoadIOmage.setVisibility(View.INVISIBLE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.pogBarLoadIOmage.setVisibility(View.INVISIBLE);
                return false;
            }
        }).into(holder.imgOfBlogItem);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,BlogDetails.class);
                i.putExtra("url",items.get(position).getUrl());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class BlogViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imgOfBlogItem;
        TextView autherOfBlog,titleOfBlog,blogdatepublishedAt;
        ProgressBar pogBarLoadIOmage;
        public BlogViewHolder(@NonNull View itemView) {
            super(itemView);
            imgOfBlogItem = itemView.findViewById(R.id.imgOfBlogitem);
            autherOfBlog = itemView.findViewById(R.id.authorOfBlog);
            titleOfBlog = itemView.findViewById(R.id.titleOfBlogItem);
            blogdatepublishedAt = itemView.findViewById(R.id.publishedAtOfBlogItem);
            pogBarLoadIOmage = itemView.findViewById(R.id.progress_load_photoBlogitem);

        }
    }
}
