package com.pamodzichild.Sponsorship;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.Context;
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

public class Sponsorship extends Fragment   implements SearchView.OnQueryTextListener{
    View view;
    String uid;
    DatabaseReference mySponseesRef;
    FirebaseAuth mAuth;
    ArrayList<ModelSponsee> mySponseesist;

    RecyclerView.LayoutManager layoutManager;
    RecyclerView my_sponsorship_recycler_view;
    SponsorshipAdapter mySponsorshipAdapter;
    SearchView searchView;

    boolean haveSponsees;
    TextView txtLoading;
    TextView txtDonated, txtAvailable, txtUnverified, txtRejected;

    LinearLayout no_donations;
    ProgressDialog progressDialog;

    Context context;

    Button addSponsee;





    // boolean trandate=false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sponsorship, container, false);
        requireActivity().setTitle("Sponsorship");
        MainActivity.flag = 1;

        setHasOptionsMenu(true);



        addSponsee = view.findViewById(R.id.addSponsee);



        mySponseesRef = FirebaseDatabase.getInstance().getReference().child("Sponsee");
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();

        no_donations = view.findViewById(R.id.no_donations);


        txtLoading = view.findViewById(R.id.txtLoading);

        haveSponsees = false;

        mySponseesist = new ArrayList<>();
        mySponsorshipAdapter = new SponsorshipAdapter(mySponseesist, getContext());
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

        addSponsee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the SharedPreferences object
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("fragment", "toSponsorship");
                editor.apply();

                assert getFragmentManager() != null;
                getFragmentManager().beginTransaction().replace(R.id.content_frame, new Add_Sponsee()).addToBackStack(null).commit();


           /*     for (int i = 0; i < 10; i++) {

                    ModelSponsee modelSponsee = new ModelSponsee(
                            ""+i,
                            "" +"Lazarous"+i,
                            ""+"Deredza",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            ""

                    );


                    mySponseesist.add(modelSponsee);
                    no_donations.setVisibility(View.GONE);
                    my_sponsorship_recycler_view.setVisibility(View.VISIBLE);
                    //  loadRecylerView();
                    mySponsorshipAdapter = new SponsorshipAdapter(mySponseesist, getContext());
                    my_sponsorship_recycler_view.setAdapter(mySponsorshipAdapter);
                    mySponsorshipAdapter.notifyItemInserted(mySponseesist.size() - 1);
                    //  mySponsorshipAdapter.notifyDataSetChanged();

                }
*/



            }
        });


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
                            if (modelSponsee.getStatus().equals("Approved")) {
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
                        txtLoading.setText("There are no individuals or families to sponsor at the moment\nPlease check back later");
                        my_sponsorship_recycler_view.setVisibility(View.GONE);
                        progressDialog.dismiss();
                    }


                } else {
                    haveSponsees = false;
                    Log.e("TAG", "Donations : " + haveSponsees);
                    no_donations.setVisibility(View.VISIBLE);
                    txtLoading.setText("There are no individuals or families to sponsor at the moment\nPlease check back later");
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


        mySponsorshipAdapter = new SponsorshipAdapter(mySponseesist, getContext());
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
