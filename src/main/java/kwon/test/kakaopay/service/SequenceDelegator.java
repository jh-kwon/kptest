package kwon.test.kakaopay.service;

import kwon.test.kakaopay.Constants;
import kwon.test.kakaopay.model.monogodb.SequenceCounterM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SequenceDelegator {
    private static final Logger LOGGER = LoggerFactory.getLogger(SequenceDelegator.class);

    @Autowired
    MongoTemplate mongoTemplate;


    private long getNextSequence(String key){

        Update update = new Update();
        update.inc(Constants.MongoDBField.seq, 1);

        FindAndModifyOptions opts = new FindAndModifyOptions();
        opts.returnNew(true);

        SequenceCounterM newSeq = null;
//        synchronized(this){
            newSeq = this.mongoTemplate.findAndModify(query(where(Constants.MongoDBField._id).is(key)), update, opts, SequenceCounterM.class, Constants.MongoDB.SEQ_COUNTER_M);
//        }

        if(newSeq == null){
            throw new RuntimeException("FAIL TO GET SEQUENCE");
        }

        return newSeq.getSeq();
    }



    public long getUserSequence(){
        return this.getNextSequence(Constants.SequenceKey.USER_SEQ);
    }
}
