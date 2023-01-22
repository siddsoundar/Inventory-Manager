package soundar.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.InHouse;
import model.Inventory;
import model.Outsource;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static soundar.Controller.AddPartController.*;
import static soundar.Controller.MainController.returnToMain;

/**
 * RUNTIME ERROR: WARNING: Can not retrieve property 'price' in PropertyValueFactory: javafx.scene.control.cell.PropertyValueFactory@2e5dc1a5 with provided class type: class model.Outsource
 * java.lang.RuntimeException: java.lang.IllegalAccessException: module javafx.base cannot access class model.Part (in module soundar.slideshow2) because module soundar.slideshow2 does not open model to javafx.base
 * This runtime error was corrected by adding opens model to javafx.fxml; exports model; to module-info.java
 * Controller for Modify Part Menu
 */
public class ModifyPartController  implements Initializable{
    public Label IDorCompanyLabel;
    public Button CancelButton;
    public RadioButton OutsourceRadioModPart;
    public RadioButton InHouseRadioModPart;
    public TextField IDText;
    @FXML private TextField Min;
    @FXML private TextField IDorCompanyText;
    @FXML private TextField Price;
    @FXML private TextField Stock;
    @FXML private TextField Name;
    @FXML private TextField Max;
    private Part selectedPart;
    private int partID;

    /**
     * Cancels Modify Part changes and returns to main menu
     * @param actionEvent click cancel button
     * @throws IOException
     */
    @FXML void ClickCancel(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Cancel changes and return to Main Menu?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            returnToMain(actionEvent);
        }
    }

    /**
     * click InHouse radio button to change text to Machine ID
     * @param actionEvent
     */
    @FXML void ClickInHouseRadio(ActionEvent actionEvent) {
        IDorCompanyLabel.setText("Machine ID");
    }

    /**
     * click Outsource radio button to change text to Company Name
     * @param actionEvent
     */
    @FXML void ClickOutsourceRadio(ActionEvent actionEvent) {
        IDorCompanyLabel.setText("Company Name");
    }

    /**
     * Clicking save on modify part menu will update the entry
     * @param actionEvent click save button
     * @throws IOException
     */
    @FXML void ClickSaveModifyPart(ActionEvent actionEvent) throws IOException{
        try {
            int id = selectedPart.getId();
            String name = Name.getText();
            int stock = Integer.parseInt(Stock.getText());
            Double price = Double.parseDouble(Price.getText());
            int max = Integer.parseInt(Max.getText());
            int min = Integer.parseInt(Min.getText());
            int machineID;
            String companyName;
            boolean Success = false;

            if (validMin(min, max) && validStock(min, max, stock)) {
                    if (InHouseRadioModPart.isSelected()) {
                        try {
                            machineID = Integer.parseInt(IDorCompanyText.getText());
                            InHouse InHousePart = new InHouse(id, name, price, stock, min, max, machineID);
                            Inventory.updatePart(partID, InHousePart);
                            Success = true;
                        }
                        catch (Exception exception) {
                            newAlert(2);
                        }
                    }
                    if (OutsourceRadioModPart.isSelected()) {
                        companyName = IDorCompanyText.getText();
                        Outsource OutsourcePart = new Outsource(id, name, price, stock, min, max, companyName);
                        Inventory.updatePart(partID, OutsourcePart);
                        Success = true;
                    }

                    if (Success) {
                        returnToMain(actionEvent);
                    }
                }
            }catch(Exception exception) {
            newAlert(1);
            }
        }

    /**
     * Populates the text fields with the selected Part
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedPart = MainController.getPartToMod();
        partID = Inventory.getAllParts().indexOf(selectedPart);

        if (selectedPart instanceof InHouse) {
            InHouseRadioModPart.setSelected(true);
            IDorCompanyLabel.setText("Machine ID");
            IDorCompanyText.setText(String.valueOf(((InHouse) selectedPart).getMachineID()));
        }
        if (selectedPart instanceof Outsource) {
            OutsourceRadioModPart.setSelected(true);
            IDorCompanyLabel.setText("Company Name");
            IDorCompanyText.setText(String.valueOf(((Outsource) selectedPart).getCompanyName()));
        }

        IDText.setText(Integer.toString(selectedPart.getId()));
        Name.setText((selectedPart.getName()));
        Price.setText(Double.toString(selectedPart.getPrice()));
        Min.setText(Integer.toString(selectedPart.getMin()));
        Max.setText(Integer.toString(selectedPart.getMax()));
        Stock.setText(String.valueOf(selectedPart.getStock()));
    }
}

