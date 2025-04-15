package com.example.stiqr_java.firebase;

import android.content.Context;
import android.widget.Toast;

import com.example.stiqr_java.dialog.DialogNotif;
import com.example.stiqr_java.recyclerview.model.LateRecordAllModel;
import com.example.stiqr_java.recyclerview.model.LateRecordsModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

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
import java.util.concurrent.atomic.AtomicInteger;

public class LateRecord {

    Context context;

    public LateRecord(Context context) {
        this.context = context;
    }



    //grade 12
    public void readAllRecord(String docname, String teacherEmail, String gradeLevel, lateRecordCallBack callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //get all section

        ArrayList<String> sectionList = new ArrayList<>();
        db.collection("teachers").document(docname).collection("class").get().addOnSuccessListener(QueryDocumentSnapshot -> {
           if (!QueryDocumentSnapshot.isEmpty()) {

               //get all section they handle
               for (DocumentSnapshot sectionDoc : QueryDocumentSnapshot) {
                    sectionList.add(sectionDoc.getId());
               }

               List<LateRecordAllModel> LateAll = new ArrayList<>();

               final int[] completedCount = {0};
               //loop per each section they handle
               for (String section : sectionList) {
                   //loop condtion to change section after all violators been retrive and save to model
                   db.collection("lateRecords").document(gradeLevel).collection(section).whereEqualTo("teacherEmail", teacherEmail).orderBy(FieldPath.documentId(), Query.Direction.DESCENDING).get().addOnSuccessListener(QueryDocumentSnapshot2 -> {

                       if (!QueryDocumentSnapshot2.isEmpty()) {
                          //retrive all violators from this section
                          for (DocumentSnapshot violatorDoc : QueryDocumentSnapshot2) {
                              String lateArrival = violatorDoc.getString("lateArrival");
                              String date = violatorDoc.getString("date");
                              String subject = violatorDoc.getString("subject");
                              String schedule = violatorDoc.getString("schedule");
                              String name = violatorDoc.getString("name");
                              String section1 = violatorDoc.getString("section");
                              LateAll.add(new LateRecordAllModel(lateArrival, date, subject, schedule, name, section1));
                          }
                      }
                       completedCount[0]++;
                       if (completedCount[0] == sectionList.size()) {
                            if (!LateAll.isEmpty()) {
                                callback.onCallBack(LateAll);
                            } else {
                                LateAll.add(new LateRecordAllModel("NO LATE RECORD", " ", " ", " ", " ", " "));
                            }
                       }
                   });
               }

           } else {
               DialogNotif.DialogShower(context, "Empty class");

           }
        });
    }

    public interface lateRecordCallBack{
        void onCallBack(List<LateRecordAllModel> lateAll);
    }

    public void readLateRecord(String studentNumber, String gradeLevel, String section, recordCallBack callBackRecord) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<LateRecordsModel> lateRecordList = new ArrayList<>();
        db.collection("lateRecords").document(gradeLevel).collection(section).whereEqualTo("studentNumber", studentNumber).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    int totalMinuteLate = 0;
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            String lateArrival = document.getString("lateArrival");
                            String date = document.getString("date");
                            String subject = document.getString("subject");
                            String teacher = document.getString("teacher");
                            String schedule = document.getString("schedule");
                            lateRecordList.add(new LateRecordsModel(date, subject, teacher, lateArrival, schedule));
                            assert lateArrival != null;
                            totalMinuteLate += Integer.parseInt(lateArrival);
                        }
                    }
                    callBackRecord.onRecordLoad(lateRecordList, totalMinuteLate);
                });
    }


    public void readRecentLate(String studentNumber, String gradeLevel, String section, recordHomeCallBack CB_homeRecord) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<LateRecordsModel> recentLate = new ArrayList<>();

        db.collection("lateRecords").document(gradeLevel).collection(section).whereEqualTo("studentNumber", studentNumber).orderBy(FieldPath.documentId(), Query.Direction.DESCENDING).limit(5).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            String lateArrival = document.getString("lateArrival");
                            String date = document.getString("date");
                            String subject = document.getString("subject");
                            String teacher = document.getString("teacher");
                            String schedule = document.getString("schedule");
                            recentLate.add(new LateRecordsModel(date, subject, teacher, lateArrival, schedule));
                            assert lateArrival != null;
                        }
                    }else {
                    }
                    CB_homeRecord.onHomeRecordLoad(recentLate);
                });
    }


    public interface recordHomeCallBack {
        void onHomeRecordLoad(List<LateRecordsModel> lateHomeRecord);
    }

    public interface recordCallBack {
        void onRecordLoad(List<LateRecordsModel> lateRecord, int totalMinuteLate);
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

        //QUERY SUBJECT INFO
        db.collection("schedule").document("shs").collection(gradeLevel).document(section).collection(currentDay).whereEqualTo("teacherEmail", teacherEmail).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    //IF WALANG TONG TC SA SCHED THROW ELSE COND
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                        String subTimeQUERY = document.getString("time1");
                        String timeOutQuery = document.getString("time2");
                        String schedule = document.getString("time");
                        String subject = document.getString("subject");
                        String teacher = document.getString("professor");

                        //ARRIVAL TIME
                        LocalTime subTime = LocalTime.parse(subTimeQUERY);
                        LocalTime timeOut = LocalTime.parse(timeOutQuery);
                        ZoneId zoneId = ZoneId.of("Asia/Manila");
                        LocalTime currentTime = LocalTime.now(zoneId);

                        if (currentTime.isAfter(subTime) && currentTime.isBefore(timeOut)) {
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
                            violatorData.put("teacherEmail", teacherEmail);
                            violatorData.put("studentNumber", studentNumber);
                            violatorData.put("subject", subject);
                            violatorData.put("teacher", teacher);


                            db.collection("counters").document("lateRecords").get()
                                    .addOnSuccessListener(query -> {
                                        if (query.exists()) {
                                            Long currentViolatorsCount = query.getLong("count");
                                            String violatorLogID = "violator" + currentViolatorsCount;
                                            db.collection("lateRecords").document(gradeLevel).collection(section).document(violatorLogID).set(violatorData).addOnSuccessListener(v -> {
                                                DialogNotif.DialogShower(context, "Late recorded successfully");
                                            });
                                            currentViolatorsCount += 1;
                                            long newCount = currentViolatorsCount;
                                            db.collection("counters").document("lateRecords").update("count", newCount);
                                        }
                                    });
                        } else {
                            DialogNotif.DialogShower(context, "This subject is already ended");
                        }
                    } else {
                        DialogNotif.DialogShower(context, "This teacher is not in your schedule or scanned invalid qr");
                    }
                });
    }
}
