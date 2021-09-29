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

    EditText sumEdit;
    Button sumAdd;
    Button sumRst;
    TextView sumDisp;

    EditText modeEdit;
    Button modeAdd;
    Button modeRst;
    TextView modeDisp;

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



        //Average
        averageEdit=root.findViewById(R.id.editAverage);
        averageAdd=root.findViewById(R.id.addAverage);
        averageRst=root.findViewById(R.id.resetAverage);
        averageDisp=root.findViewById(R.id.displayAverage);

        averageEdit.setText("");
        averageDisp.setText("0");


        averageAdd.setOnClickListener(this);
        averageRst.setOnClickListener(this);


        //Sum
        sumEdit=root.findViewById(R.id.editSum);
        sumAdd=root.findViewById(R.id.addSum);
        sumRst=root.findViewById(R.id.resetSum);
        sumDisp=root.findViewById(R.id.displaySum);

        sumEdit.setText("");
        sumDisp.setText("0");

        sumAdd.setOnClickListener(this);
        sumRst.setOnClickListener(this);




        //Average
        modeEdit=root.findViewById(R.id.editMode);
        modeAdd=root.findViewById(R.id.addMode);
        modeRst=root.findViewById(R.id.resetMode);
        modeDisp=root.findViewById(R.id.displayMode);

        modeEdit.setText("");
        modeDisp.setText("0");


        modeAdd.setOnClickListener(this);
        modeRst.setOnClickListener(this);


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



        //sum function
        if(view==sumAdd){

            double value;
            String text =sumEdit.getText().toString();
            if(!text.isEmpty())
                try
                {
                    value= Double.parseDouble(text);
                    // it means it is double

                    sums.add(value);
                    Double result=getSum(sums);

                    sumDisp.setText(Double.toString(result));
                    sumEdit.setText("");


                } catch (Exception e1) {
                    // this means it is not double
                    e1.printStackTrace();
                    Toast.makeText(getActivity(), "Invalid input!", Toast.LENGTH_SHORT).show();
                }




        }

        if(view==sumRst){

            sumEdit.setText("");
            sumDisp.setText("");
            sums.clear();


        }


        //Mode function

        if(view==modeAdd){

            double value;
            String text =modeEdit.getText().toString();
            if(!text.isEmpty())
                try
                {
                    value= Double.parseDouble(text);
                    // it means it is double

                    modes.add(value);
                    Double result=getMode(modes);

                    modeDisp.setText(Double.toString(result));
                    modeEdit.setText("");


                } catch (Exception e1) {
                    // this means it is not double
                    e1.printStackTrace();
                    Toast.makeText(getActivity(), "Invalid input!", Toast.LENGTH_SHORT).show();
                }




        }

        if(view==modeRst){

            modeEdit.setText("");
            modeDisp.setText("");
            modes.clear();


        }




    }

    public Double getMode(ArrayList<Double> modes) {

        Double mode = modes.get(0);
        int maxCount = 0;

        for (int i = 0; i < modes.size(); i++) {

            Double value = modes.get(i);

            int count = 0;
            for (int j = 0; j < modes.size(); j++) {
                if (modes.get(j) == value) {
                    count++;
                }


                if (count > maxCount) {
                    mode = value;
                    maxCount = count;
                }
            }
        }

            return mode;


    }

    public Double getSum(ArrayList<Double> sums) {

        Double sum=0.0;

        for(Double x : sums){
            sum+=x;
        }

        return sum;
    }
}