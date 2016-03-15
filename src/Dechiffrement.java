import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Dechiffrement {

	public static void main(String[] args) {
		Path nom = Paths.get("cd.priv");
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
		
		Path cryptedFile = Paths.get("crypte.txt");
		
		ArrayList<BigInteger> cryptes = new ArrayList<BigInteger>();
		List<String> allLines = null;
		try {
			allLines = Files.readAllLines(cryptedFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(String line : allLines)
		{
			cryptes.add(new BigInteger(line));
		}

		

		byte[] bytes = new byte[(taille/8)*cryptes.size()];
		
		int count=0;
		for(BigInteger currentBloc : cryptes) {
			currentBloc= currentBloc.modPow(a, n);
			for(byte currentByte : currentBloc.toByteArray())
			{
				bytes[count] = currentByte;
				count++;

			}
		}
		
		String clair = new String(bytes);
		System.out.println(clair);
	}
}
