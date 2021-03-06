package com.example.spazaadmin.dialogs;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.DialogFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.example.spazaadmin.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class AddMenuDialogFrag extends DialogFragment {

    private AppCompatImageView img_menu;
    private FloatingActionButton addMenuImg_fabButton;
    private TextInputEditText inputItemName, inputItemPrice;
    private MaterialButton btnOpenDlg;
    private MaterialButton btnSubmitMenu;
    private ChipGroup chipGroup;
    private final List<String> Items = new ArrayList<>();

    private Uri img_url = null;

    public AddMenuDialogFrag() {
        // Required empty public constructor
    }

    String key;

    public AddMenuDialogFrag(String key) {
        this.key = key;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        //set dialog width and heght
        Objects.requireNonNull(getDialog())
                .getWindow()
                .setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_add_menu_dialog, container, false);
        //call methods here
        init(view);
        pickImg(view);
        setBtnOpenDlg(view);
        //BtnSubmitMenu(view);

        return view;
    }

    private void init(View view)
    {

        MaterialToolbar tool_bar = view.findViewById(R.id.menu_toolbar);
        tool_bar.setNavigationIcon(R.drawable.ic_close);
        tool_bar.setNavigationOnClickListener(v ->
                dismiss());

        btnOpenDlg = view.findViewById(R.id.BtnOpenAddDlg);
        chipGroup = view.findViewById(R.id.chipAddOns);

        inputItemName = view.findViewById(R.id.InputItemName);
        inputItemPrice = view.findViewById(R.id.InputItemPrice);

        img_menu = view.findViewById(R.id.imgMenu);

        addMenuImg_fabButton = view.findViewById(R.id.FabMenuImg);
        btnSubmitMenu = view.findViewById(R.id.BtnSubmitMedu);
    }

    private void pickImg(View view)
    {
        addMenuImg_fabButton.setOnClickListener(v -> {
            //pick image from external storage
            ImagePicker.Companion.with(AddMenuDialogFrag.this)
                    .crop()
                    .compress(1024)
                    .maxResultSize(512, 512)
                    .start();
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && resultCode == RESULT_OK) {
            img_url = data.getData();
            img_menu.setImageURI(img_url);
            img_menu.setVisibility(View.VISIBLE);
        }
    }

    private void setBtnOpenDlg(View view)
    {
        btnOpenDlg.setOnClickListener(v -> {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(v.getContext());
            View view1 = LayoutInflater.from(v.getContext()).inflate(R.layout.fragment_chip_dialog, null);
            ImageView close_dialog_img = view1.findViewById(R.id.close_add_ons);
            TextInputLayout inputChip = view1.findViewById(R.id.textInputLayout1);
            MaterialButton BtnAddChip = view1.findViewById(R.id.BtnAddChip);

            builder.setView(view1);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            BtnAddChip.setOnClickListener(v12 -> {

                String input = inputChip.getEditText().getText().toString().trim();

                if (TextUtils.isEmpty(input)) {
                    inputChip.getEditText().setError("Please enter a menu item");
                }else {
                    Chip chip = new Chip(view1.getContext());
                    //create chip drawable
                    ChipDrawable drawable = ChipDrawable.createFromAttributes(v12.getContext(),
                            null, 0, R.style.Widget_MaterialComponents_Chip_Entry);

                    //set chip drawable
                    chip.setChipDrawable(drawable);
                    chip.setText(input);

                    Items.add(input);
                    //call the upload menu function
                    BtnSubmitMenu(view, btnSubmitMenu, Items);
                    //test the list
                    Toast.makeText(getContext(),Items.toString(),Toast.LENGTH_LONG).show();

                    chip.setOnCloseIconClickListener(v1 ->
                            chipGroup.removeView(chip));

                    chipGroup.addView(chip);
                    alertDialog.cancel();
                }
            });

            //cancel the dialog
            close_dialog_img.setOnClickListener(v13 ->
                    alertDialog.cancel());
        });
    }

    private void BtnSubmitMenu(View view, MaterialButton button, List<String> items) {
        button.setOnClickListener(v -> {
            String inputName = Objects.requireNonNull(inputItemName.getText()).toString().trim();
            String inputPrice = Objects.requireNonNull(inputItemPrice.getText()).toString().trim();

            if (TextUtils.isEmpty(inputName)) {
                inputItemName.setError("Please the item name");

            }else if (TextUtils.isEmpty(inputPrice)) {
                inputItemPrice.setError("Please the item's price");
            }else {

                final Map<String, Object> hashMap = new HashMap<>();
                hashMap.put("key",null);
                hashMap.put("name", inputName);
                hashMap.put("price", inputPrice);
                hashMap.put("status", "available");
                hashMap.put("extras",items);

                try
                {
                    FirebaseFirestore
                            .getInstance()
                            .collection("Menu")
                            .add(hashMap)
                            .addOnSuccessListener(documentReference -> {

                                documentReference.update("key", documentReference.getId());

                                if (img_url != null) {
                                    SweetAlertDialog pDialog = new SweetAlertDialog(view.getContext(), SweetAlertDialog.PROGRESS_TYPE);
                                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                    pDialog.setTitleText("Loading");
                                    pDialog.setCancelable(false);
                                    pDialog.show();

                                    FirebaseStorage
                                            .getInstance()
                                            .getReference()
                                            .child("Menu images")
                                            .child(documentReference.getId())
                                            .putFile(img_url)
                                            .addOnSuccessListener(taskSnapshot ->
                                                    taskSnapshot
                                                            .getStorage()
                                                            .getDownloadUrl()
                                                            .addOnSuccessListener(uri -> {

                                                                FirebaseFirestore
                                                                        .getInstance()
                                                                        .collection("Menu")
                                                                        .document(documentReference.getId())
                                                                        .update("url",uri.toString());

                                                                pDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                                                pDialog.setTitleText("Success!!");
                                                                pDialog.setContentText("Successfully added!");
                                                                pDialog.setConfirmText("Ok");

                                                                pDialog.setConfirmClickListener(sweetAlertDialog -> {
                                                                    dismiss();
                                                                    sweetAlertDialog.dismissWithAnimation();
                                                                });

                                                                Toast.makeText(v.getContext(), "Successfully added a menu", Toast.LENGTH_LONG).show();
                                                            })).addOnCompleteListener(task -> pDialog.dismissWithAnimation());
                                }else
                                {
                                    SweetAlertDialog dlg = new SweetAlertDialog(view.getContext(), SweetAlertDialog.SUCCESS_TYPE);
                                    dlg.setTitleText("Success!!");
                                    dlg.setContentText("Successfully Posted");
                                    dlg.setConfirmText("OK");
                                    dlg.setCancelable(false);
                                    dlg.setConfirmClickListener(sweetAlertDialog -> {
                                        dismiss();
                                        sweetAlertDialog.dismissWithAnimation();
                                    });
                                }
                            }).addOnFailureListener(e ->
                            Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_LONG).show());
                    dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }
}