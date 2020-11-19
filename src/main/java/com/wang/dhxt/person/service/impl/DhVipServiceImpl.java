package com.wang.dhxt.person.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.dhxt.common.domain.po.DhVipPo;
import com.wang.dhxt.person.mapper.DhVipMapper;
import com.wang.dhxt.person.service.DhVipService;
import org.springframework.stereotype.Service;

@Service
public class DhVipServiceImpl extends ServiceImpl<DhVipMapper, DhVipPo> implements DhVipService {
}
