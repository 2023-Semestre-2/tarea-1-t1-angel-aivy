package com.example.tarea1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainuiController {

    @FXML
    private Pane mainPane;


    @FXML
    TextField filePath;

    @FXML
    private Button loadButton;
    @FXML
    private Button nextButton;
    @FXML
    private Label message;

    @FXML
    private TableView<MemoryRow> tableMemoria;

    @FXML
    private TableColumn<MemoryRow, String> positionColumn;

    @FXML
    private TableColumn<MemoryRow, String> instructionColumn;
    @FXML
    private TableColumn<MemoryRow, String> instruccionAsm;

    @FXML
    private TableView<RegisterRow> tableRegistros;

    @FXML
    private TableColumn<RegisterRow, String> registerColumn;

    @FXML
    private TableColumn<RegisterRow, String> valueColumn;

    private ObservableList<MemoryRow> dataMemory;
    private ObservableList<RegisterRow> dataRegister;


    private MiniComputador miniComputador;
    private final List<Integer> instructionPositions = new ArrayList<>();//direcciones de memoria de las instrucciones
    private int currentInstructionIndex = 0; //
    int lineCount = 0;

    @FXML
    public void initialize() {
        miniComputador = new MiniComputador();
        // Para la tabla de memoria
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        instructionColumn.setCellValueFactory(new PropertyValueFactory<>("instruction"));
        instruccionAsm.setCellValueFactory(new PropertyValueFactory<>("instruccionAsm"));
        dataMemory = FXCollections.observableArrayList();
        tableMemoria.setItems(dataMemory);

        // Para la tabla de registros
        registerColumn.setCellValueFactory(new PropertyValueFactory<>("register"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        dataRegister = FXCollections.observableArrayList();
        tableRegistros.setItems(dataRegister);

        // Inicializa registros con valores por defecto (podría ser 0)
        dataRegister.add(new RegisterRow("PC", ""));
        dataRegister.add(new RegisterRow("IR", ""));
        dataRegister.add(new RegisterRow("AC", ""));
        dataRegister.add(new RegisterRow("AX", ""));
        dataRegister.add(new RegisterRow("BX", ""));
        dataRegister.add(new RegisterRow("CX", ""));
        dataRegister.add(new RegisterRow("DX", ""));

    }

    @FXML
    protected void onLoadButtonClick() {
        File file = selectFile();
        if (file != null) {
            filePath.setText(file.getPath());
            lineCount = Utils.countLinesInFile(file);
            int startPosition = Utils.calculateStartPosition(lineCount);
            instructionPositions.add(startPosition);
            for (int i = 1; i < lineCount; i++) {
                instructionPositions.add(startPosition + i);
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                Utils.loadInstructionsIntoMemory(reader, dataMemory, startPosition, miniComputador);//agrega a la tabla de memoria
                String line;
                while ((line = reader.readLine()) != null) {
                    Utils.updateOrAddRegister(line, dataRegister); //agrega a la tabla de registros
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void onNextButtonClick() {
        if (currentInstructionIndex >= lineCount) {
            message.setText("Fin del programa!");
            return;
        }

        int[] result = miniComputador.getCpu().ejecutarInstruction(instructionPositions.get(currentInstructionIndex), miniComputador.getMemoria());
        System.out.printf(Arrays.toString(result));
        String[] registerNames = {"PC", "IR", "AC", "AX", "BX", "CX", "DX"};
        for (int i = 0; i < result.length; i++) {
            Utils.updateRegister(registerNames[i], dataRegister, result[i]);
        }
        tableRegistros.refresh();

        currentInstructionIndex += 1;
    }

    private File selectFile() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("ASM files (*.asm)", "*.asm");
        fileChooser.getExtensionFilters().add(extFilter);
        return fileChooser.showOpenDialog(null);
    }
}
