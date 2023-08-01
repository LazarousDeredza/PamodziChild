package com.pamodzichild.CPcases;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.pamodzichild.MainActivity;
import com.pamodzichild.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Add_CP extends Fragment {

    View v;
    FloatingActionButton save;

    TextInputLayout firstnamelayout, lastnamelayout, locationlayout, agelayout, parentorguardianlayout, diabilitylayout, perpetratordescriptionlayout;

    TextInputLayout reporterfirstnamelayout, reporterlastnamelayout, reporterlocationlayout, reporterEmailLayout,
            reporteragelayout, mobilenumberlayout, reporteroccupationlayout, anyofferedservices;
    CountryCodePicker CountryCode;

    Spinner abuseSpinner, currentStateSpinner;
    RadioGroup radioGenderGroup, reporterradioGenderGroup;
    LinearLayout childDetailsLayout, repoterDetailsLayout;

    SwitchCompat disabilitySwitch;

    DatabaseReference dbRef;
    String childGender;
    String reporterGender;

    ImageView calender, calender2;


    public Add_CP() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Report a Case");
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_add__c_r_p, container, false);
        MainActivity.flag = 1;
        setHasOptionsMenu(true);
        save = v.findViewById(R.id.save);

        firstnamelayout = v.findViewById(R.id.firstnamelayout);
        lastnamelayout = v.findViewById(R.id.lastnamelayout);
        locationlayout = v.findViewById(R.id.locationlayout);
        agelayout = v.findViewById(R.id.agelayout);
        parentorguardianlayout = v.findViewById(R.id.parentorguardianlayout);
        diabilitylayout = v.findViewById(R.id.diabilitylayout);
        perpetratordescriptionlayout = v.findViewById(R.id.perpetratordescriptionlayout);

        abuseSpinner = v.findViewById(R.id.abuseSpinner);
        currentStateSpinner = v.findViewById(R.id.currentStateSpinner);

        radioGenderGroup = v.findViewById(R.id.radioGenderGroup);

        disabilitySwitch = v.findViewById(R.id.disabilitySwitch);


        childDetailsLayout = v.findViewById(R.id.childDetailsLayout);
        repoterDetailsLayout = v.findViewById(R.id.repoterDetailsLayout);


        reporterfirstnamelayout = v.findViewById(R.id.reporterfirstnamelayout);
        reporterlastnamelayout = v.findViewById(R.id.reporterlastnamelayout);
        reporterlocationlayout = v.findViewById(R.id.reporterlocationlayout);
        reporteragelayout = v.findViewById(R.id.reporteragelayout);
        mobilenumberlayout = v.findViewById(R.id.mobilenumberlayout);
        reporteroccupationlayout = v.findViewById(R.id.reporteroccupationlayout);
        anyofferedservices = v.findViewById(R.id.anyofferedservices);
        reporterEmailLayout = v.findViewById(R.id.reporterEmailLayout);

        CountryCode = v.findViewById(R.id.CountryCode);


        reporterradioGenderGroup = v.findViewById(R.id.reporterradioGenderGroup);

        dbRef = FirebaseDatabase.getInstance().getReference().child("CPcases");
        childGender = "";
        reporterGender = "";


        calender = v.findViewById(R.id.calender);
        calender2 = v.findViewById(R.id.calender2);


        return v;
    }

    @Override
    public void onResume() {

        childDetailsLayout.setVisibility(View.VISIBLE);
        repoterDetailsLayout.setVisibility(View.GONE);

        MainActivity.fab.hide();
        // ADAPTER

        disabilitySwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (disabilitySwitch.isChecked()) {
                    diabilitylayout.setVisibility(View.VISIBLE);
                } else {
                    diabilitylayout.setVisibility(View.GONE);
                }
            }
        });


        radioGenderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton btn = v.findViewById(i);
                childGender = btn.getText().toString();
            }
        });

        reporterradioGenderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton btn = v.findViewById(i);
                reporterGender = btn.getText().toString();
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

                        reporteragelayout.getEditText().setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);

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

                        agelayout.getEditText().setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);

                        Log.e("Date : ", selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);

                    }
                }, currentYear, currentMonth, currentDay);

                datePickerDialog.show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname = firstnamelayout.getEditText().getText().toString();
                String lastname = lastnamelayout.getEditText().getText().toString();
                String location = locationlayout.getEditText().getText().toString();
                String age = agelayout.getEditText().getText().toString();
                String parentorguardian = parentorguardianlayout.getEditText().getText().toString();
                String perpetratordescription = perpetratordescriptionlayout.getEditText().getText().toString();

                String disability;

                if (!disabilitySwitch.isChecked()) {
                    disability = "N/A";
                } else {
                    disability = diabilitylayout.getEditText().getText().toString();
                }

                String abuse = abuseSpinner.getSelectedItem().toString();
                String currentstate = currentStateSpinner.getSelectedItem().toString();
                String gender = childGender;



               /* int selectedId = radioGenderGroup.getCheckedRadioButtonId();

                if (selectedId != -1) {
                    RadioButton selectedRadioButton = v.findViewById(selectedId);
                    gender = selectedRadioButton.getText().toString();

                    // Do something with the selected option
                }*/


                // Get the selected radio button
               /* RadioButton selectedGenderbtn = v.findViewById(radioGenderGroup.getCheckedRadioButtonId());

                // Make sure the selected radio button is not null
                if (selectedGenderbtn != null) {
                    // Get the text of the selected radio button
                    gender = selectedGenderbtn.getText().toString();

                    // Do something with the selected value
                    // ...
                }*/


                //Reporter

                String reporterfirstname = reporterfirstnamelayout.getEditText().getText().toString();
                String reporterlastname = reporterlastnamelayout.getEditText().getText().toString();
                String reporterlocation = reporterlocationlayout.getEditText().getText().toString();
                String reporterage = reporteragelayout.getEditText().getText().toString();
                String mobilenumber = "";
                String countryCode = CountryCode.getSelectedCountryCodeWithPlus();
                mobilenumber = countryCode + mobilenumberlayout.getEditText().getText().toString();
                String reporteroccupation = reporteroccupationlayout.getEditText().getText().toString();
                String reporteranyofferedservices = anyofferedservices.getEditText().getText().toString();

                String reporterEmail = reporterEmailLayout.getEditText().getText().toString();
                String reportergender = reporterGender;

                /*int selectedrepotergenderId = reporterradioGenderGroup.getCheckedRadioButtonId();

                if (selectedrepotergenderId != -1) {
                    RadioButton selectedRadioButton = v.findViewById(selectedrepotergenderId);
                    reportergender = selectedRadioButton.getText().toString();

                    // Do something with the selected option
                }*/

                // Get the selected radio button
               /* RadioButton selectedrepotergenderId = v.findViewById(reporterradioGenderGroup.getCheckedRadioButtonId());

                // Make sure the selected radio button is not null
                if (selectedrepotergenderId != null) {
                    // Get the text of the selected radio button
                    reportergender = selectedrepotergenderId.getText().toString();

                    // Do something with the selected value
                    // ...
                }
*/


                if (repoterDetailsLayout.getVisibility() == View.VISIBLE) {
                    validateRepoter();

                    if (validateRepoter()) {
                        // save to database


                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        String currentDate = dateFormat.format(new Date());


                        SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                        String currentTime = timeformat.format(new Date());

                        if (reporteranyofferedservices.isEmpty()) {
                            reporteranyofferedservices = "NULL";
                        }


                        ModelCPcases crPcase = new ModelCPcases(
                                "" + currentDate,
                                "" + currentTime,
                                "" + firstname + " " + lastname,
                                "" + location,
                                "" + age,
                                "" + gender,
                                "" + disability,
                                "" + parentorguardian,
                                "" + abuse,
                                "" + perpetratordescription,
                                "" + currentstate);

                        Reporter reporter = new Reporter(
                                "" + reporterfirstname + " " + reporterlastname,
                                "" + mobilenumber,
                                "" + reporterEmail,
                                "" + reporterage,
                                "" + reportergender,
                                "" + reporteranyofferedservices,
                                "" + reporterlocation,
                                "" + reporteroccupation
                        );

                        crPcase.setCasesStatus("Open");
                        crPcase.setReporter(reporter);
                        crPcase.setHowitwashandled("null");

                        ProgressDialog progressDialog = new ProgressDialog(getContext());
                        progressDialog.setTitle("Please wait");
                        progressDialog.setMessage("Submitting your report");
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();


                        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int count = (int) snapshot.getChildrenCount() + 1;

                                crPcase.setId("case" + count);
                                dbRef.child("case" + count).setValue(crPcase).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        progressDialog.dismiss();

                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                        builder.setTitle("Success");
                                        builder.setIcon(R.drawable.savegreen);
                                        builder.setMessage("Your report has been submitted successfully !!! \n\nCase Number " +count+ " (For reference purpose)\n\n"+

                                                "Do you also want to report this case to Some Child Protection Organizations that we are in collaboration with ?");
                                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                Fragment fragment = new ReportToOrganisation();
                                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                                            }
                                        });
                                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Fragment fragment = new CPcases();
                                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                                            }
                                        });


                                        builder.show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        Toast.makeText(getContext(), "Failed to submit your report", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                progressDialog.dismiss();

                                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });


                        return;


//                    Fragment fragment = new CRPcases();
//                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    }


                }


                validate();
                if (validate()) {

                    childDetailsLayout.setVisibility(View.GONE);
                    repoterDetailsLayout.setVisibility(View.VISIBLE);
                }


            }
        });


        super.onResume();
    }

    private boolean validateRepoter() {

        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        if (reporterfirstnamelayout.getEditText().getText().toString().isEmpty()) {
            reporterfirstnamelayout.setError("First Name is required");
            reporterfirstnamelayout.getEditText().requestFocus();

            if (imm != null) {
                imm.showSoftInput(reporterfirstnamelayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            reporterfirstnamelayout.setError(null);
        }

        if (reporterlastnamelayout.getEditText().getText().toString().isEmpty()) {
            reporterlastnamelayout.requestFocus();
            reporterlastnamelayout.setError("Last Name is required");

            if (imm != null) {
                imm.showSoftInput(reporterlastnamelayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }

            return false;
        } else {
            reporterlastnamelayout.setError(null);
        }

        if (reporterEmailLayout.getEditText().getText().toString().isEmpty()) {
            reporterEmailLayout.requestFocus();
            reporterEmailLayout.setError("Email is required");

            if (imm != null) {
                imm.showSoftInput(reporterEmailLayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }

            return false;
        } else {
            reporterEmailLayout.setError(null);
        }


        if (reporterlocationlayout.getEditText().getText().toString().isEmpty()) {
            reporterlocationlayout.requestFocus();
            reporterlocationlayout.setError("Location is required");

            if (imm != null) {
                imm.showSoftInput(reporterlocationlayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }

            return false;
        } else {
            reporterlocationlayout.setError(null);
        }


        if (reporteragelayout.getEditText().getText().toString().isEmpty()) {
            reporteragelayout.requestFocus();
            reporteragelayout.setError("Date Of Birth is required");

            if (imm != null) {
                imm.showSoftInput(reporteragelayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }

            return false;
        } else {


            // Get the string from the TextBox
            String dateString = reporteragelayout.getEditText().getText().toString();

// Create a SimpleDateFormat object with the modified format
            SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");
            dateFormat.setLenient(false);

            try {
                // Parse the string into a Date object
                Date date = dateFormat.parse(dateString);

                reporteragelayout.setError(null);
                // If the parsing was successful, the string matches the format
                // You can do something here to indicate that the input is valid
            } catch (ParseException e) {
                // If the parsing failed, the string does not match the format
                // You can do something here to indicate that the input is invalid
                reporteragelayout.requestFocus();
                reporteragelayout.setError("Invalid Date format use dd/mm/yyyy  ");

                if (imm != null) {
                    imm.showSoftInput(reporteragelayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
                }
                return false;
            }

        }


        if (mobilenumberlayout.getEditText().getText().toString().isEmpty()) {
            mobilenumberlayout.requestFocus();
            mobilenumberlayout.setError("Mobile Number is required");

            if (imm != null) {
                imm.showSoftInput(mobilenumberlayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }

            return false;
        } else {
            mobilenumberlayout.setError(null);
        }


        if (reporteroccupationlayout.getEditText().getText().toString().isEmpty()) {
            reporteroccupationlayout.requestFocus();
            reporteroccupationlayout.setError("Occupation is required");

            if (imm != null) {
                imm.showSoftInput(reporteroccupationlayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }

            return false;
        } else {
            reporteroccupationlayout.setError(null);
        }


        if (reporterGender.isEmpty()) {

            Toast.makeText(getContext(), "Select Gender", Toast.LENGTH_SHORT).show();
            return false;
        }


        if (!isValidEmail(reporterEmailLayout.getEditText().getText().toString())) {
            reporterEmailLayout.requestFocus();
            reporterEmailLayout.setError("Invalid Email");

            if (imm != null) {
                imm.showSoftInput(reporterEmailLayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }

            return false;
        } else {
            reporterEmailLayout.setError(null);
        }


        return true;
    }


    public boolean isValidEmail(String email) {
        // Define the email regex pattern
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        // Compile the email regex pattern
        Pattern pattern = Pattern.compile(emailRegex);

        // Match the email address against the regex pattern
        Matcher matcher = pattern.matcher(email);

        // Return true if the email address matches the regex pattern
        return matcher.matches();
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
            lastnamelayout.requestFocus();
            lastnamelayout.setError("Last Name is required");

            if (imm != null) {
                imm.showSoftInput(lastnamelayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }

            return false;
        } else {
            lastnamelayout.setError(null);
        }

        if (locationlayout.getEditText().getText().toString().isEmpty()) {
            locationlayout.requestFocus();
            locationlayout.setError("Location is required");

            if (imm != null) {
                imm.showSoftInput(locationlayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            locationlayout.setError(null);
        }

        if (agelayout.getEditText().getText().toString().isEmpty()) {
            agelayout.requestFocus();
            agelayout.setError("Date Of Birth is required");

            if (imm != null) {
                imm.showSoftInput(agelayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {


            // Get the string from the TextBox
            String dateString = agelayout.getEditText().getText().toString();

// Create a SimpleDateFormat object with the modified format
            SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");
            dateFormat.setLenient(false);

            try {
                // Parse the string into a Date object
                Date date = dateFormat.parse(dateString);

                agelayout.setError(null);
                // If the parsing was successful, the string matches the format
                // You can do something here to indicate that the input is valid
            } catch (ParseException e) {
                // If the parsing failed, the string does not match the format
                // You can do something here to indicate that the input is invalid
                agelayout.requestFocus();
                agelayout.setError("Invalid Date format use dd/mm/yyyy  ");

                if (imm != null) {
                    imm.showSoftInput(agelayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
                }
                return false;
            }


        }


        if (childGender.isEmpty()) {
            Toast.makeText(getContext(), "Select Gender", Toast.LENGTH_SHORT).show();
            return false;
        }


        if (parentorguardianlayout.getEditText().getText().toString().isEmpty()) {
            parentorguardianlayout.requestFocus();
            parentorguardianlayout.setError("Parent or Guardian is required");

            if (imm != null) {
                imm.showSoftInput(parentorguardianlayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            parentorguardianlayout.setError(null);
        }


        if (abuseSpinner.getSelectedItem().toString().equals("Select Type")) {
            Toast.makeText(getContext(), "Select Abuse", Toast.LENGTH_SHORT).show();

            abuseSpinner.requestFocus();


            return false;
        }


        if (currentStateSpinner.getSelectedItem().toString().equals("Select State")) {
            Toast.makeText(getContext(), "Select Current State", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (disabilitySwitch.isChecked()) {
            if (diabilitylayout.getEditText().getText().toString().isEmpty()) {
                diabilitylayout.requestFocus();
                diabilitylayout.setError("Disability is required");

                if (imm != null) {
                    imm.showSoftInput(diabilitylayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
                }
                return false;
            } else {
                diabilitylayout.setError(null);
            }
        }

        if (perpetratordescriptionlayout.getEditText().getText().toString().isEmpty()) {
            perpetratordescriptionlayout.setError("Perpetrator Description is required");
            parentorguardianlayout.requestFocus();

            if (imm != null) {
                imm.showSoftInput(perpetratordescriptionlayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        } else {
            perpetratordescriptionlayout.setError(null);
        }


        return true;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }


}