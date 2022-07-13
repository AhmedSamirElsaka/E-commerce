package com.example.e_commerce.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.e_commerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {
    TextInputEditText etForgetPasswordEmail;
    TextInputLayout etForgetPasswordEmailLayout;
    MaterialButton btnForgetPasswordContinue;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        auth = FirebaseAuth.getInstance();
        etForgetPasswordEmail = findViewById(R.id.forget_password_et_email);
        btnForgetPasswordContinue = findViewById(R.id.forget_password_btn_continue);
        etForgetPasswordEmailLayout = findViewById(R.id.forget_password_et_email_layout);
        btnForgetPasswordContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ForgetPasswordEmail = etForgetPasswordEmail.getText().toString().trim();
                if (TextUtils.isEmpty(ForgetPasswordEmail)) {
                    etForgetPasswordEmailLayout.setError("s");
                    Toast.makeText(getBaseContext(), "Email is required", Toast.LENGTH_SHORT).show();
                    etForgetPasswordEmailLayout.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(ForgetPasswordEmail).matches()) {
                    etForgetPasswordEmailLayout.setError("d");
                    Toast.makeText(getBaseContext(), "Email is worng, please enter valid email", Toast.LENGTH_SHORT).show();
                    etForgetPasswordEmailLayout.requestFocus();
                } else {
                    auth.sendPasswordResetEmail(ForgetPasswordEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ForgetPassword.this, "check your email to reset password", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(ForgetPassword.this, "Try again , something is wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}