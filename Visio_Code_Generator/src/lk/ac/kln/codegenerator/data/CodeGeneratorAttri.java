package lk.ac.kln.codegenerator.data;

/**
 * The CodeGeneratorAttri class for 
 * get and set variable name and type
 * @author Malinda Ranasinghe
 * @date 01.06.2010
 */

public class CodeGeneratorAttri {

	protected String name;
	protected String type;
	
	
	/**
	 * method  for set variable name
	 * @param name
	 */	
	public void setName(String name){
		
		this.name = name;
	}
	
	/**
	 * method for set variable data type
	 * @param type
	 */
	public void setDataType(String type){
		
		this.type = type;
	}
	
	
	/**
	 * method for get variable name
	 * @return  name
	 */
	public String getName(){
		
		return name;
	}
	
	/**
	 * method for get variable type
	 * @return type
	 */
	public String getType(){
		
		return type;
	}
}
