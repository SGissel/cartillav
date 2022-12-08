package com.example.cartillav.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cartillav.R;
import com.example.cartillav.editar_vacuna;
import com.example.cartillav.ui.gallery.GalleryFragment;
import com.example.cartillav.ui.model.Vacuna;
import com.example.cartillav.visualizar_vacuna;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class CardAdapter extends FirestoreRecyclerAdapter<Vacuna, CardAdapter.ViewHolder> {
    Activity activity;

    private FirebaseFirestore mfirestore = FirebaseFirestore.getInstance();
    FragmentManager fm;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CardAdapter(@NonNull FirestoreRecyclerOptions<Vacuna> options,visualizar_vacuna visualizar_vacuna, FragmentManager supportFragmentManager) {
        super(options);
        this.activity = activity;
        this.fm = fm;


    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewholder, int i, @NonNull Vacuna Vacuna){
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(viewholder.getAbsoluteAdapterPosition());
        String id = documentSnapshot.getId();
        viewholder.Nombre.setText(Vacuna.getNombre());
        viewholder.Fecha_Aplicacion.setText(Vacuna.getFecha_Aplicacion());
        viewholder.Proxima_Cita.setText(Vacuna.getProxima_Cita());
        viewholder.Vacuna.setText(Vacuna.getVacuna());

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_gallery,null, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Fecha_Aplicacion;
        TextView Nombre;
        TextView Proxima_Cita;
        TextView Vacuna;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Nombre =itemView.findViewById(R.id.txtName);
            Vacuna  = itemView.findViewById(R.id.txtVac);
            Fecha_Aplicacion = itemView.findViewById(R.id.txtFecApl);
            Proxima_Cita = itemView.findViewById(R.id.txtProCita);


        }


    }
}

