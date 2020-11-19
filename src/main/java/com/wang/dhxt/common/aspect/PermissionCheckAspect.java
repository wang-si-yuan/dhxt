package com.wang.dhxt.common.aspect;

import com.wang.dhxt.common.annotation.PermissionCheck;
import com.wang.dhxt.common.domain.po.DhUserPo;
import com.wang.dhxt.common.result.ResultUtil;
import com.wang.dhxt.common.utils.TokenUtil;
import com.wang.dhxt.person.service.DhVipService;
import com.wang.dhxt.person.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
@Slf4j
public class PermissionCheckAspect {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private ResultUtil resultUtil;


    @Pointcut(value = "@annotation(com.wang.dhxt.common.annotation.PermissionCheck)")
    private void permissionCheckCut(){};

    @Around("permissionCheckCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable{
        log.info("====================进入AOP============================");
        System.out.println("====================进入AOP============================");
        //1.记录日志信息
        Signature signature = pjp.getSignature();
        String className = pjp.getTarget().getClass().getSimpleName();
        String methodName = signature.getName();
        log.info("className:{},methodName:{}",className,methodName);

        //2.角色权限校验
        MethodSignature methodSignature = (MethodSignature)signature;
        Method targetMethod = methodSignature.getMethod();
        if (targetMethod.isAnnotationPresent(PermissionCheck.class)){
            //获取方法上注解中表明的权限
            PermissionCheck permission =targetMethod.getAnnotation(PermissionCheck.class);
            Integer role =permission.role();
            log.info("当前接口请求的用户角色role:{}",role);
            if(role!=null){
                //String[] roles = role.split(",");//接口允许的角色
                //List<String> list = Arrays.asList(roles);
                //根据id从数据库中查询管理员权限(可用前台传过来的id作为参数)
                try {
                    DhUserPo user=permissionService.getCurrUser();
                    if(user==null){
                        resultUtil.getFalse(ResultUtil.TOKEN_EXCEPTION);
                    }else {
                        if (user.getDhVipCode()==role){
                            log.info("AOP权限角色校验通过，进入业务层处理！");
                            return pjp.proceed();
                        }
                    }

                }catch (NullPointerException e){
                    return resultUtil.getFalse(ResultUtil.TOKEN_EXCEPTION);
                }
            }
        }
        return resultUtil.getFalse(ResultUtil.ROLE_ERROR);
    }
}
