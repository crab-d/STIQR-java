package com.example.stiqr_java.student.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.stiqr_java.R;
import com.example.stiqr_java.firebase.StudentSchedule;
import com.example.stiqr_java.recyclerview.adapter.LateRecordsAdapter;
import com.example.stiqr_java.recyclerview.adapter.ScheduleAdapter;
import com.example.stiqr_java.recyclerview.model.ScheduleModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link schedule#newInstance} factory method to
 * create an instance of this fragment.
 */
public class schedule extends Fragment  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public schedule() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment schedule.
     */
    // TODO: Rename and change types and number of parameters
    public static schedule newInstance(String param1, String param2) {
        schedule fragment = new schedule();
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
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        Context context = getContext();
        assert context != null;
        String gradeLevel = context.getSharedPreferences("STUDENT_SESSION", Context.MODE_PRIVATE).getString("STUDENT_GRADE", "nah");
        String section = context.getSharedPreferences("STUDENT_SESSION", Context.MODE_PRIVATE).getString("STUDENT_SECTION", "nah");

        StudentSchedule DB_SCHED = new StudentSchedule(context);
        RecyclerView rv_monday = view.findViewById(R.id.rv_monday);
        RecyclerView rv_tuesday = view.findViewById(R.id.rv_tuesday);
        RecyclerView rv_wednesday = view.findViewById(R.id.rv_wednesday);
        RecyclerView rv_thursday = view.findViewById(R.id.rv_thursday);
        RecyclerView rv_friday = view.findViewById(R.id.rv_friday);
        RecyclerView rv_saturday = view.findViewById(R.id.rv_saturday);

        rv_monday.setLayoutManager(new LinearLayoutManager(context));
        rv_tuesday.setLayoutManager(new LinearLayoutManager(context));
        rv_wednesday.setLayoutManager(new LinearLayoutManager(context));
        rv_thursday.setLayoutManager(new LinearLayoutManager(context));
        rv_friday.setLayoutManager(new LinearLayoutManager(context));
        rv_saturday.setLayoutManager(new LinearLayoutManager(context));

        DB_SCHED.schedMonday(gradeLevel, section, schedule -> {
            ScheduleAdapter MondaySchedAdapter = new ScheduleAdapter(context, schedule);
            rv_monday.setAdapter(MondaySchedAdapter);
        });

        DB_SCHED.schedTuesday(gradeLevel, section, schedule -> {
            ScheduleAdapter TuesdaySchedAdapter = new ScheduleAdapter(context, schedule);
            rv_tuesday.setAdapter(TuesdaySchedAdapter);
        });

        DB_SCHED.schedWednesday(gradeLevel, section, schedule -> {
            ScheduleAdapter WednesdaySchedAdapter = new ScheduleAdapter(context, schedule);
            rv_wednesday.setAdapter(WednesdaySchedAdapter);
        });

        DB_SCHED.schedThursday(gradeLevel, section, schedule -> {
            ScheduleAdapter ThursdaySchedAdapter = new ScheduleAdapter(context, schedule);
            rv_thursday.setAdapter(ThursdaySchedAdapter);
        });
        DB_SCHED.schedFriday(gradeLevel, section, schedule -> {
            ScheduleAdapter FridaySchedAdapter = new ScheduleAdapter(context, schedule);
            rv_friday.setAdapter(FridaySchedAdapter);
        });

        DB_SCHED.schedSaturday(gradeLevel, section, schedule -> {
            ScheduleAdapter SaturdaySchedAdapter = new ScheduleAdapter(context, schedule);
            rv_saturday.setAdapter(SaturdaySchedAdapter);
        });


        return view;
    }
}