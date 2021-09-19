package com.example.spazaadmin.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.example.spazaadmin.R;
import com.example.spazaadmin.models.BusinessModel;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class InfoFragment extends Fragment {

    private FloatingActionButton edit_img_fab;
    private AppCompatImageView ImgSpazaLogo;
    private TextInputEditText businessName, businessPhoneNo, businessDescription;
    private MaterialAutoCompleteTextView InputBusinessMFOpen, InputBusinessMFClose;
    private MaterialAutoCompleteTextView InputBusinessSatOpen, InputBusinessSatClose;
    private MaterialAutoCompleteTextView InputBusinessSunOpen, InputBusinessSunClose;
    private MaterialButton BtnUpdateInfo;

    List<String> timeList;

    private GoogleMap map;
    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient fusedLocationProviderClient;

    //Request code for location permission request.
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    public static final int REQUEST_CHECK_CODE = 1;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean locationPermissionGranted;

    private Uri img_uri = null;

    public InfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        getCurrentLocation();

        //call methods here
        init(view);
        chooseImage(view);
        uploadBusinessProfile(view);

        return view;
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Task<Location> task = fusedLocationProviderClient.getLastLocation();
            task.addOnSuccessListener(location -> {
                if (location != null) {
                    Toast.makeText(getContext(), location.getLatitude()+" "+location,Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getContext(),"couldn't get location",Toast.LENGTH_LONG).show();
                }
            });
        }else {
            requestLocationPermission();
        }
    }

    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(Objects.requireNonNull(getActivity()),
                Manifest.permission.ACCESS_COARSE_LOCATION)) {
            new AlertDialog.Builder(Objects.requireNonNull(getContext()))
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

        edit_img_fab = view.findViewById(R.id.fab_edit_img);
        ImgSpazaLogo = view.findViewById(R.id.ImgSpazaLogo);

        businessName = view.findViewById(R.id.InputBusinessName);
        businessPhoneNo = view.findViewById(R.id.InputBusinessPhone);
        businessDescription = view.findViewById(R.id.InputBusinessDescription);

        InputBusinessMFOpen = view.findViewById(R.id.InputBusinessMFOpen);
        InputBusinessMFClose = view.findViewById(R.id.InputBusinessMFClose);
        setTime(InputBusinessMFOpen,InputBusinessMFClose);

        InputBusinessSatOpen = view.findViewById(R.id.InputBusinessSatOpen);
        InputBusinessSatClose = view.findViewById(R.id.InputBusinessSatClose);
        setTime(InputBusinessSatOpen,InputBusinessSatClose);

        InputBusinessSunOpen = view.findViewById(R.id.InputBusinessSunOpen);
        InputBusinessSunClose = view.findViewById(R.id.InputBusinessSunClose);
        setTime(InputBusinessSunOpen,InputBusinessSunClose);

        BtnUpdateInfo = view.findViewById(R.id.BtnUpdateProfile);
    }

    private boolean valid(){

        boolean isValid = true;

        if (TextUtils.isEmpty(Objects.requireNonNull(businessName.getText()).toString())){
            businessName.setError("Please provide the business name");
            isValid= false;
        }

        if (TextUtils.isEmpty(Objects.requireNonNull(businessPhoneNo.getText()).toString())){
            businessPhoneNo.setError("Please provide the business phone number");
            isValid= false;
        }

        if (TextUtils.isEmpty(Objects.requireNonNull(businessDescription.getText()).toString())){
            businessDescription.setError("Please provide the business description");
            isValid= false;
        }

        //auto textview
        if (TextUtils.isEmpty(InputBusinessMFOpen.getText().toString())){
            InputBusinessMFOpen.setError("Please provide the business open time");
            isValid= false;
        }

        if (TextUtils.isEmpty(InputBusinessMFClose.getText().toString())){
            InputBusinessMFClose.setError("Please provide the business open time");
            isValid= false;
        }

        if (TextUtils.isEmpty(InputBusinessSatOpen.getText().toString())){
            InputBusinessSatOpen.setError("Please provide the business open time");
            isValid= false;
        }

        if (TextUtils.isEmpty(InputBusinessSatClose.getText().toString())){
            InputBusinessSatClose.setError("Please provide the business open time");
            isValid= false;
        }

        if (TextUtils.isEmpty(InputBusinessSunOpen.getText().toString())){
            InputBusinessSunOpen.setError("Please provide the business open time");
            isValid= false;
        }

        if (TextUtils.isEmpty(InputBusinessSunClose.getText().toString())){
            InputBusinessSunClose.setError("Please provide the business open time");
            isValid= false;
        }

        if (businessPhoneNo.getText().toString().length()>10){
            businessPhoneNo.setEnabled(false);
            isValid= false;
        }
        return isValid;
    }

    private void uploadBusinessProfile(View view){
        BtnUpdateInfo.setOnClickListener(v ->
                setBusinessProfile());
    }

    private void setBusinessProfile(){
        if (!valid()){
            return;
        }

        String businessNam = businessName.getText().toString();
        String businessDesc = businessName.getText().toString();
        String businessPhoneNo = businessName.getText().toString();

        String businessMFOpen = InputBusinessMFOpen.getText().toString();
        String businessMFClose = InputBusinessMFClose.getText().toString();
        String businessSatOpen = InputBusinessSatOpen.getText().toString();
        String businessSatClose = InputBusinessSatClose.getText().toString();
        String businessSunOpen = InputBusinessSunOpen.getText().toString();
        String businessSunClose = InputBusinessSunClose.getText().toString();

        /*BusinessModel business = new BusinessModel();
        business.setBusinessName(businessNam);
        business.setBusinessDescription(businessDesc);
        business.setBusinessPhoneNo(businessPhoneNo);

        business.setBusinessMFOpen(businessMFOpen);
        business.setBusinessMFClose(businessMFClose);
        business.setBusinessSatOpen(businessSatOpen);
        business.setBusinessSatClose(businessSatClose);
        business.setBusinessSunOpen(businessSunOpen);
        business.setBusinessSunClose(businessSunClose);*/

        Map<String,Object> businessMap = new HashMap<>();
        businessMap.put("businessName", businessNam);
        businessMap.put("businessPhoneNo", businessPhoneNo);
        businessMap.put("businessDescription",businessDesc);

        businessMap.put("businessMFOpen", businessMFOpen);
        businessMap.put("businessMFClose", businessMFClose);
        businessMap.put("businessSatOpen", businessSatOpen);
        businessMap.put("businessSatClose", businessSatClose);
        businessMap.put("businessSunOpen", businessSunOpen);
        businessMap.put("businessSunClose", businessSunClose);

        SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        FirebaseFirestore
                .getInstance()
                .collection("Admins")
                .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .update(businessMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        pDialog.dismissWithAnimation();
                        Toast.makeText(getContext(), "Business profile created successfully!!!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void setTime(MaterialAutoCompleteTextView open, MaterialAutoCompleteTextView close) {
        // create list of time
        ArrayList<String> openTimeList = getOpenTimeList();
        ArrayList<String> closeTimeList = getCloseTimeList();

        //Create adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, openTimeList);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, closeTimeList);
        //Set adapter
        open.setAdapter(adapter);
        close.setAdapter(adapter1);
    }

    private ArrayList<String> getOpenTimeList() {

        ArrayList<String> openTime = new ArrayList<>();
        openTime.add("6:30 AM");
        openTime.add("7:00 AM");
        openTime.add("7:30 AM");
        openTime.add("8:00 AM");
        openTime.add("8:30 AM");
        openTime.add("9:00 AM");
        openTime.add("9:30 AM");
        openTime.add("10:00 AM");
        openTime.add("10:30 AM");
        openTime.add("11:00 AM");
        openTime.add("11:30 AM");
        openTime.add("12:00 AM");
        openTime.add("12:30 AM");

        return openTime;
    }

    private ArrayList<String> getCloseTimeList() {
        ArrayList<String> closeTime = new ArrayList<>();

        closeTime.add("13:00 PM");
        closeTime.add("13:30 PM");
        closeTime.add("14:00 PM");
        closeTime.add("14:30 PM");
        closeTime.add("15:00 PM");
        closeTime.add("15:30 PM");
        closeTime.add("16:00 PM");
        closeTime.add("16:30 PM");
        closeTime.add("17:00 PM");
        closeTime.add("17:30 PM");
        closeTime.add("18:00 PM");
        closeTime.add("18:30 PM");

        return closeTime;
    }

    private void chooseImage(View view){
        edit_img_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CHECK_CODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CHECK_CODE) {
            if (data != null) {
                img_uri = data.getData();
                ImgSpazaLogo.setImageURI(img_uri);
                setBusinessPic(img_uri);
            }
        }
    }

    private void setBusinessPic(final Uri uri) {

        final StorageReference filepath =
                FirebaseStorage
                .getInstance()
                .getReference()
                .child("images")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .child(System.currentTimeMillis()+"."+getFileExtention(uri));

        filepath.putFile(uri)
                .addOnSuccessListener(taskSnapshot -> filepath
                        .getDownloadUrl()
                        .addOnSuccessListener(uri1 -> {
                            try {
                                try {
                                    //reference.child("Users").child(uid).setValue(newuser);
                                    FirebaseFirestore
                                            .getInstance()
                                            .collection("Admins")
                                            .document(FirebaseAuth.getInstance().getUid())
                                            .update("uri", uri1.toString())
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(getContext(),"picture updated!!!",Toast.LENGTH_LONG);
                                                }
                                            });

                                }catch(Exception e)
                                {
                                    Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Toast.makeText(getContext(),""+e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        })).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtention(Uri mUri) {
        ContentResolver cr = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }
}