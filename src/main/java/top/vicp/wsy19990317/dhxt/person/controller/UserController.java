package top.vicp.wsy19990317.dhxt.person.controller;

import top.vicp.wsy19990317.dhxt.common.annotation.PermissionCheck;
import top.vicp.wsy19990317.dhxt.common.domain.param.GetUsersByQueryAPIParam;
import top.vicp.wsy19990317.dhxt.common.finalArgs.VipType;
import top.vicp.wsy19990317.dhxt.common.result.ResultUtil;
import top.vicp.wsy19990317.dhxt.common.vo.ControllerResultVo;
import top.vicp.wsy19990317.dhxt.person.service.DhUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static top.vicp.wsy19990317.dhxt.common.result.ResultUtil.*;

@RestController
@RequestMapping("/user")
@Api(tags = "用户操作接口")
public class UserController {

    @Autowired
    private DhUserService userService;

    @Autowired
    private ResultUtil resultUtil;

    @GetMapping("/GetUsersByQuery")
    @PermissionCheck(role = VipType.ADMIN)
    public ControllerResultVo GetUsersByQueryAPI(GetUsersByQueryAPIParam param){
        return resultUtil.getSuccess(userService.getUsersByQuery(param),QUERY_SUCCESS);
    }

}
