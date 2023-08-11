package com.pamodzichild.About;

import android.graphics.text.LineBreaker;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.pamodzichild.MainActivity;
import com.pamodzichild.R;


public class About extends Fragment {

    View v;
    TextView txtAbout;

    public About() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_about, container, false);
        requireActivity().setTitle("About");
        MainActivity.flag = 1;
        // Inflate the layout for this fragment

        txtAbout = v.findViewById(R.id.txtAbout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            txtAbout.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        }

        return v;
    }

    @Override
    public void onResume() {
        MainActivity.fab = requireActivity().findViewById(R.id.fab);
        MainActivity.fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_person_outline_24));
       MainActivity.fab.hide();
        super.onResume();
    }
}