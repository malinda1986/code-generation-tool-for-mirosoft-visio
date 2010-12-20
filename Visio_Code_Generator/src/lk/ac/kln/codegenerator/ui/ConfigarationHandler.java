package lk.ac.kln.codegenerator.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ConfigarationHandler {

	private Document doc = null;
	private String lookAndFeel = "";
	private String openPath = "";
	private String savePath = "";

	/**
	 * method for read application configaration
	 */
	public ConfigarationHandler() {
		try {


			  File file = new File("config/config.xml");
			  
			  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			  DocumentBuilder db = dbf.newDocumentBuilder();
			  doc = db.parse(file);
			  doc.getDocumentElement().normalize();
			  
			NodeList look = doc.getElementsByTagName("LookAndFeel");
			NodeList open = doc.getElementsByTagName("FileOpenPath");
			NodeList save = doc.getElementsByTagName("FileSavePath");
//
			Element element = (Element) look.item(0);
			setLookAndFeel(processLookAndFeel(element.getAttribute("lookAndFeels")));
			Element element1 = (Element) open.item(0);
			setOpenPath(element1.getAttribute("openPath"));
			Element element2 = (Element) save.item(0);
			setSavePath(element2.getAttribute("savePath"));

			
			
			
		} catch (Exception error) {
			JOptionPane.showMessageDialog(null,
					"Can't Read the Configaration at the moment ! Error"
							+ error.getMessage());
			error.printStackTrace();
		}
	}

	/*************************************** FUNCTION END **********************************************/

	
	public void saveCofig() {

		//if (null != openPath ) {
			try {
				DocumentBuilderFactory factory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				doc = builder.parse("config/config.xml");
				// create the root element
				Element root = doc.getDocumentElement();

				// get child element
				NodeList open = root.getElementsByTagName("FileOpenPath");				
				NodeList save = root.getElementsByTagName("FileSavePath");
				//NodeList look = root.getElementsByTagName("LookAndFeel");
				
				// Add the last file open path
				Element element1 = (Element) open.item(0);
				element1.setAttribute("openPath",
						openPath);
				
				// Add the last file save path
				Element element2 = (Element) save.item(0);
				element2.setAttribute("savePath",
						savePath);
				
				// Add the last set look and feel
//				Element element3 = (Element) look.item(0);
//				element3.setAttribute("lookAndFeels",
//						lookAndFeel);
				
				TransformerFactory transfac = TransformerFactory.newInstance();
				Transformer trans = transfac.newTransformer();

				StringWriter sw = new StringWriter();
				StreamResult result = new StreamResult(sw);
				DOMSource source = new DOMSource(doc);
				trans.transform(source, result);
				String xmlString = sw.toString();

				OutputStream f0;
				byte buf[] = xmlString.getBytes();
				f0 = new FileOutputStream("config/config.xml");
				for (int i = 0; i < buf.length; i++) {
					f0.write(buf[i]);
				}
				f0.close();
				buf = null;
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (TransformerConfigurationException e) {
				e.printStackTrace();
			} catch (TransformerException e) {
				e.printStackTrace();
			} catch (Exception error) {
				JOptionPane.showMessageDialog(null,
						"Can't Set the Configaration at the moment ! Error"
								+ error.getMessage());
				error.printStackTrace();
			}

		}

	//}
	/**
	 * Return the previous time lookandfeel.
	 */
	public String getLooNFeel() {

		return lookAndFeel;
	}

	/****** FUNCTION END **********/

	/**
	 * Return the previous time File open path.
	 */
	public String getOpenPath() {
		return openPath;
	}

	/****** FUNCTION END **********/

	
	/**
	 * Return the previous time file save path.
	 */
	public String getSavePath() {
		return savePath;
	}

	/****** FUNCTION END **********/
	
	public void setSavePath(String savePath) {
		this.savePath = savePath;
		
	}

	public void setOpenPath(String openPath) {
		this.openPath = openPath;
	}

	public void setLookAndFeel(String lookAndFeel) {
		this.lookAndFeel = lookAndFeel;
	}	

	
	
	private String processLookAndFeel(String name){
		if("Metal".equalsIgnoreCase(name)){
			lookAndFeel=UIManager.getCrossPlatformLookAndFeelClassName();
		}
		else if("Windows".equalsIgnoreCase(name)){
			lookAndFeel="com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
		}
		else if("CDE/Motif".equalsIgnoreCase(name)){
			lookAndFeel="com.sun.java.swing.plaf.motif.MotifLookAndFeel";
		}
		else{
			lookAndFeel=UIManager.getCrossPlatformLookAndFeelClassName();
		}
		return lookAndFeel;
	}

}
