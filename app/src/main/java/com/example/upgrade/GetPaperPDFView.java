package com.example.upgrade;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.pdf.PdfRenderer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;

public class GetPaperPDFView extends AppCompatActivity {

    private String PDF_Link = "";
    private String My_pdf = "my_pdf.pdf";
    String year;
    private PDFView pdfView;
    ImageView img;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_paper_pdfview);

        pdfView = findViewById(R.id.pdfViewGetpaper);
        PDF_Link = getIntent().getStringExtra("paperUrl");
        My_pdf = getIntent().getStringExtra("name");
        progressDialog = new ProgressDialog(GetPaperPDFView.this);
        progressDialog.setTitle("Downloading");
        progressDialog.setMessage("please Wait ....");
        progressDialog.show();
        year = getIntent().getStringExtra("year");
        downloadPDf(My_pdf);
    }

    public void downloadPDf(final String fileName) {
        new AsyncTask<Void, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                return downloadPDf();
            }

            private Boolean downloadPDf() {
                try {
                    File file = getFileStreamPath(fileName);
                    if (file.exists())
                        return true;

                    try {
                        FileOutputStream fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                        URL u = new URL(PDF_Link);
                        URLConnection connection = u.openConnection();
                        int contentlength = connection.getContentLength();
                        BufferedInputStream input = new BufferedInputStream(u.openStream());
                        byte[] data = new byte[contentlength];
                        long total = 0;
                        int count;
                        while ((count = input.read(data)) != -1) {
                            total += count;
                            fileOutputStream.write(data, 0, count);
                        }
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        input.close();
                        return true;

                    } catch (final Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);

                if (aBoolean) {
                    openPdf(fileName);
                } else {
                    progressDialog.hide();
                    Toast.makeText(GetPaperPDFView.this, "Unable To download", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    private void openPdf(String fileName) {
        try {
            progressDialog.hide();
            File file = getFileStreamPath(fileName);
            pdfView.setVisibility(View.VISIBLE);
            pdfView.fromFile(file)
                    .enableSwipe(true).swipeHorizontal(false)
                    .spacing(0).load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
