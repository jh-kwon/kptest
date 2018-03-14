package kwon.test.kakaopay.scenario.withserver.testresttemplate;


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
public class TRTCouponListTest extends RootTest{

    @Autowired
    private TestRestTemplate testRestTemplate;

    private RequestParamVo requestParamVo;
    private HttpEntity<RequestParamVo> httpEntity;

    @Before
    public void setUp(){
        super.setUp();

        requestParamVo = new RequestParamVo();
        httpEntity = super.generateHttpEntity(requestParamVo);
    }

    private void validateCouponListResponse(ResponseEntity<ResponseVo> responseEntity){
//        HttpHeaders httpHeaders = responseEntity.getHeaders();
//        Iterator<String> headersIt = httpHeaders.keySet().iterator();
//        while(headersIt.hasNext()){
//            String h = headersIt.next();
//            System.out.println("Header :"+h);
//            System.out.println("    Entities :[");
//            for(String entity : httpHeaders.get(h)){
//                System.out.println("            -"+entity);
//            }
//            System.out.println("]");
//        }

        ResponseVo cListRes = responseEntity.getBody();
        assertThat(cListRes.isSuccess()).isEqualTo(true);
        assertThat(cListRes.getData()).isInstanceOf(List.class);

        List cList = (List)cListRes.getData();
        System.out.println(">>>>>>>>>> COUPON LIST [START] <<<<<<<<<<<");
        for(Object o : cList){
            Map vo  = (Map)o;
            System.out.println(vo);
        }
        System.out.println(">>>>>>>>>> COUPON LIST [END]   <<<<<<<<<<<");
    }


    @Test
    public void testGetIssuedCouponListWithRunningServer(){

        PageVo page = new PageVo();
        page.setRowno(0);
        page.setRowcnt(Constants.Pagination.DEFAULT_ROW_CNT);
        requestParamVo.setPage(page);

        URI uri = URI.create("http://localhost:8080/couponlist/issued"); // with runninng Server

        ResponseEntity<ResponseVo> responseEntity = testRestTemplate.exchange(uri, HttpMethod.POST, httpEntity, ResponseVo.class);
        this.validateCouponListResponse(responseEntity);
    }
}
