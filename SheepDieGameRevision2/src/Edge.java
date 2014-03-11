import java.awt.Color;
import java.awt.Graphics;

public class Edge implements GameSettings {
	private Node lead;
	private Node end;
	private Node node1;
	private Node node2;
	private double weight;
	protected int ystep = B_HEIGHT / 20;
	protected int xstep = B_WIDTH / 20;

	public Edge(Node lead, Node end) {
		setLead(lead);
		setEnd(end);
	//	calcWeight();

	}

	private void setLead(Node lead) {
		this.node1 = lead;

	}

	private void setEnd(Node end) {
		this.node2 = end;
	}

	private void calcWeight() {
		// used distance formula to calculate weight
		int leadX = lead.getX();
		int leadY = lead.getY();
		int endX = end.getX();
		int endY = end.getY();
		// System.out.println("Lead X: " + leadX);
		// System.out.println("Lead Y: " + leadY);
		// System.out.println("end X: " + endX);
		// System.out.println("end Y: " + endY);
		double finalX = Math.pow((leadX - endX), 2);
		double finalY = Math.pow((leadY - endY), 2);
		double addedVal = (finalX + finalY);
		weight = Math.pow(addedVal, .5);

	}

	@Override
	public String toString() {
		System.out.println("Weight: " + weight);
		return "----END----";

	}

	void paint(Graphics pen) {
		pen.setColor(Color.black);
		pen.drawLine(X_MARGIN + xstep * node1.getX() + 10, Y_MARGIN + ystep
				* node1.getY() + 10, X_MARGIN + xstep * node2.getX() + 10,
				Y_MARGIN + ystep * node2.getY() + 10);
	}
}
