import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class Sheep extends Entity {
	private ArrayList<Wolf> wolfs;
	private int deathCount = deathCountValue;
	private String myOwner = null;
	private boolean sheepStupid = true;
	private ArrayList<Node> nodes = new ArrayList<Node>();
	private ArrayList<Edge> edges;
	private Node target = null;
	private boolean newTargetFound = false;
	private Edge EdgeChoice;

	public Sheep(int x, int y, Color c, Node target) {
		super(x, y, c);
		this.target = target;
	}

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

	private void moveMeThere(int move) {
		this.x = x + choseX(move);
		this.y = y + choseY(move);
		RancherGame.pause();
	}

	private void moveAction() {
		Random ran = new Random();
		int q = 0;
		while (true) {
			if (alive) {
				if (sheepStupid) {
					q = ran.nextInt(9);
					if (x + choseX(q) > 0 && x + choseX(q) < max_X
							&& y + choseY(q) > 0 && y + choseY(q) < max_Y) {
						moveMeThere(q);
					}
				} else {
				}
				try {

					moveMeThere(runTagDecisionMaker(runAwayFromWho(wolfs)));

				} catch (NullPointerException e) {
				}

			}
		}
	}

	private double getDistance(int x, int y) {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}

	private int runTagDecisionMaker(Wolf wolf) {
		int j = 8;
		int k[] = new int[j];
		Random ran = new Random();
		if (getDistance(x - wolf.getX(), y - wolf.getY()) < 8) {
			for (int q = 7; q >= 0; q--) {
				if (getDistance(x + choseX(j) - wolf.getX(), y + choseY(j)
						- wolf.getY()) < getDistance(
							x + choseX(q) - wolf.getX(),
							y + choseY(q) - wolf.getY())
						&& x + choseX(q) > 0
						&& x + choseX(q) < max_X
						&& y + choseY(q) > 0 && y + choseY(q) < max_Y) {

					j = q;
				}
			}
			// this is to try and have the wolf be able to move in multiple
			// movements besides just the one first or last
			k[0] = j;
			int d = 1;
			for (int q = 7; q >= 0; q--) {
				if (getDistance(x + choseX(j) - wolf.getX(), y + choseY(j)
						- wolf.getY()) == getDistance(
						x + choseX(q) - wolf.getX(),
						y + choseY(q) - wolf.getY())
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
		return j;
	}

	private double distanceRun(Wolf wolf) {
		return Math.sqrt(Math.pow(this.getY() - wolf.getY(), 2)
				+ Math.pow(this.getX() - wolf.getX(), 2));
	}

	private Wolf runAwayFromWho(ArrayList<Wolf> wolfs) {
		Wolf wolfIt = null;
		double closest = 1000000000;
		for (Wolf wolf : wolfs) {
			if (wolf.getTarget().equals(this)) {
				closest = distanceRun(wolf);
				return wolf;

			} else {
				if (distanceRun(wolf) < closest) {

					closest = distanceRun(wolf);
					wolfIt = wolf;
				}
			}
		}

		return wolfIt;
	}

	void squirm() {

		class SheepThread extends Thread {
			public void run() {
				if (myOwner == null) {
					while (alive == true) {
						if (newTargetFound == false) {
							findClosestNode();
						}
						hasAnyWolfKilledMe();
						if (alive) {
							moveToNode();
							if (ranch.typeOfGame("sheep")) {
								broadcast(" sheep " + name + " " + x + " " + y);
							}
						} else {
						}
						RancherGame.pause();
					}

				}

			}

		}

		SheepThread squirmThread = new SheepThread();
		squirmThread.start();
	}

	private void hasAnyWolfKilledMe() {
		for (Wolf wolf : wolfs) {
			if (wolf.sameCell(this)) {
				this.alive = false;
				if (ranch.typeOfGame("sheep")) {
					// [NickName] wolf [WolfId] killed sheep [SheepId] location
					// [X] [Y]
					broadcast(" wolf " + wolf + " killed " + "sheep " + name
							+ " location " + x + " " + y);
				}
				return;
			}
		}

	}

	void paint(Graphics pen) {
		if (deadCounter() > 0) {
			pen.setColor(c);
			pen.fillRect(X_MARGIN + xstep * x + 2, Y_MARGIN + ystep * y + 2,
					16, 16);
			pen.setColor(Color.BLACK);
			pen.drawRect(X_MARGIN + xstep * x + 2, Y_MARGIN + ystep * y + 2,
					16, 16);
		}
	}

	public void setWolfs(ArrayList<Wolf> wolfs) {
		this.wolfs = wolfs;

	}

	public int deadCounter() {
		if (!isAlive()) {
			this.deathCount--;

		}
		return deathCount;
	}

	public void reviveMe() {
		this.alive = true;
		this.deathCount = 50;
		if (debugMode) {
			this.c = Color.cyan;
		} else {
			this.c = Color.white;
		}
		squirm();
	}

	public String getMyOwner() {
		return myOwner;
	}

	public void setMyOwner(String myOwner) {
		this.myOwner = myOwner;
	}

	public void getNodes(ArrayList<Node> n) {

		edges = target.getEdges();
		EdgeChoice = edges
				.get((int) (Math.random() * ((edges.size() - 1) + 1)));
		target = EdgeChoice.getEnd();
		if (target.getX() == x && target.getY() == y) {
			target = EdgeChoice.getLead();
		}

	}

	public void findClosestNode() {

		target = nodes.get((int) (Math.random() * ((nodes.size() - 1) + 1)));
		int nodeX = target.getX();
		int nodeY = target.getY();
		if (nodeX == x && nodeY == y) {
			getNodes(nodes);
		} else {
			newTargetFound = true;
		}
	}

	public void wolfsLink(ArrayList<Wolf> wolfs) {
		this.wolfs = wolfs;
	}

	public void moveToNode() {
		int nodeX = target.getX();
		int nodeY = target.getY();
		if (nodeX == x && nodeY == y) {
			getNodes(nodes);
			//findClosestNode();
		}
		if (nodeX < x) {
			x--;
		}
		if (nodeX > x) {
			x++;
		}
		if (nodeY > y) {
			y++;
		}
		if (nodeY < y) {
			y--;
		}

	}

	public void moveFromNode() {
		int nodeX = target.getX();
		int nodeY = target.getY();
		target = EdgeChoice.getEnd();
		if (nodeX == x && nodeY == y) {
			moveToNode();
		}
		if (nodeX < x) {
			x--;
		}
		if (nodeX > x) {
			x++;
		}
		if (nodeY > y) {
			y++;
		}
		if (nodeY < y) {
			y--;
		}

	}

	public void setWolfOne(Wolf wolf) {
		// TODO Auto-generated method stub

	}

	boolean areYouHit(int mouse_x, int mouse_y) {
		System.out.println(mouse_x);
		System.out.println(x);
		return ((xstep * x + xstep) - mouse_x >= 0)
				&& ((y * ystep + ystep) - mouse_y >= 0)
				&& ((xstep * x + xstep) - mouse_x < xstep)
				&& ((y * ystep + ystep) - mouse_y < ystep);
	}
}
