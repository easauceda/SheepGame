import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class RancherGame extends JFrame implements GameSettings {
	// ///////////////////////////////////////////////////////////////////
	private String typeOfClient;
	private String dadsName = null;
	private boolean foundDaddy = false;
	private static final long serialVersionUID = 1L;
	private Socket socket;
	private In in;
	private Out out;
	private String userName;
	// ///////////////////this is new/////////////////////////////////////
	private boolean wolfIsPushed = false;
	private RangeCanvas rangeCanvas = new RangeCanvas();
	private JButton huntButton = new JButton("Hunt");
	private ArrayList<Wolf> wolfs = new ArrayList<Wolf>();
	private ArrayList<Sheep> sheeps = new ArrayList<Sheep>();
	private ArrayList<Node> nodes = new ArrayList<Node>();
	private ArrayList<Edge> edges = new ArrayList<Edge>();

	private final Timer timer;
	private final StopWatch timeToLunch = new StopWatch();
	private boolean gameOver = false;
	private JButton enterGame = new JButton("Enter the Game!");
	private String owner;

	public RancherGame(String userName, String server, int port, String type) {
		this.userName = userName;
		setTypeOfClient(type);
		System.out.println(type);
		setLayout(new GridLayout(1, 2));
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
		final JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		add(rangeCanvas);
		panel.setFocusable(true);
		panel.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == 119) {
					// System.out.println("CHECK");
					for (Sheep i : sheeps) {
						String compare = i.getMyOwner();
						// System.out.println(compare);
						if (compare != null) {
							// System.out.println("made it");
							if (compare.equalsIgnoreCase(owner)) {
								i.setY(i.getY() - 1);
								repaint();
							}
						}
					}
				}
				if (e.getKeyChar() == 115) {
					for (Sheep i : sheeps) {
						String compare = i.getMyOwner();
						// System.out.println(compare);
						if (compare != null) {
							// System.out.println("made it");
							if (compare.equalsIgnoreCase(owner)) {
								i.setY(i.getY() + 1);
								repaint();
							}
						}
					}
				}
				if (e.getKeyChar() == 97) {
					for (Sheep i : sheeps) {
						String compare = i.getMyOwner();
						// System.out.println(compare);
						if (compare != null) {
							// System.out.println("made it");
							if (compare.equalsIgnoreCase(owner)) {
								i.setX(i.getX() - 1);
								repaint();
							}
						}
					}
				}
				if (e.getKeyChar() == 100) {
					for (Sheep i : sheeps) {
						String compare = i.getMyOwner();
						// System.out.println(compare);
						if (compare != null) {
							// System.out.println("made it");
							if (compare.equalsIgnoreCase(owner)) {
								i.setX(i.getX() + 1);
								repaint();
							}
						}
					}
				}

			}

		});
		final JTextField feed = new JTextField();
		feed.setPreferredSize(new Dimension(200, 417));
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		panel.add(feed, c);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		huntButton.setPreferredSize(new Dimension(200, 26));
		panel.add(huntButton, c);
		c.gridx = 1;
		c.gridy = 1;
		panel.add(enterGame, c);
		enterGame.setPreferredSize(new Dimension(200, 26));
		enterGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String name = (String) JOptionPane.showInputDialog(null,
						"Enter your UserName", "New Sheep", 1, null, null,
						"Bob");
				createSheepForPlayer(name, 10, 10);
				owner = name;
				feed.setText(owner + " Added a Sheep!");
				panel.requestFocus();
			}

		});

		add(panel);
		whatDoICreate(type);

		timer = new Timer(BOARD_REFRESH_RATE, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// System.out.println(wolf + " " + sheep);
				rangeCanvas.repaint();

				if (areSheepsAlive() == true && gameOver == false
						&& typeOfClient.equalsIgnoreCase("normal")) {
					JOptionPane.showMessageDialog(null,
							("Time: " + timeToLunch.stopTiming()) + " Seconds!");
					gameOver = true;
					panel.repaint();
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
					if (typeOfClient.equalsIgnoreCase("normal")) {

						timer.start();
						timeToLunch.startTiming();
						wolfsPlayTag();
						giveWolfHisSheep();
						startMovingWolfs();
						startMovingSheep();
						wolfIsPushed = true;
						panel.requestFocus();
					} else {
						if (typeOfClient.equalsIgnoreCase("sheep")) {
							timer.start();
							startMovingSheep();
							wolfIsPushed = true;
						} else {
							timer.start();
							timeToLunch.startTiming();
							wolfsPlayTag();
							giveWolfHisSheep();
							startMovingWolfs();
							wolfIsPushed = true;
						}
					}

				}
			}

		});

	}

	private void sheepClientMode() {
		createNodesSetEdgesPassToCanvasFourCorners();

		for (int q = 0; q < numberOfSheeps; q++) {
			Node target = nodes
					.get((int) (Math.random() * ((nodes.size() - 1) + 1)));
			sheeps.add(new Sheep(target.getX(), target.getY(), SHEEP_COLOR,
					target));
			// sheeps.add(new Sheep(0,0,SHEEP_COLOR));
		}
		addNodesToCanvas();
		passSheepToCanvas(sheeps);
	}

	private void wolfClientMode() {
		Random random = new Random();
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

		passWolfsToCanvas(wolfs);
	}

	private void whatDoICreate(String type) {
		if (type.equalsIgnoreCase("normal")) {
			normalMode();
		} else {
			if (type.equalsIgnoreCase("sheep")) {
				sheepClientMode();
			} else {
				wolfClientMode();
			}
		}

	}

	private void normalMode() {
		createNodesSetEdgesPassToCanvasFourCorners();
		setTheAnimals();
		rangeCanvas.addGrassFractal();
	}

	private void setTypeOfClient(String type) {
		typeOfClient = type;

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
			sheep.getNodes(nodes);
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

	private void createNodesSetEdgesPassToCanvasFourCorners() {
		// Node x: 25 through 406
		// Node y: 35 through 415
		/*
		 * Node topLeft = new Node(0, 0, Color.black); Node topRight = new
		 * Node(10, 0, Color.black); Node bottomLeft = new Node(19, 5,
		 * Color.black); Node bottomRight = new Node(0, 15, Color.black);
		 * 
		 * Edge top = new Edge(topLeft, topRight); Edge right = new
		 * Edge(topRight, bottomRight); Edge bottom = new Edge(bottomRight,
		 * bottomLeft); Edge left = new Edge(bottomLeft, topLeft);
		 * 
		 * topLeft.setEdge(top); topLeft.setEdge(left); topRight.setEdge(top);
		 * topRight.setEdge(right); bottomLeft.setEdge(left);
		 * bottomLeft.setEdge(bottom); bottomRight.setEdge(bottom);
		 * bottomRight.setEdge(right);
		 * 
		 * nodes.add(topLeft); nodes.add(topRight); nodes.add(bottomLeft);
		 * nodes.add(bottomRight);
		 */
		createNodes();
		createEdges();
		addEdgesToCanvas();
	}

	private void createNodes() {

		for (String n : input.getNodes()) {
			String[] s = n.split(",");
			Node nodec = new Node(Integer.parseInt(s[1]),
					Integer.parseInt(s[2]), Color.blue);
			nodec.myNodeName(s[0]);
			nodes.add(nodec);
		}

	}

	private void addNodesToCanvas() {
		for (Node n : nodes) {
			rangeCanvas.addEntity(n);
		}
	}

	private void addEdgesToCanvas() {
		rangeCanvas.addEdges(edges);
	}

	private void createEdges() {

		for (String e : input.getEdges()) {
			String[] s = e.split(",");
			for (Node node1 : nodes) {
				if (node1.getName().equalsIgnoreCase(s[0])) {

					for (Node node2 : nodes) {
						if (node2.getName().equalsIgnoreCase(s[1])) {
							Edge edge = new Edge(node1, node2);
							edge.setWeight(Double.parseDouble(s[2]));
							node1.addEdge(edge);
							node2.addEdge(edge);
							edges.add(edge);
						}
					}
				}
			}

		}

	}

	private void setTheAnimals() {
		Random random = new Random();
		for (int q = 0; q < numberOfSheeps; q++) {
			Node target = nodes
					.get((int) (Math.random() * ((nodes.size() - 1) + 1)));
			sheeps.add(new Sheep(target.getX(), target.getY(), SHEEP_COLOR,
					target));
			// sheeps.add(new Sheep(0,0,SHEEP_COLOR));
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
		passEntitiesToCanvas();
	}

	private void passSheepToCanvas(ArrayList<Sheep> sheeps2) {
		int number = 0;
		for (Entity x : sheeps2) {
			x.setName(Integer.toString(number++));
			rangeCanvas.addEntity(x,this);
		}
	}

	private void passWolfsToCanvas(ArrayList<Wolf> wolfs2) {
		int number = 0;
		for (Entity x : wolfs2) {
			x.setName(Integer.toString(number++));
			rangeCanvas.addEntity(x,this);
		}
	}

	private void passEntitiesToCanvas() {

		for (Wolf wolf : wolfs) {
			rangeCanvas.addEntity(wolf);
		}
		for (Sheep sheep : sheeps) {
			rangeCanvas.addEntity(sheep);
		}
		for (Node node : nodes) {
			rangeCanvas.addEntity(node);
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

	public static void main(String args[]) throws Exception {

		if (args.length < 4) {
			try {
				input inputdata = new input();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			serverThread();
			String[] args2 = { "Game", "localhost", "4444", "normal" };
			RancherGame c = new RancherGame(args2[0], args2[1],
					Integer.parseInt(args2[2]), args2[3]);
			c.setLayout(new GridLayout(1, 1));
			c.setSize(windowSizeY + 400, windowSizeX + 120);
			c.setVisible(true);
			c.setLocationRelativeTo(null);
			c.setLocationRelativeTo(null);
			c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			c.listen();
			return;
		}
		RancherGame c = new RancherGame(args[0], args[1],
				Integer.parseInt(args[2]), args[3]);
		c.setLayout(new GridLayout(1, 1));
		c.setSize(windowSizeY + 400, windowSizeX + 120);
		c.setVisible(true);
		c.setLocationRelativeTo(null);
		c.setLocationRelativeTo(null);
		c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c.listen();
	}

	private static void serverThread() {
		class MyThread extends Thread {
			public void run() {
				try {
					ChatServer.main(null);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		MyThread serverThread = new MyThread();
		serverThread.start();
	}

	private static void chatClientThread() {
		class MyThread extends Thread {
			public void run() {
				try {
					String args[] = { "Narf", "localhost", "4444" };
					ChatClient.main(args);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		MyThread serverThread = new MyThread();
		serverThread.start();
	}

	public void sendIt(String typedText) {
		String message = typedText;
		System.out.println(message);
		String outMessage = userName + ": " + message;

		if (message.startsWith("/"))
			outMessage = message;

		out.println(outMessage);
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////
	private void listen() {
		String buffer;

		if(typeOfClient.equalsIgnoreCase("normal")){
			runSinglePlayerListener();
		}else{if(typeOfClient.equalsIgnoreCase("wolf")){
			wolfListener();
		}else{
				sheepListener();
			}
		}
		
	}

	private void sheepListener() {
		String buffer;
		while ((buffer = in.readLine()) != null) {
			System.out.println("jhi");
		}
		// should never gets here unless the server dies...
		out.close();
		in.close();
		try {
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("closed client socket");
		
	}

	private void wolfListener() {
		// TODO Auto-generated method stub
		String buffer;
		while ((buffer = in.readLine()) != null) {
			System.out.println("im a wolf");
		}
		// should never gets here unless the server dies...
		out.close();
		in.close();
		try {
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("closed client socket");
	}

	private void runSinglePlayerListener() {
		// TODO Auto-generated method stub
		String buffer;
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
			playerColor = new Color(255, 0, 125);
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
		// Sheep playerSheep = new Sheep(initX, initY, playerColor);
		// playerSheep.setMyOwner(player);
		// this.sheeps.add(playerSheep);
		// rangeCanvas.addEntity(playerSheep);
		// giveWolfOneSheep(playerSheep);
		// rangeCanvas.repaint();
	}

	private void createSheep(int initX, int initY) {
		// Sheep newSheep = new Sheep(initX, initY, SHEEP_COLOR);
		// this.sheeps.add(newSheep);
		// rangeCanvas.addEntity(newSheep);
		// giveWolfOneSheep(newSheep);
		// rangeCanvas.repaint();
	}

	public void sendOutThis(String message) {
		System.out.println(message);
		String outMessage = userName + ": " + message;

			outMessage = message;

		out.println(outMessage);

	}

	public String getUserName() {
		// TODO Auto-generated method stub
		return userName;
	}
}