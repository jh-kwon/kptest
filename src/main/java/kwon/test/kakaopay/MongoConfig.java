package kwon.test.kakaopay;

import com.mongodb.MongoClient;
import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean;
import kwon.test.kakaopay.model.monogodb.SequenceCounterM;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;

import java.io.IOException;

@Configuration
public class MongoConfig {

    // TODO move to properties file
    private static final String MONGO_DB_URL = "localhost";
    private static final String MONGO_DB_NAME = "test";

    @Bean
    public MongoTemplate mongoTemplate() throws IOException {
        EmbeddedMongoFactoryBean mongo = new EmbeddedMongoFactoryBean();
        mongo.setBindIp(MONGO_DB_URL);
        MongoClient mongoClient = mongo.getObject();

        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, MONGO_DB_NAME);

        this.initializeMongoDB(mongoTemplate);

        return mongoTemplate;
    }

    private void initializeMongoDB(MongoTemplate mongoTemplate){

        // initialize User Sequence
        SequenceCounterM seqM = new SequenceCounterM();
        seqM.setId(Constants.SequenceKey.USER_SEQ);
        seqM.setSeq(0);
        mongoTemplate.insert(seqM, Constants.MongoDB.SEQ_COUNTER_M);

        try{
            // make index
            mongoTemplate.indexOps(Constants.MongoDB.COUPON_ISSUED_M).ensureIndex(new Index().on(Constants.MongoDBField.email, Sort.Direction.ASC).unique());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
