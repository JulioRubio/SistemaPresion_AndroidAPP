package com.example.seguimientopresion.ui.datos_paciente;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.seguimientopresion.HomeActivity;
import com.example.seguimientopresion.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;


public class DatosPacienteFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private DatosPacienteViewModel datosPacienteViewModel;
    EditText eT_peso, eT_altura, et_date;
    Spinner sp_sexo;
    Button guardar;

    String userID;

    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore mFirestore;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

//        datosPacienteViewModel =
//                new ViewModelProvider(this).get(DatosPacienteViewModel.class);

        View root = inflater.inflate(R.layout.fragment_datos, container, false);

        eT_altura = root.findViewById(R.id.editText_Altura);
        eT_peso = root.findViewById(R.id.edit_text_peso);
        sp_sexo = root.findViewById(R.id.edit_text_sexo);
        et_date = (EditText) root.findViewById(R.id.calendarioNacimiento);
        et_date.setInputType(InputType.TYPE_NULL);
        guardar = root.findViewById(R.id.guardar_datos);
        System.out.println(guardar);
        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.setTargetFragment(DatosPacienteFragment.this, 0);
                datePicker.show(getParentFragmentManager(), "date picker");
            }
        });

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        userID = mFirebaseAuth.getCurrentUser().getUid();

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String date = et_date.getText().toString();
                final String peso = eT_peso.getText().toString();
                final String altura = eT_altura.getText().toString();
                final String sexo = sp_sexo.getSelectedItem().toString();

                if(!date.isEmpty() && !peso.isEmpty() && !altura.isEmpty()){
                    final DocumentReference documentReference = mFirestore.collection("users").document(userID);
                    documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                documentReference.update("birthdate",date);
                                documentReference.update("weight",peso);
                                documentReference.update("height",altura);
                                documentReference.update("sex",sexo);
                                et_date.setText("");
                                eT_altura.setText("");
                                eT_peso.setText("");
                            }
                        }
                    });
                }
                if(date.isEmpty()){
                    et_date.setError("Campo Vacío");
                }
                if(peso.isEmpty()){
                    eT_peso.setError("Campo Vacío");
                }
                if(altura.isEmpty()){
                    eT_altura.setError("Campo Vacío");
                }
            }
        });
        ((HomeActivity) getActivity()).hideFloatingActionButton();
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


}