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
import com.example.stiqr_java.firebase.LateRecord;
import com.example.stiqr_java.recyclerview.adapter.LateRecordsAdapter;

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

        TextView tv_name, tv_email, tv_studentNumber, tv_section, tv_totalLate, tv_minuteLate, tv_status;
        tv_name = view.findViewById(R.id.tv_name);
        tv_email = view.findViewById(R.id.tv_email);
        tv_studentNumber = view.findViewById(R.id.tv_studentNumber);
        tv_section = view.findViewById(R.id.tv_section);
        tv_minuteLate = view.findViewById(R.id.tv_minuteLate);
        tv_totalLate = view.findViewById(R.id.tv_totalLate);
        tv_status = view.findViewById(R.id.tv_status);
        RecyclerView rv_lateRecords = view.findViewById(R.id.rv_lateRecords);
        LateRecord DB_LATE = new LateRecord(context);

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


        rv_lateRecords.setLayoutManager(new LinearLayoutManager(context));
        DB_LATE.readRecentLate(studentNumber, gradeLevel, section, (LateRecord, MinuteCount) -> {
            rv_lateRecords.setAdapter(new LateRecordsAdapter(context, LateRecord));
            String lateCount = String.valueOf(LateRecord.size());
            tv_totalLate.setText(lateCount);
            tv_minuteLate.setText(String.valueOf(MinuteCount));
            DB_LATE.threshold(threshold -> {
              if (Integer.parseInt(lateCount) >= threshold) {
                  tv_status.setText("NOTICE: ELIGIBLE FOR COMMUNITY SERVICES COMPLIANCES IS A MUST");
              }
            });
        });

        return view;
    }
}