package com.wang.dhxt.person.controller;

import com.wang.dhxt.common.annotation.PassToken;
import com.wang.dhxt.common.annotation.PermissionCheck;
import com.wang.dhxt.common.domain.param.LoginAPIParam;
import com.wang.dhxt.common.result.ResultUtil;
import com.wang.dhxt.common.utils.TokenUtil;
import com.wang.dhxt.common.vo.ControllerResultVo;
import com.wang.dhxt.person.service.LoginService;
import com.wang.dhxt.person.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.wang.dhxt.common.result.ResultUtil.*;

@RestController
@RequestMapping("/login")
@Api(tags = "登陆接口")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ResultUtil resultUtil;

    @PostMapping("/login")
    @PassToken
    //account:1329314287004426241 pwd:Wsy_mt1203
    public ControllerResultVo loginAPI(LoginAPIParam param) throws Exception {
        try {
            tokenService.getCurrUser();
            return resultUtil.getFalse(USER_IS_LOGIN);
        }catch (Exception e){
            Integer res=loginService.login(param);
            switch (res){
                case 0:return resultUtil.getFalse(ResultUtil.NOT_FIND_USER);
                case 1:{
                    HttpServletRequest request= TokenUtil.getRequest();
                    String token=tokenService.getToken(loginService.getUser(param.getDhAccount(),param.getDhEmail()));
                    HttpSession session=request.getSession();
                    session.setAttribute("token",token);
                    return resultUtil.getSuccess(null,LOGIN_SUCCESS);
                }
                case -1:return resultUtil.getFalse(PASSWORD_ERROR);
                case -2:return resultUtil.getFalse(EMAIL_IS_NULL);
                default:return resultUtil.getFalse(BAD_REQUEST);
            }
        }
    }

    @PostMapping("/logout")
    public ControllerResultVo logoutAPI(){
        try {
            tokenService.deleteToken();
        }catch (Exception e){
            return resultUtil.getFalse(LOGOUT_ERROR);
        }
        return resultUtil.getSuccess(null,LOGOUT_SUCCESS);
    }

}
