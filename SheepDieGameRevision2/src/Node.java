import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;


public class Node extends Entity{
	private String name;
	ArrayList<Edge> edges = new ArrayList<Edge>();
	
	
	public Node(int x, int y, Color c){
		super(x,y,c);
	}
	
	
	
	
	void paint(Graphics pen) {
		pen.setColor(c);
		pen.drawString("N",X_MARGIN + xstep * x + 7, Y_MARGIN + ystep * y + 14);
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
