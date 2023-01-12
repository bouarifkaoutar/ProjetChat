module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.xml.crypto;
    requires java.sql;

    opens com.example.demo1 to javafx.fxml;
    exports com.example.demo1;
    exports javafx;
    opens javafx to javafx.fxml;
}