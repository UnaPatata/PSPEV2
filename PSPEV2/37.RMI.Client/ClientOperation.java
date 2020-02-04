import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;

public class ClientOperation {
	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		RMIInterface conecto = (RMIInterface) Naming.lookup("//localhost/Server");
		String txt = JOptionPane.showInputDialog("Dime tu nombre");
		
		String respuesta = conecto.helloTo(txt);
		JOptionPane.showMessageDialog(null,respuesta);
	}
}
