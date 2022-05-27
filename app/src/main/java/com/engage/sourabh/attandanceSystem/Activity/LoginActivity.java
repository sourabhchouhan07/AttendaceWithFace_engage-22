package com.engage.sourabh.attandanceSystem.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.engage.sourabh.attandanceSystem.R;
import com.engage.sourabh.attandanceSystem.global;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {


    Button login;
    private EditText email,passsword;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;


    private DatabaseReference userDatabaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        login = findViewById(R.id.cirLoginButton);
        email = findViewById(R.id.loginEmail);
        passsword = findViewById(R.id.loginPassword);

        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
        email.setEnabled(true);
        passsword.setEnabled(true);
        progressBar.setVisibility(View.GONE);


        TextView signUpbtn=findViewById(R.id.signupfromlogin);

        signUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(i);
            }
        });



            LoginFun();  //LoginFunction


        }


    private void LoginFun() {


    //UserLogin
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userEmail = email.getText().toString().trim();
                final String userPassword=passsword.getText().toString().trim();
                email.setEnabled(false);
                passsword.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);
                if(userEmail.isEmpty()){
                    email.setError(getString(R.string.emailAlert));
                    email.requestFocus();
                    progressBar.setVisibility(View.GONE);
                    email.setEnabled(true);
                    passsword.setEnabled(true);
                } else  if(userPassword.isEmpty()){
                    passsword.setError(getString(R.string.enterPass));
                    passsword.requestFocus();
                    progressBar.setVisibility(View.GONE);
                    email.setEnabled(true);
                    passsword.setEnabled(true);
                } else if(userPassword.length()<6){
                    passsword.setError(getString(R.string.shortPass));
                    passsword.requestFocus();
                    progressBar.setVisibility(View.GONE);
                    email.setEnabled(true);
                    passsword.setEnabled(true);
                } else if(!(userEmail.isEmpty() && userPassword.isEmpty())){
                    mAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser currentUser = mAuth.getCurrentUser();
                                if(currentUser!=null){
                                    ((global) getApplication()).setEmailaddrss(userEmail);
                                    ((global) getApplication()).setPassword(userPassword);
                                    final String uid = currentUser.getUid();
                                    userDatabaseRef = FirebaseDatabase.getInstance().getReference("Profile/"+uid);
                                    userDatabaseRef.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            String usertypelogin=dataSnapshot.child("userType").getValue().toString();
                                           String inCode=dataSnapshot.child("code").getValue().toString();
                                            Toast.makeText(LoginActivity.this,"Successfully login ",Toast.LENGTH_SHORT).show();
                                            if(usertypelogin.equals("Teacher")){
                                                String name=dataSnapshot.child("fullname").getValue().toString();
                                                ((global)getApplication()).setFullname(name);
                                                progressBar.setVisibility(View.GONE);
                                                Intent intent = new Intent(LoginActivity.this, IndexActivity.class);
                                                intent.putExtra("uid",uid);
                                                ((global)getApplication()).setInstituteCode(inCode);
                                                intent.putExtra("userType","Teacher");
                                                intent.putExtra("code",inCode);
                                                startActivity(intent);
                                            }else if(usertypelogin.equals("Student")){
                                                String name=dataSnapshot.child("fullname").getValue().toString();
                                                ((global)getApplication()).setFullname(name);
                                                progressBar.setVisibility(View.GONE);
                                                Intent intent = new Intent(LoginActivity.this, StudentHomeAcitvity.class);
                                                intent.putExtra("uid",uid);
                                                ((global)getApplication()).setInstituteCode(inCode);
                                                intent.putExtra("code",inCode);
                                                startActivity(intent);
                                            }else if(usertypelogin.equals("institute"))
                                            {
                                                String name=dataSnapshot.child("name").getValue().toString();
                                                ((global)getApplication()).setFullname(name);
                                                progressBar.setVisibility(View.GONE);
                                                Intent intent = new Intent(LoginActivity.this, IndexActivity.class);
                                                intent.putExtra("uid",uid);
                                                ((global)getApplication()).setInstituteCode(inCode);
                                                intent.putExtra("code",inCode);
                                                intent.putExtra("userType","institute");
                                                startActivity(intent);
                                            }
                                            else {
                                                Log.d("login","LoginError");
                                                progressBar.setVisibility(View.GONE);
                                                Toast.makeText(LoginActivity.this,"Error not usertype",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            Log.d("login","Error--"+error);
                                            Toast.makeText(LoginActivity.this,"Error"+error,Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }else {
                                    Toast.makeText(LoginActivity.this,"your are not sign",Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, R.string.vPass ,Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                email.setEnabled(true);
                                passsword.setEnabled(true);
                            }
                        }
                    });
                }else{
                    Toast.makeText(LoginActivity.this,"Error Occurred!!!",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    email.setEnabled(true);
                    passsword.setEnabled(true);
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

}
