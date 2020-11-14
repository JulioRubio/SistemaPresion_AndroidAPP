package com.example.seguimientopresion.ui.home;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.seguimientopresion.R;
import com.example.seguimientopresion.ui.datos_paciente.DatePickerFragment;
import com.example.seguimientopresion.ui.datos_paciente.TimePickerFragment;

import java.text.DateFormat;
import java.util.Calendar;

import com.example.seguimientopresion.HomeActivity;

public class RegistroPresionFragment extends Fragment {

    private HomeViewModel homeViewModel;
    EditText et_sistolica, et_diastolica, et_pulso;
    Button bt_guardar;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_registro_presion, container, false);

        et_sistolica = root.findViewById(R.id.edit_text_sistolica);
        et_diastolica = root.findViewById(R.id.edit_text_diastolica);
        et_pulso = root.findViewById(R.id.edit_text_pulso);

        bt_guardar = root.findViewById(R.id.guardar_presion);

        bt_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sistolicText = et_sistolica.getText().toString();
                String diastolicText = et_diastolica.getText().toString();
                String pulseText = et_pulso.getText().toString();
                if(sistolicText.isEmpty())
                {
                    et_sistolica.setError("Por favor ingrese presion sistolica");
                    et_sistolica.requestFocus();
                }
                if(diastolicText.isEmpty())
                {
                    et_diastolica.setError("Por favor ingrese presion diastolica");
                    et_diastolica.requestFocus();
                }
                if(pulseText.isEmpty())
                {
                    et_pulso.setError("Por favor ingrese su pulso");
                    et_pulso.requestFocus();
                }
                if(!(sistolicText.isEmpty() && diastolicText.isEmpty() && pulseText.isEmpty()))
                {
                    Toast.makeText(v.getContext(), "Presion registrada exitosamente, WIP",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(v.getContext(), "Algo salio mal",Toast.LENGTH_SHORT).show();
                }
            }
        });

        ((HomeActivity) getActivity()).hideFloatingActionButton();
        return root;
    }
}