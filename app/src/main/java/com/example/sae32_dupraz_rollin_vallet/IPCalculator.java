package com.example.sae32_dupraz_rollin_vallet;

public class IPCalculator {
    private String address;
    private String mask;
    private String addressBin;
    public IPCalculator(String address, String mask){
        this.address = address;
        this.mask = mask;
        this.mask = Getmask(mask);
    }

    //Remet les maskes /X et X.X.X.X sous la forme X
    public String Getmask(String mask){
        if(mask.contains("/")){
            mask = mask.substring(1);

            //check si la val du masque dépasse 32
            if(Integer.parseInt(mask) > 32){
                mask = "32";
            }
        }
        if(mask.contains(".")){
            mask = AddressToBaseTwo(mask);

            //Si le masque est un wildcard
            if(Integer.parseInt(mask.substring(0,1)) == 0){
                mask = String.valueOf(mask.indexOf("1"));
            }
	        else{
                mask = String.valueOf(mask.indexOf("0"));
            }
        }
        else{
            //check si la val du masque dépasse 32
            if(Integer.parseInt(mask) > 32){
                mask = "32";
            }
        }

        //check si la val du masque dépasse 32
        if(Integer.valueOf(mask) > 32){
            mask = "32";
        }

        return mask;
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

    public String NetworkAddress(){
        addressBin = AddressToBaseTwo(this.address);
        addressBin = addressBin.substring(0,(Integer.valueOf(this.mask)));
        for(int i=Integer.valueOf(this.mask);i<32;i++){
            addressBin += "0";
        }

        return toBaseTen(addressBin);
    }

    public String BroadcastAddress(){
        addressBin = AddressToBaseTwo(this.address);

        addressBin = addressBin.substring(0,(Integer.valueOf(this.mask)));
        for(int i=Integer.valueOf(this.mask);i<32;i++){
            addressBin += "1";
        }

        return toBaseTen(addressBin);
    }

    public String FirstAddress(){
        addressBin = AddressToBaseTwo(this.address);

        addressBin = addressBin.substring(0,(Integer.valueOf(this.mask)));
        for(int i=Integer.valueOf(this.mask);i<31;i++){
            addressBin += "0";
        }

        addressBin += "1";

        return toBaseTen(addressBin);
    }

    public String LastAddress(){
        addressBin = AddressToBaseTwo(this.address);

        addressBin = addressBin.substring(0,(Integer.valueOf(this.mask)));
        for(int i=Integer.valueOf(this.mask);i<31;i++){
            addressBin += "1";
        }

        addressBin += "0";

        return toBaseTen(addressBin);
    }

    public String NumberOfAddress(){
        return String.valueOf((int) (Math.pow(2,(32-Integer.valueOf(this.mask)))-2));
    }

    public String NumberOfAddressFromDesignatedMask(Integer mask){
        return String.valueOf((int) (Math.pow(2,(32-mask))-2));
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
    //_____________________VLSM_______________________
    public String NetworkAddressAlt(String address, String mask){
        addressBin = AddressToBaseTwo(address);
        addressBin = addressBin.substring(0,(Integer.valueOf(mask)));
        for(int i=Integer.valueOf(mask);i<32;i++){
            addressBin += "0";
        }

        return toBaseTen(addressBin);
    }

    public String BroadcastAddressAlt(String address, String mask){
        addressBin = AddressToBaseTwo(address);

        addressBin = addressBin.substring(0,(Integer.valueOf(mask)));
        for(int i=Integer.valueOf(mask);i<32;i++){
            addressBin += "1";
        }

        return toBaseTen(addressBin);
    }

    public String FirstAddressAlt(String address, String mask){
        addressBin = AddressToBaseTwo(address);

        addressBin = addressBin.substring(0,(Integer.valueOf(mask)));
        for(int i=Integer.valueOf(mask);i<31;i++){
            addressBin += "0";
        }

        addressBin += "1";

        return toBaseTen(addressBin);
    }

    public String LastAddressAlt(String address, String mask){
        addressBin = AddressToBaseTwo(address);

        addressBin = addressBin.substring(0,(Integer.valueOf(mask)));
        for(int i=Integer.valueOf(mask);i<31;i++){
            addressBin += "1";
        }

        addressBin += "0";

        return toBaseTen(addressBin);
    }

    public String GetMaskValue(){
        return this.mask;
    }
}