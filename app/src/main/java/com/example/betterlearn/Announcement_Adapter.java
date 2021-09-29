package com.example.betterlearn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

import TeacherFragments.AdminAnnouncement;
import models.Announcement;

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
                Toast.makeText(getContext(), "Item clicked ism : " + dataModal.getKey(), Toast.LENGTH_SHORT).show();
            }
        });
        return listitemView;
    }
}
