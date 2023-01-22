module soundar.slideshow2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens soundar.Controller to javafx.fxml;
    exports soundar.Controller;

    opens model to javafx.fxml;
    exports model;

}