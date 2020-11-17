package com.example.seguimientopresion.ui.home;


//Pacientes, constructor Getters y Setters
public class Pacientes {

    public String getSys() {
        return sistolic;
    }

    public String getDys() {
        return diastolic;
    }

    public String getPulse() {
        return pulse;
    }

    public String getDate() { return date_time; }

    public Pacientes(String sistolic, String diastolic, String pulse, String date_time) {
        this.sistolic = sistolic;
        this.diastolic = diastolic;
        this.pulse = pulse;
        this.date_time = date_time;
    }

    public Pacientes(){}

    public void setSistolic(String sistolic) {
        this.sistolic = sistolic;
    }

    public void setDiastolic(String diastolic) {
        this.diastolic = diastolic;
    }

    public void setPulse(String pulse) {
        this.pulse = pulse;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    private String sistolic;
    private String diastolic;
    private String pulse;
    private String date_time;
}
