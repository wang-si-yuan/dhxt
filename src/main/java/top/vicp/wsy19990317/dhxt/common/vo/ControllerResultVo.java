package top.vicp.wsy19990317.dhxt.common.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Scope("prototype")
@Component
public class ControllerResultVo {
    private Boolean success;
    /**
     * 错误码
     */
    private Integer code = 200;
    /**
     * 提示信息
     */
    private String msg;
    /**
     * 具体内容
     */
    private Map data;
}
