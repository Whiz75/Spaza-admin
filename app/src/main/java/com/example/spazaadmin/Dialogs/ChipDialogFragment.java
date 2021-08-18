package com.example.spazaadmin.Dialogs;

import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.spazaadmin.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class ChipDialogFragment extends DialogFragment {

    private ImageView close_dialog_img;
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
        //getDialog().setCancelable(false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Objects.requireNonNull(getDialog())
                .getWindow()
                .setLayout(1000, ViewGroup.LayoutParams.WRAP_CONTENT);

        getDialog()
                .getWindow()
                .setGravity(Gravity.CENTER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chip_dialog, container, false);

        init(view);
        fragControls(view);

        return view;
    }

    private void init(View view)
    {
        close_dialog_img = view.findViewById(R.id.close_add_ons);
        btn_addChip = view.findViewById(R.id.BtnAddChip);
        add_Chip_InputLayout = view.findViewById(R.id.textInputLayout1);
        chipGroup = view.findViewById(R.id.AddOnsChips);
    }

    private void fragControls(View view)
    {
        btn_addChip.setOnClickListener(v -> {

            String inputChip = add_Chip_InputLayout.getEditText().getText().toString().trim();
            if (TextUtils.isEmpty(inputChip)) {
                add_Chip_InputLayout.getEditText().setError("Please enter menu item");
            }
            else {
                /*Chip chip = new Chip(Objects.requireNonNull(getContext()));
                ChipDrawable drawable = ChipDrawable.createFromAttributes(getContext(),
                        null, 0, R.style.Widget_MaterialComponents_Chip_Entry);

                chip.setChipDrawable(drawable);
                chip.setText(inputChip);

                chip.setOnCloseIconClickListener(v1 ->
                        chipGroup.removeView(chip));
                chipGroup.addView(chip);*/
                chipText = inputChip;
                Toast.makeText(getContext(), chipText,Toast.LENGTH_LONG).show();
            }
        });

        close_dialog_img.setOnClickListener(v ->
                dismiss());
    }
}