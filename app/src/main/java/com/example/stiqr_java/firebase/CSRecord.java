package com.example.stiqr_java.firebase;

import android.content.Context;
import android.widget.Toast;

import com.example.stiqr_java.recyclerview.model.CSModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSRecord {
    Context context;

    public CSRecord(Context context) {
        this.context = context;
    }

    public void readStudentCS(String studentNumber, recordCSCallback CB_recordCS) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<CSModel> CSRecord = new ArrayList<>();
        db.collection("csRecords").whereEqualTo("studentNumber", studentNumber).get()
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
                        String reference = ref + count;
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
                                String docName = "CSLog" + counter;

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
