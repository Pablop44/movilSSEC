package com.example.ssec.models;

public class Nota {
    private String id;
    private String fecha;
    private String datos;
    private String ficha;

    public Nota(String id, String fecha, String datos, String ficha) {
        this.id = id;
        this.fecha = fecha;
        this.datos = datos;
        this.ficha = ficha;
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

    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }

    public String getFicha() {
        return ficha;
    }

    public void setFicha(String ficha) {
        this.ficha = ficha;
    }
    
    public String[] getFechaHora(){
        String[] valores = fecha.split(" ");
        return valores;
    }
}
