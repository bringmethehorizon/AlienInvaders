package alienInvaders;

import javax.swing.ImageIcon;

/**
 * @author Teodor Merodiyski
 * Represents an alien ship.
 *
 */
public class AlienShip extends Object2D {
	/**
	 * Every AlienShip can have only one missile.
	 */
	private Missile missile;
	private final String missilePic = "pics/alien.png";

	/**
	 * AlienShip constructor.
	 * @param i the initial x coordinate
	 * @param j the initial y coordinate
	 */
	public AlienShip(int i, int j) {
		this.x=i;
		this.y=j;
		missile=new Missile(x,y);
		ImageIcon im = new ImageIcon(this.getClass().getResource(missilePic));
		setImage(im.getImage());
	}
	/**
	 * 
	 * @param direction if direction is greater than zero the alien ship moves to the right, if it`s less than zero it moves to the left
	 */
	public void act(int direction)
	{
		this.x+=direction;
	}
	/**
	 * 
	 * @return Returns the missile of the alien ship
	 */
	public Missile getMissile()
	{
		return missile;
	}
	/**
	 * the class Missile of the alien ship
	 */
	public class Missile extends Object2D
	{
		private final String bomb = "pics/missile.png";
		private boolean destroyed;
		/**
		 * Missile constructor
		 * @param x the initial x coordinate of the missile
		 * @param y the initial y coordinate of the missile
		 */
		public Missile(int x, int y)
		{
			setDestroyed(true);
			this.x=x;
			this.y=y;
			ImageIcon im = new ImageIcon(this.getClass().getResource(bomb));
			setImage(im.getImage());
		}
		/**
		 * Method to destroy or recreate the missile of the alien ship
		 * @param destroyed sets if the missile is destroyed
		 */
		public void setDestroyed(boolean destroyed)
		{
			this.destroyed=destroyed;
		}
		/**
		 * Method to check if the missile is destroyed
		 * @return true if the missile is destoyed, else - false
		 */
		public boolean isDestroyed()
		{
			return destroyed;
		}
	}
}
