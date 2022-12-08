package com.example.cartillav.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cartillav.R;
import com.example.cartillav.databinding.FragmentGalleryBinding;

import com.example.cartillav.editar_vacuna;
import com.example.cartillav.ui.model.Vacuna;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class GalleryFragment extends Fragment {
    private View ViewVacunas;

    ImageButton editar;


    private RecyclerView mRecycler;
    FirebaseFirestore mFirestoree;
    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewVacunas = inflater.inflate(R.layout.fragment_gallery,container,false);
        mFirestoree = FirebaseFirestore.getInstance();
        mRecycler = (RecyclerView) ViewVacunas.findViewById(R.id.listaClientes);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        editar = ViewVacunas.findViewById(R.id.btnEditar);

        return ViewVacunas;

    }

    @Override
    public void onStart() {
        super.onStart();
        Query query = mFirestoree.collection("vacunas");
        FirestoreRecyclerOptions<Vacuna> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Vacuna>().setQuery(query,Vacuna.class).build();
        FirestoreRecyclerAdapter<Vacuna,vacunaViewHolder> adapter = new FirestoreRecyclerAdapter<Vacuna, vacunaViewHolder>(firestoreRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull vacunaViewHolder holder, int position, @NonNull Vacuna Vacuna) {
                DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(holder.getAbsoluteAdapterPosition());
                String id = documentSnapshot.getId();
                holder.Nomb.setText(Vacuna.getNombre());
                holder.FechaApli.setText(Vacuna.getFecha_Aplicacion());
                holder.ProximaCi.setText(Vacuna.getProxima_Cita());
                holder.Vacu.setText(Vacuna.getVacuna());
                holder.Editar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "si sirve el editar", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getActivity(), editar_vacuna.class);
                        i.putExtra("Id_nss",id);
                        startActivity(i);
                    }
                });

            }
            @NonNull
            @Override
            public vacunaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_visualizar_vacuna,viewGroup,false);
                vacunaViewHolder viewHolder = new vacunaViewHolder(view);
                return viewHolder;
            }
        };

        adapter.notifyDataSetChanged();
        mRecycler.setAdapter(adapter);
        adapter.startListening();

    }
    public class vacunaViewHolder extends RecyclerView.ViewHolder{

        TextView Nomb,Vacu,FechaApli,ProximaCi;
        ImageButton Editar, Eliminar;
        public vacunaViewHolder(@NonNull View itemView) {
            super(itemView);
            Nomb = itemView.findViewById(R.id.txtName);
            Vacu = itemView.findViewById(R.id.txtVac);
            FechaApli = itemView.findViewById(R.id.txtFecApl);
            ProximaCi = itemView.findViewById(R.id.txtProCita);
            Editar = itemView.findViewById(R.id.btnEditar);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}