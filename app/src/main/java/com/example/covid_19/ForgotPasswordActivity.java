package com.example.covid_19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText forgotEmail;
    private Button btn_forgot;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        forgotEmail = findViewById(R.id.txt_forgotpass);
        btn_forgot = findViewById(R.id.btn_forgot);

        btn_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = forgotEmail.getText().toString();

                if(email.isEmpty())
                {
                    Toast.makeText(ForgotPasswordActivity.this, "Please provide your email", Toast.LENGTH_SHORT).show();
                }
                else {
                    forgotPassWord();
                }
            }
        });
    }

    private void forgotPassWord(){
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPasswordActivity.this, "Check your email", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
                    //finish();
                }
                else{
                    Toast.makeText(ForgotPasswordActivity.this, "Error : " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}