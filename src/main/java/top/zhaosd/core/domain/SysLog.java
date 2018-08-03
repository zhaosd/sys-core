package top.zhaosd.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 系统日志表
 * Created by mvt-zhaosandong-mac on 2018/7/16.
 */
@Entity
@Table(name = "sys_log" )
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class SysLog extends BaseEntity {

    @Column(nullable = false, length = 128)
    private String action;    // 操作
    @Column(length = 255)
    private String parameter; // 用户备注描述
    private Integer uid;    // 操作用户id
    @Transient
    private String user;
    @Column(length = 64)
    private String host; // 客户端host
    @Column(length = 64)
    private String ip;   // 客户端IP
    @Column(length = 64)
    private String agent; // 浏览器
    @Column(length = 64)
    private String language; // 语言
    @Column(length = 255)
    private String referer; // 客户端IP
    @Column(length = 255)
    private String origin; // 客户端IP
    @Column()
    private Date created; // 日志记录时间

    public SysLog() {
        super();
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
