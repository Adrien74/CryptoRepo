import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Date;
import java.util.Random;


public class Gencle {

	
	BigInteger in;
	
	
	public static void main(String[] args) {
		int t = 256;
		Date date = new Date();
		Random rnd = new Random(date.getTime());
		BigInteger p = randomPrime(t, rnd);
		BigInteger q = randomPrime(t, rnd);
		BigInteger n = p.multiply(q);
		
		System.out.println("p : " + p);
		System.out.println("q : " + q);
		System.out.println("n : " + n);

		BigInteger pn = p.subtract(BigInteger.ONE);
		BigInteger qn = q.subtract(BigInteger.ONE);
		
		BigInteger phi = pn.multiply(qn);
		
		System.out.println("phi : " + phi);
		
		BigInteger b = new BigInteger(t,rnd);
		b=b.mod(phi);
		System.out.println("b : " + b);

		BigInteger a = b.modInverse(phi);
		System.out.println("a : " + a);
		
		File publicFile = new File("cd.pub");
		File privateFile = new File("cd.priv");
		
		try {
			PrintWriter ppubw = new PrintWriter(publicFile);
			ppubw.println(t + " " + n + " " + b);
			ppubw.close();
			
			PrintWriter ppriw = new PrintWriter(privateFile);
			ppriw.println(t + " " + n + " " + b + " " + p + " " + q + " " + a );
			ppriw.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static BigInteger randomPrime (int t, Random rnd) {
		BigInteger result;
		do {
			result = new BigInteger(t/2 + 16, rnd);
		} while (result.bitLength()< t/2 || !MillerRabin.estPremierRapide(result, 50));
		return result;
		
	}

}
