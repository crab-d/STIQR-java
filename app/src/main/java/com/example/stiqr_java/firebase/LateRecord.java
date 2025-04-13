package com.example.stiqr_java.firebase;

import android.content.Context;
import android.widget.Toast;

import com.example.stiqr_java.recyclerview.model.LateRecordsModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class LateRecord {

    Context context;

    public LateRecord(Context context) {
        this.context = context;
    }

    public void readLateRecord(String studentNumber, String gradeLevel, String section, recordCallBack callBackRecord) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<LateRecordsModel> lateRecordList = new ArrayList<>();
        db.collection("lateRecords").document(gradeLevel).collection(section).whereEqualTo("studentNumber", studentNumber).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {

                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            String lateArrival = document.getString("lateArrival");
                            String date = document.getString("date");
                            String subject = document.getString("subject");
                            String teacher = document.getString("teacher");
                            String schedule = document.getString("schedule");
                            lateRecordList.add(new LateRecordsModel(date, subject, teacher, lateArrival, schedule));
                        }
                    } else {
                        Toast.makeText(context, "NO RECORD", Toast.LENGTH_SHORT).show();
                    }
                    callBackRecord.onRecordLoad(lateRecordList);
                });
    }



    public void readRecentLate(String studentNumber, String gradeLevel, String section, recordHomeCallBack CB_homeRecord) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<LateRecordsModel> recentLate = new ArrayList<>();

        db.collection("lateRecords").document(gradeLevel).collection(section).whereEqualTo("studentNumber", studentNumber).limit(5).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    int totalMinuteCount = 0;

                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            String lateArrival = document.getString("lateArrival");
                            String date = document.getString("date");
                            String subject = document.getString("subject");
                            String teacher = document.getString("teacher");
                            String schedule = document.getString("schedule");
                            recentLate.add(new LateRecordsModel(date, subject, teacher, lateArrival, schedule));

                            assert lateArrival != null;
                            totalMinuteCount += Integer.parseInt(lateArrival);
                        }



                    }
                    CB_homeRecord.onHomeRecordLoad(recentLate, totalMinuteCount);
                });
    }



    public interface recordHomeCallBack {
        void onHomeRecordLoad(List<LateRecordsModel> lateHomeRecord, int totalMinute);
    }

    public interface recordCallBack {
        void onRecordLoad(List<LateRecordsModel> lateRecord);
    }

    public void threshold(countCallBack CB_count) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("counters").document("threshold").get()
                .addOnSuccessListener(Document -> {
                    int count = 0;
                   if (Document.exists()) {
                       Long value = Document.getLong("count");
                       assert value != null;
                       count = value.intValue();
                   }
                   CB_count.onCountLoad(count);
                });
    }

    public interface countCallBack {
        void onCountLoad(int count);
    }

    private String currentDate() {
        ZoneId manila = ZoneId.of("Asia/Manila");
        LocalDate today = LocalDate.now(manila);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd yyyy");
        return today.format(formatter);
    }

    public void addLateStudent(String name, String section, String gradeLevel, String teacherEmail, String studentNumber) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String currentDay = LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH).toLowerCase();
        Toast.makeText(context, currentDay + gradeLevel + section + teacherEmail, Toast.LENGTH_SHORT).show();
        //QUERY SUBJECT INFO
        db.collection("schedule").document("shs").collection(gradeLevel).document(section).collection(currentDay).whereEqualTo("teacherEmail", teacherEmail).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    //IF WALANG TONG TC SA SCHED THROW ELSE COND
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                        String subTimeQUERY = document.getString("time1");
                        String schedule = document.getString("time");
                        String subject = document.getString("subject");
                        String teacher = document.getString("professor");

                        //ARRIVAL TIME
                        LocalTime subTime = LocalTime.parse(subTimeQUERY);
                        ZoneId zoneId = ZoneId.of("Asia/Manila");
                        LocalTime currentTime = LocalTime.now(zoneId);
                        String lateArrival = String.valueOf(Duration.between(subTime, currentTime).toMinutes());
                        String date = currentDate();

                        //VIOLATORS OBJ
                        Map<String, String> violatorData = new HashMap<>();
                        violatorData.put("date", date);
                        violatorData.put("gradeLevel", gradeLevel);
                        violatorData.put("lateArrival", lateArrival);
                        violatorData.put("name", name);
                        violatorData.put("schedule", schedule);
                        violatorData.put("section", section);
                        violatorData.put("studentNumber", studentNumber);
                        violatorData.put("subject", subject);
                        violatorData.put("teacher", teacher);

//                               for (Map.Entry<String, String> entry : violatorData.entrySet()) {
//                                   Toast.makeText(context, entry.getKey() + " : " + entry.getValue(), Toast.LENGTH_SHORT).show();
//                               }


                        db.collection("counters").document("lateRecords").get()
                                .addOnSuccessListener(query -> {
                                    if (query.exists()) {
                                        Long currentViolatorsCount = query.getLong("count");
                                        String violatorLogID = "violator" + currentViolatorsCount;
                                        db.collection("lateRecords").document(gradeLevel).collection(section).document(violatorLogID).set(violatorData).addOnSuccessListener(v -> {
                                            Toast.makeText(context, "LATE RECORDED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                                        });
                                        currentViolatorsCount += 1;
                                        long newCount = currentViolatorsCount;
                                        db.collection("counters").document("lateRecords").update("count", newCount);
                                    }
                                });
                    } else {
                        Toast.makeText(context, "This teacher is not registered in your schedule or something went wrong.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
