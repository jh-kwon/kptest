package kwon.test.kakaopay;

public class Constants {

    public interface MongoDB{
        public static final String SEQ_COUNTER_M = "SEQ_COUNTER_M"; // sequence counter
        public static final String COUPON_ISSUED_M = "COUPON_ISSUED_M"; // issued coupon history & master : id+email+coupon
    }

    public interface SequenceKey{
        public static final String USER_SEQ = "useq";
    }

    public interface MongoDBField{
        public static final String _id = "_id";
        public static final String seq = "seq";
        public static final String useq = "useq";
        public static final String email = "email";
        public static final String coupon = "coupon";

    }

    public interface ErrorCode{
        public static final int E_SERVER = 1;

        public static final int E_INVALID_EMAIL = 101;
        public static final int E_FAIL_GET_SEQ = 102;
        public static final int E_OUT_OF_RANGE_OF_COUPON = 103;
    }

    public interface Pagination{
        // row cnt per one request
        public static final int DEFAULT_ROW_CNT = 100;
        public static final int MAX_ROW_CNT = 500;
    }
}
