package com.wang.dhxt.common.domain.param;

import lombok.Data;

@Data
public class PageParam {
    private Integer pageNum=1;
    private Integer pageSize=10;
}
