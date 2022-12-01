package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

import aes.AES;

public class Cliente {

    public static void main(String[] args) {
        
        try {
        	String claveEncriptacion = "dAtAbAsE";


        	AES encriptador = new AES();
        	
        	
            Scanner sn = new Scanner(System.in);
            sn.useDelimiter("\n");
            
            Socket sc = new Socket("127.0.0.1", 5000);
            
            

            
            DataInputStream in = new DataInputStream(sc.getInputStream());
            DataOutputStream out = new DataOutputStream(sc.getOutputStream());
            int mod = 7;
            int base = 5;
//            System.out.println("Por favor ingresa tu clave");
//            String clave = sn.nextLine();
//            int claveC = Integer.parseInt(clave);
           
            int claveC = ThreadLocalRandom.current().nextInt(1, 10000 + 1);
            System.out.println("Tu clave privada ha sido generada: " + claveC);
            Thread.sleep(750);
            System.out.println("Se esta Generando tu clave publica");
            double r = (Math.pow(base, claveC))%mod;
            Thread.sleep(750);
            System.out.println("Tu clave publica es: " + r + ", la cual sera compartida hasta el otro usuario");
            String publica = String.valueOf(r);
            out.writeUTF(publica);
            String publicaP = in.readUTF();
            double publicPartner = Double.parseDouble(publicaP);
            
            int solucion = (int) ((Math.pow(publicPartner, claveC))%mod);
            
            String solucionStr = String.valueOf(solucion);
            Thread.sleep(750);
            System.out.println("Ahora, mediante el algoritmo de Deffie-Hellman, se esta comprobando si la otra persona es quien tu crees");
            out.writeUTF(solucionStr);
            String solucionPartner = in.readUTF();
			Thread.sleep(125);
			System.out.print(".");
			Thread.sleep(125);
			System.out.print(".");
			Thread.sleep(125);
			System.out.print(".");
			Thread.sleep(125);
			System.out.print(".");
			Thread.sleep(125);
			System.out.print(".");
			Thread.sleep(125);
			System.out.print(".");
			Thread.sleep(125);
			System.out.print(".");
			Thread.sleep(125);
			System.out.print(".");
			Thread.sleep(125);
			System.out.print(".");
			Thread.sleep(125);
			System.out.print(".");
			Thread.sleep(125);
			System.out.print(".");
			Thread.sleep(125);
			System.out.print(".");
			Thread.sleep(125);
			System.out.print(".");
			Thread.sleep(125);
			System.out.print(".");
			Thread.sleep(125);
			System.out.print(".");
			Thread.sleep(125);
			System.out.print(".");
			System.out.println();
            if (!solucionStr.equals(solucionPartner)) {
            	System.out.println("oh no, el otro usuario no era quien tu creias! por tu seguridad se cerrara la sesión");
            	sc.close();
            }else {
            	 System.out.println("Perfecto!! se comprobo que cada quien es quien dice ser gracias a las claves simetricas, pueden continuar con su conversación!!");
                 System.out.println("Recuerda que todo mensaje enviado y recibido se cifrara mediante aes de 128bits");
     			System.out.println("Ademas, se te mostrara el mensaje cifrado justo arriba del mensaje decifrado localmente");
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
