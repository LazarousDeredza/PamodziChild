package com.pamodzichild.Organisations;

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


public class CPOrganisations extends Fragment implements SearchView.OnQueryTextListener {


    View view;
    DatabaseReference myCPOrganisationsRef;
    FirebaseAuth mAuth;
    ArrayList<ModelCPOrganisation> myCPOrganisationsList;

    RecyclerView.LayoutManager layoutManager;
    RecyclerView my_organisations_recycler_view;
    CPOrganisationsAdapter myOrganisationsAdapter;

    boolean haveOrganisations;
    TextView txtLoading;
    TextView txtDonated, txtAvailable, txtUnverified, txtRejected;

    LinearLayout no_donations;
    ProgressDialog progressDialog;
    String uid;
    FloatingActionButton adddonationfab;

    Button addCPOrg;

    SearchView searchView;

    public CPOrganisations() {
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
        view = inflater.inflate(R.layout.fragment_c_p_organisations, container, false);
        getActivity().setTitle("CP Organisations");
        MainActivity.flag = 1;

        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);

        addCPOrg = view.findViewById(R.id.addCPOrg);
        adddonationfab = view.findViewById(R.id.adddonationfab);


        myCPOrganisationsRef = FirebaseDatabase.getInstance().getReference().child("Organisations");
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();

        no_donations = view.findViewById(R.id.no_donations);


        txtLoading = view.findViewById(R.id.txtLoading);

        haveOrganisations = false;

        myCPOrganisationsList = new ArrayList<>();
        myOrganisationsAdapter = new CPOrganisationsAdapter(myCPOrganisationsList, getContext());
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

        myCPOrganisationsList.clear();


        myCPOrganisationsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {

                    haveOrganisations = true;
                    for (DataSnapshot s1 : snapshot.getChildren()) {
                        ModelCPOrganisation modelCPOrganisation = s1.getValue(ModelCPOrganisation.class);
                        myCPOrganisationsList.add(modelCPOrganisation);

                    }
                    loadRecylerView();
                    progressDialog.dismiss();
                    no_donations.setVisibility(View.GONE);
                    my_organisations_recycler_view.setVisibility(View.VISIBLE);


                } else {
                    haveOrganisations = false;
                    Log.e("TAG", "Have Organisations : " + haveOrganisations);
                    no_donations.setVisibility(View.VISIBLE);
                    txtLoading.setText("There are no Organisations that you have added\nPlease add some");
                    my_organisations_recycler_view.setVisibility(View.GONE);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        addCPOrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Bundle bundle = new Bundle();
                bundle.putString("mode", "add");

                SharedPreferences.Editor editor = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
                editor.putString("fragment", "toOrganisations");
                editor.apply();


                EditOrganisation addCPOrganisation = new EditOrganisation();
                addCPOrganisation.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, addCPOrganisation).commit();


            }
        });

    }


    private void loadRecylerView() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            myCPOrganisationsList.sort(new Comparator<ModelCPOrganisation>() {


                @Override
                public int compare(ModelCPOrganisation org1, ModelCPOrganisation org2) {
                    return org1.getName().compareTo(org2.getName());
                }

            });
        }


        myOrganisationsAdapter = new CPOrganisationsAdapter(myCPOrganisationsList, getContext());
        my_organisations_recycler_view.setAdapter(myOrganisationsAdapter);
        myOrganisationsAdapter.notifyItemInserted(myCPOrganisationsList.size() - 1);


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