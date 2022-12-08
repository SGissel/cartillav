package com.example.cartillav;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.cartillav.ui.adapter.CardAdapter;
import com.example.cartillav.ui.model.Vacuna;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class visualizar_vacuna extends AppCompatActivity {
    RecyclerView mRecycler;
    FirebaseFirestore mFirestoreee;
    CardAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_vacuna);


        mFirestoreee = FirebaseFirestore.getInstance();
        mRecycler =findViewById(R.id.listaClientes);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        Query query = mFirestoreee.collection("vacunas");

        FirestoreRecyclerOptions<Vacuna> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Vacuna>().setQuery(query,Vacuna.class).build();
        mAdapter = new CardAdapter(firestoreRecyclerOptions, this, getSupportFragmentManager());
        mAdapter.notifyDataSetChanged();

        mRecycler.setAdapter(mAdapter);

        Intent in = getIntent();
        String Nss = in.getExtras().getString("NSS");

    }


    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }

}