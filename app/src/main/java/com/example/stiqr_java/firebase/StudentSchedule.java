package com.example.stiqr_java.firebase;

import android.content.Context;

import com.example.stiqr_java.recyclerview.adapter.ScheduleAdapter;
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

    public void schedMonday(String gradelevel, String section, ScheduleCallBack callback) {
        List<ScheduleModel> schedule = new ArrayList<>();
        db.collection("schedule").document("shs").collection(gradelevel).document(section).collection("monday").get()
                .addOnSuccessListener(query -> {
                    if (!query.isEmpty()) {
                        for (DocumentSnapshot snapshot : query.getDocuments()) {
                            String subject = snapshot.getString("subject");
                            String professor = snapshot.getString("professor");
                            String time = snapshot.getString("time");
                            String room = snapshot.getString("room");
                            schedule.add(new ScheduleModel(subject, professor, time, room));
                        }
                    } else {
                        schedule.add(new ScheduleModel("NO SCHEDULE", "or database isn't updated", "MONDAY", ""));
                    }
                    callback.onScheduleLoaded(schedule);
                });
    }

    public void schedTuesday(String gradelevel, String section, ScheduleCallBack callback) {
        List<ScheduleModel> schedule = new ArrayList<>();
        db.collection("schedule").document("shs").collection(gradelevel).document(section).collection("tuesday").get()
                .addOnSuccessListener(query -> {
                    if (!query.isEmpty()) {
                        for (DocumentSnapshot snapshot : query.getDocuments()) {
                            String subject = snapshot.getString("subject");
                            String professor = snapshot.getString("professor");
                            String time = snapshot.getString("time");
                            String room = snapshot.getString("room");
                            schedule.add(new ScheduleModel(subject, professor, time, room));
                        }
                    } else {
                        schedule.add(new ScheduleModel("NO SCHEDULE", "or database isn't updated", "TUESDAY", ""));
                    }
                    callback.onScheduleLoaded(schedule);
                });
    }

    public void schedWednesday(String gradelevel, String section, ScheduleCallBack callback) {
        List<ScheduleModel> schedule = new ArrayList<>();
        db.collection("schedule").document("shs").collection(gradelevel).document(section).collection("wednesday").get()
                .addOnSuccessListener(query -> {
                    if (!query.isEmpty()) {
                        for (DocumentSnapshot snapshot : query.getDocuments()) {
                            String subject = snapshot.getString("subject");
                            String professor = snapshot.getString("professor");
                            String time = snapshot.getString("time");
                            String room = snapshot.getString("room");
                            schedule.add(new ScheduleModel(subject, professor, time, room));
                        }
                    } else {
                        schedule.add(new ScheduleModel("NO SCHEDULE", "or database isn't updated", "WEDNESDAY", ""));
                    }
                    callback.onScheduleLoaded(schedule);
                });
    }

    public void schedThursday(String gradelevel, String section, ScheduleCallBack callback) {
        List<ScheduleModel> schedule = new ArrayList<>();
        db.collection("schedule").document("shs").collection(gradelevel).document(section).collection("thursday").get()
                .addOnSuccessListener(query -> {
                    if (!query.isEmpty()) {
                        for (DocumentSnapshot snapshot : query.getDocuments()) {
                            String subject = snapshot.getString("subject");
                            String professor = snapshot.getString("professor");
                            String time = snapshot.getString("time");
                            String room = snapshot.getString("room");
                            schedule.add(new ScheduleModel(subject, professor, time, room));
                        }
                    } else {
                        schedule.add(new ScheduleModel("NO SCHEDULE", "or database isn't updated", "THURSDAY", ""));
                    }
                    callback.onScheduleLoaded(schedule);
                });
    }

    public void schedFriday(String gradelevel, String section, ScheduleCallBack callback) {
        List<ScheduleModel> schedule = new ArrayList<>();
        db.collection("schedule").document("shs").collection(gradelevel).document(section).collection("friday").get()
                .addOnSuccessListener(query -> {
                    if (!query.isEmpty()) {
                        for (DocumentSnapshot snapshot : query.getDocuments()) {
                            String subject = snapshot.getString("subject");
                            String professor = snapshot.getString("professor");
                            String time = snapshot.getString("time");
                            String room = snapshot.getString("room");
                            schedule.add(new ScheduleModel(subject, professor, time, room));
                        }
                    } else {
                        schedule.add(new ScheduleModel("NO SCHEDULE", "or database isn't updated", "FRIDAY", ""));
                    }
                    callback.onScheduleLoaded(schedule);
                });
    }

    public void schedSaturday(String gradelevel, String section, ScheduleCallBack callback) {
        List<ScheduleModel> schedule = new ArrayList<>();
        db.collection("schedule").document("shs").collection(gradelevel).document(section).collection("saturday").get()
                .addOnSuccessListener(query -> {
                    if (!query.isEmpty()) {
                        for (DocumentSnapshot snapshot : query.getDocuments()) {
                            String subject = snapshot.getString("subject");
                            String professor = snapshot.getString("professor");
                            String time = snapshot.getString("time");
                            String room = snapshot.getString("room");
                            schedule.add(new ScheduleModel(subject, professor, time, room));
                        }
                    } else {
                        schedule.add(new ScheduleModel("NO SCHEDULE", "or database isn't updated", "SATURDAY", ""));
                    }
                    callback.onScheduleLoaded(schedule);
                });
    }

    public interface ScheduleCallBack {
        void onScheduleLoaded(List<ScheduleModel> schedule);
    }

}
