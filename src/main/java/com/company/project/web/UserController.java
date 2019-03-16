package com.company.project.web;

import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.jwt.JwtTokenUtil;
import com.company.project.jwt.JwtUserDetails;
import com.company.project.model.User;
import com.company.project.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

/**
 * Created by CodeGenerator on 2019/03/12.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService mUserService;
    private final JwtTokenUtil mTokenUtil;

    @Autowired
    public UserController(JwtTokenUtil tokenUtil) {
        mTokenUtil = tokenUtil;
    }

    @PostMapping("/register")
    public Result register(String name, String password, String phone) {
        if (StringUtils.isEmpty(password)) {
            return ResultGenerator.genFailResult("密码不能为空");
        }
        if (StringUtils.isEmpty(phone)) {
            return ResultGenerator.genFailResult("手机号不能为空");
        }
        final Condition condition = new Condition(User.class);
        condition.createCriteria().andEqualTo("phone", phone);
        final List<User> users = mUserService.findByCondition(condition);
        if (users != null && !users.isEmpty()) {
            return ResultGenerator.genFailResult("该手机号已经注册");
        }
        final User user = new User(name, DigestUtils.md5Hex(DigestUtils.md5Hex(password)), phone, UUID.randomUUID().toString());
        user.setRoleId(JwtTokenUtil.DEFAULT_USER_ROLE_ID);
        mUserService.save(user);
        final String token = mTokenUtil.generateToken(new JwtUserDetails(String.valueOf(user.getId()), user.getPassword(), JwtTokenUtil.toGrantedAuthorities(user.getRoleId())));
        return ResultGenerator.genSuccessResult(token);
    }

    @PostMapping("/login")
    public Result login(String name, String password, String phone) {
        if (StringUtils.isEmpty(password)) {
            return ResultGenerator.genFailResult("密码不能为空");
        }
        if (StringUtils.isEmpty(phone)) {
            return ResultGenerator.genFailResult("手机号不能为空");
        }
        final Condition condition = new Condition(User.class);
        condition.createCriteria()
                .andEqualTo("phone", phone)
                .andEqualTo("password", password);
        final List<User> users = mUserService.findByCondition(condition);
        if (users != null && !users.isEmpty()) {
            User user = users.get(0);
            final String token = mTokenUtil.generateToken(new JwtUserDetails(String.valueOf(user.getId()), user.getPassword(), JwtTokenUtil.toGrantedAuthorities(user.getRoleId())));
            return ResultGenerator.genSuccessResult(token);
        } else {
            return ResultGenerator.genFailResult("该手机号已经注册");
        }
    }

    @PreAuthorize("hasRole('admin')")
    @PostMapping("/delete")
    public Result delete(@RequestParam Long id) {
        mUserService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PreAuthorize("principal.username.equals(#user.id)")
    @PostMapping("/update")
    public Result update(HttpServletRequest request, User user) {
        String id = mTokenUtil.getIdFromRequest(request);
        if (id == null) {
            final User byId = mUserService.findById(id);
            if (user.getName() != null) user.setName(user.getName());
            if (user.getPassword() != null)
                user.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(user.getPassword())));
            //if (user.get() != null) user.set(user.get());
            mUserService.update(byId);
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("修改失败");
    }

    @PreAuthorize("principal.username.equals(#id.toString()) or hasRole('admin')")
    @PostMapping("/detail")
    public Result detail(@RequestParam Long id) {
        User user = mUserService.findById(id);
        return ResultGenerator.genSuccessResult(user);
    }

    @PreAuthorize("hasRole('admin')")
    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<User> list = mUserService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
