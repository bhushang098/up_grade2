package my.upgrade007.upgrade;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import my.upgrade007.upgrade.PdfModel.PdfAbapter;
import my.upgrade007.upgrade.PdfModel.pdfList;

import com.example.upgrade.R;
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
    TextView noResultMessage,noResultTitle;
    Toolbar toolbar;
    Spinner spinner;
    NetworkOperation checkNet;
    RelativeLayout errorlayout;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_as_per_year);
        checkNet = new NetworkOperation();
        setUi();
        errorlayout.setVisibility(View.GONE);
        setRecview();
        setFb();
        setSupportActionBar(toolbar);
        progressBar.setVisibility(View.INVISIBLE);


        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(this, R.array.years, R.layout.color_spinner);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year = parent.getItemAtPosition(position).toString();

                switch (year) {
                    case "Year 2019":
                        if (checkNet.checknetConnection(ListAsPerYear.this)) {
                            progressBar.setVisibility(View.VISIBLE);
                            dataFromFB();
                        } else
                            Toast.makeText(ListAsPerYear.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        break;
                    case "Year 2018":
                        if (checkNet.checknetConnection(ListAsPerYear.this)) {
                            progressBar.setVisibility(View.VISIBLE);
                            dataFromFB();
                        } else
                            Toast.makeText(ListAsPerYear.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        break;
                    case "Year 2017":
                        if (checkNet.checknetConnection(ListAsPerYear.this)) {
                            progressBar.setVisibility(View.VISIBLE);
                            dataFromFB();
                        } else
                            Toast.makeText(ListAsPerYear.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        break;
                    case "Year 2016":
                        if (checkNet.checknetConnection(ListAsPerYear.this)) {
                            progressBar.setVisibility(View.VISIBLE);
                            dataFromFB();
                        } else
                            Toast.makeText(ListAsPerYear.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        break;
                    case "Year 2015":
                        if (checkNet.checknetConnection(ListAsPerYear.this)) {
                            progressBar.setVisibility(View.VISIBLE);
                            dataFromFB();
                        } else
                            Toast.makeText(ListAsPerYear.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
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
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot documentSnapshot : Objects.requireNonNull(task.getResult())) {

                            pdfList list = new pdfList(documentSnapshot.getString("Name")
                                    , documentSnapshot.getString("Link"));
                            arrayList.add(list);
                        }

                        Collections.sort(arrayList, new Comparator<pdfList>() {
                            @Override
                            public int compare(pdfList o1, pdfList o2) {
                                return o1.getName().compareTo(o2.getName());
                            }
                        });
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
        for (pdfList paper : arrayList) {
            String paperName = paper.getName().toLowerCase();
            if (paperName.contains(newText)) {
                newList.add(paper);
            }
        }
        if (newList.size()>0){
            errorlayout.setVisibility(View.GONE);
            myAdapter.setFilter(newList);
        }else {
            showError("No Results", "Try Typing Different Words");
            myAdapter.setFilter(newList);
        }

        return true;
    }

    private void setUi() {
        errorlayout = findViewById(R.id.no_search_results);
        noResultMessage = findViewById(R.id.noSearchMessage);
        noResultTitle = findViewById(R.id.noSearchTitle);
        toolbar = findViewById(R.id.tbOfListpaper);
        spinner = findViewById(R.id.spnOfTb);
        progressBar = findViewById(R.id.pbOfListAsPerYear);
    }

    private void showError(String title , String message)
    {
        if (errorlayout.getVisibility() == View.GONE){
            errorlayout.setVisibility(View.VISIBLE);
        }
        noResultMessage.setText(message);
        noResultTitle.setText(title);
    }
}
