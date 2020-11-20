package top.vicp.wsy19990317.dhxt.person.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.vicp.wsy19990317.dhxt.common.domain.po.DhUserPo;
import top.vicp.wsy19990317.dhxt.common.utils.TokenUtil;
import top.vicp.wsy19990317.dhxt.person.mapper.DhUserMapper;
import top.vicp.wsy19990317.dhxt.person.service.TokenService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Service
public class TokenServiceImpl extends ServiceImpl<DhUserMapper, DhUserPo> implements TokenService {

    @Override
    public DhUserPo getCurrUser() {
        HttpServletRequest httpServletRequest=TokenUtil.getRequest();
        HttpSession session=httpServletRequest.getSession();
        return getById(JWT.decode((String) session.getAttribute("token")).getAudience().get(0));
    }

    @Override
    public String getToken(DhUserPo user) {
        Date start = new Date();
        long currentTime = System.currentTimeMillis() + 60* 60 * 1000;//一小时有效时间
        Date end = new Date(currentTime);
        return JWT.create().withAudience(user.getDhAccount()).withIssuedAt(start).withExpiresAt(end).sign(Algorithm.HMAC256(user.getDhPwd()));
    }

    @Override
    public void deleteToken(){
        HttpServletRequest httpServletRequest=TokenUtil.getRequest();
        HttpSession session=httpServletRequest.getSession();
        session.removeAttribute("token");
    }
}
