package br.com.cadmea.model.orm;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import br.com.cadmea.comuns.orm.enums.Gender;
import br.com.cadmea.comuns.orm.enums.Relationship;
import br.com.cadmea.model.BaseEntityPersistent;

/**
 * @author Gilberto Santos
 *
 */
@Entity
@Table(name = "cadmea_person_member")
@AttributeOverrides(@AttributeOverride(name = "id", column = @Column(name = "pes_id", nullable = false)))
public class Person extends BaseEntityPersistent {

	/**
	 *
	 */
	private static final long serialVersionUID = 6754653476445927880L;
	// can't have whitespace
	@Column(nullable = false, length = 35, name = "pes_name")
	private String name;

	@Column(nullable = true, length = 250, name = "pes_surname")
	private String surname;

	@NotNull
	@Column(nullable = false, length = 1, name = "pes_gender")
	@Enumerated(EnumType.ORDINAL)
	private Gender gender;

	@Column(nullable = true, length = 1, name = "pes_relationship")
	@Enumerated(EnumType.ORDINAL)
	private Relationship relationship;

	@NotNull
	@Column(nullable = false, name = "pes_date_of_birth")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateOfBirth;

	@OneToOne
	private Country country;
	
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegister() {
		return surname;
	}

	public void setRegister(String register) {
		this.surname = register;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Relationship getRelationship() {
		return relationship;
	}

	public void setRelationship(Relationship relationship) {
		this.relationship = relationship;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

}
