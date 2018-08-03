package top.zhaosd.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * 系统模块定义表
 */
@Entity
@Table(name = "sys_module" )
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class SysModule extends BaseEntity {

	@Column(nullable = false, unique = true, length = 64)
    private String code;	// 模块定义串
	@Column(length = 64)
    private String name;		// 模块名称
	@Column(length = 255)
	private String description;	// 模块描述

	private List<SysPermission> permissions; // 模块下的权限列表

	public SysModule() {
		super();
	}

	public SysModule(Integer id, String code, String name, String description) {
		super();
		super.setId(id);
		this.code = code;
		this.name = name;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public List<SysPermission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<SysPermission> permissions) {
		this.permissions = permissions;
	}

}
