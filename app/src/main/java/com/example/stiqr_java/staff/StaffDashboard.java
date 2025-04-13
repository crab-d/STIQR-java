package com.example.stiqr_java.staff;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.stiqr_java.LoginActivity;
import com.example.stiqr_java.R;
import com.example.stiqr_java.staff.fragment.StaffHome;

public class StaffDashboard extends AppCompatActivity {
    TextView tv_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_staff_dashboard);
        tv_logout = findViewById(R.id.tv_logout);

        loadFragment(new StaffHome());

        tv_logout.setOnClickListener(v -> {
            getSharedPreferences("STAFF_SESSION", MODE_PRIVATE).edit().clear().commit();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(0,0);
            finish();
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_staff, fragment);
        fragmentTransaction.commit();
    }
}