/**
 * An extenion of a Point to allow for coloration
 * 
 * authors Jacob Wellinghoff (jgw7654), Ian Dempsey (ijd8975)
 * 
 * 
 */

import java.awt.Color;
import java.awt.Point;


public class ColoredPoint extends Point {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7731048684391096602L;
	public Color color = Color.GREEN;
	public ColoredPoint(int x, int y, Color coloring) {
		super(x,y);
		color = coloring;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color newColor) {
		color = newColor;
	}
	
	public String toString() {
		return "Point[x=" + this.getX() + ",y=" + this.getY() + ",color=" + this.getColor() + "]";
	}

}
