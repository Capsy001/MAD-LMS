package com.example.betterlearn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

import TeacherFragments.AdminAssignments;
import TeacherFragments.TeacherAssigmentsView;
import models.Assigments;

public class Assigment_Adapter extends ArrayAdapter<Assigments> {


    public Assigment_Adapter(@NonNull FragmentActivity context, ArrayList<Assigments> dataModalArrayList) {
        super(context, 0, dataModalArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // below line is use to inflate the
        // layout for our item of list view.
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.assigments_items, parent, false);
        }

        // after inflating an item of listview item
        // we are getting data from array list inside
        // our modal class.
        Assigments dataModal = getItem(position);
        // initializing our UI components of list view item.
        TextView Titlelist = listitemView.findViewById(R.id.assign_title);
        TextView Descriptionlist = listitemView.findViewById(R.id.assign_description);
        TextView Deadlinelist = listitemView.findViewById(R.id.assign_deadline);

        // after initializing our items we are
        // setting data to our view.
        // below line is use to set data to our text view.
        Titlelist.setText(dataModal.getTitle());
        Descriptionlist.setText(dataModal.getDescription());
        Deadlinelist.setText(dataModal.getDeadline());
        listitemView.setTag(dataModal);

        // below line is use to add item
        // click listener for our item of list view.
        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on the item click on our list view.
                // we are displaying a toast message.
                Assigments selectedAssignmnt=(Assigments)v.getTag();

                FragmentTransaction ft =  AdminAssignments.fragmentManager.beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                TeacherAssigmentsView fragment2 = new TeacherAssigmentsView();

                Bundle bundle = new Bundle();
                bundle.putSerializable("Show_Assigments",selectedAssignmnt);
                fragment2.setArguments(bundle);
                ft.replace(R.id.defaultDashboard, fragment2);
                ft.addToBackStack(null);
                ft.commit();

            }
        });
        return listitemView;
    }
}
