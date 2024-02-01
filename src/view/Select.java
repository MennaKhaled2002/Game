package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import engine.*;
import model.world.*;

public class Select extends JFrame{
	private TextArea details;
	JPanel text;
	JPanel champs;
	JPanel team1;
	JPanel team2;
	JPanel selection;
	JPanel teams;
	JPanel title;
	public Select() throws IOException {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setBounds(100, 100, 1200, 1000);
		champs=new JPanel();
		team1=new JPanel();
		team2=new JPanel();
		text=new JPanel();
		selection=new JPanel();
		teams=new JPanel();
		title=new JPanel();
		title.setPreferredSize(new Dimension(100,100));
		text.setPreferredSize(new Dimension(400,this.getHeight()));
		team1.setPreferredSize(new Dimension(200,200));
		team2.setPreferredSize(new Dimension(200,200));
		details=new TextArea();
		details.setPreferredSize(new Dimension(400,this.getHeight()));
		champs.setSize(new Dimension(200,200));
		champs.setLayout(new GridLayout(0,3));
		this.add(title,BorderLayout.NORTH);
		title.add(new JLabel("SELECT YOUR CHAMPIONS"));
		this.add(selection,BorderLayout.WEST);
		selection.add(champs);
		this.add(text,BorderLayout.EAST);
		text.add(details);
		this.add(teams,BorderLayout.CENTER);
		teams.add(team1);
		teams.add(team2);
		team1.setLayout(new GridLayout(3,1));
		team2.setLayout(new GridLayout(3,1));
		teams.setBackground(Color.GRAY);
		title.setBackground(Color.GRAY);
		champs.setBackground(Color.GRAY);
		selection.setBackground(Color.GRAY);
		details.setEditable(false);
		this.setVisible(true);
		this.revalidate();
		this.repaint();
	}
	public void selectLeader(ArrayList<Champion> fp,ArrayList<Champion> sp) {
		for(int i=0;i<fp.size();i++) {
			
		}
	}
	
	public JPanel getText() {
		return text;
	}
	public JPanel getTeam1() {
		return team1;
	}
	public JPanel getTeam2() {
		return team2;
	}
	public JPanel getChamps() {
		return champs;
	}

	public void setChamps(JPanel champs) {
		this.champs = champs;
	}

	public TextArea getDetails() {
		return details;
	}

	public void setDetails(TextArea details) {
		this.details = details;
	}


/*	public void actionPerformed(ActionEvent e) {
			Selected= (JButton) e.getSource();
			int index= champions.indexOf(Selected);
			s+= Game.getAvailableChampions().get(index).toString() + "\n______________\n";
			details.setText(s);
			Selected.setEnabled(false);

	}
	*/
	
	public static void main(String[]args) throws IOException {
		  new Select();
	  }

	
	
}
