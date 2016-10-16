/**
 *
 */
package br.com.cadmea.spring.util;

import java.util.Collection;
import java.util.HashSet;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;

import br.com.cadmea.spring.security.orm.UserAccess;

/**
 * @author Gilberto Santos
 *
 */
public final class SpringFunctions {

  /**
   * 
   * @return UsuarioAcesso
   */
  public static UserAccess getCurrentUser() {
    Object principal = SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();
    if (principal instanceof UserAccess)
      return ((UserAccess) principal);
    return null;
  }

  public static String getNameOfUser() {
    return ((UserAccess) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal()).getUsername();
  }

  /**
   * verifica varias permissoes para o usuario que possui permissao de acesso
   * 
   * @param roles
   * @return Boolean verdadeiro se o perfil do usuario logado permitir
   *         visualizar o conteudo
   */
  public static Boolean isUserHasPerfil() {
    Collection<?> regras = SecurityContextHolder.getContext()
        .getAuthentication().getAuthorities();
    Collection<Boolean> permissoes = new HashSet<Boolean>();
    for (Object role : regras) {
      permissoes.add(isAuthorized(String.valueOf(role)));
    }
    return permissoes.contains(Boolean.FALSE) ? Boolean.FALSE : Boolean.TRUE;
  }

  /**
   * verifica se usuario esta autenticado
   * 
   * @return verdadeiro caso usuario esteja autenticado
   */
  public static Boolean isAuthentication() {
    Authentication authentication = SecurityContextHolder.getContext()
        .getAuthentication();
    return authentication != null
        && !(authentication instanceof AnonymousAuthenticationToken)
        && authentication.isAuthenticated();
  }

  /**
   * verifica permissao <strong> especifica </strong> para o usuario
   * 
   * @param role
   * @return verdadeiro se o perfil do usuario logado permitir visualizar o
   *         conteudo
   */
  public static Boolean isAuthorized(String role) {
    boolean condicao = false;
    HttpServletRequest req = (HttpServletRequest) FacesContext
        .getCurrentInstance().getExternalContext().getRequest();
    SecurityContextHolderAwareRequestWrapper sc = new SecurityContextHolderAwareRequestWrapper(
        req, "");
    String[] roles = role.contains(",") ? role.split(",")
        : new String[] { role };
    for (int i = 0; i < roles.length; i++) {
      condicao = sc.isUserInRole(roles[i].trim());
    }
    return condicao;
  }

}
