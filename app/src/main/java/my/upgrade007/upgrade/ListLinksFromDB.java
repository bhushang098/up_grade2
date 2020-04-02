package my.upgrade007.upgrade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

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

public class ListLinksFromDB extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    ArrayList<LinksInDB> arrayList = new ArrayList<>();
    FirebaseFirestore db;
    LinksAdapter myAdapter;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_links_from_db);
        toolbar =  findViewById(R.id.tbOfListLinks);
        progressBar = findViewById(R.id.pbOfListLInksFromDB);
        toolbar.setTitle("Important Links");
        setSupportActionBar(toolbar);
        setRecview();
        setFb();
        dataFromFB();

    }

    public void dataFromFB() {

        if (arrayList.size() > 0)
            arrayList.clear();

        db = FirebaseFirestore.getInstance();

        db.collection("Links").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot documentSnapshot : Objects.requireNonNull(task.getResult())) {

                            LinksInDB list = new LinksInDB(documentSnapshot.getString("Name")
                                    , documentSnapshot.getString("Link"));
                            arrayList.add(list);
                        }

                       Collections.sort(arrayList, new Comparator<LinksInDB>() {
                           @Override
                           public int compare(LinksInDB o1, LinksInDB o2) {
                               return o1.getLinkName().compareTo(o2.getLinkName());
                           }
                       });
                        myAdapter = new LinksAdapter(arrayList,ListLinksFromDB.this);
                        recyclerView.setAdapter(myAdapter);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(ListLinksFromDB.this, "Error__>>>", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setRecview() {
        recyclerView = findViewById(R.id.recOfListLinksFromDB);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    public void setFb() {
        db = FirebaseFirestore.getInstance();
    }

}
