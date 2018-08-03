package top.zhaosd.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * 系统权限定义表
 */
@Entity
@Table(name = "sys_permission" )
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class SysPermission extends BaseEntity {

	@Column(nullable = false, unique = true, length = 128)
    private String permission;	// 权限定义串, uri路径, 或自定义权限控制串
	@Column(length = 64)
    private String name;		// 权限名称, 数据库中用默认语言填充, 界面通过国际化方式调用
	@Column(length = 255)
	private String description;	// 权限描述, 同上
	@Column
	private Integer mid;	// 权限所属模块id

	public SysPermission() {
		super();
	}

	public SysPermission(Integer id, String permission, String name, String description) {
		super();
		super.setId(id);
		this.permission = permission;
		this.name = name;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}
}
