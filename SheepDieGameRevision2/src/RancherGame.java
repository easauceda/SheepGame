
//test

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class RancherGame extends JFrame implements GameSettings {
	private boolean wolfIsPushed= false;
	private RangeCanvas rangeCanvas = new RangeCanvas();;
	private JTextField SheepX = new JTextField(2);
	private JTextField SheepY = new JTextField(2);
	private JTextField WolfX = new JTextField(2);
	private JTextField WolfY = new JTextField(2);
	private JButton huntButton = new JButton("hunt");
	
	private Sheep sheep = new Sheep(INIT_SHEEP_X, INIT_SHEEP_Y, SHEEP_COLOR);
	private Wolf wolf = new Wolf(INIT_WOLF_X, INIT_WOLF_Y, WOLF_COLOR);;
	
	private final Timer timer;
	
	private void finish() {
		timer.stop();
	}
	
	private void updatePositions() {
		sheep.setX(Integer.parseInt(SheepX.getText().trim()));
		sheep.setY(Integer.parseInt(SheepY.getText().trim()));

		wolf.setX(Integer.parseInt(WolfX.getText().trim()));
		wolf.setY(Integer.parseInt(WolfY.getText().trim()));
		
		rangeCanvas.repaint();
	}
	
	public RancherGame() {

		// layout etc
		JPanel panel = new JPanel();
		add(rangeCanvas);
		panel.add(new JLabel("Wolf (x,y) = "));
		panel.add(WolfX);
		panel.add(WolfY);
		panel.add(new JLabel(" "));
		panel.add(huntButton);
		panel.add(new JLabel(" "));
		panel.add(new JLabel("Sheep (x,y) = "));
		panel.add(SheepX);
		panel.add(SheepY);
		add(panel, BorderLayout.SOUTH);
		rangeCanvas.addEntity(wolf);
		rangeCanvas.addEntity(sheep);
		rangeCanvas.addGrassFractal();
		
		timer = new Timer(BOARD_REFRESH_RATE, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println(wolf + " " + sheep);


				rangeCanvas.repaint();
				if (sheep.isAlive() == false) {
					finish();
				}
			}
		});
		
		// actions below
		huntButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!wolfIsPushed){
				Object[] options = {"No, thanks","Yes, please"
	                    };
	int n = JOptionPane.showOptionDialog(null,
	    "Would you like to see what squares the wolf has visited? ",
	    "Track the wolfs steps",
	    JOptionPane.YES_NO_CANCEL_OPTION,
	    JOptionPane.QUESTION_MESSAGE,
	    null,
	    options,
	    options[1]);
	Object[] options2 = {"No, Grass","Yes, On the Grass"
    ,"Grass makes wolf slower"};
	int g = JOptionPane.showOptionDialog(null,
		    "Would you like to see grass effect the wolf or just see grass? ",
		    "Do you want grass with that?",
		    JOptionPane.YES_NO_CANCEL_OPTION,
		    JOptionPane.QUESTION_MESSAGE,
		    null,
		    options2,
		    options2[2]);
	if (g>0){
		rangeCanvas.setGrassNeeded();
	}
				timer.start();
				//System.out.println(n+" this is your answer should be zero");
				sheep.squirm();
				wolf.hunt(sheep,n,rangeCanvas.getGrassArray(),g);
				wolfIsPushed=true;
			}}
		});
		
		SheepX.setText(""+ sheep.getX());
		SheepX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updatePositions();
			}
		});

		SheepY.setText("" + sheep.getY());
		SheepY.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updatePositions();
			}
		});
		
		WolfX.setText("" + wolf.getX());
		WolfX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updatePositions();
			}
		});
		
		WolfY.setText("" + wolf.getY());
		WolfY.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updatePositions();
			}
		});
		
	}
	
	public static void pause() {
		try {
			Thread.sleep(GAME_SPEED);
		} catch (Exception e) {
			// uh oh!
		}
	}
	
	public static void main(String args[]) {
		RancherGame c = new RancherGame();
		c.setLayout(new GridLayout(2, 1));
		c.setSize(B_WIDTH + 120, B_HEIGHT + 120);
		c.setVisible(true);
		c.setLocationRelativeTo(null);
		c.setLocationRelativeTo(null);
		c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
