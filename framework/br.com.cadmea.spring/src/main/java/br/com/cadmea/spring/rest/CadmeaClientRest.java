package br.com.cadmea.spring.rest;

import br.com.cadmea.comuns.clazz.ProjectStage;
import br.com.cadmea.model.orm.UserSystem;
import br.com.cadmea.spring.util.SpringFunctions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

//http://www.baeldung.com/how-to-use-resttemplate-with-basic-authentication-in-spring
@Component
public class CadmeaClientRest {

    private RestTemplate restTemplate;

    private final String CADMEA_USER = "cadmeaSystemAdmin";
    private final String CADMEA_PASSWORD = "$2a$08$ZlQuc3MkNSWvTh5PdjHZ2uVB34LiSMaI6NT8VI5a8k1PXmtyqxiaq";

    @Value("${spring.profiles.active}")
    private String profile;

    private int port;

    private final String URL_SERVER = "http://" + profile;
    private final String URL_GET_USER_BY_ID = URL_SERVER + ":" + port + "/cadmea/api/private/user/load/{id}";

    @PostConstruct
    public void init() {
        switch (profile) {
            case ProjectStage.PRODUCTION:
                port = 80;
                break;
            default:
                port = 8080;
                break;
        }
    }

    /**
     * obtain an UserSystem from Cadmea services
     *
     * @param id
     * @return UserSystem
     */
    public UserSystem getCadmeaUserSystemBy(final @NotNull Long id) {
        final HttpHeaders httpHeaders = SpringFunctions.createHeaders(CADMEA_USER, CADMEA_PASSWORD);
        final HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL_GET_USER_BY_ID).queryParam("id", id);
        final ResponseEntity<UserSystem> userSystemResponseEntity = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, httpEntity, UserSystem.class);
        return userSystemResponseEntity.getBody();
    }


}
