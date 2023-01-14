package com.example.covid_19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class StatisticsActivity extends AppCompatActivity {

    TextView affected, dead, recovered, active, serious;
    Button btn_global, btn_local;
    DatabaseReference reff;
    PhotoView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        affected = findViewById(R.id.affected);
        dead = findViewById((R.id.dead));
        recovered = findViewById((R.id.recovered));
        active = findViewById((R.id.active));
        serious = findViewById((R.id.serious));
        btn_global = findViewById(R.id.btn_global);
        btn_local = findViewById(R.id.btn_local);
        photoView = (PhotoView) findViewById(R.id.photo_view);

        showDataGlobal();
        styleGlobalBtn();
        btn_global.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDataGlobal();
                styleGlobalBtn();
            }
        });
        btn_local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDataLocal();
                styleLocalBtn();
            }
        });
    }

    public void showDataGlobal() {
        reff = FirebaseDatabase.getInstance().getReference().child("Datashow").child("Global");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Affected = snapshot.child("Affected").getValue().toString();
                String Dead = snapshot.child("Dead").getValue().toString();
                String Recovered = snapshot.child("Recovered").getValue().toString();
                String Active = snapshot.child("Active").getValue().toString();
                String Serious = snapshot.child("Serious").getValue().toString();

                affected.setText(Affected);
                dead.setText(Dead);
                recovered.setText(Recovered);
                active.setText(Active);
                serious.setText(Serious);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void showDataLocal() {
        reff = FirebaseDatabase.getInstance().getReference().child("Datashow").child("Local");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Affected = snapshot.child("Affected").getValue().toString();
                String Dead = snapshot.child("Dead").getValue().toString();
                String Recovered = snapshot.child("Recovered").getValue().toString();
                String Active = snapshot.child("Active").getValue().toString();
                String Serious = snapshot.child("Serious").getValue().toString();

                affected.setText(Affected);
                dead.setText(Dead);
                recovered.setText(Recovered);
                active.setText(Active);
                serious.setText(Serious);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void styleGlobalBtn() {
        btn_global.setBackgroundResource(R.drawable.button_action);
        btn_global.setTextColor(Color.rgb(255, 255, 255));
        btn_local.setBackgroundResource(R.drawable.change_local_global);
        btn_local.setTextColor(Color.rgb(0, 0, 0));
        photoView.setImageResource(R.drawable.map);

    }

    public void styleLocalBtn() {
        btn_local.setBackgroundResource(R.drawable.button_action);
        btn_local.setTextColor(Color.rgb(255, 255, 255));
        btn_global.setBackgroundResource(R.drawable.change_local_global);
        btn_global.setTextColor(Color.rgb(0, 0, 0));
        photoView.setImageResource(R.drawable.covid_map_local);

    }

}