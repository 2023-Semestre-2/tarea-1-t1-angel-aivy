package com.example.tarea1;

import java.util.HashMap;

public class CPU {
    int AC; // Acumulador
    int PC; // Program Counter
    HashMap<String, Integer> registros;

    public CPU() {
        registros = new HashMap<>();
    }

    public void printRegistros() {
        System.out.printf(registros.toString());
    }

    public void agregarRegistro(String registro, int valor) {
        this.registros.put(registro, valor);
    }

    public int[] ejecutarInstruction(int memoryPos, Memoria memoria) {
        this.PC = memoryPos;
        Instruccion instruccion = memoria.getInstruction(memoryPos);
        int valor = instruccion.getValor();
        if (valor == 0){
            valor = registros.getOrDefault(instruccion.getRegistro(), 0);
        }
        registros.put(instruccion.getRegistro(), valor);

        calcularAC(instruccion);

        int[] resultArray = new int[7];
        resultArray[0] = this.PC;
        resultArray[1] = instruccion.getBinaryStringToDecimal();
        resultArray[2] = this.AC;
        resultArray[3] = registros.getOrDefault("AX", 0);
        resultArray[4] = registros.getOrDefault("BX", 0);
        resultArray[5] = registros.getOrDefault("CX", 0);
        resultArray[6] = registros.getOrDefault("DX", 0);



        return resultArray;
    }

    public void calcularAC (Instruccion instruccion) {
        String operador = instruccion.getOperador();
        String registroKey = instruccion.getRegistro();
        int valor = instruccion.getValor();

        switch (operador) {
            case "MOV" -> // MOV
                    this.registros.put(registroKey, valor);
            case "LOAD" -> // LOAD
                    this.AC = this.registros.getOrDefault(registroKey, 0);
            case "ADD" -> // ADD
                    this.AC += this.registros.getOrDefault(registroKey, 0);
            case "SUB" -> // SUB
                    this.AC -= this.registros.getOrDefault(registroKey, 0);
            case "STORE" -> // STORE
                    this.registros.put(registroKey, this.AC);
            default -> {
            }
            // Manejar operador no válido
        }
    }

    //TODO: Este tiene un bug, hay que ajsutarlo para que haga el calculo de AC
    public void ejecutarInstruccion(int instruccion, Memoria memoria) {
        int operador = (instruccion >> 12) & 0xF; // Los primeros 4 bits son el operador
        int direccionamiento = (instruccion >> 8) & 0xF; // Los siguientes 4 bits son el direccionamiento
        int valor = instruccion & 0xFF; // Los últimos 8 bits son el valor

        // Convertir 'direccionamiento' a la clave del registro correspondiente
        String registroKey = null;
        switch (direccionamiento) {
            case 0x1 -> registroKey = "AX";
            case 0x2 -> registroKey = "BX";
            case 0x3 -> registroKey = "CX";
            case 0x4 -> registroKey = "DX";
            default -> {
                // Manejar caso no válido
                return;
            }
        }

        if (registroKey == null) return; // Termina si el direccionamiento no es válido

        switch (operador) {
            case 0x6 -> // MOV
                    this.registros.put(registroKey, valor);
            case 0x2 -> // LOAD
                    this.AC = this.registros.getOrDefault(registroKey, 0);
            case 0xA -> // ADD
                    this.AC += this.registros.getOrDefault(registroKey, 0);
            case 0x8 -> // SUB
                    this.AC -= this.registros.getOrDefault(registroKey, 0);
            case 0x4 -> // STORE
                    memoria.escribir(this.AC, this.registros.getOrDefault(registroKey, 0));
            default -> {
            }
            // Manejar operador no válido
        }
    }
}
