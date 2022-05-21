package com.engage.sourabh.attandanceSystem.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.engage.sourabh.attandanceSystem.Fragment.InstituteHomeFragment;
import com.engage.sourabh.attandanceSystem.Fragment.NotificationFragment;
import com.engage.sourabh.attandanceSystem.Fragment.ProfileFragment;
import com.engage.sourabh.attandanceSystem.Fragment.TeacherHomeFragment;
import com.engage.sourabh.attandanceSystem.R;
import com.engage.sourabh.attandanceSystem.global;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class IndexActivity extends AppCompatActivity {
         //variable initialization
    MeowBottomNavigation bottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        bottomNavigation =findViewById(R.id.bottom_navigation);

        //Add menu item to nav
        Intent intent=getIntent();
        String uids=intent.getStringExtra("uid");
        String code=intent.getStringExtra("code");
        String userType=intent.getStringExtra("userType");
        ((global) getApplication()).setUid(uids);
        ((global)getApplication()).setInstituteCode(code);

        ((global)getApplication()).setUsertype(userType);



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
                         if(userType.equals("institute")){
                             fragment=new InstituteHomeFragment();
                         }else {
                             fragment = new TeacherHomeFragment();
                         }
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
                Toast.makeText(getApplicationContext(),"you clicked"+item.getId(),
                        Toast.LENGTH_LONG).show();
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