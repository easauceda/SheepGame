import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class Edge {
	private Node lead;
	private Node end;
	public Node getLead() {
		return lead;
	}

	public Node getEnd() {
		return end;
	}

	private double weight;
	
	public Edge(Node lead, Node end) {
		setLead(lead);
		setEnd(end);
		calcWeight();

	}

	void setLead(Node lead) {
		this.lead = lead;

	}

	void setEnd(Node end) {
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

	public void paint(Graphics pen) {
		pen.setColor(Color.GRAY);
		Graphics2D pen2 = (Graphics2D) pen;
        pen2.setStroke(new BasicStroke(2));
        pen2.draw(new Line2D.Float(lead.getX() + 5, lead.getY() - 5 , end.getX() + 5, end.getY() - 5 ));
		
	}
}
