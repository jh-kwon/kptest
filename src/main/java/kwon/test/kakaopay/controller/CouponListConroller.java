package kwon.test.kakaopay.controller;

import kwon.test.kakaopay.Constants;
import kwon.test.kakaopay.exceptions.CustomException;
import kwon.test.kakaopay.model.IssuedCouponVo;
import kwon.test.kakaopay.model.PageVo;
import kwon.test.kakaopay.model.RequestParamVo;
import kwon.test.kakaopay.model.ResponseVo;
import kwon.test.kakaopay.service.CouponListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value="/couponlist")
public class CouponListConroller {

    private static final Logger LOGGER = LoggerFactory.getLogger(CouponListConroller.class);

    @Autowired
    CouponListService couponListService;

    @RequestMapping(value="/issued", method= RequestMethod.POST)
    @ResponseBody
    public ResponseVo getIssuedCouponList(@RequestBody RequestParamVo param) {

        PageVo page = param.getPage();
        if(page == null){
            page = new PageVo();
            param.setPage(page);
        }

        int rowcnt = page.getRowcnt();
        if(rowcnt == 0 /* set default */
                || rowcnt > Constants.Pagination.MAX_ROW_CNT /* check limit */)
        {
            page.setRowcnt(Constants.Pagination.DEFAULT_ROW_CNT);
        }

        ResponseVo res =  new ResponseVo();

        try{
            List<IssuedCouponVo> couponList = this.couponListService.getPaginationIssuedCouponList(param.getPage());

            res.setSuccess(true);
            res.setData(couponList);

            //pagination info
            //-- rowno
            int resultSize = couponList.size();
            if(resultSize == 0 || rowcnt > resultSize){
                page.setRowno(-1);
            }else{
                page.setRowno(couponList.get(resultSize-1).getSeq());
            }

            //-- rowcnt
            page.setRowcnt(rowcnt);

            res.setPage(page);

        }catch (Exception e){
            e.printStackTrace();

            res.setSuccess(false);
            res.setEcode(Constants.ErrorCode.E_SERVER);

            if(e instanceof CustomException){
                CustomException ce = (CustomException)e;
                res.setEcode(ce.getEcode());
            }
        }

        return res;
    }

}
