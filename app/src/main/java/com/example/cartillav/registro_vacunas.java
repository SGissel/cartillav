package com.example.cartillav;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class registro_vacunas extends AppCompatActivity {
    EditText numero,nombreC,vacuna,idv,fechaA,proximaC;
    Button RegistrarVacuna;
    FirebaseFirestore mfirestore;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_vacunas);
        drawerLayout = findViewById(R.id.drawerlayout2);
        findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        NavigationView navigationView = findViewById(R.id.navigationview);
        navigationView.setItemIconTintList(null);


        //Instacia a la bd
        mfirestore = FirebaseFirestore.getInstance();
        //insertar datos a firebase

        numero = findViewById(R.id.numero);
        nombreC = findViewById(R.id.nombreC);
        vacuna = findViewById(R.id.vacuna);
        idv = findViewById(R.id.idv);
        fechaA =findViewById(R.id.fechaA);
        proximaC = findViewById(R.id.proximaC);
        RegistrarVacuna = findViewById(R.id.RegistrarVacuna);

        RegistrarVacuna.setOnClickListener(v -> {
            String numeroMedico = numero.getText().toString().trim();
            String nombreMedico = nombreC.getText().toString().trim();
            String vacunaMedico = vacuna.getText().toString().trim();
            String idvacMedico = idv.getText().toString().trim();
            String aplicacionMedico = fechaA.getText().toString().trim();
            String citaMedico = proximaC.getText().toString().trim();

            if (numeroMedico.isEmpty() && nombreMedico.isEmpty() && vacunaMedico.isEmpty() && idvacMedico.isEmpty() && aplicacionMedico.isEmpty() && citaMedico.isEmpty()){
                Toast.makeText(getApplicationContext(), "No dejes ningún campo vacío", Toast.LENGTH_SHORT).show();
            }else {
                postReg(numeroMedico,nombreMedico,vacunaMedico,idvacMedico,aplicacionMedico,citaMedico);
            }
            numero.setText("");
            nombreC.setText("");
            vacuna.setText("");
            idv.setText("");
            fechaA.setText("");
            proximaC.setText("");
        });
    }
    private void postReg(String numeroMedico, String nombreMedico, String vacunaMedico, String idvacMedico, String aplicacionMedico, String citaMedico) {
        Map<String,Object> map = new HashMap<>();
        map.put("NSS",numeroMedico);
        map.put("Nombre",nombreMedico);
        map.put("Vacuna",vacunaMedico);
        map.put("ID",idvacMedico);
        map.put("Fecha_Aplicacion",aplicacionMedico);
        map.put("Proxima_Cita",citaMedico);

        mfirestore.collection("vacunas").add(map).addOnSuccessListener(documentReference -> {
            Toast.makeText(getApplicationContext(), "Se ha registrado correctamente la vacuna", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Error al registrar la vacuna", Toast.LENGTH_SHORT).show());
    }

    public void CerrarSesion(View view) {
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        finish();
    }

}