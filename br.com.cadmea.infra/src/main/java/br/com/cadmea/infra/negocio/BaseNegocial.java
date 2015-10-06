package br.com.cadmea.infra.negocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import br.com.cadmea.comuns.excecao.NoneExistException;
import net.sf.beanlib.hibernate3.Hibernate3DtoCopier;
import br.com.cadmea.comuns.excecao.JaExisteException;
import br.com.cadmea.comuns.orm.enums.Result;
import br.com.cadmea.model.dao.DaoGenerico;
import br.com.cadmea.model.orm.BaseEntidade;

/**
 * Classe responsavel pelas regras de negocio da entidade manipulada pela classe concreta 
 * e com algumas operacaoes de persistencia ja definidas.
 * 
 * @version 1.0
 * @param <E>
 *            Entidade que sera manipulada
 */
public abstract class BaseNegocial<E extends BaseEntidade> implements Negocial<E>  {
	
	private final Hibernate3DtoCopier copier = new Hibernate3DtoCopier();

    /**
     * Construtor protegido para rentringir a criacao desta classe somente a subclasses.
     */
    protected BaseNegocial() {
        // Construtor.
    }

    public Hibernate3DtoCopier getCopier() {
		return copier;
	}

	/**
     * Retorna instancia de DAO.
     * 
     * @return O valor do atributo dao
     */
    protected abstract DaoGenerico<E> getDao();

    /* (non-Javadoc)
	 * @see br.com.cadmea.infra.negocio.Negocial#find(java.io.Serializable)
	 */
    @Override
	public E find(Serializable identificador) {
        E entidade = getDao().find(identificador);
        if (entidade == null) {
            throw new NoneExistException();
        }
        return entidade;
    }
    
    /* (non-Javadoc)
	 * @see br.com.cadmea.infra.negocio.Negocial#insert(E)
	 */
	@Override
	public E insert(E entidade) {
        if (isThere(entidade))
            throw new JaExisteException();
        return getDao().save(entidade);
    }

	/* (non-Javadoc)
	 * @see br.com.cadmea.infra.negocio.Negocial#save(E)
	 */
	@Override
	public void save(E entidade) {
        getDao().save(entidade);
    }
	
	public void save(Collection<E> entidade) {
        getDao().save(entidade);
    }

	/* (non-Javadoc)
	 * @see br.com.cadmea.infra.negocio.Negocial#remove(E)
	 */
	@Override
	public void remove(E entidade) {
        E entity = find(entidade.getId());
        getDao().remove(entity);
    }

	/* (non-Javadoc)
	 * @see br.com.cadmea.infra.negocio.Negocial#remove(java.util.Collection)
	 */
	@Override
	public void remove(Collection<E> entidades) {
        Collection<E> removidas = new ArrayList<E>();
        Iterator<E> iterator = entidades.iterator();
        while (iterator.hasNext()) {
            E entidade = iterator.next();
            try {
                remove(entidade);
                removidas.add(entidade);
            } catch (Exception e) {
                continue;
            }
        }
        entidades.removeAll(removidas);
    }

    /* (non-Javadoc)
	 * @see br.com.cadmea.infra.negocio.Negocial#find(java.util.Map, br.com.cadmea.comuns.orm.enums.Result)
	 */
	@Override
	public E find(Map<String, Object> params, Result resl) {
        E entidade = getDao().find(params, resl);
        if (entidade == null) {
            throw new NoneExistException();
        }
        E copy = copier.hibernate2dto(entidade);
        return copy;
    }

   /* (non-Javadoc)
 * @see br.com.cadmea.infra.negocio.Negocial#find(java.util.Map)
 */
	@Override
	public Collection<E> find(Map<String, Object> params) {
        Collection<E> entidades = getDao().find(params);
        if (entidades == null) {
            throw new NoneExistException();
        }
        return entidades;
    }

	/* (non-Javadoc)
	 * @see br.com.cadmea.infra.negocio.Negocial#listAll()
	 */
	@Override
	public Collection<E> findAll() {
        return getDao().listAll();
    }

	/* (non-Javadoc)
	 * @see br.com.cadmea.infra.negocio.Negocial#listAll(int, int)
	 */
	@Override
	public Collection<E> findAll(int de, int ate) {
        return getDao().listAll(new HashMap<String, Object>(), de, ate);
    }
	
	/* (non-Javadoc)
	 * @see br.com.cadmea.infra.negocio.Negocial#listAll(java.util.Map, int, int)
	 */
	@Override
	public Collection<E> findAll(Map<String, Object> params, int de, int ate) {
        return getDao().listAll(params, de, ate);
    }

    /**
     * Obtem a descricao da entidade informada.
     * 
     * @param entidade
     *            Entidade a ser obtida a descricao
     * @return A descricao da entidade informada
     */
    protected String getDescricao(E entidade) {
        return entidade.getId().toString();
    }

    /**
     * Verifica se o registro informado ja existe.<br>
     * Obs. Este metodo deve ser sobrescrito quando houver a necessidade de validar 
     * se os dados informados ja existem e nao podem ser incluidos. Este metodo e chamado na inclusao e na alteracao.
     * 
     * @param entidade
     *            Objeto a ser verificado.
     * @return <code>true</code> caso o objeto ja exista e <code>false</code> caso contrario.
     */
    protected boolean isThere(E entidade) {
        boolean result = false;
        return result;
    }

}
