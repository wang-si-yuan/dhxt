package top.vicp.wsy19990317.dhxt.common.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PassToken {
    public boolean isPassToken() default true;
}
