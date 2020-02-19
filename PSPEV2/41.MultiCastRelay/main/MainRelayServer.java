package main;

import java.io.IOException;
import java.io.PipedWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;

import domain.ListenerUDP;
import domain.RelayService;

public class MainRelayServer {
	public static Semaphore lock = new Semaphore(1);
	public static int clientesActuales = 0;
	
	public static void main(String[] args) throws IOException {
		final int clientTCPPort = 7777;
		final int multiUDPPort = 8888;
		final String multiUDPNet = "226.0.0.1";
		final int maxClientes = 20;
		int clientes = 0;
		
		PipedWriter [] emisor = new PipedWriter[maxClientes];
		for (int i = 0 ; i < maxClientes ; i++) {
			emisor[i] = new PipedWriter();
		}
		ListenerUDP relay = new ListenerUDP(multiUDPNet,multiUDPPort,emisor,maxClientes);
		relay.start();
		
		ServerSocket miServer = new ServerSocket(clientTCPPort);
		System.out.println("Servidor Mesas funcionando");
		System.out.println("Esperando conexiones...");
		Socket s = null;
		
		while(true) {
			if (clientes < maxClientes) {
				//Se aceptan clientes
				s = miServer.accept();
				clientes++;
				String ipCliente = s.getRemoteSocketAddress().toString();
				System.out.println("Puesto " + clientes + " conectado desde " + ipCliente);
				RelayService miServicio = new RelayService(s, clientes, emisor[clientes]);
				miServicio.start();
			} else {
				System.out.println("Límite de conexiones alcanzado");
				System.out.println("Libere alguna mesa");
			}
		}//end-whileTrue
	}//end-main
}//end-MainServer
