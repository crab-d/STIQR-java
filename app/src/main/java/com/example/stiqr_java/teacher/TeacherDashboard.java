package com.example.stiqr_java.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.stiqr_java.LoginActivity;
import com.example.stiqr_java.R;
import com.example.stiqr_java.teacher.fragment.FragRecord;
import com.example.stiqr_java.teacher.fragment.TeacherHome;

public class TeacherDashboard extends AppCompatActivity {
    TextView tv_logout, tv_records, tv_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_teacher_dashboard);
        tv_logout = findViewById(R.id.tv_logout);
        tv_records = findViewById(R.id.tv_records);
        tv_home = findViewById(R.id.tv_home);
        tv_logout.setOnClickListener(v -> {
            getSharedPreferences("TEACHER_SESSION", MODE_PRIVATE).edit().clear().commit();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(0,0);
            finish();
        });

        tv_home.setOnClickListener(v -> {
            loadFragment(new TeacherHome());
        });

        tv_records.setOnClickListener(v -> {
            loadFragment(new FragRecord());
        });

        loadFragment(new TeacherHome());
    }

    public  void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }
}