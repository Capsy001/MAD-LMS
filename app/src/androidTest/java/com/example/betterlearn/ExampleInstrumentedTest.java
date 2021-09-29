package com.example.betterlearn;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleInstrumentedTest {


    private Calculator testCalculator;

    @Before
    public void initialize() {
        this.testCalculator = new Calculator();
    }


    @Test
    public void testAverage(){
        ArrayList<Double> input=new ArrayList<Double>();
        input.add(5.0);
        input.add(7.0);
        input.add(4.0);

        Double result=testCalculator.getAverage(input);

        assertEquals(5.33, result, 0.01);
    }

    @Test
    public void testSum(){
        ArrayList<Double> input=new ArrayList<Double>();
        input.add(20.0);
        input.add(30.4);
        input.add(40.56);

        Double result=testCalculator.getSum(input);
        //result=Math.round(result*100.0)/100.0;




        assertEquals(90.96, result, 0.01);
    }


    @Test
    public void testMode(){

        ArrayList<Double> input=new ArrayList<Double>();
        input.add(1.0);
        input.add(1.0);
        input.add(1.0);
        input.add(9.0);
        input.add(1.0);
        input.add(8.0);
        input.add(9.0);
        input.add(7.0);
        input.add(5.0);
        input.add(9.0);
        input.add(2.0);

        Double result=testCalculator.getMode(input);

        assertEquals(1.0, result, 0);



    }




}