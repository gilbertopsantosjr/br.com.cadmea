package br.com.cadmea.comuns.test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import net.vidageek.mirror.dsl.Mirror;
import br.com.cadmea.comuns.orm.EntityPersistent;
import br.com.cadmea.comuns.orm.enums.CenariosDeViolacao;

public class AssUsers {

	/**
	 * Uma coleção de instancias para a classe <Entidade> com valores
	 * irregulares para realização de testes
	 * 
	 * @param clazz
	 * @return Collection<T>
	 * @throws ClassNotFoundException
	 */
	public static <T extends EntityPersistent> Collection<T> paraClasse(Class<T> clazz) {
		Collection<T> entidades = new ArrayList<T>();
		// obtem todos os atributos de Entidade
		List<Field> atributos = new Mirror().on(clazz).reflectAll().fields();
		// para cada tipo de violacao, uma nova entidade
		Collection<CenariosDeViolacao> cenariosDeTextos = Arrays
				.asList(CenariosDeViolacao.values());

		try {
			for (CenariosDeViolacao violacao : cenariosDeTextos) {
				T entidade = clazz.newInstance();
				for (Field field : atributos) {
					if (field.getType().isAssignableFrom(String.class)) {
						// seta um valor irregular para cada atributo encontrado
						new Mirror().on(entidade).set().field(field)
								.withValue(violacao.getDescription());
					}
				}
				entidades.add(entidade);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();

		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return entidades;
	}

}
