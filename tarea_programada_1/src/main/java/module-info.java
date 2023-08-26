module com.mycompany.tarea_programada_1 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.tarea_programada_1 to javafx.fxml;
    exports com.mycompany.tarea_programada_1;
}
