package domain;

import java.io.IOException;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import main.MainRelayServer;

public class ListenerUDP extends Thread{
	private String multiUDPNet;
	private int multiUDPPort;
	private PipedWriter[] emisor;
	private int maxClientes;
	private int clientesConectados = 0;
	private byte[] bufferIn = new byte[1024];//1KiB
	private String sData;
	private MulticastSocket s;
	private PrintWriter [] flujoS;

	public ListenerUDP(String multiUDPNet, int multiUDPPort, PipedWriter[] emisor, int maxClientes) throws IOException {
		this.multiUDPNet = multiUDPNet;
		this.multiUDPPort = multiUDPPort;
		this.emisor = emisor;
		this.maxClientes = maxClientes;
		this.s = new MulticastSocket(multiUDPPort);
		this.s.joinGroup(InetAddress.getByName(multiUDPNet));
		this.flujoS = new PrintWriter[maxClientes];
		for (int i = 0 ; i < maxClientes ; i++) {
			this.flujoS[i] = new PrintWriter(this.emisor[i]);
		}
	}//end-constructor

	public void run() {
		DatagramPacket in = new DatagramPacket(bufferIn,bufferIn.length);
		System.out.println("Client running...");
		while (true) {
			try {
				MainRelayServer.lock.acquire();
				if (MainRelayServer.clientesActuales > 0)
					clientesConectados = MainRelayServer.clientesActuales;
				
				MainRelayServer.lock.release();
				s.receive(in);
				sData = new String(in.getData(),0,in.getData().length).trim();
				if (clientesConectados > 0) {
					for (int i = 0 ; i < clientesConectados ; i++) {
						flujoS[i].println(sData);
					}
				}
				System.out.println("Listener: " + sData);
				//Borra buffer
				for (int i = 0 ; i < bufferIn.length ; i++) {
					bufferIn[i] = 0;
				}
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}//end-try
		}//end-while
	}//end-run
}//end-class
