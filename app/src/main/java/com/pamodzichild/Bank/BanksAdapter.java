package com.pamodzichild.Bank;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pamodzichild.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class BanksAdapter extends RecyclerView.Adapter<BanksAdapter.MyViewHolder> implements Filterable {

    ArrayList<ModelBank> myDonationsList, filterList;

    Context c;

    public BanksAdapter(ArrayList<ModelBank> myDonationsList, Context c) {
        this.myDonationsList = myDonationsList;
        this.c = c;
        this.filterList = myDonationsList;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(c).inflate(R.layout.bank_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ModelBank modelCPOrganisation = myDonationsList.get(position);

        String id = modelCPOrganisation.getId();

        String bankName = modelCPOrganisation.getBankName();
        String accountOwner = modelCPOrganisation.getAccountOwner();
        String accountNumber = modelCPOrganisation.getAccountNumber();
        String status = modelCPOrganisation.getStatus();

        if (status.equals("active")) {
            holder.txtStatus.setText("Active");
            holder.txtStatus.setTextColor(ContextCompat.getColor(c, R.color.green));
            holder.txtEnable.setEnabled(false);
            holder.txtDisable.setEnabled(true);
        } else if (status.equals("inactive")) {
            holder.txtStatus.setText("Inactive");
            holder.txtStatus.setTextColor(ContextCompat.getColor(c, R.color.red));
            holder.txtEnable.setEnabled(true);
            holder.txtDisable.setEnabled(false);
        }

        // final String[] organisationWebsite = {modelCPOrganisation.getWebsite()};


        holder.txtBankName.setText(bankName);
        holder.txtAccountOwner.setText(accountOwner);
        holder.txtAccountNumber.setText(accountNumber);


        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Redirect to edit
                SharedPreferences.Editor editor = c.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).edit();
                editor.putString("fragment", "toBanks");
                editor.apply();

                Fragment fragment = new EditBank();
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                bundle.putString("mode", "edit");

                fragment.setArguments(bundle);

                FragmentManager fragmentManager = ((FragmentActivity) c).getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();


            }
        });


        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Delete organisation

                AlertDialog.Builder builder = new AlertDialog.Builder(c);
                builder.setTitle("Delete Account");
                builder.setMessage("Are you sure you want to delete this account?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Banks");
                        databaseReference.child(id).removeValue();

                        Toast.makeText(c, "Account deleted successfully", Toast.LENGTH_SHORT).show();

                        Fragment fragment = new Banks();
                        FragmentManager fragmentManager = ((FragmentActivity) c).getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

                        SharedPreferences.Editor editor = c.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).edit();
                        editor.putString("fragment", "toHome");

                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();

                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });


        holder.txtEnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog progressDialog = new ProgressDialog(c);
                progressDialog.setMessage("Enabling account...");
                progressDialog.show();

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Banks");
                databaseReference.child(id).child("status").setValue("active").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(c, "Account enabled successfully", Toast.LENGTH_SHORT).show();
                        holder.txtStatus.setText("Active");
                        holder.txtStatus.setTextColor(ContextCompat.getColor(c, R.color.green));
                        holder.txtEnable.setEnabled(false);

                        Fragment fragment = new Banks();
                        FragmentManager fragmentManager = ((FragmentActivity) c).getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

                        SharedPreferences.Editor editor = c.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).edit();
                        editor.putString("fragment", "toHome");

                    }
                });


            }
        });


        holder.txtDisable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog progressDialog = new ProgressDialog(c);
                progressDialog.setMessage("Disabling account...");
                progressDialog.show();

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Banks");
                databaseReference.child(id).child("status").setValue("inactive").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(c, "Account disabled successfully", Toast.LENGTH_SHORT).show();
                        holder.txtStatus.setText("Inactive");
                        holder.txtStatus.setTextColor(ContextCompat.getColor(c, R.color.red));
                        holder.txtDisable.setEnabled(false);

                        Fragment fragment = new Banks();
                        FragmentManager fragmentManager = ((FragmentActivity) c).getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

                        SharedPreferences.Editor editor = c.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).edit();
                        editor.putString("fragment", "toHome");

                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {

        if (myDonationsList == null) {
            return 0;
        } else {
            return myDonationsList.size();
        }


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView txtBankName, txtAccountOwner, txtAccountNumber, txtStatus;

        TextView txtEnable, txtDisable;

        ImageView imgEdit, imgDelete;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            txtBankName = itemView.findViewById(R.id.txtBankName);
            txtAccountOwner = itemView.findViewById(R.id.txtAccountOwner);
            txtAccountNumber = itemView.findViewById(R.id.txtAccountNumber);
            txtStatus = itemView.findViewById(R.id.txtStatus);

            imgEdit = itemView.findViewById(R.id.imgEdit);
            imgDelete = itemView.findViewById(R.id.imgDelete);

            txtEnable = itemView.findViewById(R.id.txtEnable);
            txtDisable = itemView.findViewById(R.id.txtDisable);

        }
    }


    public void addItemDecoration(RecyclerView recyclerView) {
        int[] attrs = {android.R.attr.listDivider};
        TypedValue typedValue = new TypedValue();
        c.getTheme().resolveAttribute(attrs[0], typedValue, true);
        Drawable dividerDrawable = ContextCompat.getDrawable(c, typedValue.resourceId);

        GradientDrawable shapeDrawable = new GradientDrawable();
        shapeDrawable.setShape(GradientDrawable.LINE);
        shapeDrawable.setSize(1, 1);
        shapeDrawable.setColor(ContextCompat.getColor(c, R.color.grey_300));

        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{shapeDrawable, dividerDrawable});
        recyclerView.addItemDecoration(new DividerItemDecoration(c, layerDrawable));
    }

    public class DividerItemDecoration extends RecyclerView.ItemDecoration {
        private Drawable divider;

        public DividerItemDecoration(Context context, LayerDrawable layerDrawable) {
            divider = layerDrawable;
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, @NonNull RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            for (int i = 0; i < parent.getChildCount() - 1; i++) {
                View child = parent.getChildAt(i);
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + divider.getIntrinsicHeight();

                divider.setBounds(left, top, right, bottom);
                divider.draw(c);
            }
        }

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
                    ArrayList<ModelBank> filteredPlayers = new ArrayList<>();
                    for (int i = 0; i < filterList.size(); i++) {


                        if (
                                filterList.get(i).getBankName().toUpperCase().contains(constraint) ||
                                        filterList.get(i).getAccountNumber().toUpperCase().contains(constraint) ||
                                        filterList.get(i).getAccountOwner().toUpperCase().contains(constraint) ||
                                        filterList.get(i).getId().toUpperCase().contains(constraint)


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

                myDonationsList = (ArrayList<ModelBank>) results.values;

                notifyDataSetChanged();
            }
        };
    }


}
