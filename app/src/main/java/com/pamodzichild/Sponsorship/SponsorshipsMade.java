package com.pamodzichild.Sponsorship;

import android.app.ProgressDialog;
import android.content.Context;
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
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pamodzichild.Bank.ModelBank;
import com.pamodzichild.MainActivity;
import com.pamodzichild.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;


public class SponsorshipsMade extends Fragment implements SearchView.OnQueryTextListener {
    View view;
    String uid;
    DatabaseReference mySponseesRef, bankRef;
    FirebaseAuth mAuth;
    ArrayList<ModelSponsorship> mySponseesist;

    RecyclerView.LayoutManager layoutManager;
    RecyclerView my_sponsorship_recycler_view;
    SponsorshipRequestAdapter mySponsorshipAdapter;
    SearchView searchView;

    boolean haveSponsees;
    TextView txtLoading;
    TextView txtDonated, txtAvailable, txtUnverified, txtRejected;

    LinearLayout no_donations;
    ProgressDialog progressDialog;

    Context context;

    Button addSponsee;

    ArrayList<String> arrayList;


    public SponsorshipsMade() {
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
        view = inflater.inflate(R.layout.fragment_sponsorships_made, container, false);

        requireActivity().setTitle("Sponsorship Requests");
        MainActivity.flag = 1;

        setHasOptionsMenu(true);


        addSponsee = view.findViewById(R.id.addSponsee);

        arrayList = new ArrayList<>();

        mySponseesRef = FirebaseDatabase.getInstance().getReference().child("sponsorships");
        bankRef = FirebaseDatabase.getInstance().getReference().child("Banks");
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        Log.e("Logged Email", mAuth.getCurrentUser().getEmail());

        no_donations = view.findViewById(R.id.no_donations);


        txtLoading = view.findViewById(R.id.txtLoading);

        haveSponsees = false;

        mySponseesist = new ArrayList<>();
        mySponsorshipAdapter = new SponsorshipRequestAdapter(mySponseesist, getContext(), arrayList);
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
        MainActivity.fab.hide();
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
                        ModelSponsorship modelSponsee = s1.getValue(ModelSponsorship.class);
                        assert modelSponsee != null;
                        if (modelSponsee.getStatus().equals("approved") || modelSponsee.getStatus().equals("pending")) {
                            mySponseesist.add(modelSponsee);
                            haveSponsees = true;

                        }

                    }


                    if (haveSponsees) {
                        Log.e("TAG", " Donations are available : " + haveSponsees);
                        no_donations.setVisibility(View.GONE);
                        my_sponsorship_recycler_view.setVisibility(View.VISIBLE);


                        bankRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.hasChildren()) {

                                    for (DataSnapshot s1 : snapshot.getChildren()) {
                                        ModelBank modelBank = s1.getValue(ModelBank.class);
                                        assert modelBank != null;
                                        if (modelBank.getStatus().equals("active")) {
                                            arrayList.add(modelBank.getBankName() + " - " + modelBank.getAccountNumber());
                                        }

                                    }

                                    loadRecylerView();
                                    progressDialog.dismiss();
                                } else {
                                    loadRecylerView();
                                    progressDialog.dismiss();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    } else {

                        Log.e("TAG", "Donations : " + haveSponsees);
                        no_donations.setVisibility(View.VISIBLE);
                        txtLoading.setText("There are no new sponsorships at the moment\nPlease check back later");
                        my_sponsorship_recycler_view.setVisibility(View.GONE);
                        progressDialog.dismiss();
                    }


                } else {
                    haveSponsees = false;
                    Log.e("TAG", "Donations : " + haveSponsees);
                    no_donations.setVisibility(View.VISIBLE);
                    txtLoading.setText("here are no new sponsorships at the moment\nPlease check back later");
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

    private void loadRecylerView() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mySponseesist.sort(new Comparator<ModelSponsorship>() {


                @Override
                public int compare(ModelSponsorship modelDonation, ModelSponsorship t1) {

                    return t1.getSponsor().getFname().compareTo(modelDonation.getSponsor().getFname());

                }

            });
        }


        mySponsorshipAdapter = new SponsorshipRequestAdapter(mySponseesist, getContext(),arrayList);
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