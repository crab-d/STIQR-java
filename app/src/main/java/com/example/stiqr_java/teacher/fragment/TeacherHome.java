package com.example.stiqr_java.teacher.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stiqr_java.R;
import com.example.stiqr_java.firebase.CSRecord;
import com.example.stiqr_java.firebase.LateRecord;
import com.example.stiqr_java.firebase.StudentSchedule;
import com.example.stiqr_java.teacher.TeacherQR;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeacherHome#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class TeacherHome extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeacherHome.
     */
    // TODO: Rename and change types and number of parameters
    public static TeacherHome newInstance(String param1, String param2) {
        TeacherHome fragment = new TeacherHome();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public TeacherHome() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_teacher_home, container, false);
        Context context = getContext();


        TextView tv_name = view.findViewById(R.id.tv_name);
        TextView tv_email = view.findViewById(R.id.tv_email);

        assert context != null;
        String name = context.getSharedPreferences("TEACHER_SESSION", Context.MODE_PRIVATE).getString("TEACHER_NAME", "NAH");
        String teacherEmail = context.getSharedPreferences("TEACHER_SESSION", Context.MODE_PRIVATE).getString("TEACHER_EMAIL", "NAH");

        tv_name.setText("NAME: " + name);
        tv_email.setText("EMAIL: " + teacherEmail);
        ImageView im_qr = view.findViewById(R.id.im_qr);

        Bitmap qrBitmap = TeacherQR.generateQRCode(teacherEmail, 900, 900);

        if (qrBitmap != null) {
            im_qr.setImageBitmap(qrBitmap);
        }
        return view;
    }
}