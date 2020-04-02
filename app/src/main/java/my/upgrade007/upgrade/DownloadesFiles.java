package my.upgrade007.upgrade;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.upgrade.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class DownloadesFiles extends AppCompatActivity implements SearchView.OnQueryTextListener {

    //  "/data/data/com.upgrade007.upgrade/files"

    private String m_root;
    ArrayList<String> m_item;
    ArrayList<String> m_path;
    ArrayList<String> m_files;
    ArrayList<String> m_filesPath;
    DownloadedAdapter recAdapter;
    RecyclerView recyclerView;
    private RelativeLayout errorlayout;
    TextView noResultTitle, noResultMessage;
    String m_curDir;
    File[] m_filesArray;
    Toolbar toolbar;
    ArrayList<DownloadedList> arraylist = new ArrayList<>();
    DownloadedList fileitem;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_downloades_files);
        toolbar = findViewById(R.id.tbOfDownloadedFiles);
        errorlayout = findViewById(R.id.no_search_results);
        noResultMessage = findViewById(R.id.noSearchMessage);
        noResultTitle = findViewById(R.id.noSearchTitle);
        m_root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/UpGradeFiles";
        setSupportActionBar(toolbar);
        toolbar.setTitle("Downloads");
        setRecview();
        recyclerView = findViewById(R.id.recViewForDownFIles);
        getDirFromRoot(m_root);
    }

    public void getDirFromRoot(String p_rootPath) {
        m_item = new ArrayList<String>();
        Boolean m_isRoot = true;
        m_path = new ArrayList<String>();
        m_files = new ArrayList<String>();
        m_filesPath = new ArrayList<String>();
        File m_file = new File(p_rootPath);
        m_filesArray = m_file.listFiles();
        if (!p_rootPath.equals(m_root)) {
            m_item.add("../");
            m_path.add(m_file.getParent());
            m_isRoot = false;
        }
        m_curDir = p_rootPath;
        //sorting file list in alphabetical order
        Arrays.sort(m_filesArray);
        if (m_filesArray.length < 1)
            showError("No Results", "You haven't Downloaded Anything Yet");
        else {
            for (int i = 0; i < m_filesArray.length; i++) {
                File file = m_filesArray[i];
                if (file.isDirectory()) {
                    m_item.add(file.getName());
                    m_path.add(file.getPath());
                } else {
                    m_files.add(file.getName());
                    m_filesPath.add(file.getPath());
                }
            }
            for (String m_AddFile : m_files) {
                m_item.add(m_AddFile);
            }
            for (String m_AddPath : m_filesPath) {
                m_path.add(m_AddPath);
            }
        }

        for (int i = 0; i < m_item.size(); i++) {
            fileitem = new DownloadedList(m_item.get(i), m_path.get(i));
            arraylist.add(fileitem);
        }
        Collections.sort(arraylist, new Comparator<DownloadedList>() {
            @Override
            public int compare(DownloadedList o1, DownloadedList o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        if (arraylist.size() > 0) {
            errorlayout.setVisibility(View.GONE);
            recAdapter = new DownloadedAdapter(DownloadesFiles.this, m_isRoot, arraylist);
            recyclerView.setAdapter(recAdapter);
        } else {
            showError("No Results", "You haven't Downloaded Anything Yet");
        }
    }

    public void setRecview() {
        recyclerView = findViewById(R.id.recViewForDownFIles);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setQueryHint("Search your Downloaded File");
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String newQuery = newText.toLowerCase().trim();
        ArrayList<DownloadedList> newList = new ArrayList<>();
        for (DownloadedList file_name : arraylist) {
            if (file_name.getName().toLowerCase().trim().contains(newQuery)) {
                newList.add(file_name);
            }
        }
        if (newList.size() > 0) {
            errorlayout.setVisibility(View.GONE);
            recAdapter.setFilter(newList);
        } else {
            showError("No Results", "Try Typing Different Words ");
            recAdapter.setFilter(newList);
        }
        return true;
    }

//    String newQuery = newText.toLowerCase().trim();
//    ArrayList<String> newList = new ArrayList<>();
//        for (int i = 0; i < m_item.size(); i++) {
//        if (m_item.get(i).substring(0, m_item.get(i).length() - 14).toLowerCase().trim().contains(newQuery)) {
//            newList.add(m_item.get(i).substring(0, m_item.get(i).length() - 14));
//        }
//    }
//        recAdapter.setFilter(newList);
//        return true;

    private void showError(String title, String message) {
        if (errorlayout.getVisibility() == View.GONE) {
            errorlayout.setVisibility(View.VISIBLE);
        }
        noResultMessage.setText(message);
        noResultTitle.setText(title);
    }
}


