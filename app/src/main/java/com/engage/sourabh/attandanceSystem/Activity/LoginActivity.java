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

import com.engage.sourabh.attandanceSystem.Model.profiledatabase;
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

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {


    private EditText email,passsword;
    private FirebaseAuth mAuth,mauth1;
    private ProgressBar spinner;
    private DatabaseReference notice;
    private DatabaseReference notice1;
    private DatabaseReference notice2;
    private DatabaseReference notice3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Button login = findViewById(R.id.cirLoginButton);
        email = findViewById(R.id.loginEmail);
        passsword = findViewById(R.id.loginPassword);
//        TextView forget = findViewById(R.id.forget);
        spinner = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
        email.setEnabled(true);
        passsword.setEnabled(true);
        spinner.setVisibility(View.GONE);


        TextView signUpbtn=findViewById(R.id.signupfromlogin);

        signUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(i);
            }
        });






        Intent intent12=getIntent();
        String action=intent12.getStringExtra("action");
        String email1=intent12.getStringExtra("email");
        String password1=intent12.getStringExtra("password");

        if(action!=null){
            if(action.equals("autologin")){
                mauth1 = FirebaseAuth.getInstance();
                spinner.setVisibility(View.VISIBLE);
                String emailaddrss = ((global) this.getApplication()).getEmailaddrss();
                String password12 = ((global) this.getApplication()).getPassword();
                mauth1.signInWithEmailAndPassword(emailaddrss, password12).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            if(currentUser!=null){
                                final String uid = currentUser.getUid();
                                notice3 = FirebaseDatabase.getInstance().getReference("Profile/"+uid);
                                notice3.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        profiledatabase profiledatabase=dataSnapshot.getValue(profiledatabase.class);
                                        String usertypelogin=dataSnapshot.child("userType").getValue().toString();
                                        Toast.makeText(LoginActivity.this,"Successfull login ",Toast.LENGTH_SHORT).show();
                                        if(usertypelogin.equals("Teacher")){
                                            spinner.setVisibility(View.GONE);
                                            String name=dataSnapshot.child("fullname").getValue().toString();
                                            ((global)getApplication()).setFullname(name);
                                            Intent i = new Intent(LoginActivity.this, IndexActivity.class);
                                            i.putExtra("uid",uid);
                                            i.putExtra("userType","Teacher");
                                            startActivity(i);
                                        }else if(usertypelogin.equals("Student")){

                                            String name=dataSnapshot.child("fullname").getValue().toString();
                                            ((global)getApplication()).setFullname(name);
                                            spinner.setVisibility(View.GONE);
                                            Intent i = new Intent(LoginActivity.this, StudentHomeAcitvity.class);
                                            i.putExtra("uid",uid);
                                            startActivity(i);
                                        } else if (usertypelogin.equals("Institute")){
                                            String name=dataSnapshot.child("name").getValue().toString();
                                            ((global)getApplication()).setFullname(name);
                                            spinner.setVisibility(View.GONE);

                                            Intent i = new Intent(LoginActivity.this, IndexActivity.class);
                                            i.putExtra("uid",uid);
                                            i.putExtra("userType","institute");
                                            startActivity(i);



                                        }
                                        else {
                                            Log.d("login","Error");
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
                            Toast.makeText(LoginActivity.this,"Please enter valid password",Toast.LENGTH_LONG).show();
                            spinner.setVisibility(View.GONE);
                            email.setEnabled(true);
                            passsword.setEnabled(true);
                        }
                    }
                });
            }else if(action.equals("login")){
                mauth1.signInWithEmailAndPassword(email1, password1).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            if(currentUser!=null){
                                final String uid = currentUser.getUid();
                                notice3 = FirebaseDatabase.getInstance().getReference("Profile/"+uid);
                                notice3.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        profiledatabase profiledatabase=dataSnapshot.getValue(profiledatabase.class);
                                        String usertypelogin=dataSnapshot.child("userType").getValue().toString();
                                        Toast.makeText(LoginActivity.this,"Successfull login ",Toast.LENGTH_SHORT).show();
                                        if(usertypelogin.equals("Teacher")){
                                            String name=dataSnapshot.child("fullname").getValue().toString();
                                            ((global)getApplication()).setFullname(name);
                                            spinner.setVisibility(View.GONE);
                                            Intent i = new Intent(LoginActivity.this, IndexActivity.class);
                                            i.putExtra("uid",uid);
                                            i.putExtra("userType","Teacher");
                                            startActivity(i);
                                        }else if(usertypelogin.equals("institute")){
                                            String name=dataSnapshot.child("name").getValue().toString();
                                            ((global)getApplication()).setFullname(name);
                                            spinner.setVisibility(View.GONE);
                                            Intent i = new Intent(LoginActivity.this, IndexActivity.class);
                                            i.putExtra("uid",uid);
                                            i.putExtra("userType","institute");
                                            startActivity(i);
                                        }
                                        else if(usertypelogin.equals("Student")){
                                            String name=dataSnapshot.child("fullname").getValue().toString();
                                            ((global)getApplication()).setFullname(name);
                                            spinner.setVisibility(View.GONE);
                                            Intent i = new Intent(LoginActivity.this, StudentHomeAcitvity.class);
                                            i.putExtra("uid",uid);
                                            startActivity(i);
                                        }else {
                                            Log.d("login","Error");
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
                            Toast.makeText(LoginActivity.this,"Please enter valid password",Toast.LENGTH_LONG).show();
                            spinner.setVisibility(View.GONE);
                            email.setEnabled(true);
                            passsword.setEnabled(true);
                        }
                    }
                });

            }

        }

//        //<--------------------------register------------------>>
//        forget.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final String email1 = email.getText().toString();
//                spinner.setVisibility(View.VISIBLE);
//                if(email1.isEmpty()) {
//                    email.setError("Please enter email");
//                    email.requestFocus();
//                    spinner.setVisibility(View.GONE);
//                }
//                else {
//                    notice = FirebaseDatabase.getInstance().getReference("Profile/");
//                    notice.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            for(DataSnapshot info:dataSnapshot.getChildren()){
//                                final profiledatabase profiledatabase=info.getValue(profiledatabase.class);
//                                String emailiinfo= null;
//                                if (profiledatabase != null) {
//                                    emailiinfo = profiledatabase.getEmail().trim();
//                                    if(emailiinfo.equals(email1)){
//                                        notice1=FirebaseDatabase.getInstance().getReference("Profile/"+info.getKey());
//                                        notice1.addValueEventListener(new ValueEventListener() {
//                                            @Override
//                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                profiledatabase profiledatabase1=dataSnapshot.getValue(profiledatabase.class);
//                                                String numberinfo=profiledatabase1.getNumbers();
//                                                String passwordinfo=profiledatabase1.getPassword();
//                                                Intent intent= new Intent(login.this, forgetpassword.class);
//                                                intent.putExtra("email",email1);
//                                                intent.putExtra("number",numberinfo);
//                                                intent.putExtra("password",passwordinfo);
//                                                startActivity(intent);
//                                                finish();
//                                                spinner.setVisibility(View.GONE);
//                                            }
//                                            @Override
//                                            public void onCancelled(@NonNull DatabaseError databaseError) {
//                                                Toast.makeText(login.this,"Error"+databaseError,Toast.LENGTH_SHORT).show();
//                                                spinner.setVisibility(View.GONE);
//                                            }
//                                        });
//                                    }else {
//                                        Toast.makeText(login.this,"Email are not Register!",Toast.LENGTH_LONG).show();
//                                        spinner.setVisibility(View.GONE);
//                                    }
//                                }else{
//                                    Toast.makeText(login.this,"profile null",Toast.LENGTH_LONG).show();
//                                    spinner.setVisibility(View.GONE);
//                                }
//                            }
//                        }
//                        @Override
//                        public void onCancelled(DatabaseError error) {
//                            Toast.makeText(login.this,"Please enter valid email"+error,Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            }
//        });

        //<-----------------------login--------------------
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email1 = email.getText().toString().trim();
                final String password1=passsword.getText().toString().trim();
                email.setEnabled(false);
                passsword.setEnabled(false);
                spinner.setVisibility(View.VISIBLE);
                if(email1.isEmpty()){
                    email.setError("Please enter email");
                    email.requestFocus();
                    spinner.setVisibility(View.GONE);
                    email.setEnabled(true);
                    passsword.setEnabled(true);
                } else  if(password1.isEmpty()){
                    passsword.setError("Please enter your password");
                    passsword.requestFocus();
                    spinner.setVisibility(View.GONE);
                    email.setEnabled(true);
                    passsword.setEnabled(true);
                } else if(password1.length()<6){
                    passsword.setError("password too short");
                    passsword.requestFocus();
                    spinner.setVisibility(View.GONE);
                    email.setEnabled(true);
                    passsword.setEnabled(true);
                } else if(!isValid(email1)){
                    email.setError("Email not valid");
                    email.requestFocus();
                    spinner.setVisibility(View.GONE);
                    email.setEnabled(true);
                    passsword.setEnabled(true);
                } else if(!(email1.isEmpty() && password1.isEmpty())){
                    mAuth.signInWithEmailAndPassword(email1, password1).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser currentUser = mAuth.getCurrentUser();
                                if(currentUser!=null){
                                    ((global) getApplication()).setEmailaddrss(email1);
                                    ((global) getApplication()).setPassword(password1);
                                    final String uid = currentUser.getUid();
                                    notice2 = FirebaseDatabase.getInstance().getReference("Profile/"+uid);
                                    notice2.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            profiledatabase profiledatabase=dataSnapshot.getValue(profiledatabase.class);
                                            String usertypelogin=dataSnapshot.child("userType").getValue().toString();
                                           String inCode=dataSnapshot.child("code").getValue().toString();
                                            Toast.makeText(LoginActivity.this,"Successfully login ",Toast.LENGTH_SHORT).show();
                                            if(usertypelogin.equals("Teacher")){
                                                String name=dataSnapshot.child("fullname").getValue().toString();
                                                ((global)getApplication()).setFullname(name);
                                                spinner.setVisibility(View.GONE);
                                                Intent i = new Intent(LoginActivity.this, IndexActivity.class);
                                                i.putExtra("uid",uid);
                                                ((global)getApplication()).setInstituteCode(inCode);
                                                i.putExtra("userType","Teacher");
                                                i.putExtra("code",inCode);
                                                startActivity(i);
                                            }else if(usertypelogin.equals("Student")){
                                                String name=dataSnapshot.child("fullname").getValue().toString();
                                                ((global)getApplication()).setFullname(name);
                                                spinner.setVisibility(View.GONE);
                                                Intent i = new Intent(LoginActivity.this, StudentHomeAcitvity.class);
                                                i.putExtra("uid",uid);
                                                ((global)getApplication()).setInstituteCode(inCode);
                                                i.putExtra("code",inCode);
                                                startActivity(i);
                                            }else if(usertypelogin.equals("institute"))
                                            {
                                                String name=dataSnapshot.child("name").getValue().toString();
                                                ((global)getApplication()).setFullname(name);
                                                spinner.setVisibility(View.GONE);
                                                Intent i = new Intent(LoginActivity.this, IndexActivity.class);
                                                i.putExtra("uid",uid);
                                                ((global)getApplication()).setInstituteCode(inCode);
                                                i.putExtra("code",inCode);
                                                i.putExtra("userType","institute");
                                                startActivity(i);
                                            }
                                            else {
                                                Log.d("login","Error");
                                                spinner.setVisibility(View.GONE);
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
                                Toast.makeText(LoginActivity.this,"Please enter valid password",Toast.LENGTH_LONG).show();
                                spinner.setVisibility(View.GONE);
                                email.setEnabled(true);
                                passsword.setEnabled(true);
                            }
                        }
                    });
                }else{
                    Toast.makeText(LoginActivity.this,"Error Occurred!!!",Toast.LENGTH_LONG).show();
                    spinner.setVisibility(View.GONE);
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
    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}
