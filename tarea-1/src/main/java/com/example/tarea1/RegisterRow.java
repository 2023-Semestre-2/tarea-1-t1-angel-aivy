package com.example.tarea1;

public class RegisterRow {
    private String register;
    private String value;

    public RegisterRow(String register, String value) {
        this.register = register;
        this.value = value;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
