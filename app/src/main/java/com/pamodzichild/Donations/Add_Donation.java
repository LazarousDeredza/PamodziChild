package com.pamodzichild.Donations;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
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
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pamodzichild.MainActivity;
import com.pamodzichild.R;
import com.pamodzichild.SendEmail.SendMailTask;
import com.pamodzichild.Users.UserModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hbb20.CountryCodePicker;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;


public class Add_Donation extends Fragment {

    View v;
    Dialog dialog1;

    TextInputLayout firstnamelayout, lastnamelayout, emailLayout, locationlayout, doblayout, mobilenumberlayout, idNumberLayout,
            occupationlayout, productdescriptionlayout, productNamelayout;


    RadioGroup radioGenderGroup;

    CountryCodePicker countryCode;

    Spinner donationtypeSpinner;
    Button proofbutton;
    TextView quantity;

    ImageView proofimage, calender;

    FloatingActionButton adddonationfab;
    DatabaseReference userRef, donorsRef, donationsRef;
    StorageReference storageReference;
    FirebaseAuth mAuth;
    String uid;
    ProgressBar progressBar;

    CircleImageView increase, decrease;

    String gender;
    String textimageOfProof;
    String dob;
    RadioButton radioMale, radioFemale;
    boolean donorAlreadyExist = false;
    private static final int GALLERY_REQUEST_CODE = 100;
    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int PERMISSIONS_REQUEST_CODE = 300;

    private String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    private ImageSwitcher imageSwitcher;

    public Add_Donation() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_add__donation, container, false);
        MainActivity.flag = 1;


        getActivity().setTitle("Make a Donation");

        firstnamelayout = v.findViewById(R.id.firstnamelayout);
        lastnamelayout = v.findViewById(R.id.lastnamelayout);
        emailLayout = v.findViewById(R.id.emailLayout);
        locationlayout = v.findViewById(R.id.locationlayout);
        doblayout = v.findViewById(R.id.doblayout);
        mobilenumberlayout = v.findViewById(R.id.mobilenumberlayout);
        occupationlayout = v.findViewById(R.id.occupationlayout);
        productdescriptionlayout = v.findViewById(R.id.productdescriptionlayout);
        idNumberLayout = v.findViewById(R.id.idNumberLayout);
        productNamelayout = v.findViewById(R.id.productNamelayout);

        radioGenderGroup = v.findViewById(R.id.radioGenderGroup);

        countryCode = v.findViewById(R.id.CountryCode);

        donationtypeSpinner = v.findViewById(R.id.donationtypeSpinner);

        proofbutton = v.findViewById(R.id.proofbutton);

        proofimage = v.findViewById(R.id.proofimage);
        //proofimage.setVisibility(View.INVISIBLE);

        adddonationfab = v.findViewById(R.id.adddonationfab);

        gender = "";

        donorsRef = FirebaseDatabase.getInstance().getReference().child("Donors");
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        donationsRef = FirebaseDatabase.getInstance().getReference().child("Donations");

        storageReference = FirebaseStorage.getInstance().getReference().child("DonationProofs");

        dob = "";
        textimageOfProof = "";
        calender = v.findViewById(R.id.calender);

        dialog1 = new Dialog(getContext());

        increase = v.findViewById(R.id.increase);
        decrease = v.findViewById(R.id.decrease);

        quantity = v.findViewById(R.id.quantity);

        mAuth = FirebaseAuth.getInstance();

        uid = mAuth.getCurrentUser().getUid();
        radioMale = v.findViewById(R.id.radioMale);
        radioFemale = v.findViewById(R.id.radioFemale);

        progressBar = v.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        MainActivity.fab.hide();
        // textimageOfProof = "";
        //proofimage.setVisibility(View.INVISIBLE);


        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity1 = Integer.parseInt(quantity.getText().toString());
                quantity1++;
                quantity.setText(String.valueOf(quantity1));
            }
        });

        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity1 = Integer.parseInt(quantity.getText().toString());
                if (quantity1 > 1) {
                    quantity1--;
                    quantity.setText(String.valueOf(quantity1));
                }
            }
        });


        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int currentYear = calendar.get(Calendar.YEAR);
                int currentMonth = calendar.get(Calendar.MONTH);
                int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {

                        doblayout.getEditText().setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
                        dob = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;

                        Log.e("Date : ", selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);

                    }
                }, currentYear, currentMonth, currentDay);

                datePickerDialog.show();
            }
        });



        radioGenderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton btn = v.findViewById(i);
                gender = btn.getText().toString();

                Log.e("Selected gender : ", gender);
            }
        });


        proofbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkPermissions();

            }
        });
        proofimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkPermissions();

            }
        });


        adddonationfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String firstname = firstnamelayout.getEditText().getText().toString();
                String lastname = lastnamelayout.getEditText().getText().toString();
                String email = emailLayout.getEditText().getText().toString();
                String location = locationlayout.getEditText().getText().toString();
                String dob = doblayout.getEditText().getText().toString();
                String mobilenumber = mobilenumberlayout.getEditText().getText().toString();
                String occupation = occupationlayout.getEditText().getText().toString();
                String countrycode = countryCode.getSelectedCountryCodeWithPlus();
                String country = countryCode.getSelectedCountryName();
                String countryCodeName = countryCode.getSelectedCountryNameCode();
                String Gender = gender;
                String idnumber = idNumberLayout.getEditText().getText().toString();


                String productname = productNamelayout.getEditText().getText().toString();
                String quantity1 = quantity.getText().toString();
                String productdescription = productdescriptionlayout.getEditText().getText().toString();
                String donationType = donationtypeSpinner.getSelectedItem().toString();


                validate();

                if (validate()) {


                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    String currentDate = dateFormat.format(new Date());


                    SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                    String currentTime = timeformat.format(new Date());


                    ModelDonor modelDonor = new ModelDonor(
                            "" + firstname,
                            "" + lastname,
                            "" + email,
                            "" + countrycode + mobilenumber,
                            "",
                            "" + countryCodeName,
                            "" + country,
                            "" + mobilenumber,
                            "" + countrycode,
                            "" + idnumber,
                            "" + location,
                            "" + dob,
                            "" + Gender,
                            "" + occupation,
                            ""+uid
                    );

                    String key = donationsRef.push().getKey();

                    ModelDonation donation = new ModelDonation(
                            "" + uid,
                            "" + donationType,
                            "" + productname,
                            "" + productdescription,
                            "" + currentDate,
                            "" + currentTime,
                            "" + quantity1,
                            "" + textimageOfProof,
                            "" + "Pending Approval"


                    );

                    donation.setDonationId(key);
                  //  donation.setSeeker("");




                    ProgressDialog progressDialog = new ProgressDialog(getContext());
                    progressDialog.setTitle("Please wait");
                    progressDialog.setMessage("Submitting your donation for review");
                    progressDialog.setCancelable(false);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();


                    donorsRef.child(uid).setValue(modelDonor).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            donationsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    int count = 1;

                                    if (snapshot.child(uid).exists()) {
                                        count = (int) snapshot.child(uid).getChildrenCount() + 1;
                                    }

                                    donationsRef.child(uid).child("Donation " + count).setValue(donation).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {

                                            String message = "<html><body><p>Hello " + modelDonor.getFirstname() + " " + modelDonor.getLastname() + ",</p>\n\n" +
                                                    "<p>Thank you for your donation. We will review your donation and notify you once it is approved.</p>\n\n" +
                                                    "<p></p>" +
                                                    "<p></p>" +
                                                    "<p></p>" +
                                                    "<p></p>" +
                                                    "<p>Regards,</p>\n" +
                                                    "<p>Team Pamodzi Child Africa</p>";


                                            SendMailTask sendMailTask = new SendMailTask(modelDonor.getEmail(), "Your Donation is being reviewed", message, requireContext());
                                            sendMailTask.execute();





                                            String message2 = "<html><body><p>Hello Pamodzi Child Admin,</p>" +
                                                    "<p>A new donation has been submitted for review. Please review it and approve it if it meets the requirements.</p>" +
                                                    "<p>Doonation ID : <b>" + key + "</b> (you can use it to filter the from donation list in the application) </p>" +
                                                    "<p><b>Donor :</b> " + firstname+ " " + lastname + "</p>" +
                                                    "<p><b>Location :</b> " + location + "</p>" +
                                                    "<p><b>Donation Type :</b> " + donationType + "\t" +
                                                    "<b>       Product Name :</b> " + productname + " \t" +
                                                    "<b>       Quantity :</b> " + quantity1 + "</p>" +
                                                    "<p><b>Product Description :</b> " + productdescription + "</p>" +
                                                    "<p><b>Date : </b> " + currentDate + "\t<b>    Time :</b> " + currentTime + "</p>" +
                                                    "<p><b>Contact Details :</b></p>" +
                                                    "<p><b>      Phone :</b> " + countrycode + mobilenumber + "</p>" +
                                                    "<p><b>      Email :</b> " + email + "</p>" +
                                                    "<p><b>Image of Proof :</b> </p> <img src=\""+textimageOfProof+"\" alt=\"Image Not Found\" width=\"350px\" height=\"400px\">" +
                                                    "<p>Regards,<br>Team Pamodzi Child Africa</p></body></html>";


                                           // damarisaswa12@gmail.com


                                            SendMailTask sendMailTask2 = new SendMailTask("damarisaswa12@gmail.com", "New Donation Arrived", message2, requireContext(), "text/html");
                                            sendMailTask2.execute();


                                            progressDialog.dismiss();


                                            AlertDialog alertDialog = new AlertDialog.Builder(Objects.requireNonNull(requireContext())).create();
                                            alertDialog.setTitle("Success : Thank you");
                                            alertDialog.setMessage("Your donation has been submitted successfully." +
                                                    " You will be notified once it is verified and ready to be donated.");
                                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.dismiss();
                                                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new MyDonations()).addToBackStack(null).commit();
                                                }
                                            });
                                            alertDialog.show();


                                        }
                                    });


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getContext(), "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });


                        }
                    });


                }


            }
        });


        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Preparing...");
        progressDialog.setTitle("Please wait");
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String currentUserID = mAuth.getCurrentUser().getUid();

        donorsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(currentUserID).exists()) {
                    //Toast.makeText(getContext(), "You have already donated", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    donorAlreadyExist = true;

                    ModelDonor modelDonors = snapshot.child(currentUserID).getValue(ModelDonor.class);

                    firstnamelayout.getEditText().setText(modelDonors.getFirstname());
                    lastnamelayout.getEditText().setText(modelDonors.getLastname());
                    emailLayout.getEditText().setText(modelDonors.getEmail());
                    locationlayout.getEditText().setText(modelDonors.getAddress());
                    doblayout.getEditText().setText(modelDonors.getDob());
                    mobilenumberlayout.getEditText().setText(modelDonors.getPhoneSuffix());
                    occupationlayout.getEditText().setText(modelDonors.getOccupation());
                    idNumberLayout.getEditText().setText(modelDonors.getIdNumberOrPassport());
                    gender = modelDonors.getGender();

                    if (gender.equals("Male")) {
                        radioMale.setChecked(true);
                        radioFemale.setChecked(false);

                    } else if (gender.equals("Female")) {
                        radioFemale.setChecked(true);
                        radioMale.setChecked(false);
                    }


                    countryCode.setCountryForNameCode(modelDonors.getCountryCodeName());


                } else {
                    donorAlreadyExist = false;
                    progressDialog.dismiss();
                    //Toast.makeText(getContext(), "You have not donated Before", Toast.LENGTH_SHORT).show();

                    userRef.child(currentUserID).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.exists()) {
                                UserModel modelUsers = dataSnapshot.getValue(UserModel.class);

                                firstnamelayout.getEditText().setText(modelUsers.getFname());
                                lastnamelayout.getEditText().setText(modelUsers.getLname());
                                emailLayout.getEditText().setText(modelUsers.getUser_email());
                                locationlayout.getEditText().setText(modelUsers.getLocaladdress());
                                mobilenumberlayout.getEditText().setText(modelUsers.getPhoneSuffix());

                                countryCode.setCountryForNameCode(modelUsers.getCountyCodeName());

                                progressDialog.dismiss();

                            }

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


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
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            progressBar.setVisibility(View.VISIBLE);
            adddonationfab.setEnabled(false);


            switch (requestCode) {
                case GALLERY_REQUEST_CODE:
                    if (data != null) {
                        Uri selectedImageUri = data.getData();
                        // Handle the selected image from gallery
                        dialog1.dismiss();
                        proofimage.setVisibility(View.VISIBLE);
                        proofimage.setImageURI(selectedImageUri);

                        //  Log.e("Image : ", selectedImageUri.toString());


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
                                        textimageOfProof = uri.toString();
                                        progressBar.setVisibility(View.GONE);
                                        adddonationfab.setEnabled(true);

                                        Log.e("Image URL", textimageOfProof);


                                        // Do something with the download URL, such as storing it in a database
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Image upload failed
                                // Handle the error
                            }
                        });


                    }
                    break;
                case CAMERA_REQUEST_CODE:
                    if (data != null) {
                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        // Handle the captured photo from camera
                        dialog1.dismiss();
                        proofimage.setVisibility(View.VISIBLE);
                        proofimage.setImageBitmap(photo);
                        proofimage.setScaleType(ImageView.ScaleType.CENTER_CROP);


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
                                        textimageOfProof = uri.toString();
                                        progressBar.setVisibility(View.GONE);
                                        adddonationfab.setEnabled(true);

                                        Log.e("Image URL", textimageOfProof);


                                        // Do something with the download URL, such as storing it in a database
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Image upload failed
                                // Handle the error
                            }
                        });


                        // Log.e("Image", photo.toString());
                        // Log.e("Image", textimageOfProof);
                    }
                    break;
            }
        }
    }


    private boolean validate() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        if (firstnamelayout.getEditText().getText().toString().isEmpty()) {
            firstnamelayout.setError("First Name is required");
            firstnamelayout.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(firstnamelayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            firstnamelayout.setError(null);
        }


        if (lastnamelayout.getEditText().getText().toString().isEmpty()) {
            lastnamelayout.setError("Last Name is required");
            lastnamelayout.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(lastnamelayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            lastnamelayout.setError(null);
        }


        if (emailLayout.getEditText().getText().toString().isEmpty()) {
            emailLayout.setError("Email is required");
            emailLayout.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(emailLayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            emailLayout.setError(null);
        }


        if (idNumberLayout.getEditText().getText().toString().isEmpty()) {
            idNumberLayout.setError("ID Number is required");
            idNumberLayout.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(idNumberLayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            idNumberLayout.setError(null);
        }


        if (locationlayout.getEditText().getText().toString().isEmpty()) {
            locationlayout.setError("Location is required");
            locationlayout.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(locationlayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            locationlayout.setError(null);
        }


        if (doblayout.getEditText().getText().toString().isEmpty()) {
            doblayout.setError("Date of Birth is required");
            doblayout.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(doblayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            doblayout.setError(null);
        }

        if (gender.isEmpty()) {
            Toast.makeText(getContext(), "Select Gender", Toast.LENGTH_SHORT).show();
            return false;
        }


        if (mobilenumberlayout.getEditText().getText().toString().isEmpty()) {
            mobilenumberlayout.setError("Mobile Number is required");
            mobilenumberlayout.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(mobilenumberlayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            mobilenumberlayout.setError(null);
        }


        if (occupationlayout.getEditText().getText().toString().isEmpty()) {
            occupationlayout.setError("Occupation is required");
            occupationlayout.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(occupationlayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            occupationlayout.setError(null);
        }

        if (donationtypeSpinner.getSelectedItem().toString().equals("Select Type")) {
            Toast.makeText(getContext(), "Please select donation type", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(productNamelayout.getEditText().getText().toString())) {
            productNamelayout.setError("Product Name is required");
            productNamelayout.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(productNamelayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            productNamelayout.setError(null);
        }


        if (textimageOfProof.trim().isEmpty()) {
            Toast.makeText(getContext(), "Please Provide Proof of your product", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }
}