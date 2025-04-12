package com.example.stiqr_java.student.fragment;

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
import com.example.stiqr_java.recyclerview.adapter.LateRecordsAdapter;
import com.example.stiqr_java.recyclerview.model.LateRecordsModel;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = getContext();
        View view = inflater.inflate(R.layout.fragment_student_home, container, false);

        TextView tv_name, tv_email, tv_studentNumber, tv_section, tv_totalLate;
        tv_name = view.findViewById(R.id.tv_name);
        tv_email = view.findViewById(R.id.tv_email);
        tv_studentNumber = view.findViewById(R.id.tv_studentNumber);
        tv_section = view.findViewById(R.id.tv_section);

        tv_totalLate = view.findViewById(R.id.tv_totalLate);

        RecyclerView rv_lateRecords = view.findViewById(R.id.rv_lateRecords);

        List<LateRecordsModel> lateRecord = new ArrayList<>();
        lateRecord.add(new LateRecordsModel("APRIL 12 2025", "ENTREPRENEURSHIP", "CABANIT", "50 MINS", "11:00 AM - 2:00 PM"));
        lateRecord.add(new LateRecordsModel("APRIL 12 2025", "ENTREPRENEURSHIP", "CABANIT", "50 MINS", "11:00 AM - 2:00 PM"));
        lateRecord.add(new LateRecordsModel("APRIL 12 2025", "ENTREPRENEURSHIP", "CABANIT", "50 MINS", "11:00 AM - 2:00 PM"));

        String lateCount = String.valueOf(lateRecord.size());

        rv_lateRecords.setLayoutManager(new LinearLayoutManager(context));
        rv_lateRecords.setAdapter(new LateRecordsAdapter(context, lateRecord));

        String name = context.getSharedPreferences("STUDENT_SESSION", Context.MODE_PRIVATE).getString("STUDENT_NAME", "NAH");
        String email = context.getSharedPreferences("STUDENT_SESSION", Context.MODE_PRIVATE).getString("STUDENT_EMAIL", "NAH");
        String studentNumber = context.getSharedPreferences("STUDENT_SESSION", Context.MODE_PRIVATE).getString("STUDENT_NUMBER", "NAH");
        String section = context.getSharedPreferences("STUDENT_SESSION", Context.MODE_PRIVATE).getString("STUDENT_SECTION", "NAH");

        tv_name.setText("NAME: " + name);
        tv_email.setText("EMAIL: " + email);
        tv_studentNumber.setText("STUDENT NUMBER: " + studentNumber);
        tv_section.setText("SECTION: " + section);

        tv_totalLate.setText(lateCount);

        return view;
    }
}