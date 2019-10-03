package com.example.upgrade;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.upgrade.PdfModel.pdfList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ListStudymaterialFromDB extends AppCompatActivity implements SearchView.OnQueryTextListener{

    FirebaseFirestore db;
    RecyclerView recyclerView;
    ArrayList<pdfList> arrayList = new ArrayList<>();
    AdapterOfListStudymaterial myAdapter;
    String branch;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_studymaterial_from_db);

        //Assigning Intent got Through ListStudyMaterial.class in Branch
        branch = getIntent().getStringExtra("branchName");
        toolbar = findViewById(R.id.tbOfsimpleList);
        setSupportActionBar(toolbar);
        toolbar.setTitle(branch);
        setRecview();
        setFb();
        dataFromFB();
    }

    public void dataFromFB() {

        if (arrayList.size()>0)
            arrayList.clear();

        db = FirebaseFirestore.getInstance();

        db.collection(branch).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot documentSnapshot: task.getResult()){

                            pdfList list = new pdfList(documentSnapshot.getString("Name")
                                    ,documentSnapshot.getString("Link"));
                            arrayList.add(list);
                        }
                        myAdapter = new AdapterOfListStudymaterial(ListStudymaterialFromDB.this,arrayList);
                        recyclerView.setAdapter(myAdapter);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ListStudymaterialFromDB.this,"Error__>>>",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setFb() {
        db = FirebaseFirestore.getInstance();
    }

    public  void setRecview() {
        recyclerView = findViewById(R.id.recViewOflistFromDb);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_menu,menu);
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

        ArrayList<pdfList> newList = new ArrayList<>();
        for (pdfList material : arrayList)
        {
            String materialName = material.getName().toLowerCase();
            if (materialName.contains(newText)){
                newList.add(material);
            }
        }
        myAdapter.setFilter(newList);
        return true;
    }
}
