package com.radius.assignment.view;

import static com.radius.assignment.utils.AppConstant.PREF_API_RESPONSE;
import static com.radius.assignment.utils.AppConstant.PREF_CURRENT_DATE;
import static com.radius.assignment.utils.AppConstant.SHARED_PREF_NAME;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.radius.assignment.adapter.FacilityListAdapter;
import com.radius.assignment.databinding.ActivityMainBinding;
import com.radius.assignment.model.FacilityResponse;
import com.radius.assignment.presenter.MainContract;
import com.radius.assignment.presenter.MainPresenter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    private ActivityMainBinding binding;
    private MainPresenter presenter;
    private FacilityListAdapter facilityAdapter;
    private SharedPreferences sharedPreferences;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String stored_date = sharedPreferences.getString(PREF_CURRENT_DATE, null);

        presenter = new MainPresenter(this);

        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        binding.recyclerView.setLayoutManager(manager);
        facilityAdapter = new FacilityListAdapter(this);
        binding.recyclerView.setAdapter(facilityAdapter);

        if (stored_date == null || !isCurrentDate(stored_date)) {
            presenter.fetchData();
        } else {
            String stored_data = sharedPreferences.getString(PREF_API_RESPONSE, null);
            if (stored_data != null) {
                FacilityResponse facilityResponse = new Gson().fromJson(stored_data, FacilityResponse.class);
                facilityAdapter.setList(facilityResponse);
            }

        }
    }

    private boolean isCurrentDate(String stored_date) {
        Date currentDate = new Date();
        String convertedDate = dateFormat.format(currentDate);
        try {
            Date old_date = dateFormat.parse(stored_date);
            if (old_date != null) {
                return old_date.compareTo(dateFormat.parse(stored_date)) == 0;
            } else {
                sharedPreferences.edit().putString(PREF_CURRENT_DATE, convertedDate).apply();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void showData(FacilityResponse facilityResponse) {
        /*save current date*/
        Date current_date = new Date();
        String convertedDate = dateFormat.format(current_date);
        sharedPreferences.edit().putString(PREF_CURRENT_DATE, convertedDate).apply();

        sharedPreferences.edit().putString(PREF_API_RESPONSE, facilityResponse.toString()).apply();
        facilityAdapter.setList(facilityResponse);
    }
}