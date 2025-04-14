package com.example.stiqr_java.recyclerview.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stiqr_java.R;
import com.example.stiqr_java.recyclerview.model.StudentModel;
import com.example.stiqr_java.staff.StudentInfo;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentHolder> {
    Context context;
    List<StudentModel> student = new ArrayList<>();

    @Override
    public int getItemCount() {
        return student.size();
    }

    @Override
    public void onBindViewHolder(@NonNull StudentHolder holder, int position) {
        holder.tv_name.setText(student.get(position).getName());
        holder.tv_studentNumber.setText(student.get(position).getStudentNumber());
        holder.btn_view.setOnClickListener(v -> {
            Intent intent = new Intent(context, StudentInfo.class);
            intent.putExtra("STUDENT_NUMBER", student.get(position).getStudentNumber());
            intent.putExtra("STUDENT_NAME", student.get(position).getName());
            context.startActivity(intent);
        });

    }

    @NonNull
    @Override
    public StudentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.item_student, parent, false);
        return new StudentHolder(view);
    }

    public StudentAdapter(Context context, List<StudentModel> student) {
        super();
        this.context = context;
        this.student = student;
    }

    public class StudentHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_studentNumber;
        Button btn_view;
        public StudentHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_studentNumber = itemView.findViewById(R.id.tv_studentNumber);
            btn_view = itemView.findViewById(R.id.btn_view);
        }
    };
}
