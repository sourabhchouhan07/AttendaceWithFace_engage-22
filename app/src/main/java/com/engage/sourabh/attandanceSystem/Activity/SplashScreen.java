package com.engage.sourabh.attandanceSystem.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.engage.sourabh.attandanceSystem.R;
import com.engage.sourabh.attandanceSystem.global;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

public class SplashScreen extends AppCompatActivity {
    String inCode="";
    public static int splash_time_out=2000;
    private ImageView appLogo, pattern1, pattern2,appSlogan;


    //Other Variables
    private Animation topAnimation, bottomAnimation, startAnimation, endAnimation;

    private FirebaseAuth mAuth;
    private DatabaseReference notice3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        mAuth = FirebaseAuth.getInstance();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(getColor(R.color.colorBackground));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        initViews();
        initAnimation();


    }


    private void initViews() {
        //Initialize Views
        appLogo = findViewById(R.id.app_logo);
        pattern1 = findViewById(R.id.pattern1);
        pattern2 = findViewById(R.id.pattern2);
        appSlogan = findViewById(R.id.slogan);

    }

    private void initAnimation() {
        //Initialize Animations
        topAnimation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.splash_top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.splash_bottom_animation);
        startAnimation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.splash_start_animation);
        endAnimation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.splash_end_animation);
    }

    @Override
    protected void onStart() {
        super.onStart();
        int SPLASH_TIMER = 3000;


        //Set Animation To Views
        appLogo.setAnimation(topAnimation);
        pattern1.setAnimation(startAnimation);
        pattern2.setAnimation(endAnimation);
        appSlogan.setAnimation(bottomAnimation);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                FirebaseUser currentUser = mAuth.getCurrentUser();
                if( currentUser != null ){
                    final String uid = currentUser.getUid();

                    notice3 = FirebaseDatabase.getInstance().getReference("Profile/"+uid);

                    notice3.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//

                            String usertypelogin=dataSnapshot.child("userType").getValue().toString();
                            inCode=dataSnapshot.child("code").getValue().toString();

                            DynamicToast.makeSuccess(SplashScreen.this, "Successfully login").show();


                            //NOW selecting Correct User from his Database


                            if(usertypelogin.equals("Teacher")){

                                Intent i = new Intent(SplashScreen.this, IndexActivity.class);
                                i.putExtra("uid",uid);
                                i.putExtra("userType",usertypelogin);
                                i.putExtra("code",inCode);
                                String name=dataSnapshot.child("fullname").getValue().toString();
                                ((global)getApplication()).setInstituteCode(inCode);
                                ((global)getApplication()).setFullname(name);
                                startActivity(i);
                                finish();
                            }else if(usertypelogin.equals("Student")){

                                Intent i = new Intent(SplashScreen.this, StudentHomeAcitvity.class);
                                i.putExtra("uid",uid);
                                ((global)getApplication()).setInstituteCode(inCode);
                                String name=dataSnapshot.child("fullname").getValue().toString();
                                ((global)getApplication()).setFullname(name);
                                i.putExtra("code",inCode);
                                startActivity(i);
                                finish();
                            }else if(usertypelogin.equals("institute")){


                                Intent i = new Intent(SplashScreen.this, IndexActivity.class);
                                i.putExtra("uid",uid);
                                ((global)getApplication()).setInstituteCode(inCode);

                                String name=dataSnapshot.child("fullname").getValue().toString();
                                ((global)getApplication()).setFullname(name);
                                i.putExtra("code",inCode);
                                i.putExtra("userType",usertypelogin);
                                startActivity(i);
                                finish();

                            }
                            else {
                                Log.d("login","Error");
                                Toast.makeText(SplashScreen.this,"Error not usertype",Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                            Log.d("login","Error--"+error);
                            Toast.makeText(SplashScreen.this,"Error"+error,Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
                else{

                    Toast.makeText(SplashScreen.this,"Please Login",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },splash_time_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
