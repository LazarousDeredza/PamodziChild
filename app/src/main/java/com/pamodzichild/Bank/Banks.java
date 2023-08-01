package com.pamodzichild.Bank;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
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
import java.util.Comparator;


public class Banks extends Fragment implements SearchView.OnQueryTextListener {


    View view;
    DatabaseReference myCPOrganisationsRef;
    FirebaseAuth mAuth;
    ArrayList<ModelBank> modelBanks;

    RecyclerView.LayoutManager layoutManager;
    RecyclerView my_organisations_recycler_view;
    BanksAdapter myOrganisationsAdapter;

    boolean haveOrganisations;
    TextView txtLoading;


    LinearLayout no_donations;
    ProgressDialog progressDialog;
    String uid;
    FloatingActionButton adddonationfab;

    Button addBank;

    SearchView searchView;

    public Banks() {
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
        view = inflater.inflate(R.layout.fragment_banks, container, false);
        getActivity().setTitle("Bank Accounts");
        MainActivity.flag = 1;

        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);

        addBank = view.findViewById(R.id.addCPOrg);
        adddonationfab = view.findViewById(R.id.adddonationfab);


        myCPOrganisationsRef = FirebaseDatabase.getInstance().getReference().child("Banks");
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();

        no_donations = view.findViewById(R.id.no_donations);


        txtLoading = view.findViewById(R.id.txtLoading);

        haveOrganisations = false;

        modelBanks = new ArrayList<>();
        myOrganisationsAdapter = new BanksAdapter(modelBanks, getContext());
        my_organisations_recycler_view = view.findViewById(R.id.my_organisations_recycler_view);
//        my_organisations_recycler_view.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        my_organisations_recycler_view.setLayoutManager(layoutManager);

        myOrganisationsAdapter.addItemDecoration(my_organisations_recycler_view);
        progressDialog = new ProgressDialog(getContext());


//There are no Organisations that you have added\nPlease add some

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

        modelBanks.clear();


        myCPOrganisationsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {

                    haveOrganisations = true;
                    for (DataSnapshot s1 : snapshot.getChildren()) {
                        ModelBank modelCPOrganisation = s1.getValue(ModelBank.class);
                        modelBanks.add(modelCPOrganisation);

                    }
                    loadRecylerView();
                    progressDialog.dismiss();
                    no_donations.setVisibility(View.GONE);
                    my_organisations_recycler_view.setVisibility(View.VISIBLE);


                } else {
                    haveOrganisations = false;

                    no_donations.setVisibility(View.VISIBLE);
                    txtLoading.setText("There are no banks Added\nPlease add some");
                    my_organisations_recycler_view.setVisibility(View.GONE);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        addBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Bundle bundle = new Bundle();
                bundle.putString("mode", "add");

                SharedPreferences.Editor editor = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
                editor.putString("fragment", "toBanks");
                editor.apply();


                EditBank editBank = new EditBank();
                editBank.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, editBank).commit();


            }
        });

    }


    private void loadRecylerView() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            modelBanks.sort(new Comparator<ModelBank>() {


                @Override
                public int compare(ModelBank org1, ModelBank org2) {
                    return org1.getBankName().compareTo(org2.getBankName());
                }

            });
        }


        myOrganisationsAdapter = new BanksAdapter(modelBanks, getContext());
        my_organisations_recycler_view.setAdapter(myOrganisationsAdapter);
        myOrganisationsAdapter.notifyItemInserted(modelBanks.size() - 1);


    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        myOrganisationsAdapter.getFilter().filter(newText);
        return false;
    }

}