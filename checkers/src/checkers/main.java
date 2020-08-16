package checkers;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class main {
	//setting up images for each piece
			static ImageIcon black_piece_image = new ImageIcon("C:/Users/ctorr/git/Checkers/checkers/src/resources/black_piece.png");
			static ImageIcon black_king_image = new ImageIcon("C:/Users/ctorr/git/Checkers/checkers/src/resources/black_king.png");
			static ImageIcon red_piece_image = new ImageIcon("C:/Users/ctorr/git/Checkers/checkers/src/resources/red_piece.png");
			static ImageIcon red_king_image = new ImageIcon("C:/Users/ctorr/git/Checkers/checkers/src/resources/red_king.png");
			static ArrayList<Integer> movement_pieces = new ArrayList<Integer>();
			static ArrayList<Integer> possibleMoves = new ArrayList<Integer>();
			static boolean movementCheck = false;
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
		movement_pieces.clear();
		buttons[a].setActionCommand("unpressed");
		buttons[a].setActionCommand("unpressed");
		if (buttons[a].getIcon() != null && buttons[b].getIcon() == null)
		{
			buttons[b].setIcon(buttons[a].getIcon());
			buttons[a].setIcon(null);
		}
		else 
		{System.out.println("Invalid Move");}
		UpgradeCheck(buttons, b);
		possibleMoves.clear();
	}
	
	//-------------------------------------------------------------------------------------------------//
	//Kills a piece and verifies if its the final piece of that side//
	private boolean DeathAndCheck(JButton[] buttons, final int j)
	{	
		if(buttons[j].getIcon() == black_piece_image || buttons[j].getIcon() == black_king_image)
		{
			buttons[j].setIcon(null);
			System.out.println("A black piece has been killed!");
			for (int i = 0; i <64; i++)
			{if(buttons[i].getIcon() == black_piece_image || buttons[i].getIcon() == black_king_image) {return false;}}
		}
		
		else 
		{
			buttons[j].setIcon(null);
			System.out.println("A red piece has been killed!");
			for (int i = 0; i <64; i++)
			{if(buttons[i].getIcon() == red_piece_image || buttons[i].getIcon() == red_king_image) {return false;}}
		}
		
		return true;
	}
	//-------------------------------------------------------------------------------------------------------//
	//Calculates and returns the possible moves that one specific piece can make
	private int[] PossibleMoves(JButton[] buttons, int i)
	{
		if(buttons[i].getIcon() == black_piece_image)
		{
			if (i % 8 == 0) {possibleMoves.add(i + 9);}
			else if (i % 8 == 6) {possibleMoves.add(i + 7);}
			else {possibleMoves.add(i + 7);
				  possibleMoves.add(i + 9);}
		}
		else if(buttons[i].getIcon() == red_piece_image) 
		{
			if (i % 8 == 0) {possibleMoves.add(i - 7);}
			else if (i % 8 == 6) {possibleMoves.add(i - 9);}
			else {possibleMoves.add(i + 7);
				  possibleMoves.add(i + 9);}
		}
		else if(buttons[i].getIcon() == black_king_image) {return null;}
		else if(buttons[i].getIcon() == red_king_image) {return null;}
		return null;
	}
	//-------------------------------------------------------------------------------------------------------//
	//sets up Jframe with the pieces on each of the buttons
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
				if((i)% 8 == 0)
				{buttons[i].setBackground(buttons[i - 1].getBackground());}
				else 
				{
				if (buttons[i - 1].getBackground() == Color.red)
					{buttons[i].setBackground(Color.black);}
				else {buttons[i].setBackground(Color.red);}
				System.out.println(i);
				}
				gridPanel.add(buttons[i]);
			}
			
			for(int i = 0; i < 24; i++)
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
	//Verifies if a specific piece is viable to be uprgraded to king and does so if its true//
	 private static void UpgradeCheck (JButton[] buttons, int a)
	 {
		 if (a < 8 && buttons[a].getIcon() == red_piece_image)
		 {buttons[a].setIcon(red_king_image);
		 System.out.println("The red side uprgraded one of their pieces to a king!");}
		 else if (a > 56 && buttons[a].getIcon() == black_piece_image)
		 {buttons[a].setIcon(black_king_image);
		 System.out.println("The black side uprgraded one of their pieces to a king!");}
		 }
	//--------------------------------------------------------------------------------------------------------//
	//set up interactions between buttons//
		public static void giveCommands(JButton[] buttons)
		{
			ActionListener test = new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String state = e.getActionCommand();
					if (state == "unpressed")
					{
						for(int i = 0; i < 64; i++)
						{
							if (e.getSource().equals(buttons[i]))
							{
								buttons[i].setActionCommand("pressed");
								movement_pieces.add(i);
								if(movement_pieces.size()==4) 
								{
								Move(buttons, movement_pieces.get(1), movement_pieces.get(2));
								buttons[i].setActionCommand("unpressed");
								}
								System.out.println(movement_pieces);
							}
						}
					}
					else if (state == "pressed")
					{
						for (int i = 0; i < 64; i++)
						{
							if (e.getSource().equals(buttons[i]))
							{
								buttons[i].setActionCommand("unpressed");
								movement_pieces.clear();
								System.out.println(movement_pieces);
							}
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
		}
	//-------------------------------------------------------------------------------------------------------//
