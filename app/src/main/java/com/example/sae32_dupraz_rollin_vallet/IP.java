package com.example.sae32_dupraz_rollin_vallet;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class IP extends AppCompatActivity {


    SharedPreferences preferences;
    SharedPreferences preferences2;
    SharedPreferences.Editor editor;



    //Retour en arrière
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(0, 0);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip);


        //_____________Variables/Objets_______________
        View MainBannerButtonHome = findViewById(R.id.MainBannerButtonHome);
        View IPButtonIP = findViewById(R.id.IPButtonIP);
        TextView IPNetworkAddressBox = findViewById(R.id.IPNetworkAddressBox);
        TextView IPBroadcastAddressBox = findViewById(R.id.IPBroadcastAddressBox);
        TextView IPRangeBox = findViewById(R.id.IPRangeBox);
        TextView IPAvailableAddressBox = findViewById(R.id.IPAvailableAddressBox);
        final EditText IPBoxIP =  (EditText) findViewById(R.id.IPBoxIP);
        final EditText IPBoxMask =  (EditText) findViewById(R.id.IPBoxMask);
        preferences=getSharedPreferences("Save_IP",MODE_PRIVATE);
        preferences2=getSharedPreferences("Save_Main",MODE_PRIVATE);
        //____________________________________________


        //Changement d'activité (Main)
        MainBannerButtonHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, 0);
            }
        });


        //Changement de langue si l'activité est reconstruite (changement d'orientation ou dimensions)
        //Enregistrement de la langue actuelle
        Locale localeEn = new Locale("en");
        Locale localeFr = new Locale("fr");
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.getLocales();
        String langAct = "";

        if (configuration.getLocales().toString().contains("[en")){
            langAct = "en";
        } else if (configuration.getLocales().toString().contains("[fr")) {
            langAct = "fr";
        }

        //Changement de la langue utilisée
        if (preferences2.getString("LangAct", "").equals("fr") && langAct.equals("en")){
            configuration.setLocale(localeFr);
            recreate();
        } else if (preferences2.getString("LangAct", "").equals("en") && langAct.equals("fr")) {
            configuration.setLocale(localeEn);
            recreate();
        }


        //Calculs IP
        IPButtonIP.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            public void onClick(View v) {
                //Récupération du champ IP
                String address = String.valueOf(IPBoxIP.getText());
                String mask = String.valueOf(IPBoxMask.getText());

                //Retrait des caratères interdits "a","b","c","d","e","f","g","h","i","j"
                ArrayList ListAddress = new ArrayList<>(Arrays.asList("/","-"," "));
                ArrayList ListMask = new ArrayList<>(Arrays.asList(":","-"," "));

                //Champ IP
                for (int c=0; c < ListAddress.size(); c++) {
                    while (address.contains(ListAddress.get(c).toString())) {
                        address = address.substring(0, address.indexOf(ListAddress.get(c).toString())) + address.substring(address.indexOf(ListAddress.get(c).toString()) + 1, address.length());
                    }
                }
                //Champ masque
                for (int c=0; c < ListMask.size(); c++) {
                    while (mask.contains(ListMask.get(c).toString())) {
                        mask = mask.substring(0, mask.indexOf(ListMask.get(c).toString())) + mask.substring(mask.indexOf(ListMask.get(c).toString()) + 1, mask.length());
                    }
                }

                //On ne lance rien si un champ est vide
                if (address.equals("") | mask.equals("")){

                }
                else {
                    IPBoxIP.setText("");
                    IPBoxMask.setText("");
                    IPBoxIP.requestFocus();
                    IPBoxMask.requestFocus();

                    //On ne lance rien si un champ est vide
                    if (address.equals("") | mask.equals("")) {

                    }
                    //Lancement du calcul IP
                    else {
                        //Envoi des valeurs dans le layout
                        IPCalculator ip1;
                        try {
                            ip1 = new IPCalculator(address, mask);
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                        IPNetworkAddressBox.setText(ip1.NetworkAddress());
                        IPBroadcastAddressBox.setText(ip1.BroadcastAddress());
                        IPRangeBox.setText(ip1.FirstAddress() + " - " + ip1.LastAddress());
                        IPAvailableAddressBox.setText(ip1.NumberOfAddress());

                        //Sauvegarde des champs : Adresse réseau, Adresse broadcast, Plage et Adresses disponibles
                        editor=preferences.edit();
                        editor.putString("IPNetworkAddressBox",IPNetworkAddressBox.getText().toString());
                        editor.putString("IPBroadcastAddressBox",IPBroadcastAddressBox.getText().toString());
                        editor.putString("IPRangeBox",IPRangeBox.getText().toString());
                        editor.putString("IPAvailableAddressBox",IPAvailableAddressBox.getText().toString());
                        editor.commit();
                    }
                }
            }
        });


        //Restauration Sauvegarde
        IPNetworkAddressBox.setText(preferences.getString("IPNetworkAddressBox", ""));
        IPBroadcastAddressBox.setText(preferences.getString("IPBroadcastAddressBox", ""));
        IPRangeBox.setText(preferences.getString("IPRangeBox", ""));
        IPAvailableAddressBox.setText(preferences.getString("IPAvailableAddressBox", ""));
    }
}