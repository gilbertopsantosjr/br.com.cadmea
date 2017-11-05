package br.com.cadmea.spring.security;

import br.com.cadmea.model.orm.Permission;
import br.com.cadmea.spring.pojos.UserAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Lazy
@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private DataSource dataSource;

    @Value("${cadmea.users.sql.loginByUsername:SELECT u.usu_id as id, u.usu_pwd as password, u.usu_email as email, u.usu_nickname as nickname, u.usu_situation, u.usu_favorite_language as flocale FROM user_system u INNER_JOIN System sys ON sys.sys_id = u.user_id WHERE u.usu_email = ? and u.usu_situation = 1 and sys.sys_id = ? }")
    private String sqlUser;

    @Value("${cadmea.users.sql.rolesByUser:SELECT p.role as per_role_nome FROM cadmea_permissions_per_user ppu INNER JOIN cadmea_user_permission p ON ppu.permission_id = p.per_id WHERE ppu.user_id = ?}")
    private String sqlRoles;

    @Override
    public UserDetails loadUserByUsername(final String email)
            throws UsernameNotFoundException {

        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        UserAccess userAccess = null;

        class UserRowMapper implements RowMapper<UserAccess> {
            @Override
            public UserAccess mapRow(final ResultSet rs, final int rowNum) throws SQLException {
                final UserAccess found = new UserAccess(rs.getString("nickname"));
                found.setId(rs.getLong("id"));
                found.setPassword(rs.getString("password"));
                Locale locale = Locale.UK;
                if (null != rs.getString("flocale") && !rs.getString("flocale").isEmpty()) {
                    locale = new Locale(rs.getString("flocale"));
                }
                found.setLocale(locale);
                return found;
            }
        }

        userAccess = jdbcTemplate.queryForObject(sqlUser, new Object[]{email},
                new UserRowMapper());

        if (userAccess == null) {
            throw new UsernameNotFoundException(
                    "User with email \"" + email + "\" was not found");
        }

        final List<Map<String, Object>> rows = jdbcTemplate.queryForList(sqlRoles,
                new Object[]{userAccess.getId()});

        for (final Map<String, Object> row : rows) {
            final Permission permission = new Permission();
            permission.setId((Long) row.get("per_id"));
            permission.setRole((String) row.get("per_role_nome"));
            userAccess.addRole(permission.getRole());
        }

        return userAccess;
    }


}
