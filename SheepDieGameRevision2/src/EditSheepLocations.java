import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EditSheepLocations extends JFrame {

	/**

	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EditSheepLocations(ArrayList<Sheep> sheepList,
			ArrayList<Wolf> wolfList) {
		JFrame locations = new JFrame();
		locations.setSize(300, 400);
		locations.setDefaultCloseOperation(EXIT_ON_CLOSE);
		JPanel title = new JPanel();
		JPanel body = new JPanel();
		GridLayout bodyLayout = new GridLayout(0,10); 
		GridLayout titleLayout = new GridLayout(0, 2);
		title.setLayout(titleLayout);
		body.setLayout(bodyLayout);
		JLabel sheep = new JLabel("Sheep Locations");
		JLabel wolves = new JLabel("Wolf locations");
		title.add(sheep);
		title.add(wolves);
		locations.add(title, BorderLayout.NORTH);
		locations.setVisible(true);
		for (int a = 0; a < sheepList.size(); a++) {
			JLabel test = new JLabel("THIS IS A TEST LABEL");
			body.add(test);
		}

	}

}
