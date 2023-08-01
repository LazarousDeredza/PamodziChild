package com.pamodzichild.Donations;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pamodzichild.MainActivity;
import com.pamodzichild.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

;

public class Donations extends Fragment {

    View view;

    DatabaseReference myDonationsRef;
    FirebaseAuth mAuth;
    ArrayList<ModelDonation> myDonationsList;

    RecyclerView.LayoutManager layoutManager;
    RecyclerView my_donations_recycler_view;
    DonationsAdapter myDonationsAdapter;

    boolean haveDonations;
    TextView txtLoading;
    TextView txtDonated, txtAvailable, txtUnverified, txtRejected;

    LinearLayout no_donations;
    ProgressDialog progressDialog;
    String uid;
    FloatingActionButton adddonationfab;

    Button mydonationsbutton;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_donations, container, false);
        getActivity().setTitle("Donations");
        MainActivity.flag = 1;
        setHasOptionsMenu(true);

        mydonationsbutton = view.findViewById(R.id.mydonationsbutton);
        adddonationfab = view.findViewById(R.id.adddonationfab);


        myDonationsRef = FirebaseDatabase.getInstance().getReference().child("Donations");
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();

        no_donations = view.findViewById(R.id.no_donations);


        txtLoading = view.findViewById(R.id.txtLoading);

        haveDonations = false;

        myDonationsList = new ArrayList<>();
        myDonationsAdapter = new DonationsAdapter(myDonationsList, getContext());
        my_donations_recycler_view = view.findViewById(R.id.my_donations_recycler_view);
//        my_donations_recycler_view.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        my_donations_recycler_view.setLayoutManager(layoutManager);

        myDonationsAdapter.addItemDecoration(my_donations_recycler_view);
        progressDialog = new ProgressDialog(getContext());


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        MainActivity.fab.hide();
        // getFragmentManager().beginTransaction().replace(R.id.content_frame,new MyDonations()).addToBackStack(null).commit();


        mydonationsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the SharedPreferences object
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("fragment", "toDonations");
                editor.apply();

                assert getFragmentManager() != null;
                getFragmentManager().beginTransaction().replace(R.id.content_frame, new MyDonations()).addToBackStack(null).commit();


            }
        });

        adddonationfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the SharedPreferences object
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("fragment", "toDonations");
                editor.apply();

                assert getFragmentManager() != null;
                getFragmentManager().beginTransaction().replace(R.id.content_frame, new Add_Donation()).addToBackStack(null).commit();
            }
        });


        txtLoading.setText("Loading  ...");


        progressDialog.setMessage("Preparing...");
        progressDialog.setTitle("Please wait");
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();

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
                            if (modelDonation.getStatus().equals("Available")) {
                                myDonationsList.add(modelDonation);
                                Log.e("TAG", "Donated by : " + modelDonation.getDonatedby());
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
                        txtLoading.setText("There are no donations available at the moment\nPlease check back later");
                        my_donations_recycler_view.setVisibility(View.GONE);
                        progressDialog.dismiss();
                    }


                } else {
                    haveDonations = false;
                    Log.e("TAG", "Donations : " + haveDonations);
                    no_donations.setVisibility(View.VISIBLE);
                    txtLoading.setText("There are no donations available at the moment\nPlease check back later");
                    my_donations_recycler_view.setVisibility(View.GONE);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }


    private void loadRecylerView() {


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


        myDonationsAdapter = new DonationsAdapter(myDonationsList, getContext());
        my_donations_recycler_view.setAdapter(myDonationsAdapter);
        myDonationsAdapter.notifyItemInserted(myDonationsList.size() - 1);


    }
}