package com.example.stiqr_java.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stiqr_java.R;
import com.example.stiqr_java.recyclerview.model.LateRecordsModel;

import java.util.List;

public class LateRecordsAdapter extends RecyclerView.Adapter<LateRecordsAdapter.ScheduleHolder> {

    Context context;
    List<LateRecordsModel> lateRecord;
    public LateRecordsAdapter(Context context, List<LateRecordsModel> lateRecord) {
        super();
        this.context = context;
        this.lateRecord = lateRecord;
    }

    @NonNull
    @Override
    public ScheduleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_record, parent, false);
        return  new ScheduleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleHolder holder, int position) {
        holder.tv_date.setText(lateRecord.get(position).getDate());
        holder.tv_lateArrival.setText(lateRecord.get(position).getLateArrival() + " MIN. LATE");
        holder.tv_subject.setText(lateRecord.get(position).getSubject());
        holder.tv_teacher.setText(lateRecord.get(position).getTeacher());
        holder.tv_schedule.setText(lateRecord.get(position).getSchedule());
    }

    @Override
    public int getItemCount() {
        return lateRecord.size();
    }

    public class ScheduleHolder extends RecyclerView.ViewHolder {
        TextView tv_date, tv_lateArrival, tv_teacher, tv_subject, tv_schedule;

        public ScheduleHolder(@NonNull View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_lateArrival = itemView.findViewById(R.id.tv_lateArrival);
            tv_subject = itemView.findViewById(R.id.tv_subject);
            tv_teacher = itemView.findViewById(R.id.tv_teacher);
            tv_schedule = itemView.findViewById(R.id.tv_schedule);
        }
    }
}
