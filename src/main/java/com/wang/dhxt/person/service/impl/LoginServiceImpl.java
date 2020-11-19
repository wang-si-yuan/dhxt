package com.wang.dhxt.person.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.dhxt.common.domain.param.LoginAPIParam;
import com.wang.dhxt.common.domain.po.DhUserPo;
import com.wang.dhxt.common.utils.Md5;
import com.wang.dhxt.person.mapper.DhUserMapper;
import com.wang.dhxt.person.service.LoginService;
import com.wang.dhxt.person.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl extends ServiceImpl<DhUserMapper, DhUserPo> implements LoginService {

    @Autowired
    private TokenService tokenService;

    @Override
    public Integer login(LoginAPIParam param) throws Exception {
        DhUserPo user=getUser(param.getDhAccount(),param.getDhEmail());
        if (user==null){
            return 0;
        }
        if (user.getDhAccount()==null){
            return -2;
        }
        if (Md5.verify(param.getDhPwd(),Md5.md5key,user.getDhPwd())) {
            return 1;
        }else {
            return -1;
        }

    }

    @Override
    public DhUserPo getUser(String dhAccount,String dhEmail){
        if(dhEmail!=null){
            QueryWrapper<DhUserPo> byEmailWrapper=new QueryWrapper<>();
            byEmailWrapper.eq("dh_email",dhEmail);
            return getOne(byEmailWrapper);
        }else if (dhAccount!=null){
            return getById(dhAccount);
        }else {
            return new DhUserPo();
        }
    }
}
