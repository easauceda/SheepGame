import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//Idea of this test file is to implement a file reader for the homework
/*
 * what the file does right now is it reads the text file word by word and checks 
 * for keywords such as wolf or sheep in the compareForNextStep method.
 */
public class inputTest {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new File("input.txt"));
		while (fileReader.hasNext()) {
			String data = fileReader.next();
			//send data to check for keyword
			int answer = compareForNextStep(data);
			//Right now all it does is print out the answer we get from compareForNextStep
			System.out.println(answer);
		}

		fileReader.close();

	}

	public static int compareForNextStep(String data) {
		//if there's a hit on a keyword, the int being returned will change
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
 * the idea is to pull that number and use it to tell the program what to do next, create a wolf
 * create sheep, or change the time clock.
 */
}
