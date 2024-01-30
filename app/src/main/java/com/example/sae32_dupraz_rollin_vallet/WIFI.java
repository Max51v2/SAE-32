package com.example.sae32_dupraz_rollin_vallet;

import androidx.appcompat.app.AppCompatActivity;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
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
        View WIFITextInterface3 = findViewById(R.id.WIFITextInterface3);
        View WIFIScrollView = findViewById(R.id.WIFIScrollView);
        View WIFIButtonInterfaceClose = findViewById(R.id.WIFIButtonInterfaceClose);
        TextView WIFITextSSID = findViewById(R.id.WIFITextSSID);
        View WIFIWarningBackground = findViewById(R.id.WIFIWarningBackground);
        View WIFIWarningLogo = findViewById(R.id.WIFIWarningLogo);
        TextView WIFIWarningText = findViewById(R.id.WIFIWarningText);
        TextView WIFITextStatus = findViewById(R.id.WIFITextStatus);
        //____________________________________________


        //Changement d'activité (Main)
        WIFIBannerButtonHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(WIFI.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
            }
        });


        //###################### Thread 1 ######################
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
                    ArrayList<String> interfaceMAC = new ArrayList<>();
                    ArrayList<Integer> size = new ArrayList<>();
                    String address;

                    while (interfaces.hasMoreElements()) {
                        NetworkInterface networkInterface = interfaces.nextElement();

                        //nom interfaces
                        interfaceName.add(networkInterface.getName().toString());

                        //adresses interfaces
                        address = networkInterface.getInterfaceAddresses().get(0).toString();
                        if (address.contains("%")) {
                            address = address.substring(address.indexOf("/") + 1, address.indexOf("%"));
                        } else {
                            address = address.substring(address.indexOf("/") + 1);
                            address = address.substring(0, address.indexOf("/"));
                        }
                        interfaceAddress.add(address);

                        //masque
                        address = networkInterface.getInterfaceAddresses().get(0).toString();
                        address = address.substring(address.indexOf("/") + 1);
                        address = address.substring(address.indexOf("/") + 1, address.indexOf("[") - 1);
                        interfacemask.add(address);

                        //@MAC (Nécéssite d'être ROOT)
                        //address = Arrays.toString(networkInterface.getHardwareAddress());
                        //Log.d("test", Arrays.toString(networkInterface.getHardwareAddress()));
                        //interfaceMAC.add(address);

                        //Seconde adresse
                        if (networkInterface.getInterfaceAddresses().size() > 1) {
                            //adresses interfaces
                            address = networkInterface.getInterfaceAddresses().get(1).toString();
                            if (address.contains("%")) {
                                address = address.substring(address.indexOf("/") + 1, address.indexOf("%"));
                            } else {
                                address = address.substring(address.indexOf("/") + 1);
                                address = address.substring(0, address.indexOf("/"));
                            }
                            interfaceAddress.add(address);

                            //masque
                            address = networkInterface.getInterfaceAddresses().get(1).toString();
                            address = address.substring(address.indexOf("/") + 1);
                            address = address.substring(address.indexOf("/") + 1, address.indexOf("[") - 1);
                            interfacemask.add(address);
                            interfaceName.add("placeholder");

                            //@MAC
                            //address = Arrays.toString(networkInterface.getHardwareAddress());
                            //interfaceMAC.add(address);

                            size.add(2);
                        } else {
                            size.add(1);
                        }
                    }


                    //Impression des resultats
                    int iMax = 0;
                    for (int i = 0; i < size.size(); i++) {
                        iMax += size.get(i);
                    }


                    for (int i = 0; i < iMax; i++) {
                        if (interfaceName.get(i).contains("placeholder")) {
                            resultFr[0] += "Adresse n°2 : " + interfaceAddress.get(i).toString() + "/" + interfacemask.get(i).toString() + " \n \n";
                        } else {
                            resultFr[0] += "Interface : " + interfaceName.get(i).toString() + " \n";
                            //resultFr[0] += "Adresse MAC : " + interfaceMAC.get(i).toString() + " \n";
                            if (interfaceName.get(i + 1).contains("placeholder") && i < iMax - 1) {
                                resultFr[0] += "Adresse n°1 : " + interfaceAddress.get(i).toString() + "/" + interfacemask.get(i).toString() + " \n";
                            } else {
                                resultFr[0] += "Adresse : " + interfaceAddress.get(i).toString() + "/" + interfacemask.get(i).toString() + " \n \n";
                            }
                        }

                        if (interfaceName.get(i).contains("placeholder")) {
                            resultEn[0] += "Address n°2 : " + interfaceAddress.get(i).toString() + "/" + interfacemask.get(i).toString() + " \n \n";
                        } else {
                            resultEn[0] += "Interface : " + interfaceName.get(i).toString() + " \n";
                            //resultEn[0] += "MAC Address : " + interfaceMAC.get(i).toString() + " \n";
                            if (interfaceName.get(i + 1).contains("placeholder") && i < iMax - 1) {
                                resultEn[0] += "Address n°1 : " + interfaceAddress.get(i).toString() + "/" + interfacemask.get(i).toString() + " \n";
                            } else {
                                resultEn[0] += "Address : " + interfaceAddress.get(i).toString() + "/" + interfacemask.get(i).toString() + " \n \n";
                            }
                        }
                    }
                } catch (SocketException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        //###################### Fin Thread 1 ######################


        LocalHost.start();


        //###################### Thread 2 ######################
        Thread LocalHost2 = new Thread() {
            public void run() {
                //Langue
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

                while (true) {
                    //Informations WIFI
                    WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                    WifiInfo infos = wifi.getConnectionInfo();


                    //SSID
                    String SSID = infos.getSSID();
                    SSID = SSID.substring(1, SSID.length() - 1);


                    //String state = wifi.getWifiState();
                    Log.d("testTAG", String.valueOf(wifi.getWifiState()));
                    String str = "";
                    if (wifi.getWifiState() == 1) {
                        if (lang.equals("fr")) {
                            str = "Status : Desactivé";
                        }
                        if (lang.equals("en")) {
                            str = "Status : Disabled";
                        }
                    }
                    if (wifi.getWifiState() == 3) {
                        if (lang.equals("fr")) {
                            str = "Status : Activé";
                        }
                        if (lang.equals("en")) {
                            str = "Status : Enabled";
                        }
                    }


                    //Mise à jour des éléments dans le Thread principal sinon ça plante
                    String finalStr = str;
                    String finalSSID = SSID;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            WIFITextSSID.setText("SSID : " + finalSSID);
                            WIFITextStatus.setText(finalStr);
                        }
                    });


                    //Actualisation toutes les secondes
                    synchronized (this) {
                        try {
                            this.wait(200);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }

        };
        //###################### Fin Thread 2 ######################


        LocalHost2.start();


        //Langue
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


        //Affichage des informations sur les interfaces lors du click sur le bouton
        //Ouverture
        WIFIButtonInterface.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                WIFITextInterface.setVisibility(View.INVISIBLE);
                WIFIButtonInterface.setVisibility(View.INVISIBLE);
                WIFITextInterface2.setVisibility(View.VISIBLE);
                WIFIButtonInterfaceClose.setVisibility(View.VISIBLE);
                WIFITextInterface3.setVisibility(View.VISIBLE);
                WIFIScrollView.setVisibility(View.VISIBLE);

                //Choix langue
                Resources resources = getResources();
                Configuration configuration = resources.getConfiguration();
                configuration.getLocales();
                String lang = "";
                if (configuration.getLocales().toString().contains("[en")) {
                    WIFITextInterface2.setText(resultEn[0]);
                }
                if (configuration.getLocales().toString().contains("[fr")) {
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
                WIFITextInterface3.setVisibility(View.INVISIBLE);
                WIFIScrollView.setVisibility(View.INVISIBLE);

                WIFITextInterface2.setText("");
            }
        });


        //Warnings
        //Création du contenu de l'avertissement
        Context context = this;
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        String str = "";
        if (isGpsEnabled == false) {
            if (lang.equals("fr")) {
                str = "Veuillez activer la localisation sur l'appareil \n\n";
            }
            if (lang.equals("en")) {
                str = "Please enable localisation on the device \n\n";
            }
        }
        if (checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED | checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            if (lang.equals("fr")) {
                str += "Veuillez redémarrer l'application et activer la localisarion précise";
            }
            if (lang.equals("en")) {
                str += "Please restart the app and enable fine localisation";
            }
        }
        WIFIWarningText.setText(str);


        //Affichage ou non de l'avertissement
        if (checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED | checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED | isGpsEnabled == false) {
            WIFIWarningBackground.setVisibility(View.VISIBLE);
            WIFIButtonInterface.setVisibility(View.INVISIBLE);
            WIFITextInterface.setVisibility(View.INVISIBLE);
            WIFITextSSID.setVisibility(View.INVISIBLE);
            WIFIWarningLogo.setVisibility(View.VISIBLE);
            WIFIWarningText.setVisibility(View.VISIBLE);
            WIFITextStatus.setVisibility(View.INVISIBLE);
        } else {
            WIFIWarningBackground.setVisibility(View.INVISIBLE);
            WIFIButtonInterface.setVisibility(View.VISIBLE);
            WIFITextInterface.setVisibility(View.VISIBLE);
            WIFITextSSID.setVisibility(View.VISIBLE);
            WIFIWarningLogo.setVisibility(View.INVISIBLE);
            WIFIWarningText.setVisibility(View.INVISIBLE);
            WIFITextStatus.setVisibility(View.VISIBLE);
        }


    }
    }