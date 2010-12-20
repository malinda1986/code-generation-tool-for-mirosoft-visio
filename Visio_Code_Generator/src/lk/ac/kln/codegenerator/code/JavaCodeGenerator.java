package lk.ac.kln.codegenerator.code;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import lk.ac.kln.codegenerator.data.ClassAttribute;
import lk.ac.kln.codegenerator.data.CodeGeneratorClasses;
import lk.ac.kln.codegenerator.data.CodeGeneratorMethods;
import lk.ac.kln.codegenerator.data.MethodAttribute;

/************************************************                                            *
 * @author malinda                              *
 * this class generate code for "java" language *
 * implement from CodeGenerator Interface       *
 ************************************************/

public class JavaCodeGenerator implements CodeGenerator {


	
	/**
	 * Return the final output code
	 * @return->string
	 */
	public String getCode(CodeGeneratorClasses cdCls) {
		
		return writeClsDeclareation(cdCls);
	}
	
	
	/**
	 * method for create class declaration
	 * @param val
	 * @return clsDec
	 */	
	public String writeClsDeclareation(CodeGeneratorClasses val){
		
		String clsDec = "";
		String clsVari = "";
		String clsMethod ="";
		
		String clsVisibility = val.getClassVisibility();
		String clsName = val.getClassName();
		
		String abs = val.getIsAbstract();
		String absParent = val.getAbsParent();
		if(abs.equals("true")){
			
			clsDec = "abstract "+ " class " + clsName + " "+ "{" + "\n" + "\n"+ "\n"+ "\n";			
			clsVari = writeClsVaribles(val) + "\n"+ "\n";
			clsMethod = writeMethodDeclaration(val);
			
			
		}else if(absParent != ""){
			
			clsDec = clsVisibility+ " class " + clsName + " extend "+ absParent +"{" + "\n" + "\n"+ "\n"+ "\n";			
			clsVari = writeClsVaribles(val) + "\n"+ "\n";
			clsMethod = writeMethodDeclaration(val);
		
			
		}
		else if(val.getIsInterface()){
		
			clsDec = "Interface "+ " " + clsName + "{" + "\n" + "\n"+ "\n"+ "\n";			
			clsVari = writeClsVaribles(val) + "\n"+ "\n";
			clsMethod = writeInterMtdDec(val);
			
			
			
		}else if (val.getIsSuper()){
			
			clsDec = clsVisibility + " class " + clsName + " inherits  " + val.getParentClass()+ "{" + "\n" + "\n"+ "\n"+ "\n";
			clsVari = writeClsVaribles(val) + "\n"+ "\n";
			clsMethod = writeMethodDeclaration(val);
		}
		else{
			
			clsDec = clsVisibility + " class " + clsName + " " +  "{" + "\n" + "\n"+ "\n"+ "\n";
			clsVari = writeClsVaribles(val) + "\n"+ "\n";
			clsMethod = writeMethodDeclaration(val);
			
		}
		//clsTree.add(methodTree);
		
		if(clsMethod == ""){
			
			clsVari += "}";
		}
		if(clsVari == " "){
			
			clsDec += "}";
		}
		
		return clsDec+ clsVari +clsMethod +  "\n\n\n" + "}" ;
	}
	/***************************************FUNCTION END**********************************************/

	
	
	/**
	 *method for write class variables
	 *@param val
	 *@return String ->clsSignature
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
			

	         //add all class variables to clsVari
	         clsVari += value.getVisibility() + "  " + value.getType() + "  " + value.getName() + "; " + "\n";

		}
		
		
		return clsVari;
	}
	/***************************************FUNCTION END**********************************************/

	
	/**
	 *method for create class variables
	 *@param CodeGeneratorClasses->val
	 *@return String->clsMethod
	 */
	public String writeMethodDeclaration(CodeGeneratorClasses val){
		
		String mtdDec = "";
		
		
		Map<String, CodeGeneratorMethods> clsMethodMap = val.getClassMethodList();
		
		Set<String> keys = clsMethodMap.keySet(); // The set of keys in the map.
		Iterator<String> keyIter = keys.iterator();
		int numOfMethods = clsMethodMap.size();
		
		for(int i = 0; i < numOfMethods; i++){
			
			String key = keyIter.next();  // Get the next key.
			CodeGeneratorMethods value = clsMethodMap.get(key);  // Get the value for that key.
			String rType = getMethodReturnType( value,value.getMethodName().toString());
			String checkMain = value.isMain(); 
			if( checkMain.equals("main") ){
				
				mtdDec += "\t" + "public static" + " " + "void main " 
				 + " " +"(String[] args"; 
				
			}else{
				
				mtdDec += "\t" + value.getMethodVisibity() + " " + rType 
				 + " " + value.getMethodName()+"("; 
			}
			
			mtdDec += writeMethodPara(value,value.getMethodName().toString());
			mtdDec += ")" + "{" + "\n"  + "\n"  + "\n";
			
			String res = "String";
			String re ="";
			if (res.equals(rType) ||rType.equals("char")||rType.equals("Object") ){
				
				mtdDec += "\t\t" + "return null;" + "\n";
				
			}
			else if(rType.equals("double")){
				
				mtdDec += "\t\t" + "return 0.0;" + "\n";
			
			}
			else if(rType.equals("boolean")){
				
				mtdDec += "\t\t" + "return false;" + "\n";
			
			}
			else if(rType.equals("long") || rType.equals("int") ||rType.equals("short")||rType.equals("byte")){
				
				mtdDec += "\t\t" + "return 0;" + "\n";
			
			}
			
			if(mtdDec != null){
				
				mtdDec += re + "\t"+"}" + "\n\n\n";
			}
		}
		

		// methodTree.add(new DefaultMutableTreeNode("kkkkkk"));
		
		
		
		return mtdDec +"\n";
	}
	/***************************************FUNCTION END**********************************************/
	
	/**
	 * method for write Interface declaration
	 * @param CodeGeneratorClasses->val
	 */
	public String writeInterMtdDec(CodeGeneratorClasses val){
		
		String mtdDec = "";
		
		
		Map<String, CodeGeneratorMethods> clsMethodMap = val.getClassMethodList();
		
		Set<String> keys = clsMethodMap.keySet(); // The set of keys in the map.
		Iterator<String> keyIter = keys.iterator();
		int numOfMethods = clsMethodMap.size();
		
		for(int i = 0; i < numOfMethods; i++){
			
			String key = keyIter.next();  // Get the next key.
			CodeGeneratorMethods value = clsMethodMap.get(key);  // Get the value for that key.
			//String rType = getMethodReturnType( value,value.getMethodName().toString());
			
			
			mtdDec += "\t" + value.getMethodVisibity() + "   static " + "void" 
						 + " " + value.getMethodName()+"("; 
			mtdDec += writeMethodPara(value,value.getMethodName().toString());
			mtdDec += ")" + ";" + "\n"  + "\n"  + "\n";
			

		}
		

		// methodTree.add(new DefaultMutableTreeNode("kkkkkk"));
		
		
		
		return mtdDec +"\n";
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
		
		if(numOfMethodsPara == 1){//dont add "," after one parameter
			
			String key = keyIter.next(); // Get the next key.
			MethodAttribute value = clsMethodParaList.get(key); // Get the value// for that key.
			
			int result = value.getName().compareTo(mName);// get the method return type
			if(result == 0){
				
				//mPara += "return"+"\t\t"+ value.getType() + " " + value.getName() + ";" + "\n";
				
			}else{
				
				mPara = " "+ value.getType() + " " + value.getName() + "" + "";
			
			}
			
		}else{//add "," one after one
			for (int i = 0; i < numOfMethodsPara; i++) {
				
				String key = keyIter.next(); // Get the next key.
				MethodAttribute value = clsMethodParaList.get(key); // Get the value// for that key.
				
				int result = value.getName().compareTo(mName);// get the method return type
				if(result == 0){
					
					//mPara += "return"+"\t\t"+ value.getType() + " " + value.getName() + ";" + "\n";
					
				}else{
					
					
					
					if(i == (numOfMethodsPara-1)){ //if it last parameter dont add ","
						
						mPara += " "+ value.getType() + " " + value.getName() + "" + " ";
					}
					else{ // add "," one after another
						
						mPara += " "+ value.getType() + " " + value.getName() + "" + ", ";
					}
				
				}
				
				
				
			}
			
		}


		return mPara;
	}
	/***************************************FUNCTION END**********************************************/

	
	/**
	 * method for get the return type of
	 * the method
	 * @param CodeGeneratorMethods->val,String->mName
	 * @return string ->returnType
	 */
	public static String getMethodReturnType(CodeGeneratorMethods val,String mName){
		

		String returnType = "void ";

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
				
				returnType = value.getType();
			
				break;
			}else{
				
				returnType = "void";
			
			}			
			
		}

		return returnType;
	
		
	}
	/***************************************FUNCTION END**********************************************/
	

	
}/////CLASS END
