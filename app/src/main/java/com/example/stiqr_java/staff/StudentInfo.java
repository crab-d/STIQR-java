package com.example.stiqr_java.staff;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stiqr_java.R;
import com.example.stiqr_java.firebase.CSRecord;
import com.example.stiqr_java.recyclerview.adapter.CSRecordsAdapter;
import com.example.stiqr_java.recyclerview.adapter.StaffCSOnGoingAdapter;
import com.example.stiqr_java.recyclerview.adapter.StaffCSRecordsAdapter;

public class StudentInfo extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_info);
        String studentNumber = getIntent().getStringExtra("STUDENT_NUMBER");
        String name = getIntent().getStringExtra("STUDENT_NAME");
        RecyclerView rv_record = findViewById(R.id.rv_record);
        TextView tv_csTotal = findViewById(R.id.tv_csTotal);
        TextView tv_csPending = findViewById(R.id.tv_csPending);
        TextView tv_csOnGoing = findViewById(R.id.tv_csOnGoing);
        TextView tv_csComplete = findViewById(R.id.tv_csComplete);
        TextView tv_pending = findViewById(R.id.tv_pending);
        TextView tv_OnGoing = findViewById(R.id.tv_onGoing);
        Button btn_back = findViewById(R.id.btn_back);
        @SuppressLint("CutPasteId") TextView tv_complete = findViewById(R.id.tv_complete);
        @SuppressLint("MissingInflatedId") TextView tv_name = findViewById(R.id.tv_name);
        @SuppressLint("MissingInflatedId") TextView tv_StudentNumber = findViewById(R.id.tv_studentNumber);

        tv_name.setText("NAME: " + name);
        tv_StudentNumber.setText("STUDENT NUMBER: " + studentNumber);

        btn_back.setOnClickListener(v -> {
            finish();
        });

        CSRecord DB_CS = new CSRecord(this);
        rv_record.setLayoutManager(new LinearLayoutManager(this));

        DB_CS.readStudentCS(studentNumber, total -> {
            tv_csTotal.setText(String.valueOf(total.size()));
        });

        DB_CS.readPendingCS(studentNumber, total -> {
            tv_csPending.setText(String.valueOf(total.size()));
        });

        DB_CS.readOnGoingCS(studentNumber, total -> {
            tv_csOnGoing.setText(String.valueOf(total.size()));
        });

        DB_CS.readCompleteCS(studentNumber, total -> {
            tv_csComplete.setText(String.valueOf(total.size()));
        });


        tv_pending.setOnClickListener(v -> {
            DB_CS.readStaffPendingCS(studentNumber, pending -> {
                rv_record.setAdapter(new StaffCSRecordsAdapter(this, pending));
            });
            tv_OnGoing.setTextColor(Color.parseColor("#3283FF"));
            tv_OnGoing.setBackgroundColor(Color.parseColor("#FFFFFF"));
            tv_pending.setTextColor(Color.parseColor("#FFFFFF"));
            tv_pending.setBackgroundColor(Color.parseColor("#3283FF"));
            tv_complete.setTextColor(Color.parseColor("#3283FF"));
            tv_complete.setBackgroundColor(Color.parseColor("#FFFFFF"));
        });

        tv_OnGoing.setOnClickListener(v -> {
            DB_CS.readStaffOnGoingCS(studentNumber, onGoing -> {
                rv_record.setAdapter(new StaffCSOnGoingAdapter(this, onGoing));
            });
            tv_OnGoing.setTextColor(Color.parseColor("#FFFFFF"));
            tv_OnGoing.setBackgroundColor(Color.parseColor("#3283FF"));
            tv_pending.setTextColor(Color.parseColor("#3283FF"));
            tv_pending.setBackgroundColor(Color.parseColor("#FFFFFF"));
            tv_complete.setTextColor(Color.parseColor("#3283FF"));
            tv_complete.setBackgroundColor(Color.parseColor("#FFFFFF"));
        });

        tv_complete.setOnClickListener(v -> {
            DB_CS.readCompleteCS(studentNumber, complete -> {
                rv_record.setAdapter(new CSRecordsAdapter(this, complete));
            });
            tv_OnGoing.setTextColor(Color.parseColor("#3283FF"));
            tv_OnGoing.setBackgroundColor(Color.parseColor("#FFFFFF"));
            tv_pending.setTextColor(Color.parseColor("#3283FF"));
            tv_pending.setBackgroundColor(Color.parseColor("#FFFFFF"));
            tv_complete.setTextColor(Color.parseColor("#FFFFFF"));
            tv_complete.setBackgroundColor(Color.parseColor("#3283FF"));
        });

        DB_CS.readStaffPendingCS(studentNumber, pending -> {
            rv_record.setAdapter(new StaffCSRecordsAdapter(this, pending));
        });

    }
}