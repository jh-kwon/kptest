package kwon.test.kakaopay.scenario.webmvctest;

import kwon.test.kakaopay.Constants;
import kwon.test.kakaopay.MongoConfig;
import kwon.test.kakaopay.RootTest;
import kwon.test.kakaopay.model.PageVo;
import kwon.test.kakaopay.model.RequestParamVo;
import kwon.test.kakaopay.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class WMTRegistrationTest extends RootTest{

    @Autowired
    private MockMvc mockMvc;


    @TestConfiguration
    static class TestConfig{
        @Bean
        public MongoTemplate mongoTemplate() throws Exception{
            return new MongoConfig().mongoTemplate();
        }

        @Bean
        public RegistrationService registrationService(){
            return new RegistrationService();
        }

        @Bean
        public CouponListService couponListService(){
            return new CouponListService();
        }

        @Bean
        public SequenceDelegator sequenceDelegator(){
            return new SequenceDelegator();
        }

        @Bean
        public EmailValidationService emailValidationService(){
            return new EmailValidationService();
        }

        @Bean
        public CouponIssuingService couponIssuingService(){
            return new CouponIssuingService();
        }
    }


    @Test
    public void testRegistAndGetIssuedCouponList() throws Exception{

        //STEP 1. regist email
        String email = "a@bc.com";
        String expectCoupon ="aaaaaaaaaaaaaaaa";

        RequestParamVo requestParamVo = new RequestParamVo();
        requestParamVo.setEmail(email);

        String registUri = "/regist"; // with test scope context
//        URI registUri = new URI("http://localhost:8080/regist"); // also test scope

        MvcResult registRt = mockMvc.perform(post(registUri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(super.convertRequestParamTojson(requestParamVo)))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.success", is(Boolean.valueOf(true))))
            .andExpect(jsonPath("$.data", is(expectCoupon)))
            .andReturn();


        MockHttpServletResponse regiRes = registRt.getResponse();
        System.out.println("[REG]content_type:"+regiRes.getContentType());
//        assertThat(regiRes.getData()).isInstanceOf(String.class).asString().hasSize(16);
        String regiResBody = regiRes.getContentAsString();
        System.out.println("[REG]response_body:["+regiResBody+"]");


        //STEP 1-2. second try with same email will be expected to fail!
        MvcResult registFailRt = mockMvc.perform(post(registUri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(super.convertRequestParamTojson(requestParamVo)))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.success", is(Boolean.valueOf(false))))
            .andReturn();

        MockHttpServletResponse regiFailRes = registFailRt.getResponse();
        String regiFailResBody = regiFailRes.getContentAsString();
        System.out.println("[REG_FAIL]response_body:["+regiFailResBody+"]");


        //STEP 2. get issued coupon list
        PageVo page = new PageVo();
        page.setRowno(0);
        page.setRowcnt(Constants.Pagination.DEFAULT_ROW_CNT);
        requestParamVo.setPage(page);

        String cListuri = "/couponlist/issued"; // with test scope context
//        URI cListuri =  new URI("http://localhost:8080/couponlist/issued"); // also test scope

        MvcResult cListRt = mockMvc.perform(post(cListuri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(super.convertRequestParamTojson(requestParamVo)))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.success", is(Boolean.valueOf(true))))
            .andExpect(jsonPath("$.data", hasSize(1)))
            .andExpect(jsonPath("$.data[-1].email", is(email)))
            .andExpect(jsonPath("$.data[-1].coupon", is(expectCoupon)))
            .andReturn();

        MockHttpServletResponse cListRes = cListRt.getResponse();
        System.out.println("[C_LIST]content_type:"+cListRes.getContentType());
        String cListResBody = cListRes.getContentAsString();
        System.out.println("[C_LIST]response_body:["+cListResBody+"]");


    }
}
