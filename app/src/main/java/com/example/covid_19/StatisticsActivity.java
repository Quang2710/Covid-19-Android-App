package com.example.covid_19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

public class StatisticsActivity extends AppCompatActivity {

    TextView affected,dead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        affected = findViewById(R.id.affected);
        dead = findViewById((R.id.dead));
        showData();

    }
    public void showData(){
        Intent intent = getIntent();
        String Affected = intent.getStringExtra("Affected");
        String Dead = intent.getStringExtra("Dead");
//        FirebaseDatabase.getInstance().getReference().child("Global").push().child("Affected");
//        FirebaseDatabase.getInstance().getReference().child("Global").push().child("Dead");
        affected.setText(Affected);
        dead.setText(Dead);
    }
}