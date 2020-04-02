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
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.upgrade.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;

public class GetSyllabus extends AppCompatActivity {

    private String PDF_Link;
    private String My_pdf;
    private PDFView pdfView;
    ProgressDialog progressDialog;
    FloatingActionButton actionButton;
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
        setContentView(R.layout.activity_get_syllabus);
        pdfView = findViewById(R.id.pdfView);
        checkNet = new NetworkOperation();
        actionButton = findViewById(R.id.fabpdfdownloadsyllabus);

        PDF_Link = getIntent().getStringExtra("urlodbrsyllabus");
        My_pdf = getIntent().getStringExtra("brName") + " Syllabus     ";
        progressDialog = new ProgressDialog(GetSyllabus.this);
        progressDialog.setTitle(My_pdf+" is Downloading");
        progressDialog.setMessage("please Wait ....");
        outPutFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "/UpGradeFiles"), My_pdf + ".pdf");

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
//            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
//                    PackageManager.PERMISSION_DENIED) {
//
//                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
//                requestPermissions(permissions, PERMISSION_STORAGE_CODE);
//            } else {
////                if (!outPutFile.exists())
////                    downloadPDf(My_pdf, PDF_Link);
//            }
//        } else {
//            if (checkNet.checknetConnection(this)) {
//                progressDialog.show();
//                downloadPDf(My_pdf, PDF_Link);
//            }
//        }

        if (outPutFile.exists()) {
            openPdf();
        } else {
            if (checkNet.checknetConnection(this) && !outPutFile.exists()) {
                progressDialog.show();
                downloadPDf(My_pdf, PDF_Link);
            } else Toast.makeText(this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }

//        if (checkNet.checknetConnection(this)) {
//            progressDialog.show();
//            if (!outPutFile.exists()) {
//                downloadPDf(My_pdf, PDF_Link);
//            }
//        } else
//            Toast.makeText(this, "NO Internet !! \nSee Your Downloads", Toast.LENGTH_SHORT).show();


        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.fromFile(outPutFile);
                if (outPutFile.length() > 0) {
                    Intent share = new Intent();
                    share.setAction(Intent.ACTION_SEND);
                    progressDialog.dismiss();
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

//    private void downloadPDf(final String fileName, final String url) {
//        new AsyncTask<Void, Integer, Boolean>() {
//            @Override
//            protected Boolean doInBackground(Void... voids) {
//                return downloadPDf();
//            }
//
//            private Boolean downloadPDf() {
//                try {
//                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "/UpGradeFiles"), fileName + ".pdf");
//                    if (file.exists())
//                        return true;
//                    try {
//                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
//                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
//
//                        request.setTitle("Downloading");
//                        request.setDescription(fileName + "id Downlaoding Paleas Wait...");
//                        request.allowScanningByMediaScanner();
//                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS + "/UpGradeFiles", fileName + ".pdf");
//
//                        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
//                        manager.enqueue(request);
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
//                    openPdf();
//                } else {
//                    progressDialog.hide();
//                    Toast.makeText(GetSyllabus.this, "Unable To download", Toast.LENGTH_LONG).show();
//                }
//            }
//        }.execute();
//    }

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
            progressDialog.hide();
        }

    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case PERMISSION_STORAGE_CODE: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    if (checkNet.checknetConnection(this))
//                        downloadPDf(My_pdf, PDF_Link);
//                    else Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
//
//                } else {
//                    Toast.makeText(this, "Allow Permissions", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//    }

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
