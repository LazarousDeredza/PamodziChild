package com.pamodzichild.Donations;

import static android.app.Activity.RESULT_OK;
import static com.pamodzichild.MainActivity.fab;

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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.squareup.picasso.Picasso;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class SeekDonation extends Fragment {

    View view;
    Dialog dialog1;

    TextInputLayout emailLayout, locationlayout, doblayout, mobilenumberlayout, idNumberLayout,
            occupationlayout,biolayout,educationlayout,reasonlayout;


    RadioGroup radioGenderGroup;

    CountryCodePicker countryCode;

    TextView txtName,txtSelectedProductName;

    ImageView  calender;

    FloatingActionButton adddonationfab;
    DatabaseReference userRef, doneesRef, donationsRef;
    StorageReference storageReference;
    FirebaseAuth mAuth;
    String uid;
    ProgressBar progressBar;

    CircleImageView profileImage;

    String gender;
    String textimageOfProof;
    String dob;
    RadioButton radioMale, radioFemale,radioOther;

    String donationId,donationName,donorId;
    String firstName, lastName;


    boolean donorAlreadyExist = false;
    private static final int GALLERY_REQUEST_CODE = 100;
    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int PERMISSIONS_REQUEST_CODE = 300;

    private String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    public SeekDonation() {
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
        view =inflater.inflate(R.layout.fragment_seek_donation, container, false);
        getActivity().setTitle("Seek Donations");
        MainActivity.flag = 1;

        dialog1 = new Dialog(getActivity());

        adddonationfab = view.findViewById(R.id.adddonationfab);

        emailLayout = view.findViewById(R.id.emailLayout);
        locationlayout = view.findViewById(R.id.locationlayout);
        doblayout = view.findViewById(R.id.doblayout);
        mobilenumberlayout = view.findViewById(R.id.mobilenumberlayout);
        idNumberLayout = view.findViewById(R.id.idNumberLayout);
        occupationlayout = view.findViewById(R.id.occupationlayout);
        biolayout = view.findViewById(R.id.biolayout);
        educationlayout = view.findViewById(R.id.educationlayout);
        reasonlayout = view.findViewById(R.id.reasonlayout);

        radioGenderGroup = view.findViewById(R.id.radioGenderGroup);

        countryCode = view.findViewById(R.id.countryCode);


        txtName = view.findViewById(R.id.txtName);
        txtSelectedProductName = view.findViewById(R.id.txtSelectedProductName);


        calender = view.findViewById(R.id.calender);

        profileImage = view.findViewById(R.id.profileImage);

        radioMale = view.findViewById(R.id.radioMale);
        radioFemale = view.findViewById(R.id.radioFemale);
        radioOther = view.findViewById(R.id.radioOther);

        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        gender = "";
        dob = "";
        textimageOfProof = "";
        firstName = "";
        lastName = "";


        doneesRef = FirebaseDatabase.getInstance().getReference().child("Donees");
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        donationsRef = FirebaseDatabase.getInstance().getReference().child("Donations");

        storageReference = FirebaseStorage.getInstance().getReference().child("profile_images");


        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            donationId = bundle.getString("donationId");
            donationName = bundle.getString("donationName");
            donorId = bundle.getString("donorId");
            txtSelectedProductName.setText(donationName);
        }



        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        fab.hide();

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
                RadioButton btn = view.findViewById(i);
                gender = btn.getText().toString();

                Log.e("Selected gender : ", gender);
            }
        });


        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkPermissions();

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

        doneesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(currentUserID).exists()) {
                    //Toast.makeText(getContext(), "You have already donated", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    donorAlreadyExist = true;

                    ModelDonor modelDonors = snapshot.child(currentUserID).getValue(ModelDonor.class);

                    txtName.setText(modelDonors.getFirstname()+" "+modelDonors.getLastname());

                    lastName = modelDonors.getLastname();
                    firstName = modelDonors.getFirstname();

                    textimageOfProof = modelDonors.getPhoto();

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
                        radioOther.setChecked(false);

                    } else if (gender.equals("Female")) {
                        radioFemale.setChecked(true);
                        radioMale.setChecked(false);
                        radioOther.setChecked(false);
                    }else {
                        radioFemale.setChecked(false);
                        radioMale.setChecked(false);
                        radioOther.setChecked(true);
                    }


                    countryCode.setCountryForNameCode(modelDonors.getCountryCodeName());



                    String imgProfile= modelDonors.getPhoto();
                    Picasso.get().load(imgProfile)
                            .placeholder(R.drawable.profileblank)
                            .error(R.drawable.profileblank)
                            .into(profileImage);






                } else {
                    donorAlreadyExist = false;
                    progressDialog.dismiss();
                    //Toast.makeText(getContext(), "You have not donated Before", Toast.LENGTH_SHORT).show();

                    userRef.child(currentUserID).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.exists()) {
                                UserModel modelUsers = dataSnapshot.getValue(UserModel.class);

                                txtName.setText(modelUsers.getFname() +" " +modelUsers.getLname());

                                lastName = modelUsers.getLname();
                                firstName = modelUsers.getFname();

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








        adddonationfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String firstname = firstName;
                String lastname = lastName;
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

                String bio = biolayout.getEditText().getText().toString();
                String reason = reasonlayout.getEditText().getText().toString();
                String education = educationlayout.getEditText().getText().toString();


                String productname = donationName;
                String donorID=donorId;
                String donationID=donationId;


                validate();

                if (validate()) {




                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    String currentDate = dateFormat.format(new Date());


                    SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                    String currentTime = timeformat.format(new Date());


                    ModelSeeker modelSeeker = new ModelSeeker(
                            "" + firstName,
                            "" + lastName,
                            "" + email,
                            "" + countrycode + mobilenumber,
                            ""+textimageOfProof,
                            "" + countryCodeName,
                            "" + country,
                            "" + mobilenumber,
                            "" + countrycode,
                            "" + idnumber,
                            "" + location,
                            "" + dob,
                            "" + Gender,
                            "" + occupation,
                            ""+uid,
                            ""+bio,
                            ""+reason,
                            ""+education,
                            ""+currentDate,
                            ""+currentTime
                    );










                    ProgressDialog progressDialog = new ProgressDialog(getContext());
                    progressDialog.setTitle("Please wait");
                    progressDialog.setMessage("Submitting your donation seek");
                    progressDialog.setCancelable(false);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();


                    doneesRef.child(uid).setValue(modelSeeker).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {


                            donationsRef.child(donorId).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String key="";

                                   for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                       key = dataSnapshot.getKey();

                                       ModelDonation modelDonation = dataSnapshot.getValue(ModelDonation.class);

                                       Log.d("TAG", "onDataChange: "+key);

                                        if (dataSnapshot.child("donationId").getValue().equals(donationId)) {

                                           // int seekersNumberCount=  snapshot.child("seeker").getValue();

                                            ArrayList<ModelSeeker> existingSeekers = new ArrayList<>();


                                            for(DataSnapshot dataSnapshot1 : dataSnapshot.child("seeker").getChildren()){

                                                ModelSeeker modelSeeker1 = dataSnapshot1.getValue(ModelSeeker.class);
                                                existingSeekers.add(modelSeeker1);

                                            }

                                            existingSeekers.add(modelSeeker);


                                            donationsRef.child(donorId).child(key).child("seeker").setValue(existingSeekers).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {


                                                    assert modelDonation != null;
                                                    String message2 = "<html><body><p>Hello Pamodzi Child Admin,</p>" +
                                                            "<p>There is a new Donee who just sought a donation, Please review.</p>\n\n" +
                                                            "<img src='"+textimageOfProof+"' style=\"margin-left: 80px\" width=\"150px\" height=\"150px\" alt=\" Donee Profile Picture\"/>" +
                                                            "<p><b>Donee Details </b><br>" +
                                                            "Name : <b>" + firstName + " " + lastName + "</b>      <br>     Email : <b>" + email + "</b><br>" +
                                                            "Phone : <b>" + countrycode + mobilenumber + "</b>          Location : <b>" + location + "</b><br>" +
                                                            "Education : <b>" + education + "</b><br>" +
                                                            "Occupation : <b>" + occupation + "</b><br><br>" +
                                                            "<b>Reason :</b> " + reason + "</b><br><br>" +
                                                            "Biography :<br>" +
                                                            "" + bio + "</p>" +


                                                            "<p><b>Donation Details </b><br>" +

                                                            "Donor User ID :  "+modelDonation.getDonatedby()+ "<br>" +
                                                            "Donation ID :  "+modelDonation.getDonationId()+ "<br>" +
                                                            "Donation Type :  "+modelDonation.getDonationType()+ "<br>" +
                                                            "Product Name :  "+modelDonation.getProductname()+ "<br>" +
                                                            "Quantity :  "+modelDonation.getQuantity()+ "<br>" +
                                                            "Product Description :  "+modelDonation.getProductDescription()+ "<br>" +
                                                            "Date :  "+modelDonation.getDate()+ "<br>" +
                                                            "Time :  "+modelDonation.getTime()+ "<br>" +
                                                            "Image of Product :  <br><br>"+
                                                            "<img src=\""+modelDonation.getImage()+"\" alt=\"Image Not Found\" width=\"350px\" height=\"400px\"> </p>" +

                                                            "<p>Regards,<br>Team Pamodzi Child Africa</p></body></html>";


                                                    // damarisaswa12@gmail.com


                                                    SendMailTask sendMailTask2 = new SendMailTask("damarisaswa12@gmail.com", "Donation Seek", message2, requireContext(), "text/html");
                                                    sendMailTask2.execute();


                                                    progressDialog.dismiss();
                                                   // Toast.makeText(getContext(), "Your donation seek has been submitted successfully", Toast.LENGTH_LONG).show();






                                                    AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                                                    alertDialog.setTitle("Donation Seek Submitted");
                                                    alertDialog.setCancelable(false);
                                                    alertDialog.setCanceledOnTouchOutside(false);
                                                    alertDialog.setMessage("Your donation seek has been submitted successfully. You will be notified when the management accepts your donation seek");
                                                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {

                                                            dialog.dismiss();
                                                            Fragment fragment = new Donations();
                                                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                            fragmentTransaction.replace(R.id.content_frame, fragment);
                                                            fragmentTransaction.addToBackStack(null);
                                                            fragmentTransaction.commit();

                                                        }
                                                    });

                                                    alertDialog.show();


                                                }
                                            });

                                            break;
                                        }

                                      }




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
                        profileImage.setImageURI(selectedImageUri);

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

                        profileImage.setImageBitmap(photo);
                        profileImage.setScaleType(ImageView.ScaleType.CENTER_CROP);


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

       if(textimageOfProof.isEmpty()){
            Toast.makeText(getContext(), "Please upload a profile picture by clicking top image  in this form", Toast.LENGTH_SHORT).show();
            return false;
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


        if (reasonlayout.getEditText().getText().toString().isEmpty()) {
            reasonlayout.setError("Reason is required");
            reasonlayout.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(reasonlayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            reasonlayout.setError(null);
        }



        if (TextUtils.isEmpty(biolayout.getEditText().getText().toString())) {
            biolayout.setError("Bio is required");
            biolayout.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(biolayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            biolayout.setError(null);
        }





        return true;
    }

}