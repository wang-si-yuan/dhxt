package com.wang.dhxt.person.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.dhxt.common.domain.param.LoginAPIParam;
import com.wang.dhxt.common.domain.po.DhUserPo;

public interface LoginService extends IService<DhUserPo> {
    Integer login(LoginAPIParam param) throws Exception;
    DhUserPo getUser(String dhAccount,String dhEmail);
}
