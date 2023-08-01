package com.pamodzichild.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.pamodzichild.MainActivity;
import com.pamodzichild.R;

public class Profile extends Fragment {
    View v;
    public Profile() {
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

        v=   inflater.inflate(R.layout.fragment_profile, container, false);
        requireActivity().setTitle("Profile");
        MainActivity.flag = 1;

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.fab.hide();
    }
}