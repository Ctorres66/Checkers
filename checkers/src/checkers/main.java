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
			
	//setting up arrayLists for pieces that shall move, possible moves that a selected piece can make and
	//which of those possible moves would kill a unit
	//KillCombo is a list of Arrays of size 3 in the format: [target, unit to be killed, destination]
			static ArrayList<Integer> movement_pieces = new ArrayList<Integer>();
			static ArrayList<Integer> possibleMoves = new ArrayList<Integer>();
			static ArrayList<Integer[]> KillMoves = new ArrayList<Integer[]>();
			static boolean movementCheck = false;
			
			
	//turn counter to keep in track the lengh of the game and also to use a tool for determining whos turn is it
			static int turnCounter = 1;
			
	//------------------------------------main code---------------------------------------------------//
	public static void main(String args[]) 
	{
	JButton[] buttons = drawCheckerboard();
	giveCommands(buttons);
	
	}
	//-------------------------------------------------------------------------------------------------//
	//Moves piece from tile A to tile B if it is a possible move
	private static void Move(JButton[] buttons, int a, int b, String PassOrFail)
	{	
		movement_pieces.clear();
		buttons[a].setActionCommand("unpressed");
		buttons[b].setActionCommand("unpressed");
		
		if(PassOrFail == "Pass")
		{
			if (buttons[a].getIcon() != null && buttons[b].getIcon() == null)
			{
			buttons[b].setIcon(buttons[a].getIcon());
			buttons[a].setIcon(null);
			}
			else  {System.out.println("Invalid Move.");}
			UpgradeCheck(buttons, b);
			System.out.println("Move done.");
		}
		else if (PassOrFail == "PFail")
		{System.out.println("Invalid Move.");}
		else if (PassOrFail == "KFail")
		{System.out.println("Invalid Move. You are able to eliminate an enemy unit with a diferent move.");}
		
		possibleMoves.clear();
		KillMoves.clear();
		
		}
	
	//-------------------------------------------------------------------------------------------------//
	//Kills a piece and verifies if its the final piece of that side//
	private static boolean DeathAndCheck(JButton[] buttons, final int j)
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
	//Calculates the possible moves that one specific piece, which would kill a unit and puts those moves into 
	//their respective ArrayLists
	private static void CalculatePossibleMoves(JButton[] buttons, int i)
	{
		if(buttons[i].getIcon() == black_piece_image)
		{
			if (i % 8 == 0) {possibleMoves.add(i + 9);}
			else if (i % 8 == 7) {possibleMoves.add(i + 7);}
			else {possibleMoves.add(i + 7);
				  possibleMoves.add(i + 9);}
		}
		else if(buttons[i].getIcon() == red_piece_image) 
		{
			if (i % 8 == 0) {possibleMoves.add(i - 7);}
			else if (i % 8 == 7) {possibleMoves.add(i - 9);}
			else {possibleMoves.add(i - 7);
				  possibleMoves.add(i - 9);}
		}
		else if(buttons[i].getIcon() == black_king_image || buttons[i].getIcon() == red_king_image ) 
		{
			possibleMoves.add(i + 7);
			possibleMoves.add(i + 9);
			possibleMoves.add(i - 7);
			possibleMoves.add(i - 9);
		}
		
		
		
		for (Integer numb : possibleMoves) 
		{
			if(buttons[i].getIcon() == black_piece_image || buttons[i].getIcon() == black_king_image)
			{
				if(buttons[numb].getIcon() == black_piece_image || buttons[numb].getIcon() == black_king_image) 
				{possibleMoves.remove(numb);}
				else if(buttons[numb].getIcon() == red_piece_image || buttons[numb].getIcon() == red_king_image) 
				{possibleMoves.remove(numb);
				if(buttons[numb].getIcon() == null) {
					Integer[] killMove = {i, numb, i + 2*(Math.abs(i - numb))};
					KillMoves.add(killMove);
				}
				}
				
				
			}
			
			else if(buttons[i].getIcon() == red_piece_image || buttons[i].getIcon() == red_king_image)
			{
				if(buttons[numb].getIcon() == red_piece_image || buttons[numb].getIcon() == red_king_image) 
				{possibleMoves.remove(numb);}
				else if(buttons[numb].getIcon() == black_piece_image || buttons[numb].getIcon() == black_king_image) 
				{
					possibleMoves.remove(numb);
					if(buttons[numb].getIcon() == null) {
						Integer[] killMove = {i, numb, i + 2*(Math.abs(i - numb))};
						KillMoves.add(killMove);
					}
				 }
			}
		}
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
				}
				gridPanel.add(buttons[i]);
			}
			
			for(int i = 0; i < 24; i++)
			{
				if (buttons[i].getBackground() == Color. black)
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
	//set up interactions between buttons, mainly how by pressing one button after another, it tries to make a
    //move in-game
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
								CalculatePossibleMoves(buttons, movement_pieces.get(1));
								System.out.println(possibleMoves);
								System.out.println(KillMoves);
								if (KillMoves.isEmpty())
								{
									if(possibleMoves.contains(movement_pieces.get(2)))
									{Move(buttons, movement_pieces.get(1), movement_pieces.get(2), "Pass");}
									else
									{Move(buttons, movement_pieces.get(1), movement_pieces.get(2), "PFail");}
								}
								
								
								else 
								{
									for (Integer[] KillCombo : KillMoves)
									{
										if(KillCombo[0] == movement_pieces.get(1)
												&& KillCombo[2] == movement_pieces.get(2))
										{
											Move(buttons, movement_pieces.get(1), movement_pieces.get(2), "Pass");
											DeathAndCheck(buttons, KillCombo[1]);
											break;
										}
									}
									if(movement_pieces.isEmpty() == false)
									{
										Move(buttons, movement_pieces.get(1), movement_pieces.get(2), "KFail");
									}
								}
								buttons[i].setActionCommand("unpressed");
								}
								//System.out.println(movement_pieces);
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
