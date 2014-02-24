import java.awt.Graphics;

import javax.swing.JPanel;


public class MiniMap extends JPanel implements GameSettings {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3838903850457547123L;

	protected void paintComponent(Graphics pen) {
		pen.drawRect(B_WIDTH, B_HEIGHT, 20, 20);
		
	}
	
}
