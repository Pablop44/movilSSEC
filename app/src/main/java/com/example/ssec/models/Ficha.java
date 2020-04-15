package com.example.ssec.models;

import java.util.List;

public class Ficha {
    private String id;
    private String fechaCreacion;
    private String paciente;
    private String medico;
    private List<Enfermedad> enfermedad;

    public Ficha(String id, String fechaCreacion, String paciente, String medico, List<Enfermedad> enfermedad) {
        this.id = id;
        this.fechaCreacion = fechaCreacion;
        this.paciente = paciente;
        this.medico = medico;
        this.enfermedad = enfermedad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public List<Enfermedad> getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(List<Enfermedad> enfermedad) {
        this.enfermedad = enfermedad;
    }
}
