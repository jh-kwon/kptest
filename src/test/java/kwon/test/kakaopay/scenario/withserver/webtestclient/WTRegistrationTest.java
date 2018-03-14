//package kwon.test.kakaopay.scenario.withserver.webtestclient;
//
//import kwon.test.kakaopay.Constants;
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
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class WMTRegistrationTest {
//
//    @Autowired
//    private WebTestClient webTestClient; //-- start test scope server
//
//    /* for test with running server
//    private WebTestClient webTestClient; // non-autowired
//    @Before
//    public void setUp(){
//        this.webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080/").build();
//    }
//    */
//
//    @Test
//    public void testRegistAndGetIssuedCouponList(){
//        String email = "a@bc.com";
//
//        RequestParamVo requestParamVo = new RequestParamVo();
//
//        //STEP 1. regist email
//        requestParamVo.setEmail(email);
//
//        EntityExchangeResult<ResponseVo> registRt = webTestClient.post().uri("/regist")
//                .body(BodyInserters.fromObject(requestParamVo))
//                .exchange()
//                .expectStatus().isOk()
//                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
//                .expectBody(ResponseVo.class)
//                .returnResult();
//
//        ResponseVo regiRes = registRt.getResponseBody();
//        assertThat(regiRes.isSuccess()).isEqualTo(true);
//        assertThat(regiRes.getData()).isInstanceOf(String.class).asString().hasSize(16);
//        String coupon = (String)registRt.getResponseBody().getData();
//        System.out.println(">>>>>>>>>> coupon:["+coupon+"]");
//
//
//        //STEP 2. get issued coupon list
//        PageVo page = new PageVo();
//        page.setRowno(0);
//        page.setRowcnt(Constants.Pagination.DEFAULT_ROW_CNT);
//        requestParamVo.setPage(page);
//
//        webTestClient.post().uri("/couponlist/issued")
//                .body(BodyInserters.fromObject(requestParamVo))
//                .exchange()
//                .expectStatus().isOk()
//                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
//                .expectBody()
//                .jsonPath("$.success").isEqualTo(true)
//                .jsonPath("$.data").isArray()
//                .jsonPath("$.data[-1].email").isEqualTo(email)
//                .jsonPath("$.data[-1].coupon").isEqualTo(coupon);
//    }
//}

// TOO SLOW
// AND need to add some depdencies to pom.xml