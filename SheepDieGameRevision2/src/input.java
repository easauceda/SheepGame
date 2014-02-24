import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//Idea of this test file is to implement a file reader for the homework
/*
 * what the file does right now is it reads the text file word by word and checks 
 * for keywords such as wolf or sheep in the compareForNextStep method.
 */
public class input {
	private static int sheepMax;
	private static int wolfMax;
	private static int gameSpeed;

	public input() throws FileNotFoundException {
		Scanner fileReader = new Scanner(new File("input.txt"));
		while (fileReader.hasNext()) {
			String data = fileReader.next();
			int answer = compareForNextStep(data);
			if (answer == 1) {
				// skipped through one text line by hand but it feels like
				// cheating a bit
				fileReader.next();
				gameSpeed = Integer.parseInt(fileReader.next());
				// set time
			}
			if (answer == 2) {

				fileReader.next();
				fileReader.next();
				sheepMax = Integer.parseInt(fileReader.next());
			}
			if (answer == 3) {

				fileReader.next();
				fileReader.next();
				wolfMax = Integer.parseInt(fileReader.next());
			}

		}
		fileReader.close();
	}

	public static int compareForNextStep(String data) {
		// if there's a hit on a keyword, the int being returned will change
		if (data.equalsIgnoreCase("clock")) {
			return 1;
		}
		if (data.equalsIgnoreCase("sheep")) {
			return 2;
		}
		if (data.equalsIgnoreCase("wolf")) {
			return 3;
		} else {
			return 0;
		}
	}

	/*
	 * the idea is to pull that number and use it to tell the program what to do
	 * next, create a wolf create sheep, or change the time clock.
	 */

	public static int getNumberOfSheep() {
		// TODO Auto-generated method stub
		return sheepMax;
	}

	public static int getNumberOfWolfs() {
		// TODO Auto-generated method stub
		return wolfMax;
	}

	public static int getSpeed() {
		// TODO Auto-generated method stub
		return gameSpeed;
	}
}
