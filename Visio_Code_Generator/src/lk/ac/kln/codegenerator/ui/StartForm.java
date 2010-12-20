package lk.ac.kln.codegenerator.ui;


public class StartForm  {


	public static void main(String[] args) {

		Splash.showSplash(1500);
		
		CodeWriter rCode = new CodeWriter();
		rCode.createGUI();

	}

}
