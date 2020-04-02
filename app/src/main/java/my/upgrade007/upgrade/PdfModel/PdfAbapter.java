package my.upgrade007.upgrade.PdfModel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import my.upgrade007.upgrade.GetPaperPDFView;
import com.example.upgrade.R;

import java.util.ArrayList;

public class PdfAbapter extends RecyclerView.Adapter<PdfAbapter.PdfViewHolderForpapers> {

    private Context context;
    ArrayList<pdfList> pdfLists;
    String year;

    public PdfAbapter(Context context, ArrayList<pdfList> pdfLists,String year) {
        this.context = context;
        this.pdfLists = pdfLists;
        this.year = year;
    }

    @NonNull
    @Override
    public PdfViewHolderForpapers onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.pdf_elements,parent,false);

        return new PdfViewHolderForpapers(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PdfViewHolderForpapers holder, final int position) {
        holder.pdfpaperNbame.setText(pdfLists.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, GetPaperPDFView.class);
                i.putExtra("paperUrl",pdfLists.get(position).getLink());
                i.putExtra("name",pdfLists.get(position).getName());
                i.putExtra("year",year);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pdfLists.size();
    }

    public void setFilter(ArrayList<pdfList> newList) {

        pdfLists = new ArrayList<>();
        pdfLists.addAll(newList);
        notifyDataSetChanged();
    }

    class PdfViewHolderForpapers extends RecyclerView.ViewHolder{

        TextView pdfpaperNbame;

        public PdfViewHolderForpapers(@NonNull View itemView) {
            super(itemView);
            pdfpaperNbame = itemView.findViewById(R.id.tvPdfPaperName);
        }
    }
}
