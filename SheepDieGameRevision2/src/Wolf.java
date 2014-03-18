import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;

//////
public class Wolf extends Entity {

	private PriorityQueue<Target> targets = new PriorityQueue<Target>();
	private ArrayList<Sheep> sheeps = new ArrayList<Sheep>();
	private boolean imIt = false;
	private Wolf noTagBacks = null;
	private int countToTen = 10000;

	public Wolf(int x, int y, Color c) {

		super(x, y, c);
	}

	public String toString() {
		return "wolf" + super.toString();
	}

	void paint(Graphics pen) {
		pen.setColor(c);
		pen.fillRect(X_MARGIN + xstep * x + 2, Y_MARGIN + ystep * y + 2, 16, 16);
		// draw a line to my target.........
		if (targets.size() > 0 && debugMode && sheepStillAlive(sheeps)) {
			pen.setColor(Color.red);
			pen.drawLine(X_MARGIN + xstep * x + 8, Y_MARGIN + ystep * y + 8,
					Y_MARGIN + ystep * (targets.peek().getSheep().getX()) + 8,
					Y_MARGIN + ystep * (targets.peek().getSheep().getY()) + 8);

			/*
			 * pen.setColor(Color.green); pen.drawLine(X_MARGIN + xstep * x + 8,
			 * Y_MARGIN + ystep * y + 8, Y_MARGIN + ystep * (iWantX) + 8,
			 * Y_MARGIN + ystep * (iWantY) + 8);
			 */
		}
	}

	private void huntAction(ArrayList<Sheep> sheeps, ArrayList<Wolf> wolfs) {
		// new Wolf starts here
		boolean huntAsPack = true;
		while (true) {
			if (!sheepStillAlive(sheeps)) {
				playTag(wolfs);
			} else {
				while (sheepStillAlive(sheeps)) {
					reorderQueue();
					huntAsPack = areWeStillAPack(wolfs, sheeps);
					if (targets.size() > 0) {

						if (targets.peek().isTargetStillAlive()
								&& nobodyClose(huntAsPack, wolfs)) {

							huntTheTarget();

						} else {
							if (debugMode) {
								this.c = Color.CYAN;
								RancherGame.pause(50);

							}
							this.c = Color.black;
							this.targets.remove();
							reorderQueue();
						}
					} else {
						createSheepQueue(sheeps);
					}
				}
			}
		}
	}

	private void playTag(ArrayList<Wolf> wolfs) {

		if (imIt) {
			if (countToTen == 0) {
				this.c = Color.pink;
				moveMeThere(chaseTagDecisionMaker(whoIsClosest(wolfs)));

				yourItSucker(wolfs);
			} else {
				this.countToTen--;
			}
		} else {
			this.c = Color.DARK_GRAY;
			RancherGame.pause(100);
			moveMeThere(whereToDecisionMaker(runAwayFromWho(wolfs)));
		}

	}

	private void yourItSucker(ArrayList<Wolf> wolfs) {
		for (Wolf wolf : wolfs) {
			if (this.sameCell(wolf) && !wolf.equals(this)) {
				wolf.yourIt();
				wolf.setNoTagBacks(this);
				System.out.println("Tag your It " + wolf);
				this.countToTen = 1000;
				this.imIt = false;
				this.c = Color.black;
				this.noTagBacks = null;

				return;
			}
		}

	}

	public void setNoTagBacks(Wolf wolf) {
		this.noTagBacks = wolf;
	}

	private int whereToDecisionMaker(Entity thingy) {
		// try{
		if (sheepStillAlive(sheeps)) {
			int j = 8;
			for (int q = 7; q >= 0; q--) {
				if (getDistance(x + choseX(j) - thingy.getX(), y + choseY(j)
						- thingy.getY()) > getDistance(
							x + choseX(q) - thingy.getX(), y + choseY(q)
									- thingy.getY())
						&& x + choseX(q) >= 0
						&& x + choseX(q) < max_X
						&& y + choseY(q) >= 0 && y + choseY(q) < max_Y) {
					if (sheepInSquare(q)) {
						return q;
					}
					j = q;

				}
			}

			return j;
		} else {
			int j = 8;
			int k[] = new int[j];
			Random ran = new Random();
			for (int q = 7; q >= 0; q--) {
				if (getDistance(x - thingy.getX(), y - thingy.getY()) < 4
						&& getDistance(x + choseX(j) - thingy.getX(), y
								+ choseY(j) - thingy.getY()) < getDistance(x
								+ choseX(q) - thingy.getX(), y + choseY(q)
								- thingy.getY()) && x + choseX(q) > 0
						&& x + choseX(q) < max_X && y + choseY(q) > 0
						&& y + choseY(q) < max_Y) {

					j = q;
				}
			}
			// this is to try and have the wolf be able to move in multiple
			// movements besides just the one first or last
			k[0] = j;
			int d = 1;
			for (int q = 7; q >= 0; q--) {
				if (getDistance(x + choseX(j) - thingy.getX(), y + choseY(j)
						- thingy.getY()) == getDistance(
						x + choseX(q) - thingy.getX(),
						y + choseY(q) - thingy.getY())
						&& x + choseX(q) > 0
						&& x + choseX(q) < max_X
						&& y + choseY(q) > 0 && y + choseY(q) < max_Y) {
					k[d++] = q;

				}
			}
			// this is where he makes his choice for if there are more than
			// one;;;
			if (d > 1) {
				j = k[ran.nextInt(d)];
			}

			return j;
		}
		// }catch(NullPointerException e){return 8;}
	}

	private boolean sheepInSquare(int q) {
		if (x + choseX(q) == targets.peek().getSheep().getX()
				&& y + choseY(q) == targets.peek().getSheep().getY()
				&& targets.peek().getSheep().isAlive()) {
			return true;
		}
		for (Sheep sheep : sheeps) {
			if (x + choseX(q) == sheep.getX() && y + choseY(q) == sheep.getY()
					&& sheep.isAlive()) {

				return true;
			}
		}
		return false;
	}

	private Wolf whoIsClosest(ArrayList<Wolf> wolfs) {
		Wolf wolfIt = null;
		double closest = 1000000000;
		for (Wolf wolf : wolfs) {

			if (!wolf.equals(this) && distanceTag(wolf) < closest
					&& !wolf.equals(noTagBacks)) {
				closest = distanceTag(wolf);
				wolfIt = wolf;
			}
		}

		return wolfIt;
	}

	private double distanceTag(Wolf wolf) {
		return Math.sqrt(Math.pow(this.getY() - wolf.getY(), 2)
				+ Math.pow(this.getX() - wolf.getX(), 2));
	}

	private Wolf runAwayFromWho(ArrayList<Wolf> wolfs) {
		Wolf wolfIt = null;

		for (Wolf wolf : wolfs) {
			if (wolf.imIt()) {
				wolfIt = wolf;
				// System.out.println(wolf);
			}
		}

		if (wolfIt.equals(null)) {
			return runAwayFromWho(wolfs);
		}
		return wolfIt;
	}

	private boolean areWeStillAPack(ArrayList<Wolf> wolfs,
			ArrayList<Sheep> sheeps) {

		if (!areWeTargetingAll(sheeps, wolfs)) {
			return true;
		}
		if (debugMode) {
			this.c = Color.green;
		}
		return false;

	}

	private boolean areWeTargetingAll(ArrayList<Sheep> sheeps,
			ArrayList<Wolf> wolfs) {
		for (Sheep sheep : sheeps) {
			if (areYouNotBeingChased(wolfs, sheep) && sheep.isAlive()) {
				return false;
			}
		}
		return true;
	}

	private boolean areYouNotBeingChased(ArrayList<Wolf> wolfs, Sheep sheep) {

		for (Wolf wolf : wolfs) {
			try {
				if (sheep.equals(wolf.getTarget())) {
					return false;
				}
			} catch (NullPointerException e) {
			}
		}

		return true;
	}

	private void reorderQueue() {
		PriorityQueue<Target> redoTargets = new PriorityQueue<Target>();

		while (targets.size() > 0) {
			if (targets.peek().isTargetStillAlive()) {
				redoTargets
						.add(new Target(this.targets.peek().getSheep(), this));

			}
			this.targets.remove();
		}
		this.targets = redoTargets;
	}

	private boolean nobodyClose(boolean huntAsPack, ArrayList<Wolf> wolfs) {
		if (huntAsPack) {
			for (Wolf wolf : wolfs) {

				if (wolf.isThisYourTarget(targets.peek().getSheep())
						&& whoIsCloser(wolf)) {
					return false;
				}
			}

		}
		return true;
	}

	private boolean whoIsCloser(Wolf wolf) {

		if (targets.peek().getDistance() <= wolf.getTargetDistance()) {
			// Me
			return false;

		}
		return true;
	}

	public double getTargetDistance() {

		return targets.peek().getDistance();
	}

	public boolean isThisYourTarget(Sheep target) {
		try {
			return target.equals((targets.peek().getSheep()));
		} catch (NullPointerException e) {
			return false;
		}
	}

	public Sheep getTarget() {
		if (targets.peek().equals(null)) {
			return null;
		}
		return this.targets.peek().getSheep();
	}

	private void huntTheTarget() {
		moveMeThere(whereToDecisionMaker(this.targets.peek().getSheep()));
	}

	private void anySheepHereKillThem(ArrayList<Sheep> sheeps) {
		for (Sheep sheep : sheeps) {
			if (this.sameCell(sheep)) {
				sheep.die();
			}
		}

	}

	private void moveMeThere(int move) {
		if (sheepStillAlive(sheeps)) {
			this.x = x + choseX(move);
			this.y = y + choseY(move);
			//anySheepHereKillThem(sheeps);
			broadcast(" wolf "+name+" "+x+" "+y);
			RancherGame.pause();
			return;
		}
		this.x = x + choseX(move);
		this.y = y + choseY(move);
		broadcast(" wolf "+name+" "+x+" "+y);
		RancherGame.pause(400);

	}

	private int chaseTagDecisionMaker(Wolf wolf) {
		int j = 0;

		for (int q = 1; q < 8; q++) {
			if (getDistance(x + choseX(j) - wolf.getX(),
					y + choseY(j) - wolf.getY()) > getDistance(x + choseX(q)
					- wolf.getX(), y + choseY(q) - wolf.getY())) {
				j = q;
			}
		}
		return j;

	}

	private double getDistance(int x, int y) {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}

	private boolean sheepStillAlive(ArrayList<Sheep> sheeps) {
		for (Sheep sheep : sheeps) {
			if (sheep.isAlive()) {
				return true;
			}
		}
		return false;
	}

	private int choseY(int counter) {
		switch (counter) {
		case 0:
			return -1;
		case 1:
			return -1;
		case 2:
			return -1;
		case 3:
			return 0;
		case 4:
			return 0;
		case 5:
			return 1;
		case 6:
			return 1;
		case 7:
			return 1;
		case 8:
			return 0;
		}
		return 0;
	}

	private int choseX(int counter) {
		switch (counter) {
		case 0:
			return -1;
		case 1:
			return 0;
		case 2:
			return 1;
		case 3:
			return -1;
		case 4:
			return 1;
		case 5:
			return -1;
		case 6:
			return 0;
		case 7:
			return 1;
		case 9:
			return 0;
		}
		return 0;
	}

	void hunt(final ArrayList<Wolf> wolfs) {
		createSheepQueue(sheeps);

		class MyThread extends Thread {
			public void run() {
					huntAction(sheeps, wolfs);
			}
		}

		MyThread huntThread = new MyThread();
		huntThread.start();
	}

	public void addSheep(Sheep sheep) {
		this.sheeps.add(sheep);
	}

	private void createSheepQueue(ArrayList<Sheep> sheeps) {
		this.targets.clear();
		for (Sheep sheep : sheeps) {
			if (sheep.isAlive()) {
				this.targets.add(new Target(sheep, this));
			}
		}
	}

	public void yourIt() {
		this.imIt = true;

	}

	public boolean imIt() {
		return imIt;
	}

}