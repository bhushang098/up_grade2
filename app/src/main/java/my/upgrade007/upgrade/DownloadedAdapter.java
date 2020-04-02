package my.upgrade007.upgrade;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.upgrade.R;

import java.util.ArrayList;

public class DownloadedAdapter extends RecyclerView.Adapter<DownloadedAdapter.DownloadedFilesViewHolder> {

    Context m_context;
    Boolean m_isRoot;
    ArrayList<DownloadedList> simpleList;

    public DownloadedAdapter(Context m_context, Boolean m_isRoot, ArrayList<DownloadedList> simpleList) {
        this.m_context = m_context;
        this.m_isRoot = m_isRoot;
        this.simpleList = simpleList;
    }

    @NonNull
    @Override
    public DownloadedFilesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.downloaded_files_elements, parent, false);
        return new DownloadedFilesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadedFilesViewHolder holder, final int position) {
        holder.tvFileNme.setText(simpleList.get(position).getName().substring(0,simpleList.get(position).getName().length()-18));
        holder.tvfileType.setText(simpleList.get(position).getName().substring(simpleList.get(position).getName().length()-18,
                simpleList.get(position).getName().length()-4));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(m_context,OpenDownloadedFiles.class);
                i.putExtra("fileName",simpleList.get(position).getName());
                m_context.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return simpleList.size();
    }

    class DownloadedFilesViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvFileNme,tvfileType;
        public DownloadedFilesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFileNme = itemView.findViewById(R.id.tvPdfPaperName);
            tvfileType = itemView.findViewById(R.id.tvDownloadedfiletype);
        }
    }

    public void setFilter(ArrayList<DownloadedList> m_item) {
        simpleList = new ArrayList<>();
        simpleList.addAll(m_item);
        notifyDataSetChanged();
    }

}
