package com.example.betterlearn;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
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
    Double grade=null;


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

    EditText gradeEdit;
    Button gradeAdd;
    Button gradeRst;
    TextView gradeDisp;

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




        //Mode
        modeEdit=root.findViewById(R.id.editMode);
        modeAdd=root.findViewById(R.id.addMode);
        modeRst=root.findViewById(R.id.resetMode);
        modeDisp=root.findViewById(R.id.displayMode);

        modeEdit.setText("");
        modeDisp.setText("0");


        modeAdd.setOnClickListener(this);
        modeRst.setOnClickListener(this);


        //Grade
        gradeEdit=root.findViewById(R.id.editLast);
        gradeAdd=root.findViewById(R.id.addLast);
        gradeRst=root.findViewById(R.id.resetLast);
        gradeDisp=root.findViewById(R.id.displayLast);

        gradeEdit.setText("");
        gradeDisp.setText("0");


        gradeAdd.setOnClickListener(this);
        gradeRst.setOnClickListener(this);


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

            if(averageEdit.getText().toString().isEmpty()){
                Toast.makeText(getActivity(), "Empty input!", Toast.LENGTH_SHORT).show();
                return;

            }

            double value;
            String text =averageEdit.getText().toString();
            if(!text.isEmpty())
                try
                {
                    value= Double.parseDouble(text);
                    // it means it is double

                    averages.add(value);
                    Double result=getAverage(averages);

                    result=Math.round(result*100.0)/100.0;

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

            if(sumEdit.getText().toString().isEmpty()){
                Toast.makeText(getActivity(), "Empty input!", Toast.LENGTH_SHORT).show();
                return;

            }

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

            if(modeEdit.getText().toString().isEmpty()){
                Toast.makeText(getActivity(), "Empty input!", Toast.LENGTH_SHORT).show();
                return;

            }

            double value;
            String text =modeEdit.getText().toString();
            if(!text.isEmpty())
                try
                {
                    value= Double.parseDouble(text);
                    // it means it is double

                    modes.add(value);
                    Double result=getMode(modes);

                    Log.d("TAG","Mooooooooooooooooooooooode :"+result);

                    result=Math.round(result*100.0)/100.0;

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



        //Grade function
        if(view==gradeAdd){

            if(gradeEdit.getText().toString().isEmpty()){
                Toast.makeText(getActivity(), "Empty input!", Toast.LENGTH_SHORT).show();
                return;

            }

            double value;
            String text =gradeEdit.getText().toString();
            if(!text.isEmpty())
                try
                {
                    value= Double.parseDouble(text);
                    // it means it is double

                    grade=value;
                    String result=getGrade(grade);

                    gradeDisp.setText(result);
                    gradeEdit.setText("");


                } catch (Exception e1) {
                    // this means it is not double
                    e1.printStackTrace();
                    Toast.makeText(getActivity(), "Invalid input!", Toast.LENGTH_SHORT).show();
                }




        }

        if(view==gradeRst){

            gradeEdit.setText("");
            gradeDisp.setText("");

        }




    }

    public String getGrade(Double grade) {
        String result;

        if(grade > 100 || grade<0){
            result= "Invalid";
        }
        else if(grade >= 90 && grade<=100){
            result= "A+";
        }else if(grade < 90 && grade >= 80){
            result= "A";
        }else if(grade < 80 && grade >= 75){
            result= "A-";
        }else if(grade <75 && grade >= 70){
            result= "B+";
        }else if(grade < 70 && grade >= 65){
            result= "B";
        }else if(grade < 65 && grade >= 60){
            result= "B-";
        }else if(grade < 60 && grade >= 55){
            result= "C+";
        }else if(grade < 55 && grade >= 45){
            result= "C";
        }else {
            result= "F";
        }
            //




        return result;
    }

    public Double getMode(ArrayList<Double> modes) {

       // Toast.makeText(getActivity(), "last entered: "+modes.get(modes.size()-1), Toast.LENGTH_SHORT).show();

        Double mul=1.0;

        for(Double x : modes){
            mul*=x;
        }

        return mul;

    }

    public Double getSum(ArrayList<Double> sums) {

        Double sum=0.0;

        for(Double x : sums){
            sum+=x;
        }

        return sum;
    }
}