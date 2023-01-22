package soundar.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.InHouse;
import model.Inventory;
import model.Outsource;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static soundar.Controller.MainController.returnToMain;

/**
 * Controller for the Add Part Menu
 */
public class AddPartController implements Initializable {

    public RadioButton InHouseRadioAddPart;
    public Label IDorCompanyAdd;
    public RadioButton OutsourceRadioAddPart;
    public Button CancelAddPart;
    public Button SaveAddPart;
    public TextField AddPartNameText;
    public TextField AddPartMaxText;
    public TextField AddPartMinText;
    public TextField AddPartPriceText;
    public TextField AddPartStockText;
    public TextField IDorCompanyAddText;

    /**
     * Makes sure min is between 0 and max
     *
     * @param min
     * @param max
     * @return bool
     */
    public static boolean validMin(int min, int max) {

        boolean validity = true;
        if (min <= 0 || max <= min) {
            validity = false;
            newAlert(3);
        }
        return validity;
    }

    /**
     * makes sure inventory is between min and max
     *
     * @param max
     * @return bool
     */
    public static boolean validStock(int min, int max, int stock) {

        boolean validity = true;
        if (stock < min || stock > max) {
            validity = false;
            newAlert(4);
        }
        return validity;
    }

    /**
     * method to Show popup Alerts when an error in the form is detected
     *
     * @param type
     */
    public static void newAlert(int type) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        switch (type) {
            case 1 -> {
                alert.setTitle("Error");
                alert.setContentText("Blank fields/invalid values detected");
                alert.showAndWait();
            }
            case 2 -> {
                alert.setTitle("Error: Invalid Machine ID");
                alert.setContentText("Machine ID can only have integers");
                alert.showAndWait();
            }
            case 3 -> {
                alert.setTitle("Error: Invalid Min");
                alert.setContentText("Min must be between 0 and Max");
                alert.showAndWait();
            }
            case 4 -> {
                alert.setTitle("Error: Invalid Stock");
                alert.setContentText("Stock must be between Min and Max");
                alert.showAndWait();
            }
            case 5 -> {
                alert.setTitle("Error: No Name");
                alert.setContentText("No Name detected");
                alert.showAndWait();
            }
            case 6 -> {
                alert.setTitle("Error");
                alert.setContentText("No part selected");
                alert.showAndWait();
            }
            case 7 -> {
                alert.setTitle("Error");
                alert.setContentText("Name can't be empty");
                alert.showAndWait();
            }
        }
    }

    /**
     * initializable that selects the In House radio button
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        InHouseRadioAddPart.setSelected(true);
    }

    /**
     * Returns to main menu when clicking cancel. Confirmation Alert also pops up
     *
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    void ClickCancelAddPart(ActionEvent actionEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Cancel changes and return to Main Menu?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            returnToMain(actionEvent);
        }
    }

    /**
     * Changes label if Radio button In-House is clicked
     *
     * @param actionEvent
     */
    @FXML
    void ClickInHouseRadioAddPart(ActionEvent actionEvent) {
        IDorCompanyAdd.setText("Machine ID");
    }

    /**
     * Changes label if Radio button Outsource is clicked
     *
     * @param actionEvent
     */
    @FXML
    void ClickOutsourceRadioAddPart(ActionEvent actionEvent) {
        IDorCompanyAdd.setText("Company Name");
    }

    /**
     * Method for the Save Button in the Add part form. Will parse the data and add to the main menu table
     * @param actionEvent
     * @throws IOException
     */
    public void ClickSaveAddPart(ActionEvent actionEvent) throws IOException {
        try {
            int id = 0;
            String name = AddPartNameText.getText();
            int stock = Integer.parseInt(AddPartStockText.getText());
            Double price = Double.parseDouble(AddPartPriceText.getText());
            int max = Integer.parseInt(AddPartMaxText.getText());
            int min = Integer.parseInt(AddPartMinText.getText());
            int machineID;
            String companyName;
            boolean Success = false;

            if (name.isEmpty()) {
                newAlert(5);
            } else {
                if (validMin(min, max) && validStock(min, max, stock)) {
                    if (InHouseRadioAddPart.isSelected()) {
                        try {
                            machineID = Integer.parseInt(IDorCompanyAddText.getText());
                            InHouse InHousePart = new InHouse(id, name, price, stock, min, max, machineID);
                            InHousePart.setId(Inventory.getNewPartID());
                            Inventory.AddPart(InHousePart);
                            Success = true;
                        } catch (Exception exception) {
                            newAlert(2);
                        }
                    }
                    if (OutsourceRadioAddPart.isSelected()) {
                        companyName = IDorCompanyAddText.getText();
                        Outsource OutsourcePart = new Outsource(id, name, price, stock, min, max, companyName);
                        OutsourcePart.setId(Inventory.getNewPartID());
                        Inventory.AddPart(OutsourcePart);
                        Success = true;
                    }

                    if (Success) {
                        returnToMain(actionEvent);
                    }
                }
            }
        } catch (Exception exception) {
            newAlert(1);
        }


    }
}



