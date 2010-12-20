package lk.ac.kln.codegenerator.controller;
import lk.ac.kln.codegenerator.code.CodeGenerator;
import lk.ac.kln.codegenerator.code.CodeGeneratorFactory;
import lk.ac.kln.codegenerator.data.CodeGeneratorClasses;
import lk.ac.kln.codegenerator.data.DataStorage;
import lk.ac.kln.codegenerator.xml.XMLPaser;
import lk.ac.kln.codegenerator.xml.XMLPaserFactory;

public class PaserController {

	private DataStorage dataStrorage = new DataStorage();
	

	public DataStorage getDataStrorage() {
		return dataStrorage;
	}

	public void parseXmlFile(String type, int a,String filePath) {

		XMLPaser p = XMLPaserFactory.getInstance().createParse(a);

		p.parseFile("Visio", dataStrorage,filePath);
	}

	public String generateCode(CodeGeneratorClasses cdCls) {

		CodeGenerator c = CodeGeneratorFactory.getInstance().createrGenerator("java");
		
		return  c.getCode(cdCls);
		
	}
}

