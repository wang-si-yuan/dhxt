package top.vicp.wsy19990317.dhxt.common.aspect;

import top.vicp.wsy19990317.dhxt.common.annotation.PassToken;
import top.vicp.wsy19990317.dhxt.common.result.ResultUtil;
import top.vicp.wsy19990317.dhxt.person.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class TokenCheckAspect {
    @Autowired
    private ResultUtil resultUtil;

    @Autowired
    private TokenService tokenService;


    @Pointcut(value = "execution (* com..*.controller.*Controller.*(..))")
    private void TokenCheckCut(){};

    @Around("TokenCheckCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Method targetMethod = methodSignature.getMethod();
        if (targetMethod.isAnnotationPresent(PassToken.class)){
            PassToken passToken=targetMethod.getAnnotation(PassToken.class);
            if (passToken.isPassToken()){
                return pjp.proceed();
            }
        }
        try {
            if(tokenService.getCurrUser()==null){
                resultUtil.getFalse(ResultUtil.TOKEN_EXCEPTION);
            }

        }catch (NullPointerException e){
            return resultUtil.getFalse(ResultUtil.TOKEN_EXCEPTION);
        }
        return pjp.proceed();
    }

}
