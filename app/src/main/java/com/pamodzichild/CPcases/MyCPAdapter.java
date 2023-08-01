package com.pamodzichild.CPcases;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.pamodzichild.MainActivity;
import com.pamodzichild.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class MyCPAdapter extends RecyclerView.Adapter<MyCPAdapter.MyCPViewHolder> implements Filterable {


    Context c;

    String uid;
    ArrayList<ModelCPcases> models, filterList;

    CustomCRPFilter filter;
    String level = "user";


    // MyDbHelper dbHelper;

    public MyCPAdapter(Context c, ArrayList<ModelCPcases> stockModels) {

        this.c = c;
        this.models = stockModels;
        this.filterList = stockModels;
    }

    @Override
    public MyCPViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(c).inflate(R.layout.crp_layout_card, parent, false);
        // dbHelper = new MyDbHelper(c);
        return new MyCPViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public void onBindViewHolder(final MyCPViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        //  level=dbHelper.getLogged("1").getLevel();

        String dobString = models.get(position).getChildAge();


// Create a SimpleDateFormat object with the format "dd/MM/yyyy"
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            // Parse the string into a Date object
            Date dob = dateFormat.parse(dobString);

            // Get the current date
            Calendar today = Calendar.getInstance();

            // Get the date of birth as a Calendar object
            Calendar dobCal = Calendar.getInstance();
            dobCal.setTime(dob);

            // Calculate the difference between the two dates in years
            int age = today.get(Calendar.YEAR) - dobCal.get(Calendar.YEAR);
            if (today.get(Calendar.MONTH) < dobCal.get(Calendar.MONTH) ||
                    (today.get(Calendar.MONTH) == dobCal.get(Calendar.MONTH) && today.get(Calendar.DAY_OF_MONTH) < dobCal.get(Calendar.DAY_OF_MONTH))) {
                age--;
            }

            holder.childage.setText(String.valueOf(age));

            // The variable "age" now contains the age in years
            // You can use this value as needed in your application

        } catch (ParseException e) {
            holder.childage.setText(models.get(position).getChildAge());
        }


        holder.childName.setText(models.get(position).getNameOfChild());
        holder.dateReported.setText(models.get(position).getDateOfReporting());
        holder.childgender.setText(models.get(position).getGenderOfChild());
        holder.childlocation.setText(models.get(position).getChildLocation());
        holder.reporterName.setText(models.get(position).getReporter().getReporterName());
        holder.stateofchild.setText(models.get(position).getCurrentStateOfChild());
        holder.typeofabuse.setText(models.get(position).getTypeOfAbuse());
        holder.reporterGender.setText(models.get(position).getReporter().getReporterGender());
        holder.reporterphone.setText(models.get(position).getReporter().getReporterPhoneNo());
        holder.anyofferedservices.setText(models.get(position).getReporter().getAnyserviceProvided());
        holder.reporteroccupation.setText(models.get(position).getReporter().getReporterOccupation());
        holder.perpetratorDetails.setText(models.get(position).getDetailsOfPerpetrator());

        String howitwashandled = models.get(position).getHowitwashandled();
        Log.e("howitwashandled", howitwashandled);
        if (howitwashandled.length()==0||howitwashandled.equals("null")||howitwashandled.equals("none")||howitwashandled.equals("None")||howitwashandled.equals("NONE")) {
            holder.HowitwasHandledLayout.setVisibility(View.GONE);
        } else {
            holder.HowitwasHandled.setText(models.get(position).getHowitwashandled());
            holder.HowitwasHandledLayout.setVisibility(View.VISIBLE);

        }


        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (holder.gone.getVisibility() == View.VISIBLE) {
                    holder.gone.setVisibility(View.GONE);
                    holder.cv2.setImageDrawable(c.getResources().getDrawable(R.drawable.expanddown));

                } else {
                    holder.gone.setVisibility(View.VISIBLE);
                    holder.cv2.setImageDrawable(c.getResources().getDrawable(R.drawable.collapseup));
                }
            }
        });



        if (MainActivity.Ulevel.equals("user")) {
            holder.editBtn.setVisibility(View.GONE);
            holder.delbtn.setVisibility(View.GONE);
        } else {
            holder.editBtn.setVisibility(View.VISIBLE);
            holder.delbtn.setVisibility(View.VISIBLE);
        }

        holder.delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(c);
                builder.setTitle("Delete");
                builder.setMessage("Are you sure you want to delete this case?");
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    ProgressDialog progressDialog = new ProgressDialog(c);
                    progressDialog.setMessage("Deleting...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setCancelable(false);
                    progressDialog.show();


                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    ref.child("CPcases").child(models.get(position).getId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            Toast.makeText(c, "Deleted", Toast.LENGTH_SHORT).show();

                            ref.child("CPcases").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    models.clear();
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                        ModelCPcases model = dataSnapshot.getValue(ModelCPcases.class);
                                        models.add(model);
                                    }

                                    progressDialog.dismiss();
                                    notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    });


                });
                builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
                AlertDialog alert = builder.create();
                alert.show();






            }
        });


        holder.caseID.setText(models.get(position).getId());
        String status = models.get(position).getCasesStatus().trim().toLowerCase();

        // Get a reference to the button
        Button caseIDbtn = holder.itemView.findViewById(R.id.caseIDbtn);

        if (status.equals("open")) {
            Log.e("Status", status);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                caseIDbtn.setBackgroundTintList(ContextCompat.getColorStateList(c, R.color.yellow));
            }
        } else if (status.equals("closed")) {
            Log.e("Status", status);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                caseIDbtn.setBackgroundTintList(ContextCompat.getColorStateList(c, R.color.green));
            }
        } else {
            // Handle other cases here
            Log.e("Status", "Invalid status: " + status);
        }




        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {







                final TextInputEditText editText = new TextInputEditText(c);
                editText.setHint("Type Here !!");
                editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
                editText.setPadding(20, 4, 20, 10);
                editText.setGravity(Gravity.TOP | Gravity.START);
                editText.setVerticalScrollBarEnabled(true);
                editText.setMovementMethod(ScrollingMovementMethod.getInstance());
                editText.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
                editText.setScrollBarSize(20);
                editText.setScrollBarFadeDuration(2000);
                editText.setScrollBarDefaultDelayBeforeFade(1000);



                androidx.appcompat.app.AlertDialog.Builder passwordResetDialog = new androidx.appcompat.app.AlertDialog.Builder(c);
                passwordResetDialog.setTitle("Case Edit");
                passwordResetDialog.setMessage("Enter how the case was handled");
                passwordResetDialog.setView(editText);

                passwordResetDialog.setPositiveButton("Submit", (dialogInterface, i) -> {
                    //extract the email and send reset link
                    String howitwashandled = editText.getText().toString().trim();

                    if (howitwashandled.length() == 0) {
                        editText.setError("Please enter how the case was handled");
                        editText.requestFocus();

                    }else {

                        ProgressDialog progressDialog = new ProgressDialog(c);
                        progressDialog.setMessage("Updating...");
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();



                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("CPcases").child(models.get(position).getId());
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()){

                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("howitwashandled", howitwashandled);
                                    hashMap.put("casesStatus", "Closed");

                                    reference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                progressDialog.dismiss();
                                                Toast.makeText(c, "Case Updated Successfully", Toast.LENGTH_SHORT).show();
                                                holder.HowitwasHandled.setText(howitwashandled);
                                                holder.HowitwasHandledLayout.setVisibility(View.VISIBLE);
                                                holder.gone.setVisibility(View.VISIBLE);
                                                holder.cv2.setImageDrawable(c.getResources().getDrawable(R.drawable.collapseup));

                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                                    caseIDbtn.setBackgroundTintList(ContextCompat.getColorStateList(c, R.color.green));
                                                }


                                            } else {
                                                progressDialog.dismiss();
                                                Toast.makeText(c, "Error Updating Case", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });



                    }



                });

                passwordResetDialog.setNegativeButton("Cancel", (dialogInterface, i) -> {
                    //close the dialog
                    passwordResetDialog.create().dismiss();

                });

                passwordResetDialog.create().show();



            }
        });

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {


                FilterResults results = new FilterResults();
                // CHECK CONSTRAINT VALIDITY
                if (constraint != null && constraint.length() > 0) {
                    // CHANGE TO UPPER
                    constraint = constraint.toString().toUpperCase();
                    // STORE OUR FILTERED PLAYERS
                    Log.e("Constraint", constraint.toString());
                    ArrayList<ModelCPcases> filteredPlayers = new ArrayList<>();
                    for (int i = 0; i < filterList.size(); i++) {

                        String name = filterList.get(i).getNameOfChild().trim().toUpperCase();

                        // CHECK
                        if (
                                filterList.get(i).getTimeOfReporting().toUpperCase().contains(constraint) ||
                                        filterList.get(i).getDetailsOfPerpetrator().toUpperCase().contains(constraint) ||
                                        filterList.get(i).getChildLocation().toUpperCase().contains(constraint) ||
                                        filterList.get(i).getId().toUpperCase().contains(constraint) ||
                                        filterList.get(i).getDateOfReporting().toUpperCase().contains(constraint) ||
                                        filterList.get(i).getId().toUpperCase().contains(constraint) ||

                                        name.contains(constraint)


                        ) {
                            // ADD PLAYER TO FILTERED PLAYERS
                            filteredPlayers.add(filterList.get(i));
                            Log.e("Found ", "True");

                        }
                    }
                    results.count = filteredPlayers.size();
                    results.values = filteredPlayers;
                } else {
                    results.count = filterList.size();
                    results.values = filterList;
                }
                return results;


            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                // Log.d("TAG", "publishResults: " + results.values);

                models = (ArrayList<ModelCPcases>) results.values;

                notifyDataSetChanged();
            }
        };
    }


    public class MyCPViewHolder extends RecyclerView.ViewHolder {

        TextView caseID, childName, reporterName, stateofchild, childgender, dateReported, perpetratorDetails,
                childlocation, typeofabuse, reporterGender, childage, reporterphone, anyofferedservices, reporteroccupation;
        ImageView editBtn, delbtn;
        ImageView cv2;
        LinearLayout gone;
        CardView cv;
        Button caseIDbtn;
        TextView HowitwasHandled;
        LinearLayout HowitwasHandledLayout;

        public MyCPViewHolder(View itemView) {
            super(itemView);

            cv = (CardView) itemView.findViewById(R.id.cv);
            gone = (LinearLayout) itemView.findViewById(R.id.gone_layout);

            childName = (TextView) itemView.findViewById(R.id.childName);
            reporterName = (TextView) itemView.findViewById(R.id.reporterName);
            stateofchild = (TextView) itemView.findViewById(R.id.stateofchild);
            childgender = (TextView) itemView.findViewById(R.id.childgender);
            dateReported = (TextView) itemView.findViewById(R.id.dateReported);
            childlocation = (TextView) itemView.findViewById(R.id.childlocation);
            typeofabuse = (TextView) itemView.findViewById(R.id.typeofabuse);
            reporterGender = (TextView) itemView.findViewById(R.id.reporterGender);
            reporterphone = (TextView) itemView.findViewById(R.id.reporterphone);
            childage = (TextView) itemView.findViewById(R.id.childage);
            anyofferedservices = (TextView) itemView.findViewById(R.id.anyofferedservices);
            reporteroccupation = (TextView) itemView.findViewById(R.id.reporteroccupation);
            perpetratorDetails = (TextView) itemView.findViewById(R.id.perpetratorDetails);
            cv2 = (ImageView) itemView.findViewById(R.id.cv2);


            editBtn = (ImageView) itemView.findViewById(R.id.editbtn);
            delbtn = (ImageView) itemView.findViewById(R.id.delbtn);

            caseIDbtn = (Button) itemView.findViewById(R.id.caseIDbtn);
            caseID = (TextView) itemView.findViewById(R.id.caseID);

            HowitwasHandled = (TextView) itemView.findViewById(R.id.HowitwasHandled);
            HowitwasHandledLayout = (LinearLayout) itemView.findViewById(R.id.HowitwasHandledLayout);
        }
    }

}