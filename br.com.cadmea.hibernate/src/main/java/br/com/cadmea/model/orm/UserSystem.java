package br.com.cadmea.model.orm;

import java.util.Date;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.cadmea.comuns.orm.enums.Situation;
import br.com.cadmea.model.BaseEntityPersistent;

@Entity
@Table(name = "cadmea_user_system")
@AttributeOverrides(@AttributeOverride(name = "id",
    column = @Column(name = "usu_id", nullable = false)))
public class UserSystem extends BaseEntityPersistent {

  /**
   *
   */
  private static final long serialVersionUID = -8331133681470459544L;

  @NotNull
  @NotEmpty
  @Column(name = "usu_username", nullable = false, length = 150, unique = true)
  private String username;

  @NotNull
  @NotEmpty
  @Column(name = "usu_email", nullable = false, length = 250, unique = true)
  private String email;

  @NotNull
  @NotEmpty
  @Column(name = "usu_pwd", nullable = true, length = 70)
  private String password;

  @NotNull
  @Column(name = "usu_dt_register", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date dateRegister;

  @Column(name = "usu_dt_expired", nullable = true)
  @Temporal(TemporalType.TIMESTAMP)
  private Date dateExpire;

  @Column(nullable = false, name = "usu_last_visit")
  @Temporal(TemporalType.TIMESTAMP)
  private Date lastVisit;

  @Column(nullable = false, length = 1, name = "usu_situation")
  @Enumerated(EnumType.ORDINAL)
  private Situation situation;

  @ManyToMany(fetch = FetchType.LAZY, targetEntity = Permission.class)
  @JoinTable(name = "cadmea_permissions_per_user",
      joinColumns = { @JoinColumn(name = "user_id") },
      inverseJoinColumns = { @JoinColumn(name = "permission_id") })
  private Set<Permission> permissions;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
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

}
