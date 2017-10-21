package br.com.cadmea.model.orm;

import br.com.cadmea.comuns.orm.enums.Situation;
import br.com.cadmea.model.BaseEntityPersistent;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@Table(name = "user_system")
@AttributeOverrides(@AttributeOverride(name = "id", column = @Column(name = "usu_id", nullable = false)))
@NamedQueries({
        @NamedQuery(name = "UserSystem.loginByUsername", query = "SELECT u.id, u.email, u.nickname, p.name FROM UserSystem as u INNER JOIN u.person as p INNER JOIN u.systems sys WHERE u.email = :email and u.situation = 1 "),
        @NamedQuery(name = "UserSystem.loginByUsernameAndSystem", query = "SELECT u.id, u.email, u.nickname, p.name FROM UserSystem as u INNER JOIN u.person as p INNER JOIN u.systems sys WHERE u.email = :email and u.situation = 1 and sys.id = :sysId ")
})
@EqualsAndHashCode(callSuper = false)
public class UserSystem extends BaseEntityPersistent {


    /**
     * this would be used for a public profile like
     * http://api.cadmea.com/profile/{nickname}
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
    @Size(min = 6, message = "{userSystem.password.min.size}")
    @Column(name = "usu_pwd", nullable = true, length = 150)
    private String password;

    @Column(name = "usu_dt_register", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateRegister;

    @Column(name = "usu_dt_expired", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date dateExpire;

    @Column(nullable = false, name = "usu_last_visit")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastVisit;

    @Column(nullable = false, length = 1, name = "usu_situation")
    @Enumerated(EnumType.ORDINAL)
    private Situation situation;

    @Column(length = 1, name = "usu_remember")
    private boolean rememberMe;

    @Column(nullable = false, length = 1, name = "usu_terms")
    private boolean readTerms;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST,
            CascadeType.REMOVE}, fetch = FetchType.LAZY, targetEntity = Permission.class)
    @JoinTable(name = "cadmea_permissions_per_user", joinColumns = {
            @JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "per_id")})
    private Set<Permission> permissions = Collections.EMPTY_SET;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY, targetEntity = CadmeaSystem.class)
    @JoinTable(name = "cadmea_systems_per_user", joinColumns = {
            @JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "system_id")})
    private Set<CadmeaSystem> systems = Collections.EMPTY_SET;

    @JsonIgnore
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY, targetEntity = SocialNetwork.class)
    @JoinTable(name = "cadmea_social_per_user", joinColumns = {
            @JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "system_id")})
    private Set<SocialNetwork> socialNetworks = Collections.EMPTY_SET;

    @JsonIgnore
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "pes_id", referencedColumnName = "pes_id", nullable = false)
    private Person person;


}
