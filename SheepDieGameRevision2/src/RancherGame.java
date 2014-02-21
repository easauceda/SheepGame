import java.awt.BorderLayout;
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
	private RangeCanvas rangeCanvas = new RangeCanvas();

	private JButton huntButton = new JButton("hunt");
	private ArrayList<Wolf> wolfs = new ArrayList<Wolf>();
	private ArrayList<Sheep> sheeps = new ArrayList<Sheep>();

	private final Timer timer;

	private void finish() {
		timer.stop();
	}

	private void updatePositions() {

		rangeCanvas.repaint();
	}

	public RancherGame() {

		// layout etc
		JPanel panel = new JPanel();
		add(rangeCanvas);
		GridLayout layout = new GridLayout(4, 2);
		layout.setVgap(20);
		layout.setHgap(20);
		panel.setLayout(layout);
		panel.add(new JLabel());
		panel.add(huntButton);
		panel.add(new JLabel("Number of Sheep"));
		panel.add(new JTextField());
		panel.add(new JLabel("Number of Wolves"));
		panel.add(new JTextField());
		panel.add(new JLabel("Game Speed"));
		panel.add(new JTextField());
		add(panel, BorderLayout.EAST);

		setTheAnimals();
		rangeCanvas.addGrassFractal();

		timer = new Timer(BOARD_REFRESH_RATE, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// System.out.println(wolf + " " + sheep);

				rangeCanvas.repaint();
				// wolfsPlayTag();

				// if (areSheepsAlive() && false) {
				// finish();
				// }
			}

			private boolean areSheepsAlive() {
				for (Sheep sheep : sheeps) {
					if (sheep.isAlive()) {
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

					timer.start();
					startMovingSheep();
					wolfsPlayTag();
					giveWolfHisSheep();
					startMovingWolfs();

					wolfIsPushed = true;
				}
				makeOneSheepAlive();
			}

			private void makeOneSheepAlive() {
				int e = 0;
				for (Sheep sheep : sheeps) {
					if (e == 0 && !sheep.isAlive()) {
						sheep.reviveMe();
						e++;
					}
				}
			}

			private void startMovingWolfs() {
				for (Wolf wolf : wolfs) {
					wolf.hunt(wolfs);
				}
			}

			private void startMovingSheep() {
				for (Sheep sheep : sheeps) {
					sheep.setWolfs(wolfs);
					sheep.squirm();
				}
			}

			private void giveWolfHisSheep() {
				for (Sheep sheep : sheeps) {
					for (Wolf wolf : wolfs) {
						wolf.addSheep(sheep);
					}
				}

			}
		});

	}

	private void setTheAnimals() {
		Random random = new Random();
		for (int q = 0; q < numberOfSheeps; q++) {
			sheeps.add(new Sheep(random.nextInt(max_X - 1), random
					.nextInt(max_Y - 1), SHEEP_COLOR));
		}
		for (int q = 0; q < numberOfWolfs; q++) {
			wolfs.add(new Wolf(random.nextInt(max_X - 1), random
					.nextInt(max_Y - 1), WOLF_COLOR));
		}
		giveTheAnimalsToCanvas();
	}

	private void giveTheAnimalsToCanvas() {

		for (Wolf wolf : wolfs) {
			rangeCanvas.addEntity(wolf);
		}
		for (Sheep sheep : sheeps) {
			rangeCanvas.addEntity(sheep);
		}

	}

	public static void pause() {
		try {
			Thread.sleep(GAME_SPEED);
		} catch (Exception e) {
			// uh oh!
		}
	}

	public static void pause(int q) {
		try {
			Thread.sleep(q);
		} catch (Exception e) {
			// uh oh!
		}
	}

	private void wolfsPlayTag() {
		if (wolfNotIt()) {
			for (Wolf wolf : wolfs) {
				wolf.yourIt();
				return;
			}
		}
	}

	private boolean wolfNotIt() {
		for (Wolf wolf : wolfs) {
			if (wolf.imIt()) {
				return false;
			}
		}
		return true;
	}

	public static void main(String args[]) {
		/*
		 * What I did here was add in a dialog to ask the user if he would like
		 * to edit the settings. The default is just to start the game by
		 * reading the settings already defined. - E
		 */
		RancherGame c = new RancherGame();
		c.setLayout(new GridLayout(1, 1));
		c.setSize(windowSizeY + 400, windowSizeX + 120);
		c.setVisible(true);
		c.setLocationRelativeTo(null);
		c.setLocationRelativeTo(null);
		c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
