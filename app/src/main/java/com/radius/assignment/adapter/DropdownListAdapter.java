package com.radius.assignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.radius.assignment.R;
import com.radius.assignment.model.FacilityOptions;

import java.util.List;

public class DropdownListAdapter extends RecyclerView.Adapter<DropdownListAdapter.DropdownViewHolder> {

    private Context mContext;
    private View root;
    private List<FacilityOptions> list;
    private int SELECTED_ITEM = -1;
    private OnOptionSelectListener listener;
    private List<String> exclude_id;

    public DropdownListAdapter(Context mContext, List<FacilityOptions> list, int LAST_SELECTED_OPTION,
                               List<String> exclude_id, OnOptionSelectListener listener) {
        this.mContext = mContext;
        this.list = list;
        this.listener = listener;
        this.exclude_id = exclude_id;
        this.SELECTED_ITEM = LAST_SELECTED_OPTION;
    }

    @NonNull
    @Override
    public DropdownViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        root = LayoutInflater.from(mContext).inflate(R.layout.dropdown_item, parent, false);
        return new DropdownViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull DropdownViewHolder holder, int position) {
        if (list != null && list.size() > 0) {
            if (position == list.size() - 1) {
                holder.view.setVisibility(View.GONE);
            }
            FacilityOptions model = list.get(position);

            if (position == SELECTED_ITEM) {
                holder.txt_item.setText(list.get(position).getName());
                holder.radio.setChecked(true);
            } else {
                holder.radio.setChecked(false);
                boolean isFound = false;
                if (exclude_id != null && exclude_id.size() > 0) {
                    for (String id : exclude_id) {
                        if (model.getId().equals(id)) {
                            isFound = true;
                            break;
                        }
                    }
                }
                holder.txt_item.setText(list.get(position).getName());
                if (isFound) {
                    holder.txt_item.setAlpha(0.3f);
                    holder.radio.setAlpha(0.3f);
                    holder.card_icon.setAlpha(0.3f);
                    holder.itemView.setEnabled(false);
                } else {
                    holder.txt_item.setAlpha(1f);
                    holder.radio.setAlpha(1f);
                    holder.card_icon.setAlpha(1f);
                    holder.itemView.setEnabled(true);
                }
            }

            switch (model.getIcon()) {
                case "apartment":
                    holder.img_icon.setImageResource(R.drawable.apartment);
                    break;
                case "condo":
                    holder.img_icon.setImageResource(R.drawable.condo);
                    break;
                case "boat":
                    holder.img_icon.setImageResource(R.drawable.boat);
                    break;
                case "land":
                    holder.img_icon.setImageResource(R.drawable.land);
                    break;
                case "rooms":
                    holder.img_icon.setImageResource(R.drawable.rooms);
                    break;
                case "no-room":
                    holder.img_icon.setImageResource(R.drawable.no_room);
                    break;
                case "swimming":
                    holder.img_icon.setImageResource(R.drawable.swimming);
                    break;
                case "garden":
                    holder.img_icon.setImageResource(R.drawable.garden);
                    break;
                case "garage":
                    holder.img_icon.setImageResource(R.drawable.garage);
                    break;

            }

            holder.itemView.setOnClickListener(v -> {
                listener.onOptionClick(position, model);
                SELECTED_ITEM = position;
            });
        }
    }

    public interface OnOptionSelectListener {
        void onOptionClick(int pos, FacilityOptions option);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public static class DropdownViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private AppCompatTextView txt_item;
        private RadioButton radio;
        private ImageView img_icon;
        private CardView card_icon;
        public DropdownViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.view);
            txt_item = itemView.findViewById(R.id.txt_item);
            radio = itemView.findViewById(R.id.radio);
            img_icon = itemView.findViewById(R.id.img_icon);
            card_icon = itemView.findViewById(R.id.card_icon);
        }
    }
}
