package com.example.spazaadmin.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.spazaadmin.adapters.MenuAdapter;
import com.example.spazaadmin.dialogs.AddMenuDialogFrag;
import com.example.spazaadmin.models.MenuModel;
import com.example.spazaadmin.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

public class MenuFragment extends Fragment implements MenuAdapter.OnItemClickListener {

    private ArrayList<MenuModel> list = new ArrayList<>();
    private MaterialButton btnAddMenu;
    MenuModel m;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        //initialize fragment context
        Context context = view.getContext();
        //call methods here
        init(view);
        AddMenu(view);
        //LoadData(view);
        LoadDatabaseData(view);

        return view;
    }

    private void init(View view) {
        btnAddMenu = view.findViewById(R.id.BtnAddMenu);
    }

    private void AddMenu(View view) {
        btnAddMenu.setOnClickListener(v -> {
            AddMenuDialogFrag menuDialogFrag = new AddMenuDialogFrag();
            menuDialogFrag.show(getChildFragmentManager().beginTransaction(), "Add menu");
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void LoadDatabaseData(View view){
        RecyclerView recyclerView = view.findViewById(R.id.menu_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final MenuAdapter adapter = new MenuAdapter( list,getActivity(),this);
        recyclerView.setAdapter(adapter);

        FirebaseFirestore
                .getInstance()
                .collection("Menu")
                .addSnapshotListener((value, error) -> {
                    if (value != null){
                        for (DocumentChange dc : value.getDocumentChanges()){

                            switch (dc.getType()){
                                case ADDED:
                                    list.add(dc.getDocument().toObject(MenuModel.class));
                                    adapter.notifyDataSetChanged();
                                    break;
                                case MODIFIED:
                                    list.set(dc.getOldIndex(), dc.getDocument().toObject(MenuModel.class));
                                    adapter.notifyDataSetChanged();
                                    break;
                                case REMOVED:
                                    list.remove(dc.getOldIndex());
                                    adapter.notifyDataSetChanged();
                                    break;
                            }
                        }
                    }
                });
    }

    @Override
    public void onUpDateClick(int pos) {

        Toast.makeText(getContext(),"Successfully updated!" , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClick(int pos) {

        if (getContext() != null) {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
            builder.setTitle(R.string.alert_title);
            builder.setMessage(R.string.alert_message);
            builder.setPositiveButton(R.string.alert_positive, (dialog, which) -> {
                FirebaseFirestore
                        .getInstance()
                        .collection("Menu/"+ FirebaseAuth.getInstance().getUid()+"/Items")
                        .document(list.get(pos).getKey())
                        .delete();
                Toast.makeText(getContext(),"Menu deleted!" , Toast.LENGTH_SHORT).show();
            }).setNegativeButton(R.string.alert_negative, (dialog, which) -> dialog.dismiss());
            builder.show();
        }
    }
}