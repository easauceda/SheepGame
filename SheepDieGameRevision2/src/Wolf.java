import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;

public class Wolf extends Entity {
	private PriorityQueue<Target> targets = new PriorityQueue<Target>();
	private ArrayList<Wolf> wolfs;
	private ArrayList<Sheep> sheeps;
	private ArrayList<String> positions = new ArrayList<String>();
	private boolean wolfPositionIveBeen[][] = new boolean[11][11];
	private boolean tracking = true;
	private boolean grassEffect = false;

	public Wolf(int x, int y, Color c) {
		super(x, y, c);
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				wolfPositionIveBeen[i][j] = false;
			}
		}
	}

	public String toString() {
		return "wolf" + super.toString();
	}

	void paint(Graphics pen) {
		pen.setColor(c);
		pen.fillRect(X_MARGIN + xstep * x + 2, Y_MARGIN + ystep * y + 2, 16, 16);
	}

	private void huntAction() {
		// new Wolf starts here
		boolean huntAsPack = false;
		while (sheepStillAlive(sheeps)) {

			// targets.add(targets.poll());
			if (targets.size() != 0) {
			try{
				huntTheTarget(huntAsPack);
				refreshQueue();
			}catch(NullPointerException e){}
			} else {
				huntAsPack = true;
				try{
				refreshWithSheeps();
				
				}catch(NullPointerException e){}
			}

		}

		// new type of Wolf ends here
		/*
		 * if (tracking) { try { wolfPositionIveBeen[x][y] = true; } catch
		 * (ArrayIndexOutOfBoundsException e) { } } x = x + moveX; y = y +
		 * moveY; RancherGame.pause(); if (this.sameCell(sheep)) { //
		 * huntThread.stop(); sheep.die(); return; }
		 * 
		 * positions.add(x + "," + y); ArrayList<String> positionsInside = new
		 * ArrayList<String>(); String Q[]; int bigX, bigY; for (String p :
		 * positions) { Q = p.split(","); bigX = Integer.parseInt(Q[0]); bigY =
		 * Integer.parseInt(Q[1]); positionsInside.add(bigX + "," + bigY); }
		 * 
		 * boolean moves8[] = new boolean[8]; for (int R = 0; R < 8; R++) {
		 * moves8[R] = false; }// dont forget this braket it causes alot of
		 * trouble
		 * 
		 * int counter; Random ran = new Random(); while (!moves8[0] ||
		 * !moves8[1] || !moves8[2] || !moves8[3] || !moves8[4] || !moves8[5] ||
		 * !moves8[6] || !moves8[7]) {
		 * 
		 * // System.out.println("+++++hitting the random"); counter =
		 * ran.nextInt(8); int nMoveX, nMoveY, checkX, checkY; nMoveX =
		 * choseX(counter); nMoveY = choseY(counter); for (String p :
		 * positionsInside) { Q = p.split(","); checkX = Integer.parseInt(Q[0]);
		 * checkY = Integer.parseInt(Q[1]); System.out.println(checkX + "'" +
		 * checkY + "move       " + counter); if (x + nMoveX == checkX && y +
		 * nMoveY == checkY) { moves8[counter] = true; //
		 * System.out.println("okay this one was done a while back"); } } if ((x
		 * + nMoveX >= 0) && (y + nMoveY >= 0) && (x + nMoveX < 11) && (y +
		 * nMoveY < 11)) { if (!moves8[counter]) { //
		 * System.out.println("I guess this one hasnt been made");
		 * 
		 * RancherGame.pause(); if(grassEffect){ gamePauseByGrass(grass); }
		 * huntAction(nMoveX, nMoveY, sheep, positionsInside,grass); if
		 * (this.sameCell(sheep)) { // huntThread.stop(); sheep.die(); return; }
		 * x = x - nMoveX; y = y - nMoveY; RancherGame.pause(); } } else {
		 * moves8[counter] = true; }
		 * 
		 * } if (tracking) { try { wolfPositionIveBeen[x][y] = false; } catch
		 * (ArrayIndexOutOfBoundsException e) { } } //
		 * System.out.println("BackTrackTime"); return;
		 */
	}

	private void refreshWithSheeps() {
		for (Sheep sheep : sheeps) {
			if(sheep.isAlive()){
			targets.add(new Target(sheep,this));
		}
		}
	}

	private void huntTheTarget(boolean together) {
		// if(!this.sameCell(targets.peek().getSheep())){
		
		refreshQueue();
		if (!together) {
			anyOneHasThisTarget();
		}
		makeYourMove();
		anySheepHereKillThem();
		// }else{targets.poll().targetKill();
		// return;}
	}

	private void anyOneHasThisTarget() {
		for (Wolf wolf : wolfs) {
			try {
				if (!this.equals(wolf)) {
					if (this.targets.peek().getSheep()
							.equals(wolf.targets.peek().getSheep())) {
						okayWhosCloser(wolf);

					}
				}
			} catch (NullPointerException e) {
			}
		}

	}

	private void okayWhosCloser(Wolf wolf) {
		if (this.targets.peek().getDistance() > wolf.targets.peek()
				.getDistance()) {
			this.targets.poll();
			refreshQueue();
			anyOneHasThisTarget();
		} else {
			wolf.notMySheep();
		}
	}

	public void notMySheep() {
		this.targets.poll();
		refreshQueue();
		System.out.println("mine");

	}

	private void refreshQueue() {
		PriorityQueue<Target> targets2 = new PriorityQueue<Target>();

		while (targets.size() > 0) {
			if (targets.peek().isTargetStillAlive()) {
				targets2.add(targets.poll());

			} else {
				targets.remove();
			}
		}
		this.targets.addAll(targets2);
	}

	private void anySheepHereKillThem() {
		for (Sheep sheep : sheeps) {
			if (this.sameCell(sheep)) {
				sheep.die();
			}
		}

	}

	private void makeYourMove() {
		int move = decisionMaker();
		RancherGame.pause();
		this.x = x + choseX(move);
		this.y = y + choseY(move);
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

	private void gamePauseByGrass(Grass[][] grass) {
		for (int U = 0; U <= 5 * grass[x][y].getHeightNumber(); U++) {
			// System.out.println("pausing");
			RancherGame.pause();
		}

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

	void hunt(ArrayList<Sheep> sheeps, ArrayList<Wolf> wolfs2, int n,
			Grass[][] grass, int g) {
		for (Sheep sheep : sheeps) {
			this.targets.add(new Target(sheep, this));
		}
		this.sheeps = sheeps;

		this.wolfs = wolfs2;

		System.out.println(targets.peek().isTargetStillAlive());
		if (n <= 0) {
			this.tracking = false;
		}
		if (g == 2) {
			this.grassEffect = true;
		}
		class MyThread extends Thread {
			public void run() {
				huntAction();
			}
		}

		MyThread huntThread = new MyThread();
		huntThread.start();
	}

	public boolean getWolfPositionIveBeen(int W, int Q) {
		return wolfPositionIveBeen[W][Q];
	}

}