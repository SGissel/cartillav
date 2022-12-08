package com.example.cartillav.ui.model;

import androidx.fragment.app.FragmentManager;

import com.example.cartillav.visualizar_vacuna;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class Vacuna {
    String Fecha_Aplicacion,ID,NSS,Nombre,Proxima_Cita,Vacuna;
    public Vacuna(){}

    public Vacuna(String fecha_Aplicacion, String ID, String NSS, String nombre, String proxima_Cita, String vacuna) {
        Fecha_Aplicacion = fecha_Aplicacion;
        this.ID = ID;
        this.NSS = NSS;
        Nombre = nombre;
        Proxima_Cita = proxima_Cita;
        Vacuna = vacuna;
    }

    public String getFecha_Aplicacion() {
        return Fecha_Aplicacion;
    }

    public void setFecha_Aplicacion(String fecha_Aplicacion) {
        Fecha_Aplicacion = fecha_Aplicacion;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNSS() {
        return NSS;
    }

    public void setNSS(String NSS) {
        this.NSS = NSS;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getProxima_Cita() {
        return Proxima_Cita;
    }

    public void setProxima_Cita(String proxima_Cita) {
        Proxima_Cita = proxima_Cita;
    }

    public String getVacuna() {
        return Vacuna;
    }

    public void setVacuna(String vacuna) {
        Vacuna = vacuna;
    }
}
