package com.wang.dhxt.person.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.wang.dhxt.common.domain.param.GetUsersByQueryAPIParam;
import com.wang.dhxt.common.domain.po.DhUserPo;

public interface DhUserService extends IService<DhUserPo> {
    public PageInfo<DhUserPo> getUsersByQuery(GetUsersByQueryAPIParam param);
}
