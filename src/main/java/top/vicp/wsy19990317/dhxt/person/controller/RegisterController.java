package top.vicp.wsy19990317.dhxt.person.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import top.vicp.wsy19990317.dhxt.common.annotation.PassToken;
import top.vicp.wsy19990317.dhxt.common.domain.param.PassTokenUpdatePasswordAPIParam;
import top.vicp.wsy19990317.dhxt.common.domain.param.RegisterAPIParam;
import top.vicp.wsy19990317.dhxt.common.domain.param.SendUpdatePasswordEmailAPIParam;
import top.vicp.wsy19990317.dhxt.common.domain.po.DhGenderPo;
import top.vicp.wsy19990317.dhxt.common.domain.po.DhUserPo;
import top.vicp.wsy19990317.dhxt.common.result.ResultUtil;
import top.vicp.wsy19990317.dhxt.common.utils.EntityClassUtil;
import top.vicp.wsy19990317.dhxt.common.vo.ControllerResultVo;
import top.vicp.wsy19990317.dhxt.person.service.DhGenderService;
import top.vicp.wsy19990317.dhxt.person.service.RegisterService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

import static top.vicp.wsy19990317.dhxt.common.result.ResultUtil.*;

@RestController
@RequestMapping("register")
@Api(tags="注册接口")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @Autowired
    private DhGenderService genderService;

    @Autowired
    private ResultUtil resultUtil;

    @PostMapping("register")
    @PassToken
    public ControllerResultVo registerAPI(RegisterAPIParam param) throws Exception {
        switch (registerService.isCheckByEmail(param.getDhEmail(),param.getCheck())){
            case 0:
                return resultUtil.getFalse(IS_NULL_CHECK_CODE);
            case -1:
                return resultUtil.getFalse(CHECK_ERROR);
        }
        Integer res=registerService.register(EntityClassUtil.<DhUserPo,RegisterAPIParam>transfor(param,DhUserPo.class));
        switch (res){
            case 0:
                return resultUtil.getFalse(EMAIL_IS_NULL);
            case 1:
                return resultUtil.getFalse(EMAIL_FORMAT_ERROR);
            case 2:
                return resultUtil.getFalse(PHONE_IS_NULL);
            case 3:
                return resultUtil.getFalse(PHONE_FORMAT_ERROR);
            case 4:
                return resultUtil.getFalse(EMAIL_DUPLICATION);
            case 5:
                return resultUtil.getFalse(PHONE_DUPLICATION);
            case 6:
                return resultUtil.getFalse(PWD_FORMAT_ERROR);
            case 7:{
                Map resMap=new HashMap();
                resMap.put("dhAccount",registerService.getAccountByEmail(param.getDhEmail()));
                return resultUtil.getSuccess(resMap,REGIST_SUCCESS);
            }
            default:return resultUtil.getFalse(BAD_REQUEST);
        }
    }

    @GetMapping("/checkEmailSend")
    @PassToken
    public ControllerResultVo checkEmailSendAPI(String user,String email) throws MessagingException {
        String res=registerService.sendCheckByEmail(user,email);
        return resultUtil.getSuccess(res,CHECK_CODE_EMAIL_SEND_SUCCESS);
    }

    @PostMapping("/passTokenUpdatePassword")
    @PassToken
    public ControllerResultVo passTokenUpdatePasswordAPI(PassTokenUpdatePasswordAPIParam param) throws Exception {
        String check=param.getCheck();
        DhUserPo user=EntityClassUtil.<DhUserPo,PassTokenUpdatePasswordAPIParam>transfor(param,DhUserPo.class);
        String pwd=user.getDhPwd();
        if (user.getDhAccount()==null){
            if (user.getDhEmail()==null){
                return resultUtil.getFalse(EMAIL_IS_NULL);
            }else {
                user= registerService.getOne(new QueryWrapper<DhUserPo>().lambda().eq(DhUserPo::getDhEmail,user.getDhEmail()));
            }
        }else {
            user=registerService.getById(user.getDhAccount());
        }
        switch (registerService.updatePasswordEmail(user,check)){
            case 0:
                return resultUtil.getFalse(IS_NULL_CHECK_CODE);
            case -1:
                return resultUtil.getFalse(CHECK_ERROR);
        }
        user.setDhPwd(pwd);
        Boolean b=registerService.updatePasswordPassToken(user);
        if (b==null){
            return resultUtil.getFalse(PWD_FORMAT_ERROR);
        }
        if (b){
            return resultUtil.getSuccess(null,PASSWORD_UPDATE_SUCCESS);
        }else{
            return resultUtil.getFalse(PASSWORD_UPDATE_ERROR);
        }
    }

    @GetMapping("/sendUpdatePasswordEmail")
    @PassToken
    public ControllerResultVo sendUpdatePasswordEmailAPI(SendUpdatePasswordEmailAPIParam param) throws MessagingException {
        DhUserPo user=EntityClassUtil.<DhUserPo,SendUpdatePasswordEmailAPIParam>transfor(param,DhUserPo.class);
        if (user.getDhAccount()==null){
            if (user.getDhEmail()==null){
                return resultUtil.getFalse(EMAIL_IS_NULL);
            }else {
                user= registerService.getOne(new QueryWrapper<DhUserPo>().lambda().eq(DhUserPo::getDhEmail,user.getDhEmail()));
            }
        }
        String res=registerService.sendUpdatePasswordEmail(user);
        return resultUtil.getSuccess(null,CHECK_CODE_SUCCESS);
    }

    @GetMapping("getAllGender")
    public ControllerResultVo getAllGenderAPI(){
        return resultUtil.getSuccess(genderService.getBaseMapper().selectList(new QueryWrapper<DhGenderPo>().lambda().isNotNull(DhGenderPo::getDhGender)),0) ;
    }
}
