package com.cy.news.common.Utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * @ClassName ErrorMsgUtils
 * @Author 6yi
 * @Date 2020/11/25 11:30
 * @Version 1.0
 * @Description:
 */


public class ErrorMsgUtils {

    /**
     * @author 6yi
     * @date 2020/11/25
     * @return
     * @Description 获取堆栈错误信息
     **/
    public static String getExceptionAllInfo(Exception ex) {
        ByteArrayOutputStream out = null;
        PrintStream pout = null;
        String ret = "";
        try {
            out = new ByteArrayOutputStream();
            pout = new PrintStream(out);
            ex.printStackTrace(pout);
            ret = new String(out.toByteArray());
            out.close();
        }catch(Exception e){
            return ex.getMessage();
        }finally{
            if(pout!=null){
                pout.close();
            }
        }
        return ret;
    }

}
