package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import aes.AES;

public class Cliente {

    public static void main(String[] args) {
        
        try {
        	String claveEncriptacion = "Secreto!";


        	AES encriptador = new AES();
        	
        	
            Scanner sn = new Scanner(System.in);
            sn.useDelimiter("\n");
            
            Socket sc = new Socket("127.0.0.1", 5000);
            
            int mod = 7;

			int base = 5;

            
            DataInputStream in = new DataInputStream(sc.getInputStream());
            DataOutputStream out = new DataOutputStream(sc.getOutputStream());
            
            System.out.println("Por favor ingresa tu clave");
            String clave = sn.nextLine();
            int claveC = Integer.parseInt(clave);
            
    
            
            double r = (Math.pow(base, claveC))%mod;
            String publica = String.valueOf(r);
            out.writeUTF(publica);
            String publicaP = in.readUTF();
            double publicPartner = Double.parseDouble(publicaP);
            
            int solucion = (int) ((Math.pow(publicPartner, claveC))%mod);
            
            String solucionStr = String.valueOf(solucion);
            
            out.writeUTF(solucionStr);
            String solucionPartner = in.readUTF();
            if (!solucionStr.equals(solucionPartner)) {
            	sc.close();
            }
            
            
            while(true) {
        	
        		String msg = in.readUTF();
        		String desencriptado = encriptador.desencriptar(msg, claveEncriptacion);
        		System.out.println("Mensaje encriptado: "+ msg);
        		System.out.println("Mensaje desencriptado: " + desencriptado);
            	
            	
            	
				
				
				String msg1 = sn.next();
				String encriptado = encriptador.encriptar(msg1, claveEncriptacion);
				out.writeUTF(encriptado);
				
				
			}
        } catch (Exception e) {
            //Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, e);
        }

        
        
    }
    
}
