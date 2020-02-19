package domain;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MesaService extends Thread{
	private Socket s;
	private ContadorMesas mesas;
	private int multiUDPPort;
	private String multiUDPNet;
	private int mesaAsignada;
	private PrintWriter out;
	private Scanner in;
	private int puesto;

	public MesaService(Socket s, ContadorMesas mesas, int multiUDPPort, String multiUDPNet, int clientes) {
		this.s = s;
		this.mesas = mesas;
		this.multiUDPPort = multiUDPPort;
		this.multiUDPNet = multiUDPNet;
		this.mesaAsignada = 0;
		this.puesto = clientes;
	}

	public void run() {
		try {
			out = new PrintWriter(s.getOutputStream());
			in = new Scanner(s.getInputStream());
			out.println("Servicio de mesas, puesto " + puesto);
			out.println("Indica la mesa que quieres atender: ");
			out.flush();
			procesaCliente();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}//end-run

	private void procesaCliente() throws IOException {
		while(!s.isClosed()) {
			String comando = in.next();
			try {
				if (comando.contains("exit") || comando.contains("quit")) {
					in.close();
					out.close();
					s.close();
					break;
				} else {
					procesaComando(comando);
				}
			}catch (Exception e) {
				in.close();
			}
		}
	}//end-procesaCliente

	private void procesaComando(String comando) {
		if (comando.contains("mesa")) {
			if (mesaAsignada == 0) {
				//Si no tiene mesa asignada se le asigna
				mesaAsignada = in.nextInt();
				boolean libre = mesas.asignaMesa(mesaAsignada,puesto);
				if (libre) out.println("Mesa " + mesaAsignada + " asignada al puesto " + puesto);
				else out.println("ERROR: Mesa " + mesaAsignada + " ocupada");
				out.flush();
			} else {
				out.println("ERROR: Mesa " + mesaAsignada + " asignada al puesto " + puesto);
				out.flush();
			}//end-mesa
		} else if (comando.contains("next")) {
			mesas.nextContador(mesaAsignada);
			String pantallaMesa = "MESA " + mesaAsignada + " puesto " + puesto + " número " +  mesas.getContador(mesaAsignada);
			out.println("OK: " + pantallaMesa);
			out.flush();
			MesaMultiCastService emisor = new MesaMultiCastService(multiUDPNet, multiUDPPort, pantallaMesa);
			emisor.start();
		}//end-next
	}
}//end-MesaService



