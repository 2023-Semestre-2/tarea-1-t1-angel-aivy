package com.example.tarea1;

import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class Utils {

//    private static final Instruccion INSTRUCCION = new Instruccion();

    /**
     * Cuenta el número de líneas en un archivo.
     *
     * @param file El archivo en el cual contar las líneas.
     * @return El número de líneas en el archivo.
     */
    public static int countLinesInFile(File file) {
        int linesCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while (reader.readLine() != null) linesCount++;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return linesCount;
    }

    /**
     * Calcula una posición de inicio aleatoria para cargar instrucciones, asegurándose de que no exceda la memoria.
     *
     * @param lineCount El número de líneas (instrucciones) a cargar.
     * @return La posición de inicio aleatoria.
     */
    public static int calculateStartPosition(int lineCount) {
        int startPosition;
        Random rand = new Random();
        do {
            startPosition = 10 + rand.nextInt(90);
        } while (startPosition + lineCount > 100);
        return startPosition;
    }

    /**
     * Carga instrucciones en la memoria desde un archivo.
     *
     * @param reader        El BufferedReader del archivo.
     * @param dataMemory    La estructura de datos para almacenar las instrucciones.
     * @param startPosition La posición de inicio para cargar las instrucciones.
     */
    public static void loadInstructionsIntoMemory(BufferedReader reader, List<MemoryRow> dataMemory, int startPosition, MiniComputador miniComputador) {
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                miniComputador.loadToMemory(new Instruccion(line), startPosition);
                String instruccionAsm = instructionToAssembler(line);
                dataMemory.add(new MemoryRow(Integer.toString(startPosition), line, instruccionAsm));
                startPosition++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Convierte un código de instrucción en su representación en lenguaje ensamblador.
     *
     * @param asmInstruction El código de instrucción.
     * @return La representación en ensamblador del código de instrucción.
     */
    public static String instructionToAssembler(String asmInstruction) {
        String[] instructionParts = asmInstruction.split(" ");
        if (instructionParts.length < 2) return "erorr"; //error

        //Operador
        String operatorCode = instructionParts[0];
        int operator = 0;

        switch (operatorCode) {
            case "LOAD" -> operator = 0b001;
            case "STORE" -> operator = 0b010;
            case "MOV" -> operator = 0b011;
            case "SUB" -> operator = 0b100;
            case "ADD" -> operator = 0b101;
            default -> {
                return "erorr"; // Código de error para "operador no válido"
            }
        }

        //Registro
        String registro = instructionParts[1].replace(",", "").trim();
        int direccionamiento = 0;
        switch (registro) {
            case "AX" -> direccionamiento = 0b0001;
            case "BX" -> direccionamiento = 0b0010;
            case "CX" -> direccionamiento = 0b0011;
            case "DX" -> direccionamiento = 0b0100;
            default -> {
                return "erorr"; // Código de error para "registro no válido"
            }
        }

        int valor = 0;
        if (instructionParts.length > 2) {
            try {
                valor = Integer.parseInt(instructionParts[2].trim());
            } catch (NumberFormatException e) {
                return "error"; //error
            }
        }

        int instructionInDecimal = (operator << 12) | (direccionamiento << 8) | (valor & 0xFF);

        return formatBinaryString(instructionInDecimal);
    }


    public static String formatBinaryString(int instruction) {
        // Convertir el número entero a una representación binaria en forma de cadena de caracteres
        String binaryString = Integer.toBinaryString(instruction);

        // Rellenar con ceros al principio para asegurarse de que la cadena tenga 16 bits
        while (binaryString.length() < 16) {
            binaryString = "0" + binaryString;
        }

        // Insertar espacios cada 4 bits, pero solo hacerlo dos veces
        binaryString = binaryString.substring(0, 4) + " " +
                binaryString.substring(4, 8) + " " +
                binaryString.substring(8);

        return binaryString;
    }


    /**
     * Actualiza o añade un nuevo registro en la lista de registros.
     *
     * @param line         La línea de instrucción que posiblemente contiene el registro.
     * @param dataRegister La estructura de datos para almacenar los registros.
     */
    public static void updateOrAddRegister(String line, List<RegisterRow> dataRegister) {
        String[] parts = line.split(" ");
        if (parts.length > 1) {
            String register = parts[1].replace(",", "").trim();
            String value = parts.length > 2 ? parts[2].trim() : "0";

            RegisterRow existingRow = dataRegister.stream()
                    .filter(row -> row.getRegister().equals(register))
                    .findFirst()
                    .orElse(null);

            if (existingRow != null) {
                existingRow.setValue(value);
            } else {
                dataRegister.add(new RegisterRow(register, value));
            }
        }
    }

    public static void updateRegister(String register, List<RegisterRow> dataRegister, int val) {
        String value = Integer.toString(val);
        RegisterRow existingRow = dataRegister.stream()
                .filter(row -> row.getRegister().equals(register))
                .findFirst()
                .orElse(null);

        if (existingRow != null) {
            existingRow.setValue(value);
        } else {
            dataRegister.add(new RegisterRow(register, value));
        }
    }
}
