package com.example.seguimientopresion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText firstName, lastName, phone, address, email, password;
    Button bLogin, bRegister;
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore mFirestore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstName = findViewById(R.id.editText_name_registerActivity);
        lastName  = findViewById(R.id.editText_lastName_registerActivity);
        phone     = findViewById(R.id.editText_phone_registerActivity);
        address   = findViewById(R.id.editText_add_registerActivity);
        email     = findViewById(R.id.editText_email_registerActivity); //EditText email from RegisterActivity different from loginActivity
        password  = findViewById(R.id.editText_password_registerActivity);

        bRegister = findViewById(R.id.button_register_registerActivity);
        bLogin    = findViewById(R.id.button_login_registerActivity);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailText = email.getText().toString();
                String passText  = password.getText().toString();
                final String fNameText = firstName.getText().toString();
                final String lNameText = lastName.getText().toString();
                final String phoneText = phone.getText().toString();
                final String addText   = address.getText().toString();

                if(emailText.isEmpty())
                {
                    email.setError("Por favor ingrese correo electrónico");
                    email.requestFocus();
                }
                else if(passText.isEmpty())
                {
                    password.setError("Por favor ingrese contraseña");
                    password.requestFocus();
                }
                else if(passText.length() < 6)
                {
                    password.setError("La contraseña debe tener mas de 6 caracteres");
                    password.requestFocus();
                }
                else if(emailText.isEmpty() && passText.isEmpty()) // Check if bother fields are empty
                {
                    Toast.makeText(RegisterActivity.this, "Campos vacios",Toast.LENGTH_SHORT).show();
                }
                else if(!(emailText.isEmpty() && passText.isEmpty())) // Check if bother fields are empty
                {
                    //Register the user in firebase
                    mFirebaseAuth.createUserWithEmailAndPassword(emailText, passText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful())
                            {
                                Toast.makeText(RegisterActivity.this, "Algo salió mal, intentelo mas tarde", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(RegisterActivity.this, "Cuenta creada correctamente", Toast.LENGTH_SHORT).show();

                                //Store user data
                                userID = mFirebaseAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = mFirestore.collection("users").document(userID);
                                Map<String,Object> user = new HashMap<>();
                                user.put("firstName",fNameText);
                                user.put("lastName",lNameText);
                                user.put("phone",phoneText);
                                user.put("address",addText);
                                user.put("email", emailText);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "OnSiccess: user profile is created for" + userID);
                                    }
                                });

                                Intent intToHome = new Intent(RegisterActivity.this, HomeActivity.class);
                                intToHome.putExtra("SESSION_EMAIL", emailText);
                                startActivity(intToHome); // Go to home activity
                                finish(); // Finish RegisterActivity
                            }
                        }
                    });
                }
            }
        });

        //Go to login activity
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intToLogin = new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intToLogin); // Start activity
                finish();
            }
        });
    }
}