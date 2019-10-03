package com.example.upgrade;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.upgrade.JobsModel.Item;
import com.example.upgrade.JobsModel.Jobs;
import com.example.upgrade.api.JobApiinterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JobActivity extends AppCompatActivity {

    private static final String BaseURl = "https://www.googleapis.com/blogger/v3/blogs/183332211181768356/";

    List<Item> JobPosts = new ArrayList<>();
    RecyclerView recyclerView;
    ProgressBar progressBar;
    private SwipeRefreshLayout refreshLayout;
    private TextView erroeTitle;
    private TextView errorMessage;
    private RelativeLayout errorlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
        setUi();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadJob();
            }
        });
        loadJob();
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUi()
    {
        erroeTitle = findViewById(R.id.errorTitle);
        errorMessage = findViewById(R.id.errormessage);
        refreshLayout = findViewById(R.id.reflayoutJob);
        errorlayout = findViewById(R.id.errorlayout);
        progressBar = findViewById(R.id.pbOfJObActivity);
        recyclerView = findViewById(R.id.recViewOfJobActivity);
    }
    private void loadJob()
    {
        errorlayout.setVisibility(View.GONE);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BaseURl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JobApiinterface  jobApiinterface = retrofit.create(JobApiinterface.class);

        Call<Jobs> call = jobApiinterface.getJobPoste();

        call.enqueue(new Callback<Jobs>() {
            @Override
            public void onResponse(Call<Jobs> call, Response<Jobs> response) {
                JobPosts = response.body().getItems();
                progressBar.setVisibility(View.INVISIBLE);
                recyclerView.setAdapter(new AdapterOfJob(JobPosts,JobActivity.this));
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<Jobs> call, Throwable t) {
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
