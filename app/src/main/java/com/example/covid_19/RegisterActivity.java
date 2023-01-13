package com.example.covid_19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText txt_user,txt_password,txt_re_password;
    private Button btn_register;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txt_user = findViewById(R.id.txt_username);
        txt_password = findViewById(R.id.txt_passwords);
        txt_re_password = findViewById(R.id.txt_re_password);
        btn_register = findViewById(R.id.btn_register);
        mAuth = FirebaseAuth.getInstance();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {
        String user, pass, repass,email;
        user = txt_user.getText().toString();
        pass = txt_password.getText().toString();
        repass = txt_re_password.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(repass))
        {
            Toast.makeText(this, "Input not empty", Toast.LENGTH_SHORT).show();
        }
        else if (pass.equals(repass) && pass.length() > 7) {
            mAuth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Register successfull", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left); // Animation between activity
                    } else {
                        Toast.makeText(getApplicationContext(), "Register faild!!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else {
            Toast.makeText(getApplicationContext(), "Password > 8 letter and password = confirm password", Toast.LENGTH_SHORT).show();
        }
        if (user.matches(emailPattern))
        {
            Toast.makeText(getApplicationContext(),"Valid email address",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
        }
    }
}