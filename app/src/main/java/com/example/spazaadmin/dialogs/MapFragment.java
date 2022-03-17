package com.example.spazaadmin.dialogs;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spazaadmin.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.Objects;

public class MapFragment extends DialogFragment {

    private String address;

    public MapFragment() {
        // Required empty public constructor
    }

    public MapFragment(String address) {
        this.address = address;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        Objects.requireNonNull(getDialog())
                .getWindow()
                .setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_map, container, false);
        //methods
        init(view);

        return view;
    }

    private void init(ViewGroup view){
        MaterialTextView tvAddress = view.findViewById(R.id.tvAddress);
        MaterialButton dismiss_btn = view.findViewById(R.id.dismiss_btn);

        tvAddress.setText(address);
        dismiss_btn.setOnClickListener(v -> dismiss());
    }
}