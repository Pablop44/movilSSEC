package com.example.ssec.models;

public class InformeAsma {
    private String id;
    private String fecha;
    private String calidadSueno;
    private String dificultadRespirar;
    private String tos;
    private String gravedadTos;
    private String limitaciones;
    private String silbidos;
    private String usoMedicacion;
    private String espirometria;
    private String factoresCrisis;
    private String estadoGeneral;

    public InformeAsma(String id, String fecha, String calidadSueno, String dificultadRespirar, String tos, String gravedadTos, String limitaciones, String silbidos, String usoMedicacion, String espirometria, String factoresCrisis, String estadoGeneral) {
        this.id = id;
        this.fecha = fecha;
        this.calidadSueno = calidadSueno;
        this.dificultadRespirar = dificultadRespirar;
        this.tos = tos;
        this.gravedadTos = gravedadTos;
        this.limitaciones = limitaciones;
        this.silbidos = silbidos;
        this.usoMedicacion = usoMedicacion;
        this.espirometria = espirometria;
        this.factoresCrisis = factoresCrisis;
        this.estadoGeneral = estadoGeneral;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCalidadSueno() {
        return calidadSueno;
    }

    public void setCalidadSueno(String calidadSueno) {
        this.calidadSueno = calidadSueno;
    }

    public String getDificultadRespirar() {
        return dificultadRespirar;
    }

    public void setDificultadRespirar(String dificultadRespirar) {
        this.dificultadRespirar = dificultadRespirar;
    }

    public String getTos() {
        return tos;
    }

    public void setTos(String tos) {
        this.tos = tos;
    }

    public String getGravedadTos() {
        return gravedadTos;
    }

    public void setGravedadTos(String gravedadTos) {
        this.gravedadTos = gravedadTos;
    }

    public String getLimitaciones() {
        return limitaciones;
    }

    public void setLimitaciones(String limitaciones) {
        this.limitaciones = limitaciones;
    }

    public String getSilbidos() {
        return silbidos;
    }

    public void setSilbidos(String silbidos) {
        this.silbidos = silbidos;
    }

    public String getUsoMedicacion() {
        return usoMedicacion;
    }

    public void setUsoMedicacion(String usoMedicacion) {
        this.usoMedicacion = usoMedicacion;
    }

    public String getEspirometria() {
        return espirometria;
    }

    public void setEspirometria(String espirometria) {
        this.espirometria = espirometria;
    }

    public String getFactoresCrisis() {
        return factoresCrisis;
    }

    public void setFactoresCrisis(String factoresCrisis) {
        this.factoresCrisis = factoresCrisis;
    }

    public String getEstadoGeneral() {
        return estadoGeneral;
    }

    public void setEstadoGeneral(String estadoGeneral) {
        this.estadoGeneral = estadoGeneral;
    }

    public String[] getFechaHora(){
        String[] valores = fecha.split(" ");
        return valores;
    }
}
