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

import java.util.concurrent.atomic.AtomicReference;

public class LoginVerification {
    Context context;

    public LoginVerification(Context context) {
        this.context = context;
    }

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void loginAuthentication(String email, String password, Activity activity) {

        db.collection("staff").whereEqualTo("email", email).whereEqualTo("password", password).get().addOnSuccessListener(query -> {
            if (!query.isEmpty()) {
                context.getSharedPreferences("STAFF_SESSION", Context.MODE_PRIVATE).edit()
                        .putBoolean("STAFF_LOG", true)
                        .apply();
                Intent intent = new Intent(context, StaffDashboard.class);
                context.startActivity(intent);
                activity.finish();
            } else {
                db.collection("users").whereEqualTo("email", email).whereEqualTo("password", password).get().addOnSuccessListener(query1 -> {

                    if (!query1.isEmpty()) {
                        DocumentSnapshot snapshot = query1.getDocuments().get(0);
                        String name = snapshot.getString("name");
                        String id = snapshot.getString("studentNumber");

                        semester(sem -> {
                            String section;
                            if (sem.equalsIgnoreCase("sem1")) {
                                section = snapshot.getString("section");
                            } else {
                                section = snapshot.getString("section1");

                            }
                            String gradeLevel = snapshot.getString("gradeLevel");
                            Long deductor1 = snapshot.getLong("deductCounter");
                            int deductor = deductor1 != null ? deductor1.intValue() : 0;

                            context.getSharedPreferences("STUDENT_SESSION", context.MODE_PRIVATE).edit()
                                    .putString("STUDENT_NAME", name)
                                    .putString("STUDENT_EMAIL", email)
                                    .putString("STUDENT_NUMBER", id)
                                    .putString("STUDENT_SECTION", section)
                                    .putString("STUDENT_GRADE", gradeLevel)
                                    .putInt("STUDENT_DEDUCT", deductor)
                                    .putString("SEMESTER", sem)
                                    .putBoolean("STUDENT_LOG", true)
                                    .apply();

                            Intent intent = new Intent(context, StudentDashboard.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            context.startActivity(intent);
                            activity.finish();
                        });

                    } else {
                        db.collection("teachers").whereEqualTo("email", email).whereEqualTo("password", password).get().addOnSuccessListener(query2 -> {
                            if (!query2.isEmpty()) {
                                DocumentSnapshot snapshot1 = query2.getDocuments().get(0);
                                String name = snapshot1.getString("name");
                                String codename = snapshot1.getString("codename");
                                String docName = snapshot1.getId();
                                context.getSharedPreferences("TEACHER_SESSION", context.MODE_PRIVATE).edit()
                                        .putString("TEACHER_NAME", name)
                                        .putString("TEACHER_EMAIL", email)
                                        .putString("TEACHER_CODENAME", codename)
                                        .putString("DOCNAME", docName)
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


            }
        });




    }

    public void semester(semCallback CB_sem) {
        db.collection("term").document("semester").get()
                .addOnSuccessListener(DocumentSnapshot -> {
                    String sem = DocumentSnapshot.getString("sem");
                    CB_sem.onCallBack(sem);
                });
    }

    public interface semCallback {
        void onCallBack(String sem);
    }

}
