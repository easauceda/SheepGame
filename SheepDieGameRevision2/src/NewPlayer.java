import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class NewPlayer {
	public NewPlayer() {
		JFrame mainScreen = new JFrame();
		JLabel enterName = new JLabel("Please enter the name of your sheep");
		mainScreen.add(enterName, BorderLayout.NORTH);
		JTextField name = new JTextField();
		name.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				

			}
		});
		mainScreen.add(name, BorderLayout.SOUTH);
		mainScreen.setVisible(true);
		mainScreen.pack();
	}
	
}
