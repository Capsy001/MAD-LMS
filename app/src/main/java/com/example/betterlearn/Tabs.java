package com.example.betterlearn;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Tabs extends Fragment implements View.OnClickListener {

    Button myButton;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_tabs, container, false);
        myButton = (Button) myView.findViewById(R.id.mycourses);
        myButton.setOnClickListener(this);
        return myView;

        // Inflate the layout for this fragment



    }


    @Override
    public void onClick(View view) {

        Fragment fragment;

            fragment = new AdminAssignments();
            FragmentManager fm=getFragmentManager();
            FragmentTransaction ft=fm.beginTransaction();
            ft.replace(R.id.defaultDashboard,fragment);
            ft.commit();
        }


}



