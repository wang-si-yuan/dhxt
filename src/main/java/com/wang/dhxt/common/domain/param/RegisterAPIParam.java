package com.wang.dhxt.common.domain.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class RegisterAPIParam {
    private String dhUser;
    private String dhPwd;
    private String dhEmail;
    private String dhPhone;
    private Integer dhAge;
    private Integer dhGenderCode;
    private String dhSignature;
}
