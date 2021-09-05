package com.example.spazaadmin.dialogs;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.type.DateTime;

import java.util.Calendar;


public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    public TimePickerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set dialog width and height
        getDialog()
                .getWindow()
                .setLayout(1000, ViewGroup.LayoutParams.WRAP_CONTENT);

        //set dialog origin
        getDialog()
                .getWindow()
                .setGravity(Gravity.CENTER);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        DateTime currentTime = DateTime.getDefaultInstance();
        boolean is24HourFormat = DateFormat.is24HourFormat(getActivity());
        
        TimePickerDialog dialog = new TimePickerDialog
                (getActivity(), this, currentTime.getHours(),
                        currentTime.getMinutes(), is24HourFormat);

        return dialog;
        //return new TimePickerDialog(getActivity(),this , hour,minute, is24HourFormat);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        String AM_PM = (hourOfDay < 12)? "AM" :"PM";
        Toast.makeText(getActivity(),"Time selected HH:MM"+hourOfDay+":"+minute+":"+AM_PM, Toast.LENGTH_SHORT).show();
    }
}