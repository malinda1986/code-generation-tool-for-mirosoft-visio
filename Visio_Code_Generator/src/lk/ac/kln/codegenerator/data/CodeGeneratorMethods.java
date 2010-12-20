package lk.ac.kln.codegenerator.data;
import java.util.HashMap;
import java.util.Map;


/******************************************************
 * Purpose of this class it to set method information *
 * and provide getters to access those information    *
 * @author malinda                                    *
 ******************************************************/
public class CodeGeneratorMethods {
	
	
	
	private	String methodName = "";
	private String methodVisibility = "";
	private String methodReturnType ="";
	private String mainMethod = "";
	
	private Map <String,MethodAttribute> dataTypeMap = new HashMap<String , MethodAttribute>();
	
	/**
	 * method for set method name
	 * @param String->mName
	 */
	public void setMethodName(String mName){
		
		this.methodName = mName;
	}
	
	/**
	 * method for set method visibility
	 * @param String->mVisibility
	 */
	public void setMethodVisibity(String mVisibility){
		
		this.methodVisibility = mVisibility;
	}
	
	/**
	 * method for set method return type
	 * @param String->rType
	 */
	public void setMethodReturnType(String rType){
		
		this.methodReturnType = rType;
	}

	/**
	 * method for set methods parameters
	 * @param  String->name
	 * @param  MethodAttribute->obj
	 */
	public void setMethodParameter(String name,MethodAttribute obj){
		
		dataTypeMap.put(name, obj);
	
	}
	
	/**
	 * method for set 'is' main method 
	 * @param res
	 */
	public void setIsMain(String res){
		
		this.mainMethod = res;
	}
	
	
	////////////////////////////END SETTERS////////////////////////////////
	
	
	
	/**
	 * method for get method name
	 * @return String->methodName
	 */
	public String getMethodName(){
		
		return methodName;
	}
	
	/**
	 * method for get method visibility
	 * @return String->methodVisibility
	 */
	public String getMethodVisibity(){
		return methodVisibility;
	}
	
	/**
	 * method for get method return type
	 * @return String->methodReturnType
	 */
	public String getMethodReturnType(){
		
		return methodReturnType;
	}
	
	/**
	 * Method to get method parameters
	 * @return Map->dataTypeMap
	 */
	public Map<String, MethodAttribute>getMethodParameter(){
		
		return dataTypeMap;
	}
	
	/**
	 * method to return is main
	 * @return->res
	 */
	public String isMain(){
		
		return mainMethod;
	}
	
	////////////////////////////END SETTERS////////////////////////////////
}
