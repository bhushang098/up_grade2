package my.upgrade007.upgrade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.upgrade.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;

public class OpenDownloadedFiles extends AppCompatActivity {

    PDFView pdfView;
    String fileName;
    ProgressBar progressBar;
    FloatingActionButton shareButton;
    Uri uri;
    File openFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_downloaded_files);
        pdfView = findViewById(R.id.pdfViewDownloadedFiles);
        shareButton = findViewById(R.id.fabOfDownloadedfiles);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 openFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS+ "/UpGradeFiles"),fileName);
                Intent share = new Intent(Intent.ACTION_SEND);
                try {
                    uri = FileProvider.getUriForFile(
                            OpenDownloadedFiles.this,
                            "com.upgrade007.upgrade.provider",
                            openFile);
                } catch (IllegalArgumentException e) {
                    Log.e("File Selector>>>>>",
                            "The selected file can't be shared: " + openFile.toString());
                }

                share.setDataAndType(uri,"application/pdf");
                share.putExtra(Intent.EXTRA_STREAM,uri);
                startActivity(Intent.createChooser(share, "Share PDF using"));

            }
        });
        progressBar = findViewById(R.id.pbOfShowDownFile);
        fileName = getIntent().getStringExtra("fileName");

        try {
            openFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS+ "/UpGradeFiles"),fileName);
            progressBar.setVisibility(View.VISIBLE);
            pdfView.setVisibility(View.INVISIBLE);
            pdfView.fromFile(openFile)
                    .enableSwipe(true).swipeHorizontal(false).scrollHandle(new DefaultScrollHandle(this))
                    .spacing(0).load();
            progressBar.setVisibility(View.INVISIBLE);
            pdfView.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
