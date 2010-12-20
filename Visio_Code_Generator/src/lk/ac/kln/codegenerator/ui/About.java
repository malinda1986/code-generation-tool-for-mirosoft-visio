package lk.ac.kln.codegenerator.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

public class About extends JFrame {

	//private JPanel helppanel = new JPanel();
	//private JPanel aboutpanel = new JPanel();
	private JPanel splashPanel = new JPanel();
	private JPanel virsionPanel = new JPanel();
	private JPanel creditPanel = new JPanel();
	private JPanel contactPanel = new JPanel();
	private JPanel legalPanel = new JPanel();
	private JPanel helptopic = new JPanel();

	private JTabbedPane tabbedPane = new JTabbedPane();
	private JPanel[] panels = { splashPanel, virsionPanel, creditPanel,
			contactPanel, legalPanel, helptopic };
	//private JTextArea area = new JTextArea(90, 90);
	private JTextArea virsion = new JTextArea(40, 40);
	private JTextArea credit = new JTextArea(40, 40);
	private JTextArea contact = new JTextArea(40, 40);
	private JTextArea legal = new JTextArea(50, 40);
	private JTextArea help = new JTextArea(40, 40);
	// Labels used at the status bar
//	private JLabel statusBar1;
//	private JLabel statusBar2;
//	private JLabel statusBar3;
//	private JLabel statusBar4;

	private JLabel label;
	//private Container container;
	private JScrollPane js;
	private JSplitPane jSplitPane;
	private JPanel splitTop;
	//private DefaultMutableTreeNode clsTree = null;
	//private JTree trees = null;
	private Container f, contentPane = null;
	String tabsNames[] = null;

	
	public About() {
		super("Code Generator");
		@SuppressWarnings("unused")
		JFrame frame = new JFrame();

		// buildGUI();
		f = getContentPane();
		f.add(tabbedPane, BorderLayout.CENTER);

		setSize(1000, 1000);
		setVisible(true);
	}

	public void buildGUI() { // helppanel.add(area);

		String virsionTxt = "--- CodeGenerator Version information: ---" + "\n";
		virsionTxt += " Virsion 1.0";
		virsion.setEditable(false);
		virsion.setText(virsionTxt);
		virsionPanel.add(virsion);

		String creditTxt = "--- CodeGenerator Uses following tools to Generate code: ---"
				+ "\n";
		creditTxt += "#Microsoft Visio 2003" + "\n" + "\n" + "\n" + "\n";
		creditTxt += "#XMI Export" + "\n" + "\n";
		creditTxt += "Introduction"
				+ "\n"
				+ "The XMI Export functionality for Visio® 2003 is available only"
				+ "\n"
				+ "through Visio automation by invoking the Run command in the Visio API."
				+ "\n" + "The Add-on is built "
				+ "using Visual C++ in Microsoft® Visual Studio® .NET 2003.";

		credit.setEditable(false);
		credit.setText(creditTxt);
		creditPanel.add(credit);

		String contactInfo = "---For more information on the CodeGenarator project:--"
				+ "\n" + "\n" + "\n";
		contactInfo += "E-mail - malindar11@gmail.com";

		contact.setEditable(false);
		contact.setText(contactInfo);
		contactPanel.add(contact);

		String legalTxt = "All Rights Reserved.  "
				+ "Permission to use, copy, modify," + "\n"
				+ "and distribute this software and "
				+ "its documentation without fee, and " + "\n"
				+ "without a written agreement is hereby ,"
				+ "grantedprovided that the above copyright " + "\n"
				+ "notice and this paragraph appear in all copies.";

		legal.setEditable(false);
		legal.setText(legalTxt);
		legalPanel.add(legal);

		String helpTxt = "All Rights Reserved.  "
				+ "Permission to use, copy, modify," + "\n"
				+ "and distribute this software and "
				+ "its documentation without fee, and " + "\n"
				+ "without a written agreement is hereby ,"
				+ "grantedprovided that the above copyright " + "\n"
				+ "notice and this paragraph appear in all copies.";

		help.setEditable(false);
		help.setText(helpTxt);
		helptopic.add(help);

		Icon about = new ImageIcon("images/about.gif");
		label = new JLabel(about);
		label.setHorizontalTextPosition(SwingConstants.RIGHT);
		splashPanel.add(label);
		splashPanel.setToolTipText("TextEditer");
		label.setToolTipText("Ranasinghe Malinda.   Index No:IM/2006/042    MIT");

		String[] tabs = { "Spalsh", "Virsion", "Credits", "Contact Info",
				"Lagal", "Help Topics" };
		String[] tabTips = { "Spalsh", "Virsion", "Credits", "Contact Info",
				"Lagal", "Help Topics" };
		for (int i = 0; i < tabs.length; ++i) {
			panels[i].setBackground(Color.lightGray);
			panels[i].setBorder(new TitledBorder(tabTips[i]));
			tabbedPane.addTab(tabs[i], null, panels[i], tabTips[i]);
		}
	}

	public void showHelpTopic() {

		contentPane = (JPanel) this.getContentPane();
		contentPane.setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 6));

		// First Half of the GUI

		splitTop = new JPanel();
		splitTop.setLayout(new BorderLayout());

		JPanel leftPanel, rightPanel;
		js = new JScrollPane();
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

		jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true,
				leftPanel, rightPanel);
		jSplitPane.setContinuousLayout(true);
		jSplitPane.setDividerLocation(250);// set Initial Location
		jSplitPane.setOneTouchExpandable(true);
		jSplitPane.setLastDividerLocation(200);// set Final Location
		//
		splitTop.add(jSplitPane, BorderLayout.CENTER);
		//
		// // Finish configuring splitTop

		contentPane.add(jSplitPane, BorderLayout.CENTER);

		// help.add(contentPane);
		//
		// show();

	}
}