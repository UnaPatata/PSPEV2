import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.Scanner;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class FTPCliente {
	public static void main(String[] args) throws SocketException, IOException {
		FTPClient clienteFTP = new FTPClient();
		String serverFTP = "172.16.6.61";
		int responseFTP;
		System.out.println("Conectando a " + serverFTP);
		clienteFTP.connect(serverFTP, 21);
		responseFTP = clienteFTP.getReplyCode();
		System.out.println("Codigo : " + responseFTP);
		clienteFTP.login("master","pepe123");
		System.out.println(clienteFTP.getReplyString());
		String workingDir = "/";
		String tipoArchivo [] = {"file","directory","sym link"};
		clienteFTP.changeWorkingDirectory(workingDir);
		FTPFile [] carpeta = clienteFTP.listFiles();
		
		System.out.println("Listado del directorio actual");
		Scanner sc = new Scanner(System.in);	
		String comando;
		
		//Comienza la ejecución de lo bonito
		listaFiles(carpeta,tipoArchivo);
		while(true) {
			System.out.println("Comando: ");
			comando = sc.nextLine();
			if (comando.contains("q")) {
				break;
			}
			if (comando.contains("download")) {
				int nFile = sc.nextInt();
				String file2download = carpeta[nFile].getName();
				String fileLocal = "download/" + carpeta[nFile].getName();
				BufferedOutputStream descarga = new BufferedOutputStream(new FileOutputStream(fileLocal));
				boolean res = clienteFTP.retrieveFile(file2download, descarga);
				if (res) {
					System.out.println("Descarga correcta: " + file2download);
				} else {
					System.out.println("Descarga errónea: " + file2download);
				}//end-if
				descarga.close();
			}
			if (comando.contains("list")) {
				listaFiles(carpeta,tipoArchivo);
			}
		}//end-whileTrue
	}//end-main

	private static void listaFiles(FTPFile[] carpeta, String[] tipoArchivo) {
		for (int i = 0; i < carpeta.length; i++) {
			if(carpeta[i].getType() == 0) {
				System.out.println(i + " " + carpeta[i].getName() + " > " + tipoArchivo[carpeta[i].getType()]);
			}
		}
	}//end-listaFiles
	
	
}//end-class
