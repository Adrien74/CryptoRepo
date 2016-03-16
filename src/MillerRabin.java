import java.math.BigInteger;
import java.util.Random;

public class MillerRabin {
	public static void main(String[] args) {
		System.out.println(estPremierRapide(new BigInteger("14496839410984262740537680163695048314329116"), 50));
	}
	public static boolean estPremierRapide(BigInteger n, int k) {
		Random rnd = new Random();
		while (k > 0) {
			if (!estProbablementPremier(n, rnd)) {
				return false;
			}
			k--;
		}
		return true;
	}

	public static boolean estProbablementPremier(BigInteger n, Random rnd) {
		if (n.equals(BigInteger.ONE))
		{
			return true;
		}
		if (n.equals(BigInteger.ZERO) || n.equals(new BigInteger("2")))
		{
			return false;
		}
		BigInteger m = n;
		// Ecrire n-1 = 2^k * m, ou m est impair
		m = m.subtract(BigInteger.ONE);
		int k = 0;
		while (!m.testBit(0)) {
			m = m.divide(new BigInteger("2"));
			k++;
		}
		//Choisir aleatoirement a,1 <= a <= n-1

		BigInteger a;
		do {
			a = new BigInteger(n.subtract(BigInteger.ONE).bitLength(), rnd);
		} while (a.compareTo(n.subtract(BigInteger.ONE)) >= 0 || a.equals(BigInteger.ZERO));

		BigInteger b = a.modPow(m, n);
		if (b.mod(n).equals(BigInteger.ONE)) {
			return true;
		} else {
			for (int i = 0; i < k; i++) {
				if (b.mod(n).equals(n.subtract(BigInteger.ONE))) {
					return true;
				} else {
					b = b.pow(2).mod(n);
				}
			}
		}
		return false;
	}
}
