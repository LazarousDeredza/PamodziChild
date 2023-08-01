package com.pamodzichild.Users;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.pamodzichild.SignUp;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;

public class Users extends Fragment implements SearchView.OnQueryTextListener {
    View view;
    String uid;
    DatabaseReference usersRef;
    FirebaseAuth mAuth;
    ArrayList<UserModel> userList = new ArrayList<>();

    RecyclerView.LayoutManager layoutManager;
    RecyclerView my_sponsorship_recycler_view;
    UsersAdapter mySponsorshipAdapter;
    SearchView searchView;

    boolean haveSponsees;
    TextView txtLoading;


    LinearLayout no_donations;
    ProgressDialog progressDialog;

    Context context;
    ProgressBar progressBar;
    TextView totalUsers;
    FloatingActionButton add;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_users, container, false);
        getActivity().setTitle("Users");
        MainActivity.flag = 1;
        setHasOptionsMenu(true);


        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();

        totalUsers = view.findViewById(R.id.totalUsers);
        no_donations = view.findViewById(R.id.no_donations);
        add = view.findViewById(R.id.add);

        txtLoading = view.findViewById(R.id.txtLoading);

        haveSponsees = false;

        userList = new ArrayList<>();
        mySponsorshipAdapter = new UsersAdapter(userList, getContext());
        my_sponsorship_recycler_view = view.findViewById(R.id.recylerView);
//        my_sponsorship_recycler_view.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        my_sponsorship_recycler_view.setLayoutManager(layoutManager);

        mySponsorshipAdapter.addItemDecoration(my_sponsorship_recycler_view);
        progressDialog = new ProgressDialog(getContext());

        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);

        context = getContext();
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.fab.hide();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), SignUp.class);
                startActivity(intent);


            }
        });


        // ADAPTER


        txtLoading.setText("Loading  ...");


        progressDialog.setMessage("Preparing...");
        progressDialog.setTitle("Please wait");
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();

        userList.clear();


        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {

                    haveSponsees = false;
                    for (DataSnapshot s1 : snapshot.getChildren()) {
                        UserModel modelSponsee = s1.getValue(UserModel.class);
                        userList.add(modelSponsee);
                        haveSponsees = true;

                    }


                    if (haveSponsees) {
                        Log.e("TAG", " Donations are available : " + haveSponsees);
                        no_donations.setVisibility(View.GONE);
                        my_sponsorship_recycler_view.setVisibility(View.VISIBLE);
                        loadRecylerView();
                        progressDialog.dismiss();
                        progressBar.setVisibility(View.GONE);
                        totalUsers.setText(String.valueOf(userList.size()));

                    } else {

                        Log.e("TAG", "Donations : " + haveSponsees);
                        no_donations.setVisibility(View.VISIBLE);
                        txtLoading.setText("There are no users registered in the system");
                        my_sponsorship_recycler_view.setVisibility(View.GONE);
                        progressDialog.dismiss();
                        progressBar.setVisibility(View.GONE);
                    }


                } else {
                    haveSponsees = false;
                    Log.e("TAG", "Donations : " + haveSponsees);
                    no_donations.setVisibility(View.VISIBLE);
                    txtLoading.setText("There are no users registered in the system");
                    my_sponsorship_recycler_view.setVisibility(View.GONE);
                    progressDialog.dismiss();
                    progressBar.setVisibility(View.GONE);
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
            userList.sort(new Comparator<UserModel>() {

                @Override
                public int compare(UserModel modelDonation, UserModel t1) {

                    return t1.getFname().compareTo(modelDonation.getFname());

                }

            });
        }


        mySponsorshipAdapter = new UsersAdapter(userList, getContext());
        my_sponsorship_recycler_view.setAdapter(mySponsorshipAdapter);
        mySponsorshipAdapter.notifyItemInserted(userList.size() - 1);


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
