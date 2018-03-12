package kwon.test.kakaopay.controller;

import java.util.HashMap;
import java.util.Map;

import kwon.test.kakaopay.Constants;
import kwon.test.kakaopay.model.monogodb.CouponIssuedM;
import kwon.test.kakaopay.model.monogodb.SequenceCounterM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/test")
public class TestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

	@Autowired
	MongoTemplate mongoTemplate;
	
	@RequestMapping(value="/post", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> testPostMethod(@RequestBody(/* required=true : default*/) String param) {
		
		Map<String, Object> res = new HashMap<String,Object>();
		
		//TODO html & js 만들어서 간단한 뷰 띄어보기...
		
		return res;
	}
	
	@RequestMapping(value="/get", method=RequestMethod.GET)
	public String testGetMethod(@RequestParam(name="p"/*default : empty*/, required=false/*default : true*/, defaultValue="0"/*default : none*/) int param) {
		return "testget";
	}

	@RequestMapping(value="/mongoinfo", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getMongoInfo() {
		Map<String, Object> rt = new HashMap<>();

		Map<String, Object> db1 = new HashMap<>();
		rt.put(Constants.MongoDB.COUPON_ISSUED_M, db1);
		db1.put("indexes", this.mongoTemplate.indexOps(Constants.MongoDB.COUPON_ISSUED_M).getIndexInfo());
        db1.put("rows",this.mongoTemplate.findAll(CouponIssuedM.class, Constants.MongoDB.COUPON_ISSUED_M) );


		Map<String, Object> db2 = new HashMap<>();
		rt.put(Constants.MongoDB.SEQ_COUNTER_M, db2);
		db2.put("rows",this.mongoTemplate.findAll(SequenceCounterM.class, Constants.MongoDB.SEQ_COUNTER_M) );

		return rt;
	}
}
