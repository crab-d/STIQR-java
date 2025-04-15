package com.example.stiqr_java.student;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.stiqr_java.LoginActivity;
import com.example.stiqr_java.R;
import com.example.stiqr_java.dialog.DialogNotif;
import com.example.stiqr_java.firebase.CSRecord;
import com.example.stiqr_java.student.fragment.Record.RecordParentFrag;
import com.example.stiqr_java.student.fragment.StudentHome;
import com.example.stiqr_java.student.fragment.schedule;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.concurrent.atomic.AtomicInteger;

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

        tv_records.setOnClickListener(v -> {
            loadFragment(new RecordParentFrag());
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
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            overridePendingTransition(0, 0);
            startActivity(intent);
            finish();
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


    //not tested
    //Contrac/taskt for each success scan
    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {

        //check if qr has val
        if (result.getContents() != null) {
            String name = getSharedPreferences("STUDENT_SESSION", MODE_PRIVATE).getString("STUDENT_NAME", "NAH");
            String section = getSharedPreferences("STUDENT_SESSION", MODE_PRIVATE).getString("STUDENT_SECTION", "NAH");
            String gradeLevel = getSharedPreferences("STUDENT_SESSION", MODE_PRIVATE).getString("STUDENT_GRADE", "NAH");
            String teacherEmail = result.getContents();
            String studentNumber = getSharedPreferences("STUDENT_SESSION", MODE_PRIVATE).getString("STUDENT_NUMBER", "NAH");
            String sem = getSharedPreferences("STUDENT_SESSION", MODE_PRIVATE).getString("SEMESTER", "sem1");

            DB_LATELOG.readLateRecord(studentNumber, gradeLevel, section, (lateRecord, hi) -> {
                int lateCount = lateRecord.size();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference userDoc = db.collection("users").document(studentNumber);

                //if qr is from admin, who assign student task for cs
                 if (result.getContents().equals("DisciplinaryOfficer")) {

                     userDoc.get().addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {

                            int deductCounter = getSharedPreferences("STUDENT_SESSION", MODE_PRIVATE).getInt("STUDENT_DEDUCT", 0);

                            if (lateCount > deductCounter) {
                                CSRecord DB_CS = new CSRecord(this);
                                int conditional = lateCount - deductCounter;
                                DB_CS.threshold(threshold -> {
                                    if (conditional >= threshold) {
                                        int newCount = deductCounter + threshold;

                                        DB_CS.addCSRecord(name, studentNumber);

                                        getSharedPreferences("STUDENT_SESSION", Context.MODE_PRIVATE).edit().putInt("STUDENT_DEDUCT", newCount).apply();
                                        if (sem.equalsIgnoreCase("sem1")) {
                                            userDoc.update("deductCounter", deductCounter + threshold);
                                        } else {
                                            userDoc.update("deductCounter1", deductCounter + threshold);
                                        }
                                        new Handler().postDelayed(() -> {
                                            loadFragment(new StudentHome());

                                        }, 400);
                                    } else {
                                        DialogNotif.DialogShower(this, "Not eligible for cs");
                                    }
                                });

                            } else {
                                DialogNotif.DialogShower(this, "Not eligible for cs");
                            }
                        }
                     });
                 //if qr from teacher, just add new late record
                 } else {
                     DB_LATELOG.addLateStudent(name, section, gradeLevel, teacherEmail, studentNumber);
                     new Handler().postDelayed(() -> {
                         loadFragment(new StudentHome());

                     }, 1000);
                 }

            });
        }
    });

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame1, fragment);
        fragmentTransaction.commit();
    }
}