package kwon.test.kakaopay.service;

import kwon.test.kakaopay.Constants;
import kwon.test.kakaopay.exceptions.CustomException;
import kwon.test.kakaopay.model.ResponseVo;
import kwon.test.kakaopay.model.monogodb.CouponIssuedM;
import kwon.test.kakaopay.util.MathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationService.class);

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    SequenceDelegator sequenceDelegator;

    @Autowired
    private EmailValidationService emailValidationService;

    @Autowired
    private CouponIssuingService couponIssuingService;



    public String registEmailAndIssueCoupon(String email){
        String coupon = null;

        try{
            // 1. validating email address
            if(!emailValidationService.validateEmailAddressForm(email)) {
                LOGGER.debug("Invalid Email:{}", email);

                throw new CustomException(Constants.ErrorCode.E_INVALID_EMAIL);
            }


            // 1. issue User sequence
            long useq = this.sequenceDelegator.getUserSequence();

            CouponIssuedM registM = new CouponIssuedM();
            registM.setUseq(useq);
            registM.setEmail(email);

            // 2. issue Coupon
            coupon = this.couponIssuingService.getCouponString(useq);
            registM.setCoupon(coupon);

            this.addCouponIssueM(registM); // at here, check duplicated email by DB's unique index

        }catch (CustomException ce){
            throw ce;
        }
        catch (Exception e){
            throw new CustomException(Constants.ErrorCode.E_SERVER, e);
        }

        return coupon;
    }


    private void addCouponIssueM(CouponIssuedM couponIssuedM){
        this.mongoTemplate.insert(couponIssuedM, Constants.MongoDB.COUPON_ISSUED_M);
    }


    //-- TEST
    public String issueRandomCouponForTest(){
        return this.couponIssuingService.getCouponString(MathUtil.generatingRandomLongBounded(0, Long.MAX_VALUE));
    }
}
