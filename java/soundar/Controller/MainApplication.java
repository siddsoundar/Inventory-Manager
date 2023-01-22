package soundar.Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsource;
import model.Product;


/**
* FUTURE ENHANCEMENT: A future enhancement would be to let the user drag and drop Parts to the "Associated parts" table in the Add/Modify Product window. This would improve the user experience
 */
public class MainApplication extends Application {
    public static void main(String[] args) {

        launch(args);
    }

    /**
     * populates tables with example data
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        int partId = Inventory.getNewPartID();
        InHouse monitor = new InHouse(partId, "Monitor", 99.99, 5, 1, 10,
                19);
        partId = Inventory.getNewPartID();

        InHouse mouse = new InHouse(partId, "Mouse", 59.99, 5, 1, 10,
                12);
        partId = Inventory.getNewPartID();

        InHouse tower = new InHouse(partId, "Tower", 500.00, 5, 1, 10,
                34);
        partId = Inventory.getNewPartID();

        Outsource keyboard = new Outsource(partId, "KeyBoard", 49.99, 25, 5,
                50, "Alienware");
        Inventory.AddPart(monitor);
        Inventory.AddPart(mouse);
        Inventory.AddPart(tower);
        Inventory.AddPart(keyboard);

        int productID = Inventory.getNewProductID();
        Product PC = new Product(productID, 5, 1, 10, "X9 Turbo",
                399.99);
        PC.addAssociatedPart(monitor);
        PC.addAssociatedPart(mouse);
        PC.addAssociatedPart(tower);
        PC.addAssociatedPart(keyboard);
        Inventory.AddProduct(PC);


        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1100, 430);
        stage.setScene(scene);
        stage.show();
    }
}