
//this is for the wolf to be able to track the sheep and not be running into each other

//since they are running in completely different threads they might mess things up inside the sheep

//this makes it so that the wolf just created a priority queue that can store its own data and 

//allow him to manipulate it on its own

public class Target implements Comparable{
	Sheep sheep;
	Wolf wolf;
	int distance;
	public Target(Sheep sheep, Wolf wolf) {
		this.sheep = sheep;
		this.wolf = wolf;
		setDistance();
	}

	public void setDistance() {
		this.distance = Math.abs(sheep.getY()-wolf.getY())+Math.abs(sheep.getX()-wolf.getX());
		
	}
	@Override
	public int compareTo(Object arg0) {
		Target target1 = (Target) arg0;
		return this.getDistance() - target1.getDistance();
	}

	private int getDistance() {
		return this.distance;
	}
	public Sheep getSheep(){
		return this.sheep;
	}
	public int targetX(){
		return sheep.getX();
	}
	public int targetY(){
		return sheep.getY();
	}
	public boolean isTargetStillAlive(){
		return sheep.isAlive();
	}
	public void targetKill(){
		this.sheep.die();
	}
}
