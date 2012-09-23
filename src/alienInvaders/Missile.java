package alienInvaders;

import javax.swing.ImageIcon;

/**
 * Represents a missile shot from the ships
 * @author Teodor Merodiyski
 * @version 1.0
 * 
 */
public class Missile extends Object2D 
{
	private String missile = "pics/missile.png";
	private final int hSpace =6;
	private final int vSpace =1;
	
	/**
	 * empty constructor 
	 */
	public Missile(){}
	/**
	 * 
	 * @param x the x coordinate of the missile
	 * @param y the y coordinate of the missile
	 */
	public Missile(int x, int y)
	{
		ImageIcon im = new ImageIcon(this.getClass().getResource(missile));
		setImage(im.getImage());
		setX(x+hSpace);
		setY(y+vSpace);
	}
}
