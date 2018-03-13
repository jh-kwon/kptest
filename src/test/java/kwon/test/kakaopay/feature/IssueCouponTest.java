package kwon.test.kakaopay.feature;

import kwon.test.kakaopay.service.CouponIssuingService;
import kwon.test.kakaopay.util.MathUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@RunWith(SpringRunner.class)
public class IssueCouponTest {
    @TestConfiguration
    static class IssueCouponTestConfig {
        @Bean
        public CouponIssuingService couponIssuingService(){
            return new CouponIssuingService();
        }
    }

    @Autowired CouponIssuingService couponIssuingService;


    @Test
    public void testIssueCoupon(){
//        long baseSeq = MathUtil.generatingRandomLongBounded(0, Long.MAX_VALUE);
        long baseSeq = Long.MAX_VALUE;
        String rt = couponIssuingService.getCouponString(baseSeq);

        System.out.println(">>>>>>>>>> coupon:["+rt+"]");
        assertThat(rt).isInstanceOf(String.class).hasSize(16);

    }

}
