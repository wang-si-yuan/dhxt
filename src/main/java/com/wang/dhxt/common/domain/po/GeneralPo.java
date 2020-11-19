package com.wang.dhxt.common.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class GeneralPo {

    @TableField(value = "create_time")
    private String createTime;

    @TableField(value = "update_time")
    private String updateTime;
}
