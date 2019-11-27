package com.example.upgrade;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;

public class GetSyllabus extends AppCompatActivity {

    private String PDF_Link = "";
    private  String My_pdf = "my_pdf.pdf";
    private PDFView pdfView;
    ProgressDialog progressDialog;
    FloatingActionButton actionButton;
    NetworkOperation checkNet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_syllabus);
        pdfView = findViewById(R.id.pdfView);
        checkNet = new NetworkOperation();
        actionButton = findViewById(R.id.fabpdfdownloadsyllabus);

        PDF_Link = getIntent().getStringExtra("urlodbrsyllabus");
        progressDialog = new ProgressDialog(GetSyllabus.this);
        progressDialog.setTitle("Downloading");
        progressDialog.setMessage("please Wait ....");
        progressDialog.show();
        My_pdf = getIntent().getStringExtra("brName");

        downloadPDf(My_pdf);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                String shareBody = "Nothing";
                String shareSub = "Share The PDF";
                i.putExtra(Intent.EXTRA_SUBJECT,shareBody);
                i.putExtra(Intent.EXTRA_TEXT,shareBody);
                startActivity(Intent.createChooser(i,"share PDF Using"));
            }
        });
    }

    private void downloadPDf(final String fileName) {
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
                        }
                        catch (final Exception e) {
                            e.printStackTrace();
                            return false;
                        }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);

                if (aBoolean){
                    openPdf(fileName);
                }else {
                    progressDialog.hide();
                    Toast.makeText(GetSyllabus.this,"Unable To download",Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }
    private void openPdf(String fileName)
    {
        try {
            progressDialog.hide();
            File file = getFileStreamPath(fileName);
            pdfView.setVisibility(View.VISIBLE);
            pdfView.fromFile(file)
                    .enableSwipe(true).swipeHorizontal(false)
                    .spacing(0).load();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
