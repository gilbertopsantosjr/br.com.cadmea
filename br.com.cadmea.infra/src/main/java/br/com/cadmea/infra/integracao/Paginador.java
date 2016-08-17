/**
 * 
 */
package br.com.cadmea.infra.integracao;

import java.util.Collection;

/**
 * @author gilberto
 * 
 */
public interface Paginador {

    int obterQuantidadeTotal();

    Collection<? extends Paginavel> obterPaginado(int de, int ate);

}
