/**
 * 
 */
package br.com.cadmea.jsf.uteis;

import java.io.IOException;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.faces.context.FacesContext;

import br.com.cadmea.comuns.util.Util;

/**
 * @author Gilberto Santos
 */
public class BaseFunctions {
	
	@SuppressWarnings("rawtypes")
    public static int lengthOfCollection(Collection list){
		return (list != null && list.size() > 0) ? list.size() : 0 ; 
	}

	
	/**
	 * @param pattern dd/mm/yyyy
	 * @return return current date String format
	 */
	public static String getCurrentDate(String pattern){
		return Util.getCurrentDate(pattern);
	}
	
	public static List<String> returnList(String _token){
		List<String> list = new ArrayList<String>();
		if(_token.contains(",")){
			String [] s = _token.split(",");
			for (String item : s) {
				list.add(item);
			}
		} else 
			throw new RuntimeException("The string need exist a divisor ',' ");
		return list;
	}
	
	/**
	 * 
	 * @param _string
	 * @return
	 */
	public static Map<String, String> returnMap(String _string){
		Map<String, String> map = new HashMap<String, String>();
		if(_string.contains(",")){
			String [] s = _string.split(",");
			for (String string : s) {
				if(!string.contains(":"))
					throw new RuntimeException("The string need exist a divisor ':' ");
				String [] passThrough = string.split(":");
				map.put(passThrough[0], passThrough[1]);
			}
		}
		return map;
	}
	
	/**
	 * retira os caracteres especiais da String
	 * @param s
	 * @return
	 */
	public static String normalize(String s) {
	    String temp = Normalizer.normalize(s, java.text.Normalizer.Form.NFD);
	    return temp.replaceAll("[^\\p{ASCII}]","");
	}
	/**
	 * prepara um Id para os componentes da view
	 * @param id
	 * @return String preparada
	 */
	public static String preparedId(String id){
		return normalize( replaceString(id, " ", "_") ).toLowerCase();
	}
	
	/**
	 * substitui um trecho da String por outro
	 * @param old
	 * @param sub
	 * @param pattern
	 * @return String preparada
	 */
	public static String replaceString(String old, String sub, String pattern){
		return old.replace(sub, pattern);
	}
	
	/**
	 * limista a exibição da string em uma certa quantidade de caracteres
	 * @param string
	 * @param inicio
	 * @param fim
	 * @return
	 */
	public static String trunkString(String string, int inicio, int fim) {
		try {
			if (string.length() > fim) {
				return string.substring(inicio, fim).concat("..");
			}
			return string;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 
	 * @param string
	 * @return the string in lowerCase 
	 */
	public static String lowerCase(String string){
		return string.toLowerCase();
	}
	
	/**
	 * root of applications
	 * @return
	 */
	public static String contextPath(){
		return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
	}
	
	public static String getPropertiesDoSistema(String chave){
		String valor = "";
		try {
			//TODO implementar internacionalizacao
			Properties prop = new Properties();
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		    prop.load(classLoader.getResourceAsStream("messages_pt_BR.properties"));
			valor = prop.getProperty(chave);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return valor;
	}
	
}
