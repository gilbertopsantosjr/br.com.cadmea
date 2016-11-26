package br.com.cadmea.web.pojo;

import br.com.cadmea.comuns.orm.BaseEntity;

@SuppressWarnings("serial")
public class PermissionBean extends BaseEntity {

  private String role;

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

}
