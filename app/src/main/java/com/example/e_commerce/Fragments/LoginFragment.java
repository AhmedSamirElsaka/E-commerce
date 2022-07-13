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
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerce.R;
import com.example.e_commerce.activities.ForgetPassword;
import com.example.e_commerce.activities.LoginActivity;
import com.example.e_commerce.activities.MainActivity;
import com.example.e_commerce.activities.SplashScreen;
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


public class LoginFragment extends Fragment {

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
    TextInputEditText loginEtEmail, loginEtPassword;
    TextInputLayout loginEtEmailLayout, loginEtPasswordLayout;
    MaterialButton btnLogin;
    CheckBox loginRememberCb;
    TextView tvForgetPassword;
    ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Welcome to my E-commerce app");
        progressDialog.setMessage("Please wait......");
        progressDialog.setCanceledOnTouchOutside(false);
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        loginEtEmail = v.findViewById(R.id.login_et_email);
        loginEtPassword = v.findViewById(R.id.login_et_password);
        btnLogin = v.findViewById(R.id.login_btn_login);
        loginEtEmailLayout = v.findViewById(R.id.textInputLayout);
        loginEtPasswordLayout = v.findViewById(R.id.textInputLayout2);
        loginRememberCb = v.findViewById(R.id.login_checkbox);
        tvForgetPassword = v.findViewById(R.id.login_tv_forget_password);
        tvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ForgetPassword.class);
                startActivity(intent);
            }
        });

        loginEtEmailLayout.setTranslationX(800);
        loginEtPasswordLayout.setTranslationX(800);
        btnLogin.setTranslationX(800);
        loginRememberCb.setTranslationX(800);
        tvForgetPassword.setTranslationX(800);


        loginEtEmailLayout.setAlpha(0);
        loginEtPasswordLayout.setAlpha(0);
        btnLogin.setAlpha(0);
        loginRememberCb.setAlpha(0);
        tvForgetPassword.setAlpha(0);


        loginEtEmailLayout.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(300).start();
        loginEtPasswordLayout.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(500).start();
        loginRememberCb.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(700).start();
        tvForgetPassword.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(900).start();
        btnLogin.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(1100).start();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loginEmail = loginEtEmail.getText().toString().trim();
                String loginPassword = loginEtPassword.getText().toString().trim();
                if (TextUtils.isEmpty(loginEmail)) {
                    loginEtEmailLayout.setError("s");
                    Toast.makeText(getActivity(), "Email is required", Toast.LENGTH_SHORT).show();
                    loginEtEmailLayout.requestFocus();
                    return;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(loginEmail).matches()) {
                    loginEtEmailLayout.setError("d");
                    Toast.makeText(getActivity(), "Email is worng, please enter valid email", Toast.LENGTH_SHORT).show();
                    loginEtEmail.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(loginPassword)) {
                    loginEtEmailLayout.setErrorEnabled(false);
                    Toast.makeText(getActivity(), "Password is required", Toast.LENGTH_SHORT).show();
                    loginEtPasswordLayout.setError("d");
                    loginEtPassword.requestFocus();
                    return;
                } else {
                    progressDialog.show();
                    loginEtEmailLayout.setErrorEnabled(false);
                    loginEtPasswordLayout.setErrorEnabled(false);
                    loginEtPassword.clearFocus();
                    loginEtEmail.clearFocus();

//                    login(loginEmail , loginPassword) ;
                    firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.signInWithEmailAndPassword(loginEmail, loginPassword)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getActivity(), "Succefully login", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                        progressDialog.dismiss();
                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(getActivity(), "login Failed" + task.getException(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    return;
                }

            }
        });
        return v;
    }

//    private void login(String loginEmail, String loginPassword) {
//        DatabaseReference signUpdbRef;
//        signUpdbRef = FirebaseDatabase.getInstance().getReference();
//        signUpdbRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if ((snapshot.child(LoginActivity.PARENT_DB_NAME).child(loginEmail).exists())) {
//                    HashMap<String, Object> userDataMap = new HashMap<>();
//                    userDataMap.put("Name", signupName);
//                    userDataMap.put("Email", signupEmail);
//                    userDataMap.put("Password", signupPassword);
//                    signUpdbRef.child("Users").child(loginEmail)
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
//                    Toast.makeText(getActivity(), "Account with this " + loginEmail + " does not exists", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
}