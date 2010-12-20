package lk.ac.kln.codegenerator.xml;


public class XMLPaserFactory {

	private static XMLPaserFactory xmlPaFac = new XMLPaserFactory();
	
	public  static XMLPaserFactory getInstance(){
				
		return xmlPaFac;
	}
	
	public XMLPaser createParse(int a){
		if (a == 1){
			return new VisioXMLPaser();
		}
		else{
			return new ArgoXMLParser();
		}
	}

	
}
