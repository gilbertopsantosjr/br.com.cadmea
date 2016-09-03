/**
 * 
 */
package br.com.cadmea.spring.security.orm;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.cadmea.comuns.orm.Acessivel;

/**
 * @author Gilberto Santos
 * 
 */
public class UserAccess implements UserDetails, Acessivel, HttpSessionBindingListener {

	private static Logger logger = Logger.getLogger(UserAccess.class);
	private static final long serialVersionUID = 1L;
	private Long id;
	private String username;
	private String password;
	private String nickname;
	private String urlUserAccess;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

	public String getUrlUserAccess() {
		return urlUserAccess;
	}

	public void setUrlUserAcess(String urlUserAccess) {
		this.urlUserAccess = urlUserAccess;
	}
	
	private final SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");

	@Override
	public void valueBound(HttpSessionBindingEvent arg0) {
		logger.info("user :" + this.getNickname() + " as log ing " + sdf.format(new Date()));
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent arg0) {
		logger.info("user :" + this.getNickname() + " as log out " + sdf.format(new Date()));
	}
	
	

}
