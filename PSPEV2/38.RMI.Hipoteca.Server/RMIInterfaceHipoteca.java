import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIInterfaceHipoteca extends Remote{
	public double calculaCuota(double capital, double interes,double tiempo) throws RemoteException;
}
