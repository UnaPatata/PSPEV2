import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;

public class ClienteRMI {

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		RMIInterfaceHipoteca conector = (RMIInterfaceHipoteca) Naming.lookup("//localhost/ServerRMI");
		double interes = 4.00;
		double tiempo = 20 * 12;
		String response = JOptionPane.showInputDialog("Introduce la cantidad");
		double capital = Double.parseDouble(response);
		double cuota = conector.calculaCuota(capital, interes, tiempo);
		JOptionPane.showMessageDialog(null, "Para " + capital + " al " + interes + "% en " + tiempo + " al mes.");
		
	}//end-main
}//end-class
