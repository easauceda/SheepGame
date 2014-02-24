import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.net.Socket;
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
	// ///////////////////////////////////////////////////////////////////
	private JTextField typedText = new JTextField(32);
	private static final long serialVersionUID = 1L;
	private String userName;
	private Socket socket;
	private In in;
	private Out out;
	private Sheep playerSheep;
	// ///////////////////this is new/////////////////////////////////////
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

	public RancherGame(String userName, String server, int port) {
		// ///////////////////////////////////////////////////////////////////
		this.userName = userName;
		try {
			socket = new Socket(server, port);
			out = new Out(socket);
			in = new In(socket);

			out.println("/register " + userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ///////////////////////this is
		// new////////////////////////////////////
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
		panel.add(new JTextField(String.valueOf(input.getNumberOfSheep())));
		panel.add(new JLabel("Number of Wolves"));
		panel.add(new JTextField(String.valueOf(input.getNumberOfWolfs())));
		panel.add(new JLabel("Game Speed"));
		panel.add(new JTextField(String.valueOf(input.getSpeed())));
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
		for (Wolf wolf : wolfs) {
			wolf.yourIt();
			return;
		}
	}

	public static void main(String args[]) {
		try {
			new input();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (args.length < 3) {
			System.out
					.println("Usage: java ChatClient <username> <server> <port>");
			System.exit(-1);
		}

		/*
		 * What I did here was add in a dialog to ask the user if he would like
		 * to edit the settings. The default is just to start the game by
		 * reading the settings already defined. - E
		 */
		RancherGame c = new RancherGame(args[0], args[1],
				Integer.parseInt(args[2]));
		c.setLayout(new GridLayout(1, 1));
		c.setSize(windowSizeY + 400, windowSizeX + 120);
		c.setVisible(true);
		c.setLocationRelativeTo(null);
		c.setLocationRelativeTo(null);
		c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c.listen();
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////
	private void listen() {
		String buffer;

		// while ((buffer = in.readLine()) != null) {
		// enteredText.insert(buffer + "\n", enteredText.getText().length());
		// enteredText.setCaretPosition(enteredText.getText().length());
		// }
		// should never gets here unless the server dies...

		while ((buffer = in.readLine()) != null) {
			String[] pos = buffer.split(":");
			for (String p : pos) {
				System.out.println("[" + p + "]");
			}
			// pos[1].toLowerCase();

			// if(pos.length == 4 && pos[1].equals("sheep")){
			String player = pos[0];
			Random random = new Random();
			try {
				setUpASheep(player, random.nextInt(12), random.nextInt(12));
				// setUpASheep(player,
				// Integer.parseInt(pos[2]),Integer.parseInt(pos[3]));
				// board.repaint();
			} catch (Exception ex) {
				System.out.println("Dude they just told you whats up?");
			}
			// }
		}

		out.close();
		in.close();
		try {
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("closed client socket");
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////

	private void setUpASheep(String player, int initX, int initY) {

		// for(Sheep sheep: sheeps){
		// if(sheep.getMyOwner().equals(player)){
		// return;
		// }
		// }
		createSheepForPlayer(player, initX, initY);

	}

	private void createSheepForPlayer(String player, int initX, int initY) {
		this.playerSheep = new Sheep(initX, initY, SHEEP_COLOR);
		this.playerSheep.setMyOwner(player);
		this.sheeps.add(playerSheep);
		rangeCanvas.addEntity(playerSheep);
		rangeCanvas.repaint();
	}

	/*
	 * @Override public void actionPerformed(ActionEvent e) { String message =
	 * typedText.getText(); System.out.println(message); String outMessage =
	 * userName + ": " + message;
	 * 
	 * if (message.startsWith("/")) outMessage = message;
	 * 
	 * out.println(outMessage); typedText.setText("");
	 * typedText.requestFocusInWindow(); }
	 */
}