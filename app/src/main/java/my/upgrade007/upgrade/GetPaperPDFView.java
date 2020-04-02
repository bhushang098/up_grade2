package my.upgrade007.upgrade;

import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.upgrade.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;

public class GetPaperPDFView extends AppCompatActivity {

    private String PDF_Link = "";
    private String My_pdf = "my_pdf.pdf";
    String year;
    private PDFView pdfView;
    NetworkOperation checkNet;
    ProgressDialog progressDialog;
    FloatingActionButton actionButton;
    File outPutFile;
    long downloadId;
    Dialog oneTimeDialog;
    Button okButton;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(onDownloadComplete);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_paper_pdfview);
        checkNet = new NetworkOperation();
        actionButton = findViewById(R.id.fabpdfdownloadPaper);
        oneTimeDialog = new Dialog(this);

        pdfView = findViewById(R.id.pdfViewGetpaper);
        PDF_Link = getIntent().getStringExtra("paperUrl");
        year = getIntent().getStringExtra("year");
        My_pdf = getIntent().getStringExtra("name") + "Question paper";
        progressDialog = new ProgressDialog(GetPaperPDFView.this);
        progressDialog.setTitle(My_pdf);
        progressDialog.setMessage(" Downloading please Wait ....");

        outPutFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "/UpGradeFiles"), My_pdf + ".pdf");


        SharedPreferences sharedPreferences = getSharedPreferences("prefs",MODE_PRIVATE);
        boolean firsStart = sharedPreferences.getBoolean("1stKeyNav",true);
        if (firsStart)
        {
            showOneTimeDialog();
        }

        if (outPutFile.exists()) {
            openPdf();
        } else {
            if (checkNet.checknetConnection(this) && !outPutFile.exists()) {
                progressDialog.show();
                downloadPDf(My_pdf, PDF_Link);
            } else Toast.makeText(this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.fromFile(outPutFile);
                if (outPutFile.length() > 0) {
                    Intent share = new Intent();
                    share.setAction(Intent.ACTION_SEND);
                    share.setType("application/pdf");
                    share.putExtra(Intent.EXTRA_STREAM, uri);
                    startActivity(Intent.createChooser(share, "Share PDF using"));
                }
            }
        });

        registerReceiver(onDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

    }

    private void downloadPDf(String fileName, String url) {
        if (!outPutFile.exists()) {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);

            request.setTitle(fileName);
            request.setDescription("is Downloading Please Wait...");
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS + "/UpGradeFiles", fileName + ".pdf");

            DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            downloadId = manager.enqueue(request);
        }
    }

    private void openPdf() {

        try {
            pdfView.setVisibility(View.INVISIBLE);
            pdfView.fromFile(outPutFile)
                    .enableSwipe(true).swipeHorizontal(false).scrollHandle(new DefaultScrollHandle(this))
                    .spacing(0).load();
            pdfView.setVisibility(View.VISIBLE);
            progressDialog.hide();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (downloadId == id) {
                Toast.makeText(context, "Download Complete", Toast.LENGTH_SHORT).show();
                progressDialog.hide();
                openPdf();
            } else progressDialog.hide();
        }
    };

    public void showOneTimeDialog()
    {
        oneTimeDialog.setContentView(R.layout.popup_paper);
        okButton=oneTimeDialog.findViewById(R.id.btnKeyNavOk);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oneTimeDialog.dismiss();
            }
        });
        oneTimeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
        oneTimeDialog.show();
        SharedPreferences sharedPreferences = getSharedPreferences("prefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("1stKeyNav",false);
        editor.apply();

    }

}
