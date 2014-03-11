import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;


public class Node extends Entity{
	private String name;
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
	}

	public void myNodeName(String n){
		this.name = "N" + n;
	}
	public String getName(){
		return name;
	}
	public void addEdge(Edge edge){
		edges.add(edge);
	}
}
