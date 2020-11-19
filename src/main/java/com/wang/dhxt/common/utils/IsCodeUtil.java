package com.wang.dhxt.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IsCodeUtil {
    public static final String empty = "empty";
    public static final String format_error = "formatError";
    public static final String right = "right";

    public static String checkEmail(String email){
        if (null==email || "".equals(email)){
            return empty;
        }
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(regEx1);
        Matcher m = p.matcher(email);
        if(m.matches()){
            return right;
        }else{
            return format_error;
        }
    }

    public static String checkPhone(String phone) {

        if (null == phone || "".equalsIgnoreCase(phone)) {

            return empty;
        } else {
            if (phone.length() != 11) {
                return format_error;
            }
            String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9])|(16[6]))\\d{8}$";
            // String regex1 = "/0\\d{2,3}-\\d{7,8}/";座机
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            if (!isMatch) {
                return format_error;
            }
        }
        return right;
    }
}
