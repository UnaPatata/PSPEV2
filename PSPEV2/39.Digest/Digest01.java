import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;


public class Digest01 {
	public static void main(String[] args) throws SignatureException {
		try {
			MessageDigest md;
			String texto= "Texto de pruebas .-Patata";
			md = MessageDigest.getInstance("MD5");
			byte textoBytes[] = texto.getBytes();
			md.update(textoBytes);
			byte textoDigest[] = md.digest();
			
			System.out.println("Texto original: " + texto);
			System.out.println("Resumen: " +  hexadecimal(textoDigest));
			System.out.println("Algoritmo: " + md.getAlgorithm());
			System.out.println("Proveedor:  " + md.getProvider());
			
			//Tamaño clave encriptación
			String algoritmo = "RSA";
			int keySize = 2048;
			String outFile = "39.Digest/claves";
			
			KeyPairGenerator kpg = KeyPairGenerator.getInstance(algoritmo);
			kpg.initialize(keySize);
			KeyPair kp = kpg.genKeyPair();
			OutputStream out = null;
			System.out.println("Claves generadas");
			System.out.println("Formato clave privada: " + kp.getPrivate().getFormat());
			
			System.out.println("Guardando clave privada en " + outFile + ".priv");
			out = new FileOutputStream(outFile + ".priv");
			guardaBinario(out,kp.getPrivate());
			out.flush();
			
			System.out.println("Guardando clave pública en " + outFile + ".pub");
			out = new FileOutputStream(outFile + ".pub");
			guardaBinario(out,kp.getPublic());
			out.close();
			
			PrivateKey clavePrivada = kp.getPrivate();
			PublicKey clavePublica = kp.getPublic();
			String algoFirma = "SHA256withRSA";
			Signature sign = Signature.getInstance(algoFirma);
			sign.initSign(clavePrivada);
			sign.update(textoDigest);
			byte[] firma = sign.sign();
			System.out.println("Fima: " + hexadecimal(firma));
			
			Signature veriSign = Signature.getInstance(algoFirma);
			veriSign.initVerify(clavePublica);
			veriSign.update(textoDigest);
			boolean verifica = veriSign.verify(firma);
			System.out.println("Fima valida: " + verifica);
			
		} catch (NoSuchAlgorithmException | IOException | InvalidKeyException e) {
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
	}//end-hexadecimal

	private static void guardaBinario(OutputStream out, Key clave) {
		try {
			out.write(clave.getEncoded());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}//end-guardaBinario
}//end-class
