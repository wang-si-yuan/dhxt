package com.wang.dhxt.common.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

@Data
@TableName(value = "dh_user")
public class DhUserPo extends Model<DhUserPo> {
    @TableId(value = "dh_account",type = IdType.ASSIGN_ID)
    private String dhAccount;

    @TableField(value = "dh_user")
    private String dhUser;

    @TableField(value = "dh_pwd")
    private String dhPwd;

    @TableField(value = "dh_email")
    private String dhEmail;

    @TableField(value = "dh_phone")
    private String dhPhone;

    @TableField(value = "dh_age")
    private Integer dhAge;

    @TableField(value = "dh_gender_code")
    private Integer dhGenderCode;

    @TableField(value = "dh_signature")
    private String dhSignature;

    @TableField(value = "dh_vip_code")
    private Integer dhVipCode;

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private String createTime;

    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private String updateTime;

    @TableField(value = "flag")
    private Integer flag;
}
