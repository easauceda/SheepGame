

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

class RangeCanvas extends JPanel implements GameSettings {
	private int rows = 11;
	private int columns = 11;
	private static final long serialVersionUID = 1L;
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private Grass grass[][] = new Grass[rows][columns];
	private boolean grassNeeded = false;
	
	public void addGrassFractal() {
		for (int Q = 0; Q < rows; Q++) {
			for (int W = 0; W < columns; W++) {
				grass[Q][W] = new Grass(Q, W, Color.cyan);
				grass[Q][W].getHealth();
			}
		}
		fractalGrass(3, 0, rows, 0, columns, 8);
	}

	private void fractalGrass(int counter, int xMin, int xMax, int yMin,
			int yMax, int range) {
		Random random = new Random();
		int x = xMin + random.nextInt((xMax - xMin));
		int y = yMin + random.nextInt((yMax - yMin));

		if (counter > 0) {
			for (int t = x - range; t <= x + range + 1; t++) {
				for (int r = y - range; r < y + range + 1; r++) {
					// System.out.println("s"+t);
					try {
						if (t < xMax && t > xMin && r < yMax && r > yMin) {
							grass[t][r].grow();
							grass[t][r].getHealth();
						}
					} catch (ArrayIndexOutOfBoundsException e) {
					}
				}
			}

		} else {
			return;
		}

		if (!((x - range) < xMin)) {
			xMin = (x - (range));
		}
		if (!((x + range) > xMax)) {
			xMax = (x + (range) + 1);
		}

		if (!((y - range) < yMin)) {
			yMin = (y - (range));
		}
		if (!((y + range) > yMax)) {
			yMax = (y + (range) + 1);
		}
		fractalGrass(--counter, xMin, xMax, yMin, yMax, range / 2);
	}

	public void addEntity(Entity e) {
		entities.add(e);
	}
	public ArrayList<Entity> giveMeEntities(){
		return entities;
	}

	protected void paintComponent(Graphics pen) {
		super.paintComponent(pen);

		final int ystep = B_HEIGHT / 20;
		final int xstep = B_WIDTH / 20;

		for (int i = 1; i <= max_Y+1; i++) {
			pen.drawLine(X_MARGIN, i * ystep, xstep * (max_X+1), i * ystep);
		}

		for (int i = 1; i <= max_X+1; i++) {
			pen.drawLine(i * xstep, Y_MARGIN, i * xstep, ystep * (max_Y+1));
		}
		if (grassNeeded) {
			for (int Q = 0; Q < rows; Q++) {
				for (int W = 0; W < columns; W++) {
					grass[Q][W].paint(pen);
				}
			}
		}

		for (Entity e : entities) {
			e.paint(pen);
		}
	}
	public void setGrassNeeded (){
		this.grassNeeded = true;
	}
	public Grass[][] getGrassArray(){
		return grass;
	}

}
