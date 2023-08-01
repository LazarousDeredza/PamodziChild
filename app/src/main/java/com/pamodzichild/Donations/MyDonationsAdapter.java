package com.pamodzichild.Donations;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.pamodzichild.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyDonationsAdapter extends RecyclerView.Adapter<MyDonationsAdapter.MyViewHolder> {

    ArrayList<ModelDonation> myDonationsList;
    Context c;

    public MyDonationsAdapter(ArrayList<ModelDonation> myDonationsList, Context c) {
        this.myDonationsList = myDonationsList;
        this.c = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(c).inflate(R.layout.my_donations_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String producName = myDonationsList.get(position).getProductname();
        String date = myDonationsList.get(position).getDate();
        String time = myDonationsList.get(position).getTime();
        String donationType = myDonationsList.get(position).getDonationType();
        String quantity = myDonationsList.get(position).getQuantity();
        String status = myDonationsList.get(position).getStatus();
        String description = myDonationsList.get(position).getProductDescription();
        String receiver = myDonationsList.get(position).getReceiver();
        String image = myDonationsList.get(position).getImage();


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
        if (status.equals("Donated")) {
            holder.txtReceiver.setText(" See Receiver ");
        }else {
            holder.txtReceiver.setText(null);
            holder.txtReceiver.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return myDonationsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtDonationName, txtDate, txtTime, txtDonationType, txtQuantity, txtStatus, txtDescription, txtReceiver;

        ImageView productImage;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            txtDonationName = itemView.findViewById(R.id.txtDonationName);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtDonationType = itemView.findViewById(R.id.txtDonationType);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtDescription = itemView.findViewById(R.id.txtDescription);


            txtReceiver = itemView.findViewById(R.id.txtReceiver);

            productImage = itemView.findViewById(R.id.productImage);
        }
    }


    public void addItemDecoration(RecyclerView recyclerView) {
        int[] attrs = { android.R.attr.listDivider };
        TypedValue typedValue = new TypedValue();
        c.getTheme().resolveAttribute(attrs[0], typedValue, true);
        Drawable dividerDrawable = ContextCompat.getDrawable(c, typedValue.resourceId);

        GradientDrawable shapeDrawable = new GradientDrawable();
        shapeDrawable.setShape(GradientDrawable.LINE);
        shapeDrawable.setSize(1, 1);
        shapeDrawable.setColor(ContextCompat.getColor(c, R.color.grey_300));

        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[] {shapeDrawable, dividerDrawable});
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
