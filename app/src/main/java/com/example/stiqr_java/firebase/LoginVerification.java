package com.example.stiqr_java.firebase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.stiqr_java.staff.StaffDashboard;
import com.example.stiqr_java.student.StudentDashboard;
import com.example.stiqr_java.teacher.TeacherDashboard;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginVerification {
    Context context;

    public LoginVerification(Context context) {
        this.context = context;
    }

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public void loginAuthentication(String email, String password, Activity activity) {



        db.collection("users").whereEqualTo("email", email).whereEqualTo("password", password).get().addOnSuccessListener(query -> {
            if (!query.isEmpty()) {
                DocumentSnapshot snapshot = query.getDocuments().get(0);
                String name = snapshot.getString("name");
                String id = snapshot.getString("studentNumber");
                String section = snapshot.getString("section");
                String gradeLevel = snapshot.getString("gradeLevel");
                String deductor = snapshot.getString("deducCounter");
                context.getSharedPreferences("STUDENT_SESSION", context.MODE_PRIVATE).edit()
                        .putString("STUDENT_NAME", name)
                        .putString("STUDENT_EMAIL", email)
                        .putString("STUDENT_NUMBER", id)
                        .putString("STUDENT_SECTION", section)
                        .putString("STUDENT_GRADE", gradeLevel)
                        .putString("STUDENT_DEDUCTOR", deductor)
                        .putBoolean("STUDENT_LOG", true)
                        .apply();

                Intent intent = new Intent(context, StudentDashboard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
                activity.finish();
            } else {
                db.collection("teachers").whereEqualTo("email", email).whereEqualTo("password", password).get().addOnSuccessListener(query1 -> {
                    if (!query1.isEmpty()) {
                        DocumentSnapshot snapshot1 = query1.getDocuments().get(0);
                        String name = snapshot1.getString("name");
                        String codename = snapshot1.getString("codename");
                        context.getSharedPreferences("TEACHER_SESSION", context.MODE_PRIVATE).edit()
                                .putString("TEACHER_NAME", name)
                                .putString("TEACHER_EMAIL", email)
                                .putString("TEACHER_CODENAME", codename)
                                .putBoolean("TEACHER_LOG", true)
                                .apply();
                        Intent intent = new Intent(context, TeacherDashboard.class);
                        context.startActivity(intent);
                        activity.finish();
                    } else {
                        Toast.makeText(context, "Invalid credentials. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        db.collection("staff").whereEqualTo("email", email).whereEqualTo("password", password).get().addOnSuccessListener(query -> {
            if (!query.isEmpty()) {
                context.getSharedPreferences("STAFF_SESSION", Context.MODE_PRIVATE).edit()
                        .putBoolean("STAFF_LOG", true)
                        .apply();
                Intent intent = new Intent(context, StaffDashboard.class);
                context.startActivity(intent);
                activity.finish();
            }
        });
    }

}
