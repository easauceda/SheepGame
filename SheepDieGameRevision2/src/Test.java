import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.PriorityQueue;

public class Test {

	public static void main(String[] args) {
		PriorityQueue<Target> targets = new PriorityQueue<Target>();

		PriorityQueue<Target> targets2 = new PriorityQueue<Target>();
		
		ArrayList<Sheep> sheepArray = new ArrayList<Sheep>();
		Wolf wolf = new Wolf(10,10,Color.black);
		Wolf wolf2 = new Wolf(0,0,Color.black);
		sheepArray.add(new Sheep(10, 10, Color.black));
		sheepArray.add(new Sheep(2, 2, Color.black));
		sheepArray.add(new Sheep(4, 4, Color.black));
		sheepArray.add(new Sheep(7, 7, Color.black));
		sheepArray.add(new Sheep(3, 3, Color.black));
		sheepArray.add(new Sheep(2, 3, Color.black));
		sheepArray.add(new Sheep(5, 5, Color.black));
		sheepArray.add(new Sheep(5, 7, Color.black));
		
		for (Sheep sheep: sheepArray){
			targets.add(new Target(sheep,wolf));
		}
		for (Sheep sheep: sheepArray){
			targets2.add(new Target(sheep,wolf2));
		}
		
		while(targets.size()>0){
			if(targets2.peek().isTargetStillAlive()){
				targets2.peek().targetKill();
			System.out.println("("+targets2.peek().targetX()+","+targets2.peek().targetY()+") "+targets2.poll().distance);
		}else {targets2.remove();}
			if (targets.peek().isTargetStillAlive()){
				targets.peek().targetKill();
			System.out.println("("+targets.peek().targetX()+","+targets.peek().targetY()+") "+targets.poll().distance);
		}else {targets.remove();}
		}
	}	
	}