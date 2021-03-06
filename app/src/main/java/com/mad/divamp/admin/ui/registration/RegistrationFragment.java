package com.mad.divamp.admin.ui.registration;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anton46.stepsview.StepsView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mad.divamp.R;
import com.mad.divamp.admin.ui.registration.RegistrationViewModel;
import com.mad.divamp.databinding.FragmentRegistrationBinding;

import  com.mad.divamp.admin.models.registrationModel;
import com.skydoves.powerspinner.IconSpinnerItem;
import com.skydoves.powerspinner.PowerSpinnerView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import com.mad.divamp.utils.SHA256;

import es.dmoral.toasty.Toasty;

public class RegistrationFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StepsView stepsView;
    private FragmentRegistrationBinding binding;
    ConstraintLayout page1_ConstraintLayout_top, page1_ConstraintLayout_bottom, page2_ConstraintLayout_top, page2_ConstraintLayout_bottom, page3_ConstraintLayout;
    Button next1, next2, next3;


    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{6,}" +                // at least 6 characters
                    "$");

    private static final Pattern EMAILD_PATTERN =
            Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RegistrationViewModel registrationViewModel =
                new ViewModelProvider(this).get(RegistrationViewModel.class);

        binding = FragmentRegistrationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //
        TextView email, centerName, password, repassword, inchargeNIC, inchargeFullName, contactNo, inchargeEmail;
        Spinner provice, district;

        page1_ConstraintLayout_top = binding.adminRegPage1Inputs;
        page1_ConstraintLayout_bottom = binding.adminRegPage1Buttonset;
        page2_ConstraintLayout_top = binding.adminRegPage2Inputs;
        page2_ConstraintLayout_bottom = binding.adminRegPage2Buttonset;
        page3_ConstraintLayout = binding.adminRegPage3Image;

        stepsView = binding.stepsViewAdmin;
        email = binding.adminPage1Email;
        centerName = binding.adminPage1CenterName;
        provice = binding.adminPage1SpinnerProvince;
        district = binding.adminPage1SpinnerDistrict;
        password = binding.adminPage1Password;
        repassword = binding.adminPage1RePassword;
        inchargeNIC = binding.adminPage2NIC;
        inchargeFullName = binding.adminPage2FullName;
        contactNo = binding.adminPage2ContactNo;
        inchargeEmail = binding.adminPage2InchargeEmail;

        stepsView
                .setLabels(new String[] {"Center Details", "In-Charge Details", "Finish"})
                .setBarColorIndicator(getContext().getResources().getColor(R.color.inactive))
                .setProgressColorIndicator(getContext().getResources().getColor(R.color.light_green))
                .setLabelColorIndicator(getContext().getResources().getColor(R.color.inactive))
                .setCompletedPosition(0)
                .drawView();

        provincevalidator();
        firstPageVisible();
        next1 = binding.adminRegNextBtn1;
        next2 = binding.adminRegNextBtn2;
        next3 = binding.adminRegNextBtn3;
        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  if(email.getText().toString().isEmpty()){
                      email.setError("Please enter your email !");
                  }
                  if(!EMAILD_PATTERN.matcher(email.getText().toString()).matches()){
                      email.setError("Please enter valid email !");
                  }
                  if(centerName.getText().toString().isEmpty()){
                      centerName.setError("Please enter your vaccination center name");
                  }
                  if(provice.isSelected()){

                      Toasty.error(getActivity(), "Please select province !", Toast.LENGTH_LONG, true).show();
                  }
                  if(district.isSelected()){
                      Toasty.error(getActivity(), "Please select district !", Toast.LENGTH_LONG, true).show();
                  }
                  if(password.getText().toString().isEmpty()){
                      password.setError("Please enter your password");
                  }
                  if(repassword.getText().toString().isEmpty()){
                      repassword.setError("Please enter your re-password");
                  }
                  if( ! password.getText().toString().equals(repassword.getText().toString())){
                      repassword.setError("Please type your password again correctly !");
                  }
                  if(!PASSWORD_PATTERN.matcher(password.getText().toString()).matches()){

                      password.setError("Please make your password with 6 character and 1 special character");
                  }
                  else {
                      goToSecondPage();
                  }

            }
        });

        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(inchargeNIC.getText().toString().isEmpty()){
                    inchargeNIC.setError("Please enter your In-charge NIC !");
                }
                if(inchargeFullName.getText().toString().isEmpty()){
                    inchargeFullName.setError("Please enter your In-charge's name");
                }
                if(contactNo.getText().toString().isEmpty()){
                    contactNo.setError("Please enter your contact number");
                }
                if(inchargeEmail.getText().toString().isEmpty()){
                    inchargeEmail.setError("Please enter your In-charge's email");
                }
                if(!EMAILD_PATTERN.matcher(inchargeEmail.getText().toString()).matches()){
                    inchargeEmail.setError("Please enter valid email");
                }
                else {
                    insertVaccinationCenter(email.getText().toString(), centerName.getText().toString(),provice.getSelectedItem().toString(),
                            district.getSelectedItem().toString(), SHA256.getHash(password.getText().toString()), inchargeNIC.getText().toString(),
                            contactNo.getText().toString(), inchargeEmail.getText().toString(), inchargeFullName.getText().toString());

                }

            }
        });

        next3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email.setText("");
                centerName.setText("");
                provice.setSelected(false);
                district.setSelected(false);
                password.setText("");
                repassword.setText("");
                inchargeNIC.setText("");
                inchargeFullName.setText("");
                contactNo.setText("");
                inchargeEmail.setText("");

                goToFirstPage();


            }
        });




       // final TextView textView = binding.textRegistration;

       // registrationViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }
    private void firstPageVisible(){
        stepsView.setCompletedPosition(0).drawView();
        page1_ConstraintLayout_top.setVisibility(View.VISIBLE);
        page1_ConstraintLayout_bottom.setVisibility(View.VISIBLE);
        page2_ConstraintLayout_top.setVisibility(View.GONE);
        page2_ConstraintLayout_bottom.setVisibility(View.GONE);
        page3_ConstraintLayout.setVisibility(View.GONE);
    }
    private void secondPageVisible(){
        stepsView.setCompletedPosition(1).drawView();
        page1_ConstraintLayout_top.setVisibility(View.GONE);
        page1_ConstraintLayout_bottom.setVisibility(View.GONE);
        page2_ConstraintLayout_top.setVisibility(View.VISIBLE);
        page2_ConstraintLayout_bottom.setVisibility(View.VISIBLE);
        page3_ConstraintLayout.setVisibility(View.GONE);
    }
    private void thirdPageVisible(){
        stepsView.setCompletedPosition(2).drawView();
        page1_ConstraintLayout_top.setVisibility(View.GONE);
        page1_ConstraintLayout_bottom.setVisibility(View.GONE);
        page2_ConstraintLayout_top.setVisibility(View.GONE);
        page2_ConstraintLayout_bottom.setVisibility(View.GONE);
        page3_ConstraintLayout.setVisibility(View.VISIBLE);
    }
    private void goToSecondPage(){

        secondPageVisible();
    }
    private void goToThirdPage(){

        thirdPageVisible();
    }
    private void goToFirstPage(){

        firstPageVisible();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void provincevalidator(){

        String[] provinceArray = getResources().getStringArray(R.array.province);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item,provinceArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.adminPage1SpinnerProvince.setAdapter(adapter);

        binding.adminPage1SpinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = adapterView.getItemAtPosition(i).toString();

                switch (selected){
                    case "Western Province":{
                        String[] districtArray = getResources().getStringArray(R.array.western);
                        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item,districtArray);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.adminPage1SpinnerDistrict.setAdapter(adapter);
                        break;
                    }
                    case "Eastern Province":{
                        String[] districtArray = getResources().getStringArray(R.array.eastern);
                        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item,districtArray);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.adminPage1SpinnerDistrict.setAdapter(adapter);
                        break;
                    }
                    case "Southern Province":{
                        String[] districtArray = getResources().getStringArray(R.array.southern);
                        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item,districtArray);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.adminPage1SpinnerDistrict.setAdapter(adapter);
                        break;
                    }
                    case "Northern Province":{
                        String[] districtArray = getResources().getStringArray(R.array.northern);
                        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item,districtArray);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.adminPage1SpinnerDistrict.setAdapter(adapter);
                        break;
                    }
                    case "North Central Province":{
                        String[] districtArray = getResources().getStringArray(R.array.northCentral);
                        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item,districtArray);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.adminPage1SpinnerDistrict.setAdapter(adapter);
                        break;
                    }
                    case "North Western Province":{
                        String[] districtArray = getResources().getStringArray(R.array.northWestern);
                        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item,districtArray);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.adminPage1SpinnerDistrict.setAdapter(adapter);
                        break;
                    }
                    case "Uva Province":{
                        String[]  districtArray = getResources().getStringArray(R.array.uva);
                        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item,districtArray);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.adminPage1SpinnerDistrict.setAdapter(adapter);
                        break;
                    }
                    case "Sabaragamuwa Province":{
                        String[]  districtArray = getResources().getStringArray(R.array.sabaragamuwa);
                        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item,districtArray);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.adminPage1SpinnerDistrict.setAdapter(adapter);
                        break;
                    }
                    case "Central Province":{
                        String[] districtArray = getResources().getStringArray(R.array.central);
                        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item,districtArray);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.adminPage1SpinnerDistrict.setAdapter(adapter);
                        break;
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void insertVaccinationCenter(String email, String CenterName, String province, String district, String password, String inchargeNIC,
                                         String contactNo , String inchargeEmail, String inchargeName){

        registrationModel regModel = new registrationModel();
        regModel.setEmail(email);
        regModel.setCenterName(CenterName);
        regModel.setProvince(province);
        regModel.setDistrict(district);
        regModel.setPassword(password);
        regModel.setInchargeNIC(inchargeNIC);
        regModel.setContactNo(contactNo);
        regModel.setInchargeEmail(inchargeEmail);
        regModel.setInchargeFullName(inchargeName);

        db.collection("center").add(regModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                goToThirdPage();
                Toasty.success(getActivity(), "Vaccination center registration successful", Toast.LENGTH_LONG, true).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toasty.error(getActivity(), "Vaccination center registration failed", Toast.LENGTH_LONG, true).show();
                    }
                });
    }
}