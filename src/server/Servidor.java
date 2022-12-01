package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import aes.AES;

public class Servidor {

	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InterruptedException {

		try {
			ServerSocket server = new ServerSocket(5000);
			Scanner sn = new Scanner(System.in);
            sn.useDelimiter("\n");
			Socket sc;
			
			String claveEncriptacion = "dAtAbAsE";
			AES encriptador = new AES();

			System.out.println("Servidor iniciado");
			
			while (true) {

				// Espero la conexion del cliente
				sc = server.accept();

				DataInputStream in = new DataInputStream(sc.getInputStream());
				DataOutputStream out = new DataOutputStream(sc.getOutputStream());
				int mod = 7;
	            int base = 5;
				
//				System.out.println("Por favor ingresa tu clave");
//				String claveS = sn.nextLine();
//				int clave = Integer.parseInt(claveS);


				int clave = ThreadLocalRandom.current().nextInt(1, 10 + 1);

	            System.out.println("Tu clave privada ha sido generada: " + clave);
	            
	            Thread.sleep(740);
	            System.out.println("Se esta Generando tu clave publica");
	            
				double r = (Math.pow(base, clave))%mod;
				Thread.sleep(740);
	            System.out.println("Tu clave publica es: " + r + ", la cual sera compartida hasta el otro usuario");
				String publica = String.valueOf(r);
				out.writeUTF(publica);
				String publicaP = in.readUTF();
				double publicPartner = Double.parseDouble(publicaP);
				
				int solucion = (int) ((Math.pow(publicPartner, clave))%mod);
				
				String solucionStr = String.valueOf(solucion);
				Thread.sleep(740);
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
		            	
		            } else {
		            	System.out.println("Perfecto!! se comprobo que cada quien es quien dice ser gracias a las claves simetricas, pueden continuar con su conversación!!");
						
						System.out.println("Recuerda que todo mensaje enviado y recibido se cifrara mediante aes de 128bits");
						System.out.println("Ademas, se te mostrara el mensaje cifrado justo arriba del mensaje decifrado localmente");
						
		            }
				
				 
				while(true) {
					String msg = sn.next();
					String encriptado = encriptador.encriptar(msg, claveEncriptacion);
					out.writeUTF(encriptado);
					
					String msg1 = in.readUTF();
					String desencriptado = encriptador.desencriptar(msg1, claveEncriptacion);
					System.out.println("Mensaje encriptado: "+ msg1);
            		System.out.println("Mensaje desencriptado: " + desencriptado);
					
					
				}
				
				
			}

		} catch (IOException ex) {
//			Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

}
