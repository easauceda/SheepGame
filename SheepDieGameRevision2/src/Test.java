import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.PriorityQueue;

public class Test {

	public static void main(String[] args) {
		PriorityQueue<Sheep> sheeps = new PriorityQueue<Sheep>();
		ArrayList<Sheep> sheepArray = new ArrayList<Sheep>();

		sheepArray.add(new Sheep(10, 10, Color.black));
		sheepArray.add(new Sheep(2, 2, Color.black));
		sheepArray.add(new Sheep(4, 4, Color.black));
		sheepArray.add(new Sheep(7, 7, Color.black));
		sheepArray.add(new Sheep(3, 3, Color.black));
		sheepArray.add(new Sheep(2, 3, Color.black));
		sheepArray.add(new Sheep(5, 5, Color.black));
		sheepArray.add(new Sheep(5, 7, Color.black));
		
		for (Sheep sheep : sheepArray) {
			sheep.setDistance(0, 0);
			sheeps.add(sheep);
		}

		while (sheeps.size() > 4) {
			System.out.println(sheeps.peek());
			// sheeps.poll();

			System.out.println(sheeps.peek().getDistance() + ","
					+ sheeps.remove());
			
		}//
		//this changes everything the wolf is now at (10, 10);
		/*sheepArray.clear();
		sheepArray.addAll(sheeps);
		sheeps.clear();
		for (Sheep sheep: sheepArray){
			sheep.setDistance(10, 10);
			sheeps.add(sheep);
		}//*/
		for (Sheep sheep: sheeps){
			sheep.setDistance(10, 10);
		}
		System.out.println("This is a new Position for wolf he moved to 10 10 now");
		//look on top
		//sheeps.add(sheeps.poll());
		
		while (sheeps.size() > 0) {
			System.out.println(sheeps.peek());
			 sheeps.poll();

			System.out.println(sheeps.peek().getDistance() + ","
					+ sheeps.remove());
			
		}
	}

}
