import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.net.io.Util;;

public class ReadWrite{

	private InputStream remoteInput;
	private OutputStream remoteOutput;
	private InputStream localInput;
	private OutputStream localOutput;
	Thread reader,writer;

	public ReadWrite(InputStream inputStream, OutputStream outputStream, InputStream in, OutputStream out) {
		this.remoteInput = inputStream;
		this.remoteOutput = outputStream;
		this.localInput = in;
		this.localOutput = out;

		reader = new Thread() {
			public void run() {
				try {
					Util.copyStream(remoteInput, localOutput);
				} catch (IOException e) {
					e.printStackTrace();
				}//end-Try
			}//end-run
		};//end-reader

		writer = new Thread() {
			public void run() {
				int ch;
				try {
					while ((ch = localInput.read()) != -1) {
						remoteOutput.write(ch);
						remoteOutput.flush();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}//end-Try
			}//end-run
		};//end-writer
		
		reader.start();
		writer.start();
		try {
			writer.join();
			reader.interrupt();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}//end-constructor
}//end-class
