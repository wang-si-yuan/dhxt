package com.wang.dhxt.person.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.dhxt.common.domain.po.DhUserPo;

public interface TokenService extends IService<DhUserPo> {
    String getToken(DhUserPo user);
    DhUserPo getCurrUser();
    void deleteToken();
}
