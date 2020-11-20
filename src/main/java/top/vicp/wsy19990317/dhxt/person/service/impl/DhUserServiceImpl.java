package top.vicp.wsy19990317.dhxt.person.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import top.vicp.wsy19990317.dhxt.common.domain.param.GetUsersByQueryAPIParam;
import top.vicp.wsy19990317.dhxt.common.domain.param.UpdateCurrUserAPIParam;
import top.vicp.wsy19990317.dhxt.common.domain.po.DhUserPo;
import top.vicp.wsy19990317.dhxt.common.utils.EntityClassUtil;
import top.vicp.wsy19990317.dhxt.common.utils.Md5;
import top.vicp.wsy19990317.dhxt.person.mapper.DhUserMapper;
import top.vicp.wsy19990317.dhxt.person.service.DhUserService;
import top.vicp.wsy19990317.dhxt.person.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DhUserServiceImpl extends ServiceImpl<DhUserMapper, DhUserPo> implements DhUserService {

    @Autowired
    private TokenService tokenService;

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

    public Integer updateCurrUser(UpdateCurrUserAPIParam param) throws Exception {
        DhUserPo user= EntityClassUtil.transfor(param,DhUserPo.class);
        user.setDhPwd(Md5.md5(user.getDhPwd(),Md5.md5key));
        user.setDhAccount(tokenService.getCurrUser().getDhAccount());
        if(updateById(user)){
            return 1;
        }else return 0;
    }
}
