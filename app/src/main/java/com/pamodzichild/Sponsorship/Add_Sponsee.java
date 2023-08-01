package com.pamodzichild.Sponsorship;

import static android.app.Activity.RESULT_OK;
import static com.pamodzichild.MainActivity.fab;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.pamodzichild.MainActivity;
import com.pamodzichild.R;
import com.pamodzichild.SendEmail.SendMailTask;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hbb20.CountryCodePicker;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import android.location.LocationManager;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class Add_Sponsee extends Fragment {


    View view;
    TextView txtName;

    CircleImageView profileImage;
    TextInputLayout firstnameLayout1, lastnameLayout1, addressLayout1, idnumberLayout1, dobLayout1, disabilityLayout1, educationLayout1, reasonLayout1, bioLayout1;

    TextInputLayout firstnameLayout2, lastnameLayout2, emailLayout2, dobLayout2, mobilenumberLayout2,
            disabilityLayout2, occupationLayout2, relatioshipLayout;

    TextInputLayout moreGuardianOrParentInfoLayout,
            currentFamilySituationLayout,
            sourceOfIncomeLayout,
            averageMonthlyIncomeLayout,
            typeOfHouseLayout, numberOfPeopleInHouseLayout,
            numberOfPeopleUnder18Layout,
            typeOfLightLayout,
            typeOfCookingEnergyLayout,
            typeOfToiletLayout,
            typeOfWaterSourceLayout,
            distanceFromWaterSourceLayout,
            typeOfFloorLayout,
            numberOfSiblingsLayout,
            numberOfSiblingsInSchoolLayout,
            financialChallengesLayout,
            circumstancesAffectingSchoolingLayout,
            missedSchoolDueToLackOfResourcesLayout,
            numberOfMealsPerDayLayout,
            responsibilitiesAtHomeLayout,
            academicGoalsLayout,
            nameOfSponsorLayout,
            typeOfSponsorshipLayout,
            durationOfSponsorshipLayout,
            reasonWhySponsorshipStoppedLayout,
            howSponsorshipHelpedYouLayout,
            howYouFeltWhenSponsorshipStoppedLayout,
            planForPloughBackToCommunityLayout,
            homeLocationLayout,
            howYouKnewAboutPamhodziKidsFoundationLayout;

    RadioGroup radioGroupReceivedSponsorshipBefore;


    LinearLayout sponsorshipDetailsLayout;


    CountryCodePicker CountryCode1, CountryCode2;
    RadioGroup radioGenderGroup1, parentsAliveRadioGroup1, radioGenderGroup2;
    ImageView calender1, calender2, imageViewAddLocation;
    SwitchCompat disabilitySwitch1, disabilitySwitch2;

    FloatingActionButton save;


    Dialog dialog1;

    DatabaseReference sponseesRef, userRef;
    StorageReference storageReference;
    FirebaseAuth mAuth;
    String uid;
    ProgressBar progressBar;

    Button upload, uploadLetterBtn;
    ImageView imageViewLetterdot, letterImageView;
    RelativeLayout relativeLetterLayout;


    String gender1, gender2, parentsAlive;
    String txtimageProfile;
    String dob1, dob2;
    RadioButton radioMale1, radioFemale1, radioOther1;
    RadioButton radioMale2, radioFemale2, radioOther2;

    String firstName, lastName;

    boolean donorAlreadyExist = false;
    boolean haveReceivedSponsorshipBefore = false;

    private static final int GALLERY_REQUEST_CODE = 100;
    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int PERMISSIONS_REQUEST_CODE = 300;
    private static final int PICK_IMAGE_REQUEST = 20;
    private static final int PICK_IMAGE_REQUEST2 = 30;
    private static final int PICK_IMAGE_REQUEST3 = 40;
    private static final int PICK_IMAGE_REQUEST4 = 50;

    private static final int MAX_IMAGES = 3; // Maximum number of images that can be uploaded
    // Define the maximum file size in bytes
    private static final long MAX_FILE_SIZE = 4 * 1024 * 1024; // 5MB

    List<Uri> myimagesUriList;
    List<String> myimagesList;

    List<Uri> evidenceOfNeedUriList;
    List<String> evidenceOfNeedimagesList;
    Button uploadEvidenceBtn;


    ImageView imageView1, imageView2, imageView3;
    ImageView deleteImageView1, deleteImageView2, deleteImageView3;
    ImageView imageViewEvidenceDot;
    RelativeLayout relativeLayout1, relativeLayout2, relativeLayout3;

    RelativeLayout relativeEvidenceLayout1, relativeEvidenceLayout2, relativeEvidenceLayout3;
    ImageView evidenceImageView1, evidenceImageView2, evidenceImageView3;

    private static final String TAG = "MyActivity Tag";

    private String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };


    Bitmap photo = null;
    Uri selectedImageUri = null;
    Uri selectedLetterImageUri = null;
    String txtImageLetter = "";

    boolean usingGallery = false;
    boolean usingCamera = false;

    ProgressDialog progressDialog;


    private static final int PERMISSION_REQUEST_CODE = 1;
    private LocationManager locationManager;

    WebView webView;

    String receivedSponsorshipBefore = "";

    Button uploadLocationMapBtn;
    ImageView imageViewLocationMapDot;
    Uri selectedMapImageUri = null;
    String txtImageMap = "";
    NestedScrollView nestedScrollView;


    public Add_Sponsee() {
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
        view = inflater.inflate(R.layout.fragment_add__sponsee, container, false);
        getActivity().setTitle("Apply for Sponsorship");
        MainActivity.flag = 1;

        nestedScrollView = view.findViewById(R.id.nestedScrollView);
        nestedScrollView.scrollTo(0, 0);

        uploadLetterBtn = view.findViewById(R.id.uploadLetterBtn);
        imageViewLetterdot = view.findViewById(R.id.imageViewLetter);
        letterImageView = view.findViewById(R.id.letterImageView);

        uploadLocationMapBtn = view.findViewById(R.id.uploadLocationMapBtn);
        imageViewLocationMapDot = view.findViewById(R.id.imageViewLocationMapDot);

        relativeLetterLayout = view.findViewById(R.id.relativeLetterLayout);
        relativeLetterLayout.setVisibility(View.GONE);

        imageViewEvidenceDot = view.findViewById(R.id.imageViewEvidenceDot);

        txtName = view.findViewById(R.id.txtName);

        profileImage = view.findViewById(R.id.profileImage);

        firstnameLayout1 = view.findViewById(R.id.firstnameLayout1);
        lastnameLayout1 = view.findViewById(R.id.lastnameLayout1);
        addressLayout1 = view.findViewById(R.id.addressLayout1);
        idnumberLayout1 = view.findViewById(R.id.idnumberLayout1);
        dobLayout1 = view.findViewById(R.id.dobLayout1);
        disabilityLayout1 = view.findViewById(R.id.disabilityLayout1);
        educationLayout1 = view.findViewById(R.id.educationlayout1);
        reasonLayout1 = view.findViewById(R.id.reasonLayout1);
        bioLayout1 = view.findViewById(R.id.bioLayout1);

        firstnameLayout2 = view.findViewById(R.id.firstnameLayout2);
        lastnameLayout2 = view.findViewById(R.id.lastnameLayout2);
        emailLayout2 = view.findViewById(R.id.emailLayout2);
        dobLayout2 = view.findViewById(R.id.dobLayout2);
        mobilenumberLayout2 = view.findViewById(R.id.mobilenumberLayout2);
        disabilityLayout2 = view.findViewById(R.id.disabilityLayout2);
        occupationLayout2 = view.findViewById(R.id.occupationLayout2);
        relatioshipLayout = view.findViewById(R.id.relatioshipLayout);


        CountryCode2 = view.findViewById(R.id.CountryCode2);

        radioGenderGroup1 = view.findViewById(R.id.radioGenderGroup1);
        parentsAliveRadioGroup1 = view.findViewById(R.id.parentsAliveRadioGroup1);
        radioGenderGroup2 = view.findViewById(R.id.radioGenderGroup2);

        calender1 = view.findViewById(R.id.calender1);
        calender2 = view.findViewById(R.id.calender2);

        disabilitySwitch1 = view.findViewById(R.id.disabilitySwitch1);
        disabilitySwitch2 = view.findViewById(R.id.disabilitySwitch2);

        save = view.findViewById(R.id.save);


        dialog1 = new Dialog(getContext());

        sponseesRef = FirebaseDatabase.getInstance().getReference().child("Sponsees");
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();

        storageReference = FirebaseStorage.getInstance().getReference().child("Sponsees");

        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);


        gender1 = "";
        gender2 = "";

        parentsAlive = "";
        txtimageProfile = "";
        dob1 = "";
        dob2 = "";
        firstName = "";
        lastName = "";
        myimagesUriList = new ArrayList<>();

        myimagesList = new ArrayList<>();

        evidenceOfNeedUriList = new ArrayList<>();
        evidenceOfNeedimagesList = new ArrayList<>();

        upload = view.findViewById(R.id.upload);

        imageView1 = view.findViewById(R.id.imageView1);
        imageView2 = view.findViewById(R.id.imageView2);
        imageView3 = view.findViewById(R.id.imageView3);

        deleteImageView1 = view.findViewById(R.id.deleteImageView1);
        deleteImageView2 = view.findViewById(R.id.deleteImageView2);
        deleteImageView3 = view.findViewById(R.id.deleteImageView3);

        relativeLayout1 = view.findViewById(R.id.relativeLayout1);
        relativeLayout2 = view.findViewById(R.id.relativeLayout2);
        relativeLayout3 = view.findViewById(R.id.relativeLayout3);


        relativeLayout1.setVisibility(View.GONE);
        relativeLayout2.setVisibility(View.GONE);
        relativeLayout3.setVisibility(View.GONE);


        moreGuardianOrParentInfoLayout = view.findViewById(R.id.moreGuardianOrParentInfoLayout);
        currentFamilySituationLayout = view.findViewById(R.id.currentFamilySituationLayout);
        sourceOfIncomeLayout = view.findViewById(R.id.sourceOfIncomeLayout);
        averageMonthlyIncomeLayout = view.findViewById(R.id.averageMonthlyIncomeLayout);
        typeOfHouseLayout = view.findViewById(R.id.typeOfHouseLayout);
        numberOfPeopleInHouseLayout = view.findViewById(R.id.numberOfPeopleInHouseLayout);
        numberOfPeopleUnder18Layout = view.findViewById(R.id.numberOfPeopleUnder18Layout);
        typeOfLightLayout = view.findViewById(R.id.typeOfLightLayout);
        typeOfCookingEnergyLayout = view.findViewById(R.id.typeOfCookingEnergyLayout);
        typeOfToiletLayout = view.findViewById(R.id.typeOfToiletLayout);
        typeOfWaterSourceLayout = view.findViewById(R.id.typeOfWaterSourceLayout);
        distanceFromWaterSourceLayout = view.findViewById(R.id.distanceFromWaterSourceLayout);
        typeOfFloorLayout = view.findViewById(R.id.typeOfFloorLayout);
        numberOfSiblingsLayout = view.findViewById(R.id.numberOfSiblingsLayout);
        numberOfSiblingsInSchoolLayout = view.findViewById(R.id.numberOfSiblingsInSchoolLayout);
        financialChallengesLayout = view.findViewById(R.id.financialChallengesLayout);
        circumstancesAffectingSchoolingLayout = view.findViewById(R.id.circumstancesAffectingSchoolingLayout);
        missedSchoolDueToLackOfResourcesLayout = view.findViewById(R.id.missedSchoolDueToLackOfResourcesLayout);
        numberOfMealsPerDayLayout = view.findViewById(R.id.numberOfMealsPerDayLayout);
        responsibilitiesAtHomeLayout = view.findViewById(R.id.responsibilitiesAtHomeLayout);
        academicGoalsLayout = view.findViewById(R.id.academicGoalsLayout);
        nameOfSponsorLayout = view.findViewById(R.id.nameOfSponsorLayout);
        typeOfSponsorshipLayout = view.findViewById(R.id.typeOfSponsorshipLayout);
        durationOfSponsorshipLayout = view.findViewById(R.id.durationOfSponsorshipLayout);
        reasonWhySponsorshipStoppedLayout = view.findViewById(R.id.reasonWhySponsorshipStoppedLayout);
        howSponsorshipHelpedYouLayout = view.findViewById(R.id.howSponsorshipHelpedYouLayout);
        howYouFeltWhenSponsorshipStoppedLayout = view.findViewById(R.id.howYouFeltWhenSponsorshipStoppedLayout);
        planForPloughBackToCommunityLayout = view.findViewById(R.id.planForPloughBackToCommunityLayout);
        homeLocationLayout = view.findViewById(R.id.homeLocationLayout);
        howYouKnewAboutPamhodziKidsFoundationLayout = view.findViewById(R.id.howYouKnewAboutPamhodziKidsFoundationLayout);


        radioGroupReceivedSponsorshipBefore = view.findViewById(R.id.radioGroupReceivedSponsorshipBefore);
        sponsorshipDetailsLayout = view.findViewById(R.id.sponsorshipDetailsLayout);
        sponsorshipDetailsLayout.setVisibility(View.GONE);

        imageViewAddLocation = view.findViewById(R.id.imageViewAddLocation);

        progressDialog = new ProgressDialog(getContext());


        webView = view.findViewById(R.id.webView);
        webView.setVisibility(View.GONE);


        relativeEvidenceLayout1 = view.findViewById(R.id.relativeEvidenceLayout1);
        relativeEvidenceLayout2 = view.findViewById(R.id.relativeEvidenceLayout2);
        relativeEvidenceLayout3 = view.findViewById(R.id.relativeEvidenceLayout3);

        evidenceImageView1 = view.findViewById(R.id.evidenceImageView1);
        evidenceImageView2 = view.findViewById(R.id.evidenceImageView2);
        evidenceImageView3 = view.findViewById(R.id.evidenceImageView3);


        relativeEvidenceLayout1.setVisibility(View.GONE);
        relativeEvidenceLayout2.setVisibility(View.GONE);
        relativeEvidenceLayout3.setVisibility(View.GONE);

        uploadEvidenceBtn = view.findViewById(R.id.uploadEvidenceBtn);


        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        fab.hide();

        calender1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int currentYear = calendar.get(Calendar.YEAR);
                int currentMonth = calendar.get(Calendar.MONTH);
                int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {

                        dobLayout1.getEditText().setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
                        dob1 = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;

                        Log.e("Date : ", selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);

                    }
                }, currentYear, currentMonth, currentDay);

                datePickerDialog.show();
            }
        });
        calender2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int currentYear = calendar.get(Calendar.YEAR);
                int currentMonth = calendar.get(Calendar.MONTH);
                int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {

                        dobLayout2.getEditText().setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
                        dob2 = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;

                        Log.e("Date : ", selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);

                    }
                }, currentYear, currentMonth, currentDay);

                datePickerDialog.show();
            }
        });
        radioGenderGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton btn = view.findViewById(i);
                gender1 = btn.getText().toString();

                Log.e("Selected gender 1: ", gender1);
            }
        });
        radioGenderGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton btn = view.findViewById(i);
                gender2 = btn.getText().toString();

                Log.e("Selected gender 2: ", gender2);
            }
        });
        parentsAliveRadioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton btn = view.findViewById(i);
                parentsAlive = btn.getText().toString();

                Log.e("Parents Alive : ", parentsAlive);
            }
        });
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkPermissions();

            }
        });


        uploadLetterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleLetterFileUpload();
            }
        });


        uploadLocationMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleMapFileUpload();
            }
        });

        //   uriList.clear();


     /*   progressDialog.setMessage("Preparing...");
        progressDialog.setTitle("Please wait");
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();*/

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String currentUserID = mAuth.getCurrentUser().getUid();
     /*   userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel userModel = snapshot.child(currentUserID).getValue(UserModel.class);
                txtName.setText(userModel.getFname() + " " + userModel.getLname());
                firstName = userModel.getFname();
                lastName = userModel.getLname();
                CountryCode1.setCountryForNameCode(userModel.getCountyCodeName());
                addressLayout1.getEditText().setText(userModel.getLocaladdress());

                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/


        disabilitySwitch1.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    disabilityLayout1.setVisibility(View.VISIBLE);
                } else {
                    disabilityLayout1.setVisibility(View.GONE);
                }
            }
        });
        disabilitySwitch2.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    disabilityLayout2.setVisibility(View.VISIBLE);
                } else {
                    disabilityLayout2.setVisibility(View.GONE);
                }
            }
        });


        upload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                handleFileUpload();
            }
        });

        uploadEvidenceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                handleEvidenceOfNeedFileUpload();
            }
        });


        deleteImageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myimagesUriList.remove(0);
                relativeLayout1.setVisibility(View.GONE);
            }
        });

        deleteImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myimagesUriList.remove(1);
                relativeLayout2.setVisibility(View.GONE);
            }
        });

        deleteImageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myimagesUriList.remove(myimagesUriList.size() - 3);
                relativeLayout3.setVisibility(View.GONE);
            }
        });

        radioGroupReceivedSponsorshipBefore.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                RadioButton btn = view.findViewById(i);
                receivedSponsorshipBefore = btn.getText().toString();

                if (receivedSponsorshipBefore.equals("Yes")) {
                    sponsorshipDetailsLayout.setVisibility(View.VISIBLE);
                    haveReceivedSponsorshipBefore = true;
                } else {
                    sponsorshipDetailsLayout.setVisibility(View.GONE);
                    haveReceivedSponsorshipBefore = false;
                }

            }
        });

        imageViewAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // webView.setVisibility(View.VISIBLE);
             //   webView.getSettings().setJavaScriptEnabled(true);
                // webView.loadUrl("https://www.google.com/maps");


                // Request location permission

                if (ContextCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

                    requestLocationPermission();
                } else {
                    // get the location
                    getLocation();
                }


            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validate();

                if (validate()) {


                    save.setEnabled(false);
                    progressBar.setVisibility(View.VISIBLE);

                    progressDialog.setMessage("Uploading Profile ...");
                    progressDialog.setTitle("Please wait");
                    progressDialog.show();

                    if (usingCamera) {
                        uploadFromCamera();
                    } else if (usingGallery) {
                        uploadFromGallery();
                    }


                }
            }
        });


    }

    private void handleMapFileUpload() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean allGranted = true;
            for (String permission : PERMISSIONS) {
                if (ContextCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    break;
                }
            }
            if (allGranted) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST4);
            } else {
                requestPermissions(PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            }
        } else {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST4);

        }
    }

    private void handleLetterFileUpload() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean allGranted = true;
            for (String permission : PERMISSIONS) {
                if (ContextCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    break;
                }
            }
            if (allGranted) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST3);
            } else {
                requestPermissions(PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            }
        } else {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST3);

        }
    }

    private void handleEvidenceOfNeedFileUpload() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST2);
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(requireActivity(),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_REQUEST_CODE);
    }


    private void getLocation() {

        FusedLocationProviderClient fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(requireContext());

        try {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(location -> {
                        if (location != null) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            Log.d(TAG, "Current location: " + latitude + ", " + longitude);

                            homeLocationLayout.getEditText().setText(latitude + ", " + longitude);
                            txtImageMap = latitude + ", " + longitude;
                           // webView.setVisibility(View.GONE);
                            //webView.loadUrl("https://www.google.com/maps?q=" + latitude + "," + longitude + "&z=15&output=embed");

                            imageViewLocationMapDot.setImageDrawable(getResources().getDrawable(R.drawable.radio_button_checked_green));
                        } else {
                            Log.e(TAG, "Last location is null");
                        }
                    });
        } catch (SecurityException e) {
            Log.e(TAG, "Error getting location: " + e.getMessage());
        }

    }


    private void continueSavingdata() {
        int i = 0;
        if (myimagesUriList.size() > 0) {
            myimagesList.clear();

            progressDialog.setMessage("Uploading Your Images ...");
            progressBar.setVisibility(View.VISIBLE);

            for (Uri uri : myimagesUriList) {
                Log.e("URI List : ", myimagesUriList.get(i).toString());
                StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(uri.getLastPathSegment());
                UploadTask uploadTask = storageRef.putFile(uri);
                int finalI = i;
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Upload success
                        Log.d(TAG, "Upload success");
                        // Get the download URL for the uploaded file
                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri downloadUrl) {
                                Log.d(TAG, "File available at " + downloadUrl);

                                myimagesList.add(downloadUrl.toString());
                                //    Log.e("Added Image : "+finalI, imageList.get(finalI).toString());
                                //Toast image upload success
                                Toast.makeText(getContext(), "Image " + String.valueOf(finalI + 1) + " uploaded Successfully", Toast.LENGTH_SHORT).show();

                                progressDialog.setMessage("Uploading Image " + String.valueOf(finalI + 1) + " of " + String.valueOf(myimagesUriList.size()));

                                // Do something with the download URL, such as save it to a database
                                if (finalI == myimagesUriList.size() - 1) {

                                    saveDataFinal();
                                    Log.e("Image List  size : ", String.valueOf(myimagesList.size()));

                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                // Upload failed
                                Toast.makeText(getContext(), "Error: upload failed Try Again", Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "Upload failed : " + e.getMessage());
                                save.setEnabled(true);
                                progressBar.setVisibility(View.GONE);
                                progressDialog.dismiss();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Upload error
                        Toast.makeText(getContext(), "Error: upload failed Try Again", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Upload error" + e.getMessage());
                        save.setEnabled(true);
                        progressBar.setVisibility(View.GONE);
                        progressDialog.dismiss();
                    }
                });

                i++;
            }
        } else {
            // progressDialog.dismiss();


            saveDataFinal();


        }
    }


    private boolean validate() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);


        if (!usingCamera && !usingGallery) {
            Toast.makeText(getContext(), "Please Upload Profile Image", Toast.LENGTH_SHORT).show();

            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            nestedScrollView.scrollTo(0, 0);
            profileImage.requestFocus();

            return false;
        }


        String checkemail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


        if (firstnameLayout1.getEditText().getText().toString().isEmpty()) {

            firstnameLayout1.setError("First Name is required");
            firstnameLayout1.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(firstnameLayout1.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            firstnameLayout1.setError(null);
        }


        if (lastnameLayout1.getEditText().getText().toString().isEmpty()) {
            lastnameLayout1.setError("Last Name is required");
            lastnameLayout1.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(lastnameLayout1.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            lastnameLayout1.setError(null);
        }


        if (idnumberLayout1.getEditText().getText().toString().isEmpty()) {
            idnumberLayout1.setError("ID Number is required");
            idnumberLayout1.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(idnumberLayout1.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            idnumberLayout1.setError(null);
        }

        if (addressLayout1.getEditText().getText().toString().isEmpty()) {
            addressLayout1.setError("Address is required");
            addressLayout1.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(addressLayout1.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            addressLayout1.setError(null);
        }

        if (dobLayout1.getEditText().getText().toString().isEmpty()) {
            dobLayout1.setError("Date of Birth is required");
            dobLayout1.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(dobLayout1.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            // Get the string from the TextBox
            String dateString = dobLayout1.getEditText().getText().toString();

// Create a SimpleDateFormat object with the modified format
            SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");
            dateFormat.setLenient(false);

            try {
                // Parse the string into a Date object
                Date date = dateFormat.parse(dateString);

                dobLayout1.setError(null);
                // If the parsing was successful, the string matches the format
                // You can do something here to indicate that the input is valid
            } catch (ParseException e) {
                // If the parsing failed, the string does not match the format
                // You can do something here to indicate that the input is invalid
                dobLayout1.requestFocus();
                dobLayout1.setError("Invalid Date format use dd/mm/yyyy  ");

                if (imm != null) {
                    imm.showSoftInput(dobLayout1.getEditText(), InputMethodManager.SHOW_IMPLICIT);
                }
                return false;
            }
        }


        if (gender1.isEmpty()) {
            Toast.makeText(getContext(), "Please Select your Gender", Toast.LENGTH_SHORT).show();
            return false;
        }


        if (disabilitySwitch1.isChecked()) {
            if (disabilityLayout1.getEditText().getText().toString().isEmpty()) {
                disabilityLayout1.setError("Disability is required");
                disabilityLayout1.getEditText().requestFocus();

                if (imm != null) {
                    imm.showSoftInput(disabilityLayout1.getEditText(), InputMethodManager.SHOW_IMPLICIT);
                }
                return false;
            } else {
                disabilityLayout1.setError(null);
            }
        }

        if (educationLayout1.getEditText().getText().toString().isEmpty()) {
            educationLayout1.setError("Education is required");
            educationLayout1.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(educationLayout1.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            educationLayout1.setError(null);
        }


        if (bioLayout1.getEditText().getText().toString().isEmpty()) {
            bioLayout1.setError("Biography is required");
            bioLayout1.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(bioLayout1.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            bioLayout1.setError(null);
        }


        if (parentsAlive.isEmpty()) {
            Toast.makeText(getContext(), "Please Select if your Parents are Alive", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (firstnameLayout2.getEditText().getText().toString().isEmpty()) {
            firstnameLayout2.setError("Your Guardian First Name is required");
            firstnameLayout2.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(firstnameLayout2.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            firstnameLayout2.setError(null);
        }

        if (lastnameLayout2.getEditText().getText().toString().isEmpty()) {
            lastnameLayout2.setError("Your Guardian Last Name is required");
            lastnameLayout2.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(lastnameLayout2.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            lastnameLayout2.setError(null);
        }


        if (emailLayout2.getEditText().getText().toString().isEmpty()) {
            emailLayout2.setError("Your Guardian Email is required");
            emailLayout2.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(emailLayout2.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            if (!emailLayout2.getEditText().getText().toString().matches(checkemail)) {
                emailLayout2.setError("Invalid Guardian Email");
                emailLayout2.getEditText().requestFocus();

                if (imm != null) {
                    imm.showSoftInput(emailLayout2.getEditText(), InputMethodManager.SHOW_IMPLICIT);
                }
                return false;
            } else {
                emailLayout2.setError(null);
            }
        }


        if (relatioshipLayout.getEditText().getText().toString().isEmpty()) {
            relatioshipLayout.setError("Relationship with guardian is required");
            relatioshipLayout.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(relatioshipLayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            relatioshipLayout.setError(null);
        }


        if (dobLayout2.getEditText().getText().toString().isEmpty()) {
            dobLayout2.setError("Guardian Date of Birth is required");
            dobLayout2.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(dobLayout2.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {


            // Get the string from the TextBox
            String dateString = dobLayout2.getEditText().getText().toString();

// Create a SimpleDateFormat object with the modified format
            SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");
            dateFormat.setLenient(false);

            try {
                // Parse the string into a Date object
                Date date = dateFormat.parse(dateString);

                dobLayout2.setError(null);
                // If the parsing was successful, the string matches the format
                // You can do something here to indicate that the input is valid
            } catch (ParseException e) {
                // If the parsing failed, the string does not match the format
                // You can do something here to indicate that the input is invalid
                dobLayout2.requestFocus();
                dobLayout2.setError("Invalid Date format use dd/mm/yyyy  ");

                if (imm != null) {
                    imm.showSoftInput(dobLayout2.getEditText(), InputMethodManager.SHOW_IMPLICIT);
                }
                return false;
            }


        }


        if (gender2.isEmpty()) {
            Toast.makeText(getContext(), "Please Select gender of your guardian", Toast.LENGTH_SHORT).show();
            return false;

        }

        if (mobilenumberLayout2.getEditText().getText().toString().isEmpty()) {
            mobilenumberLayout2.setError("Your Guardian Mobile Number is required");
            mobilenumberLayout2.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(mobilenumberLayout2.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            mobilenumberLayout2.setError(null);
        }


        if (disabilitySwitch2.isChecked()) {
            if (disabilityLayout2.getEditText().getText().toString().isEmpty()) {
                disabilityLayout2.setError("Guardian Disability is required");
                disabilityLayout2.getEditText().requestFocus();

                if (imm != null) {
                    imm.showSoftInput(disabilityLayout2.getEditText(), InputMethodManager.SHOW_IMPLICIT);
                }
                return false;
            } else {
                disabilityLayout2.setError(null);
            }
        }


        if (occupationLayout2.getEditText().getText().toString().isEmpty()) {
            occupationLayout2.setError("Guardian Occupation is required");
            occupationLayout2.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(occupationLayout2.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            occupationLayout2.setError(null);
        }


        if (moreGuardianOrParentInfoLayout.getEditText().getText().toString().isEmpty()) {
            moreGuardianOrParentInfoLayout.setError("More Guardian or Parent Info is required");
            moreGuardianOrParentInfoLayout.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(moreGuardianOrParentInfoLayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            moreGuardianOrParentInfoLayout.setError(null);

        }

        if (currentFamilySituationLayout.getEditText().getText().toString().isEmpty()) {
            currentFamilySituationLayout.setError("Current Family Situation is required");
            currentFamilySituationLayout.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(currentFamilySituationLayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            currentFamilySituationLayout.setError(null);

        }

        if (sourceOfIncomeLayout.getEditText().getText().toString().isEmpty()) {
            sourceOfIncomeLayout.setError("Source of Income is required");
            sourceOfIncomeLayout.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(sourceOfIncomeLayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            sourceOfIncomeLayout.setError(null);

        }

        if (averageMonthlyIncomeLayout.getEditText().getText().toString().isEmpty()) {
            averageMonthlyIncomeLayout.setError("Average Monthly Income is required");
            averageMonthlyIncomeLayout.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(averageMonthlyIncomeLayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            averageMonthlyIncomeLayout.setError(null);

        }

        if (typeOfHouseLayout.getEditText().getText().toString().isEmpty()) {
            typeOfHouseLayout.setError("Type of House is required");
            typeOfHouseLayout.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(typeOfHouseLayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            typeOfHouseLayout.setError(null);

        }

        if (numberOfPeopleInHouseLayout.getEditText().getText().toString().isEmpty()) {
            numberOfPeopleInHouseLayout.setError("Number of People in House is required");
            numberOfPeopleInHouseLayout.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(numberOfPeopleInHouseLayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            numberOfPeopleInHouseLayout.setError(null);

        }

        if (numberOfPeopleUnder18Layout.getEditText().getText().toString().isEmpty()) {
            numberOfPeopleUnder18Layout.setError("Number of People Under 18 is required");
            numberOfPeopleUnder18Layout.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(numberOfPeopleUnder18Layout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            numberOfPeopleUnder18Layout.setError(null);

        }

        if (typeOfLightLayout.getEditText().getText().toString().isEmpty()) {
            typeOfLightLayout.setError("Type of Light is required");
            typeOfLightLayout.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(typeOfLightLayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            typeOfLightLayout.setError(null);

        }

        if (typeOfCookingEnergyLayout.getEditText().getText().toString().isEmpty()) {
            typeOfCookingEnergyLayout.setError("Type of Cooking Energy is required");
            typeOfCookingEnergyLayout.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(typeOfCookingEnergyLayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            typeOfCookingEnergyLayout.setError(null);

        }

        if (typeOfToiletLayout.getEditText().getText().toString().isEmpty()) {
            typeOfToiletLayout.setError("Type of Toilet is required");
            typeOfToiletLayout.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(typeOfToiletLayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            typeOfToiletLayout.setError(null);

        }

        if (typeOfWaterSourceLayout.getEditText().getText().toString().isEmpty()) {
            typeOfWaterSourceLayout.setError("Type of Water Source is required");
            typeOfWaterSourceLayout.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(typeOfWaterSourceLayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            typeOfWaterSourceLayout.setError(null);

        }


        if (distanceFromWaterSourceLayout.getEditText().getText().toString().isEmpty()) {
            distanceFromWaterSourceLayout.setError("Distance from Water Source is required");
            distanceFromWaterSourceLayout.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(distanceFromWaterSourceLayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            distanceFromWaterSourceLayout.setError(null);

        }

        if (typeOfFloorLayout.getEditText().getText().toString().isEmpty()) {
            typeOfFloorLayout.setError("Type of Floor is required");
            typeOfFloorLayout.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(typeOfFloorLayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            typeOfFloorLayout.setError(null);

        }

        if (numberOfSiblingsLayout.getEditText().getText().toString().isEmpty()) {
            numberOfSiblingsLayout.setError("Number of Siblings is required");
            numberOfSiblingsLayout.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(numberOfSiblingsLayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            numberOfSiblingsLayout.setError(null);

        }

        if (numberOfSiblingsInSchoolLayout.getEditText().getText().toString().isEmpty()) {
            numberOfSiblingsInSchoolLayout.setError("Number of Siblings in School is required");
            numberOfSiblingsInSchoolLayout.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(numberOfSiblingsInSchoolLayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            numberOfSiblingsInSchoolLayout.setError(null);

        }

        if (financialChallengesLayout.getEditText().getText().toString().isEmpty()) {
            financialChallengesLayout.setError("Financial Challenges is required");
            financialChallengesLayout.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(financialChallengesLayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            financialChallengesLayout.setError(null);

        }

        if (circumstancesAffectingSchoolingLayout.getEditText().getText().toString().isEmpty()) {
            circumstancesAffectingSchoolingLayout.setError("Circumstances Affecting Schooling is required");
            circumstancesAffectingSchoolingLayout.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(circumstancesAffectingSchoolingLayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            circumstancesAffectingSchoolingLayout.setError(null);

        }

        if (missedSchoolDueToLackOfResourcesLayout.getEditText().getText().toString().isEmpty()) {
            missedSchoolDueToLackOfResourcesLayout.setError("Missed School Due to Lack of Resources is required");
            missedSchoolDueToLackOfResourcesLayout.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(missedSchoolDueToLackOfResourcesLayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            missedSchoolDueToLackOfResourcesLayout.setError(null);

        }

        if (numberOfMealsPerDayLayout.getEditText().getText().toString().isEmpty()) {
            numberOfMealsPerDayLayout.setError("Number of Meals Per Day is required");
            numberOfMealsPerDayLayout.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(numberOfMealsPerDayLayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            numberOfMealsPerDayLayout.setError(null);

        }

        if (responsibilitiesAtHomeLayout.getEditText().getText().toString().isEmpty()) {
            responsibilitiesAtHomeLayout.setError("Responsibilities at Home is required");
            responsibilitiesAtHomeLayout.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(responsibilitiesAtHomeLayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;

        } else {
            responsibilitiesAtHomeLayout.setError(null);

        }

        if (academicGoalsLayout.getEditText().getText().toString().isEmpty()) {
            academicGoalsLayout.setError("Academic Goals is required");
            academicGoalsLayout.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(academicGoalsLayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            academicGoalsLayout.setError(null);

        }


        if (haveReceivedSponsorshipBefore) {

            if (nameOfSponsorLayout.getEditText().getText().toString().isEmpty()) {
                nameOfSponsorLayout.setError("Name of Sponsor is required");
                nameOfSponsorLayout.getEditText().requestFocus();

                if (imm != null) {
                    imm.showSoftInput(nameOfSponsorLayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
                }
                return false;
            } else {
                nameOfSponsorLayout.setError(null);
            }

            if (typeOfSponsorshipLayout.getEditText().getText().toString().isEmpty()) {
                typeOfSponsorshipLayout.setError("Type of Sponsorship is required");
                typeOfSponsorshipLayout.getEditText().requestFocus();

                if (imm != null) {
                    imm.showSoftInput(typeOfSponsorshipLayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
                }
                return false;
            } else {
                typeOfSponsorshipLayout.setError(null);
            }

            if (durationOfSponsorshipLayout.getEditText().getText().toString().isEmpty()) {
                durationOfSponsorshipLayout.setError("Duration of Sponsorship is required");
                durationOfSponsorshipLayout.getEditText().requestFocus();

                if (imm != null) {
                    imm.showSoftInput(durationOfSponsorshipLayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
                }
                return false;
            } else {
                durationOfSponsorshipLayout.setError(null);
            }

            if (reasonWhySponsorshipStoppedLayout.getEditText().getText().toString().isEmpty()) {
                reasonWhySponsorshipStoppedLayout.setError("Reason Why Sponsorship Stopped is required");
                reasonWhySponsorshipStoppedLayout.getEditText().requestFocus();

                if (imm != null) {
                    imm.showSoftInput(reasonWhySponsorshipStoppedLayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
                }
                return false;
            } else {
                reasonWhySponsorshipStoppedLayout.setError(null);
            }

            if (howSponsorshipHelpedYouLayout.getEditText().getText().toString().isEmpty()) {
                howSponsorshipHelpedYouLayout.setError("How Sponsorship Helped You is required");
                howSponsorshipHelpedYouLayout.getEditText().requestFocus();

                if (imm != null) {
                    imm.showSoftInput(howSponsorshipHelpedYouLayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
                }
                return false;
            } else {
                howSponsorshipHelpedYouLayout.setError(null);
            }

            if (howYouFeltWhenSponsorshipStoppedLayout.getEditText().getText().toString().isEmpty()) {
                howYouFeltWhenSponsorshipStoppedLayout.setError("How You Felt When Sponsorship Stopped is required");
                howYouFeltWhenSponsorshipStoppedLayout.getEditText().requestFocus();

                if (imm != null) {
                    imm.showSoftInput(howYouFeltWhenSponsorshipStoppedLayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
                }
                return false;
            } else {
                howYouFeltWhenSponsorshipStoppedLayout.setError(null);
            }

        }


        if (planForPloughBackToCommunityLayout.getEditText().getText().toString().isEmpty()) {
            planForPloughBackToCommunityLayout.setError("Plan for Plough Back to Community is required");
            planForPloughBackToCommunityLayout.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(planForPloughBackToCommunityLayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            planForPloughBackToCommunityLayout.setError(null);
        }

        if (reasonLayout1.getEditText().getText().toString().isEmpty()) {
            reasonLayout1.setError("Reason is required");
            reasonLayout1.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(reasonLayout1.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            reasonLayout1.setError(null);
        }


        if (homeLocationLayout.getEditText().getText().toString().isEmpty()) {
            homeLocationLayout.setError("Home Location is required");
            homeLocationLayout.getEditText().requestFocus();
            Toast.makeText(getContext(), "Please Insert Home Location coordinates or upload a drawn map", Toast.LENGTH_SHORT).show();

            if (imm != null) {
                imm.showSoftInput(homeLocationLayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            homeLocationLayout.setError(null);
        }

        if (howYouKnewAboutPamhodziKidsFoundationLayout.getEditText().getText().toString().isEmpty()) {
            howYouKnewAboutPamhodziKidsFoundationLayout.setError("How You Knew About Pamodzi is required");
            howYouKnewAboutPamhodziKidsFoundationLayout.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(howYouKnewAboutPamhodziKidsFoundationLayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            howYouKnewAboutPamhodziKidsFoundationLayout.setError(null);
        }


        if (selectedLetterImageUri == null) {
            Toast.makeText(getContext(), "Please Upload Letter From Chief / Village Head", Toast.LENGTH_SHORT).show();
            return false;
        }


        if (evidenceOfNeedUriList.size() != 3) {
            Toast.makeText(getContext(), "Please Upload Evidence of Need Images", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }

    private void saveDataFinal() {

        int i = 0;

        evidenceOfNeedimagesList.clear();

        progressDialog.setMessage("Uploading Your Images ...");

        for (Uri uri : evidenceOfNeedUriList) {
            Log.e("URI List : ", evidenceOfNeedUriList.get(i).toString());
            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(uri.getLastPathSegment());
            UploadTask uploadTask = storageRef.putFile(uri);
            int finalI = i;

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads

                    save.setEnabled(true);
                    progressBar.setVisibility(View.GONE);
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Failed to Upload Image", Toast.LENGTH_SHORT).show();
                    Log.e("Failed to Upload Image", exception.getMessage());
                    // ...
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {

                    storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Log.e("URI : ", uri.toString());
                            evidenceOfNeedimagesList.add(uri.toString());
                            if (finalI == evidenceOfNeedUriList.size() - 1) {


                                StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(selectedLetterImageUri.getLastPathSegment());
                                UploadTask uploadTask = storageRef.putFile(selectedLetterImageUri);

                                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                Log.e("URI : ", uri.toString());
                                                txtImageLetter = uri.toString();

                                                if (txtImageMap.equals("Location Map Uploaded")) {

                                                    StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(selectedMapImageUri.getLastPathSegment());
                                                    UploadTask uploadTask = storageRef.putFile(selectedMapImageUri);

                                                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                        @Override
                                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                @Override
                                                                public void onSuccess(Uri uri) {
                                                                    Log.e("URI : ", uri.toString());
                                                                    txtImageMap = uri.toString();
                                                                    finishSaving();
                                                                }
                                                            });
                                                        }
                                                    });
                                                } else {
                                                    finishSaving();
                                                }


                                            }
                                        });
                                    }
                                });


                            }
                        }
                    });

                }
            });


            i++;
        }


    }

    private void finishSaving() {
        String id = uid;

        String FirstName = firstnameLayout1.getEditText().getText().toString();
        String LastName = lastnameLayout1.getEditText().getText().toString();

        String Photo = txtimageProfile;
        String CountryCodeName = CountryCode2.getSelectedCountryNameCode();
        String CountryCode = CountryCode2.getSelectedCountryCode();
        String CountryName = CountryCode2.getSelectedCountryName();

        String PhonePrefix = CountryCode2.getSelectedCountryCodeWithPlus();
        String IDNumber = idnumberLayout1.getEditText().getText().toString();
        String Address = addressLayout1.getEditText().getText().toString();
        String DOB = dobLayout1.getEditText().getText().toString();
        String Gender = gender1;

        String Disability = disabilityLayout1.getEditText().getText().toString();
        String Bio = bioLayout1.getEditText().getText().toString();
        String Reason = reasonLayout1.getEditText().getText().toString();
        String Education = educationLayout1.getEditText().getText().toString();
        String Status = "Pending";
        String ParentsAlive = parentsAlive;

        String GuardianFirstName = firstnameLayout2.getEditText().getText().toString();
        String GuardianLastName = lastnameLayout2.getEditText().getText().toString();
        String PhonePrefix2 = CountryCode2.getSelectedCountryCodeWithPlus();
        String GuardianEmail = emailLayout2.getEditText().getText().toString();
        String GuardianMobileNumber = PhonePrefix2 + mobilenumberLayout2.getEditText().getText().toString().trim();
        String Occupation2 = occupationLayout2.getEditText().getText().toString();
        String Relationship = relatioshipLayout.getEditText().getText().toString();
        String DOB2 = dobLayout2.getEditText().getText().toString();
        String Gender2 = gender2;
        String Disability2 = disabilityLayout2.getEditText().getText().toString();


        String moreGuardianOrParentInfo = moreGuardianOrParentInfoLayout.getEditText().getText().toString();
        String currentFamilySituation = currentFamilySituationLayout.getEditText().getText().toString();
        String sourceOfIncome = sourceOfIncomeLayout.getEditText().getText().toString();
        String averageMonthlyIncome = averageMonthlyIncomeLayout.getEditText().getText().toString();
        String typeOfHouse = typeOfHouseLayout.getEditText().getText().toString();
        String numberOfPeopleUnder18 = numberOfPeopleUnder18Layout.getEditText().getText().toString();
        String numberOfPeopleInHouse = numberOfPeopleInHouseLayout.getEditText().getText().toString();
        String typeOfLight = typeOfLightLayout.getEditText().getText().toString();
        String typeOfCookingEnergy = typeOfCookingEnergyLayout.getEditText().getText().toString();
        String typeOfToilet = typeOfToiletLayout.getEditText().getText().toString();
        String typeOfWaterSource = typeOfWaterSourceLayout.getEditText().getText().toString();
        String distanceFromWaterSource = distanceFromWaterSourceLayout.getEditText().getText().toString();
        String typeOfFloor = typeOfFloorLayout.getEditText().getText().toString();
        String numberOfSiblings = numberOfSiblingsLayout.getEditText().getText().toString();
        String numberOfSiblingsInSchool = numberOfSiblingsInSchoolLayout.getEditText().getText().toString();
        String financialChallenges = financialChallengesLayout.getEditText().getText().toString();
        String circumstancesAffectingSchooling = circumstancesAffectingSchoolingLayout.getEditText().getText().toString();
        String missedSchoolDueToLackOfResources = missedSchoolDueToLackOfResourcesLayout.getEditText().getText().toString();
        String numberOfMealsPerDay = numberOfMealsPerDayLayout.getEditText().getText().toString();
        String responsibilitiesAtHome = responsibilitiesAtHomeLayout.getEditText().getText().toString();
        String academicGoals = academicGoalsLayout.getEditText().getText().toString();
        String planForPloughBackToCommunity = planForPloughBackToCommunityLayout.getEditText().getText().toString();
        String howYouKnewAboutPamhodziKidsFoundation = howYouKnewAboutPamhodziKidsFoundationLayout.getEditText().getText().toString();
        String howSponsorshipHelpedYou = howSponsorshipHelpedYouLayout.getEditText().getText().toString();
        String howYouFeltWhenSponsorshipStopped = howYouFeltWhenSponsorshipStoppedLayout.getEditText().getText().toString();
        String nameOfSponsor = nameOfSponsorLayout.getEditText().getText().toString();
        String typeOfSponsorship = typeOfSponsorshipLayout.getEditText().getText().toString();
        String durationOfSponsorship = durationOfSponsorshipLayout.getEditText().getText().toString();
        String reasonWhySponsorshipStopped = reasonWhySponsorshipStoppedLayout.getEditText().getText().toString();
        String homeLocation = txtImageMap;
        String havereceivedSponsorshipBefore = receivedSponsorshipBefore;


        List<String> myImagesUrl = myimagesList;


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());


        SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String currentTime = timeformat.format(new Date());




        ModelSponsee modelSponsee2 = new ModelSponsee(
                "" + uid,
                "" + FirstName,
                "" + LastName,
                "",
                "",
                "" + txtimageProfile,
                "" + CountryCodeName,
                "" + CountryName,
                "",
                "" + PhonePrefix,
                "" + IDNumber,
                "" + Address,
                "" + DOB,
                "" + Gender,
                "",
                "" + Disability,
                "" + Bio,
                "" + Reason,
                "" + Education,
                "" + Status,
                "" + ParentsAlive,
                "" + GuardianFirstName,
                "" + GuardianLastName,
                "" + GuardianEmail,
                "" + GuardianMobileNumber,
                "" + Occupation2,
                "" + DOB2,
                "" + Gender2,
                "" + Disability2,
                "" + Relationship,
                "" + currentDate,
                "" + currentTime,
                "" + moreGuardianOrParentInfo,
                "" + currentFamilySituation,
                "" + sourceOfIncome,
                "" + averageMonthlyIncome,
                "" + typeOfHouse,
                "" + numberOfPeopleInHouse,
                "" + numberOfPeopleUnder18,
                "" + typeOfLight,
                "" + typeOfCookingEnergy,
                "" + typeOfWaterSource,
                "" + distanceFromWaterSource,
                "" + typeOfToilet,
                "" + typeOfFloor,
                "" + numberOfSiblings,
                "" + numberOfSiblingsInSchool,
                "" + financialChallenges,
                "" + circumstancesAffectingSchooling,
                "" + missedSchoolDueToLackOfResources,
                "" + numberOfMealsPerDay,
                "" + responsibilitiesAtHome,
                "" + academicGoals,
                "" + havereceivedSponsorshipBefore,
                "" + nameOfSponsor,
                "" + typeOfSponsorship,
                "" + durationOfSponsorship,
                "" + reasonWhySponsorshipStopped,
                "" + howSponsorshipHelpedYou,
                "" + howYouFeltWhenSponsorshipStopped,
                "" + planForPloughBackToCommunity,
                evidenceOfNeedimagesList,
                "" + homeLocation,
                "" + txtImageLetter,
                "",
                "" + howYouKnewAboutPamhodziKidsFoundation,
                myImagesUrl
        );


        progressDialog.setMessage("Saving Data...");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Sponsee");
        ref.child(uid).setValue(modelSponsee2).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                //Sponsee Notification

                String message="Dear "+FirstName+" "+LastName+",\n\n"+"Your application has been received and is being processed. You will be notified of the outcome in due course.\n\n"+"Regards,\n"+"Pamodzi Child Africa";

                String subject="Application for Sponsorship Received";


                SendMailTask smt = new SendMailTask(GuardianEmail,subject,message);
                smt.execute();


                //Admin Notification
                String message2="<html><body><h3>Dear Admin,</h3><br><br>"+"A new application for sponsorship has been received. Please login to the admin portal to view the application.<br><br>"+
                       "<b>Guardian Details</b><br>"+
                        " Name: "+GuardianFirstName+" "+GuardianLastName+"<br>"+
                        " Email: "+GuardianEmail+"<br>"+
                        " Phone Number: "+GuardianMobileNumber+"<br>"+
                        " ID Number: "+IDNumber+"<br>"+
                        " Address: "+Address+"<br><br>"+

                        "<b>Child Details</b><br>"+
                        "<b>Name: </b>"+FirstName+" "+LastName+"\t\t\t"+
                        "<b>     Education Level </b>: "+Education +"<br>"+
                        "<b>Parents Alive :</b> "+ParentsAlive +"\t\t\t"+
                        "<b>     Guardian Relationship</b> : "+Relationship +"<br>"+
                        "<b>Disability : </b>"+Disability +"\t\t\t"+
                        "<b>     Date Applied : </b>"+currentDate +"<br>"+
                        "<b>Time Applied : </b>"+currentTime +"\t\t\t"+
                        "<b>     More Guardian or Parent Info :</b> "+moreGuardianOrParentInfo +"<br>"+
                        "<b>Current Family Situation </b>: "+currentFamilySituation +"\t\t\t"+
                        "<b>     Source of Income : </b>"+sourceOfIncome +"<br>"+
                        "<b>Average Monthly Income </b>: "+averageMonthlyIncome +"\t\t\t"+
                        "<b>     Type of House : </b>"+typeOfHouse +"<br>"+
                        "<b>Number of People in House :</b> "+numberOfPeopleInHouse +"\t\t\t"+
                        "<b>     Number of People Under 18 : </b>"+numberOfPeopleUnder18 +"<br>"+
                        "<b>Type of Light : </b>"+typeOfLight +"\t\t\t"+
                        "<b>     Type of Cooking Energy :</b> "+typeOfCookingEnergy +"<br>"+
                        "<b>Type of Water Source : </b>"+typeOfWaterSource +"\t\t\t"+
                        "<b>     Distance from Water Source : </b>"+distanceFromWaterSource +"<br>"+
                        "<b>Type of Toilet :</b> "+typeOfToilet +"\t\t\t"+
                        "<b>     Type of Floor : </b>"+typeOfFloor +"<br>"+
                        "<b>Number of Siblings : </b>"+numberOfSiblings +"\t\t\t"+
                        "<b>     Number of Siblings in School :</b> "+numberOfSiblingsInSchool +"<br>"+
                        "<b>Financial Challenges : </b>"+financialChallenges +"\t\t\t"+
                        "<b>     Circumstances Affecting Schooling :</b> "+circumstancesAffectingSchooling +"<br>"+
                        "<b>Missed School Due to Lack of Resources : </b>"+missedSchoolDueToLackOfResources +"\t\t\t"+
                        "<b>     Number of Meals Per Day :</b> "+numberOfMealsPerDay +"<br>"+
                        "<b>Responsibilities at Home : </b>"+responsibilitiesAtHome +"\t\t\t"+
                        "<b>     Academic Goals : </b>"+academicGoals +"<br>"+
                        "<b>Have received Sponsorship Before : </b>"+havereceivedSponsorshipBefore +"<br><br>"+

                        "<b>For More infor about this application, go to the application or visit the Parent or Guardian stated at the top </b><br>"+
                        "Regards,<br>"+"<b>Pamodzi Child Africa</b></body></html>";







                String subject2="New Application for Sponsorship Received";

                SendMailTask smt2 = new SendMailTask("damarisaswa12@gmail.com",subject2,message2);
                smt2.execute();






                progressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Successful");
                builder.setMessage("Application Submitted Successfully");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        progressDialog.dismiss();
                        progressBar.setVisibility(View.GONE);
                        save.setEnabled(true);

                        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new Sponsorship()).addToBackStack(null).commit();


                    }
                });
                builder.show();
            }
        });


    }


    // Get the file input element and listen for changes
    private void handleFileUpload() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean allGranted = true;
            for (String permission : PERMISSIONS) {
                if (ContextCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    break;
                }
            }
            if (allGranted) {
                showImagePickerDialog();
            } else {
                requestPermissions(PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            }
        } else {
            showImagePickerDialog();
        }
    }


    private void showImagePickerDialog() {


        dialog1.setContentView(R.layout.image_picker_dialog);
        dialog1.setCancelable(true);
        dialog1.setCanceledOnTouchOutside(true);
        dialog1.getWindow().setLayout(900, 1100);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog1.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        dialog1.getWindow().setAttributes(lp);

        dialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog1.findViewById(R.id.gallery_button).setOnClickListener(v1 -> {

            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, GALLERY_REQUEST_CODE);

        });

        dialog1.findViewById(R.id.camera_button).setOnClickListener(v1 -> {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        });


        dialog1.show();


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            boolean allGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    break;
                }
            }
            if (allGranted) {
                showImagePickerDialog();
            } else {
                Toast.makeText(getContext(), "Permissions not granted", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }


        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, get current location
                getLocation();
            } else {
                // Permission denied
                Log.e(TAG, "Location permission denied");
            }
        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {


            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Processing  !!!");
            progressDialog.setTitle("Please wait");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();


            //progressBar.setVisibility(View.VISIBLE);
            //  save.setEnabled(false);


            switch (requestCode) {
                case GALLERY_REQUEST_CODE:
                    if (data != null) {
                        //Toast.makeText(getContext(), "Uploading Your Image , Please wait !!!", Toast.LENGTH_LONG).show();
                        selectedImageUri = data.getData();
                        usingGallery = true;
                        usingCamera = false;

                        // Handle the selected image from gallery
                        dialog1.dismiss();
                        profileImage.setImageURI(selectedImageUri);

                        progressDialog.dismiss();


                        //  Log.e("Image : ", selectedImageUri.toString());


                    }
                    break;
                case CAMERA_REQUEST_CODE:
                    if (data != null) {
                        // Toast.makeText(getContext(), "Uploading Your Image , Please wait !!!", Toast.LENGTH_LONG).show();
                        photo = (Bitmap) data.getExtras().get("data");
                        // Handle the captured photo from camera
                        dialog1.dismiss();

                        profileImage.setImageBitmap(photo);
                        profileImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

                        usingCamera = true;
                        usingGallery = false;

                        progressDialog.dismiss();


                        // Log.e("Image", photo.toString());
                        // Log.e("Image", textimageOfProof);
                    }
                    break;

                case PICK_IMAGE_REQUEST:

                    if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
                        // Get the files from the intent
                        myimagesUriList.clear();
                        myimagesUriList = new ArrayList<>();
                        if (data.getClipData() != null) {
                            // Multiple images selected
                            int count = data.getClipData().getItemCount();
                            if (count > MAX_IMAGES) {
                                // Too many images selected, show an error message
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "Failed : You can upload only a maximum of " + MAX_IMAGES + " images ", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                save.setEnabled(true);
                                return;
                            }


                            // Toast.makeText(getContext(), "Uploading Your Image(s) , Please wait !!!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.VISIBLE);
                            for (int i = 0; i < count; i++) {
                                Uri uri = data.getClipData().getItemAt(i).getUri();
                                myimagesUriList.add(uri);

                                InputStream inputStream = null;
                                try {
                                    inputStream = getActivity().getContentResolver().openInputStream(uri);
                                } catch (FileNotFoundException e) {
                                    throw new RuntimeException(e);
                                }
                                int fileSize = 0;
                                try {
                                    fileSize = inputStream.available();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                if (fileSize > MAX_FILE_SIZE) {
                                    // File size is too large, show an error message
                                    Toast.makeText(getContext(), "Error : Image " + i + " size exceeded 5MB", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    progressBar.setVisibility(View.GONE);
                                    save.setEnabled(true);
                                    return;

                                }

                            }


                        } else if (data.getData() != null) {
                            // Single image selected
                            Toast.makeText(getContext(), "Uploading Your Image , Please wait !!!", Toast.LENGTH_LONG).show();
                            Uri uri = data.getData();
                            myimagesUriList.add(uri);
                        }

                        relativeLayout1.setVisibility(View.GONE);
                        relativeLayout2.setVisibility(View.GONE);
                        relativeLayout3.setVisibility(View.GONE);

                        progressBar.setVisibility(View.VISIBLE);
                        // Loop through each file and upload it to Firebase Storage
                        int i = 0;
                        for (Uri uri : myimagesUriList) {
                            i++;

                            if (i == 1) {
                                relativeLayout1.setVisibility(View.VISIBLE);
                                imageView1.setImageURI(uri);
                            } else if (i == 2) {
                                relativeLayout2.setVisibility(View.VISIBLE);
                                imageView2.setImageURI(uri);
                            } else if (i == 3) {
                                relativeLayout3.setVisibility(View.VISIBLE);
                                imageView3.setImageURI(uri);
                            }

                            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(uri.getLastPathSegment());
                            UploadTask uploadTask = storageRef.putFile(uri);

                            // Listen for upload completion
                            int finalI = i;

                        }

                        progressDialog.dismiss();
                        save.setEnabled(true);
                        //Log.e(TAG, "Images uploaded Successfully");
                        Log.e("List Size", String.valueOf(myimagesUriList.size()));
                        progressBar.setVisibility(View.GONE);
                    }
                    break;

                case PICK_IMAGE_REQUEST2:
                    if (requestCode == PICK_IMAGE_REQUEST2 && resultCode == RESULT_OK && data != null) {
                        // Get the files from the intent
                        evidenceOfNeedUriList.clear();
                        evidenceOfNeedUriList = new ArrayList<>();
                        if (data.getClipData() != null) {
                            // Multiple images selected
                            int count = data.getClipData().getItemCount();
                            if (count > MAX_IMAGES) {
                                // Too many images selected, show an error message
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "Failed : You can upload only a maximum of " + MAX_IMAGES + " images ", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                save.setEnabled(true);
                                return;
                            } else if (count < MAX_IMAGES) {
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "Failed : " + MAX_IMAGES + " images of evidence are required", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                save.setEnabled(true);
                                return;
                            }


                            // Toast.makeText(getContext(), "Uploading Your Image(s) , Please wait !!!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.VISIBLE);
                            for (int i = 0; i < count; i++) {
                                Uri uri = data.getClipData().getItemAt(i).getUri();
                                evidenceOfNeedUriList.add(uri);

                                InputStream inputStream = null;
                                try {
                                    inputStream = getActivity().getContentResolver().openInputStream(uri);
                                } catch (FileNotFoundException e) {
                                    throw new RuntimeException(e);
                                }
                                int fileSize = 0;
                                try {
                                    fileSize = inputStream.available();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                if (fileSize > MAX_FILE_SIZE) {
                                    // File size is too large, show an error message
                                    Toast.makeText(getContext(), "Error : Image " + i + " size exceeded 4MB", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    progressBar.setVisibility(View.GONE);
                                    save.setEnabled(true);
                                    return;

                                }

                            }


                        }

                        relativeEvidenceLayout1.setVisibility(View.GONE);
                        relativeEvidenceLayout1.setVisibility(View.GONE);
                        relativeEvidenceLayout1.setVisibility(View.GONE);

                        progressBar.setVisibility(View.VISIBLE);
                        // Loop through each file and upload it to Firebase Storage
                        int i = 0;
                        for (Uri uri : evidenceOfNeedUriList) {
                            i++;

                            if (i == 1) {
                                relativeEvidenceLayout1.setVisibility(View.VISIBLE);
                                evidenceImageView1.setImageURI(uri);
                            } else if (i == 2) {
                                relativeEvidenceLayout2.setVisibility(View.VISIBLE);
                                evidenceImageView2.setImageURI(uri);
                            } else if (i == 3) {
                                relativeEvidenceLayout3.setVisibility(View.VISIBLE);
                                evidenceImageView3.setImageURI(uri);
                            }

                            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(uri.getLastPathSegment());
                            UploadTask uploadTask = storageRef.putFile(uri);

                            // Listen for upload completion
                            int finalI = i;

                        }

                        progressDialog.dismiss();
                        save.setEnabled(true);
                        //Log.e(TAG, "Images uploaded Successfully");
                        Log.e("List Size of evidence", String.valueOf(evidenceOfNeedUriList.size()));
                        progressBar.setVisibility(View.GONE);

                        imageViewEvidenceDot.setImageDrawable(getResources().getDrawable(R.drawable.radio_button_checked_green));
                    }

                    break;

                case PICK_IMAGE_REQUEST3:
                    if (data != null) {
                        //Toast.makeText(getContext(), "Uploading Your Image , Please wait !!!", Toast.LENGTH_LONG).show();
                        relativeLetterLayout.setVisibility(View.VISIBLE);
                        selectedLetterImageUri = data.getData();
                        //set to green color
                        imageViewLetterdot.setImageDrawable(getResources().getDrawable(R.drawable.radio_button_checked_green));
                        // Handle the selected image from gallery
                        dialog1.dismiss();
                        letterImageView.setImageURI(selectedLetterImageUri);

                        progressDialog.dismiss();
                        //  Log.e("Image : ", selectedImageUri.toString());


                    }
                    break;

                case PICK_IMAGE_REQUEST4:
                    if (data != null) {
                        //Toast.makeText(getContext(), "Uploading Your Image , Please wait !!!", Toast.LENGTH_LONG).show();
                        // relativeLetterLayout.setVisibility(View.VISIBLE);
                        selectedMapImageUri = data.getData();
                        //set to green color
                        imageViewLocationMapDot.setImageDrawable(getResources().getDrawable(R.drawable.radio_button_checked_green));
                        // Handle the selected image from gallery
                        dialog1.dismiss();
                        // letterImageView.setImageURI(selectedLetterImageUri);

                        progressDialog.dismiss();
                        //  Log.e("Image : ", selectedImageUri.toString());
                        homeLocationLayout.getEditText().setText("Location Map Uploaded");
                        txtImageMap = "Location Map Uploaded";
                      //  webView.setVisibility(View.GONE);


                    }
                    break;


            }
        }
    }


    public void uploadFromCamera() {
        // Convert the photo to a byte array
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] photoBytes = byteArrayOutputStream.toByteArray();

        // Encode the byte array as a base64 string
        //textimageOfProof = Base64.encodeToString(photoBytes, Base64.DEFAULT);


        storageReference = FirebaseStorage.getInstance().getReference().
                child(uid).child(System.currentTimeMillis() + ".png");

        // Upload the image data to Firebase Storage
        UploadTask uploadTask = storageReference.putBytes(photoBytes);

        // Listen for upload progress and completion
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Image upload successful
                // Get the download URL of the uploaded image
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Image download URL obtained
                        txtimageProfile = uri.toString();

                        Log.e("Image URL", txtimageProfile);
                        continueSavingdata();


                        // Do something with the download URL, such as storing it in a database
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Image upload failed
                // Handle the error

                Log.e("Error", e.getMessage());
                Toast.makeText(getContext(), "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();

                save.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                progressDialog.dismiss();
            }
        });


    }


    public void uploadFromGallery() {
        // Convert the selected image to a byte array
        InputStream inputStream = null;
        try {
            inputStream = getActivity().getContentResolver().openInputStream(selectedImageUri);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        byte[] imageBytes = new byte[0];
        try {
            imageBytes = IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        storageReference = FirebaseStorage.getInstance().getReference().
                child(uid).child(System.currentTimeMillis() + ".png");

        // Upload the image data to Firebase Storage
        UploadTask uploadTask = storageReference.putBytes(imageBytes);

        // Listen for upload progress and completion
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Image upload successful
                // Get the download URL of the uploaded image
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Image download URL obtained
                        txtimageProfile = uri.toString();

                        Log.e("Image URL", txtimageProfile);
                        continueSavingdata();

                        // Do something with the download URL, such as storing it in a database
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Image upload failed

                // Handle the error

                Log.e("Error", e.getMessage());
                Toast.makeText(getContext(), "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();

                save.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                progressDialog.dismiss();
            }
        });


    }


    public void uploadEvidenceFromGallery() {
        // Convert the selected image to a byte array
        InputStream inputStream = null;
        try {
            inputStream = getActivity().getContentResolver().openInputStream(selectedImageUri);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        byte[] imageBytes = new byte[0];
        try {
            imageBytes = IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        storageReference = FirebaseStorage.getInstance().getReference().
                child(uid).child(System.currentTimeMillis() + ".png");

        // Upload the image data to Firebase Storage
        UploadTask uploadTask = storageReference.putBytes(imageBytes);

        // Listen for upload progress and completion
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Image upload successful
                // Get the download URL of the uploaded image
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Image download URL obtained
                        txtimageProfile = uri.toString();
                        progressBar.setVisibility(View.GONE);
                        save.setEnabled(true);


                        Log.e("Image URL", txtimageProfile);
                        continueSavingdata();

                        // Do something with the download URL, such as storing it in a database
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Image upload failed

                // Handle the error

                Log.e("Error", e.getMessage());
                Toast.makeText(getContext(), "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

}