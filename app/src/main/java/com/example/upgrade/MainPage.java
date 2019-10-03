package com.example.upgrade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.auth.FirebaseAuth;

public class MainPage extends AppCompatActivity {

    Toolbar toolbar;

    CardView newsCard,blogCard,gateCard,kycCard;

    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pagee);

        setUpUI();
        setSupportActionBar(toolbar);

        implemantAdds();

        newsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newsCard.animate();
                Intent i = new Intent(MainPage.this,NewsActivity.class);
                startActivity(i);
            }
        });

        blogCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainPage.this,BlogActivity.class);
                startActivity(i);
            }
        });

        gateCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainPage.this,GateActivityFirst.class);
                startActivity(i);
            }
        });

        kycCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainPage.this,JobActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.aboutApp:
                Intent i1 = new Intent(MainPage.this,WebPage.class);
                i1.putExtra("url","https://02aboutbhushan.blogspot.com/2019/09/about-upgrade-application.html");
                startActivity(i1);
                break;

            case R.id.ContactInfo:
                Intent i2 = new Intent(MainPage.this,WebPage.class);
                i2.putExtra("url","https://02aboutbhushan.blogspot.com/2019/09/contact-info.html");
                startActivity(i2);
                break;

            case R.id.shareApp:
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                String shareBody = "your Body here";
                String shareSub = "Your Sub here";
                i.putExtra(i.EXTRA_SUBJECT,shareBody);
                i.putExtra(i.EXTRA_TEXT,shareBody);
                startActivity(Intent.createChooser(i,"share App Using"));
                break;
            case R.id.logOutLink:
               logOutApp();
                break;
        }
        return true;
    }

    private void logOutApp() {

        FirebaseAuth firebaseAuth;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        finish();
        Intent i = new Intent(MainPage.this,LoginActivity.class);
        startActivity(i);
    }

    private void setUpUI()
    {
        toolbar = findViewById(R.id.toolbarMainPage);
        gateCard = findViewById(R.id.gateCard);
        kycCard = findViewById(R.id.kycCard);
        newsCard = findViewById(R.id.newsCard);
        blogCard = findViewById(R.id.blogCard);
        mAdView = findViewById(R.id.adView);
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
