package com.example.spazaadmin.Dialogs;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.spazaadmin.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;

public class ChipDialogFragment extends DialogFragment {

    private MaterialButton btn_addChip;
    private TextInputLayout add_Chip_InputLayout;
    private ChipGroup chipGroup;

    public ChipDialogFragment() {
        // Required empty public constructor
    }

    String chipText;

    public ChipDialogFragment(String chipText)
    {
        this.chipText = chipText;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

        //set dialog width and heght
        getDialog()
                .getWindow()
                .setLayout(1000, ViewGroup.LayoutParams.WRAP_CONTENT);

        //set dialog gravity
        getDialog()
                .getWindow()
                .setGravity(Gravity.CENTER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chip_dialog, container, false);

        //fragment context
        Context context = view.getContext();
        //call method
        init(view);
        fragControls(view);

        return view;
    }

    private void init(View view)
    {
        btn_addChip = view.findViewById(R.id.BtnAddChip);
        add_Chip_InputLayout = view.findViewById(R.id.textInputLayout1);
        chipGroup = view.findViewById(R.id.AddOnsChips);
    }

    private void fragControls(View view)
    {
        btn_addChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inputChip = add_Chip_InputLayout.getEditText().getText().toString().trim();

                if (TextUtils.isEmpty(inputChip))
                {
                    add_Chip_InputLayout.getEditText().setError("Please enter menu item");
                }
                else
                {
                    Toast.makeText(getActivity(),"You enterd "+ inputChip+" to chip group", Toast.LENGTH_SHORT).show();
                    //close dialog
                    dismiss();
                }
            }
        });
    }
}