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
        input.add(6.0);
        input.add(4.0);

        double result=testCalculator.getAverage(input);

        assertEquals(5, result, 0);
    }




}