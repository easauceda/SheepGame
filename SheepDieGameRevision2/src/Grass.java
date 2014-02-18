

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Grass extends Entity {
	private int health = 0;
	public Grass(int x, int y, Color c) {
		super(x+1, y+1, c);
	}

	@Override
	void paint(Graphics pen) {
		// System.out.println("wolf [x=" + x + " y=" + y + " color=" + c + "]");
		pen.setColor(c);
		pen.fillRect(xstep * x , ystep * y , 19, 19);
	}

	void hit(int mouse_x, int mouse_y) {
		if (areYouHit(mouse_x, mouse_y))
			System.out.println("I am a wolf");
	}
	
	boolean areYouHit(int mouse_x, int mouse_y) {
				return ((xstep * x + xstep) - mouse_x >= 0)
						&& ((y * ystep +ystep) - mouse_y >= 0)
						&& ((xstep * x +xstep) - mouse_x < xstep) 
						&& ((y * ystep+ ystep) - mouse_y < ystep);
	}

	public Color getHealth(){
		if (health == 3){
			this.c = new Color(0,120,0);
		}
		if (health == 2){
			this.c = new Color(0,200,0);
		}
		if (health == 1){
			this.c = new Color (0,250,0);
		}
		if (health == 0){
			this.c = new Color (153,76,0);
		}
		return c;
		
	}
	public void eaten(){
		this.health= health-1;
		if(health<0){
			this.health = 0;
		}
		
	}
	public void grow(){
		this.health++;
		if (health>=3){
			this.health =3;
		}
	}
	public int randomHeight(){
		Random random = new Random();
		this.health = 0 + random.nextInt(4);
		return health;
	}
	public int getHeightNumber(){
		return health;
	}
}