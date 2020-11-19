package com.wang.dhxt.common.domain.param;

import lombok.Data;

@Data
public class SendUpdatePasswordEmailAPIParam {
    private String dhAccount;
    private String dhEmail;
}
