package com.example.seguimientopresion;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.seguimientopresion.ui.home.RegistroPresionFragment;
import com.example.seguimientopresion.ui.vinculacion.VinculacionFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity implements VinculacionFragment.OnFragmentInteractionListener{

    private AppBarConfiguration mAppBarConfiguration;
    String Email, UserId;
    private FirebaseFirestore database;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_historial, R.id.nav_vinculacion, R.id.nav_datos, R.id.nav_settings)
                .setDrawerLayout(drawer)
                .build();
        
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        Email = getIntent().getStringExtra("SESSION_EMAIL");
        UserId = getIntent().getStringExtra("USER_ID");
        TextView email = navigationView.getHeaderView(0).findViewById(R.id.userEmail);
        email.setText(Email);
        fab = findViewById(R.id.fab);
        //click listener para introducir nueva toma de presion
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegistroPresionFragment registroPresionFragment = new RegistroPresionFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.fragment_container, registroPresionFragment);
                fragmentTransaction.commit();
            }
        });
    }

    public void showFloatingActionButton() {
        fab.show();
    };

    public void hideFloatingActionButton() {
        fab.hide();
    };

    //Search for doctor in collection doctor, IMPORTANT TO CHANGE TO COLLECTION DOCTOR!!!
    private void searchUser(final String doctorId)
    {
        database.collection("pruebaVinculacion")//Change for doctor collection
            .document(doctorId)
            .get()
            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.exists()) {
                            addDoctorToPatient(doctorId);
                        } else {
                            Toast.makeText(HomeActivity.this, "Doctor no encontrado", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(HomeActivity.this, "Algo salio mal intentelo mas tarde", Toast.LENGTH_LONG).show();
                    }
                }
            });
    }

    //Add doctor to patient in User document
    private void addDoctorToPatient(final String doctorId)
    {
        CollectionReference users = database.collection("users");
        users.document(UserId).update("doctor", doctorId).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(HomeActivity.this, "Doctor vinculado", Toast.LENGTH_LONG).show();
                    addPatientToDoctor(doctorId);
                } else {
                    Toast.makeText(HomeActivity.this, "Error no se puede vincular, intentarlo mas tarde", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //Add Doctor to array of patients, in Doctor document
    private void addPatientToDoctor(final String doctorId)
    {
        CollectionReference users = database.collection("pruebaVinculacion"); //CHANGE THIS FOR DOCTOR DOCUMENT, THIS IS JUST FOR TESTS
        users.document(doctorId).update("patients", FieldValue.arrayUnion(UserId)).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(HomeActivity.this, "Usuario agregado a doctor", Toast.LENGTH_LONG).show(); // Just for tests
                } else {
                    Toast.makeText(HomeActivity.this, "Error usuario no vinculado a doctor", Toast.LENGTH_LONG).show(); //Just for tests
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    //Logout
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        if(menuItem.getItemId() == R.id.action_settings)
        {
            Toast.makeText(HomeActivity.this, "Logout", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            Intent intToLogin = new Intent(HomeActivity.this,MainActivity.class);
            intToLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intToLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intToLogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intToLogin); // Start activity
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //Add method from VinculacionFragment
    @Override
    public void onButtonChoiceVincularDoctorFragment(int choice, String doctorId)
    {
        if(choice == 0)
        {
            searchUser(doctorId);
            //Toast.makeText(HomeActivity.this,"Vincular: " + doctorId + " with: " + UserId,Toast.LENGTH_SHORT).show();
        }
    }

}
