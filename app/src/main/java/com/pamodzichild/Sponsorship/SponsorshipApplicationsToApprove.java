package com.pamodzichild.Sponsorship;

import static com.pamodzichild.MainActivity.fab;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pamodzichild.MainActivity;
import com.pamodzichild.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;

public class SponsorshipApplicationsToApprove extends Fragment implements SearchView.OnQueryTextListener {
    View view;
    static String uid;
    static DatabaseReference mySponseesRef;
    static FirebaseAuth mAuth;
    static ArrayList<ModelSponsee> mySponseesist;

    static RecyclerView.LayoutManager layoutManager;
    static RecyclerView my_sponsorship_recycler_view;
    static SponsorshipAdapterApprove mySponsorshipAdapter;
    static SearchView searchView;

    static boolean haveSponsees;
    static TextView txtLoading;
    static TextView txtDonated, txtAvailable, txtUnverified, txtRejected;

    static LinearLayout no_donations;


    static Context context;
    static ProgressDialog progressDialog;


    // boolean trandate=false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sponsorship_applications_to_approve, container, false);
        requireActivity().setTitle("Sponsorship Application Approvals");
        MainActivity.flag = 1;

        setHasOptionsMenu(true);


        mySponseesRef = FirebaseDatabase.getInstance().getReference().child("Sponsee");
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();

        no_donations = view.findViewById(R.id.no_donations);


        txtLoading = view.findViewById(R.id.txtLoading);

        haveSponsees = false;

        mySponseesist = new ArrayList<>();
        mySponsorshipAdapter = new SponsorshipAdapterApprove(mySponseesist, getContext());
        my_sponsorship_recycler_view = view.findViewById(R.id.my_sponsorship_recycler_view);
//        my_sponsorship_recycler_view.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        my_sponsorship_recycler_view.setLayoutManager(layoutManager);

        mySponsorshipAdapter.addItemDecoration(my_sponsorship_recycler_view);
        progressDialog = new ProgressDialog(getContext());

        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);

        context = getContext();


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        fab.hide();
        // ADAPTER


        txtLoading.setText("Loading  ...");


        progressDialog.setMessage("Preparing...");
        progressDialog.setTitle("Please wait");
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();

        mySponseesist.clear();


        mySponseesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {

                    haveSponsees = false;
                    for (DataSnapshot s1 : snapshot.getChildren()) {
                        ModelSponsee modelSponsee = s1.getValue(ModelSponsee.class);
                        assert modelSponsee != null;
                        if (modelSponsee.getStatus().equals("Pending")) {
                            mySponseesist.add(modelSponsee);
                            haveSponsees = true;

                        }

                    }


                    if (haveSponsees) {
                        Log.e("TAG", " Donations are available : " + haveSponsees);
                        no_donations.setVisibility(View.GONE);
                        my_sponsorship_recycler_view.setVisibility(View.VISIBLE);
                        loadRecylerView();
                        progressDialog.dismiss();
                    } else {

                        Log.e("TAG", "Donations : " + haveSponsees);
                        no_donations.setVisibility(View.VISIBLE);
                        txtLoading.setText("There are no sponsorship applications to approve at the moment\nPlease check back later");
                        my_sponsorship_recycler_view.setVisibility(View.GONE);
                        progressDialog.dismiss();
                    }


                } else {
                    haveSponsees = false;
                    Log.e("TAG", "Donations : " + haveSponsees);
                    no_donations.setVisibility(View.VISIBLE);
                    txtLoading.setText("There are no sponsorship applications to approve at the moment\nPlease check back later");
                    my_sponsorship_recycler_view.setVisibility(View.GONE);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


    public static void ApproveApplicant(String id) {


        progressDialog.setMessage("Approving Sponsorship Application...");
        progressDialog.setTitle("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        DatabaseReference myDonationsRef = FirebaseDatabase.getInstance().getReference().child("Sponsee");

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();

        myDonationsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {

                    if (ds.child("id").getValue().toString().equals(id)) {
                        myDonationsRef.child(ds.getKey()).child("status").setValue("Approved");
                        break;

                    }
                }
               /* for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.child("donationId").getValue().toString().equals(donationID)) {
                        myDonationsRef.child(uid).child(ds.getKey()).child("status").setValue("Available");
                        break;
                    }
                }*/


                LoadDonations();


                progressDialog.dismiss();
                Toast.makeText(MainActivity.context, "Sponsorship Application Approved Successfully", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", "onCancelled: " + error.getMessage());
                progressDialog.dismiss();
                Toast.makeText(MainActivity.context, "Application Approval Failed", Toast.LENGTH_SHORT).show();


            }
        });


    }

    public static void DeclineApplicant(String id) {


        progressDialog.setMessage("Declining Sponsorship Application...");
        progressDialog.setTitle("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        DatabaseReference myDonationsRef = FirebaseDatabase.getInstance().getReference().child("Sponsee");

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();

        myDonationsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {

                        if (ds.child("id").getValue().toString().equals(id)) {
                            myDonationsRef.child(ds.getKey()).child("status").setValue("Declined");
                            break;

                    }
                }
               /* for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.child("donationId").getValue().toString().equals(donationID)) {
                        myDonationsRef.child(uid).child(ds.getKey()).child("status").setValue("Available");
                        break;
                    }
                }*/


                LoadDonations();


                progressDialog.dismiss();
                Toast.makeText(MainActivity.context, "Application Declined", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", "onCancelled: " + error.getMessage());
                progressDialog.dismiss();
                Toast.makeText(MainActivity.context, "Application Decline Failed", Toast.LENGTH_SHORT).show();


            }
        });


    }


    private static void LoadDonations() {


        txtLoading.setText("Loading  ...");


        progressDialog.setMessage("Preparing...");
        progressDialog.setTitle("Please wait");
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();

        mySponseesist.clear();


        mySponseesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {

                    haveSponsees = false;
                    for (DataSnapshot s1 : snapshot.getChildren()) {
                        ModelSponsee modelSponsee = s1.getValue(ModelSponsee.class);
                        assert modelSponsee != null;
                        if (modelSponsee.getStatus().equals("Pending")) {
                            mySponseesist.add(modelSponsee);
                            haveSponsees = true;

                        }

                    }


                    if (haveSponsees) {
                        Log.e("TAG", " Donations are available : " + haveSponsees);
                        no_donations.setVisibility(View.GONE);
                        my_sponsorship_recycler_view.setVisibility(View.VISIBLE);
                        loadRecylerView();
                        progressDialog.dismiss();
                    } else {

                        Log.e("TAG", "Donations : " + haveSponsees);
                        no_donations.setVisibility(View.VISIBLE);
                        txtLoading.setText("There are no sponsorship applications to approve at the moment\nPlease check back later");
                        my_sponsorship_recycler_view.setVisibility(View.GONE);
                        progressDialog.dismiss();
                    }


                } else {
                    haveSponsees = false;
                    Log.e("TAG", "Donations : " + haveSponsees);
                    no_donations.setVisibility(View.VISIBLE);
                    txtLoading.setText("There are no sponsorship applications to approve at the moment\nPlease check back later");
                    my_sponsorship_recycler_view.setVisibility(View.GONE);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


    private static void loadRecylerView() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mySponseesist.sort(new Comparator<ModelSponsee>() {
                DateFormat f = new SimpleDateFormat("dd/MM/yyyy");

                @Override
                public int compare(ModelSponsee modelDonation, ModelSponsee t1) {
                    try {
                        return f.parse(t1.getDateApplied()).compareTo(f.parse(modelDonation.getDateApplied()));
                    } catch (ParseException e) {
                        throw new IllegalArgumentException(e);
                    }
                }

            });
        }


        mySponsorshipAdapter = new SponsorshipAdapterApprove(mySponseesist, context);
        my_sponsorship_recycler_view.setAdapter(mySponsorshipAdapter);
        mySponsorshipAdapter.notifyItemInserted(mySponseesist.size() - 1);


    }


    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mySponsorshipAdapter.getFilter().filter(newText);
        return false;
    }

}
