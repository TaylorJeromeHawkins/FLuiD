package org.fluid;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class useComboBoxController {

    @FXML
    private ComboBox<String> DBComboBox;

    @FXML
    private Button confirmButton;

    @FXML
    private mainController mainCtrl;

    public void initialize() {
        // Populate ComboBoxes
        DBComboBox.getItems().addAll(mainController.getAvailableDBs());
    }

    @FXML
    public void onConfirm() {
        String selectedDB = DBComboBox.getValue();
        if (selectedDB != null) {
            mainCtrl.getQueryText().setText("USE " + selectedDB + ";");
        } else {
            mainCtrl.getQueryText().setText("Select a database first!");
        }
        mainCtrl.doQuery();
        closePopup();
    }

    private void closePopup() {
        // Get the current stage (popup window) and close it
        Stage stage = (Stage) DBComboBox.getScene().getWindow();
        stage.close();
    }

    public void setMainController(mainController mainCtrl) {
        this.mainCtrl = mainCtrl;
    }
}
