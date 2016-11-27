package br.com.cadmea.spring.security;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.cadmea.model.orm.Permission;
import br.com.cadmea.spring.security.orm.UserAccess;

@Lazy
@Component
@PropertySource(value = "classpath:cadmea.properties",
    ignoreResourceNotFound = true)
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private DataSource dataSource;

  @Value("${cadmea.users.sql.loginByUsername:SELECT u.usu_id as id, u.usu_pwd as password, u.usu_email as email, u.usu_nickname as nickname, u.usu_situation FROM cadmea_user_system u WHERE u.usu_email = ? and u.usu_situation = 1}")
  private String sqlUser;

  @Value("${cadmea.users.sql.rolesByUser:SELECT p.role as per_role_nome FROM cadmea_permissions_per_user ppu INNER JOIN cadmea_user_permission p ON ppu.permission_id = p.per_id WHERE ppu.user_id = ?}")
  private String sqlRoles;

  @Override
  public UserDetails loadUserByUsername(String email)
      throws UsernameNotFoundException {

    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

    UserAccess userAccess = null;

    class UserRowMapper implements RowMapper<UserAccess> {
      @Override
      public UserAccess mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserAccess found = new UserAccess(rs.getString("nickname"));
        found.setId(rs.getLong("id"));
        found.setPassword(rs.getString("password"));
        return found;
      }
    }

    userAccess = jdbcTemplate.queryForObject(sqlUser, new Object[] { email },
        new UserRowMapper());

    if (userAccess == null) {
      throw new UsernameNotFoundException(
          "User with email \"" + email + "\" was not found");
    }

    List<Map<String, Object>> rows = jdbcTemplate.queryForList(sqlRoles,
        new Object[] { userAccess.getId() });

    for (Map<String, Object> row : rows) {
      Permission permission = new Permission();
      permission.setId((Long) row.get("per_id"));
      permission.setRole((String) row.get("per_role_nome"));
      userAccess.addRole(permission.getRole());
    }

    return userAccess;
  }

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
    return new PropertySourcesPlaceholderConfigurer();
  }

}
