package alienInvaders;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/**
 * 
 * The Battlefield class renders all the graphics
 * 
 * @author Teodor Merodiyski
 * @version 1.0
 *
 */
public class Battlefield extends JPanel implements Runnable, GameConstants 
{ 

	/**
	 * 
	 */
	private int level=1;
	private int savedDeaths=0;
	private boolean killed=false;
	private static final long serialVersionUID = 1L;
	private long killsCount=0;
    private Dimension d;
    //private ArrayList<AlienShip> alienships;
    //private ArrayList<HighScore> highScores=new ArrayList<HighScore>();
    private List<AlienShip> alienships;
    private List<HighScore> highScores=Collections.synchronizedList(new ArrayList<HighScore>());
    private Human player;
    private Missile missile;

    private int alienX = 150;
    private int alienY = 5;
    private int direction = -1;
    private int deaths = 0;

    private boolean ingame = true;
    private final String explosion = "pics/explosion.png";
    private final String alienPic = "pics/alien.png";
    private String msg = "Game Over";

    private Thread animation;

    /**
     * Battlefield class constructor.
     * 
     */
    public Battlefield() 
    {

        addKeyListener(new TheAdapter());
        setFocusable(true);
        d = new Dimension(BOARD_WIDTH, BOARD_HEIGTH);
        setBackground(Color.black);
        setDoubleBuffered(true);
        initGame();   
        //this.getGraphics().dispose();
    }
    /**
     * Loads the high scores from the file "scores.inc"
     * @throws FileNotFoundException if the file scores.inc does not exist 
     * @throws IOException if the file scores.inc does not exist
     */
    public void loadHighScores() throws FileNotFoundException, IOException
    {
    	File f = new File("scores.inc");
    	FileReader inF = new FileReader(f);
    	BufferedReader in = new BufferedReader(inF);
    	for(int i=0; i<10; i++)
    	{
    		HighScore hs = new HighScore();
    		String tmp_name = in.readLine();
    		String tmp_score = in.readLine();
    		hs.setName(tmp_name);
    		hs.setScore(tmp_score);
    		highScores.add(hs);
    		//result+=(i+1)+") "+tmp_name+" - "+tmp_score+"<br>";
    	}
    	in.close();
    	Collections.sort(highScores, Collections.reverseOrder());
    }
    public void addNotify() 
    {
        super.addNotify();
        initGame();
    }
    /**
     * Initializes the game.
     */
    public void initGame() 
    {
    	this.requestFocusInWindow();
        alienships = Collections.synchronizedList(new ArrayList<AlienShip>());
        ImageIcon ii = new ImageIcon(this.getClass().getResource(alienPic));

        for (int i=0; i < 4; i++) 
            for (int j=0; j < 6; j++) 
            {
                AlienShip alien = new AlienShip(alienX + 18*j, alienY + 18*i);
                alien.setImage(ii.getImage());
                alienships.add(alien);
            }

        player = new Human();
        missile = new Missile();

        if (animation == null || !ingame) 
        {
        	try
        	{
            animation = new Thread(this);
            animation.start();
            }
        	catch(Exception e)
        	{
        		
        	}
        }
    }
    public void pause()
    {
    	animation.suspend();
    }
    public void resume()
    {
    	animation.resume();
    }

    /**
     * method for drawing the array of alien ships
     * @param g Graphics
     */
    public void drawAliens(Graphics g) 
    {
        Iterator<AlienShip> it = alienships.iterator();

        while (it.hasNext()) 
        {
            AlienShip alien = it.next();

            if (alien.isVisible()) 
            {
                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }

            if (alien.isDying()) 
            {
                alien.die();
            }
        }
    }

    /**
     * Method for drawing the player`s cannon
     * @param g Graphics
     */
    public void drawPlayer(Graphics g)
    {

        if (player.isVisible()) 
        {
            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }

        if (player.isDying()) 
        {
            player.die();
            ingame = false;
        }
    }
    /**
     * Method for drawing the missile of the cannon
     * @param g Graphics
     */
    public void drawShot(Graphics g) 
    {
        if (missile.isVisible())
            g.drawImage(missile.getImage(), missile.getX(), missile.getY(), this);
    }
    /**
     * Method for drawing the missiles(bombs) of the aliens
     * @param g
     */
    public void drawBombing(Graphics g) 
    {

        Iterator<AlienShip> i3 = alienships.iterator();

        while (i3.hasNext())
        {
            AlienShip a = i3.next();

            AlienShip.Missile b = a.getMissile();

            if (!b.isDestroyed()) 
            {
                g.drawImage(b.getImage(), b.getX(), b.getY(), this); 
            }
        }
    }
    
    public void paint(Graphics g)
    {
    	super.paintComponent(g);
    	super.paint(g);

    	g.setColor(Color.black);
    	g.fillRect(0, 0, d.width, d.height);
    	g.setColor(Color.DARK_GRAY);   

    	if (ingame) 
    	{
    		
    		//g.drawLine(0, GROUND, BOARD_WIDTH, GROUND);
    		g.fillRect(0, GROUND, BOARD_WIDTH, GROUND);
    		drawAliens(g);
    		drawPlayer(g);
    		drawShot(g);
    		drawBombing(g);
    		g.setColor(Color.green);
    		g.drawString(Integer.toString(deaths), 40, 20);
    	}

    	Toolkit.getDefaultToolkit().sync();
    	g.dispose();
    }
    /**
     * invoked when the cannon of the player was destroyed
     * @throws IOException if there is a problem with the file "scores.inc"
     * @throws URISyntaxException if there is a problem with the file "scores.inc"
     */
    public void gameOver() throws IOException, URISyntaxException
    {
   	
        Graphics g = this.getGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGTH);
        
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g.setColor(Color.green);
        g.setFont(small);
        String msg1="Kills:"+Long.toString(killsCount);
        g.drawString(msg, (BOARD_WIDTH - metr.stringWidth(msg))/2, 
            BOARD_WIDTH/2 - 50);
        g.drawString(msg1, (BOARD_WIDTH - metr.stringWidth(msg))/2+20, 
                BOARD_WIDTH/2);
    	if(deaths>Integer.parseInt(highScores.get(9).getScore()))
    	{
    		HighScore tmpHS = new HighScore();
    		String name = JOptionPane.showInputDialog("Enter your name:");
    		tmpHS.setName(name);
    		tmpHS.setScore(Integer.toString(deaths));
    		highScores.add(tmpHS);
    		Collections.sort(highScores, Collections.reverseOrder());
    		FileWriter outFile = new FileWriter("scores.inc",false);
    		BufferedWriter wr = new BufferedWriter(outFile);
    		for(int i=0; i<10; i++)
    		{
    			wr.write(highScores.get(i).getName()+"\n"+highScores.get(i).getScore()+"\n");
    		}
    		wr.close();
    		outFile.close();
    	}
    }
    /**
     * invoked when in between levels. Prints the number of the next level and the total kills
     */
    void betweenLvlScreen()
    {
    	level++;
    	Graphics g = this.getGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGTH);
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g.setColor(Color.green);
        g.setFont(small);
        String msg1="Kills:"+Long.toString(killsCount);
        msg="Level "+ level;
        g.drawString(msg, (BOARD_WIDTH - metr.stringWidth(msg))/2, 
            BOARD_WIDTH/2 - 50);
        //g.drawString(msg1, (BOARD_WIDTH - metr.stringWidth(msg))/2+20, 
        //        BOARD_WIDTH/2);
        //g.dispose();
        try 
        {
        	animation.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ingame=true;
    }
    /**
     * This method is called constantly to do the animation.
     * Checks for destruction of the cannon, counts the kills, moves the objects on the screen.
     */
    public void animationCycle() 
    {
        if (deaths%ALIENS_COUNT==0 && deaths!=0 && savedDeaths!=deaths)//ALIENS_COUNT) 
        {
        	savedDeaths=deaths;
            //ingame = false;
            //msg = "YOU WIN!";
        	ingame=false;
        	betweenLvlScreen();
        	//deaths++;
        	initGame();
        }
        player.act();
        if (missile.isVisible())
        {
            Iterator<AlienShip> it = alienships.iterator();
            int shotX = missile.getX();
            int shotY = missile.getY();
            while (it.hasNext()) {
                AlienShip alien = it.next();
                int alienX = alien.getX();
                int alienY = alien.getY();

                if (alien.isVisible() && missile.isVisible())
                {
                    if (shotX >= (alienX) && 
                        shotX <= (alienX + ALIEN_WIDTH) &&
                        shotY >= (alienY) &&
                        shotY <= (alienY+ALIEN_HEIGHT) )
                    	{
                    		killsCount++;
                            ImageIcon ii = 
                                new ImageIcon(getClass().getResource(explosion));
                            alien.setImage(ii.getImage());
                            alien.setDying(true);
                            deaths++;
                            missile.die();
                        }
                }
            }

            int y = missile.getY();
            y -= 4;
            if (y < 0)
            	missile.die();
            else missile.setY(y);
        }

         Iterator<AlienShip> it1 = alienships.iterator();

         while (it1.hasNext())
         {
             AlienShip a1 = it1.next();
             int x = a1.getX();

             if (x  >= BOARD_WIDTH - BORDER_RIGHT && direction != -1) 
             {
                 direction = -1;
                 Iterator<AlienShip> i1 = alienships.iterator();
                 while (i1.hasNext()) {
                	 AlienShip a2 = i1.next();
                     a2.setY(a2.getY() + GO_DOWN);
                 }
             }

            if (x <= BORDER_LEFT && direction != 1) 
            {
                direction = 1;

                Iterator<AlienShip> i2 = alienships.iterator();
                while (i2.hasNext())
                {
                	AlienShip a = i2.next();
                    a.setY(a.getY() + GO_DOWN);
                }
            }
        }


        Iterator<AlienShip> it = alienships.iterator();

        while (it.hasNext())
        {
        	AlienShip alien = it.next();
            if (alien.isVisible())
            {

                int y = alien.getY();

                if (y > GROUND - ALIEN_HEIGHT)
                {
                    ingame = false;
                    msg = "Invasion!";
                }

                alien.act(direction);
            }
        }

        // bombs

        Iterator<AlienShip> i3 = alienships.iterator();
        Random generator = new Random();

        while (i3.hasNext()) 
        {
            int shot = generator.nextInt(15);
            AlienShip a = i3.next();
            AlienShip.Missile b = a.getMissile();
            if (shot == CHANCE && a.isVisible() && b.isDestroyed()) 
            {
                b.setDestroyed(false);
                b.setX(a.getX());
                b.setY(a.getY());   
            }

            int bombX = b.getX();
            int bombY = b.getY();
            int playerX = player.getX();
            int playerY = player.getY();

            if (player.isVisible() && !b.isDestroyed())
            {
                if ( bombX >= (playerX) && 
                    bombX <= (playerX+PLAYER_WIDTH) &&
                    bombY >= (playerY) && 
                    bombY <= (playerY+PLAYER_HEIGHT) )
                	{
                        ImageIcon ii = 
                            new ImageIcon(this.getClass().getResource(explosion));
                        player.setImage(ii.getImage());
                        player.setDying(true);
                        b.setDestroyed(true);;
                    }
            }

            if (!b.isDestroyed()) 
            {
                b.setY(b.getY() + 1);   
                if (b.getY() >= GROUND - BOMB_HEIGHT) 
                {
                    b.setDestroyed(true);
                }
            }
        }
    }
    /**
     * Sets that we are in game.
     */
    void start()
    {
    	ingame=true;
    }
    /**
     * Stops the game.
     */
    void stop()
    {
    	animation.interrupt();
    }
    
    public void run() {
    	try {
			loadHighScores();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (ingame)
        {
            repaint();
            animationCycle();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0) 
                sleep = 2;
            try 
            {
                Thread.sleep(sleep);
            }
            catch (InterruptedException e)
            {
                System.out.println("interrupted");
            }
            beforeTime = System.currentTimeMillis();
        }
        try {
			gameOver();
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * Captures key strokes.
     * @author Teodor Merodiyski
     *
     */
    private class TheAdapter extends KeyAdapter
    {

        public void keyReleased(KeyEvent e) 
        {
            player.keyReleased(e);
        }

        public void keyPressed(KeyEvent e)
        {
          player.keyPressed(e);
          int y = player.getY();
          int x = player.getX();

          if (ingame)
          {
            if (e.getKeyCode()==KeyEvent.VK_SPACE) 
            {
                if (!missile.isVisible())
                	missile = new Missile(x, y);
            }
          }
        }
    }
}