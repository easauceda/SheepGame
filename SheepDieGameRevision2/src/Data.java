import java.util.ArrayList;

public class Data {
	RancherGame ranch;
	private ArrayList<Wolf> wolfs = new ArrayList<Wolf>();
	private ArrayList<Sheep> sheeps = new ArrayList<Sheep>();

	public Data(RancherGame ranch, final String data, ArrayList<Sheep> sheeps,
			ArrayList<Wolf> wolfs) {
		this.wolfs = wolfs;
		this.sheeps = sheeps;
		this.ranch = ranch;
		class MyThread extends Thread {
			public void run() {
				try {
					sortThisData(data);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		MyThread serverThread = new MyThread();
		serverThread.start();
	}

	private void sortThisData(String data) {
		if (ranch.getTypeOfClient().equalsIgnoreCase("normal")) {
			runSinglePlayerListener(data);
		} else {
			if (ranch.getTypeOfClient().equalsIgnoreCase("wolf")) {
				wolfListener(data);
			} else {
				sheepListener(data);
			}
		}
		ranch.repaint();
	}

	private void sheepListener(String buffer) {
		// TODO Auto-generated method stub
		String[] q = buffer.split(" ");
		if (q[0].equalsIgnoreCase("wolfserver")) {
			for (Entity wolf : wolfs) {
				if (wolf.name.equalsIgnoreCase(q[2])) {
					wolf.setX(Integer.parseInt(q[3]));
					wolf.setY(Integer.parseInt(q[4]));
					return;
				}
			}
			Wolf g = new Wolf(Integer.parseInt(q[3]), Integer.parseInt(q[4]),
					ranch.WOLF_COLOR);
			g.setName(q[2]);
			addThisToSheep(g);
			wolfs.add(g);
			ranch.getRangeCanvas().addEntity(g);
		}
	}

	private void addThisToSheep(Wolf wolf) {
		for (Sheep s : sheeps) {
			s.setWolfOne(wolf);
		}

	}

	private void wolfListener(String buffer) {
		// TODO Auto-generated method stub
		String[] q = buffer.split(" ");
		
		// [NickName] wolf [WolfId] killed sheep [SheepId] location [X] [Y]
		if (buffer.contains("sheep")) {// hopefully it cuts down the

			for (Sheep s : sheeps) {
				if (s.name.equalsIgnoreCase(q[2])
						&& !q[3].equalsIgnoreCase("killed")) {
					s.setX(Integer.parseInt(q[3]));
					s.setY(Integer.parseInt(q[4]));
					// s.reviveMe();
					return;
				}
			}
			if (q[3].equalsIgnoreCase("killed")) {
				for (Sheep s : ranch.getSheeps()) {
					if (q[3].equalsIgnoreCase("killed")
							&& s.name.equalsIgnoreCase(q[5])) {
						s.die();
						return;
					}
				}
			}
			Sheep g = new Sheep(Integer.parseInt(q[3]), Integer.parseInt(q[4]),
					ranch.SHEEP_COLOR);
			g.setName(q[2]);
			addThisToWolfs(g);
			sheeps.add(g);
			ranch.getRangeCanvas().addEntity(g);
		}
	}

	private void addThisToWolfs(Sheep sheep) {
		for (Wolf w : wolfs) {
			w.addSheep(sheep);
		}

	}

	private void runSinglePlayerListener(String buffer) {
		// TODO Auto-generated method stub
		String[] q = buffer.split(" ");

	}
}
