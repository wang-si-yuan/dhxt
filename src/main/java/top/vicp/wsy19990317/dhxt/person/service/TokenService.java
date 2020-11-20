package top.vicp.wsy19990317.dhxt.person.service;


import com.baomidou.mybatisplus.extension.service.IService;
import top.vicp.wsy19990317.dhxt.common.domain.po.DhUserPo;

public interface TokenService extends IService<DhUserPo> {
    String getToken(DhUserPo user);
    DhUserPo getCurrUser();
    void deleteToken();
}
