package Quiz;
import engine.*;
import model.abilities.CrowdControlAbility;
import model.abilities.DamagingAbility;
import model.abilities.HealingAbility;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Quiz3 extends JFrame implements ActionListener{
	JPanel panel;
	Game game;
	Player player1;
	Player player2;
	JButton Next;
	JButton type;
	JButton name;
	JButton counter;
	public Quiz3() throws IOException {
		this.setTitle("Quiz");
		this.setSize(800,800);
		panel=new JPanel();
		player1=new Player("menna");
		player2=new Player("mariam");
		panel.setLayout(new GridLayout(2,2));
		this.add(panel);
		name=new JButton("name");
		panel.add(name);
		name.addActionListener(this);
		type=new JButton("type");
		panel.add(type);
		game=new Game(player1,player2);
		type.addActionListener(this);
		counter=new JButton("counter");
		panel.add(counter);
		counter.addActionListener(this);
		JButton Next=new JButton("Next");
		Next.setActionCommand("next");
		Next.addActionListener(this);
		panel.add(Next);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.revalidate();
		this.repaint();
	}
	public static void main(String[]args) throws IOException {
		new Quiz3();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("next")) {
			int i=(int) (Math.random() * game.getAvailableAbilities().size());
			name.setText(game.getAvailableAbilities().get(i).getName());
			if ((game.getAvailableAbilities().get(i)instanceof HealingAbility)){
				type.setText("Healing ability");
			}
			if ( (game.getAvailableAbilities().get(i)instanceof DamagingAbility)){
				type.setText("Damaging ability");
			}
			if  (game.getAvailableAbilities().get(i)instanceof CrowdControlAbility){
				type.setText("Crowd control ability");
			}
			counter.setText(i+"");
			this.revalidate();
			this.repaint();
		}
	}
}
