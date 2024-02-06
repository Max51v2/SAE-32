package com.example.sae32_dupraz_rollin_vallet;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class VLSM extends AppCompatActivity {


    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vlsm);


        //_____________Variables/Objets_______________
        View VLSMBannerButtonHome = findViewById(R.id.VLSMBannerButtonHome);
        View VLSMButtonIP = findViewById(R.id.VLSMButtonIP);
        TextView VLSMTextAnswer = findViewById(R.id.VLSMTextAnswer);
        final EditText VLSMBoxIP =  (EditText) findViewById(R.id.VLSMBoxIP);
        final EditText VLSMBoxMask =  (EditText) findViewById(R.id.VLSMBoxMask);
        final EditText VLSMBoxSize =  (EditText) findViewById(R.id.VLSMBoxSize);
        preferences=getSharedPreferences("Save_VLSM",MODE_PRIVATE);
        editor=preferences.edit();
        //____________________________________________


        //Changement d'activité (Main)
        VLSMBannerButtonHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(VLSM.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
            }
        });


        //On récupère la langue utilisée
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.getLocales();
        String lang = "";
        if (configuration.getLocales().toString().contains("[en")) {
            lang = "en";
        }
        if (configuration.getLocales().toString().contains("[fr")) {
            lang = "fr";
        }
        String finalLang = lang;


        //Calcul VLSM
        VLSMButtonIP.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Récupération des champs : IP, masque et Sous réseaux
                String address = String.valueOf(VLSMBoxIP.getText());
                String mask = String.valueOf(VLSMBoxMask.getText());
                String subnetlist = String.valueOf(VLSMBoxSize.getText());
                VLSMBoxIP.setText("");
                VLSMBoxMask.setText("");
                VLSMBoxSize.setText("");
                VLSMBoxIP.requestFocus();
                VLSMBoxMask.requestFocus();
                VLSMBoxSize.requestFocus();

                //Retrait des espaces
                while(address.contains(" ") | mask.contains(" ") | mask.contains(" ")) {
                    if (address.contains(" ")) {
                        address = address.substring(0, address.indexOf(" ")) + address.substring(address.indexOf(" ") + 1, address.length());
                    }
                    if (mask.contains(" ")) {
                        mask = mask.substring(0, mask.indexOf(" ")) + mask.substring(mask.indexOf(" ") + 1, mask.length());
                    }
                    if (mask.contains(" ")) {
                        subnetlist = subnetlist.substring(0, subnetlist.indexOf(" ")) + subnetlist.substring(subnetlist.indexOf(" ") + 1, subnetlist.length());
                    }
                }

                //On ne lance rien si un champ est vide
                if (address.equals("") | mask.equals("") | subnetlist.equals("")){

                }
                //Lancement du calcul des adresses VLSM
                else {
                    //On execute la méthode pour les 2 langues au cas ou on change la langue entre 2 executions
                    VLSMCalculator VLSMEn = new VLSMCalculator(address, mask, subnetlist, "en");
                    VLSMCalculator VLSMFr = new VLSMCalculator(address, mask, subnetlist, "fr");

                    //On affiche la langue utilisée
                    if (finalLang.equals("en")){
                        VLSMTextAnswer.setText(VLSMEn.getResult());
                    }
                    if (finalLang.equals("fr")) {
                        VLSMTextAnswer.setText(VLSMFr.getResult());
                    }

                    //Sauvegarde du champ VLSMTextAnswer en englais et français
                    editor.putString("VLSMTextAnswerFr",VLSMFr.getResult());
                    editor.putString("VLSMTextAnswerEn",VLSMEn.getResult());
                    editor.commit();
                }
            }
        });

        //Restauration Sauvegarde
        //On affiche la langue utilisée
        if (finalLang.equals("en")){
            VLSMTextAnswer.setText(preferences.getString("VLSMTextAnswerEn", ""));
        }
        if (finalLang.equals("fr")) {
            VLSMTextAnswer.setText(preferences.getString("VLSMTextAnswerFr", ""));
        }
    }
}