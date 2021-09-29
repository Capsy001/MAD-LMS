package com.example.betterlearn;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

import StudentFragments.StudentInstitutes;
import StudentFragments.StudentInstitutesPage;
import StudentFragments.StudentMyCourses;
import StudentFragments.StudentMyCoursesPage;
import models.Content;
import models.Institute;

public class ContentAdapter  extends ArrayAdapter<Content> {

    public ContentAdapter(Context context, ArrayList<Content> contents) {
        super(context, 0, contents);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Content content=getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.recycler_content, parent, false);
        }

        TextView title = (TextView) convertView.findViewById(R.id.content_title);
        TextView description = (TextView) convertView.findViewById(R.id.content_description);

        title.setText(content.getTitle());
        description.setText(content.getDescription());


        View list_item=(View)convertView.findViewById(R.id.contentSMC);

        Content contentx=getItem(position);
        list_item.setTag(contentx);

        list_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Content selectedContent=(Content) view.getTag();


                FragmentTransaction ft =  StudentMyCourses.fragmentManager.beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                StudentMyCoursesPage fragment2 = new StudentMyCoursesPage();

                Bundle bundle = new Bundle();
                Content obj = selectedContent;
                bundle.putSerializable("selected_content", obj);
                fragment2.setArguments(bundle);
                ft.replace(R.id.defaultDashboard, fragment2);
                ft.addToBackStack(null);
                ft.commit();




            }
        });

        return convertView;

    }
}
