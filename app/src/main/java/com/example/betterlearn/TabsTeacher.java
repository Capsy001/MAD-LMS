package com.example.betterlearn;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import StudentFragments.StudentAnnouncements;
import StudentFragments.StudentInstitutes;
import StudentFragments.StudentMyCourses;
import TeacherFragments.AdminAnnouncement;
import TeacherFragments.AdminCourse;


public class TabsTeacher extends Fragment implements View.OnClickListener {

    Button myButton;
    Button myButton1;
    Button myButton2;
    Button myButton3;


    public TabsTeacher() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_tabs_teacher, container, false);


        // Inflate the layout for this fragment


        myButton = (Button) myView.findViewById(R.id.profileT);
        myButton1 = (Button) myView.findViewById(R.id.institutesT);
        myButton2 = (Button) myView.findViewById(R.id.announcementsT);
        myButton3 = (Button) myView.findViewById(R.id.assignmentsT);

        myButton.setOnClickListener(this);
        myButton1.setOnClickListener(this);
        myButton2.setOnClickListener(this);
        myButton3.setOnClickListener(this);


        return myView;


    }

    @Override
    public void onClick(View view) {

        Fragment fragment;

        if(view==myButton) {

            fragment = new DashboardProfile();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.defaultDashboard, fragment);
            ft.commit();
        }

        if(view==myButton1) {

            fragment = new AdminCourse();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.defaultDashboard, fragment);
            ft.commit();


        }
        // Announcements
        if(view==myButton2) {
            fragment = new AdminAnnouncement();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.defaultDashboard, fragment);
            ft.commit();

        }

        if(view==myButton3) {


        }

    }
}