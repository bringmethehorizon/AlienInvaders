package alienInvaders;

/**
 * 
 * @author Teodor Merodiyski
 * 
 *
 */
import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import javax.swing.*;

public class AlienInvaders extends JFrame implements GameConstants
{

	JPanel panel=new JPanel(new BorderLayout());
	private static final long serialVersionUID = 1L;
	/**
	 * AlienInvaders constructor. 
	 * Creates menus, sets actionListenters and creates new class Battlefield.
	 */
	public AlienInvaders()
	{
		JMenuBar menuBar = new JMenuBar();
		JMenu menu1 = new JMenu("Menu");
		JMenu menu2 = new JMenu("Help");
		JMenuItem item1 = new JMenuItem("New Game!");
		JMenuItem item2 = new JMenuItem("Highscores");
		JMenuItem item3 = new JMenuItem("Help");
		JMenuItem item4 = new JMenuItem("About");
		item1.addActionListener(new NewGameActionListener());
		item2.addActionListener(new HighscoresActionListener());
		item3.addActionListener(new HelpActionListener());
		item4.addActionListener(new AboutActionListener());
		menu1.add(item1);
		menu1.add(item2);
		menu2.add(item3);
		menu2.add(item4);
		menuBar.add(menu1);
		menuBar.add(menu2);
		this.setJMenuBar(menuBar);
		
		panel.add(new Battlefield());
		add(panel);
		setTitle("Alien Invaders 1.0");
		setSize(BOARD_WIDTH, BOARD_HEIGTH);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
	}
	/**
	 * 
	 * ActionListener for the New Game button.
	 *
	 */
	class NewGameActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			panel.removeAll();
			panel.add(new Battlefield());
			AlienInvaders.this.setVisible(true);
		}
	}
	/**
	 * 
	 * ActionListener for the High Scores button.
	 *
	 */
	class HighscoresActionListener implements ActionListener
	{
		/**
		 * 
		 * @return the String with the high scores
		 * @throws FileNotFoundException if scores.inc does not exist
		 * @throws IOException if there is a problem with reading scores.inc
		 */
	    public String loadHighScores() throws FileNotFoundException, IOException
	    {
	    	String result="<html><body>";
	    	File f = new File("scores.inc");
	    	FileReader inF = new FileReader(f);
	    	BufferedReader in = new BufferedReader(inF);
	    	for(int i=0; i<10; i++)
	    	{
	    		String tmp_name = in.readLine();
	    		String tmp_score = in.readLine();
	    		result+=(i+1)+") "+tmp_name+" - "+tmp_score+"<br>";
	    	}
	    	in.close();
	    	return result;
	    }
		public void actionPerformed(ActionEvent e)
		{
			Battlefield bf=(Battlefield) panel.getComponent(0);
			bf.pause();
			try {
				JOptionPane.showMessageDialog(null, loadHighScores() , "Highscores", JOptionPane.INFORMATION_MESSAGE);
			} catch (HeadlessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			bf.repaint();
			bf.resume();
		}
	}
	/**
	 * 
	 * ActionListener for the Help button.
	 *
	 */
	class HelpActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			Battlefield bf=(Battlefield) panel.getComponent(0);
			bf.pause();
			JOptionPane.showMessageDialog(null, "<html><body>Use the left and right arrow keys to move.<br>Use space to shoot.<br>","Help",JOptionPane.INFORMATION_MESSAGE);
			bf.resume();
		}
	}
	/**
	 * 
	 * ActionListener for the About button.
	 *
	 */
	class AboutActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			Battlefield bf=(Battlefield) panel.getComponent(0);
			bf.pause();
			JOptionPane.showMessageDialog(null, "<html><body>AlienInvaders<br>Version: 1.0<br>(c) Teodor Merodiyski 2012<br>","Help",JOptionPane.INFORMATION_MESSAGE);
			bf.resume();
		}
	}
	
	public static void main(String[] args) 
	{
		new AlienInvaders();
	}
}
