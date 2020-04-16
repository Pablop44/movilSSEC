package com.example.ssec.models;

import java.util.List;

public class InformeMigranas {
    private String id;
    private String fecha;
    private String ficha;
    private String frecuencia;
    private String duracion;
    private String horario;
    private String finalizacion;
    private String tipoEpisodio;
    private String intensidad;
    private String limitaciones;
    private String despiertoNoche;
    private String estadoGeneral;
    private List<Sintoma> sintomas;
    private List<Factor> factores;

    public InformeMigranas(String id, String fecha, String ficha, String frecuencia, String duracion, String horario, String finalizacion, String tipoEpisodio, String intensidad, String limitaciones, String despiertoNoche, String estadoGeneral, List<Sintoma> sintomas, List<Factor> factores) {
        this.id = id;
        this.fecha = fecha;
        this.ficha = ficha;
        this.frecuencia = frecuencia;
        this.duracion = duracion;
        this.horario = horario;
        this.finalizacion = finalizacion;
        this.tipoEpisodio = tipoEpisodio;
        this.intensidad = intensidad;
        this.limitaciones = limitaciones;
        this.despiertoNoche = despiertoNoche;
        this.estadoGeneral = estadoGeneral;
        this.sintomas = sintomas;
        this.factores = factores;
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

    public String getFicha() {
        return ficha;
    }

    public void setFicha(String ficha) {
        this.ficha = ficha;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getFinalizacion() {
        return finalizacion;
    }

    public void setFinalizacion(String finalizacion) {
        this.finalizacion = finalizacion;
    }

    public String getTipoEpisodio() {
        return tipoEpisodio;
    }

    public void setTipoEpisodio(String tipoEpisodio) {
        this.tipoEpisodio = tipoEpisodio;
    }

    public String getIntensidad() {
        return intensidad;
    }

    public void setIntensidad(String intensidad) {
        this.intensidad = intensidad;
    }

    public String getLimitaciones() {
        return limitaciones;
    }

    public void setLimitaciones(String limitaciones) {
        this.limitaciones = limitaciones;
    }

    public String getDespiertoNoche() {
        return despiertoNoche;
    }

    public void setDespiertoNoche(String despiertoNoche) {
        this.despiertoNoche = despiertoNoche;
    }

    public String getEstadoGeneral() {
        return estadoGeneral;
    }

    public void setEstadoGeneral(String estadoGeneral) {
        this.estadoGeneral = estadoGeneral;
    }

    public List<Sintoma> getSintomas() {
        return sintomas;
    }

    public void setSintomas(List<Sintoma> sintomas) {
        this.sintomas = sintomas;
    }

    public List<Factor> getFactores() {
        return factores;
    }

    public void setFactores(List<Factor> factores) {
        this.factores = factores;
    }
}
