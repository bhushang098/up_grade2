package my.upgrade007.upgrade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.upgrade.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.io.File;

public class GateActivityFirst extends AppCompatActivity {

    CardView cvSyllbus, cvQuestionpaper, cvLinks, cvStudymaterial;
    private AdView mAdView;
    Toolbar toolbar;
    Dialog oneTimeDialog;
    NetworkOperation checknet;
    Button okButton;
    File outPutFile;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gate_first);
        oneTimeDialog = new Dialog(this);
        handler = new Handler();
        setupUi();
        setSupportActionBar(toolbar);
        implemantAdds();


        SharedPreferences sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firsStart = sharedPreferences.getBoolean("1stonNavDownload", true);
        if (firsStart) {
            showOneTimeDialog();
        }

        cvSyllbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GateActivityFirst.this, ListSyllabus.class);
                startActivity(i);
            }
        });

        cvStudymaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GateActivityFirst.this, ListStudymaterial.class);
                startActivity(i);
            }
        });

        cvQuestionpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GateActivityFirst.this, ListAsPerYear.class);
                startActivity(i);
            }
        });

        cvLinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checknet = new NetworkOperation();
                if (checknet.checknetConnection(GateActivityFirst.this)) {
                    Intent i = new Intent(GateActivityFirst.this, ListLinksFromDB.class);
                    startActivity(i);
                } else {
                    Toast.makeText(GateActivityFirst.this, "Needs Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                outPutFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/UpGradeFiles");
                if (!outPutFile.exists())
                    outPutFile.mkdir();
            }
        }, 100);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_gate_first, menu);
        return true;
    }

    private void setupUi() {
        toolbar = findViewById(R.id.tbOfMAinGate);
        cvLinks = findViewById(R.id.cvLinksGate);
        cvQuestionpaper = findViewById(R.id.cvQPaperGate);
        cvStudymaterial = findViewById(R.id.cvStudyGate);
        cvSyllbus = findViewById(R.id.cvSyllbusGate);
        mAdView = findViewById(R.id.adViewgateFirst);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.downloadedFiles:
                Intent i1 = new Intent(GateActivityFirst.this, DownloadesFiles.class);
                startActivity(i1);
                break;

        }
        return true;
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

    public void showOneTimeDialog() {
        oneTimeDialog.setContentView(R.layout.custom_popup2);
        okButton = oneTimeDialog.findViewById(R.id.btngotIt);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oneTimeDialog.dismiss();
            }
        });
        oneTimeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
        oneTimeDialog.show();
        SharedPreferences sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("1stonNavDownload", false);
        editor.apply();

    }
}
