package com.example.stiqr_java.firebase;

import android.content.Context;

import com.example.stiqr_java.recyclerview.adapter.ScheduleAdapter;
import com.example.stiqr_java.recyclerview.model.ScheduleModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentSchedule {
    Context context;

    public StudentSchedule(Context context) {
        this.context = context;
    }

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    //ADD TEST SCHEDULE FOR EACHE SECTION
    public void _addTestSection () {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference grade11 = db.collection("schedule").document("shs").collection("grade11");
        CollectionReference grade12 = db.collection("schedule").document("shs").collection("grade12");

        ArrayList<String> day = new ArrayList<>();
        day.add("monday");
        day.add("tuesday");
        day.add("wednesday");
        day.add("thursday");
        day.add("friday");
        day.add("saturday");

        Map<String, Object> testSection = new HashMap<>();
        testSection.put("professor", "TESTER");
        testSection.put("room", "101");
        testSection.put("subject", "TEST");
        testSection.put("teacherEmail", "test.teacher@cubao.sti.edu.ph");
        testSection.put("time", "12:01 AM - 11:59 PM");
        testSection.put("time1", "00:01");
        testSection.put("time2", "23:59");

        Map<String, Object> InitalField = new HashMap<>();
        InitalField.put("a", "a");

        grade11.get().addOnSuccessListener(QueryDocumentSnapshot -> {
           if (!QueryDocumentSnapshot.isEmpty()) {
               for (DocumentSnapshot document : QueryDocumentSnapshot) {
                   String section = document.getId();
                   db.collection("teachers").document("test").collection("class").document(section).set(InitalField);
//                   for (int i = 0; i < day.size(); i++) {
//                       grade11.document(section).collection(day.get(i)).document("testSub").set(testSection);
//                   }
               }
           }
        });

        grade12.get().addOnSuccessListener(QueryDocumentSnapshot -> {
            if (!QueryDocumentSnapshot.isEmpty()) {
                for (DocumentSnapshot document : QueryDocumentSnapshot) {
                    String section = document.getId();
                    db.collection("teachers").document("test").collection("class").document(section).set(InitalField);
//
//                    for (int i = 0; i < day.size(); i++) {
//                        grade12.document(section).collection(day.get(i)).document("testSub").set(testSection);
//                    }
                }
            }
        });



    }


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
