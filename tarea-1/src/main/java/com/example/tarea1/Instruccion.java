package com.example.tarea1;

public class Instruccion {
    private String operador;
    private String registro;
    private int valor;
    private String binaryInstruction;

    public Instruccion() {
    }

    public Instruccion(String instruccionLine) {
        actualizarInstruccion(instruccionLine);
    }

    public void actualizarInstruccion(String instruccionLine) {
        String[] partes = instruccionLine.split(" ");
        if (partes.length < 2) {
            System.out.print("error al extraer el operador");
            return;
        }
        this.operador = partes[0];
        this.registro = partes[1].replace(",", "").trim();

        if (partes.length > 2) {
            try {
                this.valor = Integer.parseInt(partes[2].trim());
            } catch (NumberFormatException e) {
                // Manejo de error
                System.out.print("error al extraer el valor");
            }
        }
        this.binaryInstruction = Utils.instructionToAssembler(instruccionLine);
    }

    public static int binaryStringToDecimal(String binaryString) {
        // Eliminar todos los espacios del string
        String cleanedBinaryString = binaryString.replaceAll(" ", "");
        // Convertir el string binario a un entero decimal
        return Integer.parseInt(cleanedBinaryString, 2);
    }
    public int getBinaryStringToDecimal() {
        // Separar el string en partes usando espacio como delimitador
        String[] binaryStrings = this.binaryInstruction.split(" ");

        // Crear un array para almacenar los números decimales correspondientes
        int[] decimalNumbers = new int[binaryStrings.length];

        // Convertir cada string binario a un entero decimal
        for (int i = 0; i < binaryStrings.length; i++) {
            decimalNumbers[i] = Integer.parseInt(binaryStrings[i], 2);
        }

        return arrayToInt(decimalNumbers);
    }

    public int arrayToInt(int[] array) {
        int result = 0;
        for (int i = 0; i < array.length; i++) {
            result = result * 10 + array[i];
        }
        return result;
    }



    public String getOperador() {
        return operador;
    }

    public String getBinaryInstruction() {
        return binaryInstruction;
    }

    public String getRegistro() {
        return registro;
    }

    public int getValor() {
        return valor;
    }
}
