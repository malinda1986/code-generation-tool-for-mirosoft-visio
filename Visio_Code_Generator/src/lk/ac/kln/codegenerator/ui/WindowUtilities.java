package lk.ac.kln.codegenerator.ui;


import javax.swing.UIManager;



public class WindowUtilities {

	public static void setNativeLookAndFeel() {
	    try {
	      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } catch(Exception e) {
	      System.out.println("Error setting native LAF: " + e);
	    }
	  }

	  public static void setJavaLookAndFeel() {
	    try {
	      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
	    } catch(Exception e) {
	      System.out.println("Error setting Java LAF: " + e);
	    }
	  }

	   public static void setMotifLookAndFeel() {
	    try {
	    	UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
	    } catch(Exception e) {
	      System.out.println("Error setting Motif LAF: " + e);
	    }
	  }
	   
	  public static void setDarkLookAndFeel() {
		  try {
			UIManager
					.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel");
		} catch (Exception e) {
			System.out.println("Substance Graphite failed to initialize");
		}

	 }
	  
//	  public static void setGrayLookAndFeel() {
//		    try {
//		    	UIManager.setLookAndFeel(new SubstanceGraphiteLookAndFeel());
//		    	
//		    } catch(Exception e) {
//		      System.out.println("Error setting Motif LAF: " + e);
//		    }
//	 }
}
