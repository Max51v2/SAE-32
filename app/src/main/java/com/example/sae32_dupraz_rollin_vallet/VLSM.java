package com.example.sae32_dupraz_rollin_vallet;

import androidx.appcompat.app.AppCompatActivity;

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

public class VLSM extends AppCompatActivity {


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
        setContentView(R.layout.activity_vlsm);


        //_____________Variables/Objets_______________
        View VLSMBannerButtonHome = findViewById(R.id.VLSMBannerButtonHome);
        View VLSMButtonIP = findViewById(R.id.VLSMButtonIP);
        TextView VLSMTextAnswer = findViewById(R.id.VLSMTextAnswer);
        final EditText VLSMBoxIP =  (EditText) findViewById(R.id.VLSMBoxIP);
        final EditText VLSMBoxMask =  (EditText) findViewById(R.id.VLSMBoxMask);
        final EditText VLSMBoxSize =  (EditText) findViewById(R.id.VLSMBoxSize);
        preferences=getSharedPreferences("Save_VLSM",MODE_PRIVATE);
        preferences2=getSharedPreferences("Save_Main",MODE_PRIVATE);
        //____________________________________________


        //Changement d'activité (Main)
        VLSMBannerButtonHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, 0);
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


        //Changement de langue si l'activité est reconstruite (changement d'orientation ou dimensions)
        Locale localeEn = new Locale("en");
        Locale localeFr = new Locale("fr");

        //Changement de la langue utilisée
        if (preferences2.getString("LangAct", "").equals("fr") && lang.equals("en")){
            configuration.setLocale(localeFr);
            recreate();
        } else if (preferences2.getString("LangAct", "").equals("en") && lang.equals("fr")) {
            configuration.setLocale(localeEn);
            recreate();
        }


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

                //Retrait des caratères interdits "a","b","c","d","e","f","g","h","i","j"
                ArrayList ListAddress = new ArrayList<>(Arrays.asList("/","-"," "));
                ArrayList ListMask = new ArrayList<>(Arrays.asList(":","-"," "));
                ArrayList ListSubnets = new ArrayList<>(Arrays.asList(":","-","."," "));

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
                //Champ SR
                for (int c=0; c < ListSubnets.size(); c++) {
                    while (subnetlist.contains(ListSubnets.get(c).toString())) {
                        subnetlist = subnetlist.substring(0, subnetlist.indexOf(ListSubnets.get(c).toString())) + subnetlist.substring(subnetlist.indexOf(ListSubnets.get(c).toString()) + 1, subnetlist.length());
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
                    editor=preferences.edit();
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