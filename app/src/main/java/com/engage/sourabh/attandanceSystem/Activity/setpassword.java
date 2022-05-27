package com.engage.sourabh.attandanceSystem.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.engage.sourabh.attandanceSystem.Model.addTeacherdatabase;
import com.engage.sourabh.attandanceSystem.Model.profiledatabase;
import com.engage.sourabh.attandanceSystem.R;
import com.engage.sourabh.attandanceSystem.global;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class setpassword extends AppCompatActivity {


    private EditText teacherPassword,password2;
    private FirebaseAuth mAuth;
    private DatabaseReference pdroot;
    private DatabaseReference dbRef;


    private ProgressBar pbset;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setpassword);


        teacherPassword = findViewById(R.id.passsword1);
        password2 = findViewById(R.id.passsword2);

        final Button setpassword = findViewById(R.id.submit);


        pbset=findViewById(R.id.progressaddTeacer);
        pbset.setVisibility(View.GONE);


        pdroot = FirebaseDatabase.getInstance().getReference("Profile");




        Intent intent = getIntent();
        final String email = intent.getStringExtra("email");
        final String Fullname = intent.getStringExtra("fullname");
        final String degree = intent.getStringExtra("degree");
        final String birthofdate = intent.getStringExtra("birthofdate");
        final String numbers = intent.getStringExtra("number");
        final String addresss = intent.getStringExtra("address");
        final String courese = intent.getStringExtra("cource");









        setpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbset.setVisibility(View.VISIBLE);
                if(teacherPassword.getText().length()<6){
                    teacherPassword.setError("password too short");
                    teacherPassword.requestFocus();
                    pbset.setVisibility(View.GONE);
                }if (teacherPassword.getText().toString().equals(password2.getText().toString())) {
                    mAuth = FirebaseAuth.getInstance();
                    String icode=((global)getApplication()).getInstituteCode();
                    dbRef = FirebaseDatabase.getInstance().getReference("institutes/"+icode+"/"+"Teacher");
                    final String password = password2.getText().toString().trim();
                    assert email != null;
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(setpassword.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        if (user != null) {
                                            String uid = user.getUid();
                                            String code=((global)getApplication()).getInstituteCode();
                                            profiledatabase teacherData=new profiledatabase(code,Fullname, email,birthofdate, numbers, uid, addresss, password,"Teacher",null,null,null,degree,courese,null);
                                            pdroot.child(uid).setValue(teacherData)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isComplete()) {
                                                                Toast.makeText(setpassword.this, "Account created successfully...", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                Toast.makeText(setpassword.this, "Error!...", Toast.LENGTH_SHORT).show();
                                                            }

                                                        }
                                                    });
                                            addTeacherdatabase addTeacherdatabase=new addTeacherdatabase(Fullname,email,degree,courese,birthofdate,numbers,uid,addresss);

                                            dbRef.child(uid).setValue(addTeacherdatabase);
                                            Toast.makeText(setpassword.this, "teacher Registered Login Now", Toast.LENGTH_LONG).show();
                                            pbset.setVisibility(View.GONE);
                                            password2.setText("");
                                            teacherPassword.setText("");
                                            String em=((global)getApplication()).getEmailaddrss();
                                            mAuth.signOut();

                                            String ps=((global)getApplication()).getPassword();
                                            Log.d("check",em+ ps);
                                            Intent i=new Intent(setpassword.this, LoginActivity.class);
                                            startActivity(i);
                                            finish();

                                        } else {
                                            Toast.makeText(setpassword.this, "Error", Toast.LENGTH_LONG).show();
                                            pbset.setVisibility(View.GONE);
                                        }
                                    }
                                }
                            });
                } else {
                    password2.setError("password not same");
                    password2.requestFocus();
                    pbset.setVisibility(View.GONE);
                }
            }
        });
    }


}
