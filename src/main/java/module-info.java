module ma.enset.tpjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.datatransfer;


    exports ma.enset.mvc;  // Export the ma.enset.mvc package
 opens ma.enset.mvc.controller to javafx.fxml;

    opens ma.enset.mvc.dao.entities to javafx.base; // This line makes the package accessible


}