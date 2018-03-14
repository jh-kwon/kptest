//package kwon.test.kakaopay.scenario.withserver.webtestclient;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import kwon.test.kakaopay.Constants;
//import kwon.test.kakaopay.model.IssuedCouponVo;
//import kwon.test.kakaopay.model.PageVo;
//import kwon.test.kakaopay.model.RequestParamVo;
//import kwon.test.kakaopay.model.ResponseVo;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.reactive.server.EntityExchangeResult;
//import org.springframework.test.web.reactive.server.WebTestClient;
//import org.springframework.web.reactive.function.BodyInserters;
//
//import java.util.List;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class WMTCouponListTest {
//
//    private WebTestClient webTestClient;
//
//    @Before
//    public void setUp(){
//        this.webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080/").build();
//    }
//
//    @Test
//    public void testGetIssuedCouponList(){
//
//        RequestParamVo requestParamVo = new RequestParamVo();
//        PageVo page = new PageVo();
//        page.setRowno(0);
//        page.setRowcnt(Constants.Pagination.DEFAULT_ROW_CNT);
//        requestParamVo.setPage(page);
//
//        EntityExchangeResult<ResponseVo> registRt = webTestClient.post().uri("/couponlist/issued")
//                .body(BodyInserters.fromObject(requestParamVo))
//                .exchange()
//                .expectStatus().isOk()
//                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
//                .expectBody(ResponseVo.class)
//                .consumeWith(response -> {
//                    ResponseVo responseBody = response.getResponseBody();
//                    assertThat(responseBody.isSuccess()).isEqualTo(true);
//                    assertThat(responseBody.getData()).isInstanceOf(List.class);
//
//                    System.out.print("response:"+responseBody.toString());
//                })
//                .returnResult();
//    }
//}

// TOO SLOW
// AND need to add some depdencies to pom.xml