package com.grs21.movieNotes.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TxtFileReader {
    private static final String FILE_ROOT="/data/user/0/com.grs21.movienotes/files/movie_id.txt";
    private ArrayList<String> movieID=new ArrayList<>();



    public synchronized ArrayList<String> read(){

        File file=new File(FILE_ROOT);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                scanner.useDelimiter(" ");
                movieID.add(scanner.next());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return movieID;

    }

}
