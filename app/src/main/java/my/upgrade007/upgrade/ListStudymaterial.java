package my.upgrade007.upgrade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.upgrade.R;

public class ListStudymaterial extends AppCompatActivity {

    CardView cvMech,cvCsandIT,cvElectrical,cvElectronic,cvCivil,cvChemical,cvMath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_studymaterial);

        setupUi();

        cvMech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListStudymaterial.this,ListStudymaterialFromDB.class);
                i.putExtra("branchName","Mechanical");
                startActivity(i);
            }
        });
        cvCsandIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(ListStudymaterial.this,ListStudymaterialFromDB.class);
                i.putExtra("branchName","CsAndIT");
                startActivity(i);
            }
        });

        cvElectronic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListStudymaterial.this,ListStudymaterialFromDB.class);
                i.putExtra("branchName","Electronic");
                startActivity(i);
            }
        });

        cvElectrical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListStudymaterial.this,ListStudymaterialFromDB.class);
                i.putExtra("branchName","Electrical");
                startActivity(i);
            }
        });

        cvCivil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListStudymaterial.this,ListStudymaterialFromDB.class);
                i.putExtra("branchName","Civil");
                startActivity(i);
            }
        });

        cvChemical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListStudymaterial.this,ListStudymaterialFromDB.class);
                i.putExtra("branchName","Chemical");
                startActivity(i);
            }
        });
        cvMath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListStudymaterial.this,ListStudymaterialFromDB.class);
                i.putExtra("branchName","Maths");
                startActivity(i);
            }
        });
    }

    private void setupUi()
    {
        cvMech = findViewById(R.id.cvMechMaterial);
        cvCsandIT = findViewById(R.id.cvCSandITStudymaterial);
        cvElectrical = findViewById(R.id.cvElectricalStudymaterial);
        cvElectronic = findViewById(R.id.cvElectronicStudymaterial);
        cvCivil = findViewById(R.id.cvCivilStudymayewrial);
        cvChemical = findViewById(R.id.cvChemicalStudymayewrial);
        cvMath = findViewById(R.id.cvMathsMaterial);

    }
}
