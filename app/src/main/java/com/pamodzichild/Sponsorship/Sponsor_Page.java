package com.pamodzichild.Sponsorship;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pamodzichild.MainActivity;
import com.pamodzichild.R;
import com.pamodzichild.SendEmail.SendMailTask;
import com.pamodzichild.Users.UserModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mynameismidori.currencypicker.CurrencyPicker;
import com.mynameismidori.currencypicker.CurrencyPickerListener;
import com.mynameismidori.currencypicker.ExtendedCurrency;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Sponsor_Page extends Fragment {

    View view;

    ModelSponsee modelSponsee;
    TextView txtDate, txtTime, txtSponseeName, txtAge,
            txtGender, txtAddress, txtParentsAlive, txtOccupation;

    Button currencyButton, btnSubmit;
    TextInputLayout amountLayout, startDateforSponsorship, purposeLayout, durationLayout, requirementsLayout;
    ImageView calender;
    TextView txtCurrency;

    DatabaseReference reference, refSponsorship;
    UserModel userModel;

    String uid;

    public Sponsor_Page() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sponsor__page, container, false);
        // Inflate the layout for this fragment
        getActivity().setTitle("Sponsor now");
        MainActivity.flag = 1;


        txtDate = view.findViewById(R.id.txtDate);
        txtTime = view.findViewById(R.id.txtTime);
        txtSponseeName = view.findViewById(R.id.txtSponseeName);
        txtAge = view.findViewById(R.id.txtAge);
        txtGender = view.findViewById(R.id.txtGender);
        txtAddress = view.findViewById(R.id.txtAddress);
        txtParentsAlive = view.findViewById(R.id.txtParentsAlive);
        txtOccupation = view.findViewById(R.id.txtOccupation);
        txtCurrency = view.findViewById(R.id.txtCurrency);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();


        currencyButton = view.findViewById(R.id.currency_button);
        amountLayout = view.findViewById(R.id.amountLayout);
        calender = view.findViewById(R.id.calender);
        startDateforSponsorship = view.findViewById(R.id.startDateforSponsorshipLayout);
        purposeLayout = view.findViewById(R.id.purposeLayout);
        durationLayout = view.findViewById(R.id.durationLayout);
        requirementsLayout = view.findViewById(R.id.requirementsLayout);

        btnSubmit = view.findViewById(R.id.btnSubmit);

        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        refSponsorship = FirebaseDatabase.getInstance().getReference().child("sponsorships");
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());
        txtDate.setText(currentDate);

        SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String currentTime = timeformat.format(new Date());
        txtTime.setText(currentTime);

        Bundle bundle = getArguments();
        String id = bundle.getString("id");

        Log.e("Sponsee ID", id);


        modelSponsee = (ModelSponsee) bundle.getSerializable("sponsee");
        Log.e("Sponsee Date", modelSponsee.toString());


        txtSponseeName.setText(modelSponsee.getFirstname() + " " + modelSponsee.getLastname());

        String dobString = modelSponsee.getDob();

// Create a SimpleDateFormat object with the format "dd/MM/yyyy"

        try {
            // Parse the string into a Date object
            Date dob = dateFormat.parse(dobString);

            // Get the current date
            Calendar today = Calendar.getInstance();

            // Get the date of birth as a Calendar object
            Calendar dobCal = Calendar.getInstance();
            dobCal.setTime(dob);

            // Calculate the difference between the two dates in years
            int age = today.get(Calendar.YEAR) - dobCal.get(Calendar.YEAR);
            if (today.get(Calendar.MONTH) < dobCal.get(Calendar.MONTH) ||
                    (today.get(Calendar.MONTH) == dobCal.get(Calendar.MONTH) && today.get(Calendar.DAY_OF_MONTH) < dobCal.get(Calendar.DAY_OF_MONTH))) {
                age--;
            }

            txtAge.setText(String.valueOf(age));

            // The variable "age" now contains the age in years
            // You can use this value as needed in your application

        } catch (ParseException e) {
            txtAge.setText(modelSponsee.getDob());
        }

        txtGender.setText(modelSponsee.getGender());
        txtAddress.setText(modelSponsee.getAddress());
        txtParentsAlive.setText(modelSponsee.getParentsAlive());
        txtOccupation.setText(modelSponsee.getOccupation());


        currencyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CurrencyPicker picker = CurrencyPicker.newInstance("Select Currency");  // dialog title
                picker.setListener(new CurrencyPickerListener() {
                    @Override
                    public void onSelectCurrency(String name, String code, String symbol, int flagDrawableResID) {
                        // Implement your code here


                        List<ExtendedCurrency> currencies = ExtendedCurrency.getAllCurrencies(); //List of all currencies
                        ExtendedCurrency[] currencies2 = ExtendedCurrency.CURRENCIES; //Array of all currencies
                        ExtendedCurrency currency = ExtendedCurrency.getCurrencyByName(name); //Get currency by its name


                        String currencyName = currency.getName();
                        String codeCode = currency.getCode();
                        int flag = currency.getFlag();  // returns android resource id of flag or -1, if none is associated
                        String symbol2 = currency.getSymbol();

                        currency.loadFlagByCode(getContext());  // attempts to associate flag to currency based on its ISO code. Used if you create your own instance of Currency.class


                        txtCurrency.setText(symbol2);
                        picker.dismiss();

                    }
                });
                picker.show(getChildFragmentManager(), "CURRENCY_PICKER");

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

                        startDateforSponsorship.getEditText().setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
                        // dob = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;

                        Log.e("Date : ", selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);

                    }
                }, currentYear, currentMonth, currentDay);

                datePickerDialog.show();
            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                validateData();

                if (validateData()) {


                    ProgressDialog progressDialog = new ProgressDialog(getContext());
                    progressDialog.setTitle("Please wait");
                    progressDialog.setMessage("Submitting your Sponsorship for review");
                    progressDialog.setCancelable(false);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();


                    String id = reference.push().getKey();
                    String purpose = purposeLayout.getEditText().getText().toString();
                    String amount = amountLayout.getEditText().getText().toString();
                    String duration = durationLayout.getEditText().getText().toString();
                    String dateForSponsorship = startDateforSponsorship.getEditText().getText().toString();
                    String requirements = requirementsLayout.getEditText().getText().toString();
                    String sponseeName = modelSponsee.getFirstname() + " " + modelSponsee.getLastname();

                    String currency = txtCurrency.getText().toString();

                    ModelSponsorship sponsorship = new ModelSponsorship(
                            "" + id,
                            "" + uid,
                            "" + modelSponsee.getId(),
                            userModel,
                            "" + currentDate,
                            "" + currentTime,
                            "" + "pending",
                            "" + amount,
                            "" + duration,
                            "" + purpose,
                            "" + dateForSponsorship,
                            "" + requirements,
                            "" + currency,
                            modelSponsee

                    );


                    assert id != null;
                    refSponsorship.child(id).setValue(sponsorship).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Sponsorship Submitted for review", Toast.LENGTH_SHORT).show();


                            String message = "<html><body><p>Hello " + userModel.getFname() + " " + userModel.getLname() + ",</p>\n\n" +

                                    "<p>Thank you for considering to make a sponsorship to " + sponseeName + ". We will review it and notify you once it is approved.</p>\n\n" +
                                    "<p></p>" +
                                    "<p></p>" +
                                    "<p></p>" +
                                    "<p></p>" +
                                    "<p>Regards,</p>\n" +
                                    "<p>Team Pamodzi Child Africa</p>";


                            SendMailTask sendMailTask = new SendMailTask(userModel.getUser_email(), "Your Sponsorship is being reviewed", message, requireContext());
                            sendMailTask.execute();


                            String message2 = "<html><body><p>Hello Pamodzi Child Admin,</p>" +
                                    "<p><b>Date : </b> " + currentDate + "\t<b>    Time :</b> " + currentTime + "</p>" +
                                    "<p> " + userModel.getFname() + " " + userModel.getLname() + ", wants to sponsor " + sponseeName + " , please review and approve!! </p>" +
                                    "<p><b>Sponsee Details </b><br>" +
                                    "<b>Name :</b> " + sponseeName + "    " +
                                    "<b>Location :</b> " + modelSponsee.getAddress() + "<br>" +
                                    "<b>Phone Number :</b> " + modelSponsee.getPhone() + "   " +
                                    "<b>Email  :</b> " + modelSponsee.getEmail() + " <br>" +
                                    "<b>Parents Alive : </b> " + modelSponsee.getParentsAlive() + "    " +
                                    "<b>Disability : </b> " + modelSponsee.getDisability() + "<br>" +
                                    "<b> Date Registered : </b>" + modelSponsee.getDateApplied() + "     " +
                                    "<b> Age : </b>" + txtAge.getText().toString() + "<br><br>" +
                                    "<p><b>Sponsorship Details </b>( id = "+id +")<br>" +
                                    "<b>Amount :</b> " + amount + "    " +
                                    "<b>Purpose :</b> " + purpose + "<br>" +
                                    "<b>Duration :</b> " + duration + "   " +
                                    "<b>Date to start the sponsorship   :</b> " + dateForSponsorship + " <br>" +
                                    "<b>Terms and Conditions (Requirements ) : </b><br> " + requirements +

                                    "<p><b>Sponsor Contact Details :</b></p>" +
                                    "<b>Name :</b> " + userModel.getFname() + " " + userModel.getLname()  + "    " +
                                    "<p><b>      Phone :</b> " + userModel.getMobile() + "</p>" +
                                    "<p><b>      Email :</b> " + userModel.getUser_email() + "</p>" +
                                    "<p>Regards,<br>Team Pamodzi Child Africa</p></body></html>";


                            // damarisaswa12@gmail.com


                            SendMailTask sendMailTask2 = new SendMailTask("damarisaswa12@gmail.com", "New Sponsorship has been made", message2, requireContext(), "text/html");
                            sendMailTask2.execute();


                            progressDialog.dismiss();


                            AlertDialog alertDialog = new AlertDialog.Builder(Objects.requireNonNull(requireContext())).create();
                            alertDialog.setTitle("Success : Thank you");
                            alertDialog.setMessage("Your Sponsorship has been submitted successfully." +
                                    " You will be notified once it is verified and ready to be given to the selected beneficiary.");
                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new Sponsorship()).addToBackStack(null).commit();
                                }
                            });
                            alertDialog.show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();

                            Toast.makeText(getContext(), "Failed to submit sponsorship", Toast.LENGTH_SHORT).show();
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


        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                userModel = snapshot.child(currentUserID).getValue(UserModel.class);
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Error : ", error.getMessage());
            }
        });

    }

    private boolean validateData() {

        if (purposeLayout.getEditText().getText().toString().isEmpty()) {
            purposeLayout.setError("Purpose is required");
            return false;
        } else {
            purposeLayout.setError(null);
        }

        if (amountLayout.getEditText().getText().toString().isEmpty()) {
            amountLayout.setError("Amount is required");
            return false;
        } else {
            amountLayout.setError(null);
        }

        if (txtCurrency.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please select currency", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (durationLayout.getEditText().getText().toString().isEmpty()) {
            durationLayout.setError("Duration is required");
            return false;
        } else {
            durationLayout.setError(null);
        }

        if (startDateforSponsorship.getEditText().getText().toString().isEmpty()) {

            startDateforSponsorship.setError("Date is required");

            return false;
        } else if (!isDateValid(startDateforSponsorship.getEditText().getText().toString())) {
            startDateforSponsorship.setError("Invalid date format");
            return false;
        }else {
            startDateforSponsorship.setError(null);
        }


        return true;
    }

    private boolean isDateValid(String _date) {


// Create a SimpleDateFormat object with the modified format
        SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");
        dateFormat.setLenient(false);

        try {
            // Parse the string into a Date object
            Date date = dateFormat.parse(_date);

        } catch (ParseException e) {

            return false;
        }
        return true;

    }
}