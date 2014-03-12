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

	}

	private void setLead(Node lead) {
		this.lead = lead;

	}

	private void setEnd(Node end) {
		this.end = end;
	}

	public Node getLead() {
		return lead;

	}

	public Node getEnd() {
		return end;

	}

	@Override
	public String toString() {
		System.out.println("Weight: " + weight);
		return "----END----";
	}

	void paint(Graphics pen) {

		pen.setColor(Color.black);
		pen.drawLine(X_MARGIN + xstep * end.getX() + 10,
				Y_MARGIN + ystep * end.getY() + 10,
				X_MARGIN + xstep * lead.getX() + 10,
				Y_MARGIN + ystep * lead.getY() + 10);
	
		pen.drawString(Double.toString(weight) , (X_MARGIN + xstep * end.getX() + 10 +
				X_MARGIN + xstep * lead.getX() + 10)/2,
				(Y_MARGIN + ystep * lead.getY() + 10 + Y_MARGIN + ystep * end.getY() + 10)/2);
	
	}
	public void setWeight(double weight){
		this.weight = weight;
	}
	public double getWeight(){
		return weight;
	}
}
