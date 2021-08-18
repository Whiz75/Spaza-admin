package com.example.spazaadmin.Fragments;

import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.spazaadmin.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class InfoFragment extends Fragment {

    private MaterialButton WeekdaysOpenTimeButton, WeekdaysCloseTimeButton;
    private MaterialButton WeekendsOpenTimeButton, WeekendsCloseTimeButton;
    private MaterialButton SundayOpenTimeButton, SundayCloseTimeButton;

    int openHour, openMinutes;
    int closeHour, closeMinutes;
    int satHour, satMinutes;

    List<String> timeList;

    public InfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        //call methods here
        init(view);

        //call method to set open and close time for weekdays
        setTime(view, WeekdaysOpenTimeButton, WeekdaysCloseTimeButton);
        //call method to set open and close time for weekends
        setTime(view, WeekendsOpenTimeButton, WeekendsOpenTimeButton);
        //call method to set open and close time for sundays
        setTime(view,SundayOpenTimeButton,SundayCloseTimeButton);

        return view;
    }

    private void init(View view)
    {
        WeekdaysOpenTimeButton = view.findViewById(R.id.InputBusinessMFOpen);
        WeekdaysCloseTimeButton = view.findViewById(R.id.InputBusinessMFClose);

        WeekendsOpenTimeButton = view.findViewById(R.id.InputBusinessSatOpen);
        WeekendsCloseTimeButton = view.findViewById(R.id.InputBusinessSatClose);

        SundayOpenTimeButton = view.findViewById(R.id.InputBusinessSunOpen);
        SundayCloseTimeButton = view.findViewById(R.id.InputBusinessSunClose);
    }

    private void SelectedButtons(View view)
    {
        //select weekdays open time
        WeekdaysOpenTimeButton.setOnClickListener(v -> {

            try
            {
                TimePickerDialog pickerDialog = new TimePickerDialog(getActivity(), R.style.Theme_MaterialComponents_Light_Dialog,
                        (view14, hourOfDay, minute) -> {

                            openHour = hourOfDay;
                            openMinutes = minute;

                            Calendar calendar = Calendar.getInstance();

                            calendar.set(0, 0, 0, openHour, openMinutes);

                            String time = openHour+":"+ openMinutes;
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

            try
            {
                //call time picker dialog
                /*TimePickerFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.showNow(getFragmentManager(), "time picker");*/

                TimePickerDialog pickerDialog = new TimePickerDialog(getActivity(), R.style.Theme_MaterialComponents_Light_Dialog,
                        (view13, hourOfDay, minute) -> {

                            closeHour = hourOfDay;
                            closeMinutes = minute;

                            Calendar calendar = Calendar.getInstance();

                            calendar.set(0, 0, 0, openHour, openMinutes);

                            String time = closeHour+":"+ closeMinutes;
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
            try
            {
                //call time picker dialog
                /*TimePickerFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.showNow(getFragmentManager(), "time picker");*/

                TimePickerDialog pickerDialog = new TimePickerDialog(getActivity(), R.style.Theme_MaterialComponents_Light_Dialog,
                        (view12, hourOfDay, minute) -> {

                            satHour = hourOfDay;
                            satMinutes = minute;

                            Calendar calendar = Calendar.getInstance();

                            calendar.set(0, 0, 0, openHour, openMinutes);

                            String time = satHour+":"+ satMinutes;
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
            try
            {
                //call time picker dialog
                TimePickerDialog pickerDialog = new TimePickerDialog(getActivity(), R.style.Theme_MaterialComponents_Light_Dialog,
                        (view1, hourOfDay, minute) -> {

                            satHour = hourOfDay;
                            satMinutes = minute;

                            Calendar calendar = Calendar.getInstance();
                            calendar.set(0, 0, 0, openHour, openMinutes);

                            String time = satHour+":"+ satMinutes;
                            WeekendsCloseTimeButton.setText(time);
                        }, 12, 0, false);

                pickerDialog.updateTime(openHour, openMinutes);
                pickerDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    private void setTime(View view, MaterialButton button, MaterialButton button1)
    {
        timeList = new ArrayList<>();

        button.setOnClickListener(v -> {
            try
            {
                TimePickerDialog pickerDialog = new TimePickerDialog(getActivity(), R.style.Theme_MaterialComponents_Light_Dialog,
                        (view1, hourOfDay, minute) -> {

                            openHour = hourOfDay;
                            openMinutes = minute;

                            Calendar calendar = Calendar.getInstance();
                            calendar.set(0, 0, 0, openHour, openMinutes);

                            String time = openHour+":"+ openMinutes;
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
            try
            {
                TimePickerDialog pickerDialog = new TimePickerDialog(getActivity(), R.style.Theme_MaterialComponents_Light_Dialog,
                        (view12, hourOfDay, minute) -> {

                            openHour = hourOfDay;
                            openMinutes = minute;

                            Calendar calendar = Calendar.getInstance();
                            calendar.set(0, 0, 0, openHour, openMinutes);

                            String time = openHour+":"+ openMinutes;
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