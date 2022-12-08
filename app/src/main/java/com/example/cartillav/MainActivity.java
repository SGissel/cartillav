package com.example.cartillav;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://cartillav-49786-default-rtdb.firebaseio.com/");
    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;
    RelativeLayout mMainLayout;
    ImageButton editar;


    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mMainLayout=findViewById(R.id.main_layout);
        huelladigital();
        //prueba de firestore
        /*FirebaseFirestore mfirestoreprueba = FirebaseFirestore.getInstance();
        mfirestoreprueba.collection("vacunas").whereEqualTo("NSS","2749212323").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
             {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                        Log.d("vacunas",documentSnapshot.getId()+"=>"+documentSnapshot.getData());
                    }
                }else {
                    Log.w("vacunas","ERROR",task.getException());
                }
            }
        });*/

        final EditText nombre = findViewById(R.id.nombre);
        final EditText clave = findViewById(R.id.clave);
        final Button inicio = findViewById(R.id.inicio);


        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nombremedico = nombre.getText().toString();
                final String clavemedico = clave.getText().toString();

                if (nombremedico.isEmpty() || clavemedico.isEmpty()){
                    Toast.makeText(MainActivity.this, "Ingresa tus datos para inciar sesión", Toast.LENGTH_SHORT).show();
                }else{
                    databaseReference.child("medico").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //verificar si se encuentra en la BD de firebase
                            if (snapshot.hasChild(clavemedico)){
                                final String getnombre = snapshot.child(clavemedico).child("nombre").getValue(String.class);
                                if (getnombre.equals(nombremedico)){
                                    Toast.makeText(MainActivity.this, "Acceso Autorizado", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(MainActivity.this,Menu2Activity.class));
                                    finish();
                                }else {
                                    Toast.makeText(MainActivity.this, "Acceso denegado", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(MainActivity.this, "Verifica tus datos", Toast.LENGTH_SHORT).show();
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

    private void huelladigital() {
        //Huella digital
        BiometricManager biometricManager = BiometricManager.from(this);
        //verifica si esos elementos están disponibles
        switch (biometricManager.canAuthenticate()){
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(this, "el dispositivo no tiene huella digital", Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(this, "no funciona", Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Toast.makeText(this, "No hay ninguna huella digital", Toast.LENGTH_SHORT).show();
        }
        Executor executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(MainActivity.this, "Inicio de sesion satisfactorio", Toast.LENGTH_SHORT).show();
                mMainLayout.setVisibility(View.VISIBLE);
                startActivity(new Intent(MainActivity.this,Menu2Activity.class));

            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });
        // Permite al usuario autenticarse utilizando una biometría
        promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("IMSS")
                .setDescription("Si eres médico usa la huella digital para iniciar sesión")
                .setNegativeButtonText("Cancelar").build();
        biometricPrompt.authenticate(promptInfo);
    }

    public void paciente(View view) {
        startActivity(new Intent(MainActivity.this,MainActivityPaciente.class));
    }

}