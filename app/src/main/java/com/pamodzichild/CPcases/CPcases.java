package com.pamodzichild.CPcases;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pamodzichild.MainActivity;
import com.pamodzichild.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CPcases extends Fragment  implements SearchView.OnQueryTextListener{
    View v;

    RecyclerView rv;
    LinearLayoutManager layoutManager;

    ArrayList<ModelCPcases> list;

    MyCPAdapter adapter;

    DatabaseReference databaseReference;
    SearchView searchView;

    // Define an integer constant to identify the permission request
    private static final int INTERNET_PERMISSION_REQUEST_CODE = 1;

    public CPcases() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_c_r_p_cases, container, false);
        getActivity().setTitle("CP Cases");
        MainActivity.flag = 1;
        setHasOptionsMenu(true);




        rv = (RecyclerView) v.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layoutManager);

        list = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("CPcases");

        searchView = v.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        MainActivity.fab.setImageDrawable(getResources().getDrawable(R.drawable.add));
        MainActivity.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Get the SharedPreferences object
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("fragment", "toCpcases");
                editor.apply();


                Fragment fragment = new Add_CP();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            }


        });
        MainActivity.fab.show();
        // ADAPTER




        //loadTempData();
        //loadData();

        ProgressBar progressBar = v.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                    ModelCPcases model = dataSnapshot1.getValue(ModelCPcases.class);

                   Log.e("", model.toString());


                    list.add(model);


                }
                Collections.sort(list, new Comparator<ModelCPcases>() {
                    @Override
                    public int compare(ModelCPcases o1, ModelCPcases o2) {
                        return o2.getDateOfReporting().compareTo(o1.getDateOfReporting());
                    }
                });
                adapter = new MyCPAdapter(getActivity(), list);
                rv.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);

                TextView noData = v.findViewById(R.id.noData);

                if (list.size() == 0) {
                    noData.setVisibility(View.VISIBLE);
                } else {
                    noData.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
               // Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });


        adapter = new MyCPAdapter(getActivity(), list);

        rv.setAdapter(adapter);


    }

  /*  static void loadData() {
        ProgressBar progressBar = v.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                    ModelCPcases model = dataSnapshot1.getValue(ModelCPcases.class);
                    list.add(model);


                }
                Collections.sort(list, new Comparator<ModelCPcases>() {
                    @Override
                    public int compare(ModelCPcases o1, ModelCPcases o2) {
                        return o2.getDateOfReporting().compareTo(o1.getDateOfReporting());
                    }
                });
                adapter = new MyCPAdapter(getActivity(), list);
                rv.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);

                TextView noData = v.findViewById(R.id.noData);

                if (list.size() == 0) {
                    noData.setVisibility(View.VISIBLE);
                } else {
                    noData.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });


        adapter = new MyCPAdapter(getActivity(), list);

        rv.setAdapter(adapter);

    }*/




    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == INTERNET_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                Uri uri = Uri.parse("https://keshokenya.org/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            } else {
                // Permission is not granted
                Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }



    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
        return false;
    }

}
