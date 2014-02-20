//

import java.awt.Color;
import java.awt.Graphics;

public abstract class Entity implements GameSettings {
	protected int x;
	protected int y;
	protected Color c;
	boolean alive = true;

	protected int ystep = B_HEIGHT/20;
	protected int xstep = B_WIDTH/20;

	public Entity(int x, int y, Color c) {
		this.x = x;
		this.y = y;
		this.c = c;
	}

	public boolean sameCell(Entity other) {
		return (this.x == other.x && this.y == other.y);
	}
	
	public String toString() {
		return "@(" + x + "," + y + ")";
	}
	
	protected void setX(int x) {
		this.x = x;
	}
	
	protected void setY(int y) {
		this.y = y;
	}

	protected void setColor(Color c) {
		this.c = c;
	}
	
	protected int getX() {
		return x;
	}
	
	protected int getY() {
		return y;
	}
	
	protected Color getColor() {
		return c;
	}
	
	abstract void paint(Graphics pen); 
}
