/**
 * 
 */
package br.com.cadmea.comuns.test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gilberto Santos
 * http://www.gilbertosantos.com 
 */
public class Rule {
	
	private List<Scene> scenes;
	
	public Rule add(Scene scene){
		if(scenes == null)
			scenes = new ArrayList<Scene>();
		scenes.add(scene);
		return this;
	}

	/**
	 * @return the scenes
	 */
	public List<Scene> getScenes() {
		return scenes;
	}

	/**
	 * @param scenes the scenes to set
	 */
	public void setScenes(List<Scene> scenes) {
		this.scenes = scenes;
	}

	
	
}
