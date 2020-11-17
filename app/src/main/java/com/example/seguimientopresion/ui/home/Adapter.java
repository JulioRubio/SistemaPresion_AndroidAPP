package com.example.seguimientopresion.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seguimientopresion.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.List;

public class Adapter extends FirestoreRecyclerAdapter<Pacientes, Adapter.PresionHolder> {

    public Adapter(@NonNull FirestoreRecyclerOptions<Pacientes> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PresionHolder holder, int position, @NonNull Pacientes model) {
        holder.mSys.setText(model.getSys());
        holder.mDys.setText(model.getDys());
        holder.mPulso.setText(model.getPulse());
        holder.mDate.setText(model.getDate());
    }

    @NonNull
    @Override
    public PresionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tomas_presion_item,parent,false);
        return new PresionHolder(view);
    }

    class PresionHolder extends RecyclerView.ViewHolder{

        public TextView mDate;
        public TextView mSys;
        public TextView mDys;
        public TextView mPulso;

        public PresionHolder(@NonNull View itemView) {
            super(itemView);
            mDate = itemView.findViewById(R.id.txt_date);
            mSys = itemView.findViewById(R.id.txt_sistolica);
            mDys = itemView.findViewById(R.id.txt_diastolica);
            mPulso = itemView.findViewById(R.id.txt_pulso);
        }
    }
}

