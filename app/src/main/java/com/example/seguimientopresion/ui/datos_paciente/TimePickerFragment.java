package com.example.seguimientopresion.ui.datos_paciente;

import android.app.TimePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class TimePickerFragment extends DialogFragment{

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        boolean is24HourView = false;

        return new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener) getTargetFragment(), hour, minute, is24HourView);
    }
}
