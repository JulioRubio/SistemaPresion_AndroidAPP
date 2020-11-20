package com.example.seguimientopresion.ui.vinculacion;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.seguimientopresion.HomeActivity;
import com.example.seguimientopresion.R;

public class VinculacionFragment extends Fragment {

    OnFragmentInteractionListener mListener;

    private static final String CHOICE = "choice";
    private static final int VINCULAR = 0;
    private static final int NONE = 1;
    private String id = "";

    private EditText txtIdDoctor;

    private Context mContext;

    private VinculacionViewModel vinculacionViewModel;

    public VinculacionFragment() {
        // Required empty public constructor
    }

    public static VinculacionFragment newInstance(int choice){

        VinculacionFragment fragment = new VinculacionFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(CHOICE,choice);
        fragment.setArguments(arguments);

        return fragment;
    }

    //Create interaction listener for fragment to activity
    public interface OnFragmentInteractionListener{
            void onButtonChoiceVincularDoctorFragment(int choice, String idDoctor);
    }

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

        final Button bVincular = root.findViewById(R.id.button_vincular_FragmentVincular);
        txtIdDoctor = root.findViewById(R.id.editText_idDoctor_FragmentVinculacion);

        //add click listener to button
        bVincular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = txtIdDoctor.getText().toString();
                if(!id.isEmpty()) {
                    mListener.onButtonChoiceVincularDoctorFragment(VINCULAR,id);
                }
            }
        });

        return root;
    }

    public void onAttach(Context context){

        super.onAttach(context);
        mContext = context;
        if(context instanceof  OnFragmentInteractionListener){
            mListener = (OnFragmentInteractionListener) context;
        }
        else {
            throw new ClassCastException(context.toString() + "must implement OnFragmentInteractionListener");
        }
    }
}