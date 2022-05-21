package com.engage.sourabh.attandanceSystem.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.engage.sourabh.attandanceSystem.Model.profiledatabase;
import com.engage.sourabh.attandanceSystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    TextView gotologin;
    TextView textview;
    ArrayAdapter<String> adapterShops;
    TextView ForSeller;
    String[] shops;
    Dialog dialog;
    ProgressBar pbadd;
    EditText userName,userEmail,userPass,userMobile,instituteCode;
    TextView shopType;
    Button signupBtn;
    private RadioGroup radioGroup;
    Button submit, clear;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    DatabaseReference rootRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        gotologin=findViewById(R.id.gotologin);

        gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

        pbadd=findViewById(R.id.progressaddstudent);
        pbadd.setVisibility(View.GONE);
        userName = findViewById(R.id.editTextName);
        userEmail = findViewById(R.id.editTextEmail);
        userPass = findViewById(R.id.editTextPassword);
        userMobile=findViewById(R.id.editTextMobile);
      instituteCode=findViewById(R.id.institutecode);
        signupBtn = findViewById(R.id.cirLoginButton);

        // Bind the components to their respective objects
        // by assigning their IDs
        // with the help of findViewById() method
        mAuth=FirebaseAuth.getInstance();


        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupclick();
            }
        });


    }

    private void signupclick() {
        String email = userEmail.getText().toString();
        String name = userName.getText().toString();
        String number = userMobile.getText().toString();
        String password = userPass.getText().toString();
        String code = instituteCode.getText().toString();
        String userType = "Institute";
        pbadd.setVisibility(View.VISIBLE);

        if (email.isEmpty()) {
            userEmail.setError("Please enter email id");
            userEmail.requestFocus();
            pbadd.setVisibility(View.GONE);

        } else if (name.isEmpty()) {
            userName.setError("Please enter FullName ");
            userName.requestFocus();
            pbadd.setVisibility(View.GONE);


        } else if (number.isEmpty()) {
            userMobile.setError("Please enter number");
            userMobile.requestFocus();
            pbadd.setVisibility(View.GONE);

        } else if (password.isEmpty() || password.length() < 6) {
            userPass.setError("Please enter password");
            userPass.requestFocus();
            pbadd.setVisibility(View.GONE);

        } else if (code.isEmpty() || code.length() < 4) {
            instituteCode.setError("Please enter code");
            instituteCode.requestFocus();
            pbadd.setVisibility(View.GONE);


        } else if (!isValid(email)) {
            userEmail.setError("Email not valid");
            userEmail.requestFocus();
            pbadd.setVisibility(View.GONE);

        } else if (email.isEmpty() && name.isEmpty() && number.isEmpty() && code.isEmpty() && email.isEmpty()) {
            Toast.makeText(SignUpActivity.this, "Fields Are Empty!", Toast.LENGTH_SHORT).show();
            pbadd.setVisibility(View.GONE);

        } else {


            final HashMap<String, String> profile = new HashMap<>();
            profiledatabase profiledatabase = new profiledatabase(" ",name, email, " ", " ", " ", " ", " ", userType, " ", " ", " ", " ", " ", "");
            profile.put("name", name);
            profile.put("email", email);
            profile.put("userType", "institute");
            profile.put("number",number);
            profile.put("code",code);


            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //Sign in success, update UI with the signed-in user's information

                                currentUser = mAuth.getCurrentUser();
                                rootRef = FirebaseDatabase.getInstance().getReference();
                                Toast.makeText(SignUpActivity.this, "Account created successfully...", Toast.LENGTH_SHORT).show();
                                String currentUserId = currentUser.getUid();
                                profile.put("userId", currentUserId);


                                rootRef.child("Profile").child(currentUserId).setValue(profile);
                                        rootRef.child("institutes").child(code).child("Profile").child(currentUserId).setValue(profile)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isComplete()) {
                                                    Toast.makeText(SignUpActivity.this, "Account created successfully...", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(SignUpActivity.this, "Error!...", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });

                                sentToMainActivity();
                            } else {
                                // If sign in fails, display a message to the user.

                                String message = task.getException().toString();
                                Toast.makeText(SignUpActivity.this, "Error : " + message,
                                        Toast.LENGTH_SHORT).show();

                            }

                            // ...
                        }
                    });


        }
    }



    private void sentToMainActivity() {

        Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
        finishAffinity();
        startActivity(i);
    }

        public static boolean isValid(String email) {
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
            Pattern pat = Pattern.compile(emailRegex);
            if (email == null)
                return false;
            return pat.matcher(email).matches();
        }
}

