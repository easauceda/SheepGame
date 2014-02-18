import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//Idea of this test file is to implement a file reader for the homework
public class inputTest {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new File("input.txt"));
		while(fileReader.hasNext()){
			String data = fileReader.next();
			System.out.println(data);
		}
		fileReader.close();

	}

}
