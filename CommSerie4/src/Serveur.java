import gnu.io.*;
import java.io.*;



public class Serveur {
	
	CommPortIdentifier portId;
	BufferedReader in;
	PrintWriter out;
	SerialPort serialPort;
	
	
	
	public Serveur (String port) {
		
		//on recupere le numero du port a ouvrir
		try {
			portId = CommPortIdentifier.getPortIdentifier(port);
		} 
		catch (NoSuchPortException e) {}
		
		//on ouvre le port avec le processus Client
		try {
			serialPort = (SerialPort) portId.open("Client", 2000);
		} 
		catch (PortInUseException e) {}
		
		//on selectionne tous les parametres de la connexion serie:
		try {
			serialPort.setSerialPortParams(115200,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
			System.out.println("Ouverture du port "+port);
		} 
		catch (UnsupportedCommOperationException e) {}
	}
		
	public  void EnvoiMessage(String message) {
	
		try {
			serialPort.getOutputStream().write((message+"\n").getBytes("US-ASCII"));
			System.out.println("message envoye");
		}
		catch (IOException e) {}
		
	}
	
	public void RecevoirMessge() {
		try
		{
			in =new BufferedReader(new InputStreamReader(serialPort.getInputStream(), "US-ASCII"));
			System.out.println("message recu: ");
			System.out.println(in.readLine());
			
			
		}
		catch (IOException e) {}
	}
	
	

	

	
	public static void main(String args[]) {
		
		
		//short adresse=0x0000;
		
		
		try {
			Serveur serveur= new Serveur("/dev/ttyAMA0");
			serveur.RecevoirMessge();
			String ligne=serveur.in.readLine();
			if(ligne.equals("coucou")==true) {
				serveur.EnvoiMessage("bonjour");
			}
		}
		catch(IOException e) {}
		/*
		Trame trame= new Trame();
		trame.CreationTrameEnvoi(adresse,trame.trame);*/
	}

}
