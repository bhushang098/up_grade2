package my.upgrade007.upgrade;

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
import my.upgrade007.upgrade.JobsModel.Item;

import com.example.upgrade.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;

public class AdapterOfJob extends RecyclerView.Adapter<AdapterOfJob.MyJobViewHolder> {

    private List<Item> items;
    private Context context;
    private ManuString manuString = new ManuString();

    public AdapterOfJob(List<Item> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public MyJobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.blog_items,parent,false);
        return new MyJobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyJobViewHolder holder, final int position) {

        holder.autherOfJob.setText(items.get(position).getAuthor().getDisplayName());
        holder.titleOfJob.setText(items.get(position).getTitle());
        holder.JObdatepublishedAt.setText(manuString.getPerfectString(items.get(position).getPublished()));
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
        }).into(holder.imgOfjobItem);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,JObDetails.class);
                i.putExtra("url",items.get(position).getUrl());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyJobViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imgOfjobItem;
        TextView autherOfJob,titleOfJob,JObdatepublishedAt;
        ProgressBar pogBarLoadIOmage;
        public MyJobViewHolder(@NonNull View itemView) {
            super(itemView);

            imgOfjobItem = itemView.findViewById(R.id.imgOfBlogitem);
            autherOfJob = itemView.findViewById(R.id.authorOfBlog);
            titleOfJob = itemView.findViewById(R.id.titleOfBlogItem);
            JObdatepublishedAt = itemView.findViewById(R.id.publishedAtOfBlogItem);
            pogBarLoadIOmage = itemView.findViewById(R.id.progress_load_photoBlogitem);
        }
    }
}
