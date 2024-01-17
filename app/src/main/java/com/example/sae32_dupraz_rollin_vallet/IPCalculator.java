package com.example.sae32_dupraz_rollin_vallet;

import android.util.Log;

public class IPCalculator {
    private String address;
    private String prefix;
    public IPCalculator(String address, String prefix){
        this.address = address;
        this.prefix = prefix;

        String addressBin = AddressToBaseTwo(address);
    }

    //Remet les prefixes /X et X.X.X.X sous la forme X
    private String GetPrefix(String prefix){
        if(prefix.contains("/")){
            prefix = prefix.substring(1,prefix.length());
        }
        if(prefix.contains(".")){
            prefix = AddressToBaseTwo(prefix);
            prefix = String.valueOf(prefix.indexOf("0"));
        }
        return prefix;
    }

    //Transforme une adresse en notation décimale pointée en 32 bits
    private String AddressToBaseTwo(String address){

        int byte1;
        int byte2;
        int byte3;
        int byte4;

        //On sépare les 4 octets
        int beginIndex = 0;
        int endIndex =address.indexOf(".");

        byte1 = Integer.valueOf(address.substring(beginIndex,endIndex));

        address = address.substring(endIndex+1,address.length());
        endIndex =address.indexOf(".");
        byte2 = Integer.valueOf(address.substring(beginIndex,endIndex));

        address = address.substring(endIndex+1,address.length());
        endIndex =address.indexOf(".");
        byte3 = Integer.valueOf(address.substring(beginIndex,endIndex));

        address = address.substring(endIndex+1,address.length());
        endIndex =address.length();
        byte4 = Integer.valueOf(address.substring(beginIndex,endIndex));

        String addressBin = toBaseTwo(byte1);
        addressBin = addressBin+toBaseTwo(byte2);
        addressBin = addressBin+toBaseTwo(byte3);
        addressBin = addressBin+toBaseTwo(byte4);
        return addressBin;
    }

    //Convertis un octet en base 2
    private String toBaseTwo(int Byte){
        String result = "";
        for(int i = 7; i >= 0; i--) {
            if(Byte >= Math.pow(2,i)){
                Byte -= Math.pow(2,i);
                result = result+"1";
            }
            else{
                result = result+"0";
            }
        }
        return result;
    }

    public String NetworkAddress(String addressBin, String prefix){
        prefix = GetPrefix(prefix);

        Log.d("cpt", prefix);
        Log.d("cpt", addressBin);
        addressBin = addressBin.substring(0,(Integer.valueOf(prefix)));
        for(int i=Integer.valueOf(prefix);i<32;i++){
            addressBin += "0";
        }

        return toBaseTen(addressBin);
    }

    public String BroadcastAddress(String addressBin, String prefix){
        prefix = GetPrefix(prefix);

        addressBin = addressBin.substring(0,(Integer.valueOf(prefix)));
        for(int i=Integer.valueOf(prefix);i<32;i++){
            addressBin += "1";
        }

        return toBaseTen(addressBin);
    }

    public String FirstAddress(String addressBin, String prefix){
        prefix = GetPrefix(prefix);

        addressBin = addressBin.substring(0,(Integer.valueOf(prefix)));
        for(int i=Integer.valueOf(prefix);i<31;i++){
            addressBin += "0";
        }

        addressBin += "1";

        return toBaseTen(addressBin);
    }

    public String LastAddress(String addressBin, String prefix){
        prefix = GetPrefix(prefix);

        addressBin = addressBin.substring(0,(Integer.valueOf(prefix)));
        for(int i=Integer.valueOf(prefix);i<31;i++){
            addressBin += "1";
        }

        addressBin += "0";

        return toBaseTen(addressBin);
    }

    public String NumberOfAddress(String prefix){
        prefix = GetPrefix(prefix);
        return String.valueOf((int) (Math.pow(2,(32-Integer.valueOf(prefix)))-2));
    }

    //Convertis 32 bits en base 10 avec une notation décimale pointée
    private String toBaseTen(String addressBin){

        String byte1=addressBin.substring(0,8);
        String byte2=addressBin.substring(8,16);
        String byte3=addressBin.substring(16,24);
        String byte4=addressBin.substring(24,32);

        byte1 = functionToBaseTen(byte1);
        byte2 = functionToBaseTen(byte2);
        byte3 = functionToBaseTen(byte3);
        byte4 = functionToBaseTen(byte4);

        return byte1+"."+byte2+"."+byte3+"."+byte4;
    }

    //Convertis un octect en base 10
    private String functionToBaseTen(String Byte){
        int result = 0;
        for (int i = 7; i >= 0; i--){
            if(Byte.substring(7 - i, 8 - i).equals("1")){
                result += Math.pow(2,i);
            }
        }
        return String.valueOf(result);
    }
}