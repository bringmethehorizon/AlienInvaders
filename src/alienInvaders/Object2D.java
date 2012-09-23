package alienInvaders;

import java.awt.Image;

/**
 * Superclass which contains the common features of the 2D objects
 * @author Teodor Merodiyski
 * @version 1.0
 *
 */
public class Object2D 
{
	private boolean visible;
	private Image image;
	protected int x;
	protected int y;
	protected boolean dying;
	protected int dx;
	
	/**
	 * Object2D constructor
	 */
	public Object2D()
	{
		visible=true;
	}
	/**
	 * the object dies
	 */
	public void die()
	{
		visible=false;
	}
	/**
	 * 
	 * @return true if the object is visible
	 */
	public boolean isVisible()
	{
		return visible;
	}
	/**
	 * 
	 * @param visible sets object visibility
	 */
	protected void setVisible(boolean visible)
	{
		this.visible=visible;
	}
	/**
	 * 
	 * @param image sets the image of the object
	 */
	public void setImage(Image image)
	{
		this.image=image;
	}
	/**
	 * 
	 * @return returns the image
	 */
	public Image getImage()
	{
		return image;
	}
	/**
	 * 
	 * @param x set the x coordinate
	 */
	public void setX(int x)
	{
		this.x=x;
	}
	/**
	 * 
	 * @param y set the y coordinate
	 */
	public void setY(int y)
	{
		this.y=y;
	}
	/**
	 * 
	 * @return gets the x coordinate
	 */
	public int getX()
	{
		return x;
	}
	/**
	 * 
	 * @return gets the y coordinate
	 */
	public int getY()
	{
		return y;
	}
	/**
	 * 
	 * @param dying set the object to dying state if true
	 */
	public void setDying(boolean dying)
	{
		this.dying=dying;
	}
	/**
	 * 
	 * @return returns true if the object is dying
	 */
	public boolean isDying()
	{
		return this.dying;
	}

}
