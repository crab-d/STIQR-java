package com.example.stiqr_java.staff.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stiqr_java.R;
import com.example.stiqr_java.firebase.StudentRecord;
import com.example.stiqr_java.recyclerview.adapter.StudentAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragSearch#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragSearch extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragSearch() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragSearch.
     */
    // TODO: Rename and change types and number of parameters
    public static FragSearch newInstance(String param1, String param2) {
        FragSearch fragment = new FragSearch();
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
        View view = inflater.inflate(R.layout.fragment_frag_search, container, false);
        RecyclerView rv_student = view.findViewById(R.id.rv_student);
        Context context = getContext();
        StudentRecord DB_Student = new StudentRecord(context);
        EditText et_search = view.findViewById(R.id.et_search);
        Button btn_search = view.findViewById(R.id.btn_search);

        btn_search.setOnClickListener(v -> {
            String studentNumber = et_search.getText().toString().trim();
            if (studentNumber.isEmpty()) {
                et_search.setError("Enter student number");
                return;
            }
            rv_student.setLayoutManager(new LinearLayoutManager(context));
            DB_Student.searchStudent(studentNumber, student -> {
                if (!student.isEmpty()) {
                    rv_student.setAdapter(new StudentAdapter(context, student));
                } else {
                    Toast.makeText(context, "No student found", Toast.LENGTH_SHORT).show();
                }
            });
        });
        

        return view;
    }
}