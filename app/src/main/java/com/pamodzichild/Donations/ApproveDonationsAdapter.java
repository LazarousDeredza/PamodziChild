package com.pamodzichild.Donations;

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

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.pamodzichild.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ApproveDonationsAdapter extends RecyclerView.Adapter<ApproveDonationsAdapter.MyViewHolder> implements Filterable {

    ArrayList<ModelDonation> myDonationsList;
    ArrayList<ModelDonation> filterList;

    String query;
    Context c;

    public ApproveDonationsAdapter(ArrayList<ModelDonation> myDonationsList, Context c) {
        this.myDonationsList = myDonationsList;
        this.c = c;
        this.filterList = myDonationsList;
        this.query = "";
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(c).inflate(R.layout.my_approving_donations_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String donationID = myDonationsList.get(position).getDonationId();
        ModelDonor donor = myDonationsList.get(position).getDonor();

        String producName = myDonationsList.get(position).getProductname();
        String date = myDonationsList.get(position).getDate();
        String time = myDonationsList.get(position).getTime();
        String donationType = myDonationsList.get(position).getDonationType();
        String quantity = myDonationsList.get(position).getQuantity();
        String description = myDonationsList.get(position).getProductDescription();
        String image = myDonationsList.get(position).getImage();

        holder.txtDonationID.setText(donationID);
        holder.txtDonorName.setText(donor.getFirstname() + " " + donor.getLastname());


        holder.txtDonationName.setText(producName);
        holder.txtDate.setText(date);
        holder.txtTime.setText(time);
        holder.txtDonationType.setText(donationType);
        holder.txtQuantity.setText(quantity);


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

        holder.txtApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("TAG", "onClick : " + donationID);
                Log.e("TAG", "onClick :  Donated BY " + donor.getLastname() + " " + donor.getFirstname());


                Approve.ApproveDonation(donationID);


            }
        });

        holder.txtDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Approve.DeclineDonation(donationID);
            }
        });


    }

    @Override
    public int getItemCount() {
        return myDonationsList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtDonationName, txtDate, txtTime, txtDonationType, txtQuantity, txtStatus, txtDescription;
        TextView txtApprove, txtDecline;

        TextView txtDonationID, txtDonorName;

        ImageView productImage;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            txtDonationName = itemView.findViewById(R.id.txtDonationName);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtDonationType = itemView.findViewById(R.id.txtDonationType);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);

            txtDescription = itemView.findViewById(R.id.txtDescription);


            txtApprove = itemView.findViewById(R.id.txtApprove);
            txtDecline = itemView.findViewById(R.id.txtDisapprove);

            productImage = itemView.findViewById(R.id.productImage);

            txtDonationID = itemView.findViewById(R.id.txtDonationID);

            txtDonorName = itemView.findViewById(R.id.txtDonorName);

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


    // Constructor and ViewHolder implementation...

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
                    ArrayList<ModelDonation> filteredPlayers = new ArrayList<>();
                    for (int i = 0; i < filterList.size(); i++) {

                        String name = filterList.get(i).getDonor().getLastname().trim().toUpperCase() +
                                filterList.get(i).getDonor().getFirstname().trim().toUpperCase();
                        // CHECK
                        if (filterList.get(i).getProductname().toUpperCase().contains(constraint) ||
                                filterList.get(i).getProductDescription().toUpperCase().contains(constraint) ||
                                filterList.get(i).getDonationType().toUpperCase().contains(constraint) ||
                                filterList.get(i).getDonatedby().toUpperCase().contains(constraint) ||
                                filterList.get(i).getDonationId().toUpperCase().contains(constraint) ||
                                filterList.get(i).getDate().toUpperCase().contains(constraint) ||
                                filterList.get(i).getTime().toUpperCase().contains(constraint) ||
                                filterList.get(i).getDonor().getLastname().toUpperCase().contains(constraint) ||
                                filterList.get(i).getDonor().getFirstname().toUpperCase().contains(constraint) ||
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

                myDonationsList = (ArrayList<ModelDonation>) results.values;

                notifyDataSetChanged();
            }
        };
    }


}
