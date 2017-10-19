package com.qa_ninjas;

import java.io.*;
import java.util.ArrayList;

/**
 * Class to handle all File I/O operations.
 *
 * Created by jacobhuschilt on 10/17/17.
 */
public class FileIO {

    static ArrayList readFile(String filename) {
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
                String line;

                if (in != null) {
                    line = in.readLine();
                    fileContents.add(line);
                } else {
                    done = true;
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

    protected void writeToFile(String filename, ArrayList<String> contents) {
        try {
            PrintWriter write = new PrintWriter(new FileWriter(filename));
            for (String line : contents) {
                write.println(line); // write the line to the file
            }
            write.close();
        } catch (IOException error) {
            System.out.println("Error writing to the file");
        }
    }
}
