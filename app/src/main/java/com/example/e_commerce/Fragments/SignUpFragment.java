package com.example.e_commerce.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.e_commerce.R;
import com.example.e_commerce.activities.LoginActivity;
import com.example.e_commerce.activities.MainActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.regex.Pattern;

public class SignUpFragment extends Fragment {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{8,}" +               //at least 4 characters
                    "$");
    TextInputEditText signupEtEmail, signupEtPassword, signupEtName;
    TextInputLayout signupEtEmailLayout, signupEtPasswordLayout, signupEtNameLayout;
    MaterialButton btnSignup;
    ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Welcome to my E-commerce app");
        progressDialog.setMessage("Please wait......");
        progressDialog.setCanceledOnTouchOutside(false);
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sign_up, container, false);
        signupEtEmail = v.findViewById(R.id.sign_up_et_email);
        signupEtPassword = v.findViewById(R.id.sign_up_et_password);
        signupEtName = v.findViewById(R.id.sign_up_et_name);
        signupEtEmailLayout = v.findViewById(R.id.sign_up_et_email_layout);
        signupEtPasswordLayout = v.findViewById(R.id.sign_up_et_password_layout);
        signupEtNameLayout = v.findViewById(R.id.sign_up_et_name_layout);
        btnSignup = v.findViewById(R.id.sign_up_btn_sign_up);


        signupEtNameLayout.setTranslationX(800);
        signupEtEmailLayout.setTranslationX(800);
        signupEtPasswordLayout.setTranslationX(800);
        btnSignup.setTranslationX(800);


        signupEtNameLayout.setAlpha(0);
        signupEtEmailLayout.setAlpha(0);
        signupEtPasswordLayout.setAlpha(0);
        btnSignup.setAlpha(0);


        signupEtNameLayout.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(300).start();
        signupEtEmailLayout.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(500).start();
        signupEtPasswordLayout.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(500).start();
        btnSignup.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(700).start();


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String signupName = signupEtName.getText().toString();
                String signupEmail = signupEtEmail.getText().toString().trim();
                String signupPassword = signupEtPassword.getText().toString().trim();
                Toast.makeText(getActivity(), "Succefully Register", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
                progressDialog.dismiss();
                if (TextUtils.isEmpty(signupName)) {
                    signupEtNameLayout.setError("s");
                    Toast.makeText(getActivity(), "Name is required", Toast.LENGTH_SHORT).show();
                    signupEtName.requestFocus();
                } else if (TextUtils.isEmpty(signupEmail)) {
                    signupEtNameLayout.setErrorEnabled(false);
                    signupEtName.clearFocus();
                    signupEtEmailLayout.setError("s");
                    Toast.makeText(getActivity(), "Email is required", Toast.LENGTH_SHORT).show();
                    signupEtEmail.requestFocus();
                    return;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(signupEmail).matches()) {
                    signupEtNameLayout.setErrorEnabled(false);
                    signupEtName.clearFocus();
                    signupEtEmailLayout.setError("d");
                    Toast.makeText(getActivity(), "Email is worng, please enter valid email", Toast.LENGTH_SHORT).show();
                    signupEtEmail.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(signupPassword)) {
                    signupEtNameLayout.setErrorEnabled(false);
                    signupEtName.clearFocus();
                    signupEtEmailLayout.setErrorEnabled(false);
                    Toast.makeText(getActivity(), "Password is required", Toast.LENGTH_SHORT).show();
                    signupEtPasswordLayout.setError("d");
                    signupEtPassword.requestFocus();
                    return;
                } else if (!PASSWORD_PATTERN.matcher(signupPassword).matches()) {
                    signupEtNameLayout.setErrorEnabled(false);
                    signupEtName.clearFocus();
                    signupEtEmailLayout.setErrorEnabled(false);
                    Toast.makeText(getActivity(), "Password is too weak", Toast.LENGTH_SHORT).show();
                    signupEtPasswordLayout.setError("d");
                    signupEtPassword.requestFocus();
                    return;
                } else {
                    progressDialog.show();
                    signupEtNameLayout.setErrorEnabled(false);
                    signupEtName.clearFocus();
                    signupEtEmailLayout.setErrorEnabled(false);
                    signupEtPasswordLayout.setErrorEnabled(false);
                    signupEtPassword.clearFocus();
                    signupEtEmail.clearFocus();

//                    signUp(signupName, signupEmail, signupPassword);
                    firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.createUserWithEmailAndPassword(signupEmail, signupPassword)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getActivity(), "Succefully Register", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                        progressDialog.dismiss();
                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(getActivity(), "Registration Failed" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    return;
                }
            }
        });

        return v;
    }

    private void signUp(String signupName, String signupEmail, String signupPassword) {
//        DatabaseReference signUpdbRef;
//        signUpdbRef = FirebaseDatabase.getInstance().getReference();
//        signUpdbRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (!(snapshot.child(LoginActivity.PARENT_DB_NAME).child("email").exists())) {
//                    HashMap<String, Object> userDataMap = new HashMap<>();
//                    userDataMap.put("Name", signupName);
//                    userDataMap.put("Email", signupEmail);
//                    userDataMap.put("Password", signupPassword);
//                    signUpdbRef.child("Users").child(signupEmail)
//                            .updateChildren(userDataMap)
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//                                        Toast.makeText(getActivity(), "Succefully Register", Toast.LENGTH_SHORT).show();
//                                        Intent intent = new Intent(getActivity(), MainActivity.class);
//                                        startActivity(intent);
//                                        getActivity().finish();
//                                    } else {
//                                        Toast.makeText(getActivity(), "Network Error, Please try again after some time...", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                } else {
//                    Toast.makeText(getActivity(), "This Email is Already Exist", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }
}