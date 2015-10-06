/**
 * 
 */
package br.com.cadmea.spring.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.cadmea.spring.security.orm.UserAccess;

/**
 * @author Gilberto Santos
 * 
 */
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private static Logger logger = Logger.getLogger(CustomAuthenticationProvider.class);

	private DataSource dataSource;

	static final String SQL_SELECT_USER = "SELECT u.usu_id as id, u.usu_email as email, u.usu_nick_name as nickname, "
			+ " u.usu_url_id as url_user_access FROM tb_user u "
			+ " WHERE u.usu_email = ? AND u.usu_pwd = ?; ";

	static final String SQL_SELECT_PERFIL = " SELECT u.usu_perfil as per_role_nome FROM tb_user u WHERE u.usu_id  = ?; ";

	protected String obterUsuario() {
		return SQL_SELECT_USER;
	}

	protected String obterPerfilDoUsuario() {
		return SQL_SELECT_PERFIL;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		try {
			String hashedPass = DigestUtils.md5Hex(authentication.getCredentials().toString().trim());

			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			UserAccess usuarioAcesso = jdbcTemplate.queryForObject(obterUsuario(), new Object[] { authentication.getName().trim(),
					hashedPass }, new RowMapper<UserAccess>() {
				@Override
				public UserAccess mapRow(ResultSet rs, int rowNum) throws SQLException {
					UserAccess found = new UserAccess();
					found.setId(rs.getLong("id"));
					found.setNickname(rs.getString("nickname"));
					found.setUrlUserAcess(rs.getString("url_user_access"));
					return found;
				}
			});
			
			if (usuarioAcesso == null)
				throw new AuthenticationServiceException("User not found !");

			logger.info("usuarioAcesso: " + usuarioAcesso.getNickname() + " found ");

			final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			final List<Map<String, Object>> rows = jdbcTemplate.queryForList(obterPerfilDoUsuario(),
					new Object[] { Integer.parseInt(usuarioAcesso.getId().toString()) });

			for (Map<String, Object> row : rows) {
				String authoritie = (String) row.get("per_role_nome");
				logger.info("authoritie found: " + authoritie);
				authorities.add(new SimpleGrantedAuthority(authoritie));
			}

			logger.info("authorities: " + rows.size() + " found ");

			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(usuarioAcesso, null, authorities);
			authToken.setDetails(usuarioAcesso);
			
			SecurityContextHolder.createEmptyContext();
			SecurityContextHolder.getContext().setAuthentication(authToken);
			
			return authToken;

		} catch (AuthenticationServiceException ase) {
			throw ase;

		} catch (Exception e) {
			//e.printStackTrace();
			logger.info("error while authentication: " + e.getCause() );
			return null;
		}
	}

	public boolean supports(Class<? extends Object> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	
}
