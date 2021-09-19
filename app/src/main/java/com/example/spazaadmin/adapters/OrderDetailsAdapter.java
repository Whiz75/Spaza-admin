package com.example.spazaadmin.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spazaadmin.R;
import com.example.spazaadmin.interfaces.PrepareClickListener;
import com.example.spazaadmin.models.OrderModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder>{

    private List<OrderModel> mList = new ArrayList<>();
    private final PrepareClickListener clickListener;

    public OrderDetailsAdapter(List<OrderModel> mList, PrepareClickListener clickListener) {
        this.mList = mList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public OrderDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewGroup view = (ViewGroup) LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.order_details_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailsAdapter.ViewHolder holder, int position) {

        OrderModel order = mList.get(position);

        holder.txt_order_id.setText(order.getOrder_id());
        holder.txt_quantity.setText(order.getQuantity());
        holder.txt_customer_id.setText(order.getCustomer_Id());

        for (int i = 0; i < order.getExtras().size();i++){
            @SuppressLint("InflateParams")
            Chip chip = (Chip) LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.chip_item,null);

            chip.setText(order.getExtras().get(i));
            holder.chipGroup.addView(chip);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final MaterialTextView txt_order_id;
        private final MaterialTextView txt_quantity;
        private final MaterialTextView txt_customer_id;
        private final ChipGroup chipGroup;
        private final MaterialButton btn_prepare;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_order_id = itemView.findViewById(R.id.row_order_id);
            txt_quantity = itemView.findViewById(R.id.row_quantity);
            txt_customer_id = itemView.findViewById(R.id.row_customer_id);
            chipGroup = itemView.findViewById(R.id.AddOnsChips);
            btn_prepare = itemView.findViewById(R.id.row_prepare_btn);

            btn_prepare.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();

            if (v.getId() == btn_prepare.getId()){
                clickListener.onPrepareClick(pos);
            }
        }
    }
}
