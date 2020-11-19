package com.wang.dhxt.common.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionCheck {
    //自定义角色值，如果是多个角色，用逗号分割。
    public int role() default 0;
}
