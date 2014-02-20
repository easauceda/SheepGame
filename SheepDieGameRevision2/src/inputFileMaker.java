import javax.swing.JFrame;
import javax.swing.JLabel;

//this is where we ask the player to enter the game settings, in order to package them up and send to the reader
public class inputFileMaker extends JFrame {


	private static final long serialVersionUID = 1L;
	private String clock;
	private String wolf;
	private String sheep;

	public static void main(String Args[]) {
		JFrame test = new JFrame();
		JLabel tester = new JLabel("Hello World");
		test.add(tester);
		test.pack();
		test.setVisible(true);
		test.setDefaultCloseOperation(EXIT_ON_CLOSE);
		System.out.println("I Like pie");
	}
}
