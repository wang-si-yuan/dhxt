package top.vicp.wsy19990317.dhxt.person.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.vicp.wsy19990317.dhxt.common.domain.param.LoginAPIParam;
import top.vicp.wsy19990317.dhxt.common.domain.po.DhUserPo;
import top.vicp.wsy19990317.dhxt.common.utils.Md5;
import top.vicp.wsy19990317.dhxt.person.mapper.DhUserMapper;
import top.vicp.wsy19990317.dhxt.person.service.LoginService;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl extends ServiceImpl<DhUserMapper, DhUserPo> implements LoginService {

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
            return getOne(new QueryWrapper<DhUserPo>().lambda().eq(DhUserPo::getDhEmail,dhEmail));
        }else if (dhAccount!=null){
            return getById(dhAccount);
        }else {
            return new DhUserPo();
        }
    }
}
