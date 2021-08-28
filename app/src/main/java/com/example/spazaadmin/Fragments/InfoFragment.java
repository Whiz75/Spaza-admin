package com.example.spazaadmin.Fragments;

import static android.content.Context.LOCATION_SERVICE;
import static android.provider.SettingsSlicesContract.KEY_LOCATION;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.spazaadmin.R;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import com.google.android.gms.location.FusedLocationProviderClient;

public class InfoFragment extends Fragment{

    private MaterialButton WeekdaysOpenTimeButton, WeekdaysCloseTimeButton;
    private MaterialButton WeekendsOpenTimeButton, WeekendsCloseTimeButton;
    private MaterialButton SundayOpenTimeButton, SundayCloseTimeButton;
    private MaterialButton BtnUpdateInfo;

    int openHour, openMinutes;
    int closeHour, closeMinutes;
    int satHour, satMinutes;

    List<String> timeList;

    private GoogleMap map;
    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient fusedLocationProviderClient;

    //Request code for location permission request.
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    public static final int REQUEST_CHECK_CODE = 1;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean locationPermissionGranted;


    public InfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        //get current location
        getCurrentLocation();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        //call methods here
        init(view);

        return view;
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Task<Location> task = fusedLocationProviderClient.getLastLocation();
            task.addOnSuccessListener(location -> {
                if (location != null)
                {
                    Toast.makeText(getContext(), location.getLatitude()+" "+location,Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getContext(),"couldn't get location",Toast.LENGTH_LONG).show();
                }
            });
        }else {
            requestLocationPermission();
        }
    }

    private void requestLocationPermission()
    {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION)) {
            new AlertDialog.Builder(getContext())
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", (dialog, which) -> ActivityCompat.requestPermissions(getActivity(),
                            new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE))
                    .setNegativeButton("cancel", (dialog, which) -> dialog.dismiss())
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Permission GRANTED", Toast.LENGTH_SHORT).show();
                getCurrentLocation();
            } else {
                Toast.makeText(getContext(), "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void init(View view) {
        WeekdaysOpenTimeButton = view.findViewById(R.id.InputBusinessMFOpen);
        WeekdaysCloseTimeButton = view.findViewById(R.id.InputBusinessMFClose);

        WeekendsOpenTimeButton = view.findViewById(R.id.InputBusinessSatOpen);
        WeekendsCloseTimeButton = view.findViewById(R.id.InputBusinessSatClose);

        SundayOpenTimeButton = view.findViewById(R.id.InputBusinessSunOpen);
        SundayCloseTimeButton = view.findViewById(R.id.InputBusinessSunClose);

        BtnUpdateInfo = view.findViewById(R.id.BtnUpdateProfile);
    }

    private void SelectedButtons(View view) {
        //select weekdays open time
        WeekdaysOpenTimeButton.setOnClickListener(v -> {

            try {
                TimePickerDialog pickerDialog = new TimePickerDialog(getActivity(), R.style.Theme_MaterialComponents_Light_Dialog,
                        (view14, hourOfDay, minute) -> {

                            openHour = hourOfDay;
                            openMinutes = minute;

                            Calendar calendar = Calendar.getInstance();

                            calendar.set(0, 0, 0, openHour, openMinutes);

                            String time = openHour + ":" + openMinutes;
                            WeekdaysOpenTimeButton.setText(time);
                        }, 12, 0, false);

                pickerDialog.updateTime(openHour, openMinutes);
                pickerDialog.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //select weekdays close time
        WeekdaysCloseTimeButton.setOnClickListener(v -> {

            try {
                //call time picker dialog
                /*TimePickerFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.showNow(getFragmentManager(), "time picker");*/

                TimePickerDialog pickerDialog = new TimePickerDialog(getActivity(), R.style.Theme_MaterialComponents_Light_Dialog,
                        (view13, hourOfDay, minute) -> {

                            closeHour = hourOfDay;
                            closeMinutes = minute;

                            Calendar calendar = Calendar.getInstance();

                            calendar.set(0, 0, 0, openHour, openMinutes);

                            String time = closeHour + ":" + closeMinutes;
                            WeekdaysCloseTimeButton.setText(time);
                        }, 12, 0, false);

                pickerDialog.updateTime(openHour, openMinutes);
                pickerDialog.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //select sat open time
        WeekendsOpenTimeButton.setOnClickListener(v -> {
            try {
                //call time picker dialog
                /*TimePickerFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.showNow(getFragmentManager(), "time picker");*/

                TimePickerDialog pickerDialog = new TimePickerDialog(getActivity(), R.style.Theme_MaterialComponents_Light_Dialog,
                        (view12, hourOfDay, minute) -> {

                            satHour = hourOfDay;
                            satMinutes = minute;

                            Calendar calendar = Calendar.getInstance();

                            calendar.set(0, 0, 0, openHour, openMinutes);

                            String time = satHour + ":" + satMinutes;
                            WeekendsOpenTimeButton.setText(time);
                        }, 12, 0, false);

                pickerDialog.updateTime(openHour, openMinutes);
                pickerDialog.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //select sat close time
        WeekendsCloseTimeButton.setOnClickListener(v -> {
            try {
                //call time picker dialog
                TimePickerDialog pickerDialog = new TimePickerDialog(getActivity(), R.style.Theme_MaterialComponents_Light_Dialog,
                        (view1, hourOfDay, minute) -> {

                            satHour = hourOfDay;
                            satMinutes = minute;

                            Calendar calendar = Calendar.getInstance();
                            calendar.set(0, 0, 0, openHour, openMinutes);

                            String time = satHour + ":" + satMinutes;
                            WeekendsCloseTimeButton.setText(time);
                        }, 12, 0, false);

                pickerDialog.updateTime(openHour, openMinutes);
                pickerDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    private void UpLoadBusinessInfo(View view) {
        BtnUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setTime(View view, MaterialButton button, MaterialButton button1) {
        timeList = new ArrayList<>();

        button.setOnClickListener(v -> {
            try {
                TimePickerDialog pickerDialog = new TimePickerDialog(getActivity(), R.style.Theme_MaterialComponents_Light_Dialog,
                        (view1, hourOfDay, minute) -> {

                            openHour = hourOfDay;
                            openMinutes = minute;

                            Calendar calendar = Calendar.getInstance();
                            calendar.set(0, 0, 0, openHour, openMinutes);

                            String time = openHour + ":" + openMinutes;
                            button.setText(time);
                            //add to list
                            timeList.add(time);
                        }, 12, 0, false);

                pickerDialog.updateTime(openHour, openMinutes);
                pickerDialog.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        button1.setOnClickListener(v -> {
            try {
                TimePickerDialog pickerDialog = new TimePickerDialog(getActivity(), R.style.Theme_MaterialComponents_Light_Dialog,
                        (view12, hourOfDay, minute) -> {

                            openHour = hourOfDay;
                            openMinutes = minute;

                            Calendar calendar = Calendar.getInstance();
                            calendar.set(0, 0, 0, openHour, openMinutes);

                            String time = openHour + ":" + openMinutes;
                            button1.setText(time);

                            timeList.add(time);
                        }, 12, 0, false);

                pickerDialog.updateTime(openHour, openMinutes);
                pickerDialog.show();

                Toast.makeText(getContext(), timeList.toString(), Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}