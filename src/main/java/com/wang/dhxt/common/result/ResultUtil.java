package com.wang.dhxt.common.result;

import com.wang.dhxt.common.vo.ControllerResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class ResultUtil {

    @Autowired
    private org.springframework.beans.factory.BeanFactory beanFactory;

    static public final Integer UNKNOWN_QUESTION=0;

    static public final Integer BAD_REQUEST=-1;
    static public final Integer NOT_FIND_USER=-2;
    static public final Integer PASSWORD_ERROR=-3;
    static public final Integer EMAIL_IS_NULL=-4;
    static public final Integer EMAIL_FORMAT_ERROR=-5;
    static public final Integer PHONE_IS_NULL=-6;
    static public final Integer PHONE_FORMAT_ERROR=-7;
    static public final Integer EMAIL_DUPLICATION=-8;
    static public final Integer PHONE_DUPLICATION=-9;
    static public final Integer PWD_FORMAT_ERROR=-10;
    static public final Integer ROLE_ERROR=-11;
    static public final Integer TOKEN_EXCEPTION=-12;
    static public final Integer IS_NULL_CHECK_CODE=-13;
    static public final Integer CHECK_ERROR=-14;
    static public final Integer PASSWORD_UPDATE_ERROR=-15;
    static public final Integer USER_IS_LOGIN=-16;
    static public final Integer LOGOUT_ERROR=-17;


    static public final Integer LOGIN_SUCCESS=1;
    static public final Integer REGIST_SUCCESS=2;
    static public final Integer CHECK_CODE_EMAIL_SEND_SUCCESS=3;
    static public final Integer CHECK_CODE_SUCCESS=4;
    static public final Integer PASSWORD_UPDATE_SUCCESS=5;
    static public final Integer QUERY_SUCCESS=6;
    static public final Integer LOGOUT_SUCCESS=7;


    static private Map<Integer,String> map;

    static {
        map=new HashMap<>();

        map.put(UNKNOWN_QUESTION,"未知问题");

        map.put(BAD_REQUEST,"当前请求无法被服务器理解");
        map.put(NOT_FIND_USER,"找不到当前用户");
        map.put(PASSWORD_ERROR,"账户与密码输入不匹配");
        map.put(EMAIL_IS_NULL,"邮箱为空");
        map.put(EMAIL_FORMAT_ERROR,"邮箱格式错误");
        map.put(PHONE_IS_NULL,"电话号码为空");
        map.put(PHONE_FORMAT_ERROR,"电话号码格式错误");
        map.put(EMAIL_DUPLICATION,"邮箱重复");
        map.put(PHONE_DUPLICATION,"电话号码重复");
        map.put(PWD_FORMAT_ERROR,"密码格式错误");
        map.put(ROLE_ERROR,"身份验证失败");
        map.put(TOKEN_EXCEPTION,"token验证失败");
        map.put(IS_NULL_CHECK_CODE,"验证码失效");
        map.put(CHECK_ERROR,"验证码输入错误");
        map.put(PASSWORD_UPDATE_ERROR,"密码更新失败");
        map.put(USER_IS_LOGIN,"用户已登陆,请注销后操作");
        map.put(LOGOUT_ERROR,"登出异常");

        map.put(QUERY_SUCCESS,"查询成功");
        map.put(LOGIN_SUCCESS,"登陆成功");
        map.put(REGIST_SUCCESS,"注册成功");
        map.put(CHECK_CODE_EMAIL_SEND_SUCCESS,"邮箱验证码发送成功");
        map.put(CHECK_CODE_SUCCESS,"邮箱验证码校验成功");
        map.put(PASSWORD_UPDATE_SUCCESS,"密码更新成功");
        map.put(LOGOUT_SUCCESS,"登出成功");
    }
    public ControllerResultVo getSuccess(Object data,Integer myCode){
        ControllerResultVo res= (ControllerResultVo) beanFactory.getBean(ControllerResultVo.class);
        res.setData(new HashMap());
        res.getData().put("data",data);
        res.getData().put("myCode",myCode);
        res.setCode(200);
        res.setMsg(map.get(myCode));
        res.setSuccess(true);
        return res;
    }

    public ControllerResultVo getFalse(Integer myCode){
        ControllerResultVo res= (ControllerResultVo) beanFactory.getBean(ControllerResultVo.class);
        res.setCode(400);
        res.setMsg(map.get(myCode));
        res.setData(new HashMap());
        res.getData().put("myCode",myCode);
        res.setSuccess(false);
        return res;
    }


}
