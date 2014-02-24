
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
	private MiniMap miniMap = new MiniMap();

	private JButton huntButton = new JButton("hunt");
	private JTextField numberOfSheep = new JTextField();
	private JTextField numberOfWolves = new JTextField();
	private JTextField gameSpeed = new JTextField();
	
	private ArrayList<Wolf> wolfs = new ArrayList<Wolf>();
	private ArrayList<Sheep> sheeps = new ArrayList<Sheep>();
	private static Settings set = new Settings();

	private final Timer timer;

	private void finish() {
		timer.stop();
	}

	private void updatePositions() {

		rangeCanvas.repaint();
	}

	public RancherGame() {
		
		
		// read text from input file
		
		
		
		
		
		
		
		
		// enter text into textfield
		numberOfSheep.setText(Integer.toString(set.getSheep()));
		numberOfWolves.setText(Integer.toString(set.getWolves()));
		gameSpeed.setText(Integer.toString(set.getSpeed()));
		
		
		
		// layout etc
		JPanel panel = new JPanel();
		add(rangeCanvas);
		GridLayout layout = new GridLayout(10, 4);
		layout.setVgap(0);
		layout.setHgap(20);
		panel.setLayout(layout);
		panel.add(new JLabel());
		panel.add(huntButton);
		panel.add(new JLabel("Number of Sheep"));
		panel.add(numberOfSheep);
		panel.add(new JLabel("Number of Wolves"));
		panel.add(numberOfWolves);
		panel.add(new JLabel("Game Speed"));
		panel.add(gameSpeed);
		panel.add(miniMap);
		add(panel, BorderLayout.EAST);
		

		fillSheepAndPassToCanvas();
		fillWolvesAndPassToCanvas();
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

					for (Sheep sheep : sheeps) {
						sheep.setWolfs(wolfs);
						sheep.squirm();
					}

					for (Wolf wolf : wolfs) {
						wolf.hunt(sheeps, wolfs);
					}
					wolfIsPushed = true;
				}
			}
		});
		
		numberOfSheep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				set.setSheep(Integer.parseInt(numberOfSheep.getText()) );
				fillSheepAndPassToCanvas();
				updatePositions();
				System.out.println(set.getSheep());
				
			}
			
		});
		
		numberOfWolves.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				set.setWolves(Integer.parseInt(numberOfWolves.getText()) );
				fillWolvesAndPassToCanvas();
				updatePositions();
				System.out.println(set.getWolves());
			}
		});
		
		gameSpeed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				set.setGameSpeed(Integer.parseInt(gameSpeed.getText()) );
				
				updatePositions();
				System.out.println(set.getSpeed());
			}
		});
		

	}
	
	private void fillSheepAndPassToCanvas() {
		Random random = new Random();
		sheeps.clear();
		for (int q = 0; q < set.getSheep(); q++) {
			sheeps.add(new Sheep(random.nextInt(max_X - 1), random
					.nextInt(max_Y - 1), SHEEP_COLOR));
		}
		for (Sheep sheep : sheeps) {
			rangeCanvas.addEntity(sheep);
		}	
	}
	
	private void fillWolvesAndPassToCanvas() {
		Random random = new Random();
		wolfs.clear();
		for (int q = 0; q < set.getWolves(); q++) {
			wolfs.add(new Wolf(random.nextInt(max_X - 1), random
					.nextInt(max_Y - 1), WOLF_COLOR));
		}
		for (Wolf wolf : wolfs) {
			rangeCanvas.addEntity(wolf);
		}
	}

	public static void pause() {
		try {
			Thread.sleep(set.getSpeed());
		} catch (Exception e) {
			// uh oh!
		}
	}
	
	
	
	public static void main(String args[]) {
		RancherGame c = new RancherGame();
		c.setLayout(new GridLayout(1, 1));
		c.setSize(windowSizeY + 400, windowSizeX + 120);
		c.setVisible(true);
		c.setLocationRelativeTo(null);
		c.setLocationRelativeTo(null);
		c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
