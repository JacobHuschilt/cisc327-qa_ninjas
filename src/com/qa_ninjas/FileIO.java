package com.qa_ninjas;

import java.io.*;
import java.util.ArrayList;

/**
 * Class to handle all File I/O operations.
 *
 * Created by jacobhuschilt on 10/17/17.
 */
public class FileIO {

    /**
     * Reads the contents of a specified file.
     *
     * @param filename a given filename
     * @return the contents of the file
     */
    static ArrayList<String> readFile(String filename) {
        ArrayList<String> fileContents = new ArrayList<>();
        File file = new File(filename);
        BufferedReader in = null;
        boolean done = false; // Used to indicate when there's no more data in the file


        try {
            in = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException error) {
            Main.terminalOutput.add("The file cannot be opened: " + error);
        }

        // Read the File one line at a time
        do {
            try {
                String line;

                if (in != null) {
                    line = in.readLine();
                    if (line != null) {
                        fileContents.add(line);
                    } else {
                        done = true;
                    }
                } else {
                    done = true;
                }
            } catch (IOException error) {
                Main.terminalOutput.add("Error reading file: " + error);
            }
        } while (!done);

        try {
            in.close();
        } catch (IOException error) {
            Main.terminalOutput.add("Error closing file: " + error);
        } catch (NullPointerException e) {
            Main.terminalOutput.add("Error closing the file: " + e);
        }

        return fileContents;
    }

    /**
     * Writes the specified contents to the specified file.
     *
     * @param filename name of the file to be written to
     * @param contents the contents to be written to the file
     */
    static void writeToFile(String filename, ArrayList<String> contents) {
        try {
            PrintWriter write = new PrintWriter(new FileWriter(filename));
            for (String line : contents) {
                write.println(line); // write the line to the file
            }
            write.close();
        } catch (IOException error) {
            Main.terminalOutput.add("Error writing to the file");
        }
    }
}
