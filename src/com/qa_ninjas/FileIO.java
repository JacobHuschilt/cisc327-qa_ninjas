package com.qa_ninjas;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by jacobhuschilt on 10/17/17.
 */
public class FileIO {

    public static ArrayList readFile(String filename) {
        ArrayList<String> fileContents = new ArrayList<>();
        File file = new File(filename);
        BufferedReader in = null;
        boolean done = false; // Used to indicate when there's no more data in the file


        try {
            in = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException error) {
            System.out.println("The file cannot be opened: " + error);
        }

        // Read the File one line at a time
        do {
            try {
                String line = in.readLine();

                if (line == null) {
                    done = true;
                } else {
                    fileContents.add(line);
                }
            } catch (IOException error) {
                System.out.println("Error reading file: " + error);
            }
        } while (!done);

        try {
            in.close();
        } catch (IOException error) {
            System.out.println("Error closing file: " + error);
        }

        return fileContents;
    }

    protected void writeToFile(String filename, ArrayList contents) {
        // TODO: Wrtie to file
    }
}
