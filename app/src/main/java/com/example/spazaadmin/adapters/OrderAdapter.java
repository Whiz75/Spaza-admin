package com.example.spazaadmin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spazaadmin.R;
import com.example.spazaadmin.models.Customer;
import com.example.spazaadmin.models.OrderModel;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{

    private List<OrderModel> mList = new ArrayList<>();
    private OnItemClickListener itemClickListener;

    public OrderAdapter(List<OrderModel> mList, OnItemClickListener itemClickListener) {
        this.mList = mList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.single_order_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {

        OrderModel order = mList.get(position);

        FirebaseFirestore
                .getInstance()
                .collection("Customers")
                .document(order.customer_Id)
                .addSnapshotListener((value, error) -> {
                    if (value != null){
                       // if (FirebaseAuth.getInstance().getUid() == order.customer_Id){
                        Customer customer = value.toObject(Customer.class);
                        holder.txt_customer_id.setText(customer.getNames());
                       // }
                    }
                });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView txt_customer_id;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_customer_id = itemView.findViewById(R.id.customer_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = getAdapterPosition();

                    itemClickListener.onItemClick(pos);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }
}
