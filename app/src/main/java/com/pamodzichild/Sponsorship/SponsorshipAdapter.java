package com.pamodzichild.Sponsorship;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pamodzichild.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SponsorshipAdapter extends RecyclerView.Adapter<SponsorshipAdapter.MyViewHolder> implements Filterable {

    ArrayList<ModelSponsee> mySponseesList, filterList;
    Context c;

    public SponsorshipAdapter(ArrayList<ModelSponsee> mySponseesList, Context c) {
        this.mySponseesList = mySponseesList;
        this.c = c;
        this.filterList = mySponseesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(c).inflate(R.layout.sponsorship_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ModelSponsee modelSponsee = mySponseesList.get(position);

        String sponseeName = mySponseesList.get(position).getFirstname() + " " + mySponseesList.get(position).getLastname();
        String Address = mySponseesList.get(position).getAddress();
        String Gender = mySponseesList.get(position).getGender();
        String Disability = mySponseesList.get(position).getDisability();
        String Bio = mySponseesList.get(position).getBio();
        String image = mySponseesList.get(position).getPhoto();
        String parentAlive = mySponseesList.get(position).getParentsAlive();
        String id = mySponseesList.get(position).getId();

        Picasso.get().load(image)
                .placeholder(R.drawable.loadingimage)
                .error(R.drawable.loadingimage)
                .into(holder.profleImage);

        holder.txtName.setText(sponseeName);
        holder.txtAddress.setText(Address);
        holder.txtGender.setText(Gender);
        holder.txtDisability.setText(Disability);
        holder.txtBio.setText(Bio);


        String dobString = mySponseesList.get(position).getDob();

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

            holder.txtAge.setText(String.valueOf(age));

            // The variable "age" now contains the age in years
            // You can use this value as needed in your application

        } catch (ParseException e) {
            holder.txtAge.setText(mySponseesList.get(position).getDob());
        }


        if (parentAlive.equals("Single Parent")) {

            holder.radioNone.setChecked(false);
            holder.radioOne.setChecked(true);
            holder.radioTwo.setChecked(false);
        } else if (parentAlive.equals("Both Alive")) {
            holder.radioNone.setChecked(false);
            holder.radioOne.setChecked(false);
            holder.radioTwo.setChecked(true);
        } else {
            holder.radioNone.setChecked(true);
            holder.radioOne.setChecked(false);
            holder.radioTwo.setChecked(false);
        }



        holder.txtMoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Put Model Sponsee object in a bundle
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                bundle.putSerializable("sponsee", modelSponsee);



                SharedPreferences sharedPreferences = c.getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("fragment", "toSponsorship");
                editor.apply();


                // Navigate to the Home fragment
                ViewSponsee viewSponsee = new ViewSponsee();
                viewSponsee.setArguments(bundle);

                FragmentManager fragmentManager = ((FragmentActivity) c).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, viewSponsee)
                        .commit();

            }
        });


        holder.btnSponsor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                bundle.putSerializable("sponsee", modelSponsee);


                SharedPreferences sharedPreferences = c.getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("fragment", "toSponsorship");

                editor.apply();


                // Navigate to the Home fragment
                Sponsor_Page sponsor_page = new Sponsor_Page();
                sponsor_page.setArguments(bundle);

                FragmentManager fragmentManager = ((FragmentActivity) c).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, sponsor_page)
                        .commit();
            }
        });



        /*String producName = myDonationsList.get(position).getProductname();
        String date = myDonationsList.get(position).getDate();
        String time = myDonationsList.get(position).getTime();
        String donationType = myDonationsList.get(position).getDonationType();
        String quantity = myDonationsList.get(position).getQuantity();
        String status = myDonationsList.get(position).getStatus();
        String description = myDonationsList.get(position).getProductDescription();
        String receiver = myDonationsList.get(position).getReceiver();
        String image = myDonationsList.get(position).getImage();


        String donationId = myDonationsList.get(position).getDonationId();
        String donorId = myDonationsList.get(position).getDonatedby()  ;



        holder.txtDonationName.setText(producName);
        holder.txtDate.setText(date);
        holder.txtTime.setText(time);
        holder.txtDonationType.setText(donationType);
        holder.txtQuantity.setText(quantity);
        holder.txtStatus.setText(status);

        // Truncate the description to 29 characters and append three dots if necessary
        if (description.length() > 29) {
            description = description.substring(0, 29) + "...";
        }
        // Set the truncated description as the text of the TextView
        holder.txtDescription.setText(description);


        // Load the image into the ImageView using Picasso
        Picasso.get().load(image)
                .placeholder(R.drawable.loadingimage)
                .error(R.drawable.loadingimage)
                .into(holder.productImage);

        // If the donation has been received, set the text of the TextView to "See Received child"



        ArrayList<ModelSeeker> seekers = myDonationsList.get(position).getSeeker();
        if(seekers == null){
            holder.txtReceiver.setText("  Seek  ");
        }
        else{
            for (int i = 0; i < seekers.size(); i++) {
                Log.d("seeker", "Seeker ID "+seekers.get(i).getSeekerId() + " Seeker Name "+seekers.get(i).getFirstname());
                if(seekers.get(i).getSeekerId().equals(MainActivity.loggedInUser)){
                    holder.txtReceiver.setText("  See  ");
                    holder.txtReceiver.setVisibility(View.INVISIBLE);
                    break;
                }
                else{
                    holder.txtReceiver.setText("  Seek  ");

                }
            }
        }



        //holder.txtReceiver.setText("  Seek  ");




        holder.txtReceiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("donationId", donationId);
                bundle.putString("donationName", producName);
                bundle.putString("donorId", donorId);


                SharedPreferences sharedPreferences = c.getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("fragment", "toDonations");
                editor.apply();


                // Navigate to the Home fragment
                SeekDonation seekDonation = new SeekDonation();
                seekDonation.setArguments(bundle);

                FragmentManager fragmentManager = ((FragmentActivity) c).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, seekDonation)
                        .commit();


            }
        });*/


    }

    @Override
    public int getItemCount() {
        return mySponseesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtAddress, txtAge, txtGender, txtDisability, txtBio, txtMoreInfo;

        ImageView profleImage;
        RadioButton radioNone, radioOne, radioTwo;
        Button btnSponsor;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            txtName = itemView.findViewById(R.id.txtSponseeName);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            txtAge = itemView.findViewById(R.id.txtAge);
            txtGender = itemView.findViewById(R.id.txtGender);
            txtDisability = itemView.findViewById(R.id.txtDisability);
            txtBio = itemView.findViewById(R.id.txtBio);
            txtMoreInfo = itemView.findViewById(R.id.txtMoreInfo);
            ;

            profleImage = itemView.findViewById(R.id.profileImage);

            radioNone = itemView.findViewById(R.id.radioNone);
            radioOne = itemView.findViewById(R.id.radioOne);
            radioTwo = itemView.findViewById(R.id.radioTwo);

            btnSponsor = itemView.findViewById(R.id.btnSponsor);


      /*      txtDonationName = itemView.findViewById(R.id.txtDonationName);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtDonationType = itemView.findViewById(R.id.txtDonationType);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtDescription = itemView.findViewById(R.id.txtDescription);


            txtReceiver = itemView.findViewById(R.id.txtReceiver);

            productImage = itemView.findViewById(R.id.productImage);*/
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
                    ArrayList<ModelSponsee> filteredPlayers = new ArrayList<>();
                    for (int i = 0; i < filterList.size(); i++) {

                        String name = filterList.get(i).getLastname().trim().toUpperCase();
                        String lastname = filterList.get(i).getFirstname().trim().toUpperCase();
                        // CHECK
                        if (filterList.get(i).getAddress().toUpperCase().contains(constraint) ||
                                filterList.get(i).getCountry().toUpperCase().contains(constraint) ||
                                filterList.get(i).getDisability().toUpperCase().contains(constraint) ||
                                filterList.get(i).getEmail().toUpperCase().contains(constraint) ||
                                filterList.get(i).getGender().toUpperCase().contains(constraint) ||
                                filterList.get(i).getId().toUpperCase().contains(constraint) ||
                                filterList.get(i).getDateApplied().toUpperCase().contains(constraint) ||
                                filterList.get(i).getPhone().toUpperCase().contains(constraint) ||
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

                mySponseesList = (ArrayList<ModelSponsee>) results.values;

                notifyDataSetChanged();
            }
        };
    }
}
