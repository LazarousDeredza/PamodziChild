package com.pamodzichild;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.pamodzichild.R;
import com.pamodzichild.Users.UserModel;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SignUp extends AppCompatActivity {

    Button btnRegister, btnLogin;
    DatabaseReference reference;

    TextInputLayout Fname, Lname, Email, Pass, cpass, mobileno, localaddress;
    Spinner Townspin, Cityspin;
    CountryCodePicker Cpp;
    FirebaseAuth FAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    String fname, lname, emailid, password, confpassword, mobile, Localaddress;
    String role = "User";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);

        Fname = (TextInputLayout) findViewById(R.id.Fname);
        Lname = (TextInputLayout) findViewById(R.id.Lname);
        Email = (TextInputLayout) findViewById(R.id.Emailid);
        Pass = (TextInputLayout) findViewById(R.id.Password);
        cpass = (TextInputLayout) findViewById(R.id.confirmpass);
        mobileno = (TextInputLayout) findViewById(R.id.Mobilenumber);
        localaddress = (TextInputLayout) findViewById(R.id.Localaddress);


        Cpp = (CountryCodePicker) findViewById(R.id.CountryCode);

        databaseReference = firebaseDatabase.getInstance().getReference("Users");
        FAuth = FirebaseAuth.getInstance();


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFirstName() && validateLastName() && validateEmail() && validatePassword() && validateConfirmPassword() && validateMobileNo() && validateLocalAddress()) {

                    String user_email = Email.getEditText().getText().toString().trim();
                    String user_password = Pass.getEditText().getText().toString().trim();

                    String fname = Fname.getEditText().getText().toString().trim();
                    String lname = Lname.getEditText().getText().toString().trim();
                    String mobile = Cpp.getSelectedCountryCodeWithPlus() + mobileno.getEditText().getText().toString().trim();
                    String Localaddress = localaddress.getEditText().getText().toString().trim();

                    String country = Cpp.getSelectedCountryName();
                    String CountryNameCode = Cpp.getSelectedCountryNameCode();


                    ProgressDialog dialog = new ProgressDialog(SignUp.this);
                    dialog.setTitle("Registering");
                    dialog.setMessage("Please wait...");
                    dialog.setCancelable(false);
                    dialog.show();


                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    String currentDate = dateFormat.format(new Date());





                    FAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {

                            String id= FirebaseAuth.getInstance().getCurrentUser().getUid();

                            UserModel user = new UserModel(""+fname,
                                    ""+lname,
                                    ""+user_email,
                                    ""+ user_password,
                                    ""+ mobile,
                                    ""+ Localaddress,
                                    ""+role,
                                    ""+Cpp.getSelectedCountryCodeWithPlus(),
                                    ""+mobileno.getEditText().getText().toString().trim(),
                                    ""+country,
                                    ""+CountryNameCode);
                            user.setPhoto("");
                            user.setDateRegistered(currentDate);
                            user.setId(id);



                            Log.e("TAG", "onClick: " + user.toString());





                            FirebaseDatabase.getInstance().getReference("Users").child(id).setValue(user).addOnCompleteListener(task1 -> {

                                if (task1.isSuccessful()) {


                                    dialog.dismiss();

                                    Dialog dialog1 = new Dialog(SignUp.this);
                                    dialog1.setContentView(R.layout.verify_email_dialog);
                                    dialog1.setCancelable(false);
                                    dialog1.getWindow().setLayout(900, 1100);

                                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                                    lp.copyFrom(dialog1.getWindow().getAttributes());
                                    lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                                    lp.gravity = Gravity.CENTER;

                                    dialog1.getWindow().setAttributes(lp);

                                    dialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                    dialog1.findViewById(R.id.verify_email_ok_btn).setOnClickListener(v1 -> {
                                        dialog1.dismiss();
                                        clear();
                                        startActivity(new Intent(SignUp.this, Login.class));
                                        finish();
                                    });

                                    dialog1.findViewById(R.id.verify_email_close_btn).setOnClickListener(v1 -> {
                                        dialog1.dismiss();
                                        clear();
                                    });


                                    dialog1.show();


                                } else {
                                    dialog.dismiss();
                                    Toast.makeText(SignUp.this, task1.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                }

                            });

                        } else {

                            dialog.dismiss();
                            Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    });

                }

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                startActivity(new Intent(SignUp.this, Login.class));
                finish();

            }
        });


    }

    private void clear() {

        Fname.getEditText().setText("");
        Lname.getEditText().setText("");
        Email.getEditText().setText("");
        Pass.getEditText().setText("");
        cpass.getEditText().setText("");
        mobileno.getEditText().setText("");
        localaddress.getEditText().setText("");

    }

    private boolean validateFirstName() {

        String val = Fname.getEditText().getText().toString().trim();

        if (val.isEmpty()) {

            Fname.setError("Field cannot be empty");
            return false;

        } else {

            Fname.setError(null);
            Fname.setErrorEnabled(false);
            return true;

        }

    }

    private boolean validateLastName() {

        String val = Lname.getEditText().getText().toString().trim();

        if (val.isEmpty()) {

            Lname.setError("Field cannot be empty");
            return false;

        } else {

            Lname.setError(null);
            Lname.setErrorEnabled(false);
            return true;

        }

    }

    private boolean validateEmail() {

        String val = Email.getEditText().getText().toString().trim();
        String checkemail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {

            Email.setError("Field cannot be empty");
            return false;

        } else if (!val.matches(checkemail)) {

            Email.setError("Invalid Email!");
            return false;

        } else {

            Email.setError(null);
            Email.setErrorEnabled(false);
            return true;

        }

    }

    private boolean validatePassword() {

        String val = Pass.getEditText().getText().toString().trim();
        String checkPassword = "^" +
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {

            Pass.setError("Field cannot be empty");
            return false;

        } else if (!val.matches(checkPassword)) {

            Pass.setError("Password should contain at least 4 characters!");
            return false;

        } else {

            Pass.setError(null);
            Pass.setErrorEnabled(false);
            return true;

        }

    }

    private boolean validateConfirmPassword() {

        String val = cpass.getEditText().getText().toString().trim();
        String val1 = Pass.getEditText().getText().toString().trim();

        if (val.isEmpty()) {

            cpass.setError("Field cannot be empty");
            return false;

        } else if (!val.matches(val1)) {

            cpass.setError("Password does not match!");
            return false;

        } else {

            cpass.setError(null);
            cpass.setErrorEnabled(false);
            return true;

        }

    }

    private boolean validateMobileNo() {

        String val = mobileno.getEditText().getText().toString().trim();

        if (val.isEmpty()) {

            mobileno.setError("Field cannot be empty");
            return false;

        } else {

            mobileno.setError(null);
            mobileno.setErrorEnabled(false);
            return true;

        }

    }

    private boolean validateLocalAddress() {

        String val = localaddress.getEditText().getText().toString().trim();

        if (val.isEmpty()) {

            localaddress.setError("Field cannot be empty");
            return false;

        } else {

            localaddress.setError(null);
            localaddress.setErrorEnabled(false);
            return true;

        }

    }
}