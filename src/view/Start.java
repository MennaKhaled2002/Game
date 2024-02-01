package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.Controller;

public class Start extends JFrame {
	JButton button=new JButton("Start");
	JTextField text=new JTextField();
	JTextField text1=new JTextField();
	JPanel panel1=new JPanel();
	JPanel panel2=new JPanel();
	JLabel label1=new JLabel();
	JLabel label2=new JLabel();
	ImageIcon image;
	public Start() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(" WELCOME TO MARVEL ");
		label1.setText("First Player :");
		label2.setText("Second Player :");
		image=new ImageIcon("marvel.png");
		JLabel i=new JLabel(image);
		panel1.setLayout(new FlowLayout());
		panel2.setLayout(new BorderLayout());
		text.setPreferredSize(new Dimension(250,40));
		text1.setPreferredSize(new Dimension(250,40));
		panel1.add(label1);
		panel1.add(text);
		panel1.add(label2);
		panel1.add(text1);
		panel1.add(button);
	//	button.addActionListener(this);
		this.add(panel1,BorderLayout.NORTH);
		this.add(panel2,BorderLayout.CENTER);
		this.setSize(800,800);
		panel1.setBackground(Color.gray);
		panel2.setBackground(Color.RED);
		i.setIcon(image);
		panel2.add(i,BorderLayout.CENTER);
		this.setVisible(true);
		this.revalidate();
		this.repaint();
		
	}
	public JTextField getText() {
		return text;
	}
	public JButton getButton() {
		return button;
	}
	public JPanel getPanel1() {
		return panel1;
	}
	public JPanel getPanel2() {
		return panel2;
	}
	public JTextField getText1() {
		return text1;
	}
	public JLabel getLabel1() {
		return label1;
	}
	public JLabel getLabel2() {
		return label2;
	}
/*	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==button) {
			this.dispose();
			try {
				new Select();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		}*/
	
	public static void main(String[]args) {
		new Start();
	}
}
