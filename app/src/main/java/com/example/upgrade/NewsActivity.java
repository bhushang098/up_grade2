package com.example.upgrade;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.upgrade.api.ApiClient;
import com.example.upgrade.api.ApiInterface;
import com.example.upgrade.models.Article;
import com.example.upgrade.models.News;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity {

    public static final String API_Kay = "1359e8fa588942cd9c4d7b416f85fe0a";

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Article> articles = new ArrayList<>();
    private AdapterOfNews adapter;
    private String TAG = MainActivity.class.getSimpleName();
    private SwipeRefreshLayout refreshLayout;
    private TextView erroeTitle;
    private TextView errorMessage;
    private ProgressBar progressBar;
    private RelativeLayout errorlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        setUI();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadJSON("");
            }
        });
        loadJSON("");
    }

    public void loadJSON(final String keyWord){

        errorlayout.setVisibility(View.GONE);

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        String country = Utils.getCountry();

        Call<News> call;

        if (keyWord.length() >0){

            call = apiInterface.getNewsSearch(keyWord,"en","publishedAt",API_Kay);

        }else {
            call = apiInterface.getNews(country,API_Kay);
        }

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {

                articles = response.body().getArticles();
                recyclerView.setLayoutManager(new LinearLayoutManager(NewsActivity.this));
                recyclerView.setAdapter(new AdapterOfNews(articles,NewsActivity.this));
                refreshLayout.setRefreshing(false);

            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                refreshLayout.setRefreshing(false);
                showError("No Results","Check Internet Connection \n "+t.toString(),progressBar);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.news_menu,menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        MenuItem menuItem = menu.findItem(R.id.action_search);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search news Worldwide");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                refreshLayout.setRefreshing(false);

                if(query.length() > 2){
                    loadJSON(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                refreshLayout.setRefreshing(false);
                loadJSON(newText);
                return false;
            }
        });
        menuItem.getIcon().setVisible(false,false);
        return true;
    }
    private void setUI()
    {
        erroeTitle = findViewById(R.id.errorTitle);
        errorMessage = findViewById(R.id.errormessage);
        refreshLayout = findViewById(R.id.reflayoutNews);
        errorlayout = findViewById(R.id.errorlayout);
        progressBar = findViewById(R.id.pbOfNEwsActivity);
        recyclerView = findViewById(R.id.recViewNewsActivity);
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
