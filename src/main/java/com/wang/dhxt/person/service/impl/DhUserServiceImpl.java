package com.wang.dhxt.person.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wang.dhxt.common.domain.param.GetUsersByQueryAPIParam;
import com.wang.dhxt.common.domain.po.DhUserPo;
import com.wang.dhxt.person.mapper.DhUserMapper;
import com.wang.dhxt.person.service.DhUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DhUserServiceImpl extends ServiceImpl<DhUserMapper, DhUserPo> implements DhUserService {
    @Override
    public PageInfo<DhUserPo> getUsersByQuery(GetUsersByQueryAPIParam param) {
        QueryWrapper<DhUserPo> queryWrapper=new QueryWrapper<>();
        if (param.getDhAccount()!=null){
            queryWrapper.like("dh_account",param.getDhAccount());
        }
        if (param.getDhEmail()!=null){
            queryWrapper.like("dh_email",param.getDhEmail());
        }
        if (param.getDhPhone()!=null){
            queryWrapper.like("dh_phone",param.getDhPhone());
        }
        if (param.getDhVipCode()!=null){
            queryWrapper.eq("dh_vip_code",param.getDhVipCode());
        }
        if (param.getDhGenderCode()!=null){
            queryWrapper.eq("dh_gender_code",param.getDhGenderCode());
        }
        if (param.getDhSignature()!=null){
            queryWrapper.like("dh_signature",param.getDhSignature());
        }
        if (param.getCreateTimeStart()!=null){
            if (param.getCreateTimeEnd()!=null){
                queryWrapper.between("create_time",param.getCreateTimeStart(),param.getCreateTimeEnd());
            }else {
                queryWrapper.ge("create_time",param.getCreateTimeStart());
            }
        }else if (param.getCreateTimeEnd()!=null){
            queryWrapper.le("create_time",param.getCreateTimeEnd());
        }
        PageHelper.startPage(param.getPageNum(),param.getPageSize());
        List<DhUserPo> userList =getBaseMapper().selectList(queryWrapper);

        return new PageInfo<>(userList);
    }
}
