package org.fluid;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class columnComboBoxController {

    @FXML
    private ComboBox<String> tableComboBox;

    @FXML
    private Button confirmButton;

    @FXML
    private mainController mainCtrl;

    public void initialize() {
        // Populate ComboBoxes
        tableComboBox.getItems().addAll(mainController.getAvailableTables());
    }

    @FXML
    public void onConfirm() {
        String selectedDB = tableComboBox.getValue();
        if (selectedDB != null) {
            mainCtrl.getQueryText().setText("SHOW columns FROM " + selectedDB + ";");
        } else {
            mainCtrl.getQueryText().setText("Select a table first!");
        }
        mainCtrl.doQuery();
        closePopup();
    }

    private void closePopup() {
        // Get the current stage (a popup window) and close it
        Stage stage = (Stage) tableComboBox.getScene().getWindow();
        stage.close();
    }

    public void setMainController(mainController mainCtrl) {
        this.mainCtrl = mainCtrl;
    }
}

