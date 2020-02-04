import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerOperation extends UnicastRemoteObject implements RMIInterface {

	protected ServerOperation() throws RemoteException {
		super();
	}

	public static void main(String[] args) {
		try {
			Naming.rebind("rmi://localhost/Server", new ServerOperation());
		} catch (RemoteException | MalformedURLException e) {
			e.printStackTrace();
		}
	}//end-main

	@Override
	public String helloTo(String name) throws RemoteException {
		return "Servidor saluda a " + name;
	}
}//end-class
