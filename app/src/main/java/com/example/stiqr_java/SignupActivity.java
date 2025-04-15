package com.example.stiqr_java;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.stiqr_java.dialog.DialogNotif;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    private EditText fnameEditText, lnameEditText, emailEditText, passwordEditText, studentNumberEditText;
    private AutoCompleteTextView auto_complete_section, auto_complete_gender;
    private TextView signupButton;
    private ArrayAdapter<String> adapterItems, genderAdapterItems;
    ProgressBar progressBar;
    private FirebaseFirestore firestore;
    private Map<String, String> sectionGradeMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        if (!hasInternet(this)) {
            DialogNotif.DialogShower(this, "No internet connection");
            finish();
        } else {

            progressBar = findViewById(R.id.progressBar);
            LinearLayout layout = findViewById(R.id.layout);
            layout.setVisibility(View.GONE); // Hide layout initially
            progressBar.setVisibility(View.VISIBLE); // Show progress bar

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


            firestore = FirebaseFirestore.getInstance();

            // Initialize UI elements
            fnameEditText = findViewById(R.id.fnameEditText);
            lnameEditText = findViewById(R.id.lnameEditText);
            emailEditText = findViewById(R.id.emailEditText);
            passwordEditText = findViewById(R.id.passwordEditText);
            studentNumberEditText = findViewById(R.id.studentNumberEditText);
            signupButton = findViewById(R.id.signupButton);

            auto_complete_section = findViewById(R.id.auto_complete_section);

            // Initialize gender dropdown
            String[] gender = {"Male", "Female"};
            auto_complete_gender = findViewById(R.id.auto_complete_gender);
            genderAdapterItems = new ArrayAdapter<>(this, R.layout.list_item, gender);
            auto_complete_gender.setAdapter(genderAdapterItems);

            // Fetch section data
            fetchSections();

            signupButton.setOnClickListener(view -> signUpUser());
        }
    }

    private void fetchSections() {
        List<String> sectionList = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("term").document("semester").get().addOnSuccessListener(documentSnapshot -> {
            if(documentSnapshot.exists()) {
                String sem = documentSnapshot.getString("sem");
                Task<QuerySnapshot> grade12Task = firestore.collection("schedule").document("shs").collection("grade12").whereEqualTo("sem", sem).get();
                Task<QuerySnapshot> grade11Task = firestore.collection("schedule").document("shs").collection("grade11").whereEqualTo("sem", sem).get();

                // Combine tasks
                Task<List<QuerySnapshot>> combinedTask = Tasks.whenAllSuccess(grade12Task, grade11Task);

                combinedTask.addOnSuccessListener(querySnapshots -> {
                    // Add data from grade12
                    QuerySnapshot grade12Snapshot = querySnapshots.get(0);
                    for (QueryDocumentSnapshot document : grade12Snapshot) {
                        sectionList.add(document.getId());
                        sectionGradeMap.put(document.getId(), "grade12");
                    }

                    // Add data from grade11
                    QuerySnapshot grade11Snapshot = querySnapshots.get(1);
                    for (QueryDocumentSnapshot document : grade11Snapshot) {
                        sectionList.add(document.getId());
                        sectionGradeMap.put(document.getId(), "grade11");

                    }

                    // Update the adapter with fetched sections
                    adapterItems = new ArrayAdapter<>(this, R.layout.list_item, sectionList);
                    auto_complete_section.setAdapter(adapterItems);

                    // Show layout and hide progress bar
                    progressBar.setVisibility(View.GONE);
                    LinearLayout layout = findViewById(R.id.layout);
                    layout.setVisibility(View.VISIBLE);

                }).addOnFailureListener(e -> {
                    // Handle errors
                    DialogNotif.DialogShower(this, "Failed to fetch sections");

                    Log.e("FirestoreError", "Error fetching sections", e);

                    progressBar.setVisibility(View.GONE);
                    LinearLayout layout = findViewById(R.id.layout);
                    layout.setVisibility(View.VISIBLE); // Still show the form to avoid blocking the user
                });
            }
        });
        // Fetch sections for both grade11 and grade12

    }

    private void signUpUser() {
        // Get input values
        String fname = fnameEditText.getText().toString().trim();
        String lname = lnameEditText.getText().toString().trim();
        String fullname = fname + " " + lname;
        String email = emailEditText.getText().toString().trim().toLowerCase();
        String password = passwordEditText.getText().toString().trim();
        String studentNumber = studentNumberEditText.getText().toString().trim();

        // Get selected section and gender from AutoCompleteTextView
        String section = auto_complete_section.getText().toString().trim();
        String gender =  auto_complete_gender.getText().toString().trim();
        String gradeLevel = sectionGradeMap.get(section);


        // Validate required fields
        if (TextUtils.isEmpty(fname)) {
            fnameEditText.setError("First name is required");
            return;
        }

        if (TextUtils.isEmpty(lname)) {
            fnameEditText.setError("Last name is required");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Email is required");
            return;
        }

        if (!email.endsWith("@cubao.sti.edu.ph")) {
            emailEditText.setError("Only email from STI cubao are allowed");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Password is required");
            return;
        }

        if (password.length() < 6) {
            passwordEditText.setError("Password must be at least 6 characters");
            return;
        }

        if (TextUtils.isEmpty(studentNumber)) {
            studentNumberEditText.setError("Student Number is required");
            return;
        }

        if (studentNumber.length() != 11) {
            studentNumberEditText.setError("Student Number must be 11 digits");
            return;
        }

        if (TextUtils.isEmpty(section)) {
            DialogNotif.DialogShower(this, "Please choose a section");

            return;
        }

        firestore.collection("users")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        emailEditText.setError("Account already exists. Need help? contact MIS for assistance.");
                    } else if (!studentNumber.isEmpty()) {
                        firestore.collection("users").document(studentNumber).get().addOnSuccessListener(d -> {
                            if(d.exists()) {
                                studentNumberEditText.setError("Student number already exist in database, contact MIS for assistance.");
                                return;
                            } else {
                                generateUserIdAndRegister(fullname, fname, lname, email, password, studentNumber, section, gender, gradeLevel);
                            }
                        });
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(SignupActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void generateUserIdAndRegister(String fullname, String fname, String lname, String email, String password, String studentNumber, String section, String gender, String gradeLevel) {

        Map<String, Object> user = new HashMap<>();
        user.put("name", fullname);
        user.put("fname", fname);
        user.put("lname", lname);
        user.put("email", email);
        user.put("password", password);
        user.put("studentNumber", studentNumber);
        user.put("deductCounter", 0);
        user.put("gender", gender);
        user.put("status", "Enrolled");
        user.put("earlyOut", false);

        user.put("gradeLevel", gradeLevel);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("term").document("semester").get().addOnSuccessListener(ds -> {
            String sem = ds.getString("sem");

            if(sem.equalsIgnoreCase("sem2")) {
                user.put("section1", section);
                db.collection("term").document("semester").collection("section").whereEqualTo("section", section).get().addOnSuccessListener(a -> {
                    if(!a.isEmpty()) {
                        DocumentSnapshot documentSnapshot = a.getDocuments().get(0);
                        String getId = documentSnapshot.getId();
                        user.put("section", getId);
                        firestore.runTransaction((Transaction.Function<Void>) transaction -> {


                            String userId = studentNumber;

                            transaction.set(firestore.collection("users").document(userId), user);

                            return null;
                        }).addOnSuccessListener(aVoid -> {
                            DialogNotif.DialogShower(this, "Account registered successfully");
                            Intent intent = new Intent(this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(0,0);

                        }).addOnFailureListener(e -> {
                            Toast.makeText(SignupActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                    }
                });
            } else {
                user.put("section", section);
                db.collection("term").document("semester").collection("section").document(section).get().addOnSuccessListener(d -> {
                    if(d.exists()) {
                        user.put("section1", section);
                        firestore.runTransaction((Transaction.Function<Void>) transaction -> {
                            String userId = studentNumber;
                            transaction.set(firestore.collection("users").document(userId), user);
                            return null;
                        }).addOnSuccessListener(aVoid -> {
                            DialogNotif.DialogShower(this, "Account registered successfully");
                            Intent intent = new Intent(this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(0,0);

                        }).addOnFailureListener(e -> {
                            Toast.makeText(SignupActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                    }

                });

            }



        });

    }





    public boolean hasInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = connectivityManager.getActiveNetwork();
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);

            if (capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                return isInternetWorking(); // Check actual internet speed or ping
            }
        } else {
            android.net.NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnected()) {
                return isInternetWorking(); // Check actual internet speed or ping
            }
        }
        return false;
    }

    private boolean isInternetWorking() {
        try {
            // Ping a reliable server to test connectivity
            Process process = Runtime.getRuntime().exec("ping -c 1 google.com");
            int returnVal = process.waitFor();
            return (returnVal == 0); // If ping is successful
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }
}