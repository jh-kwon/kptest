package kwon.test.kakaopay.service;

import kwon.test.kakaopay.Constants;
import kwon.test.kakaopay.model.IssuedCouponVo;
import kwon.test.kakaopay.model.PageVo;
import kwon.test.kakaopay.model.RequestParamVo;
import kwon.test.kakaopay.model.monogodb.CouponIssuedM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CouponListService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CouponListService.class);

    @Autowired
    MongoTemplate mongoTemplate;

    public List<IssuedCouponVo> getPaginationIssuedCouponList(PageVo page){
        List<IssuedCouponVo> rtList = new ArrayList<>();

        List<CouponIssuedM> findList = this.findCouponIssuedMListOfGreaterThan(page.getRowno(), page.getRowcnt());
        for(CouponIssuedM f : findList){
            IssuedCouponVo rt = new IssuedCouponVo();
            rt.setSeq(f.getUseq());
            rt.setEmail(f.getEmail());
            rt.setCoupon(f.getCoupon());
            rt.setDate(f.getCt());

            rtList.add(rt);
        }

        return rtList;
    }


    private List<CouponIssuedM> findCouponIssuedMListOfGreaterThan(long seq, int limit){

        Query query = new Query();
        query.addCriteria(Criteria.where(Constants.MongoDBField.useq).gt(seq));
        query.limit(limit);

        return this.mongoTemplate.find(query, CouponIssuedM.class, Constants.MongoDB.COUPON_ISSUED_M); // this don't return null
    }
}
