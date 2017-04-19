package cliente;



import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.file.Files;
import java.util.Random;

import protocolo.TFTP;

public class Cliente {
	
	private DatagramSocket socket;
	InetAddress ip;
	int port;
	Random r;

	int dPort;

	
	
	public Cliente(InetAddress ip) throws SocketException{
		this.ip = ip;
		int min = 49152;
		int max = 65535;
		port = r.nextInt();
		port = port * (max-min) + min;
		socket = new DatagramSocket(port, ip);
	}
	
	
	public void rrq(){
		
		
	}
	
	public void wrq(String fileName) throws IOException{
		
		//Creo el paquete
		DatagramPacket packet;
		
		byte[] request = TFTP.crearPeticion(fileName, "netascii", false);
		
		packet = new DatagramPacket(request,request.length,ip,69);
		
		//Envio paquete
		socket.send(packet);
		
		//Recibe ACK
		byte[] reception = new byte[4];
		DatagramPacket rPacket = new DatagramPacket(reception, reception.length);
		
		socket.receive(rPacket);
		
		dPort = rPacket.getPort();
		
		//Conexion establecida, comienza envio datos
		File file = new File(fileName);
		
		byte[] array = Files.readAllBytes(new File(fileName).toPath()); //path to file
		byte[] datos = new byte[512];
		
		int blockn=0; //block number
		for(int i=0, bytes=0 ; i<array.length ; i++, bytes++){
			if(bytes==512){
				//Creamos paquete y enviamos, ponemos bytes a 0
			}
			else{
				//rellenamos array con datos
				datos[bytes] = array[i];
				//que pasa si no quedna mas datos?
			}
			
		}
		
	}
	

}
