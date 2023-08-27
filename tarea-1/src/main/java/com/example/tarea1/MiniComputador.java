package com.example.tarea1;

public class MiniComputador {
    private CPU cpu;
    private Memoria memoria;

    public MiniComputador() {
        this.cpu = new CPU();
        this.memoria = new Memoria();
    }

    public void loadToMeomory(String instruction, int direccion) {
        this.memoria.escribir(direccion, instruction);
    }
    public void loadToMemory(Instruccion instruction, int direccion) {
        this.memoria.escribir(direccion, instruction);
    }

    public CPU getCpu() {
        return cpu;
    }

    public Memoria getMemoria() {
        return memoria;
    }

}
