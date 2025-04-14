package com.example.stiqr_java.staff.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.stiqr_java.R;
import com.example.stiqr_java.teacher.TeacherQR;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragQR#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragQR extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragQR() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragQR.
     */
    // TODO: Rename and change types and number of parameters
    public static FragQR newInstance(String param1, String param2) {
        FragQR fragment = new FragQR();
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
        View view = inflater.inflate(R.layout.fragment_frag_q_r, container, false);
        ImageView im_qr = view.findViewById(R.id.iv_qr);

        Bitmap qrBitmap = TeacherQR.generateQRCode("DisciplinaryOfficer", 900, 900);

        if (qrBitmap != null) {
            im_qr.setImageBitmap(qrBitmap);
        }
        return view;
    }
}