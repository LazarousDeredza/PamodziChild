package com.pamodzichild;

import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;

public class SignOutGeneric {

    FirebaseAuth mAuth;

    public void signOut() {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        Intent intent = new Intent(MainActivity.context, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        MainActivity.context.startActivity(intent);
        MainActivity.activity.finish();

    }
}
