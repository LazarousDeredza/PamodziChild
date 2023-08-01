package com.pamodzichild.Organisations;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CPOrganisationsAdapter extends RecyclerView.Adapter<CPOrganisationsAdapter.MyViewHolder> implements Filterable {

    ArrayList<ModelCPOrganisation> myDonationsList,filterList;

    Context c;

    public CPOrganisationsAdapter(ArrayList<ModelCPOrganisation> myDonationsList, Context c) {
        this.myDonationsList = myDonationsList;
        this.c = c;
        this.filterList = myDonationsList;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(c).inflate(R.layout.cp_organisations_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ModelCPOrganisation modelCPOrganisation = myDonationsList.get(position);

        String id = modelCPOrganisation.getId();

        String organisationName = modelCPOrganisation.getName();
        String organisationEmail = modelCPOrganisation.getEmail();
        String organisationPhone = modelCPOrganisation.getPhone();
        String organisationCountry = modelCPOrganisation.getCountry();
        String organisationWhatsapp = modelCPOrganisation.getWhatsapp();
        String organisationWebsite = modelCPOrganisation.getWebsite().trim();


        if (!organisationWebsite.startsWith("http://") && !organisationWebsite.startsWith("https://")) {
            organisationWebsite = "https://" + organisationWebsite;
        }

        // final String[] organisationWebsite = {modelCPOrganisation.getWebsite()};

        String logo = modelCPOrganisation.getLogo();


        holder.txtOrganisationName.setText(organisationName);
        holder.txtEmail.setText(organisationEmail);
        holder.txtPhone.setText(organisationPhone);
        holder.countryOrCity.setText(organisationCountry);
        holder.txtWhatsapp.setText(organisationWhatsapp);
        holder.txtWebsite.setText(organisationWebsite);

        // Load the image into the ImageView using Picasso
        if (!logo.equals("")) {
            Picasso.get().load(logo)
                    .placeholder(R.drawable.loadingimage)
                    .error(R.drawable.profileblank)
                    .into(holder.companyImage);
        } else {
            holder.companyImage.setImageResource(R.drawable.profileblank);
        }

        String finalOrganisationWebsite = organisationWebsite;
        holder.txtWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Redirect to website

              /*  Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(android.net.Uri.parse(organisationWebsite));
                c.startActivity(intent);*/


                Log.e("Website", "onClick:" + finalOrganisationWebsite);



               /* // Create an Intent object with the ACTION_VIEW action and the website URL
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(finalOrganisationWebsite));

                // Check if there is an activity available to handle the intent
                PackageManager packageManager = c.getPackageManager();
                List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
                boolean isIntentSafe = activities.size() > 0;

                // If there is an activity available, start it
                if (isIntentSafe) {
                    c.startActivity(intent);
                } else {
                    // Handle the case where there is no activity available to handle the intent
                    // You can display an error message or take some other action here
                    Toast.makeText(c, "No app found to handle this action", Toast.LENGTH_SHORT).show();
                }
*/


                // Create an Intent object with the ACTION_VIEW action and the website URL
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(finalOrganisationWebsite));

                // Check if there is an activity available to handle the intent
                PackageManager packageManager = c.getPackageManager();
                List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
                boolean isIntentSafe = activities.size() > 0;

                // If there is an activity available, start it
                if (isIntentSafe) {
                    c.startActivity(intent);
                } else {
                    // Handle the case where there is no activity available to handle the intent
                    // You can display a dialog with options for the user
                    AlertDialog.Builder builder = new AlertDialog.Builder(c);
                    builder.setTitle("No app found to handle this action")
                            .setMessage("Please install a web browser or use an alternative method to access the website.")
                            .setPositiveButton("Copy URL", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Handle the "Copy URL" button click
                                    ClipboardManager clipboard = (ClipboardManager) c.getSystemService(Context.CLIPBOARD_SERVICE);
                                    ClipData clip = ClipData.newPlainText("Website URL", finalOrganisationWebsite);
                                    clipboard.setPrimaryClip(clip);
                                    Toast.makeText(c, "URL copied to clipboard", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("Cancel", null);

                    // Add an option to launch an in-app web view, if desired
                    builder.setNeutralButton("Open in-app browser", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Handle the "Open in-app browser" button click
                            // You can launch an in-app web view here
                            // Create a WebView object
                            WebView webView = new WebView(c);
                            webView.getSettings().setJavaScriptEnabled(true);


                            // Load the website URL into the WebView
                            webView.loadUrl(finalOrganisationWebsite);
                            // Create a layout parameters object that fills the screen
                            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT);

                        // Set the layout parameters of the WebView
                            webView.setLayoutParams(layoutParams);

                            // Display the WebView in a dialog
                            AlertDialog.Builder builder = new AlertDialog.Builder(c);
                            builder.setView(webView)
                                    .setTitle("Website")
                                    .setPositiveButton("Close", null)
                                    .create()
                                    .show();
                        }
                    });

                    builder.create().show();
                }


            }
        });


        holder.txtWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Redirect to whatsapp
                String organisationWhatsapp2 = organisationWhatsapp.replace("+", "");
                String organisationWhatsapp3 = organisationWhatsapp2.replace(" ", "");


                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://api.whatsapp.com/send?phone=" + organisationWhatsapp3));
                c.startActivity(intent);

            }
        });


        holder.txtEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Redirect to email

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("mailto:" + organisationEmail));
                c.startActivity(intent);

            }
        });


        holder.txtPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Redirect to phone

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("tel:" + organisationPhone));
                c.startActivity(intent);

            }
        });


        holder.txtEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Redirect to edit
                SharedPreferences.Editor editor = c.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).edit();
                editor.putString("fragment", "toOrganisations");
                editor.apply();

                Fragment fragment = new EditOrganisation();
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                bundle.putString("mode", "edit");

                fragment.setArguments(bundle);

                FragmentManager fragmentManager = ((FragmentActivity) c).getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();


            }
        });


        holder.txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Delete organisation

                AlertDialog.Builder builder = new AlertDialog.Builder(c);
                builder.setTitle("Delete Organisation");
                builder.setMessage("Are you sure you want to delete this organisation?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Organisations");
                        databaseReference.child(id).removeValue();

                        Toast.makeText(c, "Organisation deleted successfully", Toast.LENGTH_SHORT).show();

                        Fragment fragment = new CPOrganisations();
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






       /* holder.txtDonationName.setText(producName);
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
        });
*/

    }

    @Override
    public int getItemCount() {
        return myDonationsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView txtOrganisationName, txtCountryCity, txtPhone, txtWhatsapp, txtEmail, txtWebsite;

        TextView countryOrCity;
        TextView txtEdit, txtDelete;

        ImageView companyImage;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            txtOrganisationName = itemView.findViewById(R.id.txtOrganisationName);
            txtCountryCity = itemView.findViewById(R.id.txtCountryCity);
            txtPhone = itemView.findViewById(R.id.txtPhone);
            txtWhatsapp = itemView.findViewById(R.id.txtWhatsapp);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtWebsite = itemView.findViewById(R.id.txtWebsite);
            txtEdit = itemView.findViewById(R.id.txtEdit);
            txtDelete = itemView.findViewById(R.id.txtDelete);

            countryOrCity = itemView.findViewById(R.id.txtCountryCity);

            companyImage = itemView.findViewById(R.id.companyImage);

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
                    Log.e("Constraint", constraint.toString());
                    ArrayList<ModelCPOrganisation> filteredPlayers = new ArrayList<>();
                    for (int i = 0; i < filterList.size(); i++) {

                        String name = filterList.get(i).getName().trim().toUpperCase();

                        // CHECK
                        if (
                                filterList.get(i).getCountry().toUpperCase().contains(constraint) ||
                                        filterList.get(i).getPhone().toUpperCase().contains(constraint) ||
                                        filterList.get(i).getEmail().toUpperCase().contains(constraint) ||
                                        filterList.get(i).getId().toUpperCase().contains(constraint) ||
                                        filterList.get(i).getWebsite().toUpperCase().contains(constraint) ||

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

                myDonationsList = (ArrayList<ModelCPOrganisation>) results.values;

                notifyDataSetChanged();
            }
        };
    }




}
