package com.example.upgrade;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListSyllabus extends AppCompatActivity implements SearchView.OnQueryTextListener{

    public String[] brNames = {"General Aptitude(GA)","Aerospace Engineering (AE)","Agricultural Engineering(AG)","Architecture and Planning (AR)","Biotechnology (BT)"
            ,"Civil Engineering(CE)","Chemical Engineering(CH)","Computer Science and Information Technology(CS)","Chemistry(CY)","Electronics And Communication Engineering(EC)",
            "Electrical Engineering (EE)","Ecology and Evolution(EY)","Geology and Geophysics(GG)","Instrumentation Engineering (IN)","Mathematics(MA)",
            "Mechanical Engineering (ME)","Mining Engineering (MN)","Metallurgical Engineering (MT)","Petroleum Engineering (PE)","Physics(PH)",
            "Production and Industrial Engineering (PI)","Textile Engineering and Fiber Science (TF)","Mathematical Engineering (ME)","Fluid Mechanics(XE-B)","Materials Science (XE-c)",
            "Solid Mechanics (XE-D)","Thermodynamics (XE-E)","Polymer Science and Engineering (XE-F)","Food Technology(XE-G)","Atmospheric and Ocean Science (XE-H)",
            "Chemistry(XL-P)","Biochemistry (XL-Q)","Botany (XL-R)","Microbiology (XL-s)","Zoology (XL-T)","Food Technology(XL-U)"};

   public int[] brImage = {R.drawable.contract,R.drawable.plane,R.drawable.agriculture,R.drawable.architecture,R.drawable.biotechnology,
            R.drawable.engineer,R.drawable.chemical,R.drawable.csandit,R.drawable.chemistry,R.drawable.electronics,
            R.drawable.eletrical,R.drawable.ecology,R.drawable.geology,R.drawable.instrumentalengg,R.drawable.mathamatics,
            R.drawable.engineer,R.drawable.miningengineering,R.drawable.metallergy,R.drawable.petrolium,R.drawable.physics,
            R.drawable.production,R.drawable.textileengineering,R.drawable.engg_mechanics,R.drawable.fluid_mechanics,R.drawable.materialscience,
            R.drawable.solidmechanics,R.drawable.thermofynamics,R.drawable.polymer,R.drawable.foodtechnology,R.drawable.atmospheric,
            R.drawable.p_chemistry,R.drawable.biochemistry,R.drawable.botany,R.drawable.microbiology,R.drawable.zoology,R.drawable.foodtecg2};

    String [] url = {"https://firebasestorage.googleapis.com/v0/b/fir-1-b4412.appspot.com/o/syllabus%2FGeneral-Aptitude_compressed.pdf?alt=media&token=2dc1ef23-7966-45f9-b81b-f65859438ede"
    ,"https://firebasestorage.googleapis.com/v0/b/fir-1-b4412.appspot.com/o/syllabus%2FAE-Aerospace-Engineering.pdf?alt=media&token=ed306589-9a18-4c5c-9fc6-b5dc8b6a74d2",
    "https://firebasestorage.googleapis.com/v0/b/fir-1-b4412.appspot.com/o/syllabus%2FAG_Agricultural-Engineering_compressed.pdf?alt=media&token=98d22d0b-ef39-4747-b137-9d5448c3edb3",
    "https://firebasestorage.googleapis.com/v0/b/fir-1-b4412.appspot.com/o/syllabus%2FAR_Architecture-and-Planning_compressed.pdf?alt=media&token=1ec217cd-4702-4ce4-be3c-078991bc2770"
    ,"https://firebasestorage.googleapis.com/v0/b/fir-1-b4412.appspot.com/o/syllabus%2FBT_Biotechnology_compressed.pdf?alt=media&token=0d9c9bf0-24b7-490d-9b46-87f59bee12d4",
    "https://firebasestorage.googleapis.com/v0/b/fir-1-b4412.appspot.com/o/syllabus%2FCE_Civil-Engineering_compressed.pdf?alt=media&token=1013f212-ae84-44d8-b117-8295c53c0991",
    "https://firebasestorage.googleapis.com/v0/b/fir-1-b4412.appspot.com/o/syllabus%2FCH_Chemical-Engineering_compressed.pdf?alt=media&token=009fa8e0-4c37-49e4-a5d9-8b3ad6fc0bae",
    "https://firebasestorage.googleapis.com/v0/b/fir-1-b4412.appspot.com/o/syllabus%2FCS_Computer-Science-and-Information-Technology_compressed.pdf?alt=media&token=8209195b-1872-469a-a4c7-d88c8009e691",
    "https://firebasestorage.googleapis.com/v0/b/fir-1-b4412.appspot.com/o/syllabus%2FCY_Chemistry_compressed.pdf?alt=media&token=844cf6a1-72ee-4efa-ae85-f0cd6f46e8ed",
    "https://firebasestorage.googleapis.com/v0/b/fir-1-b4412.appspot.com/o/syllabus%2FEC_Electronics-and-Communications_Engineering_compressed.pdf?alt=media&token=97bf153d-e06b-4407-bf51-e6eec7dec597",
    "https://firebasestorage.googleapis.com/v0/b/fir-1-b4412.appspot.com/o/syllabus%2FEE_Electrical-Engineering_compressed.pdf?alt=media&token=32427958-b898-4279-9e60-721b160b00af",
    "https://firebasestorage.googleapis.com/v0/b/fir-1-b4412.appspot.com/o/syllabus%2FEY_Ecology-and-Evolution_compressed.pdf?alt=media&token=080ea3f5-5cfa-4786-8447-5ab31829ba84",
    "https://firebasestorage.googleapis.com/v0/b/fir-1-b4412.appspot.com/o/syllabus%2FGG_Geology-and-Geophysics_compressed.pdf?alt=media&token=c5dfb6cc-0257-4fde-93e2-4c7f48839093",
    "https://firebasestorage.googleapis.com/v0/b/fir-1-b4412.appspot.com/o/syllabus%2FIN_Instrumentation-Engineering_compressed.pdf?alt=media&token=70dd5de9-038e-45ed-9b96-45868c10ee24",
    "https://firebasestorage.googleapis.com/v0/b/fir-1-b4412.appspot.com/o/syllabus%2FMA_Mathematics_compressed.pdf?alt=media&token=17bf7174-918e-4248-a307-506fb5189104",
    "https://firebasestorage.googleapis.com/v0/b/fir-1-b4412.appspot.com/o/syllabus%2FME_Mechanical-Engineering_compressed.pdf?alt=media&token=914f7918-b417-4bdc-ae39-d3036325eb46",
    "https://firebasestorage.googleapis.com/v0/b/fir-1-b4412.appspot.com/o/syllabus%2FMN_Mining-Engineering_compressed.pdf?alt=media&token=91de8fc4-4127-463f-9581-87fb2a570fd2",
    "https://firebasestorage.googleapis.com/v0/b/fir-1-b4412.appspot.com/o/syllabus%2FMT_Metallurgical-Engineering_compressed.pdf?alt=media&token=80ec193a-ec64-4203-8182-5ff33d580368",
    "https://firebasestorage.googleapis.com/v0/b/fir-1-b4412.appspot.com/o/syllabus%2FPE_Petroleum_Engineering_compressed.pdf?alt=media&token=a695e3a5-f041-4b36-92b1-5cb2518ba8e0",
    "https://firebasestorage.googleapis.com/v0/b/fir-1-b4412.appspot.com/o/syllabus%2FPH_Physics_compressed.pdf?alt=media&token=afad36f1-fcd5-44b5-b6e6-25e70d863691",
    "https://firebasestorage.googleapis.com/v0/b/fir-1-b4412.appspot.com/o/syllabus%2FPI_Production-And-Industrial-Engineering_compressed.pdf?alt=media&token=c882edd5-a6c0-42af-851f-221bf4d4fe80",
    "https://firebasestorage.googleapis.com/v0/b/fir-1-b4412.appspot.com/o/syllabus%2FTF_Textile_Engineering_and_Fibre_Science_compressed.pdf?alt=media&token=c4d0b402-dbdc-4391-bed5-f1561aeb9af1",
    "https://firebasestorage.googleapis.com/v0/b/fir-1-b4412.appspot.com/o/syllabus%2FME_Mechanical-Engineering_compressed.pdf?alt=media&token=914f7918-b417-4bdc-ae39-d3036325eb46",
    "https://firebasestorage.googleapis.com/v0/b/fir-1-b4412.appspot.com/o/syllabus%2FXE_B_Fluid-Mechanics_compressed.pdf?alt=media&token=cdece040-5788-43eb-92b1-5169f26d903e",
    "https://firebasestorage.googleapis.com/v0/b/fir-1-b4412.appspot.com/o/syllabus%2FXE_C_Materials-Science_compressed.pdf?alt=media&token=df97a1a3-c29b-4559-bd66-7ff4c61616f3",
    "https://firebasestorage.googleapis.com/v0/b/fir-1-b4412.appspot.com/o/syllabus%2FXE_D_Solid-Mechanics_compressed.pdf?alt=media&token=6acd5b33-83e4-4afb-ae01-f07faf4f088f",
    "https://firebasestorage.googleapis.com/v0/b/fir-1-b4412.appspot.com/o/syllabus%2FXE_E_Thermodynamics_compressed.pdf?alt=media&token=cf538957-d4ec-4796-9fce-c06fdd051198",
    "https://firebasestorage.googleapis.com/v0/b/fir-1-b4412.appspot.com/o/syllabus%2FXE_F_Polymer-Science-and-Engineering_compressed.pdf?alt=media&token=1d12ad5b-bf97-47bb-bda9-1e9a21247a2a",
    "https://firebasestorage.googleapis.com/v0/b/fir-1-b4412.appspot.com/o/syllabus%2FXE_G_Food-Technology_compressed.pdf?alt=media&token=bcb79f10-81c5-466e-a105-508d12eac373",
    "https://firebasestorage.googleapis.com/v0/b/fir-1-b4412.appspot.com/o/syllabus%2FXE_H_Atmospheric_OceSc_compressed.pdf?alt=media&token=6c3d75a5-c924-4d43-825d-8c678f11c6ea",
    "https://firebasestorage.googleapis.com/v0/b/fir-1-b4412.appspot.com/o/syllabus%2FXL_P_Chemistry_compressed.pdf?alt=media&token=cae7b891-4c56-48c3-adc2-55c10a659551",
    "https://firebasestorage.googleapis.com/v0/b/fir-1-b4412.appspot.com/o/syllabus%2FXL_Q_Biochemistry_compressed.pdf?alt=media&token=3e6f8666-d2b8-4c7b-934b-5d12fc568048",
    "https://firebasestorage.googleapis.com/v0/b/fir-1-b4412.appspot.com/o/syllabus%2FXL_R_Botany_compressed.pdf?alt=media&token=7dce121e-bb07-4dc7-9acf-a57c61315ff7",
    "https://firebasestorage.googleapis.com/v0/b/fir-1-b4412.appspot.com/o/syllabus%2FXL_S_Microbiology_compressed.pdf?alt=media&token=687642d9-e8c1-4104-88f8-ad68e6d4db07",
    "https://firebasestorage.googleapis.com/v0/b/fir-1-b4412.appspot.com/o/syllabus%2FXL_T_Zoology_compressed.pdf?alt=media&token=b75afb81-795a-40a7-91ea-9575a4aa347f",
    "https://firebasestorage.googleapis.com/v0/b/fir-1-b4412.appspot.com/o/syllabus%2FXL_U_Food-Technology_compressed.pdf?alt=media&token=2006594c-80f6-4453-a535-f7cc09df680b"};

    Toolbar toolbar;
    RecyclerView recyclerView;
    AdapterOfBranches adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Branches> arrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_syllabus);
        toolbar = findViewById(R.id.tbofbrlayout);
        recyclerView = findViewById(R.id.rvOfBranches);
        setSupportActionBar(toolbar);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);
        int count = 0;

        for (String Name : brNames)
        {
            arrayList.add(new Branches(Name,brImage[count],url[count]));
            count ++;
        }
        adapter = new AdapterOfBranches(arrayList,ListSyllabus.this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setQueryHint("Search your Branch");
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        newText = newText.toLowerCase();

        ArrayList<Branches> newList = new ArrayList<>();
        for (Branches branches : arrayList)
        {
            String brname = branches.getBrName().toLowerCase();
            if (brname.contains(newText)){
                newList.add(branches);
            }
        }
        adapter.setFilter(newList);
        return true;
    }

}

