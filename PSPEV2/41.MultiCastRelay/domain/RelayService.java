package domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import main.MainRelayServer;

public class RelayService extends Thread{
	private Socket s;
	private int clientes;
	private PipedReader receptor;
	private BufferedReader flujoE;
	private PrintWriter out;
	private Scanner in;
	
	public RelayService(Socket s, int clientes, PipedWriter emisor) {
		this.s = s;
		this.clientes = clientes;
		try {
			this.receptor = new PipedReader(emisor);
			this.flujoE = new BufferedReader(receptor);
			MainRelayServer.lock.acquire();
			MainRelayServer.clientesActuales++;
			MainRelayServer.lock.release();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		try {
			out = new PrintWriter(s.getOutputStream());
			in = new Scanner(s.getInputStream());
			out.println("Servicio de relay activo");
			out.flush();
			procesaCliente();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}//end-run	

	private void procesaCliente()  throws IOException {
		while(!s.isClosed()) {
			String mensa = flujoE.readLine();
			out.println("Relay: " + mensa);
			out.flush();
		}
	}//end-procesaCliente
}
