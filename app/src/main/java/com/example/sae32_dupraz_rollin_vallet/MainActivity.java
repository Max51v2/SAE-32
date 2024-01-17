package com.example.sae32_dupraz_rollin_vallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button boutton1;
    boolean AltBannerVisible = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //_____________Variables/Objets_______________
        View MainBannerButton = findViewById(R.id.MainBannerButton);
        View MainBannerButtonCross = findViewById(R.id.MainBannerButtonCross);
        View MainBannerOpened = findViewById(R.id.MainBannerOpened);
        View MainButtonIP = findViewById(R.id.MainButtonIP);
        View MainButtonVLSM = findViewById(R.id.MainButtonVLSM);
        View MainButtonWIFI = findViewById(R.id.MainButtonWIFI);
        View MainButtonLang = findViewById(R.id.MainButtonLang);
        View MainButtonInfo = findViewById(R.id.MainButtonInfo);
        //____________________________________________


        //Ouverture du bandeau latéral
        MainBannerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainBannerButtonCross.setVisibility(View.VISIBLE);
                MainBannerOpened.setVisibility(View.VISIBLE);
                MainButtonLang.setVisibility(View.VISIBLE);
                MainButtonInfo.setVisibility(View.VISIBLE);
                MainBannerButton.setVisibility(View.INVISIBLE);
                MainButtonIP.setVisibility(View.INVISIBLE);
                MainButtonVLSM.setVisibility(View.INVISIBLE);
                MainButtonWIFI.setVisibility(View.INVISIBLE);
            }
        });

        //Fermeture du bandeau latéral
        MainBannerButtonCross.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                MainBannerButtonCross.setVisibility(View.INVISIBLE);
                MainBannerOpened.setVisibility(View.INVISIBLE);
                MainButtonLang.setVisibility(View.INVISIBLE);
                MainButtonInfo.setVisibility(View.INVISIBLE);
                MainBannerButton.setVisibility(View.VISIBLE);
                MainButtonIP.setVisibility(View.VISIBLE);
                MainButtonVLSM.setVisibility(View.VISIBLE);
                MainButtonWIFI.setVisibility(View.VISIBLE);
            }
        });


        //Changement d'activité (IP)
        MainButtonIP.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, IP.class));
            }
        });


        //Changement d'activité (VLSM)
        MainButtonVLSM.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });


        //Changement d'activité (WI-FI)
        MainButtonWIFI.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });


        //Changement de langue (à finir)
        MainButtonLang.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Locale localeEn = new Locale("en");
                Locale localeFr = new Locale("Default Value");
                Locale.setDefault(localeEn);
                Configuration config = new Configuration();
                config.locale = localeEn;
                Log.d("langueEn", "onClick: ");
            }
        });

    }
}