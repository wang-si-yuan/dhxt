package com.wang.dhxt.person.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.dhxt.common.domain.po.DhGenderPo;
import com.wang.dhxt.common.domain.po.DhUserPo;

import javax.mail.MessagingException;
import java.util.List;

public interface RegisterService extends IService<DhUserPo> {
    Integer register(DhUserPo user) throws Exception;
    String getAccountByEmail(String email);
    String sendCheckByEmail(String user,String email) throws MessagingException;
    Integer isCheckByEmail(String email,String check);
    Boolean updatePasswordPassToken(DhUserPo user) throws Exception;
    String sendUpdatePasswordEmail(DhUserPo user) throws MessagingException;
    Integer updatePasswordEmail(DhUserPo user,String check);
}
