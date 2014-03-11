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
		pen.setColor(Color.BLUE);
		pen.drawRect(X_MARGIN + xstep * x + 2, Y_MARGIN + ystep * y + 2,
				16, 16);
		for (Edge i: edges) {
			i.paint(pen);
		}
	}

	public ArrayList<Edge> getEdges() {
		
		return edges;
	}
}
