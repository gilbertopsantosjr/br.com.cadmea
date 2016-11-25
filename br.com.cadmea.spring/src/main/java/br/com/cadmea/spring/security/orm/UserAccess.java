/**
 *
 */
package br.com.cadmea.spring.security.orm;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.cadmea.model.orm.UserSystem;

/**
 * @author Gilberto Santos
 *
 */
public class UserAccess extends UserSystem
    implements UserDetails, HttpSessionBindingListener {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final Set<String> roles;

  public UserAccess(String username, String password, Set<String> roles) {
    super();
    setUsername(username);
    setPassword(password);
    this.roles = roles;
  }

  public UserAccess(String username, String password) {
    super();
    setUsername(username);
    setPassword(password);
    this.roles = new HashSet<>();
  }

  public UserAccess(String username) {
    super();
    setUsername(username);
    this.roles = new HashSet<>();
  }

  public void addRole(String role) {
    this.roles.add(role);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Set<String> roles = this.getRoles();

    if (roles == null) {
      return Collections.emptyList();
    }

    Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

    for (String role : roles) {
      authorities.add(new SimpleGrantedAuthority(role));
    }

    return authorities;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public Set<String> getRoles() {
    return roles;
  }

  private final SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");

  @Override
  public void valueBound(HttpSessionBindingEvent arg0) {
    logger.info("user :" + this.getUsername() + " as log ing "
        + sdf.format(new Date()));
  }

  @Override
  public void valueUnbound(HttpSessionBindingEvent arg0) {
    logger.info("user :" + this.getUsername() + " as log out "
        + sdf.format(new Date()));
  }

  @Override
  public String getUsername() {
    return super.getUsername();
  }

  @Override
  public String getPassword() {
    return super.getPassword();
  }

}
