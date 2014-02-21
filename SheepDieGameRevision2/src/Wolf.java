import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.PriorityQueue;

//////
public class Wolf extends Entity {

	private PriorityQueue<Target> targets = new PriorityQueue<Target>();

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
		if (targets.size() > 0 && debugMode) {
			pen.drawLine(X_MARGIN + xstep * iWantX + 4, Y_MARGIN + ystep * iWantY + 4,
					Y_MARGIN + ystep * (targets.peek().getSheep().getX()) + 4,
					Y_MARGIN + ystep * (targets.peek().getSheep().getY()) + 4);
			pen.setColor(Color.green);
			pen.fillRect(X_MARGIN + xstep * iWantX + 2, Y_MARGIN + ystep
					* iWantY + 2, 16, 16);
		}
	}

	private void huntAction(ArrayList<Sheep> sheeps, ArrayList<Wolf> wolfs) {
		// new Wolf starts here
		boolean huntAsPack = true;
		while (sheepStillAlive(sheeps)) {
				huntAsPack = areWeStillAPack(wolfs,sheeps);
			if (targets.size() > 0) {
				if (targets.peek().isTargetStillAlive()
						&& nobodyClose(huntAsPack, wolfs)) {

					huntTheTarget();

				} else {
					if (debugMode) {
						this.c = Color.CYAN;
						RancherGame.pause(500);
						this.c = Color.black;
					}
					this.targets.remove();
					reorderQueue();
				}
			} else {createSheepQueue(sheeps);
			}
			anySheepHereKillThem(sheeps);
		}

	}

	private boolean areWeStillAPack(ArrayList<Wolf> wolfs,
			ArrayList<Sheep> sheeps) {
		
		if(!areWeTargetingAll(sheeps,wolfs)){
			return true;
		}
		if (debugMode) {
			this.c = Color.red;
		}
		return false;
		
	}

	private boolean areWeTargetingAll(ArrayList<Sheep> sheeps,
			ArrayList<Wolf> wolfs) {
		for(Sheep sheep:sheeps){
			if(!areyoubeingChased(wolfs,sheep)&&sheep.isAlive()){
				return false;
			}
		}
		return true;
	}

	private boolean areyoubeingChased(ArrayList<Wolf> wolfs, Sheep sheep) {
		
		for(Wolf wolf: wolfs){
			try{
			if (sheep.equals(wolf.getTarget())){
				return true;
			}
			}catch(NullPointerException e){}
		}
		
		return false;
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
		this.targets.clear();
		this.targets = redoTargets;
	}

	private boolean nobodyClose(boolean huntAsPack, ArrayList<Wolf> wolfs) {
		if (huntAsPack) {
			for (Wolf wolf : wolfs) {
				
					if (wolf.isThisYourTarget(targets.peek().getSheep()) && whoIsCloser(wolf)) {
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
		try{
		return target.equals(targets.peek().getSheep());
		}catch(NullPointerException e){return false;}
	}

	public Sheep getTarget() {
		if(targets.peek().equals(null)){
			return null;
		}
		return this.targets.peek().getSheep();
	}

	private void huntTheTarget() {
		makeYourMove();

	}

	private void anySheepHereKillThem(ArrayList<Sheep> sheeps) {
		for (Sheep sheep : sheeps) {
			if (this.sameCell(sheep)) {
				sheep.die();
			}
		}

	}

	private void makeYourMove() {
		moveMeThere(decisionMaker());
	}

	private void moveMeThere(int move) {
		if(!debugMode){
		RancherGame.pause();
		}
		this.iWantX = x + choseX(move);
		this.iWantY = y + choseY(move);
		if (debugMode) {
			RancherGame.pause();
		}
		this.x = iWantX;
		this.y = iWantY;
	}

	private int decisionMaker() {
		int j = 0;
		for (int q = 1; q < 8; q++) {
			if (getDistance(x + choseX(j) - targets.peek().targetX(), y
					+ choseY(j) - targets.peek().targetY()) > getDistance(x
					+ choseX(q) - targets.peek().targetX(), y + choseY(q)
					- targets.peek().targetY())) {
				j = q;
			}
		}
		return j;
	}

	private double getDistance(int x, int y) {
		return (Math.pow(x, 2) + Math.pow(y, 2));
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
		}
		return 0;
	}

	void hunt(final ArrayList<Sheep> sheeps, final ArrayList<Wolf> wolfs) {

		createSheepQueue(sheeps);

		System.out.println(targets.peek().isTargetStillAlive());

		class MyThread extends Thread {
			public void run() {
				huntAction(sheeps, wolfs);
			}
		}

		MyThread huntThread = new MyThread();
		huntThread.start();
	}

	private void createSheepQueue(ArrayList<Sheep> sheeps) {
		this.targets.clear();
		for (Sheep sheep : sheeps) {
			if (sheep.isAlive()) {
				this.targets.add(new Target(sheep, this));
			}
		}
	}
}