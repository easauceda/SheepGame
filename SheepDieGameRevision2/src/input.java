import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

//Idea of this test file is to implement a file reader for the homework
/*
 * what the file does right now is it reads the text file word by word and checks 
 * for keywords such as wolf or sheep in the compareForNextStep method.
 */
public class input {
	private static int sheepMax = 10;
	private static int wolfMax = 4;
	private static int gameSpeed = 500;
	private static int count = 0;
	private static int xBoardSize = 20;
	private static int yBoardSize = 20;
	private static ArrayList<String> wolfPositions = new ArrayList<String>();
	private static ArrayList<String> nodes = new ArrayList<String>();
	private static ArrayList<String> edges = new ArrayList<String>();

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
				String x = fileReader.next();
				if (x.equals("count")) {

					fileReader.next();
					wolfMax = Integer.parseInt(fileReader.next());
				} else {
					String y = fileReader.next();
					wolfPositions.add(x + "," + y);
				}

			}
			if (answer == 4) {
				String heightOrWidth = fileReader.next();
				if (heightOrWidth.equalsIgnoreCase("height")) {
					fileReader.next();
					yBoardSize = Integer.parseInt(fileReader.next());
				}
				if (heightOrWidth.equalsIgnoreCase("width")) {
					fileReader.next();
					xBoardSize = Integer.parseInt(fileReader.next());
				}

			}
			if(answer == 0){
				if(data.contains(".txt")){
					letsFuckenCreateNodes(data);
				}
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
		}
		if (data.equalsIgnoreCase("board")) {
			return 4;
		}else {
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
		if (count == 0) {
			return wolfMax;

		}
		return getPositions().size();
	}

	public static int getSpeed() {
		return gameSpeed;
	}

	public static int getXBoardSize() {
		return xBoardSize;
	}

	public static int getYBoardSize() {
		return yBoardSize;
	}

	public static ArrayList<String> getPositions() {
		return wolfPositions;
		
	}
	
	public void letsFuckenCreateNodes(String nodeFile) throws FileNotFoundException{
		Scanner fileReader = new Scanner(new File(nodeFile));
		while (fileReader.hasNext()) {
			String data = fileReader.next();
			if(data.equalsIgnoreCase("n")){
				nodes.add(fileReader.next()+","+fileReader.next()+","+fileReader.next());
			}else{
				if(data.equalsIgnoreCase("e")){
					edges.add(fileReader.next()+","+fileReader.next());
				}
			}
			
		}
		fileReader.close();
	}

	public static ArrayList<String> getNodes() {
		return nodes;
	}
	public static ArrayList<String> getEdges(){
		return edges;
	}
}
