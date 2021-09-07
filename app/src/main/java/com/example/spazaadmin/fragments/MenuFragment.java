package com.example.spazaadmin.fragments;

import android.content.Context;
import android.content.DialogInterface;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class MenuFragment extends Fragment implements MenuAdapter.OnItemClickListener {

    private RecyclerView rv;
    private ArrayList<MenuModel> list = new ArrayList<>();
    private MenuAdapter adapter;
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
        rv = view.findViewById(R.id.menu_rv);
        btnAddMenu = view.findViewById(R.id.BtnAddMenu);
    }

    private void AddMenu(View view) {
        btnAddMenu.setOnClickListener(v -> {
            AddMenuDialogFrag menuDialogFrag = new AddMenuDialogFrag();
            menuDialogFrag.show(getFragmentManager(), "Add menu");
        });
    }

    private void LoadData(View view) {
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MenuAdapter(list,getContext(),this);
        rv.setAdapter(adapter);

        String uid = Objects.requireNonNull(FirebaseAuth.getInstance()
                .getCurrentUser())
                .getUid();
        try
        {
            for (int i = 0; i < 10 ;i++) {
                MenuModel model = new MenuModel();
                model.setKey("random");
                model.setName("Kota");
                model.setPrice("R15.00");
                model.setStatus("Available");
                //model.setUrl(R.mipmap.logo_food);
                list.add(model);
            }

            adapter = new MenuAdapter(list, getActivity(),this);
            rv.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void LoadDatabaseData(View view){
        RecyclerView recyclerView = view.findViewById(R.id.menu_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final MenuAdapter adapter = new MenuAdapter( list,getActivity(),this);
        recyclerView.setAdapter(adapter);

        FirebaseFirestore
                .getInstance()
                .collection("Menu/"+FirebaseAuth.getInstance().getUid()+"/Items")
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

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
        builder.setTitle(R.string.alert_title);
        builder.setMessage(R.string.alert_message);
        builder.setPositiveButton(R.string.alert_positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseFirestore
                        .getInstance()
                        .collection("Menu/"+ FirebaseAuth.getInstance().getUid()+"/Items")
                        .document(list.get(pos).getKey())
                        .delete();
                Toast.makeText(getContext(),"Menu deleted!" , Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton(R.string.alert_negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }
}