package alienInvaders;

import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

/**
 * Represents the cannon
 * @author Teodor Merodiyski
 * @version 1.0
 *
 */
public class Human extends Object2D implements GameConstants
{
	private final int StartX=220;
	private final int StartY=300;
	private final String player="pics/player.png";
	private int width;
	
	/**
	 * Human constructor
	 */
	public Human()
	{
		ImageIcon im = new ImageIcon(this.getClass().getResource(player));
		width=im.getImage().getWidth(null);
		setImage(im.getImage());
		setX(StartX);
		setY(StartY);
	}
	/**
	 * move the cannon
	 */
	public void act()
	{
		x+=dx;
		if(x<=2)
			x=2;
		if(x>=BOARD_WIDTH-2*width)
			x=BOARD_WIDTH-2*width;
	}
	/**
	 * 
	 * @param e KeyEvent
	 */
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		if(key==KeyEvent.VK_LEFT) dx=-2;
		if(key==KeyEvent.VK_RIGHT) dx=2;
	}
	/**
	 * 
	 * @param e KeyEvent
	 */
	public void keyReleased(KeyEvent e)
	{
		int key= e.getKeyCode();
		if(key==KeyEvent.VK_LEFT) dx=0;
		if(key==KeyEvent.VK_RIGHT) dx=0;
	}
}
