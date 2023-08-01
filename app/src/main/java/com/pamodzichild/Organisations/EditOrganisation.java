package com.pamodzichild.Organisations;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.pamodzichild.MainActivity;
import com.pamodzichild.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class EditOrganisation extends Fragment {

    View view;

    String mode;
    String id;
    Dialog dialog1;
    ProgressBar progressBar;
    StorageReference storageReference;
    DatabaseReference organisationRef;
    String textimageOfProof;


    TextInputLayout companyNameLayout, countryLayout, emailLayout,
            phoneNumberLayout, whatsappNuberLayout, webAddressLayout;

    Button companyLogoBtn;
    ImageView companyImageView;
    FloatingActionButton addfab;
    boolean donorAlreadyExist = false;
    private static final int GALLERY_REQUEST_CODE = 100;
    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int PERMISSIONS_REQUEST_CODE = 300;

    private String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    public EditOrganisation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_organisation, container, false);
        getActivity().setTitle("Edit Organisation");
        MainActivity.flag = 1;

        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        dialog1 = new Dialog(getActivity());
        companyNameLayout = view.findViewById(R.id.companyNameLayout);
        countryLayout = view.findViewById(R.id.countryLayout);
        emailLayout = view.findViewById(R.id.emailLayout);
        phoneNumberLayout = view.findViewById(R.id.phoneNumberLayout);
        whatsappNuberLayout = view.findViewById(R.id.whatsappNuberLayout);
        webAddressLayout = view.findViewById(R.id.webAddressLayout);

        companyLogoBtn = view.findViewById(R.id.companyLogoBtn);
        companyImageView = view.findViewById(R.id.companyImageView);
        addfab = view.findViewById(R.id.addfab);

        textimageOfProof = "";


        storageReference = FirebaseStorage.getInstance().getReference();
        organisationRef = FirebaseDatabase.getInstance().getReference().child("Organisations");


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mode = bundle.getString("mode", "null");
            Log.e("mode", mode);
        } else {
            Log.e("mode", "null");
        }


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();


        if (mode.equals("edit")) {
            getActivity().setTitle("Edit Organisation");
            id = getArguments().getString("id", "null");

            progressBar.setVisibility(View.VISIBLE);

            organisationRef.child(id).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.INVISIBLE);

                    ModelCPOrganisation modelCPOrganisation = task.getResult().getValue(ModelCPOrganisation.class);


                    companyNameLayout.getEditText().setText(modelCPOrganisation.getName());
                    countryLayout.getEditText().setText(modelCPOrganisation.getCountry());
                    emailLayout.getEditText().setText(modelCPOrganisation.getEmail());
                    phoneNumberLayout.getEditText().setText(modelCPOrganisation.getPhone());
                    whatsappNuberLayout.getEditText().setText(modelCPOrganisation.getWhatsapp());
                    webAddressLayout.getEditText().setText(modelCPOrganisation.getWebsite());

                    textimageOfProof = modelCPOrganisation.getLogo().trim();

                    if (!textimageOfProof.isEmpty()) {
                        Picasso.get().load(textimageOfProof)
                                .placeholder(R.drawable.loadingimage)
                                .error(R.drawable.nologo)
                                .into(companyImageView);



                    } else {
                        companyImageView.setImageResource(R.drawable.nologo);
                    }

                    progressBar.setVisibility(View.INVISIBLE);







                } else {
                    Log.e("firebase", "Error getting data", task.getException());
                }
            });


        } else if (mode.equals("add")) {
            getActivity().setTitle("Add Organisation");
            id = organisationRef.push().getKey();
        }

        addfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String companyName = companyNameLayout.getEditText().getText().toString().trim();
                String country = countryLayout.getEditText().getText().toString().trim();
                String email = emailLayout.getEditText().getText().toString().trim();
                String phoneNumber = phoneNumberLayout.getEditText().getText().toString().trim();
                String whatsappNumber = whatsappNuberLayout.getEditText().getText().toString().trim();
                String webAddress = webAddressLayout.getEditText().getText().toString().trim();

                if (companyName.isEmpty()) {
                    companyNameLayout.setError("Company Name is required");
                    companyNameLayout.requestFocus();
                    return;
                } else {
                    companyNameLayout.setError(null);
                }

                if (country.isEmpty()) {
                    countryLayout.setError("Country is required");
                    countryLayout.requestFocus();
                    return;
                } else {
                    countryLayout.setError(null);
                }

                if (email.isEmpty()) {
                    emailLayout.setError("Email is required");
                    emailLayout.requestFocus();
                    return;
                } else {
                    emailLayout.setError(null);
                }

                if (phoneNumber.isEmpty()) {
                    phoneNumberLayout.setError("Phone Number is required");
                    phoneNumberLayout.requestFocus();
                    return;
                } else {
                    phoneNumberLayout.setError(null);
                }


               ModelCPOrganisation modelCPOrganisation = new ModelCPOrganisation(
                       ""+id,
                       ""+companyName,
                          ""+webAddress,
                            ""+ textimageOfProof,
                            ""+phoneNumber,
                            ""+whatsappNumber,
                            ""+email,
                            ""+country

               );


                String perfoming="";

                if(mode.equals("add")){
                    perfoming="Adding";
                }else if(mode.equals("edit")){
                    perfoming="Editing";
                }

                ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle("Please Wait");
                progressDialog.setMessage(" "+perfoming+" Organisation");
                progressDialog.setCancelable(false);
                progressDialog.show();
                organisationRef.child(id).setValue(modelCPOrganisation).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        String perfomedTask="";

                        if(mode.equals("add")){
                            perfomedTask="Added";
                        }else if(mode.equals("edit")){
                            perfomedTask="Edited";
                        }

                        progressDialog.dismiss();

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Success");
                        builder.setCancelable(false);
                        builder.setIcon(R.drawable.savegreen);
                        builder.setMessage("Organisation "+perfomedTask+" Successfully !!");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialogInterface.dismiss();
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                                        new CPOrganisations()).commit();

                            }
                        });

                        builder.create().show();
                    }
                });



            }

        });


        companyLogoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkPermissions();

            }
        });

        companyImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkPermissions();

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
            addfab.setEnabled(false);


            switch (requestCode) {
                case GALLERY_REQUEST_CODE:
                    if (data != null) {
                        Uri selectedImageUri = data.getData();
                        // Handle the selected image from gallery
                        dialog1.dismiss();
                        companyImageView.setImageURI(selectedImageUri);

                          Log.e("Image  Selected: ", selectedImageUri.toString());


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
                                child(id).child(System.currentTimeMillis() + ".png");

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
                                        addfab.setEnabled(true);

                                        Log.e("Image URL", textimageOfProof);
                                        Toast.makeText(getContext(), "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();



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

                        companyImageView.setImageBitmap(photo);
                        companyImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);


                        // Convert the photo to a byte array
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] photoBytes = byteArrayOutputStream.toByteArray();

                        // Encode the byte array as a base64 string
                        //textimageOfProof = Base64.encodeToString(photoBytes, Base64.DEFAULT);


                        storageReference = FirebaseStorage.getInstance().getReference().
                                child(id).child(System.currentTimeMillis() + ".png");

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
                                        addfab.setEnabled(true);

                                        Log.e("Image URL", textimageOfProof);
                                        Toast.makeText(getContext(), "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();


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

}