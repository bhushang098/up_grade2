package com.example.upgrade;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.upgrade.BlogModel.Blogs;
import com.example.upgrade.BlogModel.Item;
import com.example.upgrade.api.BlogApiInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BlogActivity extends AppCompatActivity {

    public static final String BASE_URl = "https://www.googleapis.com/blogger/v3/blogs/4459620283922349315/";

    List<Item> blogPosts = new ArrayList<>();
    RecyclerView recyclerView;
    ProgressBar progressBar;
    private SwipeRefreshLayout refreshLayout;
    private TextView erroeTitle;
    private TextView errorMessage;
    private RelativeLayout errorlayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
        setUi();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadBlog();
            }
        });

        loadBlog();
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void setUi()
    {
        erroeTitle = findViewById(R.id.errorTitle);
        errorMessage = findViewById(R.id.errormessage);
        refreshLayout = findViewById(R.id.reflayoutBlog);
        errorlayout = findViewById(R.id.errorlayout);
        progressBar = findViewById(R.id.pbOfGateUpdates);
        recyclerView = findViewById(R.id.recViewOfBlogActivity);
        toolbar = findViewById(R.id.tbOfBlogActivity);
    }

    private void loadBlog()
    {
        errorlayout.setVisibility(View.GONE);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BlogApiInterface apiInterface = retrofit.create(BlogApiInterface.class);

        Call<Blogs> call = apiInterface.getBlogPoste();

        call.enqueue(new Callback<Blogs>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Call<Blogs> call, Response<Blogs> response) {
                blogPosts = Objects.requireNonNull(response.body()).getItems();
                progressBar.setVisibility(View.INVISIBLE);
                recyclerView.setAdapter(new AdapterOfBlog(blogPosts,BlogActivity.this));
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<Blogs> call, Throwable t) {
                refreshLayout.setRefreshing(false);
                showError("No Results","Check Internet Connection \n "+t.toString(),progressBar);

            }
        });

    }

    private void showError(String title ,String message,ProgressBar pogBar)
    {
        pogBar.setVisibility(View.INVISIBLE);
        if (errorlayout.getVisibility() == View.GONE){
            errorlayout.setVisibility(View.VISIBLE);
        }
        errorMessage.setText(message);
        erroeTitle.setText(title);

    }
}
