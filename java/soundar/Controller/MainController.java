package soundar.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller for the Main Menu
 */
public class MainController implements Initializable {

    private static Product productToMod;
    private static Part PartToMod;
    public Button MainAddPartButton;
    public Button MainModifyPartButton;
    public Button MainDeletePartButton;
    public TextField MainPartSearchText;
    public Button Exit;
    public TableView<Part> PartTableMain;
    public Button MainAddProduct;
    public Button MainModifyButton;
    public TableView<Product> ProductTableMain;
    public Button DeleteProductButton;
    public Button MainSearchProductButton;
    public TextField MainProductSearchText;
    public Button MainSearchPart;
    public TableColumn<Part, Integer> PartIDmain;
    public TableColumn<Part, String> PartNameMain;
    public TableColumn<Part, Integer> PartStockMain;
    public TableColumn<Part, Double> PartPriceMain;
    public TableColumn<Product, Integer> ProductIDmain;
    public TableColumn<Product, String> ProductNameMain;
    public TableColumn<Product, Integer> ProductStockMain;
    public TableColumn<Product, Double> ProductPriceMain;

    public static Product getProductToMod() {
        return productToMod;
    }

    public static Part getPartToMod() {
        return PartToMod;
    }

    /**
     * Returns to main menu
     *
     * @param actionEvent
     * @throws IOException
     */
    public static void returnToMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(MainController.class.getResource("Main.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Alert messages that handle certain errors
     * @param type error case
     */
    public static void mainAlert(int type) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        switch (type) {
            case 1 -> {
                alert.setTitle("Error");
                alert.setContentText("Part not found");
                alert.showAndWait();
            }
            case 2 -> {
                alert.setTitle("Error");
                alert.setContentText("Product not found");
                alert.showAndWait();
            }
            case 3 -> {
                alert.setTitle("Error");
                alert.setContentText("No Part selected");
                alert.showAndWait();
            }
            case 4 -> {
                alert.setTitle("Error");
                alert.setContentText("No Product selected");
                alert.showAndWait();
            }
            case 5 -> {
                alert.setTitle("Error");
                alert.setContentText("Delete all parts from product before deletion");
                alert.showAndWait();
            }
        }
    }

    /**
     * Exits application when exit button is clicked
     *
     * @param actionEvent click exit button
     */
    @FXML
    void ClickExit(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * Takes user to Add Product menu
     *
     * @param actionEvent click Add Product button
     * @throws IOException
     */
    @FXML
    void ClickMainAddProductButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Deletes Selected Part
     *
     * @param mouseEvent click Delete button
     * @throws IOException
     */
    @FXML
    void ClickMainDeletePartButton(MouseEvent mouseEvent) throws IOException {

        Part part = PartTableMain.getSelectionModel().getSelectedItem();

        if (part == null) {
            mainAlert(3);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Delete selected part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(part);
            }
        }
    }

    /**
     * Takes user to modify product menu
     *
     * @param actionEvent click modify product
     * @throws IOException
     */
    @FXML
    void ClickMainModifyProductButton(ActionEvent actionEvent) throws IOException {

        productToMod = ProductTableMain.getSelectionModel().getSelectedItem();

        if (productToMod == null) {
            mainAlert(4);
        } else {
            Parent root = FXMLLoader.load(getClass().getResource("ModifyProduct.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Searches part table. Shows all parts if search field is empty
     *
     * @param actionEvent click search button
     */
    @FXML
    void ClickMainSearchPart(ActionEvent actionEvent) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        String search = MainPartSearchText.getText();

        for (Part part : allParts) {
            if (String.valueOf(part.getId()).contains(search) || part.getName().contains(search)) {
                foundParts.add(part);
            }
        }
        PartTableMain.setItems(foundParts);

        if (foundParts.size() == 0) {
            mainAlert(1);
        }

        if (MainPartSearchText.getText().isEmpty()) {
            PartTableMain.setItems(Inventory.getAllParts());
        }

    }

    /**
     * Takes user to Add Part menu
     *
     * @param mouseEvent click Add Part button
     * @throws IOException
     */
    @FXML
    void ClickMainAddPartButton(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        Stage stage = (Stage) ((Button) mouseEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Takes user to modify part if part is selected
     *
     * @param mouseEvent
     * @throws IOException
     */
    public void ClickMainModifyPartButton(MouseEvent mouseEvent) throws IOException {

        PartToMod = PartTableMain.getSelectionModel().getSelectedItem();

        if (PartToMod == null) {
            mainAlert(4);
        } else {
            Parent root = FXMLLoader.load(getClass().getResource("ModifyPart.fxml"));
            Stage stage = (Stage) ((Button) mouseEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Deletes the Product if all the associated Parts are gone
     * @param actionEvent click delete button
     */
    public void ClickDeleteProductButton(ActionEvent actionEvent) {
        Product product = ProductTableMain.getSelectionModel().getSelectedItem();

        if (product == null) {
            mainAlert(4);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Delete selected product?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                ObservableList<Part> associatedParts = product.getAllAssociatedParts();

                if (associatedParts.size() >= 1) {
                    mainAlert(5);
                } else {
                    Inventory.deleteProduct(product);
                }
            }
        }
    }

    /**
     * Searches product table based on ID or string and displays results
     *
     * @param actionEvent click Search product
     */

    @FXML
    void ClickMainSearchProduct(ActionEvent actionEvent) {
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();
        String search = MainProductSearchText.getText();

        for (Product product : allProducts) {
            if (String.valueOf(product.getId()).contains(search) || product.getName().contains(search)) {
                foundProducts.add(product);
            }
        }
        ProductTableMain.setItems(foundProducts);

        if (foundProducts.size() == 0) {
            mainAlert(2);
        }

        if (MainProductSearchText.getText().isEmpty()) {
            ProductTableMain.setItems(Inventory.getAllProducts());
        }
    }

    /**
     * Initializes and sets Part and Product table with values
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PartIDmain.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameMain.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartStockMain.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPriceMain.setCellValueFactory(new PropertyValueFactory<>("price"));
        PartTableMain.setItems(Inventory.getAllParts());

        ProductIDmain.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProductNameMain.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProductStockMain.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProductPriceMain.setCellValueFactory(new PropertyValueFactory<>("price"));
        ProductTableMain.setItems(Inventory.getAllProducts());
    }
}
