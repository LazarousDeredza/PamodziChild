package com.pamodzichild.Donations;

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


public class Approve extends Fragment implements SearchView.OnQueryTextListener {

    View view;

    static DatabaseReference myDonationsRef;
    DatabaseReference donorsRef;
    FirebaseAuth mAuth;
    static ArrayList<ModelDonation> myDonationsList;
    static ArrayList<ModelDonor> myDonorsList;

    RecyclerView.LayoutManager layoutManager;
    static RecyclerView my_donations_recycler_view;
    static ApproveDonationsAdapter myDonationsAdapter;

    static boolean haveDonations;
    static TextView txtLoading;
    TextView txtDonated, txtAvailable, txtUnverified, txtRejected;

    static LinearLayout no_donations;
    static ProgressDialog progressDialog;
    Context context;
    String uid;

    SearchView searchView;


    public Approve() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_approve, container, false);
        MainActivity.flag = 1;
        getActivity().setTitle("Approve Donations");

        setHasOptionsMenu(true);


        myDonationsRef = FirebaseDatabase.getInstance().getReference().child("Donations");
        donorsRef = FirebaseDatabase.getInstance().getReference().child("Donors");
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();

        no_donations = view.findViewById(R.id.no_donations);


        txtLoading = view.findViewById(R.id.txtLoading);

        haveDonations = false;

        myDonationsList = new ArrayList<>();
        myDonorsList = new ArrayList<>();
        myDonationsAdapter = new ApproveDonationsAdapter(myDonationsList, getContext());
        my_donations_recycler_view = view.findViewById(R.id.my_donations_recycler_view);
//        my_donations_recycler_view.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        my_donations_recycler_view.setLayoutManager(layoutManager);

        myDonationsAdapter.addItemDecoration(my_donations_recycler_view);
        progressDialog = new ProgressDialog(getContext());

        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);

        context = getContext();


        return view;


    }


    @Override
    public void onResume() {
        super.onResume();
        MainActivity.fab.hide();
        // getFragmentManager().beginTransaction().replace(R.id.content_frame,new MyDonations()).addToBackStack(null).commit();


        txtLoading.setText("Loading  ...");


        progressDialog.setMessage("Preparing...");
        progressDialog.setTitle("Please wait");
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();

        getDonors();

        LoadDonations();


    }

    private static void LoadDonations() {

        myDonationsList.clear();
        myDonationsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {


                    haveDonations = false;
                    for (DataSnapshot s1 : snapshot.getChildren()) {
                        for (DataSnapshot s2 : s1.getChildren()) {
                            ModelDonation modelDonation = s2.getValue(ModelDonation.class);
                            assert modelDonation != null;
                            if (modelDonation.getStatus().equals("Pending Approval")) {


                                String donorId = modelDonation.getDonatedby();
                                ModelDonor donor = new ModelDonor();

                                for (ModelDonor d : myDonorsList) {
                                    if (d.getDonorId().equals(donorId)) {
                                        donor = d;
                                        break;
                                    }

                                    Log.e("TAG", "Donor  ID : " + donor.getDonorId());
                                }


                                modelDonation.setDonor(donor);


                                myDonationsList.add(modelDonation);
                                /* Log.e("TAG", "Donated by : " + modelDonation.getDonatedby());*/
                                haveDonations = true;


                            }
                        }

                    }


                    if (haveDonations) {
                        Log.e("TAG", " Donations are available : " + haveDonations);
                        no_donations.setVisibility(View.GONE);
                        my_donations_recycler_view.setVisibility(View.VISIBLE);
                        loadRecylerView();
                        progressDialog.dismiss();
                    } else {

                        Log.e("TAG", "Donations : " + haveDonations);
                        no_donations.setVisibility(View.VISIBLE);
                        txtLoading.setText("There are no new donations available at the moment\nPlease check back later");
                        my_donations_recycler_view.setVisibility(View.GONE);
                        progressDialog.dismiss();
                    }


                } else {
                    haveDonations = false;
                    Log.e("TAG", "Donations : " + haveDonations);
                    no_donations.setVisibility(View.VISIBLE);
                    txtLoading.setText("There are no donations available at the moment");
                    my_donations_recycler_view.setVisibility(View.GONE);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.context, "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void getDonors() {
        myDonorsList.clear();
        donorsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot s : snapshot.getChildren()) {

                    ModelDonor modelDonor = s.getValue(ModelDonor.class);
                    myDonorsList.add(modelDonor);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", "Error : " + error.getMessage());

            }
        });
    }

    public static void ApproveDonation(String donationID) {

        progressDialog.setMessage("Approving Donation...");
        progressDialog.setTitle("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        DatabaseReference myDonationsRef = FirebaseDatabase.getInstance().getReference().child("Donations");

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();

        myDonationsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {
                    for (DataSnapshot ds2 : ds.getChildren()) {
                        if (ds2.child("donationId").getValue().toString().equals(donationID)) {
                            myDonationsRef.child(ds.getKey()).child(ds2.getKey()).child("status").setValue("Available");
                            break;
                        }
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
                Toast.makeText(MainActivity.context, "Donation Approved", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", "onCancelled: " + error.getMessage());
                progressDialog.dismiss();
                Toast.makeText(MainActivity.context, "Donation Approval Failed", Toast.LENGTH_SHORT).show();


            }
        });


    }

    public static void DeclineDonation(String donationID) {

        progressDialog.setMessage("Declining Donation...");
        progressDialog.setTitle("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        DatabaseReference myDonationsRef = FirebaseDatabase.getInstance().getReference().child("Donations");

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();

        myDonationsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot s : snapshot.getChildren()) {
                    for (DataSnapshot ds : s.getChildren()) {
                        if (ds.child("donationId").getValue().toString().equals(donationID)) {
                            myDonationsRef.child(s.getKey()).child(ds.getKey()).child("status").setValue("Rejected");
                            break;
                        }
                    }
                }



                /*for (DataSnapshot s : snapshot.getChildren()) {
                    for (DataSnapshot ds : s.getChildren()) {
                        if (ds.child("donationId").getValue().toString().equals(donationID)) {
                            myDonationsRef.child(uid).child(ds.getKey()).child("status").setValue("Rejected");
                            break;
                        }
                    }
                }*/
                LoadDonations();


                progressDialog.dismiss();
                Toast.makeText(MainActivity.context, "Donation Declined", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", "onCancelled: " + error.getMessage());
                progressDialog.dismiss();
                Toast.makeText(MainActivity.context, "Donation Approval Failed", Toast.LENGTH_SHORT).show();


            }
        });


    }

    private static void loadRecylerView() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            myDonationsList.sort(new Comparator<ModelDonation>() {
                DateFormat f = new SimpleDateFormat("dd/MM/yyyy");

                @Override
                public int compare(ModelDonation modelDonation, ModelDonation t1) {
                    try {
                        return f.parse(t1.getDate()).compareTo(f.parse(modelDonation.getDate()));
                    } catch (ParseException e) {
                        throw new IllegalArgumentException(e);
                    }
                }

            });
        }


        myDonationsAdapter = new ApproveDonationsAdapter(myDonationsList, MainActivity.context);
        my_donations_recycler_view.setAdapter(myDonationsAdapter);
        myDonationsAdapter.notifyDataSetChanged();


    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        myDonationsAdapter.getFilter().filter(newText);
        return false;
    }
}