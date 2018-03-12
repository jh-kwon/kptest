package kwon.test.kakaopay.controller;

import kwon.test.kakaopay.exceptions.CustomException;
import kwon.test.kakaopay.model.RequestParamVo;
import kwon.test.kakaopay.model.ResponseVo;
import kwon.test.kakaopay.service.RegistrationService;
import kwon.test.kakaopay.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WelcomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WelcomeController.class);

    @Autowired
    private RegistrationService registrationService;


    @RequestMapping(value="/", method= RequestMethod.GET)
    public String testIndexWithGetMethod() {
        return "welcome";
    }

    @RequestMapping(value="/regist", method=RequestMethod.POST)
    @ResponseBody
    public ResponseVo emailRegistPostMethod(@RequestBody RequestParamVo param) {

        ResponseVo res =  new ResponseVo();

        try{
            String coupon = this.registrationService.registEmailAndIssueCoupon(param.getEmail());
            if(!StringUtil.isEmpty(coupon)){
                res.setSuccess(true);
                res.setData(coupon);
            }
        }catch (Exception e){
            res.setSuccess(false);

            if(e instanceof CustomException){
                CustomException ce = (CustomException)e;
                res.setEcode(ce.getEcode());
            }
        }

        return res;
    }
}
