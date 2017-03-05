package br.com.cadmea.model.orm;

import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.cadmea.model.BaseEntityPersistent;

@Entity
@Table(name = "cadmea_system")
@AttributeOverrides(@AttributeOverride(name = "id", column = @Column(name = "usu_id", nullable = false)))
public class CadmeaSystem extends BaseEntityPersistent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(min = 3, max = 50)
	@Column(name = "sys_identity", nullable = false, length = 150, unique = true)
	private String identity;
	
	@NotNull
	@Size(min = 3, max = 150)
	@Column(name = "sys_name", nullable = false, length = 150)
	private String name;

	@Column(name = "sys_description", nullable = true)
	private String description;
	
	/* this would be the url the login system should call after validate the login */
	@Column(name = "sys_url", nullable = false, length= 150)
	private String ulr;

	@ManyToMany(targetEntity = UserSystem.class, mappedBy = "systems")
	@JsonIgnore
	private Set<UserSystem> users;
	
	
	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getUlr() {
		return ulr;
	}

	public void setUlr(String ulr) {
		this.ulr = ulr;
	}

	public Set<UserSystem> getUsers() {
		return users;
	}

	public void setUsers(Set<UserSystem> users) {
		this.users = users;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
