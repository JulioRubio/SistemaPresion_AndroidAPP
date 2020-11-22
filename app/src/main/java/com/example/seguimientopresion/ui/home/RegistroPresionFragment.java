package com.example.seguimientopresion.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.seguimientopresion.MainActivity;
import com.example.seguimientopresion.R;
import com.example.seguimientopresion.HomeActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.text.SimpleDateFormat;

public class RegistroPresionFragment extends Fragment {

    EditText et_sistolica, et_diastolica, et_pulso;
    Button bt_guardar;
    String userID;

    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore mFirestore;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_registro_presion, container, false);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        userID = mFirebaseAuth.getCurrentUser().getUid();

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
                    CollectionReference collection = mFirestore.collection("users").document(userID).collection("BloodPressure");
                    Map<String,Object> registry = new HashMap<>();
                    registry.put("sistolic",sistolicText);
                    registry.put("diastolic",diastolicText);
                    registry.put("pulse",pulseText);
                    Long millis = System.currentTimeMillis();
                    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
                    Date reg_date = new Date(millis);
                    registry.put("date_time", sdf.format(reg_date));

                    collection.document().set(registry);

                    Toast.makeText(v.getContext(), "Presion registrada exitosamente",Toast.LENGTH_SHORT).show();
                    onDestroy();

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
    @Override
    public void onDestroy() {
        super.onDestroy();
        ((HomeActivity) getActivity()).showFloatingActionButton();
        ((HomeActivity) getActivity()).setTitleCardVisibility();
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }
}