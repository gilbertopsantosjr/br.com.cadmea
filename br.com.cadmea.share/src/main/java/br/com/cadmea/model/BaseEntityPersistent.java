/**
 *
 */
package br.com.cadmea.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;

import br.com.cadmea.comuns.orm.EntityPersistent;

/**
 * @author Gilberto Santos
 *
 */
@MappedSuperclass
public abstract class BaseEntityPersistent implements EntityPersistent {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "native_generator", strategy = "native")
	@GeneratedValue(generator = "native_generator")
	private Long id;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseEntityPersistent other = (BaseEntityPersistent) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
