import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
//
public class Sheep extends Entity {
	private ArrayList<Wolf> wolfs;

	public Sheep(int x, int y, Color c) {
		super(x, y, c);
	}

	public void die() {
		alive = false;
		c = Color.red;
	}

	public boolean isAlive() {
		return alive;
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

	private void moveAction() {
		if (alive) {
			Random random = new Random();
			int counter = random.nextInt(8);
			int nMoveX, nMoveY;
			nMoveX = choseX(counter);
			nMoveY = choseY(counter);
			if ((x + nMoveX >= 0) && (y + nMoveY >= 0) && (x + nMoveX < max_X)
					&& (y + nMoveY < max_Y)) {
				this.x = x + nMoveX;
				this.y = y + nMoveY;
			}
			RancherGame.pause();
			RancherGame.pause();
			moveAction();
			amIDead();

		}
	}

	private void amIDead() {
		for (Wolf wolf : wolfs) {
			if (this.sameCell(wolf)) {
				this.die();
			}
		}
	}

	void squirm() {

		class SheepThread extends Thread {
			public void run() {
				moveAction();
			}

		}

		SheepThread squirmThread = new SheepThread();
		squirmThread.start();
	}

	void paint(Graphics pen) {
		pen.setColor(c);
		pen.fillRect(X_MARGIN + xstep * x + 2, Y_MARGIN + ystep * y + 2, 16, 16);
		pen.setColor(Color.BLACK);
		pen.drawRect(X_MARGIN + xstep * x + 2, Y_MARGIN + ystep * y + 2, 16, 16);
	}

	public void setWolfs(ArrayList<Wolf> wolfs) {
		this.wolfs = wolfs;

	}

}
