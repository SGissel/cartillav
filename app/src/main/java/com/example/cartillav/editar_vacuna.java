package com.example.cartillav;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cartillav.ui.gallery.GalleryFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class editar_vacuna extends AppCompatActivity {
    EditText numeroEditar, nombreEditar, vacunaEditar, idvEditar, fechaEditar, proximaCEditar;
    TextView Delete;
    ImageButton  Eliminar;
    Button Actualizar;
    private String registrosID;
    private FirebaseFirestore mfire;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_vacuna);
        mfire = FirebaseFirestore.getInstance();
        registrosID = getIntent().getStringExtra("Id_nss");

        numeroEditar = findViewById(R.id.numeroEditar);
        nombreEditar = findViewById(R.id.nombreEditar);
        vacunaEditar = findViewById(R.id.vacunaEditar);
        idvEditar = findViewById(R.id.idvEditar);
        fechaEditar = findViewById(R.id.fechaEditar);
        proximaCEditar = findViewById(R.id.proximaCEditar);
        Actualizar = findViewById(R.id.Actualizar);
        Delete =findViewById(R.id.DeleteReg);

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NSS =numeroEditar.getText().toString();
                numeroEditar.setText("");
                nombreEditar.setText("");
                vacunaEditar.setText("");
                idvEditar.setText("");
                fechaEditar.setText("");
                proximaCEditar.setText("");
                DeleteReg(NSS);
            }
        });

        /*Eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NSS = numeroEditar.getText().toString();
                numeroEditar.setText("");
                DeleteReg(NSS);
            }
        });*/

        Actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateReg();
            }
        });
        GetReg();
    }

    private void GetReg() {
        mfire.collection("vacunas").document(registrosID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String noEdit = documentSnapshot.getString("NSS");
                String nomEdit = documentSnapshot.getString("Nombre");
                String vacEdit = documentSnapshot.getString("Vacuna");
                String idvEdit= documentSnapshot.getString("ID");
                String proEdit = documentSnapshot.getString("Proxima_Cita");
                String fecEdit = documentSnapshot.getString("Fecha_Aplicacion");

                numeroEditar.setText(noEdit);
                nombreEditar.setText(nomEdit);
                vacunaEditar.setText(vacEdit);
                idvEditar.setText(idvEdit);
                fechaEditar.setText(fecEdit);
                proximaCEditar.setText(proEdit);

            }

        });

    }

    private void UpdateReg() {
        String noEdit = numeroEditar.getText().toString();
        String nomEdit = nombreEditar.getText().toString();
        String vacEdit = vacunaEditar.getText().toString();
        String idvEdit = idvEditar.getText().toString();
        String fecEdit = fechaEditar.getText().toString();
        String proEdit = proximaCEditar.getText().toString();

        Map<String,Object> map = new HashMap<>();
        map.put("NSS",noEdit);
        map.put("Nombre",nomEdit);
        map.put("Vacuna",vacEdit);
        map.put("ID",idvEdit);
        map.put("Fecha_Aplicacion",fecEdit);
        map.put("Proxima_Cita",proEdit);

        mfire.collection("vacunas").document(registrosID).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al actualizar los datos", Toast.LENGTH_SHORT).show();

            }
        });
        numeroEditar.setText("");
        nombreEditar.setText("");
        vacunaEditar.setText("");
        idvEditar.setText("");
        fechaEditar.setText("");
        proximaCEditar.setText("");
    }
    private void DeleteReg(String NSS ) {
        mfire.collection("vacunas").whereEqualTo("NSS",NSS).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() && !task.getResult().isEmpty()){
                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                    String document = documentSnapshot.getId();
                    mfire.collection("vacunas").document(registrosID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "Se ha eliminado correctamente", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Error al eliminar los datos", Toast.LENGTH_SHORT).show();

                        }
                    });
                }else {
                    Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    public void Cerrareditar(View view) {

    }
}