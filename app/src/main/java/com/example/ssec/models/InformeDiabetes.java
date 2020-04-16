package com.example.ssec.models;

import java.util.List;

public class InformeDiabetes {
    private String id;
    private String fecha;
    private String ficha;
    private String numeroControles;
    private String nivelBajo;
    private String frecuenciaBajo;
    private String horarioBajo;
    private String perdidaConocimiento;
    private String nivelAlto;
    private String frecuenciaAlto;
    private String horarioAlto;
    private String actividadFisica;
    private String problemaDieta;
    private String estadoGeneral;
    private List<Momento> momentos;

    public InformeDiabetes(String id, String fecha, String ficha, String numeroControles, String nivelBajo, String frecuenciaBajo, String horarioBajo, String perdidaConocimiento, String nivelAlto, String frecuenciaAlto, String horarioAlto, String actividadFisica, String problemaDieta, String estadoGeneral, List<Momento> momentos) {
        this.id = id;
        this.fecha = fecha;
        this.ficha = ficha;
        this.numeroControles = numeroControles;
        this.nivelBajo = nivelBajo;
        this.frecuenciaBajo = frecuenciaBajo;
        this.horarioBajo = horarioBajo;
        this.perdidaConocimiento = perdidaConocimiento;
        this.nivelAlto = nivelAlto;
        this.frecuenciaAlto = frecuenciaAlto;
        this.horarioAlto = horarioAlto;
        this.actividadFisica = actividadFisica;
        this.problemaDieta = problemaDieta;
        this.estadoGeneral = estadoGeneral;
        this.momentos = momentos;
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

    public String getNumeroControles() {
        return numeroControles;
    }

    public void setNumeroControles(String numeroControles) {
        this.numeroControles = numeroControles;
    }

    public String getNivelBajo() {
        return nivelBajo;
    }

    public void setNivelBajo(String nivelBajo) {
        this.nivelBajo = nivelBajo;
    }

    public String getFrecuenciaBajo() {
        return frecuenciaBajo;
    }

    public void setFrecuenciaBajo(String frecuenciaBajo) {
        this.frecuenciaBajo = frecuenciaBajo;
    }

    public String getHorarioBajo() {
        return horarioBajo;
    }

    public void setHorarioBajo(String horarioBajo) {
        this.horarioBajo = horarioBajo;
    }

    public String getPerdidaConocimiento() {
        return perdidaConocimiento;
    }

    public void setPerdidaConocimiento(String perdidaConocimiento) {
        this.perdidaConocimiento = perdidaConocimiento;
    }

    public String getNivelAlto() {
        return nivelAlto;
    }

    public void setNivelAlto(String nivelAlto) {
        this.nivelAlto = nivelAlto;
    }

    public String getFrecuenciaAlto() {
        return frecuenciaAlto;
    }

    public void setFrecuenciaAlto(String frecuenciaAlto) {
        this.frecuenciaAlto = frecuenciaAlto;
    }

    public String getHorarioAlto() {
        return horarioAlto;
    }

    public void setHorarioAlto(String horarioAlto) {
        this.horarioAlto = horarioAlto;
    }

    public String getActividadFisica() {
        return actividadFisica;
    }

    public void setActividadFisica(String actividadFisica) {
        this.actividadFisica = actividadFisica;
    }

    public String getProblemaDieta() {
        return problemaDieta;
    }

    public void setProblemaDieta(String problemaDieta) {
        this.problemaDieta = problemaDieta;
    }

    public String getEstadoGeneral() {
        return estadoGeneral;
    }

    public void setEstadoGeneral(String estadoGeneral) {
        this.estadoGeneral = estadoGeneral;
    }

    public List<Momento> getMomentos() {
        return momentos;
    }

    public void setMomentos(List<Momento> momentos) {
        this.momentos = momentos;
    }
}
