package com.pamodzichild.CPcases;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.pamodzichild.Organisations.ModelCPOrganisation;
import com.pamodzichild.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ReportToOrganisationsAdapter extends RecyclerView.Adapter<ReportToOrganisationsAdapter.MyViewHolder> {

    ArrayList<ModelCPOrganisation> myDonationsList;

    Context c;

    public ReportToOrganisationsAdapter(ArrayList<ModelCPOrganisation> myDonationsList, Context c) {
        this.myDonationsList = myDonationsList;
        this.c = c;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(c).inflate(R.layout.cp_organisations_list_item2, parent, false);
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
        final String[] organisationWebsite = {modelCPOrganisation.getWebsite()};

        String logo = modelCPOrganisation.getLogo();


        holder.txtOrganisationName.setText(organisationName);
        holder.txtEmail.setText(organisationEmail);
        holder.txtPhone.setText(organisationPhone);
        holder.countryOrCity.setText(organisationCountry);
        holder.txtWhatsapp.setText(organisationWhatsapp);
        holder.txtWebsite.setText(organisationWebsite[0]);

        // Load the image into the ImageView using Picasso
        if (!logo.equals("")) {
            Picasso.get().load(logo)
                    .placeholder(R.drawable.loadingimage)
                    .error(R.drawable.profileblank)
                    .into(holder.companyImage);
        } else {
            holder.companyImage.setImageResource(R.drawable.profileblank);
        }

        holder.txtWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Redirect to website

               /* Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(android.net.Uri.parse(organisationWebsite));
                c.startActivity(intent);*/


                if (!organisationWebsite[0].startsWith("http://") && !organisationWebsite[0].startsWith("https://")){
                    organisationWebsite[0] = "http://" + organisationWebsite[0];
            }


                // Create an Intent object with the ACTION_VIEW action and the website URL
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(organisationWebsite[0]));

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


    }

    @Override
    public int getItemCount() {
        return myDonationsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView txtOrganisationName, txtCountryCity, txtPhone, txtWhatsapp, txtEmail, txtWebsite;

        TextView countryOrCity;

        ImageView companyImage;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            txtOrganisationName = itemView.findViewById(R.id.txtOrganisationName);
            txtCountryCity = itemView.findViewById(R.id.txtCountryCity);
            txtPhone = itemView.findViewById(R.id.txtPhone);
            txtWhatsapp = itemView.findViewById(R.id.txtWhatsapp);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtWebsite = itemView.findViewById(R.id.txtWebsite);


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
}
