package com.wang.dhxt.person.service;


import com.wang.dhxt.common.domain.po.DhUserPo;

public interface TokenService {
    public String getToken(DhUserPo user);
}
