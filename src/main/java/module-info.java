module co.edu.uniquindio.eventmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires static lombok;
    requires annotations;
    requires jdk.compiler;

    opens co.edu.uniquindio.eventmanager to javafx.fxml;
    exports co.edu.uniquindio.eventmanager;
    exports co.edu.uniquindio.eventmanager.viewController;
    opens co.edu.uniquindio.eventmanager.viewController to javafx.fxml;
}