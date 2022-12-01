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

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import aes.AES;

public class Servidor {

	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {

		try {
			ServerSocket server = new ServerSocket(5000);
			Scanner sn = new Scanner(System.in);
            sn.useDelimiter("\n");
			Socket sc;
			
			String claveEncriptacion = "Secreto!";
			AES encriptador = new AES();

			System.out.println("Servidor iniciado");
			
			while (true) {

				// Espero la conexion del cliente
				sc = server.accept();

				DataInputStream in = new DataInputStream(sc.getInputStream());
				DataOutputStream out = new DataOutputStream(sc.getOutputStream());
				int mod = 7;
				int base = 5;
				
				System.out.println("Por favor ingresa tu clave");
				String claveS = sn.nextLine();
				int clave = Integer.parseInt(claveS);

				
				double r = (Math.pow(base, clave))%mod;
				String publica = String.valueOf(r);
				out.writeUTF(publica);
				String publicaP = in.readUTF();
				double publicPartner = Double.parseDouble(publicaP);
				
				int solucion = (int) ((Math.pow(publicPartner, clave))%mod);
				
				String solucionStr = String.valueOf(solucion);
				
				out.writeUTF(solucionStr);
				String solucionPartner = in.readUTF();
				
				 if (!solucionStr.equals(solucionPartner)) {
		            	sc.close();
		            }
				
				// Pido al cliente el nombre al cliente
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
