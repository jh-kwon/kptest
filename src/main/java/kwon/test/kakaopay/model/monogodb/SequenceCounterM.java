package kwon.test.kakaopay.model.monogodb;

public class SequenceCounterM extends MongoVo{
    // ex> {"_id":"useq", seq:NumberLong(1)}

    private long seq; // seq

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }
}
