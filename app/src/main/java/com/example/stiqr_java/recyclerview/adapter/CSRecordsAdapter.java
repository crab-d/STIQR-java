package com.example.stiqr_java.recyclerview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stiqr_java.R;
import com.example.stiqr_java.recyclerview.model.CSModel;

import java.util.List;

public class CSRecordsAdapter extends RecyclerView.Adapter<CSRecordsAdapter.CSRecordsHolder> {
    Context context;
    List<CSModel> CSRecord;
    public CSRecordsAdapter(Context context, List<CSModel> CSRecord) {
        super();
        this.context = context;
        this.CSRecord = CSRecord;
    }

    @NonNull
    @Override
    public CSRecordsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cs_record, parent, false);
        return new CSRecordsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CSRecordsHolder holder, int position) {
        String status = CSRecord.get(position).getStatus();
        holder.tv_reference.setText("REFERENCE: " + CSRecord.get(position).getReference());
        holder.tv_status.setText("STATUS: " + status);
        if (status.equals("To be process")) {
            holder.tv_status.setTextColor(Color.parseColor("#D76E03"));
        } else if (status.equals("on going")) {
            holder.tv_status.setTextColor(Color.parseColor("#3283FF"));
        } else if (status.equals("complete")) {
            holder.tv_status.setTextColor(Color.parseColor("#3CFF28"));
        }
        holder.tv_task.setText("TASK: " + CSRecord.get(position).getTask());
        holder.tv_date.setText("DATE COMPLIED: " + CSRecord.get(position).getCompleteDate());
    }

    @Override
    public int getItemCount() {
        return CSRecord.size();
    }

    public class CSRecordsHolder extends RecyclerView.ViewHolder {
        TextView tv_status, tv_date, tv_reference, tv_task;

        public CSRecordsHolder(@NonNull View itemView) {
            super(itemView);
            tv_status = itemView.findViewById(R.id.tv_status);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_reference = itemView.findViewById(R.id.tv_reference);
            tv_task = itemView.findViewById(R.id.tv_task);
        }
    }
}
