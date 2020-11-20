package com.wang.dhxt.person.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.dhxt.common.annotation.PassToken;
import com.wang.dhxt.common.domain.param.PassTokenUpdatePasswordAPIParam;
import com.wang.dhxt.common.domain.param.RegisterAPIParam;
import com.wang.dhxt.common.domain.param.SendUpdatePasswordEmailAPIParam;
import com.wang.dhxt.common.domain.param.UpdatePasswordEmailAPIParam;
import com.wang.dhxt.common.domain.po.DhGenderPo;
import com.wang.dhxt.common.domain.po.DhUserPo;
import com.wang.dhxt.common.result.ResultUtil;
import com.wang.dhxt.common.utils.EntityClassUtil;
import com.wang.dhxt.common.vo.ControllerResultVo;
import com.wang.dhxt.person.service.DhGenderService;
import com.wang.dhxt.person.service.RegisterService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

import static com.wang.dhxt.common.result.ResultUtil.*;

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

    @GetMapping("/checkEmail")
    @PassToken
    public ControllerResultVo checkEmailAPI(String email,String check){
        switch (registerService.isCheckByEmail(email,check)){
            case 0:
                return resultUtil.getFalse(IS_NULL_CHECK_CODE);
            case -1:
                return resultUtil.getFalse(CHECK_ERROR);
        }
        return resultUtil.getSuccess(null,CHECK_CODE_SUCCESS);
    }

    @PostMapping("/passTokenUpdatePassword")
    @PassToken
    public ControllerResultVo passTokenUpdatePasswordAPI(PassTokenUpdatePasswordAPIParam param) throws Exception {
        DhUserPo user=EntityClassUtil.<DhUserPo,PassTokenUpdatePasswordAPIParam>transfor(param,DhUserPo.class);
        String pwd=user.getDhPwd();
        if (user.getDhAccount()==null){
            if (user.getDhEmail()==null){
                return resultUtil.getFalse(EMAIL_IS_NULL);
            }else {

                QueryWrapper<DhUserPo> queryWrapperByEmail=new QueryWrapper<>();
                queryWrapperByEmail.eq("dh_email",user.getDhEmail());
                user= registerService.getOne(queryWrapperByEmail);
            }
        }else {
            user=registerService.getById(user.getDhAccount());
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

                QueryWrapper<DhUserPo> queryWrapperByEmail=new QueryWrapper<>();
                queryWrapperByEmail.eq("dh_email",user.getDhEmail());
                user= registerService.getOne(queryWrapperByEmail);
            }
        }
        String res=registerService.sendUpdatePasswordEmail(user);
        return resultUtil.getSuccess(null,CHECK_CODE_SUCCESS);
    }

    @GetMapping("/updatePasswordEmail")
    @PassToken
    public ControllerResultVo updatePasswordEmailAPI(UpdatePasswordEmailAPIParam param){
        String check=param.getCheck();
        DhUserPo user=EntityClassUtil.<DhUserPo,UpdatePasswordEmailAPIParam>transfor(param,DhUserPo.class);
        if (user.getDhAccount()==null){
            if (user.getDhEmail()==null){
                return resultUtil.getFalse(EMAIL_IS_NULL);
            }else {

                QueryWrapper<DhUserPo> queryWrapperByEmail=new QueryWrapper<>();
                queryWrapperByEmail.eq("dh_email",user.getDhEmail());
                user= registerService.getOne(queryWrapperByEmail);
            }
        }

        switch (registerService.updatePasswordEmail(user,check)){
            case 0:
                return resultUtil.getFalse(IS_NULL_CHECK_CODE);
            case -1:
                return resultUtil.getFalse(CHECK_ERROR);
        }
        return resultUtil.getSuccess(null,CHECK_CODE_SUCCESS);
    }

    @GetMapping("getAllGender")
    public ControllerResultVo getAllGenderAPI(){
        return resultUtil.getSuccess(genderService.getBaseMapper().selectList(new QueryWrapper<DhGenderPo>().lambda().isNotNull(DhGenderPo::getDhGender)),0) ;
    }
}
