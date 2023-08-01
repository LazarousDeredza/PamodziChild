package com.pamodzichild.Summary;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pamodzichild.CPcases.ModelCPcases;
import com.pamodzichild.Donations.ModelDonation;
import com.pamodzichild.MainActivity;
import com.pamodzichild.R;
import com.pamodzichild.Sponsorship.ModelSponsorship;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Summary extends Fragment {
    View v;
    Button sample, log;

    String s;

    TextView todaycrpcasescount, thismonthcrpcasescount, todaydonations, thismonthdonations, todaysponsorshipamount, thismonthsponsorshipamount;
    String uid;
    List<ModelCPcases> cpCasesList;
    List<ModelDonation> donationList;

    List<ModelSponsorship> sponsorshipList;


    DatabaseReference crpCasesRef, donationRef, sponsorshipRef;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_summary, container, false);
        getActivity().setTitle("Summary");
        MainActivity.flag = 1;

        todaycrpcasescount = v.findViewById(R.id.todaycrpcasescount);
        todaycrpcasescount.setText("0");
        thismonthcrpcasescount = v.findViewById(R.id.thismonthcrpcasescount);
        thismonthcrpcasescount.setText("0");

        todaydonations = v.findViewById(R.id.todaydonations);
        todaydonations.setText("0");

        thismonthdonations = v.findViewById(R.id.thismonthdonations);
        thismonthdonations.setText("0");

        todaysponsorshipamount = v.findViewById(R.id.todaysponsorshipamount);
        todaysponsorshipamount.setText("0");

        thismonthsponsorshipamount = v.findViewById(R.id.thismonthsponsorshipamount);
        thismonthsponsorshipamount.setText("0");

        crpCasesRef = FirebaseDatabase.getInstance().getReference().child("CPcases");
        cpCasesList = new ArrayList<>();

        donationRef = FirebaseDatabase.getInstance().getReference().child("Donations");
        donationList = new ArrayList<>();

        sponsorshipRef = FirebaseDatabase.getInstance().getReference().child("sponsorships");
        sponsorshipList = new ArrayList<>();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.fab.hide();

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        crpCasesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cpCasesList.clear();
                int daycount = 0;
                int monthCount = 0;


                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String currentDate = dateFormat.format(new Date());

                SimpleDateFormat monthYearFormat = new SimpleDateFormat("MM/yyyy", Locale.getDefault());


                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ModelCPcases modelCPcases = ds.getValue(ModelCPcases.class);

                    cpCasesList.add(modelCPcases);


                    if (modelCPcases.getDateOfReporting().equals(currentDate)) {
                        daycount++;
                    }

                    Date date = null;
                    try {
                        date = dateFormat.parse(modelCPcases.getDateOfReporting());
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                    String currentMonthYear = monthYearFormat.format(new Date());
                    String reportMonthYear = monthYearFormat.format(date);
                    if (currentMonthYear.equals(reportMonthYear)) {
                        monthCount++;
                    }

                }


                todaycrpcasescount.setText(String.valueOf(daycount));
                thismonthcrpcasescount.setText(String.valueOf(monthCount));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        donationRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                donationList.clear();
                int daycount = 0;
                int monthCount = 0;

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String currentDate = dateFormat.format(new Date());

                SimpleDateFormat monthYearFormat = new SimpleDateFormat("MM/yyyy", Locale.getDefault());

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    for (DataSnapshot ds1 : ds.getChildren()) {
                        ModelDonation modelDonation = ds1.getValue(ModelDonation.class);
                        donationList.add(modelDonation);

                        if (modelDonation.getDate().equals(currentDate)) {
                            daycount++;
                        }

                        Date date = null;
                        try {
                            date = dateFormat.parse(modelDonation.getDate());
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }

                        String currentMonthYear = monthYearFormat.format(new Date());
                        String reportMonthYear = monthYearFormat.format(date);
                        if (currentMonthYear.equals(reportMonthYear)) {
                            monthCount++;
                        }

                    }


                }


                todaydonations.setText(String.valueOf(daycount));
                thismonthdonations.setText(String.valueOf(monthCount));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sponsorshipRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sponsorshipList.clear();
                int daycount = 0;
                int monthCount = 0;

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String currentDate = dateFormat.format(new Date());

                SimpleDateFormat monthYearFormat = new SimpleDateFormat("MM/yyyy", Locale.getDefault());

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    ModelSponsorship modelSponsorship = ds.getValue(ModelSponsorship.class);
                    sponsorshipList.add(modelSponsorship);

                    if (modelSponsorship.getDate().equals(currentDate)) {
                        daycount++;
                    }

                    Date date = null;
                    try {
                        date = dateFormat.parse(modelSponsorship.getDate());
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                    String currentMonthYear = monthYearFormat.format(new Date());
                    String reportMonthYear = monthYearFormat.format(date);
                    if (currentMonthYear.equals(reportMonthYear)) {
                        monthCount++;
                    }

                }
                progressDialog.dismiss();
                todaysponsorshipamount.setText(String.valueOf(daycount));
                thismonthsponsorshipamount.setText(String.valueOf(monthCount));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
