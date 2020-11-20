package top.vicp.wsy19990317.dhxt.person.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import top.vicp.wsy19990317.dhxt.common.domain.param.GetUsersByQueryAPIParam;
import top.vicp.wsy19990317.dhxt.common.domain.po.DhUserPo;

public interface DhUserService extends IService<DhUserPo> {
    public PageInfo<DhUserPo> getUsersByQuery(GetUsersByQueryAPIParam param);
}
