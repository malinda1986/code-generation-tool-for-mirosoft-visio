package lk.ac.kln.codegenerator.data;

import java.util.HashMap;
import java.util.Map;


public class CodeGeneratorClasses {

	private Map<String, ClassAttribute> classAttriMap = new HashMap<String ,ClassAttribute>();
	private Map<String, CodeGeneratorMethods> methodMap = new HashMap<String,CodeGeneratorMethods>();
	private String clsId = "";
	private String clsName = "";
	private String clsVisibility = "";
	private String isAbstract = "";
	private String absParent = "";
	private boolean isInterface = false;
	private boolean isSuper = false;
	private String parent = "";
	private Object sub = "";
	
	
	
	
	/**
	 * method for set Uniqe id 
	 * for class
	 */
	public void serClassId(String id){
		
		this.clsId = id;
	}
	/*********End Method **********/
	
	
	/**
	 * method for set variable clsName
	 * @param cName
	 */
	public void setClassName(String cName){
		
		this.clsName = cName;
	}
	/*********End Method **********/
	
	
	/**
	 * method for set variable clsVisibility
	 * @param cVisibility
	 */
	public void setClassVisibility(String cVisibility){
		
		this.clsVisibility = cVisibility;
	}
	/*********End Method **********/
	
	
	/**
	 * method for set variable isAbstract
	 * @param res
	 */
	public void setIsAbstract(String res){
		
		this.isAbstract = res;
	}
	/*********End Method **********/
	
	/**
	 * 
	 */
	public void setAbsPreantClass(String res){
		
		this.absParent = res;
		
	}
	/*********End Method **********/
	
	/**
	 * method for ser varible isInterface
	 * @param res
	 */
	public void setIsInterface(boolean res){
		
		this.isInterface = res;
		
	}
	/*********End Method **********/
	
	
	public void isSuper(boolean res){
		
		this.isSuper = res;
	}
	/*********End Method **********/
	
	/** 
	 * method for set parent class
	 * @param parent 
	 */
	public void setPreantClass(String superType){
		
		this.parent = superType;
	}
	/*********End Method **********/
	
	/**
	 * 
	 * @param key
	 * @param method
	 */
	
	public void setSubType(Object sub){
		
		this.sub = sub;
	}
	/*********End Method **********/
	
	public void setMethodMap(String key,CodeGeneratorMethods method){
		
		methodMap.put(key, method);
		
	}
	/*********End Method **********/
	
	
	
	
	
	/**
	 * method for get variable Visibility
	 * @param visibility
	 * @param name
	 */
	public void setClassVariableMap(String clsName, ClassAttribute attri){
		
		classAttriMap.put(clsName, attri);
		
	}
	/*********End Method **********/
	
	
	public String getAbsParent(){
		
		return absParent;
	}
	/*********End Method **********/
	
	/**
	 * 
	 */
	public String getclassId(){
		
		return clsId;
	}
	/*********End Method **********/
	
	/**
	 * method for get variable clsName
	 * @return->clsName 
	 */
	public String getClassName(){
		
		return clsName;
	}
	/*********End Method **********/
	
	
	/**
	 * method for get variable clsVisibility
	 * @return->clsVisibility
	 */
	public String getClassVisibility(){
		
		return clsVisibility;
	}
	/*********End Method **********/
	
	
	/**
	 * method for get variable isAbstract
	 * @return true or false
	 */
	public String getIsAbstract(){
		
		return isAbstract;
	
	}
	/*********End Method **********/
	
	/**
	 * 
	 */
	public boolean getIsSuper(){
		
		return isSuper;
	}
	/*********End Method **********/
	
	/**
	 * 
	 * @return
	 */
	public boolean getIsInterface(){
		
		return isInterface;
	}
	/*********End Method **********/
	
	
	/**
	 * method for get get method Map
	 * @return Map->methodMap
	 */
	public Map<String,CodeGeneratorMethods> getClassMethodList(){
	
			return methodMap;
	}
	/*********End Method **********/
	
	
	/**
	 * method for get parent class
	 * @return Object->parent
	 */
	public String getParentClass(){
	
		return parent;
	
	}
	/*********End Method **********/
	
	
	public Map<String, ClassAttribute> getClassAttribute(){
		
		return classAttriMap;
	}
	
	
}
