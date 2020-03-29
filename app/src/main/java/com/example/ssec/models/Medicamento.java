package com.example.ssec.models;

public class Medicamento {
    private String nombre;
    private String viaAdministracion;
    private String marca;
    private String dosis;

    public Medicamento(String nombre, String viaAdministracion, String marca, String dosis) {
        this.nombre = nombre;
        this.viaAdministracion = viaAdministracion;
        this.marca = marca;
        this.dosis = dosis;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getViaAdministracion() {
        return viaAdministracion;
    }

    public void setViaAdministracion(String viaAdministracion) {
        this.viaAdministracion = viaAdministracion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }
}
