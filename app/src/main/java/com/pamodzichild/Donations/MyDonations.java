package com.pamodzichild.Donations;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pamodzichild.MainActivity;
import com.pamodzichild.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MyDonations extends Fragment {

    View view;

    DatabaseReference myDonationsRef;
    FirebaseAuth mAuth;
    ArrayList<ModelDonation> myDonationsList;

    RecyclerView.LayoutManager layoutManager;
    RecyclerView my_donations_recycler_view;
    MyDonationsAdapter myDonationsAdapter;

    boolean haveDonations;
    TextView txtLoading;
    TextView txtDonated, txtAvailable, txtUnverified, txtRejected;

    LinearLayout no_donations;
    ProgressDialog progressDialog;
    String uid;
    FloatingActionButton adddonationfab;


    public MyDonations() {
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
        view = inflater.inflate(R.layout.fragment_my_donations, container, false);
        getActivity().setTitle("My Donations");
        MainActivity.flag = 1;


        myDonationsRef = FirebaseDatabase.getInstance().getReference().child("Donations");
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();

        no_donations = view.findViewById(R.id.no_donations);


        txtLoading = view.findViewById(R.id.txtLoading);

        haveDonations = false;

        myDonationsList = new ArrayList<>();
        myDonationsAdapter = new MyDonationsAdapter(myDonationsList, getContext());
        my_donations_recycler_view = view.findViewById(R.id.my_donations_recycler_view);
//        my_donations_recycler_view.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        my_donations_recycler_view.setLayoutManager(layoutManager);

        myDonationsAdapter.addItemDecoration(my_donations_recycler_view);
        progressDialog = new ProgressDialog(getContext());

        txtDonated = view.findViewById(R.id.txtDonated);
        txtAvailable = view.findViewById(R.id.txtAvailable);
        txtUnverified = view.findViewById(R.id.txtUnverified);
        txtRejected = view.findViewById(R.id.txtRejected);

        txtDonated.setText("0");
        txtAvailable.setText("0");
        txtUnverified.setText("0");
        txtRejected.setText("0");

        adddonationfab = view.findViewById(R.id.adddonationfab);

        return view;
    }


    @Override
    public void onResume() {

        super.onResume();

        MainActivity.fab.hide();

        txtLoading.setText("Loading  ...");


        progressDialog.setMessage("Preparing...");
        progressDialog.setTitle("Please wait");
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();


        myDonationsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(uid).exists()) {
                    haveDonations = true;
                    Log.e("TAG", "Have Pending Donations to be approved : " + haveDonations);
                    no_donations.setVisibility(View.GONE);
                    my_donations_recycler_view.setVisibility(View.VISIBLE);
                    loadData();

                } else {
                    haveDonations = false;
                    Log.e("TAG", "Have Donations : " + haveDonations);
                    no_donations.setVisibility(View.VISIBLE);
                    txtLoading.setText("You Have not done \nany Donations Yet");
                    my_donations_recycler_view.setVisibility(View.GONE);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


        adddonationfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add_Donation addDonation = new Add_Donation();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, addDonation).addToBackStack(null).commit();
            }
        });
    }

    private void loadData() {

        progressDialog.setMessage("Fetching Your Donations...");
        progressDialog.setTitle("Please wait");

        myDonationsRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot pendingSnapshot : snapshot.getChildren()) {
                    ModelDonation modelDonation = pendingSnapshot.getValue(ModelDonation.class);
                    myDonationsList.add(modelDonation);

                    myDonationsAdapter = new MyDonationsAdapter(myDonationsList, getContext());
                    my_donations_recycler_view.setAdapter(myDonationsAdapter);
                    myDonationsAdapter.notifyItemInserted(myDonationsList.size() - 1);
                    Log.e("TAG", "Pending Donation : " + modelDonation.toString());


                    if (modelDonation.getStatus().equals("Donated")) {
                        txtDonated.setText(String.valueOf(Integer.parseInt(txtDonated.getText().toString()) + 1));
                    } else if (modelDonation.getStatus().equals("Available")) {
                        txtAvailable.setText(String.valueOf(Integer.parseInt(txtAvailable.getText().toString()) + 1));
                    } else if (modelDonation.getStatus().equals("Pending Approval")) {
                        txtUnverified.setText(String.valueOf(Integer.parseInt(txtUnverified.getText().toString()) + 1));
                    } else if (modelDonation.getStatus().equals("Rejected")) {
                        txtRejected.setText(String.valueOf(Integer.parseInt(txtRejected.getText().toString()) + 1));
                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();

                Log.e("TAG", "Error : " + error.getMessage());

            }
        });


        progressDialog.dismiss();


    }
}