package top.vicp.wsy19990317.dhxt.person.controller;

import top.vicp.wsy19990317.dhxt.common.annotation.PassToken;
import top.vicp.wsy19990317.dhxt.common.domain.param.LoginAPIParam;
import top.vicp.wsy19990317.dhxt.common.result.ResultUtil;
import top.vicp.wsy19990317.dhxt.common.utils.TokenUtil;
import top.vicp.wsy19990317.dhxt.common.vo.ControllerResultVo;
import top.vicp.wsy19990317.dhxt.person.service.LoginService;
import top.vicp.wsy19990317.dhxt.person.service.TokenService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
            return resultUtil.getFalse(ResultUtil.USER_IS_LOGIN);
        }catch (Exception e){
            Integer res=loginService.login(param);
            switch (res){
                case 0:return resultUtil.getFalse(ResultUtil.NOT_FIND_USER);
                case 1:{
                    HttpServletRequest request= TokenUtil.getRequest();
                    String token=tokenService.getToken(loginService.getUser(param.getDhAccount(),param.getDhEmail()));
                    HttpSession session=request.getSession();
                    session.setAttribute("token",token);
                    return resultUtil.getSuccess(null, ResultUtil.LOGIN_SUCCESS);
                }
                case -1:return resultUtil.getFalse(ResultUtil.PASSWORD_ERROR);
                case -2:return resultUtil.getFalse(ResultUtil.EMAIL_IS_NULL);
                default:return resultUtil.getFalse(ResultUtil.BAD_REQUEST);
            }
        }
    }

    @PostMapping("/logout")
    public ControllerResultVo logoutAPI(){
        try {
            tokenService.deleteToken();
        }catch (Exception e){
            return resultUtil.getFalse(ResultUtil.LOGOUT_ERROR);
        }
        return resultUtil.getSuccess(null, ResultUtil.LOGOUT_SUCCESS);
    }

}
