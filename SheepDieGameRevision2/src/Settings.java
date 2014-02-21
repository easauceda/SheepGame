public class Settings {
	private int sheepNum;
	private int wolfNum;
	private int gameSpeed;

	public Settings() {
		gameSpeed = 500;
		sheepNum = 20;
		wolfNum = 5;
	}

	public void setSheep(int value) {
		sheepNum = value;
	}

	public void setWolves(int value) {
		wolfNum = value;
	}

	public void setGameSpeed(int value) {
		gameSpeed = value;
	}

	public int getSheep() {
		return sheepNum;

	}

	public int getWolves() {
		return wolfNum;

	}

	public int getSpeed() {
		return gameSpeed;

	}

}
