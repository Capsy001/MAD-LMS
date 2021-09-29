package com.example.betterlearn;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

import StudentFragments.StudentAnnouncements;
import StudentFragments.StudentAnnouncementsView;
import TeacherFragments.AdminAnnouncement;
import TeacherFragments.TeacherAnnouncementsView;
import models.Announcement;

public class Admin_Announcement_Adapter extends ArrayAdapter<Announcement> {


    public Admin_Announcement_Adapter(@NonNull FragmentActivity context, ArrayList<Announcement> dataModalArrayList) {
        super(context, 0, dataModalArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // below line is use to inflate the
        // layout for our item of list view.
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.announcements_items, parent, false);
        }

        // after inflating an item of listview item
        // we are getting data from array list inside
        // our modal class.
        Announcement dataModal = getItem(position);
        // initializing our UI components of list view item.
        TextView Titlelist = listitemView.findViewById(R.id.textView21_dup1);
        TextView Descriptionlist = listitemView.findViewById(R.id.textView22_dup1);

        // after initializing our items we are
        // setting data to our view.
        // below line is use to set data to our text view.
        Titlelist.setText(dataModal.getTitle());
        Descriptionlist.setText(dataModal.getDescription());
        listitemView.setTag(dataModal);

        // below line is use to add item
        // click listener for our item of list view.
        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on the item click on our list view.
                // we are displaying a toast message.

                Announcement selectedInstitute=(Announcement)v.getTag();

                FragmentTransaction ft =  AdminAnnouncement.fragmentManager.beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                TeacherAnnouncementsView fragment2 = new TeacherAnnouncementsView();

                Bundle bundle = new Bundle();
                bundle.putSerializable("selected_institute1",selectedInstitute);
                fragment2.setArguments(bundle);
                ft.replace(R.id.defaultDashboard, fragment2);
                ft.addToBackStack(null);
                ft.commit();

            }


        });
        return listitemView;
    }
}
