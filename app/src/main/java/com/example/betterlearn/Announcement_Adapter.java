package com.example.betterlearn;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import StudentFragments.StudentAnnouncements;
import StudentFragments.StudentAnnouncementsView;
import StudentFragments.StudentInstitutes;
import StudentFragments.StudentInstitutesPage;
import TeacherFragments.AdminAnnouncement;
import TeacherFragments.newAnnouncement;
import models.Announcement;
import models.Institute;

public class Announcement_Adapter extends ArrayAdapter<Announcement> {


    public Announcement_Adapter(@NonNull FragmentActivity context, ArrayList<Announcement> dataModalArrayList) {
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


        // below line is use to add item
        // click listener for our item of list view.
        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on the item click on our list view.
                // we are displaying a toast message.
                Announcement selectedInstitute=(Announcement)v.getTag();

                FragmentTransaction ft =  StudentAnnouncements.fragmentManager.beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                StudentAnnouncementsView fragment2 = new StudentAnnouncementsView();

                Bundle bundle = new Bundle();
                bundle.putSerializable("selected_institute", (Serializable) selectedInstitute);
                fragment2.setArguments(bundle);
                ft.replace(R.id.defaultDashboard, fragment2);
                ft.addToBackStack(null);
                ft.commit();

                Toast.makeText(getContext(), "Item clicked ism : " + dataModal.getKey(), Toast.LENGTH_SHORT).show();
            }
        });
        return listitemView;
    }
}
