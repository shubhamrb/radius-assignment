package com.radius.assignment.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.radius.assignment.R;
import com.radius.assignment.model.ExclusionsModel;
import com.radius.assignment.model.Facility;
import com.radius.assignment.model.FacilityResponse;

import java.util.ArrayList;
import java.util.List;

public class FacilityListAdapter extends RecyclerView.Adapter<FacilityListAdapter.FacilityViewHolder> {

    private Context mContext;
    private View root;
    private List<Facility> list;
    private List<List<ExclusionsModel>> exList;
    private List<String> exclude_id;

    public FacilityListAdapter(Context mContext) {
        this.mContext = mContext;
        list = new ArrayList<>();
        exList = new ArrayList<>();
        exclude_id = new ArrayList<>();
    }

    @NonNull
    @Override
    public FacilityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        root = LayoutInflater.from(mContext).inflate(R.layout.facility_item_layout, parent, false);
        return new FacilityViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull FacilityViewHolder holder, int position) {
        if (list.size() > 0) {
            Facility facility = list.get(position);
            holder.txt_facility.setText(facility.getName());

            LinearLayoutManager manager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
            holder.options_recycler.setLayoutManager(manager);
            holder.dropdownListAdapter = new DropdownListAdapter(mContext, facility.getOptions(), list.get(position).getSelected_option(), exclude_id, (pos, option) -> {
                if (position > 0) {
                    if (list.get(position - 1).getSelectedOption() == null) {
                        Toast.makeText(mContext, "Please select "+list.get(position-1).getName()+".", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                list.get(position).setSelected_option(pos);

                exclude_id = new ArrayList<>();
                boolean isFound = false;
                ExclusionsModel model = new ExclusionsModel();
                model.setFacility_id(facility.getFacility_id());
                model.setOptions_id(option.getId());
                list.get(position).setSelectedOption(model);

                for (int a = 0; a < list.size(); a++) {
                    for (int i = 0; i < exList.size(); i++) {
                        List<ExclusionsModel> exclusionsModelList = exList.get(i);
                        for (int j = 0; j < exclusionsModelList.size(); j++) {
                            if (list.get(a).getSelectedOption() != null
                                    && list.get(a).getSelectedOption().getFacility_id().equals(exclusionsModelList.get(j).getFacility_id())
                                    && list.get(a).getSelectedOption().getOptions_id().equals(exclusionsModelList.get(j).getOptions_id())) {
                                isFound = true;
                                break;
                            }
                        }
                        if (isFound) {
                            isFound = false;
                            for (int k = 0; k < exclusionsModelList.size(); k++) {
                                exclude_id.add(exclusionsModelList.get(k).getOptions_id());
                            }
                        }
                    }
                }
                Log.e("exclude", exclude_id.toString());
                notifyDataSetChanged();


            });
            holder.options_recycler.setAdapter(holder.dropdownListAdapter);

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(FacilityResponse facilityResponse) {
        this.list = facilityResponse.getFacilities();
        this.exList = facilityResponse.getExclusions();
        notifyDataSetChanged();
    }

    public static class FacilityViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView txt_facility;
        private RecyclerView options_recycler;
        private DropdownListAdapter dropdownListAdapter;

        public FacilityViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_facility = itemView.findViewById(R.id.txt_facility);
            options_recycler = itemView.findViewById(R.id.options_recycler);
        }
    }
}
