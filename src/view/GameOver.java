package view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameOver extends JFrame{
	ImageIcon image;
	JLabel l;
	JPanel panel;
	public GameOver() {
		image=new ImageIcon("GameOver.jpg");
		panel=new JPanel();
		l=new JLabel();
		panel.add(l);
		panel.setBounds(this.getBounds());
		this.add(panel);
		l.setIcon(image);
		this.setBounds(100,100,800,800);
		panel.setBackground(Color.DARK_GRAY);
		this.setVisible(true);
		this.repaint();
		this.revalidate();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public static void main(String[] args) {
		new GameOver();
	}
}
