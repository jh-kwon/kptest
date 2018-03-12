package kwon.test.kakaopay.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kwon.test.kakaopay.model.RequestParamVo;
import kwon.test.kakaopay.model.ResponseVo;
import kwon.test.kakaopay.service.CouponIssuingService;
import kwon.test.kakaopay.service.EmailValidationService;
import kwon.test.kakaopay.util.MathUtil;

@Controller
public class TestController {
	
	@Autowired private CouponIssuingService couponIssuingService;
	@Autowired private EmailValidationService emailValidationService;
	
	@RequestMapping(value="/testp", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> testPostMethod(@RequestBody(/* required=true : default*/) String param) {
		
		Map<String, Object> res = new HashMap<String,Object>();
		
		//TODO html & js 만들어서 간단한 뷰 띄어보기...
		
		return res;
	}
	
	@RequestMapping(value="/test", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> testGetMethod(@RequestParam(name="p"/*default : empty*/, required=false/*default : true*/, defaultValue="0"/*default : none*/) int param) {
		
		Map<String, Object> res = new HashMap<String,Object>();
		
		//TODO html & js 만들어서 간단한 뷰 띄어보기...
		
		return res;
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String testIndexWithGetMethod() {
		//TODO html & js 만들어서 간단한 뷰 띄어보기...
		
		return "welcome";
	}

	@RequestMapping(value="/regist", method=RequestMethod.POST)
	@ResponseBody
	public ResponseVo emailRegistPostMethod(@RequestBody RequestParamVo param) {
		ResponseVo res = new ResponseVo();
		
		if(!emailValidationService.validateEmailAddressForm(param.getEmail())) {
			res.setSuccess(false);
			
			return res;
		}
		
		res.setData(couponIssuingService.getCouponString(MathUtil.generatingRandomLongBounded(0, Long.MAX_VALUE)));
		res.setSuccess(true);
		
		return res;
	}
}
