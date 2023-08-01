package com.pamodzichild.Sponsorship;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pamodzichild.R;
import com.pamodzichild.SendEmail.SendMailTask;
import com.pamodzichild.Users.UserModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SponsorshipRequestAdapter extends RecyclerView.Adapter<SponsorshipRequestAdapter.MyViewHolder> implements Filterable {

    ArrayList<ModelSponsorship> mySponseesList, filterList;
    DatabaseReference reference;

    TextView textProgram;
    Dialog dialog;
    Context c;
    ArrayList<String> arrayList;

    public SponsorshipRequestAdapter(ArrayList<ModelSponsorship> mySponseesList, Context c, ArrayList<String> arrayList) {
        this.mySponseesList = mySponseesList;
        this.c = c;
        this.filterList = mySponseesList;
        this.reference = FirebaseDatabase.getInstance().getReference("sponsorships");
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(c).inflate(R.layout.sponsorship_request_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ModelSponsorship sponsorship = mySponseesList.get(position);

        ModelSponsee sponseeData = mySponseesList.get(position).getModelSponsee();
        UserModel sponsor = mySponseesList.get(position).getSponsor();

        String sponsorName = sponsor.getFname() + " " + sponsor.getLname();
        String sponseeName = sponseeData.getFirstname() + " " + sponseeData.getLastname();
        String amount = sponsorship.getCurrency() + "" + sponsorship.getAmount();
        String dateToStart = sponsorship.getDateToStart();
        String duration = sponsorship.getDuration();
        String purpose = sponsorship.getPurpose();
        String image = sponseeData.getPhoto();
        String Id = sponsorship.getId();

        String status = sponsorship.getStatus();

        Picasso.get().load(image)
                .placeholder(R.drawable.loadingimage)
                .error(R.drawable.loadingimage)
                .into(holder.profileImage);

        holder.txtSponseeName.setText(sponseeName);
        holder.txtSponsorName.setText(sponsorName);
        holder.txtAmount.setText(amount);
        holder.txtDate.setText(dateToStart);
        holder.txtDuration.setText(duration);
        holder.txtPurpose.setText(purpose);
        holder.txtSponsorshipID.setText(Id);


        if (status.equals("pending")) {
            holder.btnReceived.setVisibility(View.GONE);

        } else if (status.equals("approved")) {
            holder.btnAccept.setVisibility(View.GONE);
            holder.btnReject.setVisibility(View.GONE);

        } else if (status.equals("rejected")) {
            holder.btnAccept.setVisibility(View.GONE);
            holder.btnReject.setVisibility(View.GONE);
            holder.btnReceived.setVisibility(View.GONE);

        } else if (status.equals("received")) {
            holder.btnAccept.setVisibility(View.GONE);
            holder.btnReject.setVisibility(View.GONE);
            holder.btnReceived.setVisibility(View.GONE);

        }


        holder.btnAccept.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (arrayList.size() == 0 || arrayList == null) {
                    Toast.makeText(c, "Please add Bank accounts first", Toast.LENGTH_SHORT).show();
                } else {


                    ProgressDialog progressDialog = new ProgressDialog(c);
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();


                    //Initialise Dialog
                    dialog = new Dialog(c);
                    //Set Custom dialog
                    dialog.setContentView(R.layout.dialog_searchable_spinner);


                    //Set Custom Heigth and Width
                    dialog.getWindow().setLayout(650, 800);

                    //set Transparent Background

                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    //show Dialog
                    dialog.show();

                    //Initialise Dialog Components

                    EditText editText = dialog.findViewById(R.id.edit_text_Program);
                    ListView listView = dialog.findViewById(R.id.list_view);

                    //Initialise Adapter
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            c, android.R.layout.simple_list_item_1, arrayList);

                    listView.setAdapter(adapter);

                    //Set Listener
                    editText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            //Filter the list
                            adapter.getFilter().filter(s);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                            //Filter the list
                        }
                    });

                    //Set Listener
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //Get the selected item
                            String selectedItem = arrayList.get(position);

                            //Set the selected item to the text view
                            Log.e("selected Bank : ", selectedItem);

                            //Close the dialog
                            dialog.dismiss();

                            reference.child(Id).child("status").setValue("approved").addOnSuccessListener(aVoid -> {
                                progressDialog.dismiss();


                                Toast.makeText(c, "Sponsorship Approved successfully", Toast.LENGTH_SHORT).show();


                                String message = "<html><body><p>Hello " + sponsor.getFname() + " " + sponsor.getLname() + ",</p>\n\n" +

                                        "<p>Thank you for your patience, your request to sponsor <b>" + sponseeName + "</b>" +
                                        "was reviewed successfully ." +
                                        "The child is indeed in need for sure , your help will be greatly appreciated.</p>" +
                                        "<p>So you can send the </p>" + sponsorship.getCurrency() + sponsorship.getAmount() + " that you stated in your application to the following bank account </p>" +
                                        "<b> " + selectedItem + "</b>" +
                                        "<p> And send back the proof of payment to this email for accountability </p>" +
                                        "<p>Thank You</p>\n" +
                                        "<p>Regards,</p>\n" +
                                        "<p>Team Pamodzi Child Africa</p>";


                                SendMailTask sendMailTask = new SendMailTask(sponsor.getUser_email(), "Your Sponsorship was Reviewed Successfully", message, c);
                                sendMailTask.execute();


                                FragmentManager fragmentManager = ((FragmentActivity) c).getSupportFragmentManager();
                                fragmentManager.beginTransaction()
                                        .replace(R.id.content_frame, new SponsorshipsMade())
                                        .commit();


                            });


                        }


                    });


                }

            }

        });


        holder.btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProgressDialog progressDialog = new ProgressDialog(c);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();

                FirebaseDatabase.getInstance().getReference("sponsorships")
                        .child(Id)
                        .child("status")
                        .setValue("rejected").addOnSuccessListener(aVoid -> {
                            progressDialog.dismiss();
                            Toast.makeText(c, "Sponsorship Rejected", Toast.LENGTH_SHORT).show();

                            FragmentManager fragmentManager = ((FragmentActivity) c).getSupportFragmentManager();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.content_frame, new SponsorshipsMade())
                                    .commit();
                        });
            }
        });


        holder.btnReceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProgressDialog progressDialog = new ProgressDialog(c);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();

                FirebaseDatabase.getInstance().getReference("sponsorships")
                        .child(Id)
                        .child("status")
                        .setValue("received").addOnSuccessListener(aVoid -> {
                            progressDialog.dismiss();
                            Toast.makeText(c, "Sponsorship Received", Toast.LENGTH_SHORT).show();

                            FragmentManager fragmentManager = ((FragmentActivity) c).getSupportFragmentManager();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.content_frame, new SponsorshipsMade())
                                    .commit();
                        });
            }
        });


    }

    @Override
    public int getItemCount() {
        return mySponseesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView profileImage;
        TextView txtSponsorName, txtSponsorshipID, txtSponseeName,
                txtAmount, txtDate, txtDuration, txtPurpose;
        Button btnAccept, btnReceived, btnReject;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            profileImage = itemView.findViewById(R.id.profileImage);
            txtSponsorName = itemView.findViewById(R.id.txtSponsorName);
            txtSponsorshipID = itemView.findViewById(R.id.txtSponsorshipID);
            txtSponseeName = itemView.findViewById(R.id.txtSponseeName);
            txtAmount = itemView.findViewById(R.id.txtAmount);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtDuration = itemView.findViewById(R.id.txtDuration);
            txtPurpose = itemView.findViewById(R.id.txtPurpose);
            btnAccept = itemView.findViewById(R.id.btnAccept);
            btnReceived = itemView.findViewById(R.id.btnReceived);
            btnReject = itemView.findViewById(R.id.btnReject);


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
                    ArrayList<ModelSponsorship> filteredPlayers = new ArrayList<>();
                    for (int i = 0; i < filterList.size(); i++) {

                        String name = filterList.get(i).getSponsor().getLname().trim().toUpperCase();
                        String lastname = filterList.get(i).getSponsor().getLname().trim().toUpperCase();
                        // CHECK
                        if (filterList.get(i).getId().toUpperCase().contains(constraint) ||
                                filterList.get(i).getModelSponsee().getLastname().toUpperCase().contains(constraint) ||
                                filterList.get(i).getModelSponsee().getFirstname().toUpperCase().contains(constraint) ||
                                filterList.get(i).getDateToStart().toUpperCase().contains(constraint) ||
                                filterList.get(i).getId().toUpperCase().contains(constraint) ||
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

                mySponseesList = (ArrayList<ModelSponsorship>) results.values;

                notifyDataSetChanged();
            }
        };
    }
}
