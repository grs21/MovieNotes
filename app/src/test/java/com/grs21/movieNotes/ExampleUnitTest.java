package com.grs21.movieNotes;

import android.widget.ArrayAdapter;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        ArrayList<String> arrData =new ArrayList<>();
        arrData.add("Alpha");
        arrData.add("Beta");
        arrData.add("Gamma");
        arrData.add("Delta");
        arrData.add("Sigma");

        ArrayList<String>addadada=new ArrayList<>();
           addadada.add("xdddd");
           addadada.add("xd");
           arrData.addAll(addadada);

        for (String x:arrData){

            System.out.println(x);

        }

    }
}