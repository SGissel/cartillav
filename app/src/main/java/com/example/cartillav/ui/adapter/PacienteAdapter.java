package com.example.cartillav.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cartillav.R;
import com.example.cartillav.ui.model.Paciente;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class PacienteAdapter extends FirestoreRecyclerAdapter<Paciente,PacienteAdapter.ViewHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public PacienteAdapter(@NonNull FirestoreRecyclerOptions<Paciente> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int i, @NonNull Paciente Paciente) {
        holder.NombrePa.setText(Paciente.getNombre());
        holder.VacunaPa.setText(Paciente.getVacuna());
        holder.AplicacionPa.setText(Paciente.getFecha_Aplicacion());
        holder.CitaPa.setText(Paciente.getProxima_Cita());

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_paciente,parent,false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView NombrePa,VacunaPa,AplicacionPa,CitaPa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            NombrePa = itemView.findViewById(R.id.NamePa);
            VacunaPa = itemView.findViewById(R.id.VacPa);
            AplicacionPa = itemView.findViewById(R.id.FecAplPa);
            CitaPa = itemView.findViewById(R.id.ProCitaPa);
        }
    }
}
