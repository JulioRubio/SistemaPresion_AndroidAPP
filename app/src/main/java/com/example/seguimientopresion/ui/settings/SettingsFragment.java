package com.example.seguimientopresion.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.seguimientopresion.HomeActivity;
import com.example.seguimientopresion.R;

public class SettingsFragment extends Fragment {

    private SettingsViewModel mViewModel;
    EditText et_email, et_password;
    TextView t_email, t_password;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        t_email = root.findViewById(R.id.userEmail);
        t_password = root.findViewById(R.id.userPassword);

        et_email = root.findViewById(R.id.edit_text_email);
        et_password = root.findViewById(R.id.edit_text_password);
        ((HomeActivity) getActivity()).hideFloatingActionButton();
        return root;
    }

}