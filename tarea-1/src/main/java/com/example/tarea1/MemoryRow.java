package com.example.tarea1;

public class MemoryRow {
    private String position;
    private String instruction;
    private String instruccionAsm;

    public MemoryRow(String position, String instruction, String instruccionAsm) {
        this.position = position;
        this.instruction = instruction;
        this.instruccionAsm = instruccionAsm;
    }

    public String getInstruccionAsm() {
        return instruccionAsm;
    }
    public void setInstruccionAsm(String instruccionAsm) {
        this.instruccionAsm = instruccionAsm;
    }
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
}
