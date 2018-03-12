package kwon.test.kakaopay.model.monogodb;

import java.io.Serializable;

public class MongoVo implements Serializable{
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
