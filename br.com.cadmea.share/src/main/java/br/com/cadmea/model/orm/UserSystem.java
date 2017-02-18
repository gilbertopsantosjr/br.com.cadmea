package br.com.cadmea.model.orm;

import java.util.Date;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.cadmea.comuns.orm.enums.Situation;
import br.com.cadmea.model.BaseEntityPersistent;

@Entity
@Table(name = "cadmea_user_system")
@AttributeOverrides(@AttributeOverride(name = "id",
    column = @Column(name = "usu_id", nullable = false)))
@NamedQuery(name="UserSystem.loginByUsername", query="SELECT u.id, u.email, u.nickname, p.name FROM UserSystem as u INNER JOIN u.person as p WHERE u.email = :email and u.situation = 1") 
public class UserSystem extends BaseEntityPersistent {

  /**
   *
   */
  private static final long serialVersionUID = -8331133681470459544L;

  @NotNull
  @Size(min=3, max=150)
  @Column(name = "usu_nickname", nullable = false, length = 150)
  private String nickname;

  @NotNull
  @Size(min=20, max=250)
  @Column(name = "usu_email", nullable = false, length = 250, unique = true)
  private String email;

  @NotNull
  @Size(min=6, message="{userSystem.password.min.size}")
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

  @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE },
      fetch = FetchType.EAGER, targetEntity = Permission.class)
  @JoinTable(name = "cadmea_permissions_per_user",
      joinColumns = { @JoinColumn(name = "user_id") },
      inverseJoinColumns = { @JoinColumn(name = "permission_id") })
  private Set<Permission> permissions;

  @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE },
      fetch = FetchType.LAZY)
  @JoinColumn(name = "pes_id", referencedColumnName = "pes_id",
      nullable = false)
  @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
  private Person person;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String pwd) {
    this.password = pwd;
  }

  public Date getDateRegister() {
    return dateRegister;
  }

  public void setDateRegister(Date dateRegister) {
    this.dateRegister = dateRegister;
  }

  public Date getDateExpire() {
    return dateExpire;
  }

  public void setDateExpire(Date dateExpire) {
    this.dateExpire = dateExpire;
  }

  public Date getLastVisit() {
    return lastVisit;
  }

  public void setLastVisit(Date lastVisit) {
    this.lastVisit = lastVisit;
  }

  public Situation getSituation() {
    return situation;
  }

  public void setSituation(Situation situation) {
    this.situation = situation;
  }

  public Set<Permission> getPermissions() {
    return permissions;
  }

  public void setPermissions(Set<Permission> permissions) {
    this.permissions = permissions;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

}
