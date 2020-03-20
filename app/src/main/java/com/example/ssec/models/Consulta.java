package com.example.ssec.models;

public class Consulta {
    private String id;
    private String lugar;
    private String motivo;
    private String fecha;
    private String diagnostico;
    private String observaciones;
    private String medico;
    private String paciente;
    private String ficha;
    private String estado;

    public Consulta(String id, String lugar, String motivo, String fecha, String diagnostico, String observaciones, String medico, String paciente, String ficha, String estado) {
        this.id = id;
        this.lugar = lugar;
        this.motivo = motivo;
        this.fecha = fecha;
        this.diagnostico = diagnostico;
        this.observaciones = observaciones;
        this.medico = medico;
        this.paciente = paciente;
        this.ficha = ficha;
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getFicha() {
        return ficha;
    }

    public void setFicha(String ficha) {
        this.ficha = ficha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
