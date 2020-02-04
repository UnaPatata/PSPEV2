import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class ServidorRMI extends UnicastRemoteObject implements RMIInterfaceHipoteca{

	protected ServidorRMI() throws RemoteException {
		super();
	}

	public static void main(String[] args) {
		try {
			System.out.println("Servidor de hipoteca funcionando");
			Naming.rebind("rmi://localhost/ServerRMI", new ServidorRMI());
		} catch (RemoteException | MalformedURLException e) {
			e.printStackTrace();
		}
	}//end-main

	@Override
	public double calculaCuota(double capital, double interes, double tiempo) throws RemoteException {
		
		double plazoMes = tiempo / 12.00;
		double interesMes = interes / 12.00;
		double cuota = (capital * interes) / (100.00 * (Math.pow(interesMes/100.00,plazoMes)));
		return cuota;
	}//end-calculaCuota
}//end-class
