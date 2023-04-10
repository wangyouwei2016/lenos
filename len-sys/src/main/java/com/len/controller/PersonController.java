package com.len.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.len.base.BaseController;
import com.len.base.CurrentUser;
import com.len.core.annotation.Log;
import com.len.core.shiro.Principal;
import com.len.entity.SysUser;
import com.len.service.SysUserService;
import com.len.util.LenResponse;
import com.len.util.MsHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 个人资料
 */
@Controller
@RequestMapping("/person")
@Api(value = "个人资料", tags = "个人资料")
public class PersonController extends BaseController {

    private final SysUserService userService;

    public PersonController(SysUserService userService) {
        this.userService = userService;
    }

    /**
     * 跳转到个人资料
     * 
     * @param model
     * @return
     */
    @GetMapping()
    public String toPerson(Model model) {
        CurrentUser principal = Principal.getPrincipal();
        if (principal == null) {
            return "/login";
        }
        String id = principal.getId();
        SysUser user = userService.getById(id);
        model.addAttribute("user", user);
        return "/system/person/me";
    }

    /**
     * 更新个人资料
     * 
     * @param user 用户信息
     * @return success
     */
    @ApiOperation(value = "/updateUser", httpMethod = "POST", notes = "更新用户")
    @Log(desc = "更新用户", type = Log.LOG_TYPE.UPDATE)
    @PostMapping(value = "updateUser")
    @ResponseBody
    public LenResponse updatePerson(SysUser user) {
        userService.updatePerson(user);
        return succ(MsHelper.getMsg("update.success"));
    }
}
