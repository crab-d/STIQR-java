package com.example.stiqr_java.teacher.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stiqr_java.R;
import com.example.stiqr_java.firebase.LateRecord;
import com.example.stiqr_java.recyclerview.adapter.LateRecordAllAdapter;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragRecord#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragRecord extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragRecord() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragRecord.
     */
    // TODO: Rename and change types and number of parameters
    public static FragRecord newInstance(String param1, String param2) {
        FragRecord fragment = new FragRecord();
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
        View view = inflater.inflate(R.layout.fragment_frag_record, container, false);
        Context context = getContext();
        TextView tv_grade12 = view.findViewById(R.id.tv_grade12);
        TextView tv_grade11 = view.findViewById(R.id.tv_grade11);
        RecyclerView rv_record = view.findViewById(R.id.rv_lateRecord);

        String docName = context.getSharedPreferences("TEACHER_SESSION", Context.MODE_PRIVATE).getString("DOCNAME", "s");
        String teacherEmail = context.getSharedPreferences("TEACHER_SESSION", Context.MODE_PRIVATE).getString("TEACHER_EMAIL", "s");

        LateRecord DB_Late = new LateRecord(context);

        rv_record.setLayoutManager(new LinearLayoutManager(context));

        tv_grade12.setOnClickListener(v -> {
            rv_record.setAdapter(null);

            DB_Late.readAllRecord(docName, teacherEmail, "grade12", result -> {
                if (!result.isEmpty()) {
                    rv_record.setAdapter(new LateRecordAllAdapter(context, result));
                } else {
                    rv_record.setVisibility(View.GONE);
                }
                tv_grade12.setTextColor(Color.parseColor("#FFFFFF"));
                tv_grade12.setBackgroundColor(Color.parseColor("#3283FF"));
                tv_grade11.setTextColor(Color.parseColor("#3283FF"));
                tv_grade11.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }) ;

        });

        tv_grade11.setOnClickListener(v -> {
            rv_record.setAdapter(null);

            DB_Late.readAllRecord(docName, teacherEmail,"grade11", result2 -> {
                rv_record.setAdapter(new LateRecordAllAdapter(context, result2));
            });

            tv_grade12.setTextColor(Color.parseColor("#3283FF"));
            tv_grade12.setBackgroundColor(Color.parseColor("#FFFFFF"));
            tv_grade11.setTextColor(Color.parseColor("#FFFFFF"));
            tv_grade11.setBackgroundColor(Color.parseColor("#3283FF"));
        });

        DB_Late.readAllRecord(docName, teacherEmail, "grade12", result -> {
            rv_record.setAdapter(new LateRecordAllAdapter(context, result));
            if (result.isEmpty()) {
                rv_record.setVisibility(View.GONE);
            }
        });


        return view;
    }
}