package ricardotenorio.reserva.de.livros.util;

import ricardotenorio.reserva.de.livros.entity.Cliente;

import java.security.MessageDigest;

public class HashString {
    public static String getHash(String string){
        try {
            MessageDigest md  = MessageDigest.getInstance("MD5");

            md.update(string.getBytes());
            byte data[] = md.digest();

            StringBuffer hex = new StringBuffer();

            for(int i = 0; i < data.length; i++){
                String auxHex = Integer.toHexString(0xff & data[i]);
                if(auxHex.length() == 1)
                    hex.append('0');
                hex.append(auxHex);
            }

            return hex.toString();
        } catch(Exception e){
            System.err.println(e);
            return null;
        }

    }
}
