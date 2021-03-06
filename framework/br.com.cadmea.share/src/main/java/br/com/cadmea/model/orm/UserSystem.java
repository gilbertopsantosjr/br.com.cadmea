package br.com.cadmea.model.orm;

import br.com.cadmea.comuns.orm.enums.Situation;
import br.com.cadmea.dto.usersystem.UserSystemMessages;
import br.com.cadmea.model.BaseEntityPersistent;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Entity
@Data
@Table(name = "user_system")
@AttributeOverrides(@AttributeOverride(name = "id", column = @Column(name = "usu_id", nullable = false)))
@NamedQueries({
        @NamedQuery(name = "UserSystem.findByUsername", query = "SELECT new UserSystem( u.id, u.email, u.nickname, p.name) FROM UserSystem as u INNER JOIN u.person as p INNER JOIN u.systems sys WHERE u.email = :email and u.situation = 1 "),
        @NamedQuery(name = "UserSystem.findByUsernameAndSystem", query = "SELECT new UserSystem( u.id, u.email, u.nickname, p.name) FROM UserSystem as u INNER JOIN u.person as p INNER JOIN u.systems sys WHERE u.email = :email and u.situation = 1 and sys.id = :sysId ")
})
@EqualsAndHashCode(callSuper = false)
public class UserSystem extends BaseEntityPersistent {

    public static final int MIM_LENGTH_PASSWORD = 6;

    public UserSystem(){
        super();
    }

    public UserSystem(final Long id, final String email, final String nickname, final String name ){
        this.setId(id);
        this.setEmail(email);
        this.setNickname(nickname);
        this.setPerson( new Person(name) );
    }

    /**
     * this would be used for a public profile like
     * http://api.cadmea.com/profile/{nickname}
     * can't have special chars
     * can't have numbers
     * can't have whitespaces
     */
    @NotNull
    @Size(min = 3, max = 150)
    @Column(name = "usu_nickname", nullable = false, length = 150, unique = true)
    private String nickname;

    /**
     * unique and default email would be used to authentication
     */
    @NotNull
    @Size(min = 20, max = 250)
    @Column(name = "usu_email", nullable = false, length = 250, unique = true)
    private String email;

    /**
     * unique and default password would be used to authentication
     */
    @NotNull
    @Size(min = MIM_LENGTH_PASSWORD, message = UserSystemMessages.USER_SYSTEM_PASSWORD_MIN_SIZE)
    @Column(name = "usu_pwd", length = 150)
    private String password;

    @Column(name = "usu_dt_register", nullable = false)
    private LocalDateTime dateRegister;

    @Column(name = "usu_dt_expired")
    private LocalDateTime dateExpire;

    @Column(nullable = false, name = "usu_last_visit")
    private LocalDateTime lastVisit;

    @Column(nullable = false, length = 1, name = "usu_situation")
    @Enumerated(EnumType.ORDINAL)
    private Situation situation;

    @Column(length = 1, name = "usu_remember")
    private boolean rememberMe;

    @Column(nullable = false, length = 1, name = "usu_terms")
    private boolean readTerms;

    @Column(length = 5, name = "usu_favorite_language")
    private String favoriteLanguage;

    @ManyToMany(cascade = {CascadeType.PERSIST,
            CascadeType.REMOVE}, fetch = FetchType.LAZY, targetEntity = Role.class)
    @JoinTable(name = "cadmea_permissions_per_user", joinColumns = {
            @JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "per_id")})
    private List<Role> roles = Collections.EMPTY_LIST;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, targetEntity = CadmeaSystem.class)
    @JoinTable(name = "cadmea_systems_per_user", joinColumns = {
            @JoinColumn(name = "usu_id")}, inverseJoinColumns = {@JoinColumn(name = "sys_id")})
    private List<CadmeaSystem> systems = Collections.EMPTY_LIST;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY, targetEntity = SocialNetwork.class)
    @JoinTable(name = "cadmea_social_per_user", joinColumns = {
            @JoinColumn(name = "usu_id")}, inverseJoinColumns = {@JoinColumn(name = "sys_id")})
    private List<SocialNetwork> socialNetworks = Collections.EMPTY_LIST;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "pes_id", referencedColumnName = "pes_id", nullable = false)
    private Person person;


}
