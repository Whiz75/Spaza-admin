package com.example.spazaadmin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spazaadmin.models.MenuModel;
import com.example.spazaadmin.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder>
{
    final ArrayList<MenuModel> items;
    Context context;

    private OnItemClickListener listener;

    public MenuAdapter(ArrayList<MenuModel> items,Context context, OnItemClickListener listener) {
        this.items = items;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.ViewHolder holder, int position) {

        MenuModel model = items.get(position);

        holder.txt_name.setText(model.getName());
        holder.txt_price.setText(model.getPrice());
        holder.txt_status.setText(model.getStatus());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        AppCompatTextView txt_name, txt_price, txt_status;
        MaterialButton btn_update, btn_delete;
        ChipGroup chipGroup;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_name = itemView.findViewById(R.id.row_name);
            txt_price = itemView.findViewById(R.id.row_price);
            txt_status = itemView.findViewById(R.id.row_status);

            chipGroup = itemView.findViewById(R.id.AddOnsChips);

            btn_update = itemView.findViewById(R.id.row_btn_update);
            btn_delete = itemView.findViewById(R.id.row_btn_delete);

            //on buttons click
            btn_update.setOnClickListener(this);
            btn_delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == btn_update.getId())
            {
                int pos = getAdapterPosition();
                listener.onUpDateClick(pos);
            }

            if (v.getId() == btn_delete.getId())
            {
                int pos = getAdapterPosition();
                listener.onDeleteClick(pos);
            }
        }
    }

    public interface OnItemClickListener {
        void onUpDateClick(int pos);
        void onDeleteClick(int pos);
    }
}
