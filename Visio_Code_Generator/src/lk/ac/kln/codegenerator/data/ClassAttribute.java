package lk.ac.kln.codegenerator.data;

/**
 * The class ClassAttribute is extend from
 * CodeGeneratorAttri and has additional methods 
 * get and set variable visibility
 * @author Malinda Ranasinghe
 * @date 01.06.2006
 */

public class ClassAttribute extends CodeGeneratorAttri {

	private String visibility;
	
	/**
	 * method for set variable Visibility
	 * @param visibility
	 */	
	public void setVisibility(String visibility){
		
		this.visibility = visibility;
	}
	
	/**
	 * method for get varible Visibility
	 * @return visibility
	 */
	public String getVisibility(){
		
		return visibility;
	}
}
