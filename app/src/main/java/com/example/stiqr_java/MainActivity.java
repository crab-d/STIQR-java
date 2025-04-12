package com.example.stiqr_java;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stiqr_java.firebase.LoginVerification;
import com.example.stiqr_java.student.StudentDashboard;
import com.example.stiqr_java.teacher.TeacherDashboard;

public class MainActivity extends AppCompatActivity {
    EditText et_email, et_password;
    Button btn_login, btn_signup;
    LoginVerification DB = new LoginVerification(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        boolean studentLoggedIn = getSharedPreferences("STUDENT_SESSION", MODE_PRIVATE).getBoolean("STUDENT_LOG", false);
        boolean teacherLoggedIn = getSharedPreferences("TEACHER_SESSION", MODE_PRIVATE).getBoolean("TEACHER_LOG", false);

        if (studentLoggedIn) {
            Intent intent = new Intent(this, StudentDashboard.class);
            startActivity(intent);
            finish();
        } else if (teacherLoggedIn) {
            Intent intent = new Intent(this, TeacherDashboard.class);
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(v -> {
            String email = et_email.getText().toString().toLowerCase().trim();
            String password = et_password.getText().toString().trim();
            DB.loginAuthentication(email, password, MainActivity.this);
        });
    }
}