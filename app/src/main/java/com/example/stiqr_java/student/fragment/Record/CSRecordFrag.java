package com.example.stiqr_java.student.fragment.Record;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.stiqr_java.R;
import com.example.stiqr_java.firebase.CSRecord;
import com.example.stiqr_java.recyclerview.adapter.CSRecordsAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CSRecordFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CSRecordFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CSRecordFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CSRecord.
     */
    // TODO: Rename and change types and number of parameters
    public static CSRecordFrag newInstance(String param1, String param2) {
        CSRecordFrag fragment = new CSRecordFrag();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_c_s_record, container, false);
        RecyclerView rv_csRecords = view.findViewById(R.id.rv_csRecord);
        Context context = getContext();
        CSRecord DB_CS = new CSRecord(context);

        String studentNumber = context.getSharedPreferences("STUDENT_SESSION", Context.MODE_PRIVATE).getString("STUDENT_NUMBER", "ANH");


        rv_csRecords.setLayoutManager(new LinearLayoutManager(context));
        DB_CS.readStudentCS(studentNumber, CSRecord -> {
            rv_csRecords.setAdapter(new CSRecordsAdapter(context, CSRecord));
        });

        return view;
    }
}