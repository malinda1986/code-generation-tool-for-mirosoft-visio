package lk.ac.kln.codegenerator.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.tree.DefaultTreeCellRenderer;
import lk.ac.kln.codegenerator.code.JavaCodeGenerator;
import lk.ac.kln.codegenerator.controller.PaserController;
import lk.ac.kln.codegenerator.data.CodeGeneratorClasses;
import lk.ac.kln.codegenerator.data.CodeGeneratorMethods;
import lk.ac.kln.codegenerator.data.DataStorage;
import lk.ac.kln.codegenerator.xml.VisioXMLPaser;


import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.lang.Object.*;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.tree.*;
public class CodeWriter extends JFrame implements TreeSelectionListener {

	/**
	 *
	 */

	private JPanel panel;
	private JTabbedPane tabbedPane;
	private JButton exitbut, saveBut, copyBut,  openbut,
			lnfBut;
	public static ImageIcon leafIcon;
	private Icon  copy, save, open, exit, lookAndFeel;
	private JToolBar toolbar;
	private JScrollPane scroller, js;
	private JTextArea  text;
	private JPanel contentPane;
	private JPanel splitTop;
	private JProgressBar progressBar, progressBar1;
	private JButton garbageButton;
	private Runtime runtime;
	private StringBuffer buffer = null;
	private JSplitPane jSplitPane2;
	//private String clasName[];
	//private DefaultTreeModel treeModel;
	private JTree trees;
	private JRadioButton allFile, selectedFile;
	private DefaultMutableTreeNode clsTree;
	JTextArea[] panels = null;
	String tabsNames[] = null;
	static boolean res =  false;
	// Labels used at the status bar
	private JLabel statusBar1;
	private JLabel statusBar2;
	private JLabel statusBar3;
	private JLabel statusBar4;
	private JFrame optinFrame;
	private File fileOpenPath = null;
	private File fileSavePath = null;
	//private String lookAndFeel1 = null;
	PaserController ctrl = null;
	DataStorage dStorege = null;
	ConfigarationHandler config = null;

	public CodeWriter() {

		ctrl = new PaserController();
		dStorege = new DataStorage();
		config = new ConfigarationHandler();
		tabbedPane = new JTabbedPane();

		runtime = Runtime.getRuntime();
	}

	/**
	 * method for call methods
	 */
	public void createGUI() {
		buildMenuBar();
		buildContainer();
		
		try{
			UIManager.setLookAndFeel(config.getLooNFeel());
		}catch(Exception e) {
	      System.out.println("Error setting Java LAF: " + e);
	    }
		
		setSize(1000, 750);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

	}

	/*************************************** FUNCTION END **********************************************/

	/***
	 * method for create menus and nemu tiems File-> New,Save Edit ->Copy,Paste
	 * Look N Feel ->Motif, Help ->About,Help
	 * 
	 */
	public void buildMenuBar() {

		setTitle("Code Generator");

		JMenuBar menubar = new JMenuBar();

		open = new ImageIcon("images/open.gif");
		exit = new ImageIcon("images/exit.png");
		save = new ImageIcon("images/save.png");

		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);

		JMenuItem newFile = new JMenuItem("New File", open);
		newFile.setMnemonic(KeyEvent.VK_C);
		newFile.setToolTipText("Add New Xml File");

		newFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (tabsNames == null) {

					openFile();

				} else {
					int iOption = JOptionPane.showConfirmDialog(null,
							"Do want to Save this File!", "Code Generator",
							JOptionPane.YES_NO_OPTION);
					if (iOption == 0) {

						getSaveOption();
					} else {
						openFile();
					}

				}
			}
		});

		JMenuItem fileExit = new JMenuItem("Exit", exit);
		fileExit.setMnemonic(KeyEvent.VK_C);
		fileExit.setToolTipText("Exit");

		fileExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int iOption = JOptionPane.showConfirmDialog(null,
						"Are you sure you want to Exit!", "Code Generator",
						JOptionPane.YES_NO_OPTION);
				if (iOption == 0) {

					config.saveCofig();
				
					System.exit(0);
				}

			}
		});

		JMenuItem fileSave = new JMenuItem("Save", save);
		fileSave.setMnemonic(KeyEvent.VK_C);
		fileSave.setToolTipText("Save");

		fileSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (tabsNames == null) {

					JOptionPane.showMessageDialog(null, "Code Generator "
							+ "No file contain to save!");
				} else {
					getSaveOption();

				}
			}
		});

		file.add(newFile);
		file.add(fileSave);
		file.add(fileExit);

		JMenu edit = new JMenu("Edit");
		edit.setMnemonic(KeyEvent.VK_F);
		copy = new ImageIcon("images/copy.gif");

		JMenuItem copyText = new JMenuItem("Copy", copy);
		copyText.setMnemonic(KeyEvent.VK_C);
		copyText.setToolTipText("Copy Text");

		copyText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(null, "Area not selected",
						"Code Generator" + text.getSelectedText(),
						JOptionPane.ERROR_MESSAGE);
				try {
					int a = tabbedPane.getSelectedIndex();

					buffer = new StringBuffer(panels[a].getSelectedText());
				} catch (Exception copyexc) {
					JOptionPane.showMessageDialog(null, "Area not selected",
							"Code Generator", JOptionPane.ERROR_MESSAGE);
				}
			}

		});
		edit.add(copyText);

		JMenuItem pasteText = new JMenuItem("Paste", copy);
		pasteText.setMnemonic(KeyEvent.VK_C);
		pasteText.setToolTipText("Paste Text");

		pasteText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				try {

					text.append(buffer.toString());
				} catch (Exception copyexc) {
					JOptionPane.showMessageDialog(null, "Area not selected",
							"Code Generator", JOptionPane.ERROR_MESSAGE);
				}
			}

		});
		edit.add(pasteText);

		JMenu help = new JMenu("Help");
		help.setMnemonic(KeyEvent.VK_F);
		ImageIcon helpIco = new ImageIcon("images/about_icon.jpg");
		String test = "<html>\n"
        + "<body>\n"
        + "<h1>Welcome!</h1>\n"
        + "<h2>This is an H2 header</h2>\n"
        + "<p>This is some sample text</p>\n"
        + "<p><a href=\"http://devdaily.com/blog/\">devdaily blog</a></p>\n"
        + "</body>\n";
		JMenuItem about = new JMenuItem("About", helpIco);
		about.setMnemonic(KeyEvent.VK_C);
		about.setToolTipText("About");

		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				About as = new About();
				as.setBounds(150, 140, 400, 400);
				as.buildGUI();
			}

		});

		help.add(about);

		JMenuItem helpTopic = new JMenuItem(test, helpIco);
		helpTopic.setMnemonic(KeyEvent.VK_C);
		helpTopic.setToolTipText("Help Topic");

		helpTopic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				
//				About as = new About();
//				as.showHelpTopic();
//				// as.setBounds(150, 140, 400, 400);
			}

		});

		//help.add(helpTopic);

		JMenu lookAndFeel = new JMenu("Look and Feel");
		lookAndFeel.setMnemonic(KeyEvent.VK_F);

		JMenuItem nativeLnF = new JMenuItem("Native");
		nativeLnF.setMnemonic(KeyEvent.VK_F);
		nativeLnF.setToolTipText("Native");

		nativeLnF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				WindowUtilities.setNativeLookAndFeel();
				SwingUtilities.updateComponentTreeUI(CodeWriter.this);
				
				//String lookAndFeel1 = UIManager.getCrossPlatformLookAndFeelClassName();
				//config.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel");
			}

		});

		JMenuItem javaLnF = new JMenuItem("Java");
		javaLnF.setMnemonic(KeyEvent.VK_F);
		javaLnF.setToolTipText("Java");

		javaLnF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				WindowUtilities.setJavaLookAndFeel();
				SwingUtilities.updateComponentTreeUI(CodeWriter.this);
				//String lookAndFeel1 = UIManager.getCrossPlatformLookAndFeelClassName();
				//onfig.setLookAndFeel(lookAndFeel1);
			}

		});

		JMenuItem mtifLnF = new JMenuItem("Motif");
		mtifLnF.setMnemonic(KeyEvent.VK_F);
		mtifLnF.setToolTipText("Motif");

		mtifLnF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				WindowUtilities.setMotifLookAndFeel();
				SwingUtilities.updateComponentTreeUI(CodeWriter.this);
				//String lookAndFeel1 = UIManager.getCrossPlatformLookAndFeelClassName();
				//config.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");

			}

		});

	
		
		lookAndFeel.add(nativeLnF);
		lookAndFeel.add(javaLnF);
		lookAndFeel.add(mtifLnF);
	
		menubar.add(file);
		//menubar.add(edit);
		menubar.add(lookAndFeel);
		menubar.add(help);

		setJMenuBar(menubar);

	}

	/*************************************** FUNCTION END **********************************************/

	/**
	 * Method that create other GUI components create tool for open, save,copy
	 * actions and status bar
	 */
	public void buildContainer() {

		contentPane = (JPanel) this.getContentPane();
		contentPane.setLayout(new BorderLayout());

		toolbar = new JToolBar();
		// Open button
		open = new ImageIcon("images/open.gif");
		openbut = new JButton(open);
		openbut.setToolTipText("Open File");
		openbut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (tabsNames == null) {
					
					tabbedPane.removeAll();

					DefaultTreeModel models = (DefaultTreeModel) trees.getModel();
					clsTree.removeAllChildren();

					models.reload();
					openFile();

				} else {
					int iOption = JOptionPane.showConfirmDialog(null,
							"Do want to Save this File!", "Code Generator",
							JOptionPane.YES_NO_OPTION);
					if (iOption == 0) {

						getSaveOption();
					} else {
						tabbedPane.removeAll();

						DefaultTreeModel models = (DefaultTreeModel) trees.getModel();
						clsTree.removeAllChildren();

						models.reload();
						openFile();
					}

				}
			}
		});

		// Save button
		save = new ImageIcon("images/save.png");
		saveBut = new JButton(save);
		saveBut.setToolTipText("Save File");
		saveBut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (tabsNames == null) {

					JOptionPane.showMessageDialog(null, " "
							+ "No file contain to save!");
				} else {
					getSaveOption();

				}

			}
		});

//		// Copy text
//		copy = new ImageIcon("images/copy.gif");
//		copyBut = new JButton(copy);
//		copyBut.setToolTipText("Copy text");
//		copyBut.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//
//			}
//		});

		// exit
		exit = new ImageIcon("images/exit.png");
		exitbut = new JButton(exit);
		exitbut.setToolTipText("Exit");

		exitbut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int ret = JOptionPane.showConfirmDialog(panel,
						"Are you sure you want to Exit");
				if (ret == 0) {
					
					config.saveCofig();
					System.exit(0);

				}
			}
		});

//		lookAndFeel = new ImageIcon("images/look-n-feel-icon.png");
//
//		lnfBut = new JButton(lookAndFeel);
//		lnfBut.setToolTipText("Change Look and Feel");
//
//		/**
//		 * 
//		 * CODE
//		 * 
//		 */

		toolbar.setLayout(new GridLayout(1, 20));

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 6));
		panel.add(openbut);
		//panel.add(copyBut);
		panel.add(saveBut);
		// panel.add(lnfBut);
		panel.add(new JPanel());
		panel.add(new JPanel());
		panel.add(new JPanel());
		panel.add(new JPanel());

		toolbar.add(panel);

		panel.add(exitbut);
		toolbar.add(new JPanel());

		contentPane.add(toolbar, BorderLayout.NORTH);
		// First Half of the GUI
		splitTop = new JPanel();
		splitTop.setLayout(new BorderLayout());

		// Configuring Progress Bar
		statusBar1 = new JLabel();
		statusBar2 = new JLabel();
		statusBar3 = new JLabel();
		statusBar4 = new JLabel();
		statusBar1.setText("Ranasinghe Malinda.   Index No:IM/2006/042   MIT");
//		statusBar4.setText("<html>" + "<font color=\"#008000\">" + "<b>"
//				+ "Running the Garbage Collector" + "</b>" + "</font>"
//				+ "</html>");

		statusBar1.setBorder(new EtchedBorder());
		statusBar2.setBorder(new EtchedBorder());
		statusBar3.setBorder(new EtchedBorder());
		statusBar4.setBorder(new EtchedBorder());

		progressBar = new JProgressBar(JProgressBar.HORIZONTAL);
		progressBar.setStringPainted(true);
		progressBar.setBorderPainted(true);

		progressBar1 = new JProgressBar(JProgressBar.HORIZONTAL);
		progressBar1.setStringPainted(true);
		progressBar1.setBorderPainted(true);
		progressBar1.setVisible(false);

		garbageButton = new JButton();
		garbageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				statusBar3.setText(" ");
				runtime.gc();
				statusBar3.setText("");
			}
		});

		TimerTask task = new TimerTask() {
			public void run() {
				runtime.gc();

			}

		};
		Timer timer1 = new Timer(true);
		timer1.schedule(task, 10, 7000);

		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double total = runtime.totalMemory();
				double freeMem = runtime.freeMemory();
				double freeMemInt = Math.round(freeMem / 1000000);
				double totalInt = Math.round(total / 1000000);
				double percent = ((total - freeMem) / total) * 100;
				progressBar.setValue((int) percent);
				progressBar.setString((int) (totalInt - freeMemInt) + "M of "
						+ (int) totalInt + "M");
			}
		};

		javax.swing.Timer timer = new javax.swing.Timer(100, listener);
		timer.start();

		JPanel progressPanel = new JPanel();
		JPanel panelStatusBar = new JPanel();
		panelStatusBar.setLayout(new BorderLayout());

		panelStatusBar.add(progressBar, BorderLayout.CENTER);
		panelStatusBar.add(garbageButton, BorderLayout.EAST);

		GridLayout gL = new GridLayout(1, 4, 0, 0);
		progressPanel.setLayout(gL);
		progressPanel.add(statusBar1);
		progressPanel.add(statusBar2);
		progressPanel.add(progressBar1);
		progressPanel.add(statusBar4);
		progressPanel.add(panelStatusBar);

		contentPane.add(progressPanel, BorderLayout.SOUTH);

		splitePane();

		// Finished adding progress Bar

	}

	/*************************************** FUNCTION END **********************************************/

	/**
	 * Method split the main pane
	 */



	public void splitePane() {
		

		 
		JPanel leftPanel, rightPanel;
		
		leafIcon = new ImageIcon ("images/jcu_obj.gif");
		 
		 
		clsTree = new DefaultMutableTreeNode(new IconData(leafIcon,"Class"));
		
		//DefaultMutableTreeNode	 clsTree = new DefaultMutableTreeNode( new IconData(leafIcon, "Myself"));
		trees = new JTree(clsTree);
//		clsTree = new DefaultMutableTreeNode(
//				      new IconData(leafIcon, null, "Computer"));

		
		 
		trees.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);

		// Listen for when the selection changes.
		trees.addTreeSelectionListener(this);
		js = new JScrollPane(trees);

		leftPanel = new JPanel();

		leftPanel.setLayout(new BorderLayout());
		JLabel originalLable = new JLabel("Class Tree", JLabel.CENTER);
		leftPanel.add(originalLable, BorderLayout.NORTH);

		leftPanel.add(js, BorderLayout.CENTER);

		// Finish configuring the leftPanel

		// Right Side of the Top Half

		rightPanel = new JPanel();

		rightPanel.setLayout(new BorderLayout());
		JLabel modifyLable = new JLabel("Classes", JLabel.CENTER);
		rightPanel.add(modifyLable, BorderLayout.NORTH);
		rightPanel.add(tabbedPane, BorderLayout.CENTER);

		// Finish configuring the rightPanel

		jSplitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true,
				leftPanel, rightPanel);
		jSplitPane2.setContinuousLayout(true);
		jSplitPane2.setDividerLocation(250);// set Initial Location
		jSplitPane2.setOneTouchExpandable(true);
		jSplitPane2.setLastDividerLocation(200);// set Final Location

		splitTop.add(jSplitPane2, BorderLayout.CENTER);

		// Finish configuring splitTop

		contentPane.add(jSplitPane2, BorderLayout.CENTER);

	}

	/*************************************** FUNCTION END **********************************************/

	/**
	 * method for open file
	 * 
	 */
	public void openFile() {

		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		String fileName = null;

		JFileChooser fileChooser = new JFileChooser();
		File directory = new File(config.getOpenPath());
		fileChooser.setCurrentDirectory(directory);

		FileFilter filter = new FileNameExtensionFilter("xml files", "xml");
		fileChooser.addChoosableFileFilter(filter);

		int ret = fileChooser.showDialog(panel, "Open file");

		if (ret == JOptionPane.OK_OPTION) {

			// set last file open path
			fileOpenPath = fileChooser.getCurrentDirectory();
			config.setOpenPath(fileOpenPath.toString());

			fileName = fileChooser.getSelectedFile().getPath();
			ctrl.parseXmlFile("Visio", 1, fileName);
			progressBar1.setVisible(true);
			progressBar1.setValue(100);

			if (VisioXMLPaser.checkProsecc) {

				String status = "File Processed.";
				progressBar1.setString(status);
				createTab();
			} else {
				String status = "File Processed Failed.";
				progressBar1.setString(status);
			}

		} else if (ret == JOptionPane.CANCEL_OPTION) {

		}

	}

	/*************************************** FUNCTION END **********************************************/

	/**
	 * method for save selected file
	 */
	public void saveSlectedFile() {

		// get the code for string variable
		String code = panels[tabbedPane.getSelectedIndex()].getText();
		String tabName = tabsNames[tabbedPane.getSelectedIndex()];
		JFileChooser fChooser = new JFileChooser();
		
		File directory = new File(config.getSavePath());
		
		
		fChooser.setCurrentDirectory(directory);
		// set the file name
		fChooser.setSelectedFile(new File("" + File.separator + tabName));

		fChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int result = fChooser.showSaveDialog(this);
		if (result == JFileChooser.CANCEL_OPTION)
			return;

		File fileName = fChooser.getSelectedFile();

		// check the wether file exist
		if (fileName.exists()) {
		
			int iOption = JOptionPane.showConfirmDialog(null,
					"File name   "
					+ tabName + "\n" + "Already exist,You want to replace it", "Code Generator",
					JOptionPane.YES_NO_OPTION);
			if (iOption == 0) {

				try {
					FileWriter fstream = new FileWriter(fileName);
					BufferedWriter out = new BufferedWriter(fstream);
					out.write(code);
					out.flush();
					out.close();
					
					//set status to ok
					String status = "File saved.";
					progressBar1.setString(status);
					//clear the UI
					tabbedPane.removeAll();
					DefaultTreeModel models = (DefaultTreeModel) trees.getModel();
					clsTree.removeAllChildren();
					tabsNames = null;
					models.reload();
					
					//set status to ok
					String status2 = "File saved.";
					progressBar1.setString(status2);
					
					fileSavePath = fChooser.getCurrentDirectory();
					config.setSavePath(fileSavePath.toString());
					
					config.saveCofig();
				} catch (IOException saveexp) {
					JOptionPane.showMessageDialog(this, "Save", "Cant save !"
							+ saveexp.getMessage(), JOptionPane.ERROR_MESSAGE);
				}

			}

		} else {
			try {

				FileWriter fstream = new FileWriter(fileName);
				BufferedWriter out = new BufferedWriter(fstream);
				out.write(code);
				out.flush();
				out.close();

				fileSavePath = fChooser.getCurrentDirectory();
				config.setSavePath(fileOpenPath.toString());
			} catch (IOException saveexp) {
				JOptionPane.showMessageDialog(this, "Save", "Cant save !"
						+ saveexp.getMessage(), JOptionPane.ERROR_MESSAGE);
			}

		}

	}

	/*************************************** FUNCTION END **********************************************/

	/**
	 * method for save all files
	 */
	public void saveAllFiles() {

		for (int i = 0; i < panels.length; i++) {

			String code = null;
			String tabName = tabsNames[i];
			code = panels[i].getText();

			JFileChooser fChooser = new JFileChooser();
			File directory = new File(config.getSavePath());
			
			
			fChooser.setCurrentDirectory(directory);
			// set the file name
			fChooser.setSelectedFile(new File("" + File.separator + tabName));
			fChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int result = fChooser.showSaveDialog(this);
			if (result == JFileChooser.CANCEL_OPTION)
				return;

			File fileName = fChooser.getSelectedFile();
			// check the wether file exist
			if (fileName.exists()) {

				int iOption = JOptionPane.showConfirmDialog(null,
						"File name   "
						+ tabName + "\n" + "Already exist,You want to replace it", "Code Generator",
						JOptionPane.YES_NO_OPTION);
				if (iOption == 0) {
					try {
						FileWriter fstream = new FileWriter(fileName);
						BufferedWriter out = new BufferedWriter(fstream);
						out.write(code);
						out.flush();
						out.close();
						
						//set status to ok
						String status = "File saved.";
						progressBar1.setString(status);
						
						fileSavePath = fChooser.getCurrentDirectory();
						config.setSavePath(fileSavePath.toString());
						
						
					} catch (IOException saveexp) {
						JOptionPane.showMessageDialog(this, "Save",
								"Cant save !" + saveexp.getMessage(),
								JOptionPane.ERROR_MESSAGE);
					}

				}

			} else {
				try {

					FileWriter fstream = new FileWriter(fileName);
					BufferedWriter out = new BufferedWriter(fstream);
					out.write(code);
					out.flush();
					out.close();

					fileSavePath = fChooser.getCurrentDirectory();
					config.setSavePath(fileOpenPath.toString());

				} catch (IOException saveexp) {
					JOptionPane.showMessageDialog(this, "Save", "Cant save !"
							+ saveexp.getMessage(), JOptionPane.ERROR_MESSAGE);
				}

			}
		}
		//clear the UI
		tabbedPane.removeAll();
		DefaultTreeModel models = (DefaultTreeModel) trees.getModel();
		clsTree.removeAllChildren();
		tabsNames = null;
		models.reload();


	}

	/**
	 * method for create tab with code
	 * 
	 */
	public void createTab() {

		Map<String, CodeGeneratorClasses> classMap = ctrl.getDataStrorage()
				.getClassMap("");
		
		Icon leaf =new ImageIcon ("images/globe.png");
		
		Set<String> keys = classMap.keySet(); // The set of keys in the map.
		Iterator<String> keyIter = keys.iterator();
		int numOfCls = classMap.size();
		tabsNames = new String[numOfCls];
		tabbedPane.removeAll();

		DefaultTreeModel models = (DefaultTreeModel) trees.getModel();
		clsTree.removeAllChildren();

		models.reload();

		panels = new JTextArea[numOfCls];
		String tabName = "";

		for (int i = 0; i < numOfCls; i++) {

			String key = keyIter.next(); // Get the next key.
			CodeGeneratorClasses value = classMap.get(key); // Get the value for
			// that key.
			
	
			DefaultMutableTreeNode cls = new DefaultMutableTreeNode(
					value.getClassName() + ".java");
			
			adMethodTree(value, cls);
			clsTree.add(cls);

			
			DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
			 renderer.setOpenIcon(leafIcon);
			 if(!res){
				 renderer.setLeafIcon(leafIcon);
			 }else{
				renderer.setLeafIcon(leaf);
			 }
			 trees.setCellRenderer(renderer);
			 
			String code = ctrl.generateCode(value);

			text = new JTextArea(60, 60);

			panels[i] = text;

			text.setText(code);
			scroller = new JScrollPane(text);
			scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scroller.setBounds(3, 3, 300, 200);

			tabName = value.getClassName() + ".java";

			tabsNames[i] = tabName;
			tabbedPane.addTab(tabName, null, scroller, tabName);

		}
		models.reload();

	}

	/*************************************** FUNCTION END **********************************************/

	/**
	 * Add method list for class node
	 */
	public void adMethodTree(CodeGeneratorClasses value,
			DefaultMutableTreeNode cls) {

		Map<String, CodeGeneratorMethods> clsMethodMap = value
				.getClassMethodList();
		 leafIcon = new ImageIcon ("images/jcu_obj.gif");
		Set<String> keys = clsMethodMap.keySet(); // The set of keys in the map.
		Iterator<String> keyIter = keys.iterator();
		int numOfMethods = clsMethodMap.size();
		for (int i = 0; i < numOfMethods; i++) {

			String key = keyIter.next(); // Get the next key.
			CodeGeneratorMethods methodList = clsMethodMap.get(key); // Get the
																		// value
																		// for
																		// that
																		// key.
			String rType = JavaCodeGenerator.getMethodReturnType(methodList,
					methodList.getMethodName().toString());

			cls.add(new DefaultMutableTreeNode(methodList.getMethodName()
					+ "()  : " + rType));
			DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
			 renderer.setLeafIcon(leafIcon);
			 trees.setCellRenderer(renderer);
		}

	}

	/*************************************** FUNCTION END **********************************************/

	/**
	 * method for navigate through tree.
	 */
	public void valueChanged(TreeSelectionEvent e) {
		// Returns the last path element of the selection.
		// This method is useful only when the selection model allows a single
		// selection.
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) trees
				.getLastSelectedPathComponent();

		if (node == null)
			// Nothing is selected.
			return;

		Object nodeInfo = node.getUserObject();

		for (int a = 0; a < tabsNames.length; a++) {
			if (tabsNames[a].equals(nodeInfo)) {

				tabbedPane.setSelectedIndex(a);

			}
		}
	}

	/*************************************** FUNCTION END **********************************************/

	/**
	 * method for select Option for save files
	 */
	public void getSaveOption() {

		RadioButtonHandler handler = new RadioButtonHandler();
		optinFrame = new JFrame("Save file");
		JPanel panel = new JPanel();

		panel.setLayout(new GridLayout(10, 8));

		Label selectOption = new Label();

		selectOption.setText("Select Option");

		panel.add(selectOption);
		ButtonGroup buttonGroup = new ButtonGroup();
		allFile = new JRadioButton("Save all files");
		buttonGroup.add(allFile);
		panel.add(allFile);
		selectedFile = new JRadioButton("Save selected file");
		buttonGroup.add(selectedFile);
		panel.add(selectedFile);

		allFile.setSelected(false);
		allFile.addItemListener(handler);
		selectedFile.setSelected(false);
		selectedFile.addItemListener(handler);

		panel.setLayout(new GridLayout(4, 2));

		optinFrame.add(panel);
		optinFrame.setLocation(400, 170);
		optinFrame.setSize(200, 100);
		optinFrame.setVisible(true);
	}

	/*************************************** FUNCTION END **********************************************/

	
	private class RadioButtonHandler implements ItemListener {

		public void itemStateChanged(ItemEvent e) {
			if (e.getSource() == allFile) {
				optinFrame.dispose();
				saveAllFiles();

			}
			if (e.getSource() == selectedFile) {

				optinFrame.dispose();
				saveSlectedFile();

			}

		}
	}
	/*************************************** FUNCTION END **********************************************/

}
