import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Chiffrement {

	public static void main(String[] args) {
		Path nom = Paths.get("cd.pub");
		BigInteger b = BigInteger.ZERO;
		BigInteger n = BigInteger.ZERO;
		int taille = 0;

		try (BufferedReader reader = Files.newBufferedReader(nom, StandardCharsets.UTF_8)) {
			String temp = reader.readLine();
			taille = Integer.valueOf(temp.split(" ")[0]);
			n = new BigInteger(temp.split(" ")[1]);
			b = new BigInteger(temp.split(" ")[2]);

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		Scanner sc = new Scanner(System.in);
		System.out.println("Veuillez saisir un mot :");
		String str = sc.nextLine();
		byte[] bytes = str.getBytes();
		byte[] bloc = new byte[taille / 8];
		int tailleBloc = taille / 8;
		byte[] chiffre = new byte[bytes.length];
		int lastBlocSize = 0;

		PrintWriter crypteWriter = null;
		File messCrypteFile = new File("crypte.txt");
		try {
			crypteWriter = new PrintWriter(messCrypteFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < bytes.length; i++) {
			// x^b mod n

			bloc[i % tailleBloc] = bytes[i];
			lastBlocSize++;

			if ((i + 1) % tailleBloc == 0) {
				BigInteger blocInt = new BigInteger(bloc);
				blocInt = blocInt.modPow(b, n);
				int temp = tailleBloc - 1;
				crypteWriter.println(blocInt);
			
				for (int j = 0; j < tailleBloc; j++) {
					chiffre[i - temp] = blocInt.toByteArray()[j];
					temp--;
				}
				lastBlocSize = 0;
			}

		}
		if (lastBlocSize != 0) {
			byte[] lastBloc = new byte[lastBlocSize];
			System.arraycopy(bloc, 0, lastBloc, 0, lastBlocSize);
			BigInteger blocInt = new BigInteger(lastBloc);
			blocInt = blocInt.modPow(b, n);
			crypteWriter.println(blocInt);
			int temp = lastBlocSize - 1;
			for (int i = 0; i < lastBlocSize; i++) {

				chiffre[(bytes.length - 1) - temp] = blocInt.toByteArray()[i];
				temp--;
			}
		}
		
		crypteWriter.close();
	}
}
