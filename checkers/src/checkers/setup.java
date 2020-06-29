package checkers;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.*;

public class setup {

	public static void drawCheckerboard() {
		//setting buttons and grid as well as adding each button to the grid
		JPanel gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(8, 8));
		gridPanel.setSize(800, 800);
		
		JButton[] buttons = new JButton [64];
			buttons[0] = new JButton();
			buttons[0].setSize(100, 100);
			buttons[0].setBackground(Color.red);
			gridPanel.add(buttons[0]);
			for (int i = 1; i < buttons.length; i++) 
			{
				buttons[i] = new JButton();
				buttons[i].setSize(100, 100);
				if((i + 1)% 8 == 0)
				{
					buttons[i].setBackground(buttons[i - 1].getBackground());
				}
				else 
				{
				if (buttons[i - 1].getBackground() == Color.red)
					{buttons[i].setBackground(Color.black);}
				else {buttons[i].setBackground(Color.red);}
				gridPanel.add(buttons[i]);
				}
			}
		
		//jframe for board itself where the panel shall be added to
		JFrame board = new JFrame("Checkerboard");
		board.setSize(800, 800);
		board.add(gridPanel);
		board.setResizable(false);
		board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		board.setVisible(true);
		
	}
}
