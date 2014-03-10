import java.awt.Color;
import java.awt.Graphics;


public class Node extends Entity{
	public Node(int x, int y, Color c){
		super(x,y,c);
	}
	
	void paint(Graphics pen) {
		pen.setColor(c);
		pen.drawString("N", x, y);
	}

}
