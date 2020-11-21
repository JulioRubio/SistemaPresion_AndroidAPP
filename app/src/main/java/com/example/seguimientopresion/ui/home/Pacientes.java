package com.example.seguimientopresion.ui.home;


//Pacientes, constructor Getters y Setters
public class Pacientes {

    public Pacientes(){}

    public Pacientes(String sistolic, String diastolic, String pulse, String date_time) {
        this.sistolic = sistolic;
        this.diastolic = diastolic;
        this.pulse = pulse;
        this.date_time = date_time;
    }

    public String getSistolic() {
        return sistolic;
    }

    public void setSistolic(String sistolic) {
        this.sistolic = sistolic;
    }

    public String getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(String diastolic) {
        this.diastolic = diastolic;
    }

    public String getPulse() {
        return pulse;
    }

    public void setPulse(String pulse) {
        this.pulse = pulse;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    private String sistolic;
    private String diastolic;
    private String pulse;
    private String date_time;
}
