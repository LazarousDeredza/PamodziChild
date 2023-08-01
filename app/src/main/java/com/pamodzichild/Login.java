package com.pamodzichild;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.pamodzichild.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    Button btnRegister, btnLogin;
    FirebaseAuth firebaseAuth;

    TextInputLayout Lemail, Lpassword;
    TextInputEditText email, password;
    DatabaseReference databaseReference;

    TextView forgotpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);
        forgotpass = findViewById(R.id.forgotpass);

        firebaseAuth = FirebaseAuth.getInstance();

        Lemail = findViewById(R.id.Lemail);
        Lpassword = findViewById(R.id.Lpassword);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);


            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Email = email.getText().toString().trim();
                String Password = password.getText().toString().trim();

                if (Email.isEmpty()) {
                    Lemail.setError("Email is required");
                    Lemail.requestFocus();
                    return;
                } else if (Password.isEmpty()) {
                    Lpassword.setError("Password is required");
                    Lpassword.requestFocus();
                    return;

                } else {
                    ProgressDialog progressDialog = new ProgressDialog(Login.this);
                    progressDialog.setMessage("Please wait...");
                    progressDialog.setTitle("Signing in !!!");
                    progressDialog.show();
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();


                    firebaseAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                // Sign in success



                                String id = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.child(id).exists()) {
                                            progressDialog.dismiss();
                                            Toast.makeText(Login.this, "Logged in successfully",
                                                    Toast.LENGTH_SHORT).show();


                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                            finish();
                                        }else {
                                            progressDialog.dismiss();
                                            Toast.makeText(Login.this, "This account was deleted",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });





                                // do something with the user object
                            } else {
                                // Sign in failed
                                progressDialog.dismiss();
                                Toast.makeText(Login.this, task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }


                        }
                    });


                }

            }
        });



        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final TextInputEditText resetMail = new TextInputEditText(view.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Enter your email to receive reset link");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Send", (dialogInterface, i) -> {
                    //extract the email and send reset link
                    String mail = resetMail.getText().toString().trim();
                    firebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener(aVoid ->
                            Toast.makeText(Login.this, "Reset link sent to your email", Toast.LENGTH_SHORT).show()
                    ).addOnFailureListener(e ->
                            Toast.makeText(Login.this, "Error! Reset link is not sent" + e.getMessage(), Toast.LENGTH_SHORT).show()
                    );
                });

                passwordResetDialog.setNegativeButton("Cancel", (dialogInterface, i) -> {
                    //close the dialog
                    passwordResetDialog.create().dismiss();

                });

                passwordResetDialog.create().show();
            }
        });

    }
}