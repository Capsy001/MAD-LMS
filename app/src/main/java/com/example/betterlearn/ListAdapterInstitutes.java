package com.example.betterlearn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

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

        title.setText(institute.getInstituteName());    //Get the title of each institute


        return convertView;
    }
}
