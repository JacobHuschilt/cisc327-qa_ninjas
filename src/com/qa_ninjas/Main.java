/**
 * Jacob Huschilt 10197679
 * Heni Virág
 * Gaveshini Sriyananda
 *
 * Created: 09/16/2017
 *
 * ©2017 QA_Ninjas
 */

package com.qa_ninjas;

import java.util.Scanner;

public class Main {

    /**
     * Global Properties
     */
    private static Scanner consoleInput = new Scanner(System.in);

    /**
     * Enums
     */
    public enum ValidCommands {
        login,
        logout,
        createacct,
        deleteacct,
        transfer,
        deposit,
        withdraw
    }

    /**
     * QBasic Main Method.
     * @param args
     */
    public static void main(String[] args) {
        programStartupHeader();

        String command = "";
        while (command != ValidCommands.logout.toString()) {
            command = sUserPromptRead("");
        }
    }

    /**
     * Prints Program Header
     */
    private static void programStartupHeader() {
        System.out.println("QBasic");
        System.out.println("===============");
        System.out.println("©2017 QA_Ninjas");
    }

    /**
     * Prompt the user, and read a String from the console.
     * @param promptPhrase
     * @return
     */
    public static String sUserPromptRead(String promptPhrase) {
        Scanner in = new Scanner(System.in);
        System.out.print(promptPhrase + " ");
        return in.nextLine();
    }
}
