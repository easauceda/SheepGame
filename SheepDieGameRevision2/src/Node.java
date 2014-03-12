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
		pen.setColor(new Color(100,25,0));
		pen.fillRect(X_MARGIN + xstep * x + 2, Y_MARGIN + ystep * y + 2,
				16, 16);
		pen.setColor(Color.black);
		pen.drawRect(X_MARGIN + xstep * x + 2, Y_MARGIN + ystep * y + 2,
				16, 16);
		pen.setColor(Color.white);
		pen.drawString("N", X_MARGIN + xstep * x + 6, Y_MARGIN + ystep * y + 15);
	}

	public void myNodeName(String n){
		this.name = n;
	}
	public String getName(){
		return name;
	}
	public void addEdge(Edge edge){
		edges.add(edge);
	}
	public ArrayList<Edge> getEdges(){
		return edges;
		
	}
}
