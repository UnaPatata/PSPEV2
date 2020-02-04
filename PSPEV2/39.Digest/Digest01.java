import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.util.Iterator;


public class Digest01 {
	public static void main(String[] args) {
		try {
			MessageDigest md;
			String texto= "Texto de pruebas .-Patata";
			md = MessageDigest.getInstance("SHA-256");
			byte textoBytes[] = texto.getBytes();
			md.update(textoBytes);
			byte textoDigest[] = md.digest();
			
			System.out.println("Texto original: " + texto);
			String resumen = new String(textoDigest);
			System.out.println("Resumen : " +  hexadecimal(textoDigest));
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	private static String hexadecimal(byte[] textoDigest) {
		String hex = "";
		
		for (int i = 0 ; i < textoDigest.length ; i++) {
			String caracter = Integer.toHexString(textoDigest[i] & 0xFF);
			if (caracter.length() == 1) caracter = "0" + caracter;
			hex += caracter;
		}
		
		return hex.toUpperCase();
	}
}
