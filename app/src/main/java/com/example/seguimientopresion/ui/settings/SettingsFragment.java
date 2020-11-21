package com.example.seguimientopresion.ui.settings;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.seguimientopresion.HomeActivity;
import com.example.seguimientopresion.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

public class SettingsFragment extends Fragment {
    EditText et_email, popUpPassword;
    TextView t_email, t_password;
    Button bt_newEmail, bt_newPassword, btnPopUpActualizar, btnPopUpCancel;
    String userID;

    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore mFirestore;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_settings, container, false);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        userID = mFirebaseAuth.getCurrentUser().getUid();

        t_email = root.findViewById(R.id.userEmail);
        t_password = root.findViewById(R.id.userPassword);

        bt_newEmail = root.findViewById(R.id.guardar_email);
        bt_newPassword = root.findViewById(R.id.btn_newPassword);

        bt_newEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder = new AlertDialog.Builder(root.getContext());
                final View passwordPopUp = getLayoutInflater().inflate(R.layout.popup,null);

                popUpPassword = passwordPopUp.findViewById(R.id.et_validatePassword);
                et_email = passwordPopUp.findViewById(R.id.edit_text_email);
                btnPopUpActualizar = passwordPopUp.findViewById(R.id.update_email);
                btnPopUpCancel = passwordPopUp.findViewById(R.id.cancel_email);

                dialogBuilder.setView(passwordPopUp);
                dialog = dialogBuilder.create();
                dialog.show();

                btnPopUpActualizar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String userNewPassword = popUpPassword.getText().toString();
                        String userNewEmail = et_email.getText().toString();

                        if(!userNewEmail.isEmpty() && !userNewPassword.isEmpty()) {
                            DocumentReference documentReference = mFirestore.collection("users").document(userID);
                            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            final String userEmail = document.get("email").toString();
                                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                            AuthCredential credential = EmailAuthProvider.getCredential(userEmail, popUpPassword.getText().toString());
                                            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()){
                                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                        String newEmail = et_email.getText().toString();
                                                        user.updateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Toast.makeText(getContext(), "Email cambiado exitosamente", Toast.LENGTH_SHORT).show();
                                                                    dialog.dismiss();
                                                                }
                                                                else{
                                                                    Toast.makeText(getContext(), "Hubo un problema, intenta más tarde", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                                    }else{
                                                        popUpPassword.setError("Contraseña incorrecta");
                                                    }
                                                }
                                            });
                                        }
                                    } else {
                                        //No se logro completar el get, errores extenos a la aplicacion
                                        System.out.println("algo paso");
                                    }
                                }
                            });
                        }else{
                            if(userNewEmail.isEmpty())
                                et_email.setError("Campo vacío");
                            if(userNewPassword.isEmpty())
                                popUpPassword.setError("Campo vacío");
                        }
                    }
                });

                btnPopUpCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });

        bt_newPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference documentReference = mFirestore.collection("users").document(userID);
                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>(){
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String user = document.get("email").toString();
                                mFirebaseAuth.sendPasswordResetEmail(user);
                            } else {
                                //Usuario no exite, no deberia pasar nunca
                                System.out.println("ni existe");
                            }
                        } else {
                            //No se logro completar el get, errores extenos a la aplicacion
                            System.out.println("algo paso");
                        }
                    }
                });
            }
        });
        ((HomeActivity) getActivity()).hideFloatingActionButton();
        return root;
    }

}