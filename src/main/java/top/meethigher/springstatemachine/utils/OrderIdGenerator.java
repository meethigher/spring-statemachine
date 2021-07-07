package top.meethigher.springstatemachine.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author chenchuancheng
 * @date 2021-07-07 16:46:36
 **/
public class OrderIdGenerator {
    public String createId(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }
}
