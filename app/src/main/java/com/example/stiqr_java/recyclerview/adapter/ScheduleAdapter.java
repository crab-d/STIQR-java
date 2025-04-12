package com.example.stiqr_java.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stiqr_java.R;
import com.example.stiqr_java.recyclerview.model.ScheduleModel;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleHolder> {
    List<ScheduleModel> schedule;
    Context context;
    public ScheduleAdapter(Context context, List<ScheduleModel> schedule) {
        super();
        this.context = context;
        this.schedule = schedule;
    }

    @NonNull
    @Override
    public ScheduleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_schedule, parent, false);
        return new ScheduleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleHolder holder, int position) {
        holder.tv_subject.setText(schedule.get(position).getSubject());
        holder.tv_time.setText(schedule.get(position).getTime());
        holder.tv_professor.setText(schedule.get(position).getProfessor());
        holder.tv_room.setText(schedule.get(position).getRoom());
    }

    @Override
    public int getItemCount() {
        return schedule.size();
    }

    public static class ScheduleHolder extends RecyclerView.ViewHolder {
        TextView tv_subject, tv_professor, tv_time, tv_room;
        public ScheduleHolder(@NonNull View itemView) {
            super(itemView);
            tv_subject = itemView.findViewById(R.id.tv_subject);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_professor = itemView.findViewById(R.id.tv_professor);
            tv_room = itemView.findViewById(R.id.tv_room);
        }
    }
}
