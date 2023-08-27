package com.example.tarea1;

public class Memoria {
    int[] almacen = new int[100];
    String[] addresses = new String[100];

    Instruccion[] memory = new Instruccion[100];

    public int leer(int direccion) {
        if (direccion >= 0 && direccion < 100) {
            return almacen[direccion];
        }
        // Retornar algún valor de error o lanzar una excepción si la dirección es inválida
        return -1;
    }

    // Escribir un valor en una dirección específica de la memoria
    public void escribir(int direccion, int valor) {
        if (direccion >= 0 && direccion < 100) {
            almacen[direccion] = valor;
        }
        // Retornar algún valor de error o lanzar una excepción si la dirección es inválida
    }

    public void escribir(int direccion, String valor) {
        if (direccion >= 0 && direccion < 100) {
            addresses[direccion] = valor;
        } else System.out.print("Error al escribir en memoria");
    }

    public void escribir(int direccion, Instruccion instruccion) {
        if (direccion >= 0 && direccion < 100) {
            memory[direccion] = instruccion;
        } else System.out.print("Error al escribir en memoria");
    }

    public void printNonEmptyAddresses() {
        for (int i = 0; i < addresses.length; i++) {
            if (addresses[i] != null) {
                System.out.println("Address at position " + i + ": " + addresses[i]);
            }
        }
    }

    public Instruccion getInstruction(int pos) {
        return memory[pos];
    }

    public Instruccion[] getMemory() {
        return memory;
    }
}
