package com.example.stiqr_java.recyclerview.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stiqr_java.R;
import com.example.stiqr_java.firebase.CSRecord;
import com.example.stiqr_java.recyclerview.model.StaffCSRecordModel;
import com.example.stiqr_java.staff.StaffDashboard;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class StaffCSRecordsAdapter extends RecyclerView.Adapter<StaffCSRecordsAdapter.staffCSRecordsHolder> {
    Context context;
    List<StaffCSRecordModel> record;
    Activity activity;
    public StaffCSRecordsAdapter(Context context, List<StaffCSRecordModel> record, Activity activity) {
        super();
        this.context = context;
        this.record = record;
        this.activity = activity;
    }

    @Override
    public int getItemCount() {
        return record.size();
    }

    @Override
    public void onBindViewHolder(@NonNull staffCSRecordsHolder holder, int position) {
        String reference = record.get(position).getReference();
        String studentNumber = record.get(position).getStudentNumber();
        String name = record.get(position).getName();

        holder.tv_reference.setText("REFERENCE: " + reference);
        holder.tv_studentNumber.setText("STUDENT NUMBER: " + studentNumber);
        holder.tv_name.setText("STUDENT NAME: " + name);
        holder.tv_task.setText("TASK: " + record.get(position).getTask());
        holder.tv_status.setText("STATUS: " + record.get(position).getStatus());
        holder.tv_complete.setText("COMPLETE DATE: " + record.get(position).getDate());
        holder.itemView.setOnClickListener(v -> {
            Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_assigning);
            CSRecord DB_RECORD = new CSRecord(context);

            TextView tv_name = dialog.findViewById(R.id.tv_name);
            TextView tv_studentNumber = dialog.findViewById(R.id.tv_studentNumber);
            TextView tv_reference = dialog.findViewById(R.id.tv_reference);
            EditText et_task = dialog.findViewById(R.id.et_task);
            Button btn_assign = dialog.findViewById(R.id.btn_assign);

            tv_studentNumber.setText("STUDENT NUMBER: " + studentNumber);
            tv_name.setText("STUDENT NAME: " + name);
            tv_reference.setText("REFERENCE: " + reference);

            btn_assign.setOnClickListener(v1 -> {
                String task = et_task.getText().toString().trim();

                if (task.isEmpty()) {
                    et_task.setError("assign task to student");
                    return;
                }

                CSRecord DB_CS = new CSRecord(context);
                DB_CS.assignStudent(record.get(position).getReference(), task);
                Intent intent = new Intent(context, StaffDashboard.class);
                context.startActivity(intent);
                activity.finish();
                dialog.dismiss();
            });
            dialog.show();
        });
    }

    @NonNull
    @Override
    public staffCSRecordsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_staff_cs_record, parent, false);
        return new staffCSRecordsHolder(view);
    }

    public class staffCSRecordsHolder extends RecyclerView.ViewHolder {
        TextView tv_reference, tv_studentNumber, tv_name, tv_status, tv_task, tv_complete;
        public staffCSRecordsHolder(@NonNull View itemView) {
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
