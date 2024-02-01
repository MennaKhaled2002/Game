package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import engine.Game;

public class GameView extends JFrame{
	JPanel firstPlayer;
	JPanel board;
	JPanel secondPlayer;
	JPanel actions;
	JPanel Queue;
	TextArea champDetails1;
	TextArea champDetails2;
	public JPanel getActions() {
		return actions;
	}
	public GameView() {
		this.setBounds(100, 100, 800, 800);
		this.setTitle("GAME BOARD");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		firstPlayer=new JPanel();
		board=new JPanel();
		secondPlayer=new JPanel();
		actions=new JPanel();
		Queue=new JPanel();
		Queue.setPreferredSize(new Dimension(80,30));
		firstPlayer.setPreferredSize(new Dimension(160,this.getHeight()));
		firstPlayer.setBackground(Color.YELLOW);
		board.setLayout(new GridLayout(5,5));
		Queue.setBackground(Color.LIGHT_GRAY);
		secondPlayer.setPreferredSize(new Dimension(160,this.getHeight()));
		secondPlayer.setBackground(Color.RED);
		actions.setPreferredSize(new Dimension(150,150));
		this.add(Queue,BorderLayout.NORTH);
		this.add(firstPlayer,BorderLayout.WEST);
		this.add(board,BorderLayout.CENTER);
		this.add(secondPlayer,BorderLayout.EAST);
		actions.setBackground(Color.DARK_GRAY);
		this.add(actions,BorderLayout.SOUTH);
		firstPlayer.setLayout(new FlowLayout());
		secondPlayer.setLayout(new FlowLayout());
		actions.setLayout(new FlowLayout());
		this.setVisible(true);
		this.revalidate();
		this.repaint();
		
	}
	public JPanel getQueue() {
		return Queue;
	}
	public JPanel getFirstPlayer() {
		return firstPlayer;
	}
	public JPanel getBoard() {
		return board;
	}
	public JPanel getSecondPlayer() {
		return secondPlayer;
	}
	public static void main(String[]args) throws IOException {
		new GameView();
	}
}
