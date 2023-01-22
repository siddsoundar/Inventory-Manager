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
import static soundar.Controller.MainController.returnToMain;

/**
 * Controller for the Add Product Menu
 */
public class AddProductController implements Initializable {
    public Button AddProductAddButton;
    public Button AddProductSearch;
    public TextField AddProductSearchField;
    public Button RemoveButton;
    public TextField AddProductNameText;
    public TextField AddProductStockText;
    public TextField AddProductPriceText;
    public TextField AddPartMaxText;
    public TextField AddPartMinText;
    public TableColumn<Part, Integer> AssociatedPartIDcol;
    public TableColumn<Part, String> AssociatedPartNameCol;
    public TableColumn<Part, Integer> AssociatedPartStockCol;
    public TableColumn<Part, Double> AssociatedPartPriceCol;
    public Button CancelAddProduct;
    public TableView<Part> associatedPartTable;
    public TableView<Part> partTable;
    @FXML
    private TableColumn<Part, Integer> partIDcol;
    @FXML
    private TableColumn<Part, String> PartNameCol;
    @FXML
    private TableColumn<Part, Integer> PartStockCol;
    @FXML
    private TableColumn<Part, Double> PartPriceCol;
    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * Cancel adding the product and return to main menu
     * @param actionEvent click cancel button
     * @throws IOException
     */
    @FXML
    void ClickCancelAddProduct(ActionEvent actionEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Cancel changes and return to Main Menu?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            returnToMain(actionEvent);
        }
    }

    /**
     * Moves a selected part from all parts table to associated parts table
     *
     * @param actionEvent click add button
     */
    @FXML
    void ClickAddProductAddButton(ActionEvent actionEvent) {
        Part selectedPart = partTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            newAlert(6);
        } else {
            associatedParts.add(selectedPart);
            associatedPartTable.setItems(associatedParts);
        }
    }

    /**
     * Searches the parts table and displays the parts based on the text field next to it
     * @param actionEvent button click
     */
    @FXML
    void ClickAddProductSearch(ActionEvent actionEvent) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        String search = AddProductSearchField.getText();

        for (Part part : allParts) {
            if (String.valueOf(part.getId()).contains(search) || part.getName().contains(search)) {
                foundParts.add(part);
            }
        }
        partTable.setItems(foundParts);

        if (foundParts.size() == 0) {
            newAlert(1);
        }

        if (AddProductSearchField.getText().isEmpty()) {
            partTable.setItems(Inventory.getAllParts());
        }

    }

    /**
     * Removes part from associated parts table
     * @param actionEvent click remove
     */
    @FXML
    void ClickRemoveButton(ActionEvent actionEvent) {
        Part selectedPart = associatedPartTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            newAlert(5);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Remove Part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                associatedParts.remove(selectedPart);
                associatedPartTable.setItems(associatedParts);
            }
        }
    }

    /**
     * Saves product to inventory and returns to main menu
     *
     * @param actionEvent click save
     * @throws IOException
     */
    @FXML
    void ClickSaveButton(ActionEvent actionEvent) throws IOException {
        try {
            int id = 0;
            String name = AddProductNameText.getText();
            int stock = Integer.parseInt(AddProductStockText.getText());
            Double price = Double.parseDouble(AddProductPriceText.getText());
            int max = Integer.parseInt(AddPartMaxText.getText());
            int min = Integer.parseInt(AddPartMinText.getText());

            if (name.isEmpty()) {
                newAlert(1);
            } else {
                if (validMin(min, max) && validStock(min, max, stock)) {
                    Product product = new Product(id, stock, min, max, name, price);

                    for (Part part : associatedParts) {
                        product.addAssociatedPart(part);
                    }

                    product.setId(Inventory.getNewProductID());
                    Inventory.AddProduct(product);
                    returnToMain(actionEvent);
                }
            }
        } catch (Exception newexception) {
            newAlert(1);
        }
    }

    /**
     * Populates the parts and associated parts tables
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partIDcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partTable.setItems(Inventory.getAllParts());

        AssociatedPartIDcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        AssociatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        AssociatedPartStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AssociatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
