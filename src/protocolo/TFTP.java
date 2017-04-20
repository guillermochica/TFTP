package protocolo;

public class TFTP {
	
	public static byte OPCODE_RRQ=1;
	public static byte OPCODE_WRQ=2;
	public static byte OPCODE_DATA=3;
	public static byte OPCODE_ACK=4;
	public static byte OPCODE_ERROR=5;
	
	private static byte zero = 0;
	
	public static byte[] crearPeticion(String fileName, String mode, boolean type){
		/*Longitud de la peticion
		 * 2 bytes de Opcode (1 byte 0 + 1 byte codigo) + longitud fileName + 1 byte 0 + longitud mode + 1 byte 0
		 */
		int rqByteLength = 2 + fileName.length() + 1 + mode.length() + 1;
		
		byte[] rqByteArray = new byte[rqByteLength];
		
		int position = 0; //recorre el array de bytes rrqByteArray
		rqByteArray[position] = zero; //en la primera posicion esta el byte 0
		position++;
		
		// Si type es true -> Read 
		// Si type es false -> Write
		if(type) {
			rqByteArray[position] = OPCODE_RRQ;
		}
		else{
			rqByteArray[position] = OPCODE_WRQ;
		}
		
		position++;
		for (int i = 0; i < fileName.length(); i++) {
			rqByteArray[position] = (byte) fileName.charAt(i); //pasamos a byte cada caracter del fileName
			position++;
		}
		rqByteArray[position] = zero;
		position++;
		for (int i = 0; i < mode.length(); i++) {
			rqByteArray[position] = (byte) mode.charAt(i);
			position++;
		}
		rqByteArray[position] = zero;
		
		return rqByteArray;
	}
	
	public static byte[] crearPaqueteDatos(byte[] blockNumber, byte[] data){
		
		//Longitud: 2 bytes OPCode + 2 bytes blockNumber + n bytes data
		
		int packetLength = 2 + 2 + data.length; 
		
		byte[] dataByteArray = new byte[packetLength];
		int posicion = 0;
		dataByteArray[posicion] = 0;
		posicion++;
		dataByteArray[posicion] = OPCODE_DATA;
		posicion++;
		dataByteArray[posicion] = blockNumber[0];
		posicion++;
		dataByteArray[posicion] = blockNumber[1];
		posicion++;
		
		for(int i=0;i<data.length ; i++) {
			dataByteArray[posicion] = data[i];
			posicion++;
		}
		
		return dataByteArray;
		
		
	}
	
	public static byte[] crearACK(byte[] blockNumber){
		//Son dos bytes de blockNumber
		byte[] ACK = { 0, OPCODE_ACK, blockNumber[0], blockNumber[1] };
		
		return ACK;
		
	}
	
	public static byte[] crearError(String errorMsg, byte[] errorCode){
		

		int errorLength = 2 + 2 + errorMsg.length() + 1;
		
		byte[] errorArray = new byte[errorLength];
		
		int position = 0; //recorre el array de bytes rrqByteArray
		
		//OPCODE
		errorArray[position] = zero; //en la primera posicion esta el byte 0
		position++;
		errorArray[position] = OPCODE_ERROR;
		position++;
		//ERROR CODE
		errorArray[position] = errorCode[0];
		position++;
		errorArray[position] = errorCode[1];
		position++;
		//ERROR MESSAGE
		for (int i = 0; i < errorMsg.length(); i++) {
			errorArray[position] = (byte) errorMsg.charAt(i); 
			position++;
		}
		//ZERO
		errorArray[position] = zero;
		
		return errorArray;
	}
}
