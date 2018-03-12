package kwon.test.kakaopay.util;

import java.util.Random;

public class MathUtil {

	public static long generatingRandomLongBounded(long min, long max) {
		Random r = new Random();
	    long rt = min + ((long) (r.nextDouble() * (max - min)));
	   
	    return rt;
	}
	
	
}
