package com.example.seguimientopresion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    //All the items needed  from the layout
    EditText email, password;
    Button bLogin, bRegister, bForgotPassword;
    FirebaseAuth mFirebaseAuth;
    private String Email, UserId;

    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editText_email_loginActivity);
        password = findViewById(R.id.editText_password_loginActivity);
        bLogin = findViewById(R.id.button_login_loginActivity);
        bRegister = findViewById(R.id.button_resgister_loginActivity);
        bForgotPassword = findViewById(R.id.button_fPassword_loginActivity);

        //Get the user from database to check if it exists and send User message pf successful login
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser != null)
                {
                    Email = mFirebaseUser.getEmail();
                    UserId = mFirebaseUser.getUid();
                    Toast.makeText(MainActivity.this, "Login successful",Toast.LENGTH_SHORT).show();
                    Intent intToHome = new Intent(MainActivity.this,HomeActivity.class);
                    intToHome.putExtra("SESSION_EMAIL", Email);
                  
                    //intToHome.putExtra("USER_ID",UserId); //Github mark this as conflict so for test remove the comment to check if it works 

                    startActivity(intToHome); // Go to home activity
                    finish();
                }
            }
        };
        //Set click listener to Button Login to retrieve the user and password from edit text
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailText = email.getText().toString();
                String passwordText = password.getText().toString();
                if(emailText.isEmpty()) //Check if the edit text of email is empty
                {
                    email.setError("Por favor ingrese correo electrónico");
                    email.requestFocus();
                }
                else if(passwordText.isEmpty()) // Check if the edit text of password is empty
                {
                    password.setError("Por favor ingrese contraseña");
                    password.requestFocus();
                }
                else if(emailText.isEmpty() && passwordText.isEmpty()) // Check if bother fields are empty
                {
                    Toast.makeText(MainActivity.this, "Campos vacios",Toast.LENGTH_SHORT).show();
                }
                else if(!(emailText.isEmpty() && passwordText.isEmpty()))
                {
                    //Retrieve the email and password form edit texts and check with database for existing user and password
                    mFirebaseAuth.signInWithEmailAndPassword(emailText,passwordText).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful())
                            {
                                Toast.makeText(MainActivity.this, "Algo salió mal, intentelo mas tarde",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Intent intToHome = new Intent(MainActivity.this,HomeActivity.class);
                                intToHome.putExtra("SESSION_EMAIL", emailText);
                                intToHome.putExtra("USER_ID",UserId);
                                startActivity(intToHome); // Go to home activity
                                finish(); // Finish MainActivity
                            }
                        }
                    });
                }
                else
                {
                       Toast.makeText(MainActivity.this, "Algo salio mal",Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Click listener for button register
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intToRegister = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intToRegister); // Start activity
                finish();
            }
        });

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
