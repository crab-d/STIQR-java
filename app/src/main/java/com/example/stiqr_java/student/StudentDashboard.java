package com.example.stiqr_java.student;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.stiqr_java.MainActivity;
import com.example.stiqr_java.R;
import com.example.stiqr_java.student.fragment.LateRecord;
import com.example.stiqr_java.student.fragment.StudentHome;
import com.example.stiqr_java.student.fragment.schedule;

public class StudentDashboard extends AppCompatActivity {
    TextView tv_home, tv_schedule, tv_records, tv_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_dashboard);
        tv_home = findViewById(R.id.tv_home);
        tv_schedule = findViewById(R.id.tv_schedule);
        tv_records = findViewById(R.id.tv_records);
        tv_logout = findViewById(R.id.tv_logout);

        String name = getSharedPreferences("STUDENT_SESSION", MODE_PRIVATE).getString("STUDENT_NAME", "nAH");
        Toast.makeText(this, "name" + name, Toast.LENGTH_SHORT).show();

        tv_records.setOnClickListener(v -> {
            loadFragment(new LateRecord());
        });

        tv_home.setOnClickListener(v -> {
            loadFragment(new StudentHome());
        });

        tv_schedule.setOnClickListener(v -> {
            loadFragment(new schedule());
        });

        loadFragment(new StudentHome());

        tv_logout.setOnClickListener(v -> {
            getSharedPreferences("STUDENT_SESSION", MODE_PRIVATE).edit().clear().apply();
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            overridePendingTransition(0,0);
            startActivity(intent);
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }
}