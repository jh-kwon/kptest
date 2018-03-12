package kwon.test.kakaopay.model.monogodb;

public class CouponIssuedM extends MongoVo{
    // If I'm in real world, then I'll make unique index for {email:1, coupon:1}
    // but in this enviroment which use Embedded MongoDB,
    // shoul create index every application start-up
    // by Spring data's MongoTemplate(only support creating one field index).
    // So, I just create unique index for email

    private long useq;
    private String email;
    private String coupon;

    public long getUseq() {
        return useq;
    }

    public void setUseq(long useq) {
        this.useq = useq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }
}
