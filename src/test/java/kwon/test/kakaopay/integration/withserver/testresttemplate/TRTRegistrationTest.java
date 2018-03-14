package kwon.test.kakaopay.integration.withserver.testresttemplate;

import kwon.test.kakaopay.Constants;
import kwon.test.kakaopay.RootTest;
import kwon.test.kakaopay.model.PageVo;
import kwon.test.kakaopay.model.RequestParamVo;
import kwon.test.kakaopay.model.ResponseVo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TRTRegistrationTest extends RootTest{

    @Autowired
    private TestRestTemplate testRestTemplate;


    private RequestParamVo requestParamVo;

    @Before
    public void setUp(){
        super.setUp();

        requestParamVo = new RequestParamVo();
    }


    private void validateSuccessRegistResponse(ResponseEntity<ResponseVo> registRt){
        ResponseVo regiRes = registRt.getBody();
        assertThat(regiRes.isSuccess()).isEqualTo(true);
        assertThat(regiRes.getData()).isInstanceOf(String.class).asString().hasSize(16);
    }


    private void validateFailedRegistResponse(ResponseEntity<ResponseVo> registRt){
        ResponseVo regiRes = registRt.getBody();
        assertThat(regiRes.isSuccess()).isEqualTo(false);
        assertThat(regiRes.getData()).isNull();
    }


    private void validateCouponListAfterRegister(ResponseEntity<ResponseVo> cListRt, String expectedEmail, String expectedCoupon){
        ResponseVo cListRes = cListRt.getBody();
        assertThat(cListRes.isSuccess()).isEqualTo(true);
        assertThat(cListRes.getData()).isInstanceOf(List.class);

        List cList = (List)cListRes.getData();
        Map vo = (Map)cList.get(cList.size()-1);

        assertThat(vo.get(Constants.MongoDBField.email)).isEqualTo(expectedEmail);
        assertThat(vo.get(Constants.MongoDBField.coupon)).isEqualTo(expectedCoupon);
    }



    @Test
    public void testRegistAndGetIssuedCouponListInTestScope(){
        String email = "a@bc.com";

        //STEP 1-1. regist email that expect to success
        requestParamVo.setEmail(email);

        String uri = "/regist"; // with test scope context
        ResponseEntity<ResponseVo> registRt = testRestTemplate.exchange(uri, HttpMethod.POST, super.generateHttpEntity(requestParamVo), ResponseVo.class);

        this.validateSuccessRegistResponse(registRt);
        String coupon = (String)registRt.getBody().getData();
        System.out.println(">>>>>>>>>> return_coupon:["+coupon+"]");

        //STEP 1-2. regist email that expect to fail
        ResponseEntity<ResponseVo> registFailRt = testRestTemplate.exchange(uri, HttpMethod.POST, super.generateHttpEntity(requestParamVo), ResponseVo.class);
        this.validateFailedRegistResponse(registFailRt);


        //STEP 2. get issued coupon list
        PageVo page = new PageVo();
        page.setRowno(0);
        page.setRowcnt(Constants.Pagination.DEFAULT_ROW_CNT);
        requestParamVo.setPage(page);

        String uri2 = "/couponlist/issued"; // with test scope context
        ResponseEntity<ResponseVo> cListRt = testRestTemplate.exchange(uri2, HttpMethod.POST, super.generateHttpEntity(requestParamVo), ResponseVo.class);

        this.validateCouponListAfterRegister(cListRt, email, coupon);

    }


    @Test
    public void testRegistAndGetIssuedCouponListWithRunningServer(){
        String email = "a@bc.com";

        //STEP 1-1. regist email that expect to success
        requestParamVo.setEmail(email);

        URI uri = URI.create("http://localhost:8080/regist"); // with runninng Server
        ResponseEntity<ResponseVo> registRt = testRestTemplate.exchange(uri, HttpMethod.POST, super.generateHttpEntity(requestParamVo), ResponseVo.class);

        this.validateSuccessRegistResponse(registRt);
        String coupon = (String)registRt.getBody().getData();
        System.out.println(">>>>>>>>>> return_coupon:["+coupon+"]");

        //STEP 1-2. regist email that expect to fail
        ResponseEntity<ResponseVo> registFailRt = testRestTemplate.exchange(uri, HttpMethod.POST, super.generateHttpEntity(requestParamVo), ResponseVo.class);
        this.validateFailedRegistResponse(registFailRt);


        //STEP 2. get issued coupon list
        PageVo page = new PageVo();
        page.setRowno(0);
        page.setRowcnt(Constants.Pagination.DEFAULT_ROW_CNT);
        requestParamVo.setPage(page);


        URI uri2 = URI.create("http://localhost:8080/couponlist/issued"); // with runninng Server
        ResponseEntity<ResponseVo> cListRt = testRestTemplate.exchange(uri2, HttpMethod.POST, super.generateHttpEntity(requestParamVo), ResponseVo.class);

        this.validateCouponListAfterRegister(cListRt, email, coupon);

    }
}
