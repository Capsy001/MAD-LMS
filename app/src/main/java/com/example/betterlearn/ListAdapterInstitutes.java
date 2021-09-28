package com.example.betterlearn;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import StudentFragments.StudentDashboard;
import StudentFragments.StudentInstitutes;
import StudentFragments.StudentInstitutesPage;
import models.Institute;

public class ListAdapterInstitutes extends ArrayAdapter<Institute> {


    public ListAdapterInstitutes(@NonNull Context context, ArrayList<Institute> instituteList) {

        super(context, R.layout.recycler_item_institute, instituteList);


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Institute institute=getItem(position);

        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.recycler_item_institute,parent,false);


        }

        ImageView img= (ImageView)convertView.findViewById(R.id.recycler_image);
        TextView title= (TextView)convertView.findViewById(R.id.recycler_title);
   //     TextView desc= (TextView)convertView.findViewById(R.id.recycler_description);



        View view1 = (View) convertView.findViewById(R.id.listViewRoot);

        Institute institutex=getItem(position);

        view1.setTag(institutex);

        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Institute selectedInstitute=(Institute)view.getTag();



                FragmentTransaction ft =  StudentInstitutes.fragmentManager.beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                StudentInstitutesPage fragment2 = new StudentInstitutesPage();

                Bundle bundle = new Bundle();
                Institute obj = selectedInstitute;
                bundle.putSerializable("selected_institute", obj);
                fragment2.setArguments(bundle);
                ft.replace(R.id.defaultDashboard, fragment2);
                ft.addToBackStack(null);
                ft.commit();


            }
        });


        title.setText(institute.getInstituteName());    //Get the title of each institute

        Glide.with(img.getContext())
                .load(institute.getImage_url())
                .centerCrop()
                .into(img);

        return convertView;
    }
}
