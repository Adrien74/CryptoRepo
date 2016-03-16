import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class VerifSigne {

	public static void main(String[] args) {
		Path nom = Paths.get(args[0] + ".pub");
		BigInteger b = BigInteger.ZERO;
		BigInteger n = BigInteger.ZERO;

		try (BufferedReader reader = Files.newBufferedReader(nom, StandardCharsets.UTF_8)) {
			String temp = reader.readLine();
			n = new BigInteger(temp.split(" ")[1]);
			b = new BigInteger(temp.split(" ")[2]);

		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		Path cryptedFile = Paths.get(args[1]);
		
		BigInteger cryptes = null;
		try (BufferedReader reader = Files.newBufferedReader(cryptedFile, StandardCharsets.UTF_8)) {
			cryptes = new BigInteger(reader.readLine());
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		

		byte[] bytes = new byte[32];
		
		cryptes = cryptes.modPow(b, n);
		bytes = cryptes.toByteArray();
		

		Path messageFile = Paths.get(args[2]);
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try (InputStream is = Files.newInputStream(messageFile);
		     DigestInputStream dis = new DigestInputStream(is, md)) 
		{

		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		byte[] hash = md.digest();
				
		
		System.out.println(toHexString(bytes));
		System.out.println(toHexString(hash));
		System.out.println(Arrays.equals(bytes, hash));
	}
	
	
	public static String toHexString(byte[] bytes) {
	    StringBuilder sb = new StringBuilder();
	    for(byte b : bytes) {
	        sb.append(String.format("%02x", b));
	    }
	    return sb.toString();
	}
	
}
