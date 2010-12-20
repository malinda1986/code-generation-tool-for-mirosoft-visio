package lk.ac.kln.codegenerator.xml;



import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import lk.ac.kln.codegenerator.data.ClassAttribute;
import lk.ac.kln.codegenerator.data.CodeGeneratorClasses;
import lk.ac.kln.codegenerator.data.CodeGeneratorMethods;
import lk.ac.kln.codegenerator.data.DataStorage;
import lk.ac.kln.codegenerator.data.MethodAttribute;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;

/****************************************************************
 * This class read the XML file generate form Microsoft Visio   *
 * and Store the required details that need for code generation.*
 * @author malinda                                              * 
 * @date 01.08.2010                                             *
 *--------------------------------------------------------------*
 * XML file details                                             *
 *                                                              *
 * XMI Export Version -                                         *
 * encoding = 'UTF-8'                                           *
 *                                                              *
 ****************************************************************/
public class VisioXMLPaser implements XMLPaser {

	private Document doc = null;
	private Map<String, String> dataTypeMap = new HashMap<String, String>();
	private Map<String, String> assoTypeMap = new HashMap<String, String>();
	private Map<String ,String> classMap = new HashMap<String, String>();
	private String cName = "";
	public static boolean checkProsecc = false;
	public void parseFile(String fileName, DataStorage dStorage,String filePath) {
		
		dStorage.getClassMap("").clear();	
		try {
			
			File file = new File(filePath);
			
			if (file.exists()){
				//default- factory set to dtd validation true 
		        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		        
		        DocumentBuilder builder = factory.newDocumentBuilder();
		        doc = builder.parse(filePath);
		        //Create transformer
		        Transformer tFormer = TransformerFactory.newInstance().newTransformer();
		       
		        tFormer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		        tFormer.setOutputProperty(OutputKeys.STANDALONE,"yes");

		        Source source = new DOMSource(doc);
		        StreamResult result = new StreamResult(System.out);
		        tFormer.transform(source, result);
		        checkProsecc = true;
		      }
			else{
		        JOptionPane.showMessageDialog(null,"File not found!");
		      }
			

			
			processDataType();
			processRelations();
			addClassIDAndName();
			NodeList list = doc.getElementsByTagName("XMI.content");

			for (int i = 0; i < list.getLength(); ++i) {
				Node node = list.item(i);
				NodeList childList = node.getChildNodes();

				for (int j = 0; j < childList.getLength(); ++j) {
					Node childNode = childList.item(j);
					if (childNode.getNodeName() == "Model_Management.Model") {
						// System.out.println(childNode.getNodeName());
						NodeList classList = childNode.getChildNodes();

						for (int k = 0; k < classList.getLength(); k++) {
							Node childClassList = classList.item(k);

							if (childClassList.getNodeName() == "Foundation.Core.Namespace.ownedElement") {
								// /System.out.println(childClassList.getNodeName());
								NodeList allClasses = childClassList
										.getChildNodes();

								for (int l = 0; l < allClasses.getLength(); l++) {
									Node allClassesList = allClasses.item(l);

									if (allClassesList.getNodeName() == "Model_Management.Package") {
										// System.out.println(allClassesList.getNodeName());
										NodeList classNode = allClassesList
												.getChildNodes();
										for (int m = 0; m < classNode
												.getLength(); m++) {
											Node cCNode = classNode.item(m);
											if (cCNode.getNodeName() == "Foundation.Core.Namespace.ownedElement") {
												NodeList cCnoedList = cCNode
														.getChildNodes();

												for (int d = 0; d < cCnoedList.getLength(); d++) {
													Node cNode = cCnoedList.item(d);
													if (cNode.getNodeName() == "Foundation.Core.Class")// classes
													{
													    System.out.println(cNode.getNodeName());
														
													    Element element1 = (Element) cCnoedList.item(d);
													
													   
													    NodeList nodeClass = cNode.getChildNodes();
														processClasses(nodeClass,dStorage,element1.getAttribute("xmi.id").toString());

													}else if (cNode.getNodeName() == "Foundation.Core.Generalization") {
														
														//System.out.println(cNode.getNodeName());
														NodeList nodeAssco = cNode.getChildNodes();
														
														procesClassAccosiation(nodeAssco,dStorage);
														 //System.out.println(cNode.getNodeName());
											
													}else if(cNode.getNodeName() == "Foundation.Core.Interface"){
														
														//System.out.println("Interface==========================" + cNode.getNodeName());
														
														
														 Element element1 = (Element) cCnoedList.item(d);
														  
														// System.out.println(cNode.getNodeName());
														 NodeList nodeInterface = cNode.getChildNodes();
														 processInterface(nodeInterface,dStorage,element1.getAttribute("xmi.id").toString());
													}
													

												}

											}
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception error) {
			 JOptionPane.showMessageDialog(null,"Can't process at the moment ! Error" + error.getMessage());
			error.printStackTrace();
		}
	}

	/**
	 * method for prosesss class ->name, visibility
	 * 
	 * @param nList
	 */
	public void processClasses(NodeList nList, DataStorage dStorage,String cId) {

		CodeGeneratorClasses codeGenClass = new CodeGeneratorClasses();
		
		for (int f = 0; f < nList.getLength(); f++)// class list
		{			
			Node classes = nList.item(f);

			if (classes.getNodeName() == "Foundation.Core.ModelElement.name") {
				Element element1 = (Element) nList.item(f);
				NodeList clsName = element1.getChildNodes();
				System.out.println("Class Name : "
						+ ((clsName.item(0)).getNodeValue()));
				cName = (clsName.item(0)).getNodeValue();
				
				// set class name
				codeGenClass.setClassName((clsName.item(0)).getNodeValue());
			}
			if (classes.getNodeName() == "Foundation.Core.ModelElement.visibility") {

				Element element1 = (Element) nList.item(f);
				System.out.println("Class Visibility = "
						+ element1.getAttribute("xmi.value"));

				// set class visibility
				codeGenClass
						.setClassVisibility(element1.getAttribute("xmi.value"));
			}
			if (classes.getNodeName() == "Foundation.Core.GeneralizableElement.isAbstract") {
				Element element1 = (Element) nList.item(f);
				System.out.println("Is Abstractract = "
						+ element1.getAttribute("xmi.value"));

				// set isAbstract
				codeGenClass.setIsAbstract(element1.getAttribute("xmi.value").toString());
			}
			if (classes.getNodeName() == "Foundation.Core.Classifier.feature") {

				NodeList feature = classes.getChildNodes();

				prosessClassAttribute(feature,dStorage,codeGenClass);
			}			
		}
				
		dStorage.setClassMap(cName, codeGenClass);
		
	}
	/********************************************* End Method **********************************************/

	/**
	 * method for prosesss class ->name, visibility
	 * 
	 * @param nList
	 */
	public void processInterface(NodeList nList, DataStorage dStorage,String cId) {

		
		CodeGeneratorClasses codeGenClass = new CodeGeneratorClasses();
		for (int f = 0; f < nList.getLength(); f++)// class list
		{
			
			Node classes = nList.item(f);
			codeGenClass.setIsInterface(true);
			if (classes.getNodeName() == "Foundation.Core.ModelElement.name") {
				Element element1 = (Element) nList.item(f);
				NodeList clsName = element1.getChildNodes();
				System.out.println("Class Name : "
						+ ((clsName.item(0)).getNodeValue()));
				cName = (clsName.item(0)).getNodeValue();
				// set class name
				codeGenClass.setClassName((clsName.item(0)).getNodeValue());
			}
			
			
			if (classes.getNodeName() == "Foundation.Core.Classifier.feature") {

				NodeList feature = classes.getChildNodes();

				prosessClassAttribute(feature,dStorage,codeGenClass);

			}

			/**
			 * add all class details to DataStorage
			 * 
			 * @input
			 * @key->class name, @value->class object
			 */
			
			dStorage.setClassMap(cName, codeGenClass);
		}
		
		Map<String, CodeGeneratorClasses> Map = dStorage.getClassMap("");

		System.out.println("CLASSSSSSSSSSSSSSSSSSS:" + Map.size());
	}
	/********************************************* End Method **********************************************/
	
	/**
	 * method for process class Association
	 * @param dStorage 
	 * @param NodeList->acco
	 */
	public void procesClassAccosiation(NodeList acco, DataStorage dStorage) {

		
		for(int i = 0 ; i < acco.getLength(); i++){
			
			Node nodeAcco = acco.item(i);
			
		    String associationType = ""; 
			if(nodeAcco.getNodeName() == "Foundation.Core.ModelElement.stereotype"){
				
				NodeList assoListname = nodeAcco.getChildNodes();
				
				for(int n = 0 ; n < assoListname.getLength(); n++){
					
					Node assoNodeName = assoListname.item(n);
					
					if(assoNodeName.getNodeName() == "Foundation.Extension_Mechanisms.Stereotype"){
						
						Element element1 = (Element) assoListname.item(n);
						
						associationType = assoTypeMap.get(element1.getAttribute("xmi.idref"));
						
						System.out.println("gdgfdggdgg === " + associationType);
					
						storeClassAssociation( acco, dStorage, associationType);
					}
				}
		
			}
			
		}
	
	}
	/********************************************* End Method **********************************************/
	
	
	
	
	public void storeClassAssociation(NodeList acco, DataStorage dStorage,String accoType){
		
		
		
		if(0 == accoType.compareTo("inherits")){//inheritance
			
			String subType = "";
			String superType ="";
			
			for(int i = 0 ; i < acco.getLength(); i++){
				
				Node nodeAcco = acco.item(i);	
				
				if(nodeAcco.getNodeName() == "Foundation.Core.Generalization.subtype"){
					
					NodeList nodeLiChildAcco = nodeAcco.getChildNodes();
					
					
					for(int k = 0; k < nodeLiChildAcco.getLength(); k++){
						
						Node accoClass = nodeLiChildAcco.item(k);
						
						if(accoClass.getNodeName() == "Foundation.Core.Class"){
							
							
							System.out.println(accoClass.getNodeName());
							Element element1 = (Element) nodeLiChildAcco.item(k);
							subType = classMap.get(element1.getAttribute("xmi.idref").toString()) ;
							
							//System.out.println("SUB TYPE ==" + subType);
							
							
						}
						
					}
				}
				if(nodeAcco.getNodeName() == "Foundation.Core.Generalization.supertype"){
					
					NodeList nodeLiChildAcco = nodeAcco.getChildNodes();
					
					for(int k = 0; k < nodeLiChildAcco.getLength(); k++){
						
						Node accoClass = nodeLiChildAcco.item(k);
						
						if(accoClass.getNodeName() == "Foundation.Core.Class"){
													
							
							Element element1 = (Element) nodeLiChildAcco.item(k);
							superType = classMap.get(element1.getAttribute("xmi.idref").toString()) ;
							System.out.println("SUPER TYPE == " + superType);
							System.out.println("SUB TYPE ==" + subType);
							
							CodeGeneratorClasses cgCls = dStorage.getClass(subType);
							
							cgCls.setPreantClass(superType);
							cgCls.isSuper(true);	
						}
						
					}
					
				
				}
				
				
			}
			
			
			
		}
		else if (0 == accoType.compareTo("extends")){
			
			String subType = "";
			String superType ="";
			
			for(int i = 0 ; i < acco.getLength(); i++){
				
				Node nodeAcco = acco.item(i);	
				
				if(nodeAcco.getNodeName() == "Foundation.Core.Generalization.subtype"){
					
					NodeList nodeLiChildAcco = nodeAcco.getChildNodes();
										
					for(int k = 0; k < nodeLiChildAcco.getLength(); k++){
						
						Node accoClass = nodeLiChildAcco.item(k);
						
						if(accoClass.getNodeName() == "Foundation.Core.Class"){
														
							System.out.println(accoClass.getNodeName());
							Element element1 = (Element) nodeLiChildAcco.item(k);
							subType = classMap.get(element1.getAttribute("xmi.idref").toString()) ;
								
						}
						
					}
				}
				if(nodeAcco.getNodeName() == "Foundation.Core.Generalization.supertype"){
					
					NodeList nodeLiChildAcco = nodeAcco.getChildNodes();
					
					for(int k = 0; k < nodeLiChildAcco.getLength(); k++){
						
						Node accoClass = nodeLiChildAcco.item(k);
						
						if(accoClass.getNodeName() == "Foundation.Core.Class"){
																			
							Element element1 = (Element) nodeLiChildAcco.item(k);
							superType = classMap.get(element1.getAttribute("xmi.idref").toString()) ;
							System.out.println("SUPER TYPE == " + superType);
							System.out.println("SUB TYPE ==" + subType);
							
							CodeGeneratorClasses cgCls = dStorage.getClass(subType);
							
							cgCls.setAbsPreantClass(superType);
								
						}
						
					}
					
				
				}
				
				
			}
			
		}
				
	}
	/********************************************* End Method **********************************************/
	

	/**
	 * method for prosesss method ->name, visibility, return Type
	 * 
	 * @param feature
	 */
	public void prosessClassAttribute(NodeList feature,DataStorage dStorage,CodeGeneratorClasses classList) {

		
		//CodeGeneratorClasses clsList = new CodeGeneratorClasses();
		
		
		for (int a = 1; a < feature.getLength(); a++) {
			Node featureName = feature.item(a);

			if (featureName.getNodeName() == "Foundation.Core.Attribute") {
				NodeList attri = featureName.getChildNodes();

				// create new ClassAttribute object
				ClassAttribute clsAttri = new ClassAttribute();

				for (int q = 1; q < attri.getLength(); q++) {
					Node attriName = attri.item(q);

					if (attriName.getNodeName() == "Foundation.Core.ModelElement.name") {

						Element element1 = (Element) attri.item(q);
						NodeList clsName = element1.getChildNodes();
						System.out.println("Attribute Name : "
								+ ((clsName.item(0)).getNodeValue()));

						// set class variable Name
						clsAttri.setName((clsName.item(0)).getNodeValue());
					}
					if (attriName.getNodeName() == "Foundation.Core.ModelElement.visibility") {

						Element element1 = (Element) attri.item(q);
						System.out.println("Attribute Visibility = "
								+ element1.getAttribute("xmi.value"));

						// set class variable visibility
						clsAttri.setVisibility(element1
								.getAttribute("xmi.value"));

					}
					if (attriName.getNodeName() == "Foundation.Core.StructuralFeature.type") {
						
						NodeList dataType = attriName.getChildNodes();

						Element element1 = (Element) dataType.item(1);
						 System.out.println( "Attribute Name?????????? = " +
						 element1.getNodeName() );
						
						if( null != dataTypeMap.get(
								element1.getAttribute("xmi.idref"))
								){
							
							clsAttri.setDataType(dataTypeMap.get(
									element1.getAttribute("xmi.idref")).toString());
							
							
						}
						else{
							
							clsAttri.setDataType(classMap.get(
									element1.getAttribute("xmi.idref")).toString());
							
						}
//						System.out.println("Attribute Type = "
//								+ dataTypeMap.get(
//										element1.getAttribute("xmi.idref"))
//										.toString());

						// set class varible datatype
						
					}
				}

				// add class variable object to Map
				classList.setClassVariableMap(a + cName, clsAttri);

			}
			if (featureName.getNodeName() == "Foundation.Core.Operation") {

				NodeList method = featureName.getChildNodes();

				prosessOperation(method,dStorage,classList);

			}
		}
		
	}

	/**************************************************** End Method ************************************************************/

	/**
	 * 
	 * @param method
	 */
	public void prosessOperation(NodeList method,DataStorage dStorage,CodeGeneratorClasses classList) {

		CodeGeneratorMethods methodList = new CodeGeneratorMethods();
		String methodName = "";
		for (int q = 1; q < method.getLength(); q++) {
			Node attriName = method.item(q);
			
			if (attriName.getNodeName() == "Foundation.Core.ModelElement.name") {

				Element element1 = (Element) method.item(q);
				NodeList clsName = element1.getChildNodes();
				System.out.println("Function Name : "
						+ ((clsName.item(0)).getNodeValue()));
				methodName = (clsName.item(0)).getNodeValue();
				methodList.setMethodName((clsName.item(0)).getNodeValue());
			}
			if (attriName.getNodeName() == "Foundation.Core.ModelElement.visibility") {

				Element element1 = (Element) method.item(q);
				System.out.println("Function Visibility = "
						+ element1.getAttribute("xmi.value"));

				methodList
						.setMethodVisibity(element1.getAttribute("xmi.value"));
			}
			if (attriName.getNodeName() == "Foundation.Core.BehavioralFeature.parameter") {

				
				
				NodeList para = attriName.getChildNodes();
				processOprarionParameter(para,dStorage,methodList,methodName);
				//methodList.setMethodParameter(methodName, mAttri);
				
			}
			if(attriName.getNodeName() == "Foundation.Core.ModelElement.taggedValue"){
				
				
				NodeList para = attriName.getChildNodes();
				
				
				for (int k = 0; k < para.getLength(); k++) {
					Node mainNode = para.item(k);
					if (mainNode.getNodeName() == "Foundation.Extension_Mechanisms.TaggedValue") {
						Element element1 = (Element) method.item(1);
						//System.out.println("MAIN MAIN AAAAA" + mainNode.getNodeName()); 
						NodeList main = element1.getChildNodes();
						
						String ckeckMain = main.item(0).getNodeValue();
						if(ckeckMain.equals("main")){
							System.out
							.println("MAIN MAINMAINMAINMAINMAINMAINMAIN= "
									+ main.item(0).getNodeValue());
							 methodList.setIsMain(ckeckMain);
						}
						
					}

				}
				
				
			}
			
		}
		
		classList.setMethodMap(methodName,methodList);

	}

	/******************************** End Method ************************************************/

	
	
	public void processOprarionParameter(NodeList para,DataStorage dStorage,CodeGeneratorMethods methodList, String methodName ) {
		
		
		for (int y = 0; y < para.getLength(); y++) {
			
			Node paraName = para.item(y);
			
			if (paraName.getNodeName() == "Foundation.Core.Parameter") {
				NodeList numOfPara = paraName.getChildNodes();
				MethodAttribute mAttri = new MethodAttribute();
				for (int t = 0; t < numOfPara.getLength(); t++) {
					
					String paraKind = "";
					String mRreturnType = "";
					Node pName = numOfPara.item(t);

					if (pName.getNodeName() == "Foundation.Core.ModelElement.name") {

						Element element1 = (Element) numOfPara.item(t);
						NodeList clsName = element1.getChildNodes();
						System.out.println("Parameter Name : "
								+ ((clsName.item(0)).getNodeValue()));
						mAttri.setName((clsName.item(0)).getNodeValue());

					}
					if (pName.getNodeName() == "Foundation.Core.ModelElement.visibility") {

						Element element1 = (Element) numOfPara.item(t);
						System.out.println("Parameter Visibility = "
								+ element1.getAttribute("xmi.value"));

					}
					if (pName.getNodeName() == "Foundation.Core.Parameter.type") {

						NodeList dataType = pName.getChildNodes();

						Element element1 = (Element) dataType.item(1);
					
						if( null != dataTypeMap.get(
								element1.getAttribute("xmi.idref"))
								){
							
							mAttri.setDataType(dataTypeMap.get(
									element1.getAttribute("xmi.idref")).toString());
							
							mRreturnType = dataTypeMap.get(element1.getAttribute("xmi.idref").toString());
							
						}
						else{
							
							mAttri.setDataType(classMap.get(
									element1.getAttribute("xmi.idref")).toString());
							
							mRreturnType = dataTypeMap.get(element1.getAttribute("xmi.idref").toString());
							
						}
						
						System.out.println("Parameter Type = "
								+ dataTypeMap.get(element1
										.getAttribute("xmi.idref")
										.toString()));
//						mAttri.setDataType(dataTypeMap.get(element1
//								.getAttribute("xmi.idref")
//								.toString()));
						
						
					}
					if (pName.getNodeName() == "Foundation.Core.Parameter.kind") {

						Element element1 = (Element) numOfPara.item(t);

						paraKind = element1.getAttribute("xmi.value");
						System.out.println("Parameter kind = "
								+ paraKind);
						if (0 == paraKind.compareTo("in") ) {
							System.out.println("Parameter kindAA = "
									+ paraKind);
							
							
						} else {
							System.out.println("Method return Type = "
									+ paraKind);
							methodList.setMethodReturnType(mRreturnType);
						}
					}
					
				}
				methodList.setMethodParameter("a" + y, mAttri);
			}
			
		}
	}
	/******************************** End Method ************************************************/
	
	
	
	/**
	 * method for process all data
	 * types and stored in map
	 */
	public void processDataType() {

		NodeList root = doc.getElementsByTagName("XMI.content");

		for (int i = 0; i < root.getLength(); i++) {

			Node node = root.item(i);
			NodeList childList = node.getChildNodes();

			for (int j = 0; j < childList.getLength(); ++j) {
				Node childNode = childList.item(j);
				if (childNode.getNodeName() == "Model_Management.Subsystem") {

					NodeList model = childNode.getChildNodes();

					for (int k = 1; k < model.getLength(); k++) {
						Node ownElement = model.item(k);
						if (ownElement.getNodeName() == "Foundation.Core.Namespace.ownedElement") {
							NodeList ownEle = ownElement.getChildNodes();
							for (int n = 1; n < ownEle.getLength(); n++) {
								Node dataPackage = ownEle.item(n);
								if (dataPackage.getNodeName() == "Model_Management.Package") {

									NodeList dataPack = dataPackage
											.getChildNodes();
									for (int q = 0; q < dataPack.getLength(); q++) {
										Node dataPacEle = dataPack.item(q);

										if (dataPacEle.getNodeName() == "Foundation.Core.Namespace.ownedElement") {
											// System.out.println("Name" +
											// dataPacEle.getNodeName());

											NodeList dataOwnEle = dataPacEle
													.getChildNodes();
											getDataType(dataOwnEle);
										}

									}

								}

							}

						}

					}

				}
			}

		}

	}
	/******************************** End Method ************************************************/
	
	/**
	 * 
	 */
	public void addClassIDAndName(){
		
		NodeList root = doc.getElementsByTagName("XMI.content");
		
		for (int i = 0; i < root.getLength(); ++i) {
			Node node = root.item(i);
			NodeList childList = node.getChildNodes();

			for (int j = 0; j < childList.getLength(); ++j) {
				Node childNode = childList.item(j);
				if (childNode.getNodeName() == "Model_Management.Model") {
					// System.out.println(childNode.getNodeName());
					NodeList classList = childNode.getChildNodes();

					for (int k = 0; k < classList.getLength(); k++) {
						Node childClassList = classList.item(k);

						if (childClassList.getNodeName() == "Foundation.Core.Namespace.ownedElement") {
							// /System.out.println(childClassList.getNodeName());
							NodeList allClasses = childClassList
									.getChildNodes();

							for (int l = 0; l < allClasses.getLength(); l++) {
								Node allClassesList = allClasses.item(l);

								if (allClassesList.getNodeName() == "Model_Management.Package") {
									// System.out.println(allClassesList.getNodeName());
									NodeList classNode = allClassesList
											.getChildNodes();
									for (int m = 0; m < classNode
											.getLength(); m++) {
										Node cCNode = classNode.item(m);
										if (cCNode.getNodeName() == "Foundation.Core.Namespace.ownedElement") {
											NodeList cCnoedList = cCNode
													.getChildNodes();

											for (int d = 0; d < cCnoedList.getLength(); d++) {
												Node cNode = cCnoedList.item(d);
												if (cNode.getNodeName() == "Foundation.Core.Class")// classes
												{
												    System.out.println(cNode.getNodeName());
													
												    Element element1 = (Element) cCnoedList.item(d);
												    System.out.println("class ID" + element1.getAttribute("xmi.id"));
												    
												    NodeList nodeClass = cNode.getChildNodes();
												
												    for (int f = 0; f < nodeClass.getLength(); f++)// class list
													{
														
														Node classes = nodeClass.item(f);

														if (classes.getNodeName() == "Foundation.Core.ModelElement.name") {
															Element element2 = (Element) nodeClass.item(f);
															NodeList clsName = element2.getChildNodes();
															
															System.out.println("Class Name : "
																	+ ((clsName.item(0)).getNodeValue()));
															
															
															classMap.put(element1.getAttribute("xmi.id").toString(), clsName.item(0).getNodeValue().toString());
															
														}
													}
												
												}
												else if(cCNode.getNodeName() == "Foundation.Core.Interface"){
													
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
	}
	/******************************** End Method ************************************************/
	/**
	 * method for get data type
	 * @param Nodelist
	 */
	public void getDataType(NodeList ownElemant) {

		// Map <String,String> dataTypeMap = new HashMap<String , String>();
		for (int a = 0; a < ownElemant.getLength(); a++) {
			Node dataType = ownElemant.item(a);

			if (dataType.getNodeName() == "Foundation.Core.DataType") {

				NodeList dataTypeChild = dataType.getChildNodes();

				Element dataTypeId = (Element) ownElemant.item(a);
				

				for (int b = 1; b < dataTypeChild.getLength(); b++) {
					Node dtChildName = dataTypeChild.item(b);

					if (dtChildName.getNodeName() == "Foundation.Core.ModelElement.name") {

						Element element1 = (Element) dataTypeChild.item(b);
						NodeList dataTypeName = element1.getChildNodes();
						
						String res = dataTypeName.item(0).getNodeValue().toString();
						System.out.println("Data types ========" + res);

						if(res.equals("string") || res.equals("String")||res.equals("wstring")){  
							
							//charAt gets the first character in string and upperCase converts it to uppercase	
							//then I add the uppercase J to the rest of the String with substring starting at the second
							//character in the string java, so basically:  J + ava -> Java
							res = Character.toUpperCase(res.charAt(0)) + res.substring(1);
							
							//System.out.println("44444444444    =" + res);
							dataTypeMap.put(dataTypeId.getAttribute("xmi.id")
									.toString(), res);
						
						}else if(res.equals("Double") || res.equals("double")|| res.equals("long double")||
								res.equals("double")|| res.equals("double")|| res.equals("double") ||res.equals("decimal")){
							
							res = Character.toLowerCase(res.charAt(0)) + res.substring(1);
							dataTypeMap.put(dataTypeId.getAttribute("xmi.id")
									.toString(), "double");
							
						}else if(res.equals("short") || res.equals("unsigned short")||
								res.equals("unsigned short")|| res.equals("signed short")|| res.equals("ushort")||
								res.equals("ushort") ||res.equals("Short")){
							
							
							dataTypeMap.put(dataTypeId.getAttribute("xmi.id")
									.toString(), "short");
							
						}else if(res.equals("int")||res.equals("unsigned int")||res.equals("signed int")||res.equals("uint")
								||res.equals("Integer")){
							
							dataTypeMap.put(dataTypeId.getAttribute("xmi.id")
									.toString(), "int");
							
						}else if(res.equals("long")||res.equals("unsigned long")||
								res.equals("signed long")||res.equals("long double")||res.equals("ulong")||
								res.equals("long long")||res.equals("unsigned long long")||res.equals("Long")){
							
							dataTypeMap.put(dataTypeId.getAttribute("xmi.id")
									.toString(), "long");
						
						}else if(res.equals("byte")||res.equals("sbyte")||res.equals("Byte")){
							
							dataTypeMap.put(dataTypeId.getAttribute("xmi.id")
									.toString(), "byte");
						}else if(res.equals("char")||res.equals("unsigned char")||res.equals("signed char")||
								res.equals("wchar_t")||res.equals("wchar")||res.equals("Char")){
						
							dataTypeMap.put(dataTypeId.getAttribute("xmi.id")
									.toString(), "char");
							
						}else if(res.equals("Boolean")||res.equals("boolean")||res.equals("bool")){
							
							dataTypeMap.put(dataTypeId.getAttribute("xmi.id")
									.toString(), "boolean");
						
						}else if(res.equals("object")){
							
							dataTypeMap.put(dataTypeId.getAttribute("xmi.id")
									.toString(), "Object");
						//	JOptionPane.showMessageDialog(null,"Can't process at the moment ! Error" );
						}
						
						else{
							
							dataTypeMap.put(dataTypeId.getAttribute("xmi.id")
									.toString(), "Object");
//						
						}

						
					}
				}

			}

		}

	}
	/******************************** End Method ************************************************/
	
	/**
	 * method for process relations
	 * amoung classes and stored 
	 * in the map
	 */
	public void processRelations(){
		
		NodeList root = doc.getElementsByTagName("XMI.content");
		
		
		for(int i = 0; i < root.getLength(); i++ ){
			
			Node node = root.item(i);
			NodeList childList = node.getChildNodes();
			
				for(int j = 0; j < childList.getLength(); j++){
					
					Node childNode = childList.item(j);
					
					if (childNode.getNodeName() == "Model_Management.Subsystem") {
						
						NodeList nodeOwnEle = childNode.getChildNodes();
						
						for(int k = 0; k < nodeOwnEle.getLength(); k++  ){
							
							Node childOwnEle = nodeOwnEle.item(k);
							
							if(childOwnEle.getNodeName() == "Foundation.Core.Namespace.ownedElement"){
								
								NodeList nodeStreoType = childOwnEle.getChildNodes();
								
								for(int l = 0; l < nodeStreoType.getLength(); l++ ){
									
									Node childStreoType = nodeStreoType.item(l);
									
									if(childStreoType.getNodeName() == "Foundation.Extension_Mechanisms.Stereotype"){
										
										Element element1 = (Element) nodeStreoType.item(l);
										//NodeList dataTypeName = element1.getChildNodes();
										
										System.out.println(element1.getAttribute("xmi.id"));
										
										
											NodeList streoName = childStreoType.getChildNodes();
											
											for(int n = 0; n < streoName.getLength(); n++ ){
												
												Node streoNameType = streoName.item(n);
												if(streoNameType.getNodeName() == "Foundation.Core.ModelElement.name"){
													
													Element element2 = (Element) streoName.item(n);
													
													NodeList name = element2.getChildNodes();
													
													assoTypeMap.put(element1.getAttribute("xmi.id").toString(), (name.item(0)).getNodeValue().toString());
													
													System.out.println("Accosiation Name : "
															+ assoTypeMap.get(element1.getAttribute("xmi.id")));
													
													
												}
												
											}
									}
									
								}
								
							}
							
						
						}
					
					}
				}
		}
	
		
	}
	/******************************** End Method ************************************************/
	
	
	
	
	
	public final static String getElementValue(Node elem) {
		Node kid;
		if (elem != null) {
			if (elem.hasChildNodes()) {
				for (kid = elem.getFirstChild(); kid != null; kid = kid
						.getNextSibling()) {
					if (kid.getNodeType() == Node.TEXT_NODE) {
						return kid.getNodeValue();
					}
				}
			}
		}
		return "";
	}

	public Document parserXML(File file) throws SAXException, IOException,
			ParserConfigurationException {
		return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
				file);
	}

	//	
	// public void parseFile(String fileName,DataStorage dStorage){
	//		
	// new VisioXMLPaser();
	// System.out.print("Visio");
	// }
}
