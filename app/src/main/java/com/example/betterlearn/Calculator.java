package com.example.betterlearn;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class Calculator extends Fragment implements View.OnClickListener {

    ArrayList<Double> averages=new ArrayList<Double>();
    ArrayList<Double> sums=new ArrayList<Double>();
    ArrayList<Double> modes=new ArrayList<Double>();
    ArrayList<Double> lasts=new ArrayList<Double>();


    EditText averageEdit;
    Button averageAdd;
    Button averageRst;
    TextView averageDisp;

    public Calculator() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View root= inflater.inflate(R.layout.fragment_calculator, container, false);

        averages.clear();
        sums.clear();
        modes.clear();
        lasts.clear();




        averageEdit=root.findViewById(R.id.editAverage);
        averageAdd=root.findViewById(R.id.addAverage);
        averageRst=root.findViewById(R.id.resetAverage);
        averageDisp=root.findViewById(R.id.displayAverage);

        averageEdit.setText("");
        averageDisp.setText("0");


        averageAdd.setOnClickListener(this);
        averageRst.setOnClickListener(this);






       return root;
    }

    public double getAverage(ArrayList<Double> aver){

        Double result=null;
        Double sum=0.0;

        double i=1;

        for(Double x : aver){
            sum+=x;

        }

        result=sum/aver.size();



        return result;


    }


    @Override
    public void onClick(View view) {

        if(view==averageAdd){

            double value;
            String text =averageEdit.getText().toString();
            if(!text.isEmpty())
                try
                {
                    value= Double.parseDouble(text);
                    // it means it is double

                    averages.add(value);
                    Double result=getAverage(averages);

                    averageDisp.setText(Double.toString(result));
                    averageEdit.setText("");


                } catch (Exception e1) {
                    // this means it is not double
                    e1.printStackTrace();
                    Toast.makeText(getActivity(), "Invalid input!", Toast.LENGTH_SHORT).show();
                }




        }

        if(view==averageRst){

            averageEdit.setText("");
            averageDisp.setText("");
            averages.clear();


        }



    }
}