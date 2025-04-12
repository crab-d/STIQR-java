package com.example.stiqr_java.student;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class CaptureActivity extends com.journeyapps.barcodescanner.CaptureActivity {
 public void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
 }
}
