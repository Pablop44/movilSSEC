package com.example.ssec.models;

public class Medicamento {
    private String id;
    private String medicamento;
    private String tratamiento;

    public Medicamento(String id, String medicamento, String tratamiento) {
        this.id = id;
        this.medicamento = medicamento;
        this.tratamiento = tratamiento;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }
}
