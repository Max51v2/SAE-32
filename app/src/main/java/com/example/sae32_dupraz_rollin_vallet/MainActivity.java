package com.example.sae32_dupraz_rollin_vallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    //_____________Variables/Objets_______________
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Button boutton1;
    boolean AltBannerVisible = false;
    //____________________________________________


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
        preferences=getSharedPreferences("Save_Main",MODE_PRIVATE);
        editor=preferences.edit();
        //____________________________________________


        //Bandeau latéral
        //Ouverture
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

        //Fermeture
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



        //Requête des permissions pour la localisation
        requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);


        //Changement d'activité (Informations)
        MainButtonInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Informations.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                overridePendingTransition(0, 0);
            }
        });


        //Changement d'activité (IP)
        MainButtonIP.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, IP.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                overridePendingTransition(0, 0);
            }
        });


        //Changement d'activité (VLSM)
        MainButtonVLSM.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, VLSM.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                overridePendingTransition(0, 0);
            }
        });


        //Changement d'activité (WI-FI)
        MainButtonWIFI.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WIFI.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                overridePendingTransition(0, 0);
            }
        });


        //Changement de langue
        Locale localeEn = new Locale("en");
        Locale localeFr = new Locale("fr");
        MainButtonLang.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Resources resources = getResources();
                Configuration configuration = resources.getConfiguration();
                configuration.getLocales();
                String lang = "";

                if (configuration.getLocales().toString().contains("[en")){
                    configuration.setLocale(localeFr);
                    lang = "fr";
                } else if (configuration.getLocales().toString().contains("[fr")) {
                    configuration.setLocale(localeEn);
                    lang = "en";
                }

                getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());


                //Sauvegarde de la langue utilisée après le changement
                editor.putString("LangAct",lang);
                //Recreate permet de savoir si il y'a eu un changement de langue depuis le dernier démarrage de l'application
                editor.putString("Recreate","true");
                editor.commit();


                //Actualisation de l'activité
                recreate();
            }
        });


        //Application de la langue choisie au démarrage
        //Comptage du nombre d'itérations (démarrage de l'application depuis l'installation (utile pour la 1ere initialisation))
        int compteur;
        Boolean recreate;

        //Enregistrement de la langue actuelle
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.getLocales();
        String langAct = "";

        if (configuration.getLocales().toString().contains("[en")){
            langAct = "en";
        } else if (configuration.getLocales().toString().contains("[fr")) {
            langAct = "fr";
        }

        //Première itération
        if(preferences.getString("LangCompteur", "").equals("")){
            compteur = 1;
            recreate = false;

            //Sauvegarde language utilisé
            editor.putString("LangAct",langAct);
            editor.commit();
        }
        //Les autres itérations
        else {
            compteur = Integer.parseInt(preferences.getString("LangCompteur", "")) + 1;

            //Si le language a été modifiée via le boutton, on met recreate à true afin d'actualiser la page car elle se crée dans le language utilisé précédement
            if (preferences.getString("Recreate", "").equals("true")){
                editor.putString("Recreate","false");
                editor.commit();
                recreate = true;
            }
            else {
                recreate = false;
            }

            //Actualisation de la page si le language utilisé ne correspond pas à celui dans lequel l'application était à l'itération précédente
            if (!langAct.equals(preferences.getString("LangAct", ""))){
                recreate = true;
            }
        }


        //Sauvegarde compteur
        editor.putString("LangCompteur", String.valueOf(compteur));
        editor.commit();


        //Changement de langue
        configuration.getLocales();
        if (preferences.getString("LangAct", "").equals("fr")){
            configuration.setLocale(localeFr);
        } else if (preferences.getString("LangAct", "").equals("en")) {
            configuration.setLocale(localeEn);
        }


        //Actualisation de l'activité
        if (recreate) {
            recreate = false;
            recreate();
        }


    }


    //Permissions
    protected void onStart(){
        super.onStart();
        if (checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED | checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }
}