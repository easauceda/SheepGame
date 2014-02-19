
//this is for the wolf to be able to track the sheep and not be running into each other

//since they are running in completely different threads they might mess things up inside the sheep

//this makes it so that the wolf just created a priority queue that can store its own data and 

//allow him to manipulate it on its own

public class Target implements Comparable<Target>{
	Sheep sheep;
	Wolf wolf;
	double distance;
	public Target(Sheep sheep, Wolf wolf) {
		this.sheep = sheep;
		this.wolf = wolf;
		setDistance();
	}

	public void setDistance() {
		this.distance = Math.pow(sheep.getY()-wolf.getY(),2)+Math.pow(sheep.getX()-wolf.getX(),2);
		
	}

	public double getDistance() {
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

	@Override
	public int compareTo(Target arg0) {
		if(this.getDistance()<arg0.getDistance()){
			return -1;
		}else {if(this.getDistance()>arg0.getDistance()){
			return 1;
			}
		}
		return 0;
	}
}
