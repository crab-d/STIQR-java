package com.example.stiqr_java.student.fragment.Record;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stiqr_java.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecordParentFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecordParentFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RecordParentFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecordParentFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static RecordParentFrag newInstance(String param1, String param2) {
        RecordParentFrag fragment = new RecordParentFrag();
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

    public void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_record_parent, container, false);
        TextView tv_late = view.findViewById(R.id.tv_late);
        TextView tv_cs = view.findViewById(R.id.tv_cs);
        Context context = getContext();
        tv_late.setOnClickListener(v -> {
            loadFragment(new LateRecord());
            tv_late.setBackgroundColor(Color.parseColor("#3283FF"));
            tv_late.setTextColor(Color.parseColor("#f1f1f1"));
            tv_cs.setBackgroundColor(Color.parseColor("#f1f1f1"));
            tv_cs.setTextColor(Color.parseColor("#3283FF"));
        });

        tv_cs.setOnClickListener(v -> {
            loadFragment(new CSRecordFrag());
            tv_late.setBackgroundColor(Color.parseColor("#f1f1f1"));
            tv_late.setTextColor(Color.parseColor("#3283FF"));
            tv_cs.setBackgroundColor(Color.parseColor("#3283FF"));
            tv_cs.setTextColor(Color.parseColor("#f1f1f1"));
        });



        loadFragment(new LateRecord());

        return view;
    }
}