package com.example.sae32_dupraz_rollin_vallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class WIFI extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);


        //_____________Variables/Objets_______________
        View WIFIBannerButtonHome = findViewById(R.id.WIFIBannerButtonHome);
        View WIFITextIP = findViewById(R.id.WIFITextIP);
        final EditText WIFITextIP2 =  (EditText) findViewById(R.id.WIFITextIP2);
        //____________________________________________


        //Changement d'activité (Main)
        WIFIBannerButtonHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(WIFI.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
            }
        });


        //Infos

    }
}