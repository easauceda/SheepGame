import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;


public class Node extends Entity{
	ArrayList<Edge> edges = new ArrayList<Edge>();
	
	
	public Node(int x, int y, Color c){
		super(x,y,c);
	}
	
	public void setEdge(Edge e) {
		edges.add(e);
	}
	
	
	
	
	
	void paint(Graphics pen) {
		pen.setColor(c);
		pen.drawString("N", x, y);
		
		for (Edge i: edges) {
			i.paint(pen);
		}
	}
}
