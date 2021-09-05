package com.example.spazaadmin.dialogs;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spazaadmin.R;
import com.google.android.material.textview.MaterialTextView;


public class LoadingFragmentDialog extends DialogFragment {

    String caption;
    public LoadingFragmentDialog() {
        // Required empty public constructor
    }

    public LoadingFragmentDialog(String caption) {
        this.caption = caption;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();

        //set dialog width and height
        getDialog()
                .getWindow()
                .setLayout(1000, ViewGroup.LayoutParams.WRAP_CONTENT);

        //set dialog background
        getDialog()
                .getWindow()
                .setGravity(Gravity.BOTTOM);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.loading_dialog, container, false);
        init(view);

        return view;
    }

    private void init(View view)
    {
        MaterialTextView progresscaption = (MaterialTextView)view.findViewById(R.id.LoadingCaption);
        progresscaption.setText(caption);
    }
}