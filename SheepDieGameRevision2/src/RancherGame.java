//test

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class RancherGame extends JFrame implements GameSettings {
	private boolean wolfIsPushed = false;
	private RangeCanvas rangeCanvas = new RangeCanvas();;
	private JTextField SheepX = new JTextField(2);
	private JTextField SheepY = new JTextField(2);
	private JTextField WolfX = new JTextField(2);
	private JTextField WolfY = new JTextField(2);
	private int numberOfSheep = 5;
	private int numberOfWolfs = 2;
	private JButton huntButton = new JButton("hunt");
	private ArrayList<Wolf> wolfs = new ArrayList<Wolf>();
	private ArrayList<Sheep> sheeps = new ArrayList<Sheep>();
	private Sheep sheep = new Sheep(INIT_SHEEP_X, INIT_SHEEP_Y, SHEEP_COLOR);
	private Wolf wolf = new Wolf(INIT_WOLF_X, INIT_WOLF_Y, WOLF_COLOR);;

	private final Timer timer;

	private void finish() {
		timer.stop();
	}

	private void updatePositions() {
		sheep.setX(Integer.parseInt(SheepX.getText().trim()));
		sheep.setY(Integer.parseInt(SheepY.getText().trim()));

		wolf.setX(Integer.parseInt(WolfX.getText().trim()));
		wolf.setY(Integer.parseInt(WolfY.getText().trim()));

		rangeCanvas.repaint();
	}

	public RancherGame() {

		// layout etc
		JPanel panel = new JPanel();
		add(rangeCanvas);
		panel.add(new JLabel("Wolf (x,y) = "));
		panel.add(WolfX);
		panel.add(WolfY);
		panel.add(new JLabel(" "));
		panel.add(huntButton);
		panel.add(new JLabel(" "));
		panel.add(new JLabel("Sheep (x,y) = "));
		panel.add(SheepX);
		panel.add(SheepY);
		add(panel, BorderLayout.SOUTH);
		rangeCanvas.addEntity(sheep);
		wolfs.add(wolf);
		Random random = new Random();
		sheeps.add(sheep);
		for (int q = 0; q < numberOfSheep; q++){
			sheeps.add(new Sheep(2,random.nextInt(9),Color.pink));
		}
		for (Sheep sheep: sheeps){
			rangeCanvas.addEntity(sheep);
		}
		for (int q = 0; q < numberOfWolfs; q++){
			wolfs.add(new Wolf(10,random.nextInt(7),Color.MAGENTA));
		}
		for(Wolf wolf:wolfs){
		rangeCanvas.addEntity(wolf);
		}
		rangeCanvas.addGrassFractal();

		timer = new Timer(BOARD_REFRESH_RATE, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// System.out.println(wolf + " " + sheep);

				rangeCanvas.repaint();
				if (areSheepsAlive()) {
					finish();
				}
			}

			private boolean areSheepsAlive() {
				for (Sheep sheep: sheeps){
					if (sheep.isAlive()){
						return false;
					}
				}
				return true;
			}
		});

		// actions below
		huntButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!wolfIsPushed) {
					Object[] options = { "No, thanks", "Yes, please" };
					int n = JOptionPane
							.showOptionDialog(
									null,
									"Would you like to see what squares the wolf has visited? ",
									"Track the wolfs steps",
									JOptionPane.YES_NO_CANCEL_OPTION,
									JOptionPane.QUESTION_MESSAGE, null,
									options, options[1]);
					Object[] options2 = { "No, Grass", "Yes, On the Grass",
							"Grass makes wolf slower" };
					int g = JOptionPane
							.showOptionDialog(
									null,
									"Would you like to see grass effect the wolf or just see grass? ",
									"Do you want grass with that?",
									JOptionPane.YES_NO_CANCEL_OPTION,
									JOptionPane.QUESTION_MESSAGE, null,
									options2, options2[2]);
					if (g > 0) {
						rangeCanvas.setGrassNeeded();
					}
					timer.start();
					// System.out.println(n+" this is your answer should be zero");
					for (Sheep sheep:sheeps){
						sheep.setWolfs(wolfs);
					sheep.squirm();
					}
					for (Wolf wolf: wolfs){
					wolf.hunt(sheeps, wolfs, n, rangeCanvas.getGrassArray(), g);
					}
					wolfIsPushed = true;
				}
			}
		});

		SheepX.setText("" + sheep.getX());
		SheepX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updatePositions();
			}
		});

		SheepY.setText("" + sheep.getY());
		SheepY.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updatePositions();
			}
		});

		WolfX.setText("" + wolf.getX());
		WolfX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updatePositions();
			}
		});

		WolfY.setText("" + wolf.getY());
		WolfY.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updatePositions();
			}
		});

	}

	public static void pause() {
		try {
			Thread.sleep(GAME_SPEED);
		} catch (Exception e) {
			// uh oh!
		}
	}

	public static void main(String args[]) {
		/*
		 * What I did here was add in a dialog to ask the user if he would like to edit the
		 * settings. The default is just to start the game by reading the settings already
		 * defined. - E
		 */
		int editSettings = JOptionPane.showConfirmDialog(null, "Would you like to edit settings?");
		if (editSettings == 0){
			System.out.println("Settings will be edited");
		}
		RancherGame c = new RancherGame();
		c.setLayout(new GridLayout(2, 1));
		c.setSize(B_WIDTH + 120, B_HEIGHT + 120);
		c.setVisible(true);
		c.setLocationRelativeTo(null);
		c.setLocationRelativeTo(null);
		c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
