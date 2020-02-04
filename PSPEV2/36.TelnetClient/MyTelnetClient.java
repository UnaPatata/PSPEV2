import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.telnet.*;;

public class MyTelnetClient {
	public static void main(String[] args) throws SocketException, IOException {
		String server = "127.0.0.1";
		int port = 10101;
		TelnetClient telnet = new TelnetClient();
		telnet.connect(server,port);
		ReadWrite readWrite = new ReadWrite(telnet.getInputStream(), telnet.getOutputStream(),System.in,System.out);
	}
}