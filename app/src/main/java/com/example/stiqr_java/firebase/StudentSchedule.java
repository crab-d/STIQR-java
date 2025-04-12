package com.example.stiqr_java.firebase;

import android.content.Context;
import android.widget.Toast;

import com.example.stiqr_java.recyclerview.model.ScheduleModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class StudentSchedule {
    Context context;

    public StudentSchedule(Context context) {
        this.context = context;
    }

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public List<ScheduleModel> schedMonday(String gradelevel) {
        List<ScheduleModel> schedule = new ArrayList<>();
        db.collection("schedule").document("shs").collection(gradelevel).document("monday").get()
                .addOnSuccessListener(query -> {
                    if (!query.exists()) {
                        String subject = query.getString("subject");
                        String professor = query.getString("professor");
                        String time = query.getString("time");
                        String room = query.getString("room");
                        schedule.add(new ScheduleModel(subject, professor, time, room));
                    } else {
                        Toast.makeText(context, "NO schedule for monday", Toast.LENGTH_SHORT).show();
                    }
                });
        return schedule;
    }
}
