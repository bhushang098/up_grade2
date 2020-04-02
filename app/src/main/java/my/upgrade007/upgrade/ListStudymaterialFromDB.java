package my.upgrade007.upgrade;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import my.upgrade007.upgrade.PdfModel.pdfList;

import com.example.upgrade.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class ListStudymaterialFromDB extends AppCompatActivity implements SearchView.OnQueryTextListener {

    FirebaseFirestore db;
    RecyclerView recyclerView;
    ArrayList<pdfList> arrayList = new ArrayList<>();
    AdapterOfListStudymaterial myAdapter;
    ProgressBar progressBar;
    String branch;
    Toolbar toolbar;
    NetworkOperation checkNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_studymaterial_from_db);
        checkNet = new NetworkOperation();
        //Assigning Intent got Through ListStudyMaterial.class in Branch
        branch = getIntent().getStringExtra("branchName");
        toolbar = findViewById(R.id.tbOfsimpleList);
        progressBar = findViewById(R.id.pbOfListStudyFromDB);
        progressBar.setVisibility(View.VISIBLE);
        setSupportActionBar(toolbar);
        toolbar.setTitle(branch);
        setRecview();
        setFb();
        dataFromFB();
    }

    public void dataFromFB() {

        if (arrayList.size() > 0)
            arrayList.clear();
        if (checkNet.checknetConnection(ListStudymaterialFromDB.this)) {

            db = FirebaseFirestore.getInstance();

            db.collection(branch).get()
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
                            myAdapter = new AdapterOfListStudymaterial(ListStudymaterialFromDB.this, arrayList);
                            recyclerView.setAdapter(myAdapter);
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(ListStudymaterialFromDB.this, "Error__>>>", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "No Network !!!", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public void setFb() {
        db = FirebaseFirestore.getInstance();
    }

    public void setRecview() {
        recyclerView = findViewById(R.id.recViewOflistFromDb);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setQueryHint("Search By Topic");
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
        progressBar.setVisibility(View.VISIBLE);

        ArrayList<pdfList> newList = new ArrayList<>();
        for (pdfList material : arrayList) {
            String materialName = material.getName().toLowerCase();
            if (materialName.contains(newText)) {
                newList.add(material);
            }
        }
        myAdapter.setFilter(newList);
        progressBar.setVisibility(View.INVISIBLE);
        return true;
    }
}
