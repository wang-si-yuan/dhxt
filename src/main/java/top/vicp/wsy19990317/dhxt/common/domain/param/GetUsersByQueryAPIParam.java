package top.vicp.wsy19990317.dhxt.common.domain.param;

import lombok.Data;

@Data
public class GetUsersByQueryAPIParam extends PageParam {
    private String dhAccount;
    private String dhUser;
    private String dhPwd;
    private String dhEmail;
    private String dhPhone;
    private Integer dhAge;
    private Integer dhVipCode;
    private Integer dhGenderCode;
    private String dhSignature;
    private String createTimeStart;
    private String createTimeEnd;
}
