package top.vicp.wsy19990317.dhxt.person.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.vicp.wsy19990317.dhxt.common.domain.po.DhUserPo;
import top.vicp.wsy19990317.dhxt.common.utils.IsCodeUtil;
import top.vicp.wsy19990317.dhxt.common.utils.MailUtil;
import top.vicp.wsy19990317.dhxt.common.utils.Md5;
import top.vicp.wsy19990317.dhxt.common.utils.PasswordChecker;
import top.vicp.wsy19990317.dhxt.person.mapper.DhUserMapper;
import top.vicp.wsy19990317.dhxt.person.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class RegisterServiceImpl extends ServiceImpl<DhUserMapper, DhUserPo> implements RegisterService {

    @Autowired
    private PasswordChecker passwordChecker;


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MailUtil mailUtil;

    /**
     *
     * @param user
     * @return
     * 0- 邮箱为空
     * 1- 邮箱格式错误
     * 2- 电话号码为空
     * 3- 电话号码格式错误
     * 4- 邮箱重复
     * 5- 电话号码重复
     * 6- 密码格式错误
     */
    @Override
    public Integer register(DhUserPo user) throws Exception {

        switch (IsCodeUtil.checkEmail(user.getDhEmail())){
            case IsCodeUtil.empty: return 0;
            case IsCodeUtil.format_error:return 1;
        }
        switch (IsCodeUtil.checkPhone(user.getDhPhone())){
            case IsCodeUtil.empty:return 2;
            case IsCodeUtil.format_error:return 3;
        }

        if(getOne(new QueryWrapper<DhUserPo>().lambda().eq(DhUserPo::getDhEmail,user.getDhEmail()))!=null){
            return 4;
        }
        if(getOne(new QueryWrapper<DhUserPo>().lambda().eq(DhUserPo::getDhPhone,user.getDhPhone()))!=null){
            return 5;
        }
        if(!passwordChecker.check(user.getDhPwd())){
            return 6;
        }
        user.setDhPwd(Md5.md5(user.getDhPwd(),Md5.md5key));
        save(user);
        return 7;

    }

    @Override
    public String getAccountByEmail(String email) {
        DhUserPo user=getOne(new QueryWrapper<DhUserPo>().lambda().eq(DhUserPo::getDhEmail,email));
        if (user==null||user.getDhAccount()==null){
            return null;
        }else {
            return user.getDhAccount();
        }
    }

    @Override
    public String sendCheckByEmail(String user,String email) throws MessagingException {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        String check=str.toString();
        ValueOperations<String,String> operations=redisTemplate.opsForValue();
        operations.set(email+"_check",check,900, TimeUnit.SECONDS);

        if (!redisTemplate.hasKey(email+"_check")){
            return null;
        }
        Map<String,Object> data=new HashMap<>();
        data.put("user",user);
        data.put("check",check);
        switch (IsCodeUtil.checkEmail(email)){
            case IsCodeUtil.empty: return null;
            case IsCodeUtil.format_error:return null;
        }
        mailUtil.sendThymeleafMail("验证邮件，请您务必不要转发给它人","dhxtdatacenter@163.com",email,null,null,data,"/mail/CheckEmail");
        return check;
    }

    /**
     *
     * @param email
     * @param check
     * @return
     * 0 -验证码已过期
     * -1 -验证码错误
     * 1 -成功
     */
    @Override
    public Integer isCheckByEmail(String email, String check) {
        ValueOperations<String,String> operations=redisTemplate.opsForValue();
        if (!redisTemplate.hasKey(email+"_check")){
            return 0;
        }
        if (!operations.get(email+"_check").equals(check)){
            return -1;
        }else {
            return 1;
        }
    }

    @Override
    public Boolean updatePasswordPassToken(DhUserPo user) throws Exception {
        if(!passwordChecker.check(user.getDhPwd())){
            return null;
        }
        user.setDhPwd(Md5.md5(user.getDhPwd(),Md5.md5key));
        return updateById(user);
    }

    @Override
    public String sendUpdatePasswordEmail(DhUserPo user) throws MessagingException {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        String check=str.toString();
        ValueOperations<String,String> operations=redisTemplate.opsForValue();
        operations.set(user.getDhUser()+"_updatePasswordCheck",check,1800, TimeUnit.SECONDS);

        Map<String,Object> data=new HashMap<>();
        data.put("user",user.getDhUser());
        data.put("check",check);

        mailUtil.sendThymeleafMail("验证邮件，请您务必不要转发给它人","dhxtdatacenter@163.com",user.getDhEmail(),null,null,data,"/mail/CheckUpdatePassword");

        return check;
    }

    @Override
    public Integer updatePasswordEmail(DhUserPo user,String check) {
        ValueOperations<String,String> operations=redisTemplate.opsForValue();
        if (!redisTemplate.hasKey(user.getDhUser()+"_updatePasswordCheck")){
            return 0;
        }
        if (!operations.get(user.getDhUser()+"_updatePasswordCheck").equals(check)){
            return -1;
        }else {
            return 1;
        }
    }

}
