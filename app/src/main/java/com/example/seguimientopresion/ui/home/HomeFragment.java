package com.example.seguimientopresion.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seguimientopresion.HomeActivity;
import com.example.seguimientopresion.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class HomeFragment extends Fragment  {
    private String userID;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseFirestore mFirestore;
    private Adapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.activity_recycler, container, false);
        mFirestore = FirebaseFirestore.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        setUpRecyclerView(root);


        ((HomeActivity) getActivity()).showFloatingActionButton();
        return root;
    }


    private void setUpRecyclerView(View root){
        userID = mFirebaseAuth.getCurrentUser().getUid();
        Query query = mFirestore.collection("users");
        System.out.println(query);

        FirestoreRecyclerOptions<Pacientes> options = new FirestoreRecyclerOptions.Builder<Pacientes>()
                .setQuery(query, Pacientes.class)
                .build();

        adapter = new Adapter(options);
        RecyclerView rv = root.findViewById(R.id.recyclerview);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(root.getContext()));
        rv.setAdapter(adapter);
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}

