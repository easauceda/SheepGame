import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class Edge implements GameSettings {
	private Node lead;
	private Node end;
	private double weight;
	protected int ystep = B_HEIGHT / 20;
	protected int xstep = B_WIDTH / 20;
	
	
	public Edge(Node lead, Node end) {
		setLead(lead);
		setEnd(end);
		//calcWeight();

	}

	private void setLead(Node lead) {
		this.lead = lead;

	}

	private void setEnd(Node end) {
		this.end = end;
	}

	private void calcWeight() {
		//used distance formula to calculate weight
		int leadX = lead.getX();
		int leadY = lead.getY();
		int endX = end.getX();
		int endY = end.getY();
		double finalX = Math.pow((leadX - endX),2);
		double finalY = Math.pow((leadY - endY),2);
		double addedVal = (finalX + finalY);
		weight = Math.pow(addedVal, .5);
	}
@Override
	public String toString(){
	System.out.println("Weight: " + weight);
		return "----END----";
	}

	void paint(Graphics pen) {
		
	pen.setColor(Color.black);
	pen.drawLine(X_MARGIN + xstep * end.getX() + 10, Y_MARGIN + ystep
			* end.getY() + 10, X_MARGIN + xstep * lead.getX() + 10,
			Y_MARGIN + ystep * lead.getY() + 10);
}
}
