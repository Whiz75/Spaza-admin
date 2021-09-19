package com.example.spazaadmin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.spazaadmin.R;
import com.example.spazaadmin.models.Customer;
import com.example.spazaadmin.models.OrderModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{

    private List<OrderModel> mList = new ArrayList<>();
    private final OnItemClickListener itemClickListener;

    public OrderAdapter(List<OrderModel> mList, OnItemClickListener itemClickListener) {
        this.mList = mList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewGroup view = (ViewGroup) LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.single_order_row,parent,false);
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
                        Customer customer = value.toObject(Customer.class);
                        holder.txt_customer_id.setText(Objects.requireNonNull(customer).getNames());

                        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
                        int color1 = generator.getRandomColor();

                        TextDrawable.IBuilder builder = TextDrawable.builder()
                                .beginConfig()
                                .withBorder(4)
                                .endConfig()
                                .round();
                        // reuse the builder specs to create multiple drawables
                        TextDrawable ic2 = builder.build(customer.getNames().substring(0,1).toUpperCase(), color1);
                        holder.image.setImageDrawable(ic2);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        MaterialTextView txt_customer_id;
        MaterialButton btn_prepare;
        private final ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_customer_id = itemView.findViewById(R.id.customer_id);
            btn_prepare = itemView.findViewById(R.id.prepare_btn);
            image = itemView.findViewById(R.id.user_iamge);

            btn_prepare.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == btn_prepare.getId()){
                int pos = getAdapterPosition();
                itemClickListener.onItemClick(pos);
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }
}
