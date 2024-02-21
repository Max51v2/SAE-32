package com.example.sae32_dupraz_rollin_vallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class  Informations extends AppCompatActivity {


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
        setContentView(R.layout.activity_informations);


        //_____________Variables/Objets_______________
        TextView InformationsMail1 = findViewById(R.id.InformationsMail1);
        TextView InformationsMail2 = findViewById(R.id.InformationsMail2);
        View InformationsBannerButtonHome = findViewById(R.id.InformationsBannerButtonHome);
        preferences2=getSharedPreferences("Save_Main",MODE_PRIVATE);
        editor=preferences2.edit();
        //____________________________________________


        //Changement d'activité (IP)
        InformationsBannerButtonHome.setOnClickListener(new View.OnClickListener() {
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


        //Liens adresses mail
        InformationsMail1.setMovementMethod(LinkMovementMethod.getInstance());
        InformationsMail2.setMovementMethod(LinkMovementMethod.getInstance());
    }

}