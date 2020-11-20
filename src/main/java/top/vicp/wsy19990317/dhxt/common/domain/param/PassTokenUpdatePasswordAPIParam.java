package top.vicp.wsy19990317.dhxt.common.domain.param;

import lombok.Data;

@Data
public class PassTokenUpdatePasswordAPIParam {
    private String dhAccount;
    private String dhPwd;
    private String dhEmail;
    private String check;
}
