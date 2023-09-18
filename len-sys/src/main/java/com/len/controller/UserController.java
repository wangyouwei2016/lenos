package com.len.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.len.form.UserForm;
import com.len.response.Result;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.len.base.BaseController;
import com.len.core.annotation.Log;
import com.len.core.annotation.Log.LOG_TYPE;
import com.len.entity.SysUser;
import com.len.exception.ServiceException;
import com.len.service.SysUserService;
import com.len.util.Checkbox;
import com.len.util.FileUtil;
import com.len.util.LenResponse;
import com.len.util.MsHelper;
import com.len.util.ReType;
import com.len.util.UploadUtil;
import com.len.validator.ValidatorUtils;
import com.len.validator.group.AddGroup;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;

/**
 * 用户管理
 */
// @Api(value="user")
@Controller
@RestController
@RequestMapping(value = "/user")
@Api(value = "用户管理", tags = "用户管理业务")
@Validated
public class UserController extends BaseController {

    private final SysUserService userService;

    private final UploadUtil uploadUtil;

    public UserController(SysUserService userService, UploadUtil uploadUtil) {
        this.userService = userService;
        this.uploadUtil = uploadUtil;
    }

    @GetMapping
    public Result<IPage<SysUser>> getAllByPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            SysUser sysUser, OrderItem sort) {
        return Result.ok(
                userService.getAllUserByPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNum, pageSize), sysUser, sort)
        );
    }

    @GetMapping(value = "mainTest")
    @RequiresPermissions("user:show")
    public String showTest() {
        return "system/user/mainTest";
    }

    @GetMapping(value = "showUser")
    @RequiresPermissions("user:show")
    public String showUser() {
        return "/new/user/list";
    }

    /**
     * 用户列表
     * 
     * @param user 条件
     * @param page page
     * @param limit limit
     * @return user list
     */
    @GetMapping(value = "showUserList")
    @ResponseBody
    @RequiresPermissions("user:show")
    public ReType showUser(SysUser user, int page, int limit) {
        ReType show = userService.show(user, page, limit);
        return show;
    }

    /**
     * 审批流定义 根据角色展示用户信息
     * 
     * @param roleId 角色id
     * @param page 当前页
     * @param limit 数量
     * @return role user
     */
    @ApiOperation(value = "/listByRoleId", httpMethod = "GET", notes = "展示角色")
    @GetMapping(value = "listByRoleId")
    @ResponseBody
    @RequiresPermissions("user:show")
    public JSONObject showUser(String roleId, int page, int limit) {
        JSONObject returnValue = new JSONObject();
        Page<Object> startPage = PageHelper.startPage(page);
        List<SysUser> users = userService.getUserByRoleId(roleId);
        returnValue.put("users", users);
        returnValue.put("totals", startPage.getTotal());
        return returnValue;
    }

    /**
     * 跳转到添加用户界面
     * 
     * @param model
     * @return add user path
     */
    @GetMapping(value = "showAddUser")
    public String goAddUser(Model model) {
        List<Checkbox> checkboxList = userService.getUserRoleList(null);
        model.addAttribute("boxJson", checkboxList);
        return "/system/user/add";
    }

    @ApiOperation(value = "/addUser", httpMethod = "POST", notes = "添加用户")
    @Log(desc = "添加用户")
    @PostMapping(value = "addUser")
    @ResponseBody
    public LenResponse addUser(SysUser user, String[] role) {
        ValidatorUtils.validateEntity(user, AddGroup.class);
        if (role == null) {
            return error(MsHelper.getMsg("user.select.role"));
        }
        userService.add(user, Arrays.asList(role));
        return succ();
    }

    @PostMapping(value = "", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public Result<UserForm> add(@RequestBody @Valid UserForm user) {
        String[] role = new String[]{user.getRole()};
        SysUser sysUser = JSONObject.parseObject(JSONObject.toJSONString(user),SysUser.class);
        userService.add(sysUser, Arrays.asList(role));
        return Result.ok(user);
    }

    @GetMapping(value = "updateUser")
    public String goUpdateUser(String id, Model model, boolean detail) {
        ValidatorUtils.notEmpty(id, "failed.get.data");
        // 用户 角色
        SysUser user = userService.getById(id);
        if (user != null) {
            String photo = user.getPhoto();
            if (StringUtils.isEmpty(photo)) {
                user.setPhoto(FileUtil.defaultPhoto);
            }
        }
        model.addAttribute("user", user);
        model.addAttribute("boxJson", userService.getUserRoleList(id));
        model.addAttribute("detail", detail);
        return "system/user/update";
    }

    @ApiOperation(value = "/updateUser", httpMethod = "POST", notes = "更新用户")
    @Log(desc = "更新用户", type = LOG_TYPE.UPDATE)
    @PostMapping(value = "updateUser")
    @ResponseBody
    public LenResponse updateUser(SysUser user, String[] role) {
        List<String> roles = new ArrayList<>();
        if (role != null) {
            roles = Arrays.asList(role);
        }
        userService.updateUser(user, roles);
        return succ(MsHelper.getMsg("update.success"));
    }

    @Log(desc = "删除用户", type = LOG_TYPE.DEL)
    @ApiOperation(value = "/del", httpMethod = "POST", notes = "删除用户")
    @PostMapping(value = "/del")
    @ResponseBody
    @RequiresPermissions("user:del")
    public LenResponse del(String id, boolean realDel) {
        userService.delById(id, realDel);
        return succ(MsHelper.getMsg("del.success"));
    }

    @GetMapping(value = "goRePass")
    public String goRePass(String id, Model model) {
        ValidatorUtils.notEmpty(id, "failed.get.data1", "id");
        model.addAttribute("user", userService.getById(id));
        return "/system/user/resetPassword";
    }

    /**
     * 修改密码(存在漏洞)
     *
     * @param userId 用户id
     * @param newPwd 用户新密码
     * @return success or fail
     */
    @Log(desc = "修改密码", type = LOG_TYPE.UPDATE)
    @PostMapping(value = "rePass")
    @ResponseBody
    @RequiresPermissions("user:repass")
    public LenResponse rePass(String userId, String newPwd) {
        userService.rePass(userId, newPwd);
        return succ(MsHelper.getMsg("update.success"));
    }

    /**
     * 头像上传 默认相对路径
     */
    @PostMapping(value = "upload")
    @ResponseBody
    public LenResponse imgUpload(@RequestParam("file") MultipartFile file) {
        return succ(uploadUtil.upload(file));
    }

    /**
     * 验证用户名是否存在
     */
    @GetMapping(value = "checkUser")
    @ResponseBody
    public LenResponse checkUser(String uname) {
        if (StringUtils.isEmpty(uname)) {
            throw new ServiceException(MsHelper.getMsg("failed.get.data"));
        }
        if (userService.checkUser(uname) > 0) {
            throw new ServiceException(MsHelper.getMsg("user.exists"));
        }
        return succ();
    }

}
