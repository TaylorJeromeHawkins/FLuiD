package org.fluid.fluid;

import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class mainController {

    // TextAreas
    @FXML
    private TextArea outputText;

    @FXML
    private TextArea queryText;

    // Menu Buttons
    @FXML
    private MenuButton menuButton; // Reference to the MenuButton

    @FXML
    private MenuItem menuItemDatabases; // MenuItem for "SHOW databases"

    @FXML
    private MenuItem menuItemTables; // MenuItem for "SHOW tables"

    @FXML
    private MenuItem menuItemColumns; // MenuItem for "SHOW columns"

    @FXML
    public void initialize() {
        // Add event handlers to the MenuItems
        menuItemDatabases.setOnAction(e -> queryText.setText("SHOW databases;"));
        menuItemTables.setOnAction(e -> queryText.setText("SHOW tables;"));
        menuItemColumns.setOnAction(e -> queryText.setText("SHOW columns FROM table_name;"));
    }

    @FXML
    public void onMouseClick(MouseEvent event) {
        Object source = event.getSource();

        // Button
        if (source instanceof Button) {
            Button button = (Button) source;
            String btnValue = button.getText();

            switch (btnValue) {
                case "Copy":
                    String output = outputText.getText();
                    Clipboard clipboard = Clipboard.getSystemClipboard();
                    ClipboardContent content = new ClipboardContent();
                    content.putString(output);
                    clipboard.setContent(content);
                    break;

                case "QUERY":
                    try {
                        // Attempt to execute the query and show result in outputText
                        String result = DB.accessDB(queryText.getText());
                        outputText.setText(result);
                    } catch (Exception e) {
                        // If an error occurs, show it in queryERROR
                        outputText.setText(e.toString()); // Clear outputText on failure
                    }
                    break;

                case "Clear":
                    outputText.setText("");
                    break;

                case "ClearQuery":
                    queryText.setText("");
                    break;

                case "USE":
                    queryText.setText("USE database_name;");
                    break;

                case "SELECT":
                    queryText.setText("SELECT * FROM table_name;");
                    break;

                case "INSERT":
                    queryText.setText("INSERT INTO my_table (column1, column2) VALUES ('value1', 'value2');");
                    break;

                case "DELETE":
                    queryText.setText("DELETE FROM table_name WHERE condition;");
                    break;

                default:
                    outputText.setText("Invalid button action");
                    System.out.println("ERROR");
                    break;
            }
        }
    }

//MenuBar Functions
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
