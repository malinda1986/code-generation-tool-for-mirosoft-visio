package lk.ac.kln.codegenerator.code;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import lk.ac.kln.codegenerator.data.ClassAttribute;
import lk.ac.kln.codegenerator.data.CodeGeneratorClasses;
import lk.ac.kln.codegenerator.data.CodeGeneratorMethods;
import lk.ac.kln.codegenerator.data.MethodAttribute;


public class CppCodeGenerator implements CodeGenerator{
	
	@Override
	public String getCode(CodeGeneratorClasses cdCls) {
		String code = writeClsDeclareation(cdCls);
		
		return code;
		
	}
	
	/**
	 * method for create class declaration
	 * @param val
	 * @return clsDec
	 */	
	public String writeClsDeclareation(CodeGeneratorClasses val){
		
		String clsDec = "";
		String clsVisibility = val.getClassVisibility();
		String clsName = val.getClassName();
		
		if("true" == val.getIsAbstract()){
			
			clsDec = "abstract "+ " class " + clsName + "inherits "+ val.getParentClass()+ "{" + "\n" + "\n"+ "\n"+ "\n";			
			clsDec += writeClsVaribles(val) + "\n"+ "\n";
			clsDec += writeMethodDeclaration(val);
			
			
		}else{
			
			clsDec = clsVisibility + " class " + clsName + " " + val.getParentClass()+ "{" + "\n" + "\n"+ "\n"+ "\n";
			clsDec += writeClsVaribles(val) + "\n"+ "\n";
			clsDec += writeMethodDeclaration(val);
		}
						
		return clsDec;
	}
	/***************************************FUNCTION END**********************************************/

	
	/**
	 *method for create class variables
	 *@param val
	 *@return String ->clsVari
	 */
	public String writeClsVaribles (CodeGeneratorClasses val){
		
		
		Map<String, ClassAttribute> classAttriMap = val.getClassAttribute();
		
		Set<String> keys = classAttriMap.keySet(); // The set of keys in the map.
		Iterator<String> keyIter = keys.iterator();
		int numOfObj = classAttriMap.size();
		String clsVari ="";
		for(int i = 0; i < numOfObj; i++ ){
			
			 String key = keyIter.next();  // Get the next key.
	         ClassAttribute value = classAttriMap.get(key);  // Get the value for that key.
			

	         
	         clsVari += value.getVisibility() + "  " + value.getType() + "  " + value.getName() + ";" + "\n";

		}
		
		if(clsVari == ""){
				
			clsVari += "}";
		}
		
		
		//clsVari += "}";
		
		return clsVari;
	}
	/***************************************FUNCTION END**********************************************/

	
	/**
	 *method for create class variables
	 *@param CodeGeneratorClasses->val
	 *@return String->clsMethod
	 */
	public String writeMethodDeclaration(CodeGeneratorClasses val){
		
		String clsMethod = "";
		
		Map<String, CodeGeneratorMethods> clsMethodMap = val.getClassMethodList();
		
		Set<String> keys = clsMethodMap.keySet(); // The set of keys in the map.
		Iterator<String> keyIter = keys.iterator();
		int numOfMethods = clsMethodMap.size();
		
		for(int i = 0; i < numOfMethods; i++){
			
			String key = keyIter.next();  // Get the next key.
			CodeGeneratorMethods value = clsMethodMap.get(key);  // Get the value for that key.
			 String rType = getMethodReturnType( value,value.getMethodName().toString());
			
			 clsMethod += "\t" + value.getMethodVisibity() + " " + rType 
						 + " " + value.getMethodName()+"()" + "{" + "\n"  + "\n"  + "\n";
			 clsMethod += writeMethodPara(value,value.getMethodName().toString());
			
		}
		
		if(clsMethod == ""){
			
			//clsMethod += "}";
		}
		
		return clsMethod;
	}
	/***************************************FUNCTION END**********************************************/
	
	/**
	 * method for create method parameters
	 * @param CodeGeneratorClasses->val
	 * @return String->mPara
	 */
	public String writeMethodPara(CodeGeneratorMethods val,String mName){
		
		String mPara = " ";

		Map<String, MethodAttribute> clsMethodParaList = val
				.getMethodParameter();
		Set<String> keys = clsMethodParaList.keySet(); // The set of keys in the map.
		Iterator<String> keyIter = keys.iterator();
		
		
		int numOfMethodsPara = clsMethodParaList.size();
		for (int i = 0; i < numOfMethodsPara; i++) {
				
			String key = keyIter.next(); // Get the next key.
			MethodAttribute value = clsMethodParaList.get(key); // Get the value// for that key.
			
			int result = value.getName().compareTo(mName);// get the method return type
			if(result == 0){
				
				//mPara += "return"+"\t\t"+ value.getType() + " " + value.getName() + ";" + "\n";
				
			}else{
				
				mPara += "\t\t"+ value.getType() + " " + value.getName() + ";" + "\n";
			
			}
			
			
			
		}
		mPara += "\n" + "\t" + "}"; // end method
		mPara += "\n" + "\n" + "\n" + "\n" + "}"; // end class

		return mPara;
	}
	/***************************************FUNCTION END**********************************************/

	public String getMethodReturnType(CodeGeneratorMethods val,String mName){
		

		String mPara = "void ";

		Map<String, MethodAttribute> clsMethodParaList = val
				.getMethodParameter();
		Set<String> keys = clsMethodParaList.keySet(); // The set of keys in the map.
		Iterator<String> keyIter = keys.iterator();
		
		int numOfMethodsPara = clsMethodParaList.size();
		for (int i = 0; i < numOfMethodsPara; i++) {
				
			String key = keyIter.next(); // Get the next key.
			MethodAttribute value = clsMethodParaList.get(key); // Get the value// for that key.
			
			int result = value.getName().compareTo(mName);// get the method return type
			if(result == 0){
				
				mPara = value.getType();
			
				break;
			}else{
				
				mPara = "void";
			
			}
			
			
			
		}
		//mPara += "\n" + "\t" + "}"; // end method
		mPara += "\n" + "\n" + "\n" + "\n" + "}"; // end class

		return mPara;
	
		
	}
}
