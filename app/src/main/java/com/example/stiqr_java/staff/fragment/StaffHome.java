package com.example.stiqr_java.staff.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stiqr_java.R;
import com.example.stiqr_java.firebase.CSRecord;
import com.example.stiqr_java.recyclerview.adapter.StaffCSRecordsAdapter;
import com.example.stiqr_java.teacher.TeacherQR;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StaffHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StaffHome extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StaffHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StaffHome.
     */
    // TODO: Rename and change types and number of parameters
    public static StaffHome newInstance(String param1, String param2) {
        StaffHome fragment = new StaffHome();
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
        View view = inflater.inflate(R.layout.fragment_staff_home, container, false);
        Context context = getContext();
        CSRecord DB_CS = new CSRecord(context);
        RecyclerView rv_recent = view.findViewById(R.id.rv_recent);
        TextView tv_totalRecord = view.findViewById(R.id.tv_totalRecord);
        TextView tv_pending = view.findViewById(R.id.tv_pending);
        TextView tv_onGoing = view.findViewById(R.id.tv_onGoing);
        TextView tv_complete = view.findViewById(R.id.tv_complete);

        DB_CS.readALLCS(total -> {
            tv_totalRecord.setText(String.valueOf(total.size()));
        });

        DB_CS.readALLPending(total -> {
            tv_pending.setText(String.valueOf(total.size()));
        });

        DB_CS.readALLOnGoing(total -> {
            tv_onGoing.setText(String.valueOf(total.size()));
        });

        DB_CS.readALLComplete(total -> {
            tv_complete.setText(String.valueOf(total.size()));
        });


        rv_recent.setLayoutManager(new LinearLayoutManager(context));
        DB_CS.readLimitRecord(record -> {
            rv_recent.setAdapter(new StaffCSRecordsAdapter(context, record));
        });

        return view;
    }
}