package com.pamodzichild.Home;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.pamodzichild.MainActivity;
import com.pamodzichild.Profile.Profile;
import com.pamodzichild.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Home extends Fragment {
    View v;
    ImageView i;
    Animation rotate;

    String s;

    String uid;


    TextView hometext;

    DatabaseReference myDonationsRef;

    public static String companyEmail, storeName = "";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().setTitle("Home");

       myDonationsRef = FirebaseDatabase.getInstance().getReference("Donations");

        ArrayList<SlideModel> imageList = new ArrayList<>(); // Create image list

// imageList.add(new SlideModel("String Url" or R.drawable)
// imageList.add(new SlideModel("String Url" or R.drawable, "title") You can add title


        imageList.add(new SlideModel(R.drawable.img1, "Be the change you want to see.", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.img2, "Helping others is the greatest reward.", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.img3, "Small acts of kindness can change lives.", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.img4, "We rise by lifting others.", ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.img5, "Together we can make a difference.", ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.img6, "Be the reason someone smiles today.", ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.img7, "Spread love wherever you go.", ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.img8, "The world needs more kindness.", ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.img9, "Empathy is a superpower.", ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.img10, "Helping others brings joy to the soul.", ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.img11, "Giving is the ultimate form of living.", ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.img12, "A little help goes a long way.", ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.img13, "Be someone's sunshine on a cloudy day.", ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.img14, "Kindness is free, sprinkle that stuff everywhere.", ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.img15, "Give what you can, take what you need.", ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.img16, "The best way to find yourself is to lose yourself in the service of others.", ScaleTypes.FIT));


        ImageSlider imageSlider;
        imageSlider = v.findViewById(R.id.image_slider);
        imageSlider.setImageList(imageList);


        MainActivity.flag = 0;


        return v;


    }

    @Override
    public void onResume() {
        super.onResume();

        // Generate a new unique ID for the new record
        String key = myDonationsRef.push().getKey();

        Log.e("Key ", "Unique key : " + key);


        companyEmail = "lazarous@gmail.com";

        MainActivity.fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_person_outline_24));


        MainActivity.fab.hide();
        MainActivity.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                Profile profile = new Profile();
                fragmentManager.beginTransaction().replace(R.id.content_frame, profile).commit();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
