/**
 *
 */
package br.com.cadmea.spring.pojos;

import br.com.cadmea.model.orm.Role;
import br.com.cadmea.model.orm.UserSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Gilberto Santos
 */
public class UserAccess extends UserSystem
        implements UserDetails, HttpSessionBindingListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Set<Role> roles;

    private Locale locale;

    public UserAccess(final String email, final String password,
                      final Set<Role> roles) {
        super();
        setEmail(email);
        setPassword(password);
        this.roles = roles;
    }

    public UserAccess(final String email, final String password) {
        super();
        setEmail(email);
        setPassword(password);
        roles = new HashSet<>();
    }

    public UserAccess(final String email) {
        super();
        setEmail(email);
        roles = new HashSet<>();
    }

    public void addRole(final Role role) {
        roles.add(role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final List<Role> roles = getRoles();

        if (roles == null) {
            return Collections.emptyList();
        }

        final Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

        for (final Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return authorities;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(final Locale locale) {
        this.locale = locale;
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

    @Override
    public List<Role> getRoles() {
        return new ArrayList(roles);
    }

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");

    @Override
    public void valueBound(final HttpSessionBindingEvent arg0) {
        logger.info("user logged :" + getUsername() + " as log ing "
                + sdf.format(new Date()));
    }

    @Override
    public void valueUnbound(final HttpSessionBindingEvent arg0) {
        logger.info("user logout :" + getUsername() + " as log out "
                + sdf.format(new Date()));
    }

    @Override
    public String getUsername() {
        return super.getEmail();
    }

}
