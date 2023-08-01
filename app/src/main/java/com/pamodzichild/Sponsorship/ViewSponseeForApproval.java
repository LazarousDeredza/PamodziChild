package com.pamodzichild.Sponsorship;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.pamodzichild.MainActivity;
import com.pamodzichild.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewSponseeForApproval extends Fragment {

    View view;
    CircleImageView imgProfile;
    Button btnSponsor;

    TextView txtName1, txtDOB1, txtAddress, txtGender1, txtAge, txtCountry, txtOccupation1, txtDisability1,
            txtParentsAlive, txtPhone1, txtEmail1, txtEducationLevel, txtReason, txtBiography;
    TextView txtFirstName2, txtLastName2, txtDOB2, txtOccupation2, txtGender2, txtRelationship, txtDisability2,
            txtPhone2, txtEmail2;

    ImageView img1, img2, img3, img4;
    ImageView letterImageView;
    ImageView evidenceImageView1, evidenceImageView2, evidenceImageView3;

    ModelSponsee modelSponsee;

    String id;


    TextInputLayout moreGuardianOrParentInfoLayout,
            currentFamilySituationLayout,
            sourceOfIncomeLayout,
            averageMonthlyIncomeLayout,
            typeOfHouseLayout, numberOfPeopleInHouseLayout,
            numberOfPeopleUnder18Layout,
            typeOfLightLayout,
            typeOfCookingEnergyLayout,
            typeOfToiletLayout,
            typeOfWaterSourceLayout,
            distanceFromWaterSourceLayout,
            typeOfFloorLayout,
            numberOfSiblingsLayout,
            numberOfSiblingsInSchoolLayout,
            financialChallengesLayout,
            circumstancesAffectingSchoolingLayout,
            missedSchoolDueToLackOfResourcesLayout,
            numberOfMealsPerDayLayout,
            responsibilitiesAtHomeLayout,
            academicGoalsLayout,
            nameOfSponsorLayout,
            typeOfSponsorshipLayout,
            durationOfSponsorshipLayout,
            reasonWhySponsorshipStoppedLayout,
            howSponsorshipHelpedYouLayout,
            howYouFeltWhenSponsorshipStoppedLayout,
            planForPloughBackToCommunityLayout,

    howYouKnewAboutPamhodziKidsFoundationLayout;

    LinearLayout sponsorshipDetailsLayout;
    RadioGroup radioGroupReceivedSponsorshipBefore;
    String receivedSponsorshipBefore=null;

Button btnApprove ,btnDecline;

static ProgressDialog progressDialog;
    public ViewSponseeForApproval() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_view_sponsee_for_approval, container, false);
        getActivity().setTitle("View Sponsee");
        MainActivity.flag = 1;



        btnDecline=view.findViewById(R.id.btnDecline);
        btnApprove=view.findViewById(R.id.btnApprove);

        sponsorshipDetailsLayout = view.findViewById(R.id.sponsorshipDetailsLayout);
        radioGroupReceivedSponsorshipBefore = view.findViewById(R.id.radioGroupReceivedSponsorshipBefore);

        progressDialog = new ProgressDialog(getContext());


        imgProfile = view.findViewById(R.id.imgProfile);
//        btnSponsor = view.findViewById(R.id.btnSponsor);

        letterImageView = view.findViewById(R.id.letterImageView);

        evidenceImageView1 = view.findViewById(R.id.evidenceImageView1);
        evidenceImageView2 = view.findViewById(R.id.evidenceImageView2);
        evidenceImageView3 = view.findViewById(R.id.evidenceImageView3);

        txtName1 = view.findViewById(R.id.txtName);
        txtDOB1 = view.findViewById(R.id.txtDOB1);
        txtAddress = view.findViewById(R.id.txtAddress);
        txtGender1 = view.findViewById(R.id.txtGender1);
        txtAge = view.findViewById(R.id.txtAge);
        txtCountry = view.findViewById(R.id.txtCountry);
        txtOccupation1 = view.findViewById(R.id.txtOccupation1);
        txtDisability1 = view.findViewById(R.id.txtDisability1);
        txtParentsAlive = view.findViewById(R.id.txtParentsAlive);
        txtPhone1 = view.findViewById(R.id.txtPhone1);
        txtEmail1 = view.findViewById(R.id.txtEmail1);
        txtEducationLevel = view.findViewById(R.id.txtEducationLevel);
        txtReason = view.findViewById(R.id.txtReason);
        txtBiography = view.findViewById(R.id.txtBiography);

        txtFirstName2 = view.findViewById(R.id.txtFirstName2);
        txtLastName2 = view.findViewById(R.id.txtLastName2);
        txtDOB2 = view.findViewById(R.id.txtDate);
        txtOccupation2 = view.findViewById(R.id.txtOccupation);
        txtGender2 = view.findViewById(R.id.txtGender2);
        txtRelationship = view.findViewById(R.id.txtRelationship);
        txtDisability2 = view.findViewById(R.id.txtDisability2);
        txtPhone2 = view.findViewById(R.id.txtPhone2);
        txtEmail2 = view.findViewById(R.id.txtEmail2);

        img1 = view.findViewById(R.id.img1);
        img2 = view.findViewById(R.id.img2);
        img3 = view.findViewById(R.id.img3);
        img4 = view.findViewById(R.id.img4);


        img1.setVisibility(View.GONE);
        img2.setVisibility(View.GONE);
        img3.setVisibility(View.GONE);
        img4.setVisibility(View.GONE);


        moreGuardianOrParentInfoLayout = view.findViewById(R.id.moreGuardianOrParentInfoLayout);
        currentFamilySituationLayout = view.findViewById(R.id.currentFamilySituationLayout);
        sourceOfIncomeLayout = view.findViewById(R.id.sourceOfIncomeLayout);
        averageMonthlyIncomeLayout = view.findViewById(R.id.averageMonthlyIncomeLayout);
        typeOfHouseLayout = view.findViewById(R.id.typeOfHouseLayout);
        numberOfPeopleInHouseLayout = view.findViewById(R.id.numberOfPeopleInHouseLayout);
        numberOfPeopleUnder18Layout = view.findViewById(R.id.numberOfPeopleUnder18Layout);
        typeOfLightLayout = view.findViewById(R.id.typeOfLightLayout);
        typeOfCookingEnergyLayout = view.findViewById(R.id.typeOfCookingEnergyLayout);
        typeOfToiletLayout = view.findViewById(R.id.typeOfToiletLayout);
        typeOfWaterSourceLayout = view.findViewById(R.id.typeOfWaterSourceLayout);
        distanceFromWaterSourceLayout = view.findViewById(R.id.distanceFromWaterSourceLayout);
        typeOfFloorLayout = view.findViewById(R.id.typeOfFloorLayout);
        numberOfSiblingsLayout = view.findViewById(R.id.numberOfSiblingsLayout);
        numberOfSiblingsInSchoolLayout = view.findViewById(R.id.numberOfSiblingsInSchoolLayout);
        financialChallengesLayout = view.findViewById(R.id.financialChallengesLayout);
        circumstancesAffectingSchoolingLayout = view.findViewById(R.id.circumstancesAffectingSchoolingLayout);
        missedSchoolDueToLackOfResourcesLayout = view.findViewById(R.id.missedSchoolDueToLackOfResourcesLayout);
        numberOfMealsPerDayLayout = view.findViewById(R.id.numberOfMealsPerDayLayout);
        responsibilitiesAtHomeLayout = view.findViewById(R.id.responsibilitiesAtHomeLayout);
        academicGoalsLayout = view.findViewById(R.id.academicGoalsLayout);
        nameOfSponsorLayout = view.findViewById(R.id.nameOfSponsorLayout);
        typeOfSponsorshipLayout = view.findViewById(R.id.typeOfSponsorshipLayout);
        durationOfSponsorshipLayout = view.findViewById(R.id.durationOfSponsorshipLayout);
        reasonWhySponsorshipStoppedLayout = view.findViewById(R.id.reasonWhySponsorshipStoppedLayout);
        howSponsorshipHelpedYouLayout = view.findViewById(R.id.howSponsorshipHelpedYouLayout);
        howYouFeltWhenSponsorshipStoppedLayout = view.findViewById(R.id.howYouFeltWhenSponsorshipStoppedLayout);
        planForPloughBackToCommunityLayout = view.findViewById(R.id.planForPloughBackToCommunityLayout);
        //homeLocationLayout = view.findViewById(R.id.homeLocationLayout);
        howYouKnewAboutPamhodziKidsFoundationLayout = view.findViewById(R.id.howYouKnewAboutPamhodziKidsFoundationLayout);

        moreGuardianOrParentInfoLayout.setEnabled(false);
        moreGuardianOrParentInfoLayout.setHint("");
        currentFamilySituationLayout.setEnabled(false);
        currentFamilySituationLayout.setHint("");
        sourceOfIncomeLayout.setEnabled(false);
        sourceOfIncomeLayout.setHint("");
        averageMonthlyIncomeLayout.setEnabled(false);
        averageMonthlyIncomeLayout.setHint("");
        typeOfHouseLayout.setEnabled(false);
        typeOfHouseLayout.setHint("");
        numberOfPeopleInHouseLayout.setEnabled(false);
        numberOfPeopleInHouseLayout.setHint("");
        numberOfPeopleUnder18Layout.setEnabled(false);
        numberOfPeopleUnder18Layout.setHint("");
        typeOfLightLayout.setEnabled(false);
        typeOfLightLayout.setHint("");
        typeOfCookingEnergyLayout.setEnabled(false);
        typeOfCookingEnergyLayout.setHint("");
        typeOfToiletLayout.setEnabled(false);
        typeOfToiletLayout.setHint("");
        typeOfWaterSourceLayout.setEnabled(false);
        typeOfWaterSourceLayout.setHint("");
        distanceFromWaterSourceLayout.setEnabled(false);
        distanceFromWaterSourceLayout.setHint("");
        typeOfFloorLayout.setEnabled(false);
        typeOfFloorLayout.setHint("");
        numberOfSiblingsLayout.setEnabled(false);
        numberOfSiblingsLayout.setHint("");
        numberOfSiblingsInSchoolLayout.setEnabled(false);
        numberOfSiblingsInSchoolLayout.setHint("");
        financialChallengesLayout.setEnabled(false);
        financialChallengesLayout.setHint("");
        circumstancesAffectingSchoolingLayout.setEnabled(false);
        circumstancesAffectingSchoolingLayout.setHint("");
        missedSchoolDueToLackOfResourcesLayout.setEnabled(false);
        missedSchoolDueToLackOfResourcesLayout.setHint("");
        numberOfMealsPerDayLayout.setEnabled(false);
        numberOfMealsPerDayLayout.setHint("");
        responsibilitiesAtHomeLayout.setEnabled(false);
        responsibilitiesAtHomeLayout.setHint("");
        academicGoalsLayout.setEnabled(false);
        academicGoalsLayout.setHint("");
        nameOfSponsorLayout.setEnabled(false);
        nameOfSponsorLayout.setHint("");
        typeOfSponsorshipLayout.setEnabled(false);
        typeOfSponsorshipLayout.setHint("");
        durationOfSponsorshipLayout.setEnabled(false);
        durationOfSponsorshipLayout.setHint("");
        reasonWhySponsorshipStoppedLayout.setEnabled(false);
        reasonWhySponsorshipStoppedLayout.setHint("");
        howSponsorshipHelpedYouLayout.setEnabled(false);
        howSponsorshipHelpedYouLayout.setHint("");
        howYouFeltWhenSponsorshipStoppedLayout.setEnabled(false);
        howYouFeltWhenSponsorshipStoppedLayout.setHint("");
        planForPloughBackToCommunityLayout.setEnabled(false);
        planForPloughBackToCommunityLayout.setHint("");
        //   homeLocationLayout.setEnabled(false);
        // homeLocationLayout.setHint("");
        howYouKnewAboutPamhodziKidsFoundationLayout.setEnabled(false);
        howYouKnewAboutPamhodziKidsFoundationLayout.setHint("");


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        Bundle bundle = getArguments();
        String id = bundle.getString("id");

        Log.e("Sponsee ID", id);


        modelSponsee = (ModelSponsee) bundle.getSerializable("sponsee");


        String dobString = modelSponsee.getDob();

        txtName1.setText(modelSponsee.getFirstname() + " " + modelSponsee.getLastname());
        txtDOB1.setText("D.O.B : " + modelSponsee.getDob());
        txtAddress.setText("Address : " + modelSponsee.getAddress());
        txtGender1.setText("Gender : " + modelSponsee.getGender());

        txtCountry.setText(modelSponsee.getCountry());
        txtOccupation1.setText(modelSponsee.getOccupation());
        txtDisability1.setText(modelSponsee.getDisability());
        txtParentsAlive.setText(modelSponsee.getParentsAlive());
        txtPhone1.setText(modelSponsee.getPhone());
        txtEmail1.setText(modelSponsee.getEmail());
        txtEducationLevel.setText(modelSponsee.getEducation());
        txtReason.setText(modelSponsee.getReason());
        txtBiography.setText(modelSponsee.getBio());

        txtFirstName2.setText(modelSponsee.getGuardianfirstName());
        txtLastName2.setText(modelSponsee.getGuardianlastName());
        txtDOB2.setText(modelSponsee.getGuardianDOB());
        txtOccupation2.setText(modelSponsee.getGuardianOccupation());
        txtGender2.setText(modelSponsee.getGuardianGender());
        txtRelationship.setText(modelSponsee.getRelationship());
        txtDisability2.setText(modelSponsee.getGuardianDisability());
        txtPhone2.setText(modelSponsee.getGuardianPhone());
        txtEmail2.setText(modelSponsee.getGuardianEmail());


        moreGuardianOrParentInfoLayout.getEditText().setText(modelSponsee.getMoreGuardianOrParentInfo());
        currentFamilySituationLayout.getEditText().setText(modelSponsee.getCurrentFamilysituation());
        sourceOfIncomeLayout.getEditText().setText(modelSponsee.getSourceOfIncome());
        averageMonthlyIncomeLayout.getEditText().setText(modelSponsee.getAverageMonthlyIncome());
        typeOfHouseLayout.getEditText().setText(modelSponsee.getTypeOfHouseYouLiveIn());
        numberOfPeopleInHouseLayout.getEditText().setText(modelSponsee.getNumberOfPeopleInYourHouse());
        numberOfPeopleUnder18Layout.getEditText().setText(modelSponsee.getNumberOfPeopleInYourHouseUnder18());
        typeOfLightLayout.getEditText().setText(modelSponsee.getTypeOfLightYouUse());
        typeOfCookingEnergyLayout.getEditText().setText(modelSponsee.getTypeOfCookingEnergyYouUse());
        typeOfToiletLayout.getEditText().setText(modelSponsee.getTypeOfToiletYouUse());
        typeOfWaterSourceLayout.getEditText().setText(modelSponsee.getTypeOfWaterYouUse());
        distanceFromWaterSourceLayout.getEditText().setText(modelSponsee.getHowFarIsTheWaterSourceFromYourHouse());
        typeOfFloorLayout.getEditText().setText(modelSponsee.getTypeOfFloorYouHave());
        numberOfSiblingsLayout.getEditText().setText(modelSponsee.getNumberOfSiblingsYouhave());
        numberOfSiblingsInSchoolLayout.getEditText().setText(modelSponsee.getNumberOfSiblingsYouHaveInSchool());
        financialChallengesLayout.getEditText().setText(modelSponsee.getFinancialChallengesYouFace());
        circumstancesAffectingSchoolingLayout.getEditText().setText(modelSponsee.getCircumstancesThatAffectedYourAbilityToGoToSchool());
        missedSchoolDueToLackOfResourcesLayout.getEditText().setText(modelSponsee.getHaveYouEverMissedSchoolDueToLackOfResources());
        numberOfMealsPerDayLayout.getEditText().setText(modelSponsee.getHowManyMealsYouEatPerDay());
        responsibilitiesAtHomeLayout.getEditText().setText(modelSponsee.getAnyResponsibilitiesAtHomeThatAffectYourAbilityToGoToSchool());
        academicGoalsLayout.getEditText().setText(modelSponsee.getYourAcademicGoals());
        nameOfSponsorLayout.getEditText().setText(modelSponsee.getNameOfSponsor());
        typeOfSponsorshipLayout.getEditText().setText(modelSponsee.getIfYesWhatTypeOfSponsorship());
        durationOfSponsorshipLayout.getEditText().setText(modelSponsee.getIfYesHowLongDidYouRecieveTheSponsorship());
        reasonWhySponsorshipStoppedLayout.getEditText().setText(modelSponsee.getIfYesWhyDidItStop());
        howSponsorshipHelpedYouLayout.getEditText().setText(modelSponsee.getIfYesHowDidItHelpYou());
        howYouFeltWhenSponsorshipStoppedLayout.getEditText().setText(modelSponsee.getIfYesHowDidYouFeelWhenItStopped());
        planForPloughBackToCommunityLayout.getEditText().setText(modelSponsee.getCommunityContributionIfYouRecieveSponsorship());
        // homeLocationLayout.getEditText().setText(modelSponsee.getLocationOfHome());
        howYouKnewAboutPamhodziKidsFoundationLayout.getEditText().setText(modelSponsee.getHowDidYouKnowAboutPamodziKidsFoundation());


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

            txtAge.setText("Age : " + String.valueOf(age));

            // The variable "age" now contains the age in years
            // You can use this value as needed in your application

        } catch (ParseException e) {
            txtAge.setText(modelSponsee.getDob());
        }


        List<String> myImages = modelSponsee.getMyImages();

        if (myImages != null) {
            if (myImages.size() >= 1) {

                img1.setVisibility(View.VISIBLE);
                Picasso.get().load(myImages.get(0))
                        .placeholder(R.drawable.loadingimage)
                        .error(R.drawable.loadingimage)
                        .into(img1);

            }
            if (myImages.size() >= 2) {
                img2.setVisibility(View.VISIBLE);
                Picasso.get().load(myImages.get(1))
                        .placeholder(R.drawable.loadingimage)
                        .error(R.drawable.loadingimage)
                        .into(img2);
            }
            if (myImages.size() >= 3) {
                img3.setVisibility(View.VISIBLE);
                Picasso.get().load(myImages.get(2))
                        .placeholder(R.drawable.loadingimage)
                        .error(R.drawable.loadingimage)
                        .into(img3);
                img4.setVisibility(View.VISIBLE);
            }


        }




        receivedSponsorshipBefore = modelSponsee.getHaveYourEverRecievedAnySponsorshipBefore();

        if (receivedSponsorshipBefore.equals("Yes")) {
            sponsorshipDetailsLayout.setVisibility(View.VISIBLE);
            radioGroupReceivedSponsorshipBefore.check(R.id.radioButtonYesReceivedSponsorshipBefore);
        }else if (receivedSponsorshipBefore.equals("No")){
            sponsorshipDetailsLayout.setVisibility(View.GONE);
            radioGroupReceivedSponsorshipBefore.check(R.id.radioButtonNoReceivedSponsorshipBefore);
        }



       /* btnSponsor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                bundle.putSerializable("sponsee", modelSponsee);


                SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("fragment", "toSponsorship");

                editor.apply();


                // Navigate to the Home fragment
                Sponsor_Page sponsor_page = new Sponsor_Page();
                sponsor_page.setArguments(bundle);

                FragmentManager fragmentManager = ((FragmentActivity) getContext()).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, sponsor_page)
                        .commit();

            }
        });*/


        Picasso.get().load(modelSponsee.getPhoto())
                .placeholder(R.drawable.profileblank)
                .error(R.drawable.loadingimage)
                .into(imgProfile);

        Picasso.get().load(modelSponsee.getLetterFromLocalLeaderOrChief())
                .placeholder(R.drawable.loadingimage)
                .error(R.drawable.loadingimage)
                .into(letterImageView);


        for (int i = 0; i < modelSponsee.getEvidenceOfNeed().size(); i++) {
            if (i == 0) {
                Picasso.get().load(modelSponsee.getEvidenceOfNeed().get(i))
                        .placeholder(R.drawable.loadingimage)
                        .error(R.drawable.loadingimage)
                        .into(evidenceImageView1);


            }
            if (i == 1) {
                Picasso.get().load(modelSponsee.getEvidenceOfNeed().get(i))
                        .placeholder(R.drawable.loadingimage)
                        .error(R.drawable.loadingimage)
                        .into(evidenceImageView2);
            }
            if (i == 2) {
                Picasso.get().load(modelSponsee.getEvidenceOfNeed().get(i))
                        .placeholder(R.drawable.loadingimage)
                        .error(R.drawable.loadingimage)
                        .into(evidenceImageView3);
            }
        }





        imgProfile.setOnClickListener(new View.OnClickListener() {
            boolean isExpanded = false;

            @Override
            public void onClick(View v) {
                if (!isExpanded) {
                    ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.5f, 1.0f, 1.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    scaleAnimation.setDuration(300);
                    scaleAnimation.setFillAfter(true);
                    imgProfile.startAnimation(scaleAnimation);
                    isExpanded = true;
                } else {
                    ScaleAnimation scaleAnimation = new ScaleAnimation(1.5f, 1.0f, 1.5f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    scaleAnimation.setDuration(300);
                    scaleAnimation.setFillAfter(true);
                    imgProfile.startAnimation(scaleAnimation);
                    isExpanded = false;
                }
            }
        });





        img1.setOnClickListener(new View.OnClickListener() {
            boolean isExpanded = false;

            @Override
            public void onClick(View v) {
                if (!isExpanded) {
                    ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.5f, 1.0f, 1.5f,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    scaleAnimation.setDuration(300);
                    scaleAnimation.setFillAfter(true);
                    img1.startAnimation(scaleAnimation);
                    isExpanded = true;
                } else {
                    ScaleAnimation scaleAnimation = new ScaleAnimation(1.5f, 1.0f, 1.5f, 1.0f,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    scaleAnimation.setDuration(300);
                    scaleAnimation.setFillAfter(true);
                    img1.startAnimation(scaleAnimation);
                    isExpanded = false;
                }
            }
        });



        img2.setOnClickListener(new View.OnClickListener() {
            boolean isExpanded = false;

            @Override
            public void onClick(View v) {
                if (!isExpanded) {
                    ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.5f, 1.0f, 1.5f,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    scaleAnimation.setDuration(300);
                    scaleAnimation.setFillAfter(true);
                    img2.startAnimation(scaleAnimation);
                    isExpanded = true;
                } else {
                    ScaleAnimation scaleAnimation = new ScaleAnimation(1.5f, 1.0f, 1.5f, 1.0f,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    scaleAnimation.setDuration(300);
                    scaleAnimation.setFillAfter(true);
                    img2.startAnimation(scaleAnimation);
                    isExpanded = false;
                }
            }
        });


        img3.setOnClickListener(new View.OnClickListener() {
            boolean isExpanded = false;

            @Override
            public void onClick(View v) {
                if (!isExpanded) {
                    ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.5f, 1.0f, 1.5f,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    scaleAnimation.setDuration(300);
                    scaleAnimation.setFillAfter(true);
                    img3.startAnimation(scaleAnimation);
                    isExpanded = true;
                } else {
                    ScaleAnimation scaleAnimation = new ScaleAnimation(1.5f, 1.0f, 1.5f, 1.0f,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    scaleAnimation.setDuration(300);
                    scaleAnimation.setFillAfter(true);
                    img3.startAnimation(scaleAnimation);
                    isExpanded = false;
                }
            }
        });







        btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                progressDialog.setMessage("Approving Sponsorship Application...");
                progressDialog.setTitle("Please wait");
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                DatabaseReference myDonationsRef = FirebaseDatabase.getInstance().getReference().child("Sponsee");

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                String uid = mAuth.getCurrentUser().getUid();

                myDonationsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot ds : snapshot.getChildren()) {

                            if (ds.child("id").getValue().toString().equals(id)) {
                                myDonationsRef.child(ds.getKey()).child("status").setValue("Approved");
                                break;

                            }
                        }




                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.context, "Sponsorship Application Approved Successfully", Toast.LENGTH_SHORT).show();

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Successful");
                        builder.setMessage("Sponsorship Application Approved Successfully");
                        builder.setCancelable(false);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                progressDialog.dismiss();

                                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new SponsorshipApplicationsToApprove()).addToBackStack(null).commit();


                            }
                        });
                        builder.show();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("TAG", "onCancelled: " + error.getMessage());
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.context, "Application Approval Failed", Toast.LENGTH_SHORT).show();


                    }
                });





            }
        });

        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                progressDialog.setMessage("Declining Sponsorship Application...");
                progressDialog.setTitle("Please wait");
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                DatabaseReference myDonationsRef = FirebaseDatabase.getInstance().getReference().child("Sponsee");

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                String uid = mAuth.getCurrentUser().getUid();

                myDonationsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot ds : snapshot.getChildren()) {

                            if (ds.child("id").getValue().toString().equals(id)) {
                                myDonationsRef.child(ds.getKey()).child("status").setValue("Declined");
                                break;

                            }
                        }




                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.context, "Sponsorship Application Declined Successfully", Toast.LENGTH_SHORT).show();

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Successful");
                        builder.setMessage("Sponsorship Application Declined Successfully");
                        builder.setCancelable(false);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                progressDialog.dismiss();

                                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new SponsorshipApplicationsToApprove()).addToBackStack(null).commit();


                            }
                        });
                        builder.show();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("TAG", "onCancelled: " + error.getMessage());
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.context, "Application Decline Failed", Toast.LENGTH_SHORT).show();


                    }
                });





            }
        });


    }
}