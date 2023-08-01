package com.pamodzichild.Users;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
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
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pamodzichild.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder> implements Filterable {

    ArrayList<UserModel> mySponseesList, filterList;
    Context c;

    public UsersAdapter(ArrayList<UserModel> mySponseesList, Context c) {
        this.mySponseesList = mySponseesList;
        this.c = c;
        this.filterList = mySponseesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(c).inflate(R.layout.users_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        UserModel user = mySponseesList.get(position);

        String userName = user.getFname() + " " + user.getLname();
        Log.e("Username", userName);
        String phone = user.getPhonePrefix() + user.getPhoneSuffix();
        String email = user.getUser_email();
        String id = user.getId();
        String dateRegistered = user.getDateRegistered();
        String photo = user.getPhoto();


        if (photo.equals("")) {
            holder.profileImage.setImageResource(R.drawable.profileblank);
        } else {
            Picasso.get().load(photo)
                    .placeholder(R.drawable.loadingimage)
                    .error(R.drawable.loadingimage)
                    .into(holder.profileImage);
        }

        holder.txtUsername.setText(userName);
        holder.txtPhone.setText(phone);
        holder.txtEmail.setText(email);
        holder.txtDateRegistered.setText(dateRegistered);


        holder.txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ProgressDialog progressDialog = new ProgressDialog(c);
                progressDialog.setMessage("Deleting user...");
                progressDialog.setCancelable(false);
                progressDialog.show();


                // Get a reference to the Firebase Authentication service


                DatabaseReference databaseReference =
                        FirebaseDatabase.getInstance().getReference("Users").child(id);


                databaseReference.removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        if (error == null) {
                            Log.d("TAG", "Value removed from Firebase.");
                            Toast.makeText(c, "User deleted successfully", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            // Navigate to the Home fragment
                            Users users = new Users();


                            FragmentManager fragmentManager = ((FragmentActivity) c).getSupportFragmentManager();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.content_frame, users)
                                    .commit();

                        } else {
                            Log.e("TAG", "Error removing value from Firebase.", error.toException());
                        }
                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        if (mySponseesList != null) {
            return mySponseesList.size();
        } else {
            return 0;
        }


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtUsername, txtPhone, txtEmail, txtDateRegistered, txtDelete;

        ImageView profileImage;


        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            txtUsername = itemView.findViewById(R.id.txtUsername);
            txtPhone = itemView.findViewById(R.id.txtPhone);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtDateRegistered = itemView.findViewById(R.id.txtDateRegistered);
            txtDelete = itemView.findViewById(R.id.txtDelete);
            profileImage = itemView.findViewById(R.id.profileImage);


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
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
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


   /* @Override
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
                    ArrayList<UserModel> filteredPlayers = new ArrayList<>();
                    for (int i = 0; i < filterList.size(); i++) {

                        String name = filterList.get(i).getFname().trim().toUpperCase();
                        String lastname = filterList.get(i).getLname().trim().toUpperCase();
                        // CHECK
                        if (filterList.get(i).getUser_email().toUpperCase().contains(constraint) ||
                                filterList.get(i).getCountry().toUpperCase().contains(constraint) ||
                                filterList.get(i).getId().toUpperCase().contains(constraint) ||
                                filterList.get(i).getPhoneSuffix().toUpperCase().contains(constraint) ||

                                lastname.contains(constraint) ||
                                name.contains(constraint)


                        ) {
                            // ADD PLAYER TO FILTERED PLAYERS
                            filteredPlayers.add(filterList.get(i));
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

                mySponseesList = (ArrayList<UserModel>) results.values;

                notifyDataSetChanged();
            }
        };
    }*/




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
                    ArrayList<UserModel> filteredPlayers = new ArrayList<>();
                    for (int i = 0; i < filterList.size(); i++) {



                        // CHECK
                        if (
                                filterList.get(i).getFname().toUpperCase().contains(constraint) ||
                                        filterList.get(i).getLname().toUpperCase().contains(constraint) ||
                                        filterList.get(i).getUser_email().toUpperCase().contains(constraint) ||
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

                mySponseesList = (ArrayList<UserModel>) results.values;

                notifyDataSetChanged();
            }
        };
    }
}
