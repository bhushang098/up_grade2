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

public class LinksAdapter extends RecyclerView.Adapter<LinksAdapter.ListHolder> {

    ArrayList<LinksInDB> linkList;
    Context context;

    public LinksAdapter(ArrayList<LinksInDB> linkList, Context context) {
        this.linkList = linkList;
        this.context = context;
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.links_items,parent,false);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, final int position) {
        holder.tvLinkName.setText( linkList.get(position).getLinkName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,GetLinksDetails.class);
                i.putExtra("url",linkList.get(position).getLInk());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return linkList.size();
    }

    class ListHolder extends RecyclerView.ViewHolder{
        TextView tvLinkName;

        public ListHolder(@NonNull View itemView) {
            super(itemView);
           tvLinkName =  itemView.findViewById(R.id.tvLinkNameFromDB);
        }
    }
}
