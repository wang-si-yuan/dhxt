package com.wang.dhxt.person.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.wang.dhxt.common.domain.po.DhUserPo;
import com.wang.dhxt.person.service.TokenService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {

    @Override
    public String getToken(DhUserPo user) {
        Date start = new Date();
        long currentTime = System.currentTimeMillis() + 60* 60 * 1000;//一小时有效时间
        Date end = new Date(currentTime);
        return JWT.create().withAudience(user.getDhAccount()).withIssuedAt(start).withExpiresAt(end).sign(Algorithm.HMAC256(user.getDhPwd()));
    }
}
