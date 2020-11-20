package top.vicp.wsy19990317.dhxt.person.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.vicp.wsy19990317.dhxt.common.domain.param.LoginAPIParam;
import top.vicp.wsy19990317.dhxt.common.domain.po.DhUserPo;

public interface LoginService extends IService<DhUserPo> {
    Integer login(LoginAPIParam param) throws Exception;
    DhUserPo getUser(String dhAccount,String dhEmail);
}
