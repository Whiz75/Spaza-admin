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

import com.example.spazaadmin.R;
import com.example.spazaadmin.adapters.OrderAdapter;
import com.example.spazaadmin.dialogs.OrderDialogFragment;
import com.example.spazaadmin.models.OrderModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderFragmentTab extends Fragment implements OrderAdapter.OnItemClickListener {

    private Context context;
    private List<OrderModel> list = new ArrayList<>();

    public OrderFragmentTab() {
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
        View view = inflater.inflate(R.layout.tab_layout_orders, container, false);
        context = view.getContext();
        getOrders(view);

        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getOrders(View view){

        context = view.getContext();
        RecyclerView recyclerView = view.findViewById(R.id.RecyclerOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final OrderAdapter adapter = new OrderAdapter(list, this);
        recyclerView.setAdapter(adapter);

        FirebaseFirestore
                .getInstance()
                .collection("Orders")
                .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .collection("OrderItems")
                .addSnapshotListener((value, error) -> {
                    if (value != null){

                        for (DocumentChange dc: value.getDocumentChanges()){

                            switch (dc.getType()){
                                case ADDED:
                                    list.add(dc.getDocument().toObject(OrderModel.class));
                                    adapter.notifyDataSetChanged();
                                    break;
                                case MODIFIED:
                                    list.add(dc.getOldIndex(),dc.getDocument().toObject(OrderModel.class));
                                    adapter.notifyDataSetChanged();
                                    break;
                                case REMOVED:
                                    list.remove(dc.getOldIndex());
                                    adapter.notifyDataSetChanged();
                                    break;
                                default:
                            }
                        }
                    }
                });
    }

    @Override
    public void onItemClick(int pos) {
        //get order items
        OrderModel order = list.get(pos);

        OrderDialogFragment fragment = new OrderDialogFragment(order.product_id);
        fragment.show(getChildFragmentManager().beginTransaction(),"ORDER DETAILS");
    }
}