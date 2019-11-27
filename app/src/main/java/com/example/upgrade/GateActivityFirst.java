package com.example.upgrade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class GateActivityFirst extends AppCompatActivity {

    CardView cvSyllbus,cvQuestionpaper,cvLinks,cvStudymaterial;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gate_first);

        setupUi();
        implemantAdds();

        cvSyllbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GateActivityFirst.this,ListSyllabus.class);
                startActivity(i);
            }
        });

        cvStudymaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GateActivityFirst.this,ListStudymaterial.class);
                startActivity(i);
            }
        });

        cvQuestionpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GateActivityFirst.this,ListAsPerYear.class);
                startActivity(i);
            }
        });

        cvLinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GateActivityFirst.this,ListLinksFromDB.class);
                startActivity(i);

            }
        });

    }

    private void setupUi()
    {
        cvLinks = findViewById(R.id.cvLinksGate);
        cvQuestionpaper= findViewById(R.id.cvQPaperGate);
        cvStudymaterial = findViewById(R.id.cvStudyGate);
        cvSyllbus = findViewById(R.id.cvSyllbusGate);
        mAdView = findViewById(R.id.adViewgateFirst);
    }

    private void implemantAdds()
    {
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }
}
