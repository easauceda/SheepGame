

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;

public class Wolf extends Entity {
	private PriorityQueue<Sheep> targets = new PriorityQueue<Sheep>();
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

	private void huntAction(int moveX, int moveY, Sheep sheep,

	ArrayList<String> positions,Grass[][] grass) {
		if (tracking) {
			try {
				wolfPositionIveBeen[x][y] = true;
			} catch (ArrayIndexOutOfBoundsException e) {
			}
		}
		x = x + moveX;
		y = y + moveY;
		RancherGame.pause();
		if (this.sameCell(sheep)) {
			// huntThread.stop();
			sheep.die();
			return;
		}
		
		positions.add(x + "," + y);
		ArrayList<String> positionsInside = new ArrayList<String>();
		String Q[];
		int bigX, bigY;
		for (String p : positions) {
			Q = p.split(",");
			bigX = Integer.parseInt(Q[0]);
			bigY = Integer.parseInt(Q[1]);
			positionsInside.add(bigX + "," + bigY);
		}

		boolean moves8[] = new boolean[8];
		for (int R = 0; R < 8; R++) {
			moves8[R] = false;
		}// dont forget this braket it causes alot of trouble

		int counter;
		Random ran = new Random();
		while (!moves8[0] || !moves8[1] || !moves8[2] || !moves8[3]
				|| !moves8[4] || !moves8[5] || !moves8[6] || !moves8[7]) {

			// System.out.println("+++++hitting the random");
			counter = ran.nextInt(8);
			int nMoveX, nMoveY, checkX, checkY;
			nMoveX = choseX(counter);
			nMoveY = choseY(counter);
			for (String p : positionsInside) {
				Q = p.split(",");
				checkX = Integer.parseInt(Q[0]);
				checkY = Integer.parseInt(Q[1]);
				System.out.println(checkX + "'" + checkY + "move       "
						+ counter);
				if (x + nMoveX == checkX && y + nMoveY == checkY) {
					moves8[counter] = true;
					// System.out.println("okay this one was done a while back");
				}
			}
			if ((x + nMoveX >= 0) && (y + nMoveY >= 0) && (x + nMoveX < 11)
					&& (y + nMoveY < 11)) {
				if (!moves8[counter]) {
					// System.out.println("I guess this one hasnt been made");

					RancherGame.pause();
					if(grassEffect){
					gamePauseByGrass(grass);
					}
					huntAction(nMoveX, nMoveY, sheep, positionsInside,grass);
					if (this.sameCell(sheep)) {
						// huntThread.stop();
						sheep.die();
						return;
					}
					x = x - nMoveX;
					y = y - nMoveY;
					RancherGame.pause();
				}
			} else {
				moves8[counter] = true;
			}

		}
		if (tracking) {
			try {
				wolfPositionIveBeen[x][y] = false;
			} catch (ArrayIndexOutOfBoundsException e) {
			}
		}
		// System.out.println("BackTrackTime");
		return;
	}

	private void gamePauseByGrass(Grass[][] grass) {
		for (int U = 0; U <= 5 * grass[x][y].getHeightNumber();U++){
			//System.out.println("pausing");
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

	void hunt(final Sheep sheep, int n, final Grass[][] grass,int g) {
		if (n <= 0) {
			this.tracking = false;
		}
		if (g == 2) {
			this.grassEffect = true;
		}
		class MyThread extends Thread {
			public void run() {
				huntAction(0, 0, sheep, positions, grass);
			}
		}

		MyThread huntThread = new MyThread();
		huntThread.start();
	}

	public boolean getWolfPositionIveBeen(int W, int Q) {
		return wolfPositionIveBeen[W][Q];
	}

}