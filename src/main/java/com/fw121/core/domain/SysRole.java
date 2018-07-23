package com.fw121.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * 系统角色表
 * Created by mvt-zhaosandong-mac on 2018/7/16.
 */
@Entity
@Table(name = "sys_role" )
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class SysRole extends BaseEntity {

    @Column(nullable = false, unique = true, length = 64)
    private String name;    // 用户登陆名
    @Column(length = 255)
    private String description; // 用户备注描述

    private List<Integer> pids; // 权限id列表

    public SysRole() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Integer> getPids() {
        return pids;
    }

    public void setPids(List<Integer> pids) {
        this.pids = pids;
    }
}
