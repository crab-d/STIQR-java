package com.example.stiqr_java.firebase;

import android.content.Context;
import android.widget.Toast;

import com.example.stiqr_java.recyclerview.model.CSModel;
import com.example.stiqr_java.recyclerview.model.StaffCSRecordModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CSRecord {
    Context context;

    public CSRecord(Context context) {
        this.context = context;
    }

    public void readStudentCS(String studentNumber, recordCSCallback CB_recordCS) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<CSModel> CSRecord = new ArrayList<>();
        db.collection("csRecords").whereEqualTo("studentNumber", studentNumber).orderBy(FieldPath.documentId(), Query.Direction.DESCENDING).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            String name = document.getString("name");
                            String status = document.getString("status");
                            String task = document.getString("task");
                            String completeDate = document.getString("completeDate");
                            String reference = document.getString("reference");
                            CSRecord.add(new CSModel(name, status, completeDate, task, reference, studentNumber));
                        }
                        CB_recordCS.onCallback(CSRecord);
                    }
                });
    }

    //STAFF HOME
    public void readLimitRecord(staffRecordCallBack CB_recordCS) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("csRecords").whereEqualTo("status", "To be process").orderBy(FieldPath.documentId(), Query.Direction.DESCENDING).limit(5)
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    List<StaffCSRecordModel> staffCSRecord = new ArrayList<>();
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            String studentNumber = document.getString("studentNumber");
                            String name = document.getString("name");
                            String status = document.getString("status");
                            String task = document.getString("task");
                            String date = document.getString("completeDate");
                            String reference = document.getString("reference");
                            staffCSRecord.add(new StaffCSRecordModel(reference, studentNumber, name, status, task, date));
                        }
                    } else {
                        Toast.makeText(context, "NO RECORD", Toast.LENGTH_SHORT).show();
                    }
                    CB_recordCS.onCallBack(staffCSRecord);
                });

    }

    //RECORD STAFF INTERFACE
    public interface staffRecordCallBack {
        void onCallBack(List<StaffCSRecordModel> staffCSRecord);
    }

    public void assignStudent(String reference, String task) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> updates = new HashMap<>();
        updates.put("status", "on going");
        updates.put("task", task);
        db.collection("csRecords").whereEqualTo("reference", reference).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                   if (!queryDocumentSnapshots.isEmpty()) {
                       DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                       db.collection("csRecords").document(documentSnapshot.getId()).update(updates)
                               .addOnSuccessListener(v -> {
                                   Toast.makeText(context, "ASSIGNED SUCCESSFULLY", Toast.LENGTH_SHORT).show();         
                       });
                   }
                });
    }

    //scan ni super admin na gagamitin ni DO GENESIS para maprocess na si student
    public void addCSRecord(String name, String studentNumber) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //query get referencce para may unique id next data na iinsert
        db.collection("counters").document("reference").get()
                .addOnSuccessListener(DocumentSnapshot1 -> {
                    if (DocumentSnapshot1.exists()) {
                        String count1 = DocumentSnapshot1.getString("count");
                        int count = Integer.parseInt(count1);
                        String ref = DocumentSnapshot1.getString("reference");
                        String formatCount = String.format("%06d", count);
                        String reference = ref + studentNumber + "-" + formatCount;
                        String completeDate = "---";
                        String status = "To be process";
                        String task = "---";

                        //Data Holder
                        Map<String, String> CSData = new HashMap<>();
                        CSData.put("completeDate", completeDate);
                        CSData.put("name", name);
                        CSData.put("reference", reference);
                        CSData.put("status", status);
                        CSData.put("studentNumber", studentNumber);
                        CSData.put("task", task);

                        //update reference counter
                        count += 1;
                        db.collection("counters").document("reference").update("count", String.valueOf(count));

                        //Get record counter for unique document name
                        db.collection("counters").document("CSRecord").get().addOnSuccessListener(DocumentSnapshot -> {
                            if (DocumentSnapshot.exists()) {
                                Long counter = DocumentSnapshot.getLong("count");
                                String docName = counter + "CSLog";

                                //insert data
                                db.collection("csRecords").document(docName).set(CSData).addOnSuccessListener(v -> {
                                    Toast.makeText(context, "STUDENT VIOLATION RECORDED", Toast.LENGTH_SHORT).show();
                                });

                                //update document counter
                                counter += 1;
                                db.collection("counters").document("CSRecord").update("count", counter);
                            }
                        });
                    }
                });
    }

    public interface recordCSCallback {
        void onCallback(List<CSModel> CSRecord);
    }
}
