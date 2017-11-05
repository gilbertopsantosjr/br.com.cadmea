/**
 *
 */
package br.com.cadmea.spring.util;

import br.com.cadmea.spring.pojos.UserAccess;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author Gilberto Santos
 */
public final class SpringFunctions {

    /**
     * create basic authorization request
     *
     * @param username
     * @param password
     * @return HttpHeaders
     */
    public static HttpHeaders createHeaders(final String username, final String password) {
        return new HttpHeaders() {
            {
                final String auth = username + ":" + password;
                final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
                final String authHeader = "Basic " + new String(encodedAuth);
                set("Authorization", authHeader);
                setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            }
        };
    }

    /**
     * @return UsuarioAcesso
     */
    public static UserAccess getCurrentUser() {
        final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserAccess) {
            return ((UserAccess) principal);
        }
        return null;
    }

    /**
     * @return
     */
    public static String getNameOfUser() {
        return ((UserAccess) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    /**
     * verifica varias permissoes para o usuario que possui permissao de acesso
     *
     * @return Boolean verdadeiro se o perfil do usuario logado permitir
     * visualizar o conteudo
     */
    public static Boolean isUserHasPerfil() {
        final Collection<?> regras = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        final Collection<Boolean> permissoes = new HashSet<Boolean>();
        for (final Object role : regras) {
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
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && !(authentication instanceof AnonymousAuthenticationToken)
                && authentication.isAuthenticated();
    }

    /**
     * verifica permissao <strong> especifica </strong> para o usuario
     *
     * @param role
     * @return verdadeiro se o perfil do usuario logado permitir visualizar o
     * conteudo
     */
    public static Boolean isAuthorized(final String role) {
        boolean condicao = false;
        final HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
                .getRequest();
        final SecurityContextHolderAwareRequestWrapper sc = new SecurityContextHolderAwareRequestWrapper(req, "");
        final String[] roles = role.contains(",") ? role.split(",") : new String[]{role};
        for (int i = 0; i < roles.length; i++) {
            condicao = sc.isUserInRole(roles[i].trim());
        }
        return condicao;
    }

}
