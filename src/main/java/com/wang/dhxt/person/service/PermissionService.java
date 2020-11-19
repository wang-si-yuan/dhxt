package com.wang.dhxt.person.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.dhxt.common.domain.po.DhUserPo;

public interface PermissionService  extends IService<DhUserPo> {
    DhUserPo getCurrUser();
}
