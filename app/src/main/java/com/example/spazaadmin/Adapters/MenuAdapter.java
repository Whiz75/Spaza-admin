package com.example.spazaadmin.Adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spazaadmin.Models.MenuModel;
import com.example.spazaadmin.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder>
{
    final List<MenuModel> items;
    Context context;

    private OnItemClickListener listener;

    public MenuAdapter(List<MenuModel> items, Context context, OnItemClickListener listener) {
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
                /*AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                View view = LayoutInflater.from(v.getContext()).inflate(R.layout.fragment_chip_dialog, null);
                TextInputLayout inputChip = view.findViewById(R.id.textInputLayout1);
                MaterialButton BtnAddChip = view.findViewById(R.id.BtnAddChip);

                builder.setView(view);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                BtnAddChip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String input = inputChip.getEditText().getText().toString().trim();

                        if (TextUtils.isEmpty(input))
                        {
                            inputChip.getEditText().setError("Please enter a menu item");
                        }else
                        {
                            Chip chip = new Chip(v.getContext());
                            //create chip drawable
                            ChipDrawable drawable = ChipDrawable.createFromAttributes(v.getContext(),
                                    null, 0, R.style.Widget_MaterialComponents_Chip_Entry);

                            //set chip drawable
                            chip.setChipDrawable(drawable);
                            chip.setText(input);
                            chip.setOnCloseIconClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v)
                                {
                                    chipGroup.removeView(chip);
                                }
                            });
                            chipGroup.addView(chip);
                            alertDialog.cancel();
                        }
                    }
                });*/

                listener.onUpDateClick(pos);
            }

            if (v.getId() == btn_delete.getId())
            {
                int pos = getAdapterPosition();
                listener.onDeleteClick(pos);
            }
        }
    }

    public interface OnItemClickListener
    {
        void onUpDateClick(int pos);
        void onDeleteClick(int pos);
    }
}
