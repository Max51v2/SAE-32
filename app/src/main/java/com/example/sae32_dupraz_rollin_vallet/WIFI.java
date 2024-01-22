package com.example.sae32_dupraz_rollin_vallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;

public class WIFI extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);


        //_____________Variables/Objets_______________
        View WIFIBannerButtonHome = findViewById(R.id.WIFIBannerButtonHome);
        View WIFITextInterface = findViewById(R.id.WIFITextInterface);
        View WIFIButtonInterface = findViewById(R.id.WIFIButtonInterface);
        TextView WIFITextInterface2 = findViewById(R.id.WIFITextInterface2);
        View WIFIButtonInterfaceClose = findViewById(R.id.WIFIButtonInterfaceClose);
        //____________________________________________


        //Changement d'activité (Main)
        WIFIBannerButtonHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(WIFI.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
            }
        });


        //Infos
        final String[] resultFr = {""};
        final String[] resultEn = {""};
        Thread LocalHost = new Thread() {
            public void run() {
                try {
                    Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                    ArrayList<String> interfaceName = new ArrayList<>();
                    ArrayList<String> interfaceAddress = new ArrayList<>();
                    ArrayList<String> interfacemask = new ArrayList<>();
                    ArrayList<Integer> size = new ArrayList<>();
                    String address;

                    while (interfaces.hasMoreElements()) {
                        NetworkInterface networkInterface = interfaces.nextElement();

                        //nom interfaces
                        interfaceName.add(networkInterface.getName().toString());

                        //adresses interfaces
                        address = networkInterface.getInterfaceAddresses().get(0).toString();
                        if(address.contains("%")){
                            address = address.substring(address.indexOf("/")+1,address.indexOf("%"));
                        }
                        else{
                            address = address.substring(address.indexOf("/")+1);
                            address = address.substring(0,address.indexOf("/"));
                        }
                        interfaceAddress.add(address);

                        //masque
                        address = networkInterface.getInterfaceAddresses().get(0).toString();
                        address = address.substring(address.indexOf("/")+1);
                        address = address.substring(address.indexOf("/")+1,address.indexOf("[")-1);
                        interfacemask.add(address);

                        if (networkInterface.getInterfaceAddresses().size() > 1){
                            //adresses interfaces
                            address = networkInterface.getInterfaceAddresses().get(1).toString();
                            if(address.contains("%")){
                                address = address.substring(address.indexOf("/")+1,address.indexOf("%"));
                            }
                            else{
                                address = address.substring(address.indexOf("/")+1);
                                address = address.substring(0,address.indexOf("/"));
                            }
                            interfaceAddress.add(address);

                            //masque
                            address = networkInterface.getInterfaceAddresses().get(1).toString();
                            address = address.substring(address.indexOf("/")+1);
                            address = address.substring(address.indexOf("/")+1,address.indexOf("[")-1);
                            interfacemask.add(address);

                            size.add(2);
                        }
                        size.add(1);
                    }

                    //Impression des resultats
                    int compteur;
                    int i = 0;
                    int iback;
                    int iMax = 0;

                    for (int a=0; a < size.size(); a++){
                        iMax += size.get(a);
                    }
                    Log.d("test", "#######################");
                   while(i < iMax) {
                       iback = i;
                       compteur = 0;
                        resultFr[0] += "Interface : " + interfaceName.get(i).toString() + " \n";
                        if (size.get(i) == 2) {
                            while (compteur < 2) {
                                resultFr[0] += "Adresse : " + interfaceAddress.get(i).toString() + "/" + interfacemask.get(i).toString() + " \n \n";
                                compteur++;
                                i++;
                            }
                        }
                        else {
                            resultFr[0] += "Adresse : " + interfaceAddress.get(i).toString() + "/" + interfacemask.get(i).toString() + " \n \n";
                            i++;
                        }

                        i = iback;
                        compteur=0;
                        resultEn[0] += "Interface : " + interfaceName.get(i).toString() + " \n";
                        if (size.get(i) == 2) {
                            while (compteur < 2) {
                                resultEn[0] += "Address : " + interfaceAddress.get(i).toString() + "/" + interfacemask.get(i).toString() + " \n \n";
                                compteur++;
                                i++;
                            }
                        }
                        else {
                            resultEn[0] += "Address : " + interfaceAddress.get(i).toString() + "/" + interfacemask.get(i).toString() + " \n \n";
                            i++;
                        }
                       Log.d("test", Arrays.toString(resultEn));
                       Log.d("test", String.valueOf(i));
                    }
                } catch (SocketException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        LocalHost.start();

        //Affichage des informations sur les interfaces lors du click sur le bouton
        //Ouverture
        WIFIButtonInterface.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    WIFITextInterface.setVisibility(View.INVISIBLE);
                    WIFIButtonInterface.setVisibility(View.INVISIBLE);
                    WIFITextInterface2.setVisibility(View.VISIBLE);
                    WIFIButtonInterfaceClose.setVisibility(View.VISIBLE);

                    //Choix langue
                    Resources resources = getResources();
                    Configuration configuration = resources.getConfiguration();
                    configuration.getLocales();
                    String lang = "";
                    if (configuration.getLocales().toString().contains("en")) {
                        WIFITextInterface2.setText(resultEn[0]);
                    }
                    if (configuration.getLocales().toString().contains("fr")) {
                        WIFITextInterface2.setText(resultFr[0]);
                    }
            }
        });

        //Fermeture
        WIFIButtonInterfaceClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    WIFITextInterface.setVisibility(View.VISIBLE);
                    WIFIButtonInterface.setVisibility(View.VISIBLE);
                    WIFITextInterface2.setVisibility(View.INVISIBLE);
                    WIFIButtonInterfaceClose.setVisibility(View.INVISIBLE);

                    WIFITextInterface2.setText("");
            }
        });
    }
}