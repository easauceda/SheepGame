import java.awt.BorderLayout;
import java.awt.Color;
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
	private String dadsName = null;
	private boolean foundDaddy = false;
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

		});

	}

	private void makeOneSheepAlive() {
		for (Sheep sheep : sheeps) {
			if (!sheep.isAlive()) {
				sheep.reviveMe();
				return;
			}
		}
	}

	private void makeAllSheepsAlive() {
		for (Sheep sheep : sheeps) {
			sheep.reviveMe();
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

	private void giveWolfOneSheep(Sheep sheep) {
		for (Wolf wolf : wolfs) {
			wolf.addSheep(sheep);
		}
	}

	private void setTheAnimals() {
		Random random = new Random();
		for (int q = 0; q < numberOfSheeps; q++) {
			sheeps.add(new Sheep(random.nextInt(max_X - 1), random
					.nextInt(max_Y - 1), SHEEP_COLOR));
		}
		if (input.getPositions().size() == 0) {

			for (int q = 0; q < numberOfWolfs; q++) {
				wolfs.add(new Wolf(random.nextInt(max_X - 1), random
						.nextInt(max_Y - 1), WOLF_COLOR));
			}
		} else {
			for (String s : input.getPositions()) {
				String xy[] = s.split(",");
				wolfs.add(new Wolf(Integer.parseInt(xy[0]), Integer
						.parseInt(xy[1]), WOLF_COLOR));

			}
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
			System.out.println(pos[1]);
			if (pos[1].equalsIgnoreCase(" add sheep")) {
				createSheepForPlayer(pos[0], Integer.parseInt(pos[2]),
						Integer.parseInt(pos[3]));
			}
			if (pos[1].equalsIgnoreCase(" a")) {
				// System.out.println("HELLO");
				String tester = pos[0];
				System.out.println(tester);
				for (Sheep i : sheeps) {
					String compare = i.getMyOwner();
					// System.out.println(compare);
					if (compare != null) {
						// System.out.println("made it");
						if (compare.equalsIgnoreCase(tester)) {
							i.setX(i.getX() - 1);
							repaint();
						}
					}
				}
			}
			if (pos[1].equalsIgnoreCase(" d")) {
				// System.out.println("HELLO");
				String tester = pos[0];
				System.out.println(tester);
				for (Sheep i : sheeps) {
					String compare = i.getMyOwner();
					// System.out.println(compare);
					if (compare != null) {
						// System.out.println("made it");
						if (compare.equalsIgnoreCase(tester)) {
							i.setX(i.getX() + 1);
							repaint();
						}
					}
				}
			}
			if (pos[1].equalsIgnoreCase(" s")) {
				// System.out.println("HELLO");
				String tester = pos[0];
				System.out.println(tester);
				for (Sheep i : sheeps) {
					String compare = i.getMyOwner();
					// System.out.println(compare);
					if (compare != null) {
						// System.out.println("made it");
						if (compare.equalsIgnoreCase(tester)) {
							i.setY(i.getY() + 1);
							repaint();
						}
					}
				}
			}
			if (pos[1].equalsIgnoreCase(" w")) {
				// System.out.println("HELLO");
				String tester = pos[0];
				System.out.println(tester);
				for (Sheep i : sheeps) {
					String compare = i.getMyOwner();
					// System.out.println(compare);
					if (compare != null) {
						// System.out.println("made it");
						if (compare.equalsIgnoreCase(tester)) {
							i.setY(i.getY() - 1);
							repaint();
						}
					}
				}
			} else if (foundDaddy) {
				// for anything in here to actually execute you must first run
				// the command "I am your father" which makes
				// you the root user.....................................
				String player = pos[0];
				System.out.println("[" + dadsName + "]");
				if (player.equalsIgnoreCase(dadsName)) {
					// /////////////////////////////root commands
					// here//////////////////////////////////////
					System.out.println("[" + pos[1] + "]");
					commandEntered(pos[1]);

					// ////////////////////////////////////////////////////////////////////////////////////
				}

				// /////////////////////////////player commands
				// here//////////////////////////////////
				Random random = new Random();
				try {
					setUpASheep(player, random.nextInt(12), random.nextInt(12));

				} catch (Exception ex) {
					System.out.println("Dude they just told you whats up?");
				}

				// ////////////////////////////////////////////////////////////////////////////////////
			} else {
				if (pos[1].equalsIgnoreCase(" i am your father")) {
					this.foundDaddy = true;
					this.dadsName = pos[0];
					System.out.println("my Fathers Name Is " + dadsName);
				} else {
					System.out.println("im waiting for my father.");
				}
			}
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

	private void commandEntered(String string) {
		try {
			String[] breakUp = string.split("\\.");

			System.out.println("[" + breakUp[0] + "]");
			if (breakUp[0].equalsIgnoreCase(" move")) {
				moveSomething(breakUp[1]);
			} else {
				if (breakUp[0].equalsIgnoreCase(" add")) {
					addSomething(breakUp[1]);
				} else {
					if (breakUp[0].equalsIgnoreCase(" start")) {
						letTheGamesBegin();
					} else {
						if (breakUp[0].equalsIgnoreCase(" zombie")) {
							bringTolife(breakUp[1]);
						} else {
							if (breakUp[0].equalsIgnoreCase(" clear")) {
								clearBoard(breakUp[1]);
							}
						}
					}
				}
			}

		} catch (Exception e) {
			System.out.println("I don't understand");
			return;
		}
	}

	private void clearBoard(String string) {
		System.out.println("function still not Implemented");
	}

	private void bringTolife(String breakUp) {
		System.out.println(breakUp);
		if (breakUp.equalsIgnoreCase("one")) {
			makeOneSheepAlive();
			return;
		} else {
			if (breakUp.equalsIgnoreCase("all")) {
				makeAllSheepsAlive();
				System.out.println("zombiesssssss");
				return;
			}
		}
		System.out.println("command not understood");
	}

	private void addSomething(String string) {
		System.out.println(string);
		try {
			String[] breakUp = string.split("=");
			String[] addTo = breakUp[1].split(",");
			System.out.println(breakUp[0] + " " + addTo[0] + "," + addTo[1]);

			if (breakUp[0].equalsIgnoreCase("sheep")) {

				createSheep((Integer.parseInt(addTo[0])),
						(Integer.parseInt(addTo[1])));

				return;
			} else {
				if (breakUp[0].equalsIgnoreCase("wolf")) {
					Wolf creature = new Wolf((Integer.parseInt(addTo[0])),
							(Integer.parseInt(addTo[1])), WOLF_COLOR);
					wolfs.add(creature);

					rangeCanvas.addEntity(creature);
					rangeCanvas.repaint();

					return;

				}
			}
			System.out.println("What kind of creature is that?");

		} catch (Exception e) {
			System.out.println("I don't understand");
			return;
		}

	}

	private void letTheGamesBegin() {
		if (!wolfIsPushed) {

			timer.start();
			startMovingSheep();
			wolfsPlayTag();
			giveWolfHisSheep();
			startMovingWolfs();

			wolfIsPushed = true;
		}

	}

	private void moveSomething(String string) {
		try {
			String[] breakUp = string.split("=");
			String[] from = breakUp[0].split(",");
			String[] mTo = breakUp[1].split(",");

			for (Entity e : rangeCanvas.giveMeEntities()) {
				if (e.getX() == Integer.parseInt(from[0])
						&& e.getY() == Integer.parseInt(from[1])) {
					e.setX(Integer.parseInt(mTo[0]));
					e.setY(Integer.parseInt(mTo[1]));
					System.out.println("okay he was moved");
					rangeCanvas.repaint();
					return;
				}
			}
			System.out.println("Entity does not exist in location.");

		} catch (Exception e) {
			System.out.println("I don't understand");
			return;
		}

	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////

	private void setUpASheep(String player, int initX, int initY) {

		// for(Sheep sheep: sheeps){
		// if(sheep.getMyOwner().equals(player)){
		// return;
		// }
		// }

		// sorry had to comment this out to be able to not keep on adding sheep.
		// createSheepForPlayer(player, initX, initY);

	}

	private void createSheepForPlayer(String player, int initX, int initY) {

		int random = (int) (Math.random() * ((10 - 0) + 1));
		Color playerColor = null;
		if (random == 1) {
			playerColor = new Color(255, 0, 0);
		}
		if (random == 2) {
			playerColor = new Color(0, 255, 0);
		}
		if (random == 3) {
			playerColor = new Color(0, 0, 255);
		}
		if (random == 4) {
			playerColor = new Color(255, 255, 0);
		}
		if (random == 5) {
			playerColor = new Color(255, 0, 255);
		}
		if (random == 6) {
			playerColor = new Color(0, 255, 255);
		}
		if (random == 7) {
			playerColor = new Color(255, 0, 125 );
		}
		if (random == 8) {
			playerColor = new Color(0, 100, 255);
		}
		if (random == 9) {
			playerColor = new Color(0, 155, 255);
		}
		if (random == 10) {
			playerColor = new Color(155, 255, 255);
		}
		Sheep playerSheep = new Sheep(initX, initY, playerColor);
		playerSheep.setMyOwner(player);
		this.sheeps.add(playerSheep);
		rangeCanvas.addEntity(playerSheep);
		giveWolfOneSheep(playerSheep);
		rangeCanvas.repaint();
	}

	private void createSheep(int initX, int initY) {
		Sheep newSheep = new Sheep(initX, initY, SHEEP_COLOR);
		this.sheeps.add(newSheep);
		rangeCanvas.addEntity(newSheep);
		giveWolfOneSheep(newSheep);
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
