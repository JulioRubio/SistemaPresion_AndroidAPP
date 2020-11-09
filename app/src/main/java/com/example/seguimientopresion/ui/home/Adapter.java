package com.example.seguimientopresion.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seguimientopresion.Pacientes;
import com.example.seguimientopresion.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    private Context mContexT;
    private List<Pacientes> mPacientesList;

    public Adapter(Context mContexT, List<Pacientes> mMateriasList) {
        this.mContexT = mContexT;
        this.mPacientesList = mMateriasList;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContexT).inflate(R.layout.tomas_presion_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        // Agrega código aquí
        Pacientes currentItem = mPacientesList.get(position);
    }

    @Override
    public int getItemCount() {
        // Agrega código aquí
        return mPacientesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Agrega código aquí
        public TextView mDate;
        public TextView mSys;
        public TextView mDys;
        public TextView mPulso;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mDate = itemView.findViewById(R.id.txt_date);
            mSys = itemView.findViewById(R.id.txt_sistolica);
            mDys = itemView.findViewById(R.id.txt_diastolica);
            mPulso = itemView.findViewById(R.id.txt_pulso);
        }
    }
}
