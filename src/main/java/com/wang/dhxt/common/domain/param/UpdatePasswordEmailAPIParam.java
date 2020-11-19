package com.wang.dhxt.common.domain.param;

import lombok.Data;

@Data
public class UpdatePasswordEmailAPIParam {
    private String dhAccount;
    private String dhEmail;
    private String check;
}
