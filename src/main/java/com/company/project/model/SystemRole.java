package com.company.project.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "system_role")
public class SystemRole {
    /**
     * 角色id，tinyint类型最大127
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色对应的值
     */
    private Integer value;

    /**
     * 是否有效
     */
    private Boolean enable;

    /**
     * 角色创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 获取角色id，tinyint类型最大127
     *
     * @return id - 角色id，tinyint类型最大127
     */
    public Byte getId() {
        return id;
    }

    /**
     * 设置角色id，tinyint类型最大127
     *
     * @param id 角色id，tinyint类型最大127
     */
    public void setId(Byte id) {
        this.id = id;
    }

    /**
     * 获取角色名称
     *
     * @return name - 角色名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置角色名称
     *
     * @param name 角色名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取角色对应的值
     *
     * @return value - 角色对应的值
     */
    public Integer getValue() {
        return value;
    }

    /**
     * 设置角色对应的值
     *
     * @param value 角色对应的值
     */
    public void setValue(Integer value) {
        this.value = value;
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
     * 获取角色创建时间
     *
     * @return create_time - 角色创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置角色创建时间
     *
     * @param createTime 角色创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}