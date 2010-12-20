package lk.ac.kln.codegenerator.data;
import java.util.HashMap;
import java.util.Map;



public class DataStorage {
	
	private Map <String,CodeGeneratorClasses> classMap = new HashMap<String , CodeGeneratorClasses>();
	
	/**
	 * method to keep List of classes
	 * @param cName
	 * @param classList
	 */
	public void setClassMap(String cName, CodeGeneratorClasses classList) {
		
		this.classMap.put(cName, classList);
	
	}
	
	public Map<String, CodeGeneratorClasses> getClassMap(String clsId){
		
		return this.classMap;		
	}
	
	/**
	 * method for get class object
	 * @param id
	 * @return object->codeGeneratorClasses
	 */
	public CodeGeneratorClasses getClass(String id){
		
		return classMap.get(id);
		
	}
		
	/**
	 * 
	 * @return int->size
	 */
	public int  getNumOfclass() {
		if(classMap.isEmpty()){
			return 1;
		}else{
			return classMap.size();
		}
		
	}
	

	

	
	
}
