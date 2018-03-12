package kwon.test.kakaopay.util;

public class StringUtil {

    public static boolean isEmpty(String src){
        boolean rt = false;
        if(src == null || src.trim().isEmpty()){
            rt = true;
        }
        return rt;
    }
}
