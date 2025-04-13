package com.example.stiqr_java;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stiqr_java.firebase.LoginVerification;
import com.example.stiqr_java.staff.StaffDashboard;
import com.example.stiqr_java.student.StudentDashboard;
import com.example.stiqr_java.teacher.TeacherDashboard;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    EditText et_email, et_password;
    Button btn_login, btn_signup;
    LoginVerification DB = new LoginVerification(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        boolean studentLoggedIn = getSharedPreferences("STUDENT_SESSION", MODE_PRIVATE).getBoolean("STUDENT_LOG", false);
        boolean teacherLoggedIn = getSharedPreferences("TEACHER_SESSION", MODE_PRIVATE).getBoolean("TEACHER_LOG", false);
        boolean staffLoggedIn = getSharedPreferences("STAFF_SESSION", MODE_PRIVATE).getBoolean("STAFF_LOG", false);

        if (studentLoggedIn) {
            Intent intent = new Intent(this, StudentDashboard.class);
            startActivity(intent);
            finish();
        } else if (teacherLoggedIn) {
            Intent intent = new Intent(this, TeacherDashboard.class);
            startActivity(intent);
            finish();
        } else if (staffLoggedIn) {
            Intent intent = new Intent(this, StaffDashboard.class);
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        btn_signup = findViewById(R.id.btn_signup);

        btn_signup.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignupActivity.class);
            overridePendingTransition(0,0);
            startActivity(intent);
        });

        btn_login.setOnClickListener(v -> {
            String email = et_email.getText().toString().toLowerCase().trim();
            String password = et_password.getText().toString().trim();

            if (email.isEmpty()) {
                et_email.setError("Email is required");
                return;
            } else if (!Pattern.matches("^[a-zA-Z0-9._]+@cubao\\.sti\\.edu\\.ph$", email)) {
                et_email.setError("Only email from sti cubao are allowed");
                return;
            }

            if (password.isEmpty()) {
                et_password.setError("Password is required");
                return;
            }
            DB.loginAuthentication(email, password, LoginActivity.this);
        });
    }
}