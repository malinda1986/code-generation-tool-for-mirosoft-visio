package lk.ac.kln.codegenerator.code;


public class CodeGeneratorFactory {
	
private static CodeGeneratorFactory codGenFac = new CodeGeneratorFactory();
	
	public  static CodeGeneratorFactory getInstance(){
				
		return codGenFac;
	}
	
	public CodeGenerator createrGenerator(String a){
		if (a == "Cpp"){
			return new CppCodeGenerator();
		}
		else{
			return new JavaCodeGenerator();
		}
	}

}
