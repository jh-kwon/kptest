package kwon.test.kakaopay.exceptions;

import kwon.test.kakaopay.Constants;

public class CustomException extends RuntimeException{

    private int ecode = Constants.ErrorCode.E_SERVER; // error code
    private String emsg; // error message

    public CustomException(int ecode){
        this.ecode = ecode;
    }

    public CustomException(int ecode, String emsg){
        super(emsg);

        this.ecode = ecode;
        this.emsg = emsg;
    }

    public CustomException(int ecode, Throwable t){
        super(t);

        this.ecode = ecode;
    }

    public int getEcode() {
        return ecode;
    }

    public void setEcode(int ecode) {
        this.ecode = ecode;
    }

    public String getEmsg() {
        return emsg;
    }

    public void setEmsg(String emsg) {
        this.emsg = emsg;
    }
}
