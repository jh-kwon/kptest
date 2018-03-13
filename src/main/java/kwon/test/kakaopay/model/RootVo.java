package kwon.test.kakaopay.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;

public class RootVo implements Serializable{

    @Override
    public String toString() {
        try{
            return new ObjectMapper().writer().writeValueAsString(this);
        }catch (Exception e){
            e.printStackTrace();
        }

        return super.toString();
    }
}
