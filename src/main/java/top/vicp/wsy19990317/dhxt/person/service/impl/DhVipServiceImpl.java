package top.vicp.wsy19990317.dhxt.person.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.vicp.wsy19990317.dhxt.common.domain.po.DhVipPo;
import top.vicp.wsy19990317.dhxt.person.mapper.DhVipMapper;
import top.vicp.wsy19990317.dhxt.person.service.DhVipService;
import org.springframework.stereotype.Service;

@Service
public class DhVipServiceImpl extends ServiceImpl<DhVipMapper, DhVipPo> implements DhVipService {
}
