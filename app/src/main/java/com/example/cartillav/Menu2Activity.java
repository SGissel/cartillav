package com.example.cartillav;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cartillav.databinding.ActivityMenu2Binding;

import com.example.cartillav.ui.gallery.GalleryFragment;
import com.example.cartillav.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;
import java.util.Map;

public class Menu2Activity extends AppCompatActivity {
    EditText numero,nombreC,vacuna,idv,fechaA,proximaC;
    Button RegistrarVacuna;
    FirebaseFirestore mfirestore;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.cartillav.databinding.ActivityMenu2Binding binding = ActivityMenu2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMenu.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.editarFragment)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        //Instacia a la bd
        mfirestore = FirebaseFirestore.getInstance();
        //insertar datos a firebase

        numero = findViewById(R.id.numero);
        nombreC = findViewById(R.id.nombreC);
        vacuna = findViewById(R.id.vacuna);
        idv = findViewById(R.id.idv);
        fechaA =findViewById(R.id.fechaA);
        proximaC = findViewById(R.id.proximaC);
        RegistrarVacuna = findViewById(R.id.RegistrarVacunaa);

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.cerrarsesion:
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                finish();
                Toast.makeText(this, "Se ha cerrado sesión correctamente", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void regi(View view) {
        Navigation.findNavController(this, R.id.nav_host_fragment_content_menu).navigate(R.id.nav_home);

    }
    public void vis(View view) {
        Navigation.findNavController(this, R.id.nav_host_fragment_content_menu).navigate(R.id.nav_gallery);
        //startActivity(new Intent(Menu2Activity.this,visualizar_vacuna.class));
    }
}
