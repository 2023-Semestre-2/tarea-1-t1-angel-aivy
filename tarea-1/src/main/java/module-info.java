module com.example.tarea1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tarea1 to javafx.fxml;
    exports com.example.tarea1;
}