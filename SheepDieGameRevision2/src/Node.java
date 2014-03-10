import java.awt.Color;
import java.awt.Graphics;


public class Node extends Entity{
	private int x;
	private int y;
	public Node(int x, int y, Color c){
		super(x,y,c);
	}
	public int getX(){
		return x;
		
	}
	public int getY(){
		return y;
		
	}
	@Override
	void paint(Graphics pen) {
		// TODO Auto-generated method stub
		
	}

}
