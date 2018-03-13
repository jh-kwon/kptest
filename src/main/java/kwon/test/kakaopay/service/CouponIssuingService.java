package kwon.test.kakaopay.service;

import java.math.BigInteger;

import kwon.test.kakaopay.Constants;
import kwon.test.kakaopay.exceptions.CustomException;
import org.springframework.stereotype.Service;

@Service
public class CouponIssuingService {

	/**
	 * if shuffle seedChracters, then get shuffled result(coupon string)
	 */
	private static final String seedChracters ="abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final char [] seedChracterArr = seedChracters.toCharArray();
    private static final int RANGE_CNT = seedChracterArr.length;
    public static final int LENGTH_OF_RESULT = 16;
    public static final BigInteger LIMIT =  BigInteger.valueOf(RANGE_CNT).pow(LENGTH_OF_RESULT);

    public BigInteger getLimit(){
        return LIMIT;
    }

    public String getCouponString(long no){
        BigInteger bigNo = BigInteger.valueOf(no-1);
        if( 0 > bigNo.compareTo(BigInteger.ZERO) || bigNo.compareTo(LIMIT) >= 0){
            throw new CustomException(Constants.ErrorCode.E_OUT_OF_RANGE_OF_COUPON, "Can't Issue New Coupon : Out of range!");
        }

        StringBuffer rtSb = new StringBuffer();

        BigInteger base =  BigInteger.valueOf(RANGE_CNT);
        for(int i=(LENGTH_OF_RESULT-1); i >= 0 ; i--){
            BigInteger divider = base.pow(i);
            BigInteger bigQuotient = bigNo.divide(divider);
            int quotient = bigQuotient.intValue();
            if(quotient > 0){
                bigNo = bigNo.subtract(divider.multiply(bigQuotient)); // get remainder for next loop

                rtSb.append(seedChracterArr[quotient]);
            }else{
                rtSb.append(seedChracterArr[0]); // just put one character to this position
            }
        }

        return rtSb.toString();
    }
}
