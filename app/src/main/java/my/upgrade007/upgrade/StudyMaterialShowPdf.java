package my.upgrade007.upgrade;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.upgrade.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;

public class StudyMaterialShowPdf extends AppCompatActivity {

    private static final int PERMISSION_STORAGE_CODE = 1000;
    private String PDF_Link = "";
    private String My_pdf = "my_pdf.pdf";
    private PDFView pdfView;
    ProgressDialog progressDialog;
    FloatingActionButton floatingActionButton;
    Handler handler;
    NetworkOperation checkNet;
    File outPutFile;
    long downloadId;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(onDownloadComplete);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_material_show_pdf);
        floatingActionButton = findViewById(R.id.fabStudyMaterial);
       checkNet = new NetworkOperation();

        pdfView = findViewById(R.id.pdfViewGetMaterial);
        PDF_Link = getIntent().getStringExtra("materialUrl");
        My_pdf = getIntent().getStringExtra("nameMaterial") + "Study Notes   ";
        progressDialog = new ProgressDialog(StudyMaterialShowPdf.this);
        progressDialog.setTitle(My_pdf+"  is Downloading ");
        progressDialog.setMessage("please Wait ....");
        outPutFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "/UpGradeFiles"), My_pdf + ".pdf");
        progressDialog.setCancelable(false);


        if (outPutFile.exists()) {
            openPdf();
        } else {
            if (checkNet.checknetConnection(this) && !outPutFile.exists()) {
                progressDialog.show();
                downloadPDf(My_pdf, PDF_Link);
            } else Toast.makeText(this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
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

//    private void downloadPDf(final String fileName) {
//        new AsyncTask<Void, Integer, Boolean>() {
//            @Override
//            protected Boolean doInBackground(Void... voids) {
//                return downloadPDf();
//            }
//
//            private Boolean downloadPDf() {
//                try {
//                    File file = getFileStreamPath(fileName);
//                    if (file.exists())
//                        return true;
//                    try {
//                        FileOutputStream fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
//                        URL u = new URL(PDF_Link);
//                        URLConnection connection = u.openConnection();
//                        int contentlength = connection.getContentLength();
//                        BufferedInputStream input = new BufferedInputStream(u.openStream());
//                        byte[] data = new byte[contentlength];
//                        long total = 0;
//                        int count;
//                        while ((count = input.read(data)) != -1) {
//                            total += count;
//                            progressDialog.setProgress((int) (total / contentlength) * 100);
//                            fileOutputStream.write(data, 0, count);
//                        }
//                        fileOutputStream.flush();
//                        fileOutputStream.close();
//                        input.close();
//                        return true;
//                    } catch (final Exception e) {
//                        e.printStackTrace();
//                        return false;
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return false;
//            }
//
//            @Override
//            protected void onPostExecute(Boolean aBoolean) {
//                super.onPostExecute(aBoolean);
//
//                if (aBoolean) {
//                    openPdf(fileName);
//                } else {
//                    progressDialog.hide();
//                    Toast.makeText(StudyMaterialShowPdf.this, "Unable To download", Toast.LENGTH_LONG).show();
//                }
//            }
//        }.execute();
//    }


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
}
