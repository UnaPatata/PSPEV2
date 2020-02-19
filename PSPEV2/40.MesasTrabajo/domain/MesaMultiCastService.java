package domain;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MesaMultiCastService extends Thread {
	private String multiUDPNet;
	private int multiUDPPort;
	private String pantallaMesa;
	private DatagramSocket s;
	private DatagramPacket mSalida;
	
	public MesaMultiCastService(String multiUDPNet, int multiUDPPort, String pantallaMesa) {
		this.multiUDPNet = multiUDPNet;
		this.multiUDPPort = multiUDPPort;
		this.pantallaMesa = pantallaMesa;
	}
	
	public void run() {
		try {
			s = new DatagramSocket();
			mSalida = new DatagramPacket(pantallaMesa.getBytes(), pantallaMesa.getBytes().length,
					InetAddress.getByName(multiUDPNet), multiUDPPort);
			s.send(mSalida);
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}//end-run
}//end-class
