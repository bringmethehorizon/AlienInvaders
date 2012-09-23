/**
 * 
 */
package alienInvaders;

/**
 * Class representing an entry in the high scores
 * @author Teodor Merodiyski
 *
 */
public class HighScore implements Comparable
{
	/**
	 * 
	 * @return the name of the player
	 */
	public String getName() {
		return name;
	}
	/**
	 * 
	 * @param name the name to be set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 
	 * @return the score of this high score entry
	 */
	public String getScore() {
		return score;
	}
	/**
	 * 
	 * @param score set the score
	 */
	public void setScore(String score) {
		this.score = score;
	}
	private String name;
	private String score;
	/**
	 * method for comparing two high scores
	 */
	@Override
	public int compareTo(Object otherScore) {
		if(otherScore instanceof HighScore)
		{
			HighScore tmpscore=(HighScore) otherScore;
			if(Integer.parseInt(this.getScore()) > Integer.parseInt(tmpscore.getScore())) return 1;
			if(Integer.parseInt(this.getScore()) < Integer.parseInt(tmpscore.getScore())) return -1;
			if(Integer.parseInt(this.getScore()) == Integer.parseInt(tmpscore.getScore())) return 0;
		}
		return 0;
	}
}
