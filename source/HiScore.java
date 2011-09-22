/**
 *  HiScore - A single entry of High Score, keep the name and the score.
 * 
 *  
 *  @author Kar Wai Lim (u4361554)
 *  
 */

public class HiScore implements Comparable<HiScore> {

	private String name;
	private Double score;


	public HiScore(String n, Double s) {
		name = n;
		score = s;
	}

	public String name() {
		return name;
	}

	public Double score() {
		return score;
	}


	@Override
	// for sorting the High Score table
	public int compareTo(HiScore o) {
		return score.compareTo(o.score());
	}

	// for display on console
	@Override
	public String toString() {
		return (name + "  -  " + score);
	}



}
