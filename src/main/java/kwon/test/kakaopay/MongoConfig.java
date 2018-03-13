package kwon.test.kakaopay;

import com.mongodb.MongoClient;
import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean;
import kwon.test.kakaopay.model.monogodb.SequenceCounterM;
import kwon.test.kakaopay.util.MathUtil;
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
//        seqM.setSeq(MathUtil.generatingRandomLongBounded(0, Long.MAX_VALUE)); // make random start seq for Test

        mongoTemplate.insert(seqM, Constants.MongoDB.SEQ_COUNTER_M);

        try{
            // Make indexes
            // : If I'm in real world, then I'll make unique index for {email:1, coupon:1}
            //   but in this enviroment which use Embedded MongoDB,
            //   shoul create index every application start-up
            //   by Spring data's MongoTemplate(only support creating one field index).
            //   So, I just create unique index for email
            mongoTemplate.indexOps(Constants.MongoDB.COUPON_ISSUED_M).ensureIndex(new Index().on(Constants.MongoDBField.email, Sort.Direction.ASC).unique());
            mongoTemplate.indexOps(Constants.MongoDB.COUPON_ISSUED_M).ensureIndex(new Index().on(Constants.MongoDBField.seq, Sort.Direction.ASC));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
