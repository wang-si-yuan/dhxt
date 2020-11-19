package com.wang.dhxt.person.service.impl;

import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.dhxt.common.domain.po.DhUserPo;
import com.wang.dhxt.common.utils.TokenUtil;
import com.wang.dhxt.person.mapper.DhUserMapper;
import com.wang.dhxt.person.service.PermissionService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class PermissionServiceImpl extends ServiceImpl<DhUserMapper, DhUserPo> implements PermissionService {
    @Override
    public DhUserPo getCurrUser() {
        HttpServletRequest httpServletRequest=TokenUtil.getRequest();
        HttpSession session=httpServletRequest.getSession();
        return getById(JWT.decode((String) session.getAttribute("token")).getAudience().get(0));
    }
}
