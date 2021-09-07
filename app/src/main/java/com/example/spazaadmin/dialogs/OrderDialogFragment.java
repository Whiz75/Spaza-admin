package com.example.spazaadmin.dialogs;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spazaadmin.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textview.MaterialTextView;

import java.util.Objects;

public class OrderDialogFragment extends DialogFragment {

    public OrderDialogFragment() {
        // Required empty public constructor
    }

    String product_id;

    public OrderDialogFragment(String product_id) {
        this.product_id = product_id;
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
                .setLayout(ViewGroup.LayoutParams.MATCH_PARENT,1000);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getContext()).inflate(R.layout.order_dialog_fragment, container, false);
        init(view);
        getOrderItems(view);

        return view;
    }

    private void init(View view){
        MaterialToolbar tool_bar = view.findViewById(R.id.toolbar);
        tool_bar.setNavigationOnClickListener(v -> dismiss());
    }

    private void getOrderItems(View view){
        MaterialTextView product_tv = view.findViewById(R.id.project_id_tv);
        product_tv.setText(product_id);
    }
}