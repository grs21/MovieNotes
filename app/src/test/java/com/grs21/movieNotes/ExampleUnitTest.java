package com.grs21.movieNotes;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.grs21.movieNotes.activity.MainActivity;

import org.junit.Test;
import org.junit.runner.manipulation.Ordering;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        Path path= Paths.get("./trying.txt");

       try {
           Files.createFile(path);

       }catch (IOException e){
           e.printStackTrace();
       }

    }
    @Test
    public void reader(){
        ArrayList<String> strings=new ArrayList<>();
        ArrayList<String> totalArray=new ArrayList<>();
        String text="?529203?646593?278?238?602211?713825?524047?753926?424";
        char[] text1=text.toCharArray();

        int a=0;
        for (int i = 1; i < text1.length; i++) {

            strings.add(i,String.valueOf( text1[i]));

            if (text1[i]=='?'){
                }

            System.out.println(a);
            if (text1[i]=='?'){
                a++;
                System.out.println(a);
                if (a==2){
                    totalArray.add(strings.toString());
                    a=0;
                    System.out.println(a);
                }
            }

        }

        System.out.println(totalArray);


    }
}