package br.com.cadmea.model.orm;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.cadmea.model.BaseEntityPersistent;

/**
 * @author Gilberto Santos
 *
 */
@Entity
@Table(name = "social_network")
@AttributeOverrides(@AttributeOverride(name = "id", column = @Column(name = "soc_id", nullable = false)))
public class SocialNetwork extends BaseEntityPersistent {

	/**
	 *
	 */
	private static final long serialVersionUID = -2679869157137625329L;

	@NotNull
	@Column(name = "id_network", nullable = false)
	private String idNetwork;

	@NotNull
	@Column(name = "primary_contact", nullable = false)
	private String primaryContact;

	@Column(name = "link", nullable = true)
	private String link;
	
	@Column(name = "picture_profile", nullable = true)
	private String pictureProfile;
	
	@Column(name = "type", nullable = true)
	private TypeSocial type;

	@NotNull
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, fetch = FetchType.LAZY, targetEntity = UserSystem.class)
	private UserSystem userSystem;
	
	static enum TypeSocial {
		FACEBOOK(1),GOOGLE(2);
		int type = 0;
		TypeSocial(int i){
			this.type = i;
		}
	}
	
	public SocialNetwork() {
		super();
	}
	
	public TypeSocial getType() {
		return type;
	}

	public void setType(TypeSocial type) {
		this.type = type;
	}

	public String getPictureProfile() {
		return pictureProfile;
	}

	public void setPictureProfile(String pictureProfile) {
		this.pictureProfile = pictureProfile;
	}



	public String getIdNetwork() {
		return idNetwork;
	}

	public void setIdNetwork(String idNetwork) {
		this.idNetwork = idNetwork;
	}

	public String getPrimaryContact() {
		return primaryContact;
	}

	public void setPrimaryContact(String primaryContact) {
		this.primaryContact = primaryContact;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public UserSystem getUserSystem() {
		return userSystem;
	}

	public void setUserSystem(UserSystem userSystem) {
		this.userSystem = userSystem;
	}

}
