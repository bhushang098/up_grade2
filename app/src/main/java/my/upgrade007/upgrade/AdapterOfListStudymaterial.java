package my.upgrade007.upgrade;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import my.upgrade007.upgrade.PdfModel.pdfList;

import com.example.upgrade.R;

import java.util.ArrayList;

public class AdapterOfListStudymaterial extends RecyclerView.Adapter<AdapterOfListStudymaterial.ViewHolderOfSimpleListElements> {

    private Context context;
    ArrayList<pdfList> simpleList;

    AdapterOfListStudymaterial(Context context, ArrayList<pdfList> simpleList)
    {
        this.context = context;
        this.simpleList = simpleList;
    }

    @NonNull
    @Override
    public ViewHolderOfSimpleListElements onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.pdf_elements,parent,false);
        return new ViewHolderOfSimpleListElements(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderOfSimpleListElements holder, final int position) {
        holder.tvfromDb.setText(simpleList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,StudyMaterialShowPdf.class);
                i.putExtra("materialUrl",simpleList.get(position).getLink());
                i.putExtra("nameMaterial",simpleList.get(position).getName());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return simpleList.size();
    }

    class ViewHolderOfSimpleListElements extends RecyclerView.ViewHolder{

        TextView tvfromDb;
        public ViewHolderOfSimpleListElements(@NonNull View itemView) {
            super(itemView);
            tvfromDb = itemView.findViewById(R.id.tvPdfPaperName);
        }
    }

    public void setFilter(ArrayList<pdfList> newList) {

        simpleList = new ArrayList<>();
        simpleList.addAll(newList);
        notifyDataSetChanged();
    }
}
