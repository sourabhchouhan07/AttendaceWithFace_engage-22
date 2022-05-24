package com.engage.sourabh.attandanceSystem.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.engage.sourabh.attandanceSystem.Model.profiledatabase;
import com.engage.sourabh.attandanceSystem.R;
import com.engage.sourabh.attandanceSystem.global;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashScreen extends AppCompatActivity {
    String inCode="";
    public static int splash_time_out=100;
    private ProgressBar loader;
    private FirebaseAuth mAuth;
    private DatabaseReference notice3,notice1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        loader = findViewById(R.id.progressBar2);

        mAuth = FirebaseAuth.getInstance();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loader.setVisibility(View.VISIBLE);
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if( currentUser != null ){
                    final String uid = currentUser.getUid();
                    Toast.makeText(SplashScreen.this,"You are logged in",Toast.LENGTH_SHORT).show();
                    notice3 = FirebaseDatabase.getInstance().getReference("Profile/"+uid);

                    notice3.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            profiledatabase profiledatabase=dataSnapshot.getValue(profiledatabase.class);
//

                            String usertypelogin=dataSnapshot.child("userType").getValue().toString();
                            inCode=dataSnapshot.child("code").getValue().toString();


                            Toast.makeText(SplashScreen.this,"Successfully login ",Toast.LENGTH_SHORT).show();
                            if(usertypelogin.equals("Teacher")){
                                loader.setVisibility(View.GONE);
                                Intent i = new Intent(SplashScreen.this, IndexActivity.class);
                                i.putExtra("uid",uid);
                                i.putExtra("userType",usertypelogin);
                                i.putExtra("code",inCode);
                                String name=dataSnapshot.child("fullname").getValue().toString();
                                ((global)getApplication()).setInstituteCode(inCode);
                                ((global)getApplication()).setFullname(name);
                                startActivity(i);
                            }else if(usertypelogin.equals("Student")){
                                loader.setVisibility(View.GONE);
                                Intent i = new Intent(SplashScreen.this, StudentHomeAcitvity.class);
                                i.putExtra("uid",uid);
                                ((global)getApplication()).setInstituteCode(inCode);
                                String name=dataSnapshot.child("fullname").getValue().toString();
                                ((global)getApplication()).setFullname(name);
                                i.putExtra("code",inCode);
                                startActivity(i);
                            }else if(usertypelogin.equals("institute")){

                                loader.setVisibility(View.GONE);
                                Intent i = new Intent(SplashScreen.this, IndexActivity.class);
                                i.putExtra("uid",uid);
                                ((global)getApplication()).setInstituteCode(inCode);

                                String name=dataSnapshot.child("name").getValue().toString();
                                ((global)getApplication()).setFullname(name);
                                i.putExtra("code",inCode);
                                i.putExtra("userType",usertypelogin);
                                startActivity(i);

                            }







                            else {
                                loader.setVisibility(View.GONE);
                                Log.d("login","Error");
                                Toast.makeText(SplashScreen.this,"Error not usertype",Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            loader.setVisibility(View.GONE);
                            Log.d("login","Error--"+error);
                            Toast.makeText(SplashScreen.this,"Error"+error,Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
                else{
                    loader.setVisibility(View.GONE);
                    Toast.makeText(SplashScreen.this,"Please Login",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },splash_time_out);
    }
}