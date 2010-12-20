package lk.ac.kln.codegenerator.ui;

import javax.swing.*;
import java.awt.*;
public class Splash{


	public static void showSplash(int duration){
		JWindow splash=new JWindow();
		JPanel content=(JPanel)splash.getContentPane();

		int width=395;
		int height=310;
		Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
		int x=(screen.width-width)/2;
		int y=(screen.width-width)/2;
		splash.setBounds(x,y,width,height);

		Icon hed=new ImageIcon("images/about.gif");
		JLabel label=new JLabel(hed);
		content.add(label);
		splash.setVisible(true);

		try{
			Thread.sleep(duration);}
			catch(Exception e){}
			splash.setVisible(false);
		}
	}
