package my.upgrade007.upgrade;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.upgrade.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.auth.FirebaseAuth;

public class MainPage extends AppCompatActivity {

    private static final int PERMISSION_STORAGE_CODE = 1000;

    Toolbar toolbar;
    private FirebaseAuth firebaseAuth;
    CardView newsCard, blogCard, gateCard, kycCard;
    Dialog oneTimeDialog;
    Button okButton;
    private AdView mAdView;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pagee);
        oneTimeDialog = new Dialog(this);
        setUpUI();
        setSupportActionBar(toolbar);
        implemantAdds();

        SharedPreferences sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firsStart = sharedPreferences.getBoolean("firstStart", true);
        if (firsStart) {
            showOneTimeDialog();
        }


        newsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newsCard.animate();
                Intent i = new Intent(MainPage.this, NewsActivity.class);
                startActivity(i);
            }
        });

        blogCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainPage.this, BlogActivity.class);
                startActivity(i);
            }
        });

        gateCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED) {

                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_STORAGE_CODE);
                    } else {
                        Intent i = new Intent(MainPage.this, GateActivityFirst.class);
                        startActivity(i);
                    }
                } else {
                    Intent i = new Intent(MainPage.this, GateActivityFirst.class);
                    startActivity(i);
                }
            }

        });

        kycCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainPage.this, JobActivity.class);
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
                Intent i1 = new Intent(MainPage.this, WebPage.class);
                i1.putExtra("url", "https://02aboutbhushan.blogspot.com/2019/09/about-upgrade-application.html");
                startActivity(i1);
                break;

            case R.id.ContactInfo:
                Intent i2 = new Intent(MainPage.this, WebPage.class);
                i2.putExtra("url", "https://02aboutbhushan.blogspot.com/2019/09/contact-info.html");
                startActivity(i2);
                break;

            case R.id.shareApp:

//                ApplicationInfo applicationInfo = getApplicationContext().getApplicationInfo();
//                String apkPath = applicationInfo.sourceDir;
//                Intent intent = new Intent(Intent.ACTION_SEND);
//                intent.setType("application/vnd.android.package-archive");
//                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(apkPath)));
//                startActivity(Intent.createChooser(intent, "Share App Using"));
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                String shareBody = "https://play.google.com/store/apps/details?id=my.upgrade007.upgrade";
                i.putExtra(Intent.EXTRA_SUBJECT, shareBody);
                i.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(i, "share App Link Using"));
                break;
            case R.id.logOutLink:
                logOutApp();
                break;

            case R.id.reference_credits:
                Intent i3 = new Intent(MainPage.this, WebPage.class);
                i3.putExtra("url", "https://02aboutbhushan.blogspot.com/2020/01/references-and-credits.html");
                startActivity(i3);
                break;
        }
        return true;
    }

    private void logOutApp() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        finish();
        Intent i = new Intent(MainPage.this, LoginActivity.class);
        startActivity(i);
    }

    private void setUpUI() {
        toolbar = findViewById(R.id.toolbarMainPage);
        gateCard = findViewById(R.id.gateCard);
        kycCard = findViewById(R.id.kycCard);
        newsCard = findViewById(R.id.newsCard);
        blogCard = findViewById(R.id.blogCard);
        mAdView = findViewById(R.id.adViewmainPage);
    }

    private void implemantAdds() {
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    public void showOneTimeDialog() {
        oneTimeDialog.setContentView(R.layout.custom_popup1);
        okButton = oneTimeDialog.findViewById(R.id.btnOkDialog);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oneTimeDialog.dismiss();
            }
        });
        oneTimeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(R.color.colorAccent));
        oneTimeDialog.show();
        SharedPreferences sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_STORAGE_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent i = new Intent(MainPage.this, GateActivityFirst.class);
                    startActivity(i);

                } else {
                    Toast.makeText(this, "To Access all features Please Allow Permissions", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
