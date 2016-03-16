import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Signe {

	public static void main(String[] args) {
		Path nom = Paths.get(args[0] + ".priv");
		BigInteger a = BigInteger.ZERO;
		BigInteger n = BigInteger.ZERO;
		int taille = 0;

		try (BufferedReader reader = Files.newBufferedReader(nom, StandardCharsets.UTF_8)) {
			String temp = reader.readLine();
			taille = Integer.valueOf(temp.split(" ")[0]);
			n = new BigInteger(temp.split(" ")[1]);
			a = new BigInteger(temp.split(" ")[5]);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		Path messageFile = Paths.get(args[1]);
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try (InputStream is = Files.newInputStream(messageFile);
				DigestInputStream dis = new DigestInputStream(is, md)) {
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		byte[] bytes = md.digest();

		PrintWriter crypteWriter = null;
		File messCrypteFile = new File("signe" + args[1]);
		try {
			crypteWriter = new PrintWriter(messCrypteFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		BigInteger blocInt = new BigInteger(bytes);
		blocInt = blocInt.modPow(a, n);
		crypteWriter.println(blocInt);
		crypteWriter.close();
	}

}
