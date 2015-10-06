/**
 * 
 */
package br.com.cadmea.model.orm;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

import br.com.cadmea.comuns.orm.Entidade;
import br.com.cadmea.comuns.util.Util;

/**
 * @author Gilberto Santos
 * 
 */
@MappedSuperclass
public abstract class BaseEntidade implements Entidade {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name="kaugen" , strategy="increment")
	@GeneratedValue(generator="kaugen")
	private Long id;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final BaseEntidade other = (BaseEntidade) obj;
		if ((this.getId() == null) ? (other.getId() != null) : !this.getId()
				.equals(other.getId())) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		long hash = Long.parseLong(Util.generatorNumericCode(1));
		hash = 53 * hash + (this.getId() != null ? this.getId().hashCode() : 0);
		return Integer.parseInt(hash + "");
	}

}
