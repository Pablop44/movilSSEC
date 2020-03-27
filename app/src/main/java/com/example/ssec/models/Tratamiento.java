package com.example.ssec.models;

import java.util.List;

public class Tratamiento {
    private String id;
    private String posologia;
    private String fechaInicio;
    private String fechaFin;
    private String horario;
    private String enfermedad;
    private List<Medicamento> medicamentos;

    public Tratamiento(String id, String posologia, String fechaInicio, String fechaFin, String horario, String enfermedad, List<Medicamento> medicamentos) {
        this.id = id;
        this.posologia = posologia;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.horario = horario;
        this.enfermedad = enfermedad;
        this.medicamentos = medicamentos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPosologia() {
        return posologia;
    }

    public void setPosologia(String posologia) {
        this.posologia = posologia;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(String enfermedad) {
        this.enfermedad = enfermedad;
    }

    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }

    public String[] getFechaHoraInicio(){
        String[] valores = fechaInicio.split(" ");
        return valores;
    }

    public String[] getFechaHoraFin(){
        String[] valores = fechaFin.split(" ");
        return valores;
    }
}
