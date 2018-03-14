package kwon.test.kakaopay;

import com.fasterxml.jackson.databind.ObjectMapper;
import kwon.test.kakaopay.model.RequestParamVo;
import kwon.test.kakaopay.model.RootVo;
import org.junit.Before;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

public class RootTest {
    private ObjectMapper mapper;

    @Before
    public void setUp(){
        mapper = new ObjectMapper();
    }


    protected String convertRequestParamTojson(RequestParamVo requestParamVo) throws Exception{
        String json = mapper.writer().writeValueAsString(requestParamVo);
        System.out.println("param to Json:<<"+json+">>");

        return json;
    }

    protected <T extends RootVo> T convertResponseToObject(String json, Class<T> claz) throws Exception{
        return mapper.readValue(json, claz);
    }

    protected HttpEntity<RequestParamVo> generateHttpEntity(RequestParamVo param){
        return this.generateHttpEntity(param, null);
    }

    protected HttpEntity<RequestParamVo> generateHttpEntity(RequestParamVo param, HttpHeaders header){
        if(header == null){
            header =  new HttpHeaders();
        }
        return new HttpEntity<>(param, header);
    }
}
