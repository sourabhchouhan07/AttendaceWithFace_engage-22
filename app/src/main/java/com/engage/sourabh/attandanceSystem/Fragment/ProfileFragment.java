package com.engage.sourabh.attandanceSystem.Fragment;

import static android.view.View.GONE;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.engage.sourabh.attandanceSystem.Activity.LoginActivity;
import com.engage.sourabh.attandanceSystem.Model.profiledatabase;
import com.engage.sourabh.attandanceSystem.R;
import com.engage.sourabh.attandanceSystem.global;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileFragment extends Fragment {

    private TextView userfullName, showCourses;


    private TextView BOD, Contacts, emails;

    private TextView Addresses, uidOfUser, div;

    private TextView rollnumber, usertype, rollt;

    private TextView divt, showdegreet, showDegree,showDob,showCourse,showAddress;

    private ImageView profile;


    Button logout;
    private ProgressBar progrssBar;

    @Nullable
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_profile , container , false);


        logout = fragmentView.findViewById(R.id.logout);
        profile = fragmentView.findViewById(R.id.imageView2);
             showAddress=fragmentView.findViewById(R.id.showaddress);
             showCourse=fragmentView.findViewById(R.id.showcourse);
             showDob=fragmentView.findViewById(R.id.showdob);
        progrssBar = fragmentView.findViewById(R.id.profilepb);
        div = fragmentView.findViewById(R.id.div);
        rollnumber = fragmentView.findViewById(R.id.rollnumber);
        usertype = fragmentView.findViewById(R.id.usertype);
        divt = fragmentView.findViewById(R.id.divt);
        rollt = fragmentView.findViewById(R.id.rollt);
        showDegree = fragmentView.findViewById(R.id.showdegree);
        showdegreet = fragmentView.findViewById(R.id.showdegreet);
        userfullName = fragmentView.findViewById(R.id.full_name);
        showCourses = fragmentView.findViewById(R.id.showcource);
        BOD = fragmentView.findViewById(R.id.Birthofdate);
        Contacts = fragmentView.findViewById(R.id.Contact);
        emails = fragmentView.findViewById(R.id.mail);
        Addresses = fragmentView.findViewById(R.id.address);



        showDegree.setVisibility(GONE);
        showdegreet.setVisibility(GONE);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {

                FirebaseAuth mauth = FirebaseAuth.getInstance();
                mauth.signOut();
                Intent i = new Intent(getContext() , LoginActivity.class);
                startActivity(i);
            }
        });

        return fragmentView;
    }

    @Override
    public void onStart () {
        super.onStart();


        progrssBar.setVisibility(View.VISIBLE);
        String loginid = ((global) requireActivity().getApplication()).getUid();


        DatabaseReference dbRef;
        dbRef = FirebaseDatabase.getInstance().getReference("Profile/" + loginid);
        dbRef.addValueEventListener(new ValueEventListener() {



            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                profiledatabase userDatabaseSet = dataSnapshot.getValue(profiledatabase.class);
                assert userDatabaseSet != null;


                userfullName.setText(userDatabaseSet.getFullname());
                usertype.setText(" User : " + userDatabaseSet.getUserType());

                String ust = userDatabaseSet.getUserType();

                if (ust.equals("institute")) {
                    profile.setImageResource(R.drawable.instititue);

                } else if (ust.equals("Teacher")) {
                    profile.setImageResource(R.drawable.teachepic);

                } else {
                    profile.setImageResource(R.drawable.stdentpic);
                }
                if (userDatabaseSet.getCourece() != null) {
                    showCourses.setText(userDatabaseSet.getCourece());
                }

                if (userDatabaseSet.getDivision() == null) {
                    div.setVisibility(GONE);
                    divt.setVisibility(GONE);
                }
                div.setText(userDatabaseSet.getDivision());
                if (userDatabaseSet.getRollnumber() == null) {
                    rollnumber.setVisibility(GONE);
                    rollt.setVisibility(GONE);
                }
                
                if (userDatabaseSet.getCourse() != null) {
                    showCourses.setText(userDatabaseSet.getCourse());
                }
                if (userDatabaseSet.getDegree() != null) {
                    showDegree.setVisibility(View.VISIBLE);
                    showDegree.setText(userDatabaseSet.getDegree());
                    showdegreet.setVisibility(View.VISIBLE);
                }



                if(userDatabaseSet.getBirthofdate()==null){
                    showDob.setVisibility(View.INVISIBLE);
                }


                if(userDatabaseSet.getCourse()==null && userDatabaseSet.getCourece()==null){
                    showCourse.setVisibility(View.INVISIBLE);
                }

                if(userDatabaseSet.getAddresss()==null){
                    showAddress.setVisibility(View.INVISIBLE);
                }



                BOD.setText(userDatabaseSet.getBirthofdate());
                Contacts.setText(userDatabaseSet.getNumbers());
                emails.setText(userDatabaseSet.getEmail());
                Addresses.setText(userDatabaseSet.getAddresss());


                rollnumber.setText(userDatabaseSet.getRollnumber());
                progrssBar.setVisibility(GONE);
            }

            @Override
            public void onCancelled (@NonNull DatabaseError error) {
                Toast.makeText(getContext() , "error" + error , Toast.LENGTH_LONG).show();
                progrssBar.setVisibility(GONE);
            }
        });

    }
}
