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

public class RegistroPresionFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private HomeViewModel homeViewModel;
    EditText et_sistolica, et_diastolica, et_date, et_time;
    Button bt_guardar;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_registro_presion, container, false);

        et_sistolica = root.findViewById(R.id.edit_text_sistolica);
        et_diastolica = root.findViewById(R.id.edit_text_diastolica);
        et_date = (EditText) root.findViewById(R.id.edit_text_datePressure);
        et_date.setInputType(InputType.TYPE_NULL);
        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.setTargetFragment(RegistroPresionFragment.this, 0);
                datePicker.show(getParentFragmentManager(), "date picker");
            }
        });
        et_time = (EditText) root.findViewById(R.id.edit_text_timePressure);
        et_time.setInputType(InputType.TYPE_NULL);
        et_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.setTargetFragment(RegistroPresionFragment.this, 0);
                timePicker.show(getParentFragmentManager(), "date picker");
            }
        });

        bt_guardar = root.findViewById(R.id.guardar_datos);

        bt_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sistolicText = et_sistolica.getText().toString();
                String diastolicText = et_diastolica.getText().toString();
                String dateText = et_date.getText().toString();
                String timeText = et_time.getText().toString();
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
                if(dateText.isEmpty())
                {
                    et_date.setError("Por favor ingrese fecha de registro");
                    et_date.requestFocus();
                }
                if(timeText.isEmpty())
                {
                    et_time.setError("Por favor ingrese hora del registro");
                    et_time.requestFocus();
                }
                if(!(sistolicText.isEmpty() && diastolicText.isEmpty() && dateText.isEmpty() && timeText.isEmpty()))
                {
                    Toast.makeText(v.getContext(), "Presion registrada exitosamente, WIP",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(v.getContext(), "Algo salio mal",Toast.LENGTH_SHORT).show();
                }
            }
        });

        ((HomeActivity) getActivity()).showFloatingActionButton();
        return root;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDate = DateFormat.getDateInstance().format(c.getTime());

        et_date.setText(currentDate);
    }

    @Override
    public void onTimeSet(TimePicker view, int hour, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        String currentTime = hour + ":" + minute;

        et_time.setText(currentTime);
    }
}