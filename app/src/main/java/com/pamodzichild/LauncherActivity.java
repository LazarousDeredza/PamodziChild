package com.pamodzichild;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.pamodzichild.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;
import java.util.Random;


public class LauncherActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN = 7000;

    //variables
    Animation topAnim, bottomAnim;
    ImageView image;
    TextView logo, slogan;
    FirebaseAuth Fauth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_launcher);
        checkPermissions();

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        //animation
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);


        Random random = new Random();
        int randomNumber = random.nextInt(6) + 1;

// Use the randomNumber variable as needed

        String name = "launcher" + randomNumber;


        //hooks
        image = findViewById(R.id.imageView);
        image.setImageDrawable(getResources().getDrawable(getResources().getIdentifier(name, "drawable", getPackageName())));
        logo = findViewById(R.id.textView);
        slogan = findViewById(R.id.textView2);

        image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);


        TypeWriter typeWriter = (TypeWriter) findViewById(R.id.textView2);
        typeWriter.setCharacterDelay(40);
        typeWriter.animateText("Pamodzi Child");


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                Fauth = FirebaseAuth.getInstance();
                if (Fauth.getCurrentUser() != null) {


                    Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                    String uid = Fauth.getCurrentUser().getUid();

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.child(uid).exists()) {
                                Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Intent intent = new Intent(LauncherActivity.this, Login.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                } else {


                    Intent intent = new Intent(LauncherActivity.this, Login.class);
                    startActivity(intent);
                    finish();
                }


            }
        }, SPLASH_SCREEN);
    }


    public void checkPermissions() {
        Dexter.withContext(this)
                .withPermissions(


                       Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.INTERNET,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.RECEIVE_SMS,
                        Manifest.permission.READ_SMS,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA


                ).withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {/* ... */}

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

}