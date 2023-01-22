package soundar.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static soundar.Controller.AddPartController.*;
import static soundar.Controller.MainController.mainAlert;
import static soundar.Controller.MainController.returnToMain;

public class ModifyProductController implements Initializable {
    public TextField Max;
    public TextField Min;
    public TextField Price;
    public TextField Stock;
    public TextField Name;
    public TextField SearchFieldModProd;
    public TableColumn<Part, Integer> PartIDCol;
    public TableColumn<Part, String> PartNameCol;
    public TableColumn<Part, Integer> PartStockCol;
    public TableColumn<Part, Double> PartPriceCol;
    public TableColumn<Part, Integer> PartIDCol2;
    public TableColumn<Part, String> PartNameCol2;
    public TableColumn<Part, Integer> PartStockCol2;
    public TableColumn<Part, Double> PartPriceCol2;
    public TextField IDfield;
    Product selectedProduct;
    @FXML
    private TableView<Part> AssociatedPartsTable;
    @FXML
    private TableView<Part> partsTable;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * Moves selected Part to the associated parts table
     *
     * @param actionEvent
     */
    @FXML
    void ClickAdd(ActionEvent actionEvent) {
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            newAlert(5);
        } else {
            associatedParts.add(selectedPart);
            AssociatedPartsTable.setItems(associatedParts);
        }
    }

    /**
     * Cancel changes and return to main menu
     *
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    void ClickCancelModProduct(ActionEvent actionEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Cancel changes and return to Main Menu?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            returnToMain(actionEvent);
        }
    }

    /**
     * Remove associated part from table
     *
     * @param actionEvent
     */
    @FXML
    void ClickRemoveAssociatedPart(ActionEvent actionEvent) {
        Part selectedPart = AssociatedPartsTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            newAlert(5);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Remove Part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                associatedParts.remove(selectedPart);
                AssociatedPartsTable.setItems(associatedParts);
            }
        }
    }

    /**
     * Saves the modified product
     * @param actionEvent click save button
     * @throws IOException
     */
    @FXML
    void ClickSaveModifyProduct(ActionEvent actionEvent) throws IOException {
        try {
            int id = selectedProduct.getId();
            String name = Name.getText();
            int stock = Integer.parseInt(Stock.getText());
            Double price = Double.parseDouble(Price.getText());
            int max = Integer.parseInt(Max.getText());
            int min = Integer.parseInt(Min.getText());

            if (name.isEmpty()) {
                newAlert(7);
            } else {
                if (validMin(min, max) && validStock(min, max, stock)) {
                    Product product = new Product(id, stock, min, max, name, price);

                    for (Part part : associatedParts) {
                        product.addAssociatedPart(part);
                    }
                    Inventory.AddProduct(product);
                    Inventory.deleteProduct(selectedProduct);
                    returnToMain(actionEvent);
                }

            }
        } catch (Exception exc) {
            newAlert(1);
        }
    }

    /**
     * Searches parts table based on ID or string
     *
     * @param actionEvent
     */
    @FXML
    void ClickSearchModProd(ActionEvent actionEvent) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        String search = SearchFieldModProd.getText();

        for (Part part : allParts) {
            if (String.valueOf(part.getId()).contains(search) || part.getName().contains(search)) {
                foundParts.add(part);
            }
        }
        partsTable.setItems(foundParts);

        if (foundParts.size() == 0) {
            mainAlert(1);
        }

        if (SearchFieldModProd.getText().isEmpty()) {
            partsTable.setItems(Inventory.getAllParts());
        }

    }

    /**
     * Takes the info from the selected part to be modified and puts the information in the table and text fields
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedProduct = MainController.getProductToMod();
        associatedParts = selectedProduct.getAllAssociatedParts();

        PartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partsTable.setItems(Inventory.getAllParts());

        PartIDCol2.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameCol2.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartStockCol2.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPriceCol2.setCellValueFactory(new PropertyValueFactory<>("price"));
        AssociatedPartsTable.setItems(associatedParts);

        IDfield.setText(String.valueOf(selectedProduct.getId()));
        Name.setText((selectedProduct.getName()));
        Price.setText(String.valueOf(selectedProduct.getPrice()));
        Min.setText(String.valueOf(selectedProduct.getMin()));
        Max.setText(String.valueOf(selectedProduct.getMax()));
        Stock.setText(String.valueOf(selectedProduct.getStock()));
    }
}
