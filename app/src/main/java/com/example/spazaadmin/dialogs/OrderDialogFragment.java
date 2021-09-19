package com.example.spazaadmin.dialogs;
import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.spazaadmin.R;
import com.example.spazaadmin.adapters.OrderDetailsAdapter;
import com.example.spazaadmin.interfaces.PrepareClickListener;
import com.example.spazaadmin.models.OrderModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class OrderDialogFragment extends DialogFragment implements PrepareClickListener {

    private final List<OrderModel> mList = new ArrayList<>();

    public OrderDialogFragment() {
        // Required empty public constructor
    }

    String product_id;
    List<String> extras;

    public OrderDialogFragment(String product_id, List<String> extras) {
        this.product_id = product_id;
        this.extras = extras;
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
        tool_bar.setTitle(R.string.orders_toolbar_title);
        tool_bar.setNavigationOnClickListener(v -> dismiss());
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getOrderItems(View view){

        RecyclerView recyclerView = view.findViewById(R.id.order_details_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final OrderDetailsAdapter adapter = new OrderDetailsAdapter(mList, this);
        recyclerView.setAdapter(adapter);

        FirebaseFirestore
                .getInstance()
                .collection("Orders")
                .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .collection("OrderItems")
                .addSnapshotListener((value, error) -> {
                    if (value != null){
                        for (DocumentChange dc: value.getDocumentChanges()){
                            switch (dc.getType()) {
                                case ADDED:
                                    //list.add(dc);
                                    mList.add(dc.getDocument().toObject(OrderModel.class));
                                    adapter.notifyDataSetChanged();
                                    break;
                                case MODIFIED:
                                    mList.add(dc.getOldIndex(),dc.getDocument().toObject(OrderModel.class));
                                    adapter.notifyDataSetChanged();
                                    break;
                                case REMOVED:
                                    mList.remove(dc.getOldIndex());
                                    adapter.notifyDataSetChanged();
                                    break;
                            }
                        }
                    }
                });
    }

    @Override
    public void onPrepareClick(int pos) {

        OrderModel order = mList.get(pos);
        Toast.makeText(getContext(),"Order ID:" +order.getOrder_id(), Toast.LENGTH_SHORT).show();

        Map<String, String> prepare = new HashMap<>();
        prepare.put("customer_id",order.getCustomer_Id());
        prepare.put("order_id",order.getOrder_id());
        //prepare.put("extras",order.getExtras());


        FirebaseFirestore
                .getInstance()
                .collection("Preparing")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("OrderItems")
                .document()
                .set(prepare)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), "Preparing order No :"+order.getOrder_id(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}