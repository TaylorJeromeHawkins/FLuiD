package org.fluid;

import javafx.application.Platform;

public class menuBarController {

    public static void openFileNew() {
        //Open a file
        System.out.println("newFile");
    }

    public static void openFile() {
        //Open a file
        System.out.println("openFile");
    }

    public static void saveFile() {
        //Save a file
        System.out.println("saveFile");
    }

    public static void saveFileAs() {
        //Save a file As
        System.out.println("saveFileAs");
    }

    public static void quitFile() {
        // Close the application
        Platform.exit();
    }

    public static void showPreferences() {
        //Preferences
        System.out.println("Preferences");
    }
}
