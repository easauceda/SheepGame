public class Edge {
	private Node lead;
	private Node end;
	private double weight;

	public Edge(Node lead, Node end) {
		setLead(lead);
		setEnd(end);
		calcWeight();

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
//		System.out.println("Lead X: " + leadX);
//		System.out.println("Lead Y: " + leadY);
//		System.out.println("end X: " + endX);
//		System.out.println("end Y: " + endY);
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
}
