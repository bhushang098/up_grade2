package com.example.upgrade;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.upgrade.PdfModel.PdfAbapter;
import com.example.upgrade.PdfModel.pdfList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ListAsPerYear extends AppCompatActivity implements SearchView.OnQueryTextListener {

    FirebaseFirestore db;
    RecyclerView recyclerView;
    ArrayList<pdfList> arrayList = new ArrayList<>();
    PdfAbapter myAdapter;
    String year;
    ProgressBar progressBar;
    Toolbar toolbar;
    Spinner spinner;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_as_per_year);

        setUi();
        setRecview();
        progressBar.setVisibility(View.VISIBLE);
        setFb();
        setSupportActionBar(toolbar);


        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(this,R.array.years,R.layout.color_spinner);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
      spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              year = parent.getItemAtPosition(position).toString();

              switch (year) {
                  case "Year 2019":
                      dataFromFB();
                      break;
                  case "Year 2018":
                      dataFromFB();
                      break;
                  case "Year 2017":
                      dataFromFB();
                      break;
                  case "Year 2016":
                      dataFromFB();
                      break;
                  case "Year 2015":
                      dataFromFB();
              }
          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {

          }
      });
    }

    public void dataFromFB() {

        if (arrayList.size() > 0)
            arrayList.clear();

        db = FirebaseFirestore.getInstance();

        db.collection(year).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot documentSnapshot : Objects.requireNonNull(task.getResult())) {

                            pdfList list = new pdfList(documentSnapshot.getString("Name")
                                    , documentSnapshot.getString("Link"));
                            arrayList.add(list);
                        }
                        myAdapter = new PdfAbapter(ListAsPerYear.this, arrayList, year);
                        recyclerView.setAdapter(myAdapter);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(ListAsPerYear.this, "Error__>>>", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setFb() {
        db = FirebaseFirestore.getInstance();
    }

    public void setRecview() {
        recyclerView = findViewById(R.id.recVofPdfList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setQueryHint("Search your Branch");
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();

        ArrayList<pdfList> newList = new ArrayList<>();
        for (pdfList paper : arrayList)
        {
            String paperName = paper.getName().toLowerCase();
            if (paperName.contains(newText)){
                newList.add(paper);
            }
        }
        myAdapter.setFilter(newList);
        return true;
    }

    private void setUi() {
        toolbar = findViewById(R.id.tbOfListpaper);
        spinner = findViewById(R.id.spnOfTb);
        progressBar = findViewById(R.id.pbOfListAsPerYear);
    }
}
