package com.example.cartillav;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cartillav.ui.adapter.PacienteAdapter;
import com.example.cartillav.ui.model.Paciente;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class visualizar_vacuna_paciente extends AppCompatActivity {
    RecyclerView mRecyclerPa;
    PacienteAdapter mAdapterPa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_vacuna_paciente);
        FirebaseFirestore mfirestoreprueba = FirebaseFirestore.getInstance();
        mRecyclerPa = findViewById(R.id.listaClientesPa);
        mRecyclerPa.setLayoutManager(new LinearLayoutManager(this));
        Query queryPa = mfirestoreprueba.collection("vacunas");
        FirestoreRecyclerOptions<Paciente> options = new FirestoreRecyclerOptions.Builder<Paciente>().setQuery(queryPa,Paciente.class).build();
        mAdapterPa = new PacienteAdapter(options);
        mAdapterPa.notifyDataSetChanged();
        mRecyclerPa.setAdapter(mAdapterPa);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapterPa.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapterPa.stopListening();
    }

    public void CerrarPa(View view) {
        startActivity(new Intent(visualizar_vacuna_paciente.this,MainActivityPaciente.class));

    }
}