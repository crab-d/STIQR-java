package com.example.stiqr_java.student;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.stiqr_java.MainActivity;
import com.example.stiqr_java.R;
import com.example.stiqr_java.student.fragment.LateRecord;
import com.example.stiqr_java.student.fragment.StudentHome;
import com.example.stiqr_java.student.fragment.schedule;
import com.google.zxing.integration.android.IntentIntegrator;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class StudentDashboard extends AppCompatActivity {
    TextView tv_home, tv_schedule, tv_records, tv_logout, tv_scan;
    com.example.stiqr_java.firebase.LateRecord DB_LATELOG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_dashboard);
        tv_home = findViewById(R.id.tv_home);
        tv_schedule = findViewById(R.id.tv_schedule);
        tv_records = findViewById(R.id.tv_records);
        tv_logout = findViewById(R.id.tv_logout);
        tv_scan = findViewById(R.id.tv_scan);
        DB_LATELOG = new com.example.stiqr_java.firebase.LateRecord(this);

        String gradeLevel = getSharedPreferences("STUDENT_SESSION", MODE_PRIVATE).getString("STUDENT_GRADE", "nAH");
        Toast.makeText(this, "grade" + gradeLevel, Toast.LENGTH_SHORT).show();

        tv_records.setOnClickListener(v -> {
            loadFragment(new LateRecord());
        });

        tv_home.setOnClickListener(v -> {
            loadFragment(new StudentHome());
        });

        tv_scan.setOnClickListener(v -> {
            scanCode();
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

    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("VOLUME UP TO TURN FLASHLIGHT ON");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureActivity.class);
        barLauncher.launch(options);
    }


    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
       if (result.getContents() != null) {
           String name = getSharedPreferences("STUDENT_SESSION", MODE_PRIVATE).getString("STUDENT_NAME", "NAH");
           String section = getSharedPreferences("STUDENT_SESSION", MODE_PRIVATE).getString("STUDENT_SECTION", "NAH");
           String gradeLevel = getSharedPreferences("STUDENT_SESSION", MODE_PRIVATE).getString("STUDENT_GRADE", "NAH");
           String teacherEmail = result.getContents();
           String studentNumber = getSharedPreferences("STUDENT_SESSION", MODE_PRIVATE).getString("STUDENT_NUMBER", "NAH");

           DB_LATELOG.addLateStudent(name, section, gradeLevel, teacherEmail, studentNumber);
           Toast.makeText(this, "res: " + result.getContents() , Toast.LENGTH_SHORT).show();
       }
    });

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }
}