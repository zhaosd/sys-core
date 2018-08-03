package top.zhaosd.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.List;

/**
 * 系统用户表
 */
@Entity
@Table(name = "sys_user" )
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class SysUser extends BaseEntity {

	@Column(nullable = false, unique = true, length = 64)
    private String username;    // 用户登陆名
    @Column(length = 64)
    private String firstName;       // 用户名
    @Column(length = 64)
    private String lastName;       // 用户姓
	@Column(nullable = false, length = 255)
    private String password;    // 用户密码
    @Column(length = 128)
    private String email;       // 用户email
    @Column(length = 32)
    private String mobile;       // 用户手机号
	@Column(length = 255)
	private String description; // 用户备注描述
	@Column(length = 255)
	private String avatar; // 用户头像url地址

    @Transient
    private String newPassword; // 新密码

	private List<String> roles; // 用户包含色权限列表, 后台是权限, 前端做当roles处理

    private List<Integer> rids; // 用户所属角色id列表
	
	public SysUser() {
		super();
	}
	
	public SysUser(Integer id, String username, String password, String description) {
		super();
		super.setId(id);
		this.username = username;
		this.password = password;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Integer> getRids() {
        return rids;
    }

    public void setRids(List<Integer> rids) {
        this.rids = rids;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
