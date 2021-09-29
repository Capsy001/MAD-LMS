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
import StudentFragments.StudentAssignments;
import StudentFragments.StudentInstitutes;
import StudentFragments.StudentMyCourses;


public class TabsStudent extends Fragment implements View.OnClickListener {

    Button myButton;
    Button myButton1;
    Button myButton2;
    Button myButton3;
    Button myButton4;
    Button myButton5;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_tabs, container, false);
        myButton = (Button) myView.findViewById(R.id.mycoursesS);

        myButton = (Button) myView.findViewById(R.id.mycoursesS);
        myButton1 = (Button) myView.findViewById(R.id.profileS);
        myButton2 = (Button) myView.findViewById(R.id.institutesS);
        myButton3 = (Button) myView.findViewById(R.id.announcementsS);
        myButton4 = (Button) myView.findViewById(R.id.assignmentsS);
        myButton5 = (Button) myView.findViewById(R.id.calculatorStudent);

        myButton.setOnClickListener(this);
        myButton1.setOnClickListener(this);
        myButton2.setOnClickListener(this);
        myButton3.setOnClickListener(this);
        myButton4.setOnClickListener(this);
        myButton5.setOnClickListener(this);

        return myView;

        // Inflate the layout for this fragment



    }


    @Override
    public void onClick(View view) {

        Fragment fragment;

        if(view==myButton) {

            fragment = new StudentMyCourses();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.defaultDashboard, fragment);
            ft.commit();
        }

        if(view==myButton1) {

            fragment = new DashboardProfile();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.defaultDashboard, fragment);
            ft.commit();
        }

        if(view==myButton2) {

            fragment = new StudentInstitutes();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.defaultDashboard, fragment);
            ft.commit();
        }

        if(view==myButton3) {

            fragment = new StudentAnnouncements();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.defaultDashboard, fragment);
            ft.commit();
        }

        if(view==myButton4) {

            fragment = new StudentAssignments();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.defaultDashboard, fragment);
            ft.commit();
        }

        if(view==myButton5) {

            fragment = new Calculator();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.defaultDashboard, fragment);
            ft.commit();
        }


        }


}



