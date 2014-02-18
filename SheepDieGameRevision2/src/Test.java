import java.awt.Color;
import java.util.Collection;
import java.util.PriorityQueue;
//test
public class Test {

	public static void main(String[] args) {
		PriorityQueue<Sheep> sheeps = new PriorityQueue<Sheep>();

		sheeps.add(new Sheep(10, 10, Color.black));
		sheeps.add(new Sheep(2, 2, Color.black));
		sheeps.add(new Sheep(4, 4, Color.black));
		sheeps.add(new Sheep(7, 7, Color.black));
		sheeps.add(new Sheep(3, 3, Color.black));
		sheeps.add(new Sheep(2, 3, Color.black));
		sheeps.add(new Sheep(5, 5, Color.black));
		sheeps.add(new Sheep(5, 7, Color.black));

		for (Sheep sheep : sheeps) {
			sheep.setDistance(0, 0);
		}

		while (sheeps.size() > 0) {
			System.out.println(sheeps.comparator());
			// sheeps.poll();

			System.out.println(sheeps.peek().getDistance() + ","
					+ sheeps.remove());
		}
	}

}
