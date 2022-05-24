package com.engage.sourabh.attandanceSystem.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.engage.sourabh.attandanceSystem.Fragment.NotificationFragment;
import com.engage.sourabh.attandanceSystem.Fragment.ProfileFragment;
import com.engage.sourabh.attandanceSystem.Fragment.StudentHomeFragment;
import com.engage.sourabh.attandanceSystem.Model.profiledatabase;
import com.engage.sourabh.attandanceSystem.R;
import com.engage.sourabh.attandanceSystem.global;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentHomeAcitvity extends AppCompatActivity {

    MeowBottomNavigation bottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_acitvity);

        bottomNavigation =findViewById(R.id.bottom_navigation);

        //Addm menu item to nav
        Intent intent=getIntent();
        String uids=intent.getStringExtra("uid");
        String code=intent.getStringExtra("code");
        String userType=intent.getStringExtra("userType");
        ((global) getApplication()).setUid(uids);
        ((global)getApplication()).setInstituteCode(code);

        ((global)getApplication()).setUsertype(userType);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Profile/"+uids);



        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                profiledatabase profiledatabase=dataSnapshot.getValue(profiledatabase.class);
                assert profiledatabase != null;
                String fullnameindex=profiledatabase.getFullname();
                String usertype=profiledatabase.getUserType();
                String email=profiledatabase.getEmail();

                String address=profiledatabase.getAddresss();
                String birthofdate=profiledatabase.getBirthofdate();
                String number=profiledatabase.getNumbers();

                String course=profiledatabase.getCourse();
                String rollnumber=profiledatabase.getRollnumber();
                String year=profiledatabase.getYear();
                String division=profiledatabase.getDivision();
                   String password1=profiledatabase.getPassword();
                ((global)getApplication()).setPassword(password1);
                ((global) getApplication()).setDivision(division);
                ((global) getApplication()).setCourse(course);
                ((global) getApplication()).setRollnumber(rollnumber);
                ((global) getApplication()).setYear(year);

                ((global) getApplication()).setFullname(fullnameindex);
                ((global) getApplication()).setAddress(address);
                ((global) getApplication()).setBirthofdate(birthofdate);
                ((global) getApplication()).setNumber(number);

                ((global) getApplication()).setUsertype(usertype);
                ((global) getApplication()).setEmail(email);
                Log.d("student","detail"+division+course+rollnumber+year+fullnameindex+address+birthofdate+number+usertype+email);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(StudentHomeAcitvity.this,"Error"+error,Toast.LENGTH_LONG).show();
                Log.d("index","Error"+error);
            }
        });



        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_notification));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_profile));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                //initialize Fragment
                Fragment fragment=null;
                switch (item.getId()){

                    case 1:
                        fragment=new NotificationFragment();
                        break;

                    case 2:
                        fragment=new StudentHomeFragment();
                        break;

                    case 3:
                        fragment=new ProfileFragment();
                        break;
                }

                //load fragment
                loadFragment(fragment);

            }
        });

        //set NOtification count

        bottomNavigation.setCount(1,"10");

        //set home fragment default

        bottomNavigation.show(2,true);

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                //display Toast
//                Toast.makeText(getApplicationContext(),"you clicked"+item.getId(),
//                        Toast.LENGTH_LONG).show();
            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
//                Toast.makeText(getApplicationContext(),"you  re clicked"+item.getId(),
//                        Toast.LENGTH_LONG).show();
            }
        });

    }

    private void loadFragment(Fragment fragment) {

        //replace fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout_index,fragment)
                .commit();

    }
}