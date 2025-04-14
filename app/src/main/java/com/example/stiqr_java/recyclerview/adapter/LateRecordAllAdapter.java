package com.example.stiqr_java.recyclerview.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stiqr_java.R;
import com.example.stiqr_java.recyclerview.model.LateRecordAllModel;

import java.util.List;

public class LateRecordAllAdapter extends RecyclerView.Adapter<LateRecordAllAdapter.LateRecordAllHolder> {
    Context context;
    List<LateRecordAllModel> LateRecordAll;
    public LateRecordAllAdapter(Context context,  List<LateRecordAllModel> LateRecordAll) {
        super();
        this.context = context;
        this.LateRecordAll = LateRecordAll;
    }

    @NonNull
    @Override
    public LateRecordAllHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_teacher_late, parent, false);
        return new LateRecordAllHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull LateRecordAllHolder holder, int position) {
        holder.tv_lateArrival.setText(LateRecordAll.get(position).getLateArrival() + " MINS LATE");
        holder.tv_date.setText(LateRecordAll.get(position).getDate() );
        holder.tv_subject.setText(LateRecordAll.get(position).getSubject() );
        holder.tv_schedule.setText(LateRecordAll.get(position).getSchedule());
        holder.tv_name.setText(LateRecordAll.get(position).getName());
        holder.tv_section.setText(LateRecordAll.get(position).getSection());
    }

    @Override
    public int getItemCount() {
        return LateRecordAll.size();
    }

    public class LateRecordAllHolder extends RecyclerView.ViewHolder{
        TextView tv_lateArrival, tv_date, tv_subject, tv_schedule, tv_name, tv_section;
        public LateRecordAllHolder(@NonNull View itemView) {
            super(itemView);
            tv_lateArrival = itemView.findViewById(R.id.tv_lateArrival);
            tv_date= itemView.findViewById(R.id.tv_date);
            tv_subject = itemView.findViewById(R.id.tv_subject);
            tv_schedule = itemView.findViewById(R.id.tv_schedule);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_section = itemView.findViewById(R.id.tv_section);
        }
    }
}
