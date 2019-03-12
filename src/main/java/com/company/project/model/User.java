package com.company.project.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "seed_user")
public class User {
    /**
     * 数字id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 角色id，tinyint类型最大127，跟角色相关
     */
    @Column(name = "role_id")
    private Byte roleId;

    /**
     * 字符型用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 是否有效
     */
    private Boolean enable;

    /**
     * 注册时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 获取数字id
     *
     * @return id - 数字id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置数字id
     *
     * @param id 数字id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取用户名
     *
     * @return name - 用户名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置用户名
     *
     * @param name 用户名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取手机号
     *
     * @return phone - 手机号
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置手机号
     *
     * @param phone 手机号
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取角色id，tinyint类型最大127，跟角色相关
     *
     * @return role_id - 角色id，tinyint类型最大127，跟角色相关
     */
    public Byte getRoleId() {
        return roleId;
    }

    /**
     * 设置角色id，tinyint类型最大127，跟角色相关
     *
     * @param roleId 角色id，tinyint类型最大127，跟角色相关
     */
    public void setRoleId(Byte roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取字符型用户id
     *
     * @return user_id - 字符型用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置字符型用户id
     *
     * @param userId 字符型用户id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取是否有效
     *
     * @return enable - 是否有效
     */
    public Boolean getEnable() {
        return enable;
    }

    /**
     * 设置是否有效
     *
     * @param enable 是否有效
     */
    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    /**
     * 获取注册时间
     *
     * @return create_time - 注册时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置注册时间
     *
     * @param createTime 注册时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}