package com.wang.dhxt.person.controller;

import com.github.pagehelper.PageHelper;
import com.wang.dhxt.common.annotation.PermissionCheck;
import com.wang.dhxt.common.domain.param.GetUsersByQueryAPIParam;
import com.wang.dhxt.common.domain.po.DhUserPo;
import com.wang.dhxt.common.finalArgs.VipType;
import com.wang.dhxt.common.result.ResultUtil;
import com.wang.dhxt.common.vo.ControllerResultVo;
import com.wang.dhxt.person.service.DhUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.wang.dhxt.common.result.ResultUtil.*;

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
