package com.example.stiqr_java.firebase;

import android.content.Context;

import com.example.stiqr_java.recyclerview.model.StudentModel;
import com.example.stiqr_java.staff.StudentInfo;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class StudentRecord {
    Context context;

    public StudentRecord(Context context) {
        this.context = context;
    }

    public void searchStudent(String studentNumber, studentCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(studentNumber).get().addOnSuccessListener(QueryDocumentSnapshot -> {
           List<StudentModel> student = new ArrayList<>();
            if (QueryDocumentSnapshot.exists()) {
               String name = QueryDocumentSnapshot.getString("name");
               String section = QueryDocumentSnapshot.getString("section");
               String gradeLevel = QueryDocumentSnapshot.getString("gradeLevel");
               student.add(new StudentModel(name, studentNumber, section, gradeLevel));
           }
            callback.onCallback(student);
        });

    }

    public interface studentCallback {
        void onCallback(List<StudentModel> student);
    }
}
