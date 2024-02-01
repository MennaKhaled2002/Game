package controller;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import model.abilities.*;
import engine.*;
import exceptions.AbilityUseException;
import exceptions.ChampionDisarmedException;
import exceptions.InvalidTargetException;
import exceptions.LeaderAbilityAlreadyUsedException;
import exceptions.LeaderNotCurrentException;
import exceptions.NotEnoughResourcesException;
import exceptions.UnallowedMovementException;
import model.abilities.Ability;
import model.effects.Effect;
import model.world.*;
import view.*;

public class Controller implements ActionListener{
	private Game game;
	private Start start;
	private Select select;
	private GameView view;
	private ArrayList<JButton> champions;
	private ArrayList<JButton> team1;
	private ArrayList<JButton> team2;
	GameOver gameOver;
	String s="";
	Player first;
	Player second;
	JButton confirm1;
	JButton confirm2;
	JButton next;
	JButton attackUp;
	JButton moveUp;
	JButton moveDown;
	JButton moveLeft;
	JButton moveRight;
	JButton attackDown;
	JButton attackRight;
	JButton attackLeft;
	JButton castAbility;
	JButton castAbilityUp;
	JButton castAbilityDown;
	JButton castAbilityLeft;
	JButton castAbilityRight;
	JButton castAbilityLocation;
	JButton endTurn;
	JButton UseLeader;
	int c=0;
	ArrayList<Champion> player1;
	ArrayList<Champion> player2;
	JButton[][]boardCells;
	Object[][]board;
	ArrayList<Cover>covers;
	ArrayList<JButton>coverButtons;
	JButton Selected=new JButton();
	boolean f1=false;
	boolean f2=false;
	public Controller()  throws IOException {
		champions=new ArrayList<>();
		player1=new ArrayList<>();
		player2=new ArrayList<>();
		team1=new ArrayList<>();
		team2=new ArrayList<>();
		covers=new ArrayList<>();
		coverButtons=new ArrayList<>();
		boardCells=new JButton[5][5];
		start=new Start();
		start.getButton().addActionListener(this);
		board=new Object[5][5];
	}
	public static void main(String[]args) throws IOException {
		new Controller();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==start.getButton()) {
			try {
				if (start.getText().getText().equals("") || start.getText1().getText().equals("")) {
					JOptionPane.showMessageDialog(start, "Enter players' names ", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				first=new Player(start.getText().getText());
				second=new Player(start.getText1().getText());
				game=new Game(first,second);
				start.dispose();
				select=new Select();
				for(int i=0;i<game.getAvailableChampions().size();i++) {
					JButton b=new JButton();
					b.setBackground(Color.LIGHT_GRAY);
					b.setPreferredSize(new Dimension(240,50));
					b.setText(Game.getAvailableChampions().get(i).getName());
					champions.add(b);
					select.getChamps().add(b);
					b.addActionListener(this);
					confirm1=new JButton("confirm1");
					confirm2=new JButton("confirm2");
					next=new JButton("next");
					confirm1.setBackground(Color.LIGHT_GRAY);
					confirm2.setBackground(Color.LIGHT_GRAY);
					next.setBackground(Color.LIGHT_GRAY);
					confirm1.addActionListener(this);
					confirm2.addActionListener(this);
					next.addActionListener(this);
					select.revalidate();
					select.repaint();
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			select.getChamps().add(confirm1);
			select.getChamps().add(confirm2);
			select.getChamps().add(next);
		}
		JButton Selected= (JButton) e.getSource();
		int index= champions.indexOf(Selected);
		if (index>=0) {
			if (c<3) {
		s+= game.getAvailableChampions().get(index).toString() + "\n";
		s+=game.getAvailableChampions().get(index).getAbilities().get(0).getName()+"\n";
		s+=game.getAvailableChampions().get(index).getAbilities().get(1).getName()+"\n";
		s+=game.getAvailableChampions().get(index).getAbilities().get(2).getName()+"\n______________\n";
		player1.add(game.getAvailableChampions().get(index));
		c++;
			}
			else 
				if (c<6) {
					s+= game.getAvailableChampions().get(index).toString() + "\n";
					player2.add(game.getAvailableChampions().get(index));
					s+=game.getAvailableChampions().get(index).getAbilities().get(0).getName()+"\n";
					s+=game.getAvailableChampions().get(index).getAbilities().get(1).getName()+"\n";
					s+=game.getAvailableChampions().get(index).getAbilities().get(2).getName()+"\n______________\n";
					c++;
		}
				else {
					JOptionPane.showMessageDialog(select,"both players have chosen their team" , "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			
		select.getDetails().setText(s);
		Selected.setEnabled(false);
		}
		
		if (e.getSource()==confirm1 && c>2) {
			for(int i=0;i<player1.size();i++) {
				first.getTeam().add(player1.get(i));
				JButton b=new JButton(player1.get(i).getName());
				
				b.setBackground(Color.YELLOW);
				b.setPreferredSize(new Dimension(200,40));
				select.getTeam1().add(b);
//				select.getTeam1().revalidate();
//				select.getTeam1().repaint();
				team1.add(b);
				
				b.addActionListener(this);
				select.revalidate();
				select.repaint();
			}
			select.revalidate();
			select.repaint();
			Selected.setEnabled(false);
		}
		if (e.getSource()==confirm2 &&c>5) {
			for(int i=0;i<player2.size();i++) {
				second.getTeam().add(player2.get(i));
				JButton b=new JButton(player2.get(i).getName());
				b.setBackground(Color.RED);
				b.setPreferredSize(new Dimension(200,40));
				select.getTeam2().add(b);
				team2.add(b);
				b.addActionListener(this);
			}
			select.revalidate();
			select.repaint();
			Selected.setEnabled(false);
		}
		for (int i=0;i<team1.size();i++) {
			if (e.getSource()==team1.get(i)&& f1==false) {
				first.setLeader(player1.get(i));
				f1=true;
				team1.get(i).setEnabled(false);
			}
		}
		for (int i=0;i<team2.size();i++) {
			if (e.getSource()==team2.get(i)&& f2==false) {
				second.setLeader(player2.get(i));
				f2=true;
				team2.get(i).setEnabled(false);
			}
		}
		if (f1==true &&f2==true &&e.getSource()==next) {
			select.dispose();
			view=new GameView();
			game.placeChampions();
			game.prepareChampionTurns();
			this.setBoard();
			this.updateTurnOrder();
			this.remaining();
			addActions();
			}
		if (e.getSource()==moveUp) {
			this.moveUp();
			this.updateBoard();
			}
		if (e.getSource()==moveDown) {
			this.moveDown();
			this.updateBoard();
			}
		if (e.getSource()==moveLeft) {
			this.moveLeft();
			this.updateBoard();
			}
		if (e.getSource()==moveRight) {
			this.moveRight();
			this.updateBoard();
			}
		if (e.getSource()==attackRight) {
			this.AttackRight();
			this.updateBoard();
			}
		if (e.getSource()==attackLeft) {
			this.AttackLeft();
			this.updateBoard();
			}
		if (e.getSource()==attackUp) {
			this.AttackUp();
			this.updateBoard();
			}
		if (e.getSource()==attackDown) {
			this.AttackDown();
			this.updateBoard();
			}
		if (e.getSource()==UseLeader) {
			this.useLeader();
			this.updateBoard();
		}
		if (e.getSource()==endTurn) {
			this.endTurn();
			this.updateBoard();
		}
		if (game.getTurnOrder().size()!=0 && e.getActionCommand().equals(game.getCurrentChampion().getName())) {
			this.diplayCurrentChampion();
		}
		if (e.getSource()==castAbility) {
			this.performAbility();
			this.updateBoard();
		}
		if(e.getSource()==castAbilityUp) {
			this.performAbilityUp();
			this.updateBoard();
		}
		if(e.getSource()==castAbilityDown) {
			this.performAbilityDown();
			this.updateBoard();
		}
		if(e.getSource()==castAbilityLeft) {
			this.performAbilityLeft();
			this.updateBoard();
		}
		if(e.getSource()==castAbilityRight) {
			this.performAbilityRight();
			this.updateBoard();
		}
		if (e.getSource()==castAbilityLocation) {
			this.performAbilityLoc();
			this.updateBoard();
		}
		if (e.getActionCommand().equals("Cover1")) {
			this.displayCover(1);
			this.updateBoard();
		}
		if (e.getActionCommand().equals("Cover2")) {
			this.displayCover(2);
			this.updateBoard();
		}
		if (e.getActionCommand().equals("Cover3")) {
			this.displayCover(3);
			this.updateBoard();
		}
		if (e.getActionCommand().equals("Cover4")) {
			this.displayCover(4);
			this.updateBoard();
		}
		if (e.getActionCommand().equals("Cover5")) {
			this.displayCover(5);
			this.updateBoard();
		}
		}
	public void setBoard(){
		board=game.getBoard();
		for(int i=0;i<game.getBoardwidth();i++) {
			for(int j=0;j<game.getBoardheight();j++) {
				JButton b=new JButton();
				view.getBoard().add(b);
				boardCells[i][j]=b;
				boardCells[i][j].setBackground(Color.GRAY);
				b.addActionListener(this);
			}
		}
		int n=1;
		for(int i=0;i<game.getBoardwidth();i++) {
			for(int j=0;j<game.getBoardheight();j++) {
				if (board[i][j] instanceof Champion) {
					if (first.getTeam().contains((Champion)board[i][j])){
						boardCells[i][j].setBackground(Color.YELLOW);
						view.revalidate();
						view.repaint();
					}
					else {
						boardCells[i][j].setBackground(Color.RED);
						view.getBoard().revalidate();
						view.getBoard().repaint();
					}
					switch(((Champion)board[i][j]).getName()){
					case "Ironman":boardCells[i][j].setIcon(new ImageIcon("Ironman.jpg"));boardCells[i][j].setActionCommand("Ironman");break;
					case "Captain America":boardCells[i][j].setIcon(new ImageIcon("Captain America.jpg"));boardCells[i][j].setActionCommand("Captain America");break;
					case "Deadpool":boardCells[i][j].setIcon(new ImageIcon("Deadpool.jpg"));boardCells[i][j].setActionCommand("Deadpool");break;
					case "Dr Strange":boardCells[i][j].setIcon(new ImageIcon("Dr Strange.png"));boardCells[i][j].setActionCommand("Dr Strange");break;
					case "Electro":boardCells[i][j].setIcon(new ImageIcon("Electro.png"));boardCells[i][j].setActionCommand("Electro");break;
					case "Ghost Rider":boardCells[i][j].setIcon(new ImageIcon("Ghost Rider.png"));boardCells[i][j].setActionCommand("Ghost Rider");break;
					case "Hela":boardCells[i][j].setIcon(new ImageIcon("Hela.png"));boardCells[i][j].setActionCommand("Hela");break;
					case "Hulk":boardCells[i][j].setIcon(new ImageIcon("Hulk.png"));boardCells[i][j].setActionCommand("Hulk");break;
					case "Iceman":boardCells[i][j].setIcon(new ImageIcon("Iceman.png"));boardCells[i][j].setActionCommand("Iceman");break;
					case "Loki":boardCells[i][j].setIcon(new ImageIcon("Loki.jpg"));boardCells[i][j].setActionCommand("Loki");break;
					case "Quicksilver":boardCells[i][j].setIcon(new ImageIcon("Quicksilver.jpg"));boardCells[i][j].setActionCommand("Quicksilver");break;
					case "Spiderman":boardCells[i][j].setIcon(new ImageIcon("Spiderman.jpg"));boardCells[i][j].setActionCommand("Spiderman");break;
					case "Thor":boardCells[i][j].setIcon(new ImageIcon("Thor.png"));boardCells[i][j].setActionCommand("Thor");break;
					case "Venom":boardCells[i][j].setIcon(new ImageIcon("Venom.jpg"));boardCells[i][j].setActionCommand("Venom");break;
					case "Yellow Jacket":boardCells[i][j].setIcon(new ImageIcon("Yellow Jacket.jpg"));boardCells[i][j].setActionCommand("Yellow Jacket");break;
					}
				}
				if (board[i][j]instanceof Cover) {
					boardCells[i][j].setIcon(new ImageIcon("Cover.jpeg"));
					boardCells[i][j].setActionCommand("Cover"+n);
					n++;
				}
			}
		}
	}
	public void addActions() {
		moveUp=new JButton("moveUp");
		moveUp.addActionListener(this);
		moveDown=new JButton("moveDown");
		moveDown.addActionListener(this);
		moveRight=new JButton("moveRight");
		moveRight.addActionListener(this);
		moveLeft=new JButton("moveLeft");
		moveLeft.addActionListener(this);
		attackUp=new JButton("attackUp");
		attackUp.addActionListener(this);
		attackDown=new JButton("attackDown");
		attackDown.addActionListener(this);
		attackRight=new JButton("attackRight");
		attackRight.addActionListener(this);
		attackLeft=new JButton("attackLeft");
		attackLeft.addActionListener(this);
		castAbility=new JButton("castAbility");
		castAbility.addActionListener(this);
		castAbilityUp=new JButton("castAbilityUp");
		castAbilityUp.addActionListener(this);
		castAbilityDown=new JButton("castAbilityDown");
		castAbilityDown.addActionListener(this);
		castAbilityLeft=new JButton("castAbilityLeft");
		castAbilityLeft.addActionListener(this);
		castAbilityRight=new JButton("castAbilityRight");
		castAbilityRight.addActionListener(this);
		castAbilityLocation=new JButton("castAbilityLocation");
		castAbilityLocation.addActionListener(this);
		endTurn=new JButton("endTurn");
		endTurn.addActionListener(this);
		UseLeader=new JButton("UseLeader");
		UseLeader.addActionListener(this);
		view.getActions().add(moveUp);
		view.getActions().add(moveDown);
		view.getActions().add(moveRight);
		view.getActions().add(moveLeft);
		view.getActions().add(attackUp);
		view.getActions().add(attackDown);
		view.getActions().add(attackLeft);
		view.getActions().add(attackRight);
		view.getActions().add(castAbility);
		view.getActions().add(castAbilityUp);
		view.getActions().add(castAbilityDown);
		view.getActions().add(castAbilityLeft);
		view.getActions().add(castAbilityRight);
		view.getActions().add(castAbilityLocation);
		view.getActions().add(endTurn);
		view.getActions().add(UseLeader);
		view.revalidate();
		view.repaint();
	}
	public void updateBoard() {
		view.getBoard().removeAll();
		view.getFirstPlayer().removeAll();
		view.getSecondPlayer().removeAll();
		view.getQueue().removeAll();
		this.setBoard();
		this.remaining();
		this.updateTurnOrder();
	}
	public void remaining() {
	view.getFirstPlayer().add(new JLabel(first.getName()+"\n"));
	view.getSecondPlayer().add(new JLabel(second.getName()+"\n"));
	TextArea remaining1=new TextArea();remaining1.setEditable(false);
	remaining1.setPreferredSize(new Dimension(150,view.getHeight()));
	TextArea remaining2=new TextArea();remaining2.setEditable(false);
	remaining2.setPreferredSize(new Dimension(150,view.getHeight()));
	view.getFirstPlayer().add(remaining1);
	view.getSecondPlayer().add(remaining2);
	if (first.getTeam().size()==0) {
		JOptionPane.showMessageDialog(view,second.getName() , "WINNER!!!!",JOptionPane.INFORMATION_MESSAGE);
		view.dispose();
		gameOver=new GameOver();
	}
	else if(second.getTeam().size()==0) {
		JOptionPane.showMessageDialog(view,first.getName() , "WINNER!!!!",JOptionPane.INFORMATION_MESSAGE);
		view.dispose();
		gameOver=new GameOver();
	}
	else {
	remaining1.setText(this.displayChampions(first.getTeam()));
	remaining2.setText(this.displayChampions(second.getTeam()));
	}
	}
	public void AttackUp() {
		try {
			if (first.getTeam().contains(game.getCurrentChampion()))
				game.attack(Direction.UP);
			else
				game.attack(Direction.DOWN);
		} catch (NotEnoughResourcesException e) {
			JOptionPane.showMessageDialog(view,"no enough resources" , "Error", JOptionPane.ERROR_MESSAGE);
		} catch (ChampionDisarmedException e) {
			JOptionPane.showMessageDialog(view,"champion disarm" , "Error", JOptionPane.ERROR_MESSAGE);
		} catch (InvalidTargetException e) {
			JOptionPane.showMessageDialog(view,"invalid target" , "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	public void AttackDown() {
		try {
			if (first.getTeam().contains(game.getCurrentChampion()))
				game.attack(Direction.DOWN);
			else
				game.attack(Direction.UP);
		} catch (NotEnoughResourcesException e) {
			JOptionPane.showMessageDialog(view,"no enough resources" , "Error", JOptionPane.ERROR_MESSAGE);
		} catch (ChampionDisarmedException e) {
			JOptionPane.showMessageDialog(view,"champion disarm" , "Error", JOptionPane.ERROR_MESSAGE);
		} catch (InvalidTargetException e) {
			JOptionPane.showMessageDialog(view,"invalid target" , "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	public void AttackRight() {
		try {
			game.attack(Direction.RIGHT);
		} catch (NotEnoughResourcesException e) {
			JOptionPane.showMessageDialog(view,"no enough resources" , "Error", JOptionPane.ERROR_MESSAGE);
		} catch (ChampionDisarmedException e) {
			JOptionPane.showMessageDialog(view,"champion disarm" , "Error", JOptionPane.ERROR_MESSAGE);
		} catch (InvalidTargetException e) {
			JOptionPane.showMessageDialog(view,"invalid target" , "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	public void AttackLeft() {
		try {
			game.attack(Direction.LEFT);
		} catch (NotEnoughResourcesException e) {
			JOptionPane.showMessageDialog(view,"no enough resources " , "Error", JOptionPane.ERROR_MESSAGE);
		} catch (ChampionDisarmedException e) {
			JOptionPane.showMessageDialog(view,"champion disarm" , "Error", JOptionPane.ERROR_MESSAGE);
		} catch (InvalidTargetException e) {
			JOptionPane.showMessageDialog(view,"invalid target" , "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	public void moveUp() {
		try {
			if (first.getTeam().contains(game.getCurrentChampion()))
				game.move(Direction.UP);
			else
				game.move(Direction.DOWN);
		} catch (NotEnoughResourcesException e) {
			JOptionPane.showMessageDialog(view,"no enough resources,, END YOUR TURN" , "Error", JOptionPane.ERROR_MESSAGE);
		} catch (UnallowedMovementException e) {
			JOptionPane.showMessageDialog(view,"unallowed movement" , "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	public void moveDown() {
		try {
			if (first.getTeam().contains(game.getCurrentChampion()))
				game.move(Direction.DOWN);
			else
				game.move(Direction.UP);
		} catch (NotEnoughResourcesException e) {
			JOptionPane.showMessageDialog(view,"no enough resources, END YOUR TURN" , "Error", JOptionPane.ERROR_MESSAGE);
		} catch (UnallowedMovementException e) {
			JOptionPane.showMessageDialog(view,"unallowed movement" , "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	public void moveLeft() {
		try {
			game.move(Direction.LEFT);
		} catch (NotEnoughResourcesException e) {
			JOptionPane.showMessageDialog(view,"no enough resources, END YOUR TURN" , "Error", JOptionPane.ERROR_MESSAGE);
		} catch (UnallowedMovementException e) {
			JOptionPane.showMessageDialog(view,"unallowed movement" , "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	public void moveRight() {
		try {
			game.move(Direction.RIGHT);
		} catch (NotEnoughResourcesException e) {
			JOptionPane.showMessageDialog(view,"no enough resources, END YOUR TURN" , "Error", JOptionPane.ERROR_MESSAGE);
		} catch (UnallowedMovementException e) {
			JOptionPane.showMessageDialog(view,"unallowed movement" , "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	public void useLeader() {
		try {
			game.useLeaderAbility();
		} catch (LeaderNotCurrentException e) {
			JOptionPane.showMessageDialog(view,"this champion is not a leader" , "Error", JOptionPane.ERROR_MESSAGE);
		} catch (LeaderAbilityAlreadyUsedException e) {
			JOptionPane.showMessageDialog(view,"leader ability already used" , "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	public void endTurn() {
		if (game.getCurrentChampion().getCurrentActionPoints()!=0 ) {
			String []arr= {"End turn anyway","Continue"};
			int x=JOptionPane.showOptionDialog(null, "you still have action points", "Action points", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, arr, null);
			if(x==0) {
				if(game.getTurnOrder().size()!=0) {
					game.endTurn();
					this.updateTurnOrder();
					return;
				}
			}
			if (x==1||x==-1) {
				this.updateBoard();
				return;
			}
		}
		game.endTurn();
		this.updateTurnOrder();
	}
	public String displayChampions(ArrayList<Champion> team) {
		String str="";
		for(int i=0;i<team.size();i++) {
			str+="name:"+team.get(i).getName()+"\n"+"currentHP:"+team.get(i).getCurrentHP()+"\n"+"mana:"+team.get(i).getMana()+"\n"
				+"speed:"+team.get(i).getSpeed()+"\n"+"maxAction:"+team.get(i).getMaxActionPointsPerTurn()+"\n"+"attackDmg:"
						+team.get(i).getAttackDamage()+"\n"+"attackRange:"+team.get(i).getAttackRange()+"\n";
			if (team.get(i) instanceof Hero) {
				str+="Type:Hero"+"\n";
				}
			else if (team.get(i) instanceof Villain) {
				str+="Type:Villain"+"\n";
				}
				else
					str+="Type:AntiHero"+"\n";
			if (!team.get(i).equals(first.getLeader())&&!team.get(i).equals(second.getLeader()))
				str+="Not a leader"+"\n";
			else {
				str+="leader"+"\n";
				if (team.get(i).equals(first.getLeader()))
					str+="LeaderAbilityUsed:"+game.isFirstLeaderAbilityUsed()+"\n";
				else
					str+="LeaderAbilityUsed:"+game.isSecondLeaderAbilityUsed()+"\n";
			}
			for(int j=0;j<team.get(i).getAppliedEffects().size();j++) {
				str+="name:"+team.get(i).getAppliedEffects().get(j).getName()+" "
			+"duration:"+team.get(i).getAppliedEffects().get(j).getDuration()+"\n";
			}
			str+="\n";
		}
		return str;
	}
	public void updateTurnOrder() {
		String r ="";
		PriorityQueue q=game.getTurnOrder();
		PriorityQueue temp=new PriorityQueue(q.size());
			while(!q.isEmpty()) {
				if (q.peekMin()!=null) {
					r+=((Champion)game.getTurnOrder().peekMin()).getName()+"   ";
					temp.insert(q.remove());
			}
		}
			while(!temp.isEmpty()) {
				q.insert(temp.remove());
			}
			view.getQueue().add(new JLabel(r));
	}
	public String displayAbility() {
		String t="";
		Champion c=game.getCurrentChampion();
		for(int i=0;i<c.getAbilities().size();i++) {
			Ability a=c.getAbilities().get(i);
			t+="Abilityname:"+a.getName()+"\n"+"areaOfEffect:"+a.getCastArea()+"\n"+"cast range:"+a.getCastRange()+"\n"+
					"mana:"+a.getManaCost()+" "+"action cost:"+a.getRequiredActionPoints()+"\n"+
					"current CoolDown:"+a.getCurrentCooldown()+" "+"BaseCoolDown:"+a.getBaseCooldown();
			if (a instanceof HealingAbility) {
				t+="AbilityType:healing ability"+" "+"heal amount:"+((HealingAbility)a).getHealAmount()+"\n";
			}
			else if(a instanceof DamagingAbility) {
				t+="AbilityType:DamagingAbility"+" "+"damage amount:"+((DamagingAbility)a).getDamageAmount()+"\n";
			}
			else {
				t+="AbilityType: CrowdControlAbility"+" "+"effectName:"+((CrowdControlAbility)a).getEffect().getName()+" "+"effectDuration:"+
						((CrowdControlAbility)a).getEffect().getDuration()+"\n";
			}
			t+="\n";
		}
		return t;
	}
	public String displayEffects() {
		String t="";
		Champion c=game.getCurrentChampion();
		for(int i=0;i<c.getAppliedEffects().size();i++) {
			Effect e=c.getAppliedEffects().get(i);
			t+="name:"+e.getName()+" "+"duration:"+e.getDuration()+"\n";
		}
		return t;
	}
	public void diplayCurrentChampion() {
		String t="";
		Champion c=game.getCurrentChampion();
		t+="name:"+c.getName()+"\n";
		if (c instanceof Hero) {
			t+="Type:Hero"+"\n";
			}
		else if (c instanceof Villain) {
			t+="Type:Villain"+"\n";
			}
			else
				t+="Type:AntiHero"+"\n";
		t+="currentHP:"+c.getCurrentHP()+" "+"mana:"+c.getMana()+" "+"actionPoints:"+c.getCurrentActionPoints()+"\n";
		t+=this.displayAbility()+"\n"+this.displayEffects();
		t+="attackDamage:"+c.getAttackDamage()+" "+"attackRange:"+c.getAttackRange();
		JOptionPane.showMessageDialog(view,t, "CurrentChampion",JOptionPane.INFORMATION_MESSAGE );
	}
	public void performAbility() {
		String[] arr = new String[game.getCurrentChampion().getAbilities().size()];
		if (game.getCurrentChampion().getAbilities().size()==3) {
		arr[0]= game.getCurrentChampion().getAbilities().get(0).getName();
		arr[1]= game.getCurrentChampion().getAbilities().get(1).getName();
		arr[2]= game.getCurrentChampion().getAbilities().get(2).getName();
		}
		else if (game.getCurrentChampion().getAbilities().size()==4){
			arr[0]= game.getCurrentChampion().getAbilities().get(0).getName();
			arr[1]= game.getCurrentChampion().getAbilities().get(1).getName();
			arr[2]= game.getCurrentChampion().getAbilities().get(2).getName();
			arr[3]= game.getCurrentChampion().getAbilities().get(3).getName();
		}
		int x=JOptionPane.showOptionDialog(null, "choose ability to be casted", "ability", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, arr, null);
		try {
			if (x>=0)
				game.castAbility(game.getCurrentChampion().getAbilities().get(x));
		} catch (NotEnoughResourcesException e) {
			JOptionPane.showMessageDialog(view,"not enough resources" , "Error", JOptionPane.ERROR_MESSAGE);
		} catch (AbilityUseException e) {
			JOptionPane.showMessageDialog(view,"Ability cannot be used " , "Error", JOptionPane.ERROR_MESSAGE);
		} catch (CloneNotSupportedException e) {
			JOptionPane.showMessageDialog(view,"clone is not supported" , "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	public void performAbilityUp() {
		String[] arr = new String[game.getCurrentChampion().getAbilities().size()];
		if (game.getCurrentChampion().getAbilities().size()==3) {
		arr[0]= game.getCurrentChampion().getAbilities().get(0).getName();
		arr[1]= game.getCurrentChampion().getAbilities().get(1).getName();
		arr[2]= game.getCurrentChampion().getAbilities().get(2).getName();
		}
		else if (game.getCurrentChampion().getAbilities().size()==4){
			arr[0]= game.getCurrentChampion().getAbilities().get(0).getName();
			arr[1]= game.getCurrentChampion().getAbilities().get(1).getName();
			arr[2]= game.getCurrentChampion().getAbilities().get(2).getName();
			arr[3]= game.getCurrentChampion().getAbilities().get(3).getName();
		}
		int x=JOptionPane.showOptionDialog(null, "choose ability to be casted in direction UP", "ability", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, arr, null);
		try {
			if(x>=0)
			if (first.getTeam().contains(game.getCurrentChampion()))
				game.castAbility(game.getCurrentChampion().getAbilities().get(x), Direction.UP);
			else
				game.castAbility(game.getCurrentChampion().getAbilities().get(x), Direction.DOWN);
		} catch (NotEnoughResourcesException e) {
			JOptionPane.showMessageDialog(view,"not enough resources" , "Error", JOptionPane.ERROR_MESSAGE);
		} catch (AbilityUseException e) {
			JOptionPane.showMessageDialog(view,"Ability cannot be used " , "Error", JOptionPane.ERROR_MESSAGE);
		} catch (CloneNotSupportedException e) {
			JOptionPane.showMessageDialog(view,"Ability cannot be used " , "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	public void performAbilityDown() {
		String[] arr = new String[game.getCurrentChampion().getAbilities().size()];
		if (game.getCurrentChampion().getAbilities().size()==3) {
		arr[0]= game.getCurrentChampion().getAbilities().get(0).getName();
		arr[1]= game.getCurrentChampion().getAbilities().get(1).getName();
		arr[2]= game.getCurrentChampion().getAbilities().get(2).getName();
		}
		else if (game.getCurrentChampion().getAbilities().size()==4){
			arr[0]= game.getCurrentChampion().getAbilities().get(0).getName();
			arr[1]= game.getCurrentChampion().getAbilities().get(1).getName();
			arr[2]= game.getCurrentChampion().getAbilities().get(2).getName();
			arr[3]= game.getCurrentChampion().getAbilities().get(3).getName();
		}
		int x=JOptionPane.showOptionDialog(null, "choose ability to be casted in direction DOWN", "ability", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, arr, null);
		try {
			if(x>=0)
			if (first.getTeam().contains(game.getCurrentChampion()))
				game.castAbility(game.getCurrentChampion().getAbilities().get(x), Direction.DOWN);
			else
				game.castAbility(game.getCurrentChampion().getAbilities().get(x), Direction.UP);
		} catch (NotEnoughResourcesException e) {
			JOptionPane.showMessageDialog(view,"not enough resources" , "Error", JOptionPane.ERROR_MESSAGE);
		} catch (AbilityUseException e) {
			JOptionPane.showMessageDialog(view,"Ability cannot be used " , "Error", JOptionPane.ERROR_MESSAGE);
		} catch (CloneNotSupportedException e) {
			JOptionPane.showMessageDialog(view,"Ability cannot be used " , "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	public void performAbilityRight() {
		String[] arr = new String[game.getCurrentChampion().getAbilities().size()];
		if (game.getCurrentChampion().getAbilities().size()==3) {
		arr[0]= game.getCurrentChampion().getAbilities().get(0).getName();
		arr[1]= game.getCurrentChampion().getAbilities().get(1).getName();
		arr[2]= game.getCurrentChampion().getAbilities().get(2).getName();
		}
		else if (game.getCurrentChampion().getAbilities().size()==4){
			arr[0]= game.getCurrentChampion().getAbilities().get(0).getName();
			arr[1]= game.getCurrentChampion().getAbilities().get(1).getName();
			arr[2]= game.getCurrentChampion().getAbilities().get(2).getName();
			arr[3]= game.getCurrentChampion().getAbilities().get(3).getName();
		}
		int x=JOptionPane.showOptionDialog(null, "choose ability to be casted", "ability in direction RIGHT", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, arr, null);
		try {
			if(x>=0)
				game.castAbility(game.getCurrentChampion().getAbilities().get(x), Direction.RIGHT);
		} catch (NotEnoughResourcesException e) {
			JOptionPane.showMessageDialog(view,"not enough resources" , "Error", JOptionPane.ERROR_MESSAGE);
		} catch (AbilityUseException e) {
			JOptionPane.showMessageDialog(view,"Ability cannot be used " , "Error", JOptionPane.ERROR_MESSAGE);
		} catch (CloneNotSupportedException e) {
			JOptionPane.showMessageDialog(view,"Ability cannot be used " , "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	public void performAbilityLeft() {
		String[] arr = new String[game.getCurrentChampion().getAbilities().size()];
		if (game.getCurrentChampion().getAbilities().size()==3) {
		arr[0]= game.getCurrentChampion().getAbilities().get(0).getName();
		arr[1]= game.getCurrentChampion().getAbilities().get(1).getName();
		arr[2]= game.getCurrentChampion().getAbilities().get(2).getName();
		}
		else if (game.getCurrentChampion().getAbilities().size()==4){
			arr[0]= game.getCurrentChampion().getAbilities().get(0).getName();
			arr[1]= game.getCurrentChampion().getAbilities().get(1).getName();
			arr[2]= game.getCurrentChampion().getAbilities().get(2).getName();
			arr[3]= game.getCurrentChampion().getAbilities().get(3).getName();
		}
		int x=JOptionPane.showOptionDialog(null, "choose ability to be casted in direction LEFT", "ability", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, arr, null);
		try {
			if(x>=0)
			game.castAbility(game.getCurrentChampion().getAbilities().get(x), Direction.LEFT);
		} catch (NotEnoughResourcesException e) {
			JOptionPane.showMessageDialog(view,"not enough resources" , "Error", JOptionPane.ERROR_MESSAGE);
		} catch (AbilityUseException e) {
			JOptionPane.showMessageDialog(view,"Ability cannot be used " , "Error", JOptionPane.ERROR_MESSAGE);
		} catch (CloneNotSupportedException e) {
			JOptionPane.showMessageDialog(view,"Ability cannot be used " , "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	public void performAbilityLoc() {
		String s1=JOptionPane.showInputDialog("Enter X cooardinate");
		String s2=JOptionPane.showInputDialog("Enter Y coordinate");
		if (s1==null||s2==null) {
			return;
		}
		int x=Integer.parseInt(s1);
		int y=Integer.parseInt(s2);
		if (x>4||y>4) {
			JOptionPane.showMessageDialog(view,"out of bounds" , "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String[] arr = new String[game.getCurrentChampion().getAbilities().size()];
		if (game.getCurrentChampion().getAbilities().size()==3) {
		arr[0]= game.getCurrentChampion().getAbilities().get(0).getName();
		arr[1]= game.getCurrentChampion().getAbilities().get(1).getName();
		arr[2]= game.getCurrentChampion().getAbilities().get(2).getName();
		}
		else if (game.getCurrentChampion().getAbilities().size()==4) {
			arr[0]= game.getCurrentChampion().getAbilities().get(0).getName();
			arr[1]= game.getCurrentChampion().getAbilities().get(1).getName();
			arr[2]= game.getCurrentChampion().getAbilities().get(2).getName();
			arr[3]= game.getCurrentChampion().getAbilities().get(3).getName();
		}
		int n=JOptionPane.showOptionDialog(null, "choose ability to be casted in location ("+x+","+y+")"
				, "ability", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, arr, null);
		if(n>=0) {
			try {
				game.castAbility(game.getCurrentChampion().getAbilities().get(n), x, y);
			} catch (NotEnoughResourcesException e) {
				JOptionPane.showMessageDialog(view,"not enough resources to perform this ability" , "Error", JOptionPane.ERROR_MESSAGE);
			} catch (AbilityUseException e) {
				JOptionPane.showMessageDialog(view,"Ability cannot be used " , "Error", JOptionPane.ERROR_MESSAGE);
			} catch (InvalidTargetException e) {
				JOptionPane.showMessageDialog(view,"this target is invalid " , "Error", JOptionPane.ERROR_MESSAGE);
			} catch (CloneNotSupportedException e) {
				JOptionPane.showMessageDialog(view,"Ability cannot be used " , "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	public void displayCover(int c) {
		int count =1;
		Object[][] b=game.getBoard();
		for(int i=0;i<game.getBoardwidth();i++) {
			for(int j=0;j<game.getBoardheight();j++) {
				if (b[i][j] instanceof Cover) {
					if (count==c) {
						int hp=((Cover)b[i][j]).getCurrentHP();
						JOptionPane.showMessageDialog(view, hp, "Cover CurrentHP", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					count++;
				}
			}
		}
	}

	}
