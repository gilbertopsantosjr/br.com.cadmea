package br.com.cadmea.web;

import java.util.Date;
import java.util.Set;

import br.com.cadmea.comuns.orm.BaseEntity;
import br.com.cadmea.comuns.orm.enums.Situation;

@SuppressWarnings("serial")
public class UserBean extends BaseEntity {

	private String nickname;
	private String password;
	private String email;
	private Date dateRegister;
	private Date dateExpire;
	private Date lastVisit;
	private Situation situation;
	private Set<PermissionBean> permissions;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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

	public Date getDateRegister() {
		return dateRegister;
	}

	public void setDateRegister(Date dateRegister) {
		this.dateRegister = dateRegister;
	}

	public Date getDateExpire() {
		return dateExpire;
	}

	public void setDateExpire(Date dateExpire) {
		this.dateExpire = dateExpire;
	}

	public Date getLastVisit() {
		return lastVisit;
	}

	public void setLastVisit(Date lastVisit) {
		this.lastVisit = lastVisit;
	}

	public Situation getSituation() {
		return situation;
	}

	public void setSituation(Situation situation) {
		this.situation = situation;
	}

	public Set<PermissionBean> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<PermissionBean> permissions) {
		this.permissions = permissions;
	}

}
