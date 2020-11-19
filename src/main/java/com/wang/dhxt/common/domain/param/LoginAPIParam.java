package com.wang.dhxt.common.domain.param;

import lombok.Data;

@Data
public class LoginAPIParam {
    private String dhAccount;
    private String dhEmail;
    private String dhPwd;
}
