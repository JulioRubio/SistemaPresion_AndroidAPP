package com.example.seguimientopresion.ui.vinculacion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.seguimientopresion.HomeActivity;
import com.example.seguimientopresion.R;

public class VinculacionFragment extends Fragment {

    private VinculacionViewModel vinculacionViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        vinculacionViewModel =
                new ViewModelProvider(this).get(VinculacionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_vinculacion, container, false);
        final TextView textView = root.findViewById(R.id.text_vinculacion);
        vinculacionViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        ((HomeActivity) getActivity()).hideFloatingActionButton();
        return root;
    }
}