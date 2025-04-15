package com.example.stiqr_java.student.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stiqr_java.R;
import com.example.stiqr_java.firebase.CSRecord;
import com.example.stiqr_java.firebase.LateRecord;
import com.example.stiqr_java.recyclerview.adapter.LateRecordsAdapter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudentHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentHome extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;

    public StudentHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudentHome.
     */
    // TODO: Rename and change types and number of parameters
    public static StudentHome newInstance(String param1, String param2) {
        StudentHome fragment = new StudentHome();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = getContext();
        View view = inflater.inflate(R.layout.fragment_student_home, container, false);

        TextView tv_name, tv_email, tv_studentNumber, tv_section, tv_totalLate, tv_minuteLate, tv_status, tv_totalCS, tv_csPending, tv_csOnGoing, tv_csComplete, tv_semester;
        tv_name = view.findViewById(R.id.tv_name);
        tv_email = view.findViewById(R.id.tv_email);
        tv_studentNumber = view.findViewById(R.id.tv_studentNumber);
        tv_section = view.findViewById(R.id.tv_section);
        tv_minuteLate = view.findViewById(R.id.tv_minuteLate);
        tv_totalLate = view.findViewById(R.id.tv_totalLate);
        tv_status = view.findViewById(R.id.tv_status);
        tv_totalCS = view.findViewById(R.id.tv_totalCS);
        tv_csPending = view.findViewById(R.id.tv_csPending);
        tv_csOnGoing = view.findViewById(R.id.tv_csOnGoing);
        tv_semester = view.findViewById(R.id.tv_semester);
        tv_csComplete = view.findViewById(R.id.tv_csComplete);
        RecyclerView rv_lateRecords = view.findViewById(R.id.rv_lateRecords);
        LateRecord DB_LATE = new LateRecord(context);

        String semester = context.getSharedPreferences("STUDENT_SESSION", Context.MODE_PRIVATE).getString("SEMESTER", "sem2");
        if (semester.equalsIgnoreCase("sem1")) { tv_semester.setText("SEM 1"); }
        else { tv_semester.setText("SEM 2"); }

        assert context != null;
        String name = context.getSharedPreferences("STUDENT_SESSION", Context.MODE_PRIVATE).getString("STUDENT_NAME", "NAH");
        String email = context.getSharedPreferences("STUDENT_SESSION", Context.MODE_PRIVATE).getString("STUDENT_EMAIL", "NAH");
        String studentNumber = context.getSharedPreferences("STUDENT_SESSION", Context.MODE_PRIVATE).getString("STUDENT_NUMBER", "NAH");
        String section = context.getSharedPreferences("STUDENT_SESSION", Context.MODE_PRIVATE).getString("STUDENT_SECTION", "NAH");
        String gradeLevel = context.getSharedPreferences("STUDENT_SESSION", Context.MODE_PRIVATE).getString("STUDENT_GRADE", "nah");

        tv_name.setText("NAME: " + name);
        tv_email.setText("EMAIL: " + email);
        tv_studentNumber.setText("STUDENT NUMBER: " + studentNumber);
        tv_section.setText("SECTION: " + section);

        CSRecord DB_CS = new CSRecord(context);
        DB_CS.readStudentCS(studentNumber, totalCS -> {
            tv_totalCS.setText(String.valueOf(totalCS.size()));
        });

        DB_CS.readPendingCS(studentNumber, totalPending -> {
            if (!totalPending.isEmpty()) {
                tv_csPending.setText(String.valueOf(totalPending.size()));
            } else {
                tv_csPending.setText("0");
            }
        });

        DB_CS.readOnGoingCS(studentNumber, totalOnGoing -> {
            if (!totalOnGoing.isEmpty()) {
                tv_csOnGoing.setText(String.valueOf(totalOnGoing.size()));
            } else {
                tv_csOnGoing.setText("0");

            }
        });
        DB_CS.readCompleteCS(studentNumber, totalComplete -> {
            if (!totalComplete.isEmpty()) {
                tv_csComplete.setText(String.valueOf(totalComplete.size()));
            } else {
                tv_csComplete.setText("0");

            }
        });


        DB_LATE.readLateRecord(studentNumber, gradeLevel, section, (record, totalMinute) -> {
            tv_totalLate.setText(String.valueOf(record.size()));
            tv_minuteLate.setText(String.valueOf(totalMinute));
            AtomicInteger deduct = new AtomicInteger(context.getSharedPreferences("STUDENT_SESSION", Context.MODE_PRIVATE).getInt("STUDENT_DEDUCT", 0));
            //da
            int eligibilityChecker = record.size() - deduct.get();
            DB_LATE.threshold(threshold -> {
                if (eligibilityChecker >= threshold) {
                    if (record.size() >= deduct.get()) {
                        tv_status.setText("NOTICE: ELIGIBLE FOR COMMUNITY SERVICES COMPLIANCE IS A MUST PLEASE SCAN DISCIPLINARY OFFICER QR");
                        //deduct.addAndGet(threshold);
//                        context.getSharedPreferences("STUDENT_SESSION", Context.MODE_PRIVATE).edit().putInt("STUDENT_DEDUCT", deduct.get()).apply();
                    }
                }
            });
        });


        rv_lateRecords.setLayoutManager(new LinearLayoutManager(context));
        DB_LATE.readRecentLate(studentNumber, gradeLevel, section, (LateRecord) -> {
            rv_lateRecords.setAdapter(new LateRecordsAdapter(context, LateRecord));


        });

        return view;
    }
}