package com.example.stiqr_java.recyclerview.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stiqr_java.R;
import com.example.stiqr_java.firebase.CSRecord;
import com.example.stiqr_java.recyclerview.model.StaffCSOnGoingModel;

import java.util.List;

public class StaffCSOnGoingAdapter extends RecyclerView.Adapter<StaffCSOnGoingAdapter.StaffCSOnGoingHolder> {

    Context context;
    List<StaffCSOnGoingModel> CSOnGoing;
    public StaffCSOnGoingAdapter(Context context, List<StaffCSOnGoingModel> CSOnGoing) {
        super();
        this.context = context;
        this.CSOnGoing = CSOnGoing;
    }

    @NonNull
    @Override
    public StaffCSOnGoingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_staff_cs_record, parent, false);
        return new StaffCSOnGoingHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StaffCSOnGoingHolder holder, int position) {
        String reference = CSOnGoing.get(position).getReference();
        String studentNumber = CSOnGoing.get(position).getStudentNumber();
        String name = CSOnGoing.get(position).getName();

        holder.tv_reference.setText("REFERENCE: " + reference);
        holder.tv_studentNumber.setText("STUDENT NUMBER: " + studentNumber);
        holder.tv_name.setText("STUDENT NAME: " + name);
        holder.tv_task.setText("TASK: " + CSOnGoing.get(position).getTask());
        holder.tv_status.setText("STATUS: " + CSOnGoing.get(position).getStatus());
        holder.tv_complete.setText("COMPLETE DATE: " + CSOnGoing.get(position).getDate());
        holder.itemView.setOnClickListener(v -> {
            Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_confirm_complete);
            Button btn_yes = dialog.findViewById(R.id.btn_yes);
            Button btn_no = dialog.findViewById(R.id.btn_no);
            CSRecord DB_CS = new CSRecord(context);
            btn_yes.setOnClickListener(v1 -> {
                DB_CS.updateCompleteCS(reference);
                dialog.dismiss();
            });

            btn_no.setOnClickListener(v1 -> {
                dialog.dismiss();
            });
            dialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return CSOnGoing.size();
    }

    public class StaffCSOnGoingHolder extends RecyclerView.ViewHolder {
        TextView tv_reference, tv_studentNumber, tv_name, tv_status, tv_task, tv_complete;

        public StaffCSOnGoingHolder(@NonNull View itemView) {
            super(itemView);
            tv_reference = itemView.findViewById(R.id.tv_reference);
            tv_studentNumber = itemView.findViewById(R.id.tv_studentNumber);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_status = itemView.findViewById(R.id.tv_status);
            tv_task = itemView.findViewById(R.id.tv_task);
            tv_complete = itemView.findViewById(R.id.tv_date);
        }
    }
}
