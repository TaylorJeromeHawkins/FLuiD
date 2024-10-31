package org.fluid;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class mainController {

    // TextAreas
    @FXML
    private TextArea outputText;

    @FXML
    TextArea queryText;

    // Menu Buttons
    @FXML
    private MenuButton menuButton; // Reference to the MenuButton

    @FXML
    private MenuItem menuItemDatabases; // MenuItem for "SHOW databases"

    @FXML
    private MenuItem menuItemTables; // MenuItem for "SHOW tables"

    @FXML
    private MenuItem menuItemColumns; // MenuItem for "SHOW columns FROM Select_table"

    @FXML
    public void initialize() {
        // Add event handlers to the MenuItems
        menuItemDatabases.setOnAction(e -> {
            queryText.setText("SHOW databases;");
            doQuery();
        });
        menuItemTables.setOnAction(e -> {
            queryText.setText("SHOW tables;");
            doQuery();
        });
        menuItemColumns.setOnAction(e -> {
            showColumnComboBox();
        });
    }

    @FXML
    public void onMouseClick(MouseEvent event) {
        Object source = event.getSource();

        // Button
        if (source instanceof Button) {
            Button button = (Button) source;
            String btnValue = button.getText();

            switch (btnValue) {
                case "SELECT":
                    showSelectComboBox();
                    break;

                case "USE":
                    showUseComboBox();
                    break;

                case "Copy":
                    String output = outputText.getText();
                    Clipboard clipboard = Clipboard.getSystemClipboard();
                    ClipboardContent content = new ClipboardContent();
                    content.putString(output);
                    clipboard.setContent(content);
                    break;

                case "QUERY":
                    doQuery();
                    break;

                case "Clear":
                    outputText.setText("");
                    break;

                case "ClearQuery":
                    queryText.setText("");
                    break;

                case "INSERT":
                    queryText.setText("INSERT INTO my_table (column1, column2) VALUES ('value1', 'value2');");
                    break;

                case "DELETE":
                    queryText.setText("DELETE -parameter- FROM table_name WHERE condition;");
                    break;

                default:
                    outputText.setText("Invalid button action");
                    System.out.println("ERROR");
                    break;
            }
        }
    }

    public void doQuery() {
        try {
            // Execute the query and show result in outputText
            String result = DB.accessDB(queryText.getText());
            outputText.setText(result);
        } catch (Exception e) {
            outputText.setText(e.toString()); // Clear outputText on failure
        }
    }

    public void showSelectComboBox() {
        try {
            FXMLLoader loader = new FXMLLoader(FLuiD.class.getResource("selectComboBox.fxml"));
            Parent root = loader.load();

            // Pass the reference of mainController
            selectComboBoxController selectController = loader.getController();
            selectController.setMainController(this);

            // popup window
            Image image = new Image(String.valueOf(FLuiD.class.getResource("/Images/DB.png")));
            Stage popupStage = new Stage();
            popupStage.getIcons().add(image);
            popupStage.initModality(Modality.APPLICATION_MODAL); // Make it a modal window
            popupStage.setTitle("Select Table and Condition");
            popupStage.setScene(new Scene(root));
            popupStage.showAndWait();

        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    public void showUseComboBox() {
        try{
            FXMLLoader loader = new FXMLLoader(FLuiD.class.getResource("useComboBox.fxml"));
            Parent root = loader.load();

            //Pass the reference of mainController
            useComboBoxController useController = loader.getController();
            useController.setMainController(this);

            // popup window
            Image image = new Image(String.valueOf(FLuiD.class.getResource("/Images/DB.png")));
            Stage popupStage = new Stage();
            popupStage.getIcons().add(image);
            popupStage.initModality(Modality.APPLICATION_MODAL); // Make it a modal window
            popupStage.setTitle("Select Table");
            popupStage.setScene(new Scene(root));
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showColumnComboBox() {
        try {
            FXMLLoader loader = new FXMLLoader(FLuiD.class.getResource("columnComboBox.fxml"));
            Parent root = loader.load();

            // Get the controller and set the mainController reference
            columnComboBoxController columnController = loader.getController();
            columnController.setMainController(this); //Reference to mainController

            // popup window
            Image image = new Image(String.valueOf(FLuiD.class.getResource("/Images/DB.png")));
            Stage popupStage = new Stage();
            popupStage.getIcons().add(image);
            popupStage.setScene(new Scene(root));
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Select Column");
            popupStage.showAndWait();

        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    // Methods to fill combo box information
    public static List<String> getAvailableTables() {
        List<String> tableList = new ArrayList<>();
        String query = "SHOW TABLES";

        String result = DB.accessDB(query);
        String[] tables = result.split("\n");

        for (String table : tables) {
            tableList.add(table.trim());
        }
        System.out.println(tableList);
        return tableList;
    }

    public static List<String> getAvailableDBs() {
        List<String> DBList = new ArrayList<>();
        String query = "SHOW DATABASES";

        String result = DB.accessDB(query);
        String[] DBs = result.split("\n");

        for (String table : DBs) {
            DBList.add(table.trim());
        }
        return DBList;
    }

    public TextArea getQueryText() {
        return queryText;
    }

    public void onFileNew() {
        menuBarController.openFileNew();
    }

    public void onFileOpen() {
        menuBarController.openFile();
    }

    public void onFileSave() {
        menuBarController.saveFile();
    }

    public void onFileSaveAs() {
        menuBarController.saveFileAs();
    }

    public void onFileQuit() {
        menuBarController.quitFile();
    }

    public void onPreferences() {
        menuBarController.showPreferences();
    }
}
