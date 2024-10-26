package org.fluid;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class selectComboBoxController {

    @FXML
    private ComboBox<String> tableComboBox;

    @FXML
    private ComboBox<String> conditionComboBox;

    @FXML
    private Button confirmButton;

    @FXML
    private mainController mainCtrl;

    @FXML
    public void initialize() {
        // Populate ComboBoxes
        tableComboBox.getItems().addAll(mainController.getAvailableTables());
        conditionComboBox.getItems().addAll("", "WHERE", "ORDER BY", "LIMIT");
    }

    @FXML
    public void onConfirm() {
        String selectedTable = tableComboBox.getValue();
        String selectedCondition = conditionComboBox.getValue();
        if (selectedTable != null) {
            if (selectedCondition == null || selectedCondition.isEmpty()) {
                // No condition selected, only include the table
                mainCtrl.getQueryText().setText("SELECT * FROM " + selectedTable + ";");
            } else {
                // Condition is selected, include it in the query
                mainCtrl.getQueryText().setText("SELECT * FROM " + selectedTable + " " + selectedCondition + ";");
            }
            mainCtrl.doQuery();
            closePopup();
        }
    }

    public void setMainController(mainController mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    private void closePopup() {
        // Get the current stage (popup window) and close it
        Stage stage = (Stage) tableComboBox.getScene().getWindow();
        stage.close();
    }
}

