package com.example.covid_19;

import static com.example.covid_19.R.id.btn_send;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CALL = 1;
    private static final int SEND_SMS = 1;
    Button btn_call,btn_sms,btn_statis,btn_send;
    Spinner spinner;
    ImageView flags;
    Dialog mDialog;
    EditText txtfeeling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadLocale();
        spinner = findViewById(R.id.spinner);
        flags = findViewById(R.id.flag);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));

        mDialog = new Dialog(this);
        mDialog.setContentView(R.layout.sms_popup);

        btn_statis = findViewById(R.id.btn_statis);
        btn_call = findViewById(R.id.btn_call);
        btn_sms = findViewById(R.id.btn_sms);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                flags.setImageResource(CountryData.countryFlag[spinner.getSelectedItemPosition()]);
                String selectLang = parent.getItemAtPosition(position).toString();

                if(selectLang.equals("ENG")){
                   // setLocal("values-en");
                   // recreate();
                }
                else if (selectLang.equals("VIE"))
                {
                   // setLocal("values-vi");
                   // recreate();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setLocal("en");
            }
        });

        btn_statis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, StatisticsActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left); // Animation between activity
            }
        });
        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call();
            }
        });
        btn_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSMS();
            }
        });

    }

    private void openSMS() {
        mDialog.setContentView(R.layout.sms_popup);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();

        txtfeeling = mDialog.findViewById((R.id.txt_feeling));
        btn_send = mDialog.findViewById(R.id.btn_send);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                    String phone = "0857102032";
                    String message = txtfeeling.getText().toString();

                    if(!message.isEmpty()){
                        // Khởi tạo trình quản lý SMS
                        SmsManager smsManager = SmsManager.getDefault();

                        smsManager.sendTextMessage(phone,null,message,null,null);

                        Toast.makeText(MainActivity.this, "SMS send successfully", Toast.LENGTH_SHORT).show();

                        mDialog.dismiss();
                    }
                    else
                    {
                        //Toast.makeText(this, "Prevention not empty", Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, "Prevention not empty", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 100);
                }
            }
        });

    }


    private void setLocal(String langCode){
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        //Resources resources = activity.getResources();
        Configuration config = new Configuration();
        config.locale = locale;
       // resources.updateConfiguration(config,resources.getDisplayMetrics());
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Setting", MODE_PRIVATE).edit();
        editor.putString("My_Lang",langCode);
        editor.apply();
    }
    private void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Setting", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_lang","");
        setLocal(language);
    }
    private void Call()
    {
        String number = "0857102032";
        if(number.trim().length()>0)
        {
            if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
            }
            else{
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Call();
            }
            else
            {
                Toast.makeText(this, "Permisson denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}