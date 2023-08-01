package com.pamodzichild.Bank;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.pamodzichild.MainActivity;
import com.pamodzichild.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class EditBank extends Fragment {

    View view;

    String mode;
    String id;
    Dialog dialog1;
    ProgressBar progressBar;

    DatabaseReference bankRef;


    TextInputLayout bankNameLayout, accountOwnerLayout, accountNumberLayout;

    String status;
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

    public EditBank() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_bank_account, container, false);
        getActivity().setTitle("Edit Bank Account");
        MainActivity.flag = 1;

        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        dialog1 = new Dialog(getActivity());

        addfab = view.findViewById(R.id.addfab);

        bankNameLayout = view.findViewById(R.id.bankNameLayout);
        accountOwnerLayout = view.findViewById(R.id.accountOwnerLayout);
        accountNumberLayout = view.findViewById(R.id.accountNumberLayout);

        bankRef = FirebaseDatabase.getInstance().getReference().child("Banks");

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
            getActivity().setTitle("Edit Account");
            id = getArguments().getString("id", "null");

            progressBar.setVisibility(View.VISIBLE);

            bankRef.child(id).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.INVISIBLE);

                    ModelBank bank = task.getResult().getValue(ModelBank.class);


                    String textbankName = bank.getBankName();
                    String textaccountOwner = bank.getAccountOwner();
                    String textaccountNumber = bank.getAccountNumber();
                    status = bank.getStatus();


                    bankNameLayout.getEditText().setText(textbankName);
                    accountOwnerLayout.getEditText().setText(textaccountOwner);
                    accountNumberLayout.getEditText().setText(textaccountNumber);


                    progressBar.setVisibility(View.INVISIBLE);


                } else {
                    Log.e("firebase", "Error getting data", task.getException());
                }
            });


        } else if (mode.equals("add")) {
            getActivity().setTitle("Add Bank Account");
            id = bankRef.push().getKey();
            status = "active";
        }

        addfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String bankName = bankNameLayout.getEditText().getText().toString().trim();
                String accountOwner = accountOwnerLayout.getEditText().getText().toString().trim();
                String accountNumber = accountNumberLayout.getEditText().getText().toString().trim();


                if (bankName.isEmpty()) {
                    bankNameLayout.setError("Bank Name is required");
                    bankNameLayout.requestFocus();
                    return;
                } else {
                    bankNameLayout.setError(null);
                }

                if (accountOwner.isEmpty()) {
                    accountOwnerLayout.setError("Account Owner is required");
                    accountOwnerLayout.requestFocus();
                    return;
                } else {
                    accountOwnerLayout.setError(null);
                }

                if (accountNumber.isEmpty()) {
                    accountNumberLayout.setError("Account Number is required");
                    accountNumberLayout.requestFocus();
                    return;
                } else {
                    accountNumberLayout.setError(null);
                }

                ModelBank bank = new ModelBank(
                        "" + id,
                        "" + bankName,
                        "" + accountNumber,
                        "" + accountOwner,
                        "" + status


                );


                String perfoming = "";

                if (mode.equals("add")) {
                    perfoming = "Adding";
                } else if (mode.equals("edit")) {
                    perfoming = "Editing";
                }

                ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle("Please Wait");
                progressDialog.setMessage(" " + perfoming + " Bank Account");
                progressDialog.setCancelable(false);
                progressDialog.show();
                bankRef.child(id).setValue(bank).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        String perfomedTask = "";

                        if (mode.equals("add")) {
                            perfomedTask = "Added";
                        } else if (mode.equals("edit")) {
                            perfomedTask = "Edited";
                        }

                        progressDialog.dismiss();

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Success");
                        builder.setCancelable(false);
                        builder.setIcon(R.drawable.savegreen);
                        builder.setMessage("Bank Account " + perfomedTask + " Successfully !!");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialogInterface.dismiss();
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                                        new Banks()).commit();

                            }
                        });

                        builder.create().show();
                    }
                });


            }

        });


    }


}