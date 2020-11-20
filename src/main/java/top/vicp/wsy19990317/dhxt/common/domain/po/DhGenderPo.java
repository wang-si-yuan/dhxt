package top.vicp.wsy19990317.dhxt.common.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "dh_gender")
public class DhGenderPo {

    @TableId(value = "dh_gender_code")
    private Integer dhGenderCode;

    @TableField(value = "dh_gender")
    private String dhGender;

    @TableField(value = "dh_gender_description")
    private String dhGenderDescription;
}
