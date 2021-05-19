package com.example.spazaadmin.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.spazaadmin.Adapters.MenuAdapter;
import com.example.spazaadmin.Dialogs.AddMenuDialogFrag;
import com.example.spazaadmin.Dialogs.ChipDialogFragment;
import com.example.spazaadmin.Models.MenuModel;
import com.example.spazaadmin.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;


public class MenuFragment extends Fragment implements MenuAdapter.OnItemClickListener {

    private RecyclerView rv;
    private List<MenuModel> list;
    private MenuAdapter adapter;
    private MaterialButton btnAddMenu;

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
        LoadData(view);

        return view;
    }

    private void init(View view)
    {
        rv = view.findViewById(R.id.menu_rv);
        btnAddMenu = view.findViewById(R.id.BtnAddMenu);
    }

    private void AddMenu(View view)
    {
        btnAddMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(),"here it gets in", Toast.LENGTH_LONG).show();

                /*ChipDialogFragment chipDialogFragment = new ChipDialogFragment();
                chipDialogFragment.show(getFragmentManager(), "Add");*/

                AddMenuDialogFrag menuDialogFrag = new AddMenuDialogFrag();
                menuDialogFrag.show(getFragmentManager(), "Add menu");
            }
        });
    }

    private void LoadData(View view)
    {
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();

        try
        {
            for (int i = 0; i < 10 ;i++)
            {
                MenuModel model = new MenuModel();
                model.setKey("random");
                model.setName("Kota");
                model.setPrice("R15.00");
                model.setStatus("Available");
                model.setImgUrl(R.mipmap.logo_food);
                list.add(model);
            }

            adapter = new MenuAdapter(list, getActivity(),this);
            rv.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpDateClick(int pos) {

        Toast.makeText(getActivity(), "attempt to update on row:"+pos, Toast.LENGTH_LONG).show();

        /*ChipDialogFragment dialogFragment = new ChipDialogFragment();
        dialogFragment.show(getFragmentManager(), "Add chips");*/
    }

    @Override
    public void onDeleteClick(int pos) {
        Toast.makeText(getActivity(), "attempt to delete row:"+pos, Toast.LENGTH_LONG).show();
    }
}