package checkers;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class main {
	//setting up images for each piece
			static ImageIcon black_piece_image = new ImageIcon("C:/Users/ctorr/git/Checkers/checkers/src/resources/black_piece.png");
			static ImageIcon black_king_image = new ImageIcon("C:/Users/ctorr/git/Checkers/checkers/src/resources/black_king.png");
			static ImageIcon red_piece_image = new ImageIcon("C:/Users/ctorr/git/Checkers/checkers/src/resources/red_piece.png");
			static ImageIcon red_king_image = new ImageIcon("C:/Users/ctorr/git/Checkers/checkers/src/resources/red_king.png");
	
	//------------------------------------main code---------------------------------------------------//
	public static void main(String args[]) 
	{
		int turnCounter = 1;
	JButton[] buttons = drawCheckerboard();
	giveCommands(buttons);
	
	
	}
	//-------------------------------------------------------------------------------------------------//
	//Moves piece from tile A to tile B
	private static void Move(JButton[] buttons, int a, int b)
	{
		if (buttons[a].getIcon() != null && buttons[b].getIcon() == null)
		{
			buttons[b].setIcon(buttons[a].getIcon());
			buttons[a].setIcon(null);
		}
		UpgradeCheck(buttons, b);
		buttons[a].setActionCommand("unpressed");
		buttons[b].setActionCommand("unpressed");
	}
	
	//-------------------------------------------------------------------------------------------------//
	private boolean DeathAndCheck(JButton[] buttons, final int j)
	//Kills a piece and verifies if its the final piece of that side 
	{	
		if(buttons[j].getIcon() == black_piece_image || buttons[j].getIcon() == black_king_image)
		{
			buttons[j].setIcon(null);
			for (int i = 0; i <64; i++)
			{if(buttons[i].getIcon() == black_piece_image || buttons[i].getIcon() == black_king_image) {return false;}}
		}
		
		else 
		{
			buttons[j].setIcon(null);
			for (int i = 0; i <64; i++)
			{if(buttons[i].getIcon() == red_piece_image || buttons[i].getIcon() == red_king_image) {return false;}}
		}
		
		return true;
	}
	//-------------------------------------------------------------------------------------------------------//
	//Calculates and returns the possible moves that one specific piece can make
	private int[] PossibleMoves(JButton[] buttons, int i)
	{
		if(buttons[i].getIcon() == null) {return null;}
		return null;
	}
	//-------------------------------------------------------------------------------------------------------//
	//sets up jframe with the pieces on each of the buttons
	private static JButton[] drawCheckerboard() {

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
			
			for(int i = 0; i <= 24; i++)
			{
				if (buttons[i].getBackground() == Color.red)
				{
					buttons[i].setIcon(black_piece_image);
				}
				
				if (buttons[63 - i].getBackground() == Color.black)
				{
					buttons[63 - i].setIcon(red_piece_image);
				}
			}
		
		//jframe for board itself where the panel shall be added to
		JFrame board = new JFrame("Checkerboard");
		board.setSize(800, 800);
		board.add(gridPanel);
		board.setResizable(false);
		board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		board.setVisible(true);
		
		giveCommands(buttons);
		
		return buttons;
	}
	
	//-------------------------------------------------------------------------------------------------------//
	 private static void UpgradeCheck (JButton[] buttons, int a)
	 {
		 if (a < 8 && buttons[a].getIcon() == red_piece_image)
		 {buttons[a].setIcon(red_king_image);}
		 else if (a > 56 && buttons[a].getIcon() == black_piece_image)
		 {buttons[a].setIcon(black_king_image);}
		 }
	//--------------------------------------------------------------------------------------------------------//
	//set up interactions between buttons//
		public static void giveCommands(JButton[] buttons)
		{
			ActionListener test = new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					boolean movementCheck = false;
					int first = 0;
					int second = 0;
					String state = e.getActionCommand();
					if (state == "unpressed")
					{
						for (int i = 0; i < 64; i++)
						{
							if (e.getSource().equals(buttons[i]) == false)
							{
								if(buttons[i].getActionCommand() == "pressed")
								{
								first = i;
								movementCheck = true;
								}
							}
							else
							{
								buttons[i].setActionCommand("pressed");
								second = i;
							}	
						}
						if(movementCheck == true)
						{
							Move(buttons, first, second);
						}
				}
				}
			};
			for (int i = 0; i< 64; i++)
			{
				buttons[i].addActionListener(test);
				buttons[i].setActionCommand("unpressed");
			}
		}
	//-------------------------------------------------------------------------------------------------------//
}
