package com.example.cartillav;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;


import com.example.cartillav.ui.gallery.GalleryFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivityPaciente extends Activity  {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://cartillav-49786-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acttivity_main_paciente);



        final EditText nombrePa = findViewById(R.id.nombrePa);
        final EditText clavePa = findViewById(R.id.clavePa);
        final Button inicio2 = findViewById(R.id.iniciose);

        inicio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nombrePaciente = nombrePa.getText().toString();
                final String clavePaciente= clavePa.getText().toString();

                if (nombrePaciente.isEmpty() || clavePaciente.isEmpty()){
                    Toast.makeText(MainActivityPaciente.this, "Ingresa tus datos para inciar sesión", Toast.LENGTH_SHORT).show();
                }else{
                    databaseReference.child("paciente").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //verificar si se encuentra en la BD de firebase
                            if (snapshot.hasChild(clavePaciente)){
                                final String getclavePa = snapshot.child(clavePaciente).child("nombre").getValue(String.class);
                                if (getclavePa.equals(nombrePaciente)){
                                    Toast.makeText(MainActivityPaciente.this, "Acceso Autorizado", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(MainActivityPaciente.this,visualizar_vacuna_paciente.class));
                                }else {
                                    Toast.makeText(MainActivityPaciente.this, "Acceso denegado", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(MainActivityPaciente.this, "Verifica tus datos", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
        });
    }

    public void medico(View view) {
        startActivity(new Intent(MainActivityPaciente.this,MainActivity.class));
    }

}