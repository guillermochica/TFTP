package servidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Random;

import protocolo.TFTP;

public class Servidor {
	
	private DatagramSocket socketInicio;
	private DatagramSocket socketEnvio;
	InetAddress ip;
	int port;
	Random r;

	int dPort;
	
	public Servidor(InetAddress ip) throws SocketException{
		this.ip = ip;
		int min = 49152;
		int max = 65535;
		port = r.nextInt();
		port = port * (max-min) + min;
		socketEnvio = new DatagramSocket(port, ip);
		socketInicio = new DatagramSocket(69, ip);
	}
	
	public void recibirFichero() throws IOException {
		
		//Recibe la peticion WRQ del cliente
		byte[] wrq = new byte[500];
		DatagramPacket rPacket = new DatagramPacket(wrq, wrq.length);
		
		//PONER PRINT DE LOS MENSAJES
		
		socketInicio.receive(rPacket);
		dPort = rPacket.getPort();
		
		//La contesta con un ACK
		if(rPacket.getData()[1]==TFTP.OPCODE_WRQ){
			
			byte[] ack = TFTP.crearACK(new byte[]{0, 0});
			DatagramPacket ackPacket = new DatagramPacket(ack, ack.length, ip, dPort);
			socketEnvio.send(ackPacket);
		}
		
		
		//Recibe los distintos paquetes de datos
		byte[] data = new byte[512];
		DatagramPacket dataPacket = new DatagramPacket(data, data.length);
		socketEnvio.receive(dataPacket);
		
		while(ultimoPaquete(dataPacket)) {
			
			
			//Confirma los datagramas de datos con ACK
			
			//Recibe el siguiente paquete
			
			
		}

		
	}
	
	public boolean ultimoPaquete(DatagramPacket p) {
		boolean esUltimo = false;
		
		if(p.getData().length < 516) {
			esUltimo=true;
		}
		
		return esUltimo;
	}

}
