package com.wang.dhxt.common.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

@Data
@TableName(value = "dh_vip")
public class DhVipPo extends Model<DhVipPo> {

    @TableId(value = "dh_vip_code")
    private Integer dhVipCode;

    @TableField(value = "dh_vip")
    private String dhVip;

    @TableField(value = "dh_vip_description")
    private String dhVipDescription;

    @TableField(value = "create_time")
    private String createTime;

    @TableField(value = "update_time")
    private String updateTime;

    @TableField(value = "flag")
    private Integer flag;
}
